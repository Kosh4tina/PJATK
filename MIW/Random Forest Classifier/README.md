# Random Forest Classifier

### Implement a random forest classifier from scratch using Gini Impurity measure. 

Classes that you should implement are "**DecisionTree**"  and "**RandomForest**".
User should be able to run program with following parameters:
- -t, --train_file  : csv file with  data (required)
- -s, --train_test_split : how many of datapoint will be used for tests (default and min 0.2  while max 0.8)
- -c, --clasification_column : name of column in dataset with classification data (required)
- --max_depth : Maximum depth of tree, dafault value is 5 and this is a ineteger
- --acceptable_impurity : Level of impurity at which we no longer split nodes, default value 0
Script should output visualisation of trees as **png** files as well as txt file with information about correctnes level on **test dataset**.

As a result you should upload
- code as .py
- visualisation od single tree
- txt file with information about prediction quality

You must use provided depression dataset and attempt to predict if someone is depressed or not.


Dataset description (columns):
>Surveyid Villeid

>sex

>Age

>Married

>Numberchildren educationlevel

>totalmembers (in the family) gainedasset

>durableasset saveasset

>livingexpenses otherexpenses

>incomingsalary incomingownfarm incomingbusiness

>incomingnobusiness

>incomingagricultural farmexpenses

>laborprimary lastinginvestment

>nolastinginvestmen

depressed: [ Zero: No depressed] or [One: depressed] (Binary for target class) the main objective is to show statistic analysis and some data mining techniques.

#### The dataset has 23 columns or dimensions and a total of 1432 rows or objects.
