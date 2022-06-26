#!/usr/bin/python

import argparse
from math import exp
from random import random
from random import randrange


# converting data from csv file to dataset format, translated human class names to binary
def process_input_file(input_file_name):
    try:
        input_file = open(input_file_name, "r")
        input_string = input_file.read().split('\n')
        input_data = []
        human_named_classifies = []
        for line in input_string:
            if line == '':
                continue
            raw_data = line.split(',')
            data = []
            for value in raw_data:
                try:
                    value = float(value)
                    data.append(value)
                except ValueError:
                    if value not in human_named_classifies:
                        human_named_classifies.append(value)
                    data.append(human_named_classifies.index(value))
            if data not in input_data:
                input_data.append(data)
        if human_named_classifies != '':
            return input_data, human_named_classifies
        else:
            return input_data, None
    except FileNotFoundError as e:
        print(e.__str__())


# neuron activation for forward propagation
def activate(weights, inputs):
    activation = weights[-1]
    for i in range(len(weights) - 1):
        activation += weights[i] * inputs[i]
    return activation


# predictions -> implementation of forward propagation
def predict(network, row):
    inputs = row
    for layer in network:
        new_inputs = []
        for neuron in layer:
            activation = activate(neuron['weights'], inputs)
            neuron['output'] = 1.0 / (1.0 + exp(-activation))
            new_inputs.append(neuron['output'])
        inputs = new_inputs
    return inputs


# calculating of backward error
def calculate_propagate_error(network, expected):
    for i in reversed(range(len(network))):
        layer = network[i]
        errors = list()
        if i != len(network) - 1:
            for j in range(len(layer)):
                error = 0.0
                for neuron in network[i + 1]:
                    error += (neuron['weights'][j] * neuron['delta'])
                errors.append(error)
        else:
            for j in range(len(layer)):
                neuron = layer[j]
                errors.append(expected[j] - neuron['output'])
        for j in range(len(layer)):
            neuron = layer[j]
            neuron['delta'] = errors[j] * (neuron['output'] * (1.0 - neuron['output']))


def update_weights(network, row, rate):
    for i in range(len(network)):
        inputs = row[:-1]
        if i != 0:
            inputs = [neuron['output'] for neuron in network[i - 1]]
        for neuron in network[i]:
            for j in range(len(inputs)):
                neuron['weights'][j] += rate * neuron['delta'] * inputs[j]
            neuron['weights'][-1] += rate * neuron['delta']


def back_propagation(train_set, test_set, rate, epochs, hidden):
    # network initialisation (input)->(hidden)->(output)
    network = list()

    hidden_layer = [{'weights': [random() for i in range(len(train_set[0]) - 1 + 1)]} for i in range(hidden)]
    network.append(hidden_layer)

    output_layer = [{'weights': [random() for i in range(hidden + 1)]}
                    for i in range(len(set([row[-1] for row in train_set])))]
    network.append(output_layer)

    # train the net

    for epoch in range(epochs):
        if (epoch/epochs)*100 % 3 == 0:
            print("Training progress: " + str(int((epoch/epochs)*100)) + "%")
        for row in train_set:
            outputs = predict(network, row)
            expected = [0 for i in range(len(set([row[-1] for row in train_set])))]
            expected[row[-1]] = 1
            calculate_propagate_error(network, expected)
            update_weights(network, row, rate)
        # print(outputs.__str__())

    # running the algorithm
    predictions = list()
    for row in test_set:
        outputs = predict(network, row)
        predictions.append(outputs.index(max(outputs)))
    return predictions


if __name__ == '__main__':
    # Define the program description
    text = '''Using numpy and pandas library implement a multilayer neural network 
    that can be thought via backpropagation.

    Script should print out error for each training value and mean absolute error for test set.

    You can use any dataset you like (for example Iris dataset).
    Suggested source for dataset is Kaggle.'''

    # Initiate the parser with a description
    parser = argparse.ArgumentParser(description=text, conflict_handler="resolve")
    parser.add_argument("-i", "--input", help="Input file for training network", required=True)
    parser.add_argument("-n", "--epochs", help="Number of epochs", type=int, default=100)
    parser.add_argument("--test_split", help="What part of data should be used for validation (default 0.3)",
                        type=float, default=0.3)
    parser.add_argument("-e", "--learning_factor", help="Learning factor", type=float, default=0.05)
    parser.add_argument("--bipolar", help="If set use bipolar function otherwise unipolar", action="store_true")
    parser.add_argument("-h", "--hidden", help="Size of hidden layer", type=int, default=2)
    args = vars(parser.parse_args())

    print(args)
    dataset, classes = process_input_file(args["input"])
    print(classes)

    # dividing into test and train sets
    test_set = list()
    while len(test_set) < int(len(dataset) * args["test_split"]):
        index = randrange(0, len(dataset))
        test_set.append(dataset.pop(index))

    # running the algorithm
    predicted = back_propagation(dataset, test_set, args["learning_factor"], args["epochs"],
                                 args["hidden"])
    print(predicted)

    # getting the actual answer
    actual = [row[-1] for row in test_set]
    print(actual)

    for index in range(0, len(actual)):
        actual_answer = actual[index]
        predicted_answer = predicted[index]
        if actual_answer != predicted_answer:
            print("Prediction error in " + str(index) + "st test value: actual="
                  + str(actual_answer) + " : predicted=" + str(predicted_answer))

    correct_answers = 0
    for i in range(len(actual)):
        if actual[i] == predicted[i]:
            correct_answers += 1

    print("Accuracy %s" % str(correct_answers / float(len(actual)) * 100.0) + "%")
