Implementacja AlexNet

Using Keras library implement AlexNet and train it to recognize fine grained classes from CIFAR100 dataset.
This dataset is integrated into keras and can be loaded with command:
tf.keras.datasets.cifar100.load_data(label_mode='fine')
given that "import tensorflow as tf" was called first.
It is important to set label mode to "fine" otherwise a more coarse categorization will be provided which is 
not in accordance with task requirements.

Example parameters of AlexNet were given in task attachment, however  you should remember that input and output layers sizes 
are determined by data that you are working with not by a network architecture.

For grading you should provide:
Script that teaches and saves network (name of file to which save weights and architecture should be provided from command line)
JSON file with network architecture
HDF5 file with weights
Plot with history of training and validation loss