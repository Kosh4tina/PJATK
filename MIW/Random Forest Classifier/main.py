#!/usr/bin/python

import argparse
import sys
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import classification_report
import numpy as np
from sklearn.utils import resample
from graphviz import Digraph


class DecisionTree:
    class TreeNode:
        def __init__(self, pred_class, global_id):
            self.global_id = global_id
            self.pred_class = pred_class
            self.index = 0
            self.threshold = 0
            self.gini = 0
            self.left = None
            self.right = None

    def __init__(self, X, y, max_depth=None):
        self.dot = Digraph(comment='The Tree')
        self.global_id = 0
        self.no_of_features = X.shape[1]
        self.no_of_classes = len(set(y))
        self.max_depth = max_depth
        self.tree = self.build(X, y)
        self.print_tree(self.tree)
        self.dot.format = 'png'
        self.dot.render('tree.gv')

    def gini(self, X, y):
        no_of_samples = y.size
        parent_node = []
        for i in range(self.no_of_classes):
            parent_node.append(np.sum(y == i))

        summation = 0
        for j in parent_node:
            summation += (j / no_of_samples) ** 2

        best_gini = 1 - summation

        index_best = None
        threshold_best = None

        for index in range(self.no_of_features):
            thresholds, classes = zip(*sorted(zip(X[:, index], y)))
            left_child = []
            for _ in range(self.no_of_classes):
                left_child.append(0)

            right_child = parent_node.copy()
            for i in range(1, no_of_samples):
                _class = classes[i - 1]

                left_child[_class] += 1
                right_child[_class] -= 1

                summation1 = 0
                for x in range(self.no_of_classes):
                    summation1 += (left_child[x] / i) ** 2
                gini_left_child = 1.0 - summation1

                summation2 = 0
                for x in range(self.no_of_classes):
                    summation2 += (right_child[x] / (no_of_samples - i)) ** 2
                gini_right_child = 1.0 - summation2
                gini = (i * gini_left_child + (no_of_samples - i) * gini_right_child) / no_of_samples

                if thresholds[i] == thresholds[i - 1]:
                    continue

                if gini < best_gini:
                    best_gini = gini
                    index_best = index
                    threshold_best = (thresholds[i - 1] + thresholds[i]) / 2

        l = [index_best, threshold_best, best_gini]
        return l

    def build(self, X, y, depth=0):
        self.global_id += 1

        no_of_samples_per_class = []
        for i in range(self.no_of_classes):
            no_of_samples_per_class.append(np.sum(y == i))

        majority_class = np.argmax(no_of_samples_per_class)
        treeNode = self.TreeNode(pred_class=majority_class, global_id=self.global_id)

        if depth < self.max_depth:
            l = self.gini(X, y)
            index = l[0]
            threshold = l[1]

            if index is not None:
                left_indices = X[:, index] < threshold
                X_left = X[left_indices]
                y_left = y[left_indices]
                X_right = X[~left_indices]
                y_right = y[~left_indices]

                treeNode.index = index
                treeNode.threshold = threshold
                treeNode.left = self.build(X_left, y_left, depth + 1)
                treeNode.right = self.build(X_right, y_right, depth + 1)
                treeNode.gini = l[2]

        return treeNode

    def print_tree(self, tree):
        self.dot.node(str(tree.global_id), str([tree.pred_class, tree.threshold, str(tree.gini)[:5]]))
        if tree.left:
            self.dot.edge(str(tree.global_id), str(tree.left.global_id))
            self.print_tree(tree.left)
        if tree.right:
            self.dot.edge(str(tree.global_id), str(tree.right.global_id))
            self.print_tree(tree.right)

    def predict_row(self, row):
        treeNode = self.tree
        while treeNode.left:
            if row[treeNode.index] < treeNode.threshold:
                treeNode = treeNode.left
            else:
                treeNode = treeNode.right
        return treeNode.pred_class

    def predict(self, X):
        l = []
        for row in X:
            l.append(self.predict_row(row))
        return l


class RandomForest:
    def __init__(self, n_trees=10, max_depth=None):
        self.max_depth = max_depth
        self.y_pred_all = []
        self.n_trees = n_trees

    def train(self, X, y, X_test):
        for i in range(self.n_trees):
            X2, y2 = resample(X, y)
            clf = DecisionTree(X2, y2, max_depth=self.max_depth)
            y_pred = clf.predict(X_test)
            self.y_pred_all.append(y_pred)

    def predict(self):
        y_pred = []
        for i in range(len(self.y_pred_all[0])):
            count1, count2, count3 = 0, 0, 0
            for j in range(len(self.y_pred_all)):
                if self.y_pred_all[j][i] == 0:
                    count1 += 1
                elif self.y_pred_all[j][i] == 1:
                    count2 += 1
                else:
                    count3 += 1

            maxi = max(count1, count2, count3)

            if count1 == maxi:
                y_pred.append(0)
            elif count2 == maxi:
                y_pred.append(1)
            else:
                y_pred.append(2)

        return y_pred


if __name__ == '__main__':
    # Define the program description
    # Initiate the parser with a description
    parser = argparse.ArgumentParser(description="Gini tree builder", conflict_handler="resolve")
    parser.add_argument("-t", "--train_file", help="csv file with  data (required)", required=True)
    parser.add_argument("-c", "--classification_column", help="name of column in dataset with classification data ("
                                                              "required)", type=str, required=True)
    parser.add_argument("-s", "--train_test_split", help="how many of datapoint will be used for tests",
                        type=lambda x: (float(x) > 0.2) and (float(x) < 0.8) and float(x) or
                                       sys.exit("default and min 0.2 while max 0.8"), default=0.2)
    parser.add_argument("--max_depth", help="Maximum depth of tree, dafault value is 5 and this is a ineteger",
                        type=lambda x: (int(x) > 1) and int(x), default=5)
    parser.add_argument("--acceptable_impurity", help="Level of impurity at which we no longer split nodes, default "
                                                      "value 0", type=int, default=0)

    args = vars(parser.parse_args())
    print(args)

    dataset = pd.read_csv(args["train_file"])

    i = 0
    for column in dataset.columns:
        if column == args["classification_column"]:
            print(dataset.iloc[:, i:i+1])
            break
        i = i + 1
    X = dataset.iloc[:, i:i+1].values
    y = dataset.iloc[:, -1].values

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=args["train_test_split"], random_state=211)
    clf2 = RandomForest(n_trees=3, max_depth=args["max_depth"])
    clf2.train(X_train, y_train, X_test)
    y_pred2 = clf2.predict()
    report = classification_report(y_test, y_pred2)
    with open('classification_report.txt', 'w') as file:
        file.write(report)
    print(report)
