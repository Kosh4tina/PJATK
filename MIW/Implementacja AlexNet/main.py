import argparse
import numpy as np
from tensorflow import keras
import matplotlib.pyplot as plt
from tensorflow.keras.utils import to_categorical
from sklearn.metrics import accuracy_score
from tensorflow.keras.datasets import cifar100


def print_plot(history):
    plt.plot(history.history['loss'])
    plt.ylabel('loss')
    plt.xlabel('epoch')
    plt.legend(['train', 'test'], loc='upper left')
    plt.show()
    plt.plot(history.history['accuracy'])
    plt.ylabel('accuracy')
    plt.xlabel('epoch')
    plt.legend(['train', 'test'], loc='upper left')
    plt.title('AlexNet')
    plt.show()


if __name__ == '__main__':
    # Define the program description
    # Initiate the parser with a description
    parser = argparse.ArgumentParser(description="AlexNet s19575", conflict_handler="resolve")
    parser.add_argument("-j", "--json_file_name", help="json file name", type=str, default="miw_alexnet_s19575.json",
                        required=False)
    parser.add_argument("-w", "--weights_file_name", help="name of h5 file with weights", default="miw_alexnet_s19575_weights.h5", type=str, required=False)
    parser.add_argument("-e", "--n_epochs", help="number of net epochs", type=int, default=2)

    args = vars(parser.parse_args())
    print(args)

    json_file_name = args["json_file_name"]
    weights_file_name = args["weights_file_name"]
    n_epochs = args["n_epochs"]

    (X_train, y_train), (X_test, y_test) = cifar100.load_data(label_mode="fine")

    y_train = to_categorical(y_train)
    y_test = to_categorical(y_test)

    for i in range(10):
        print(y_train[i])
        plt.imshow(X_train[i])
        plt.show()

    AlexNet = keras.models.Sequential()

    AlexNet.add(keras.layers.Conv2D(input_shape=X_train.shape[1:], filters=96, kernel_size=(11, 11), strides=(4, 4),
                                    padding='same', activation='relu'))
    AlexNet.add(keras.layers.BatchNormalization())
    AlexNet.add(keras.layers.MaxPooling2D(pool_size=(3, 3), strides=(2, 2), padding='same'))

    AlexNet.add(keras.layers.Conv2D(filters=256, kernel_size=(5, 5), strides=(1, 1), padding='same', activation='relu'))
    AlexNet.add(keras.layers.BatchNormalization())
    AlexNet.add(keras.layers.MaxPooling2D(pool_size=(3, 3), strides=(2, 2), padding='same'))

    AlexNet.add(keras.layers.Conv2D(filters=384, kernel_size=(3, 3), strides=(1, 1), padding='same', activation='relu'))

    AlexNet.add(keras.layers.Conv2D(filters=384, kernel_size=(3, 3), strides=(1, 1), padding='same', activation='relu'))

    AlexNet.add(keras.layers.Conv2D(filters=256, kernel_size=(3, 3), strides=(1, 1), padding='same', activation='relu'))
    AlexNet.add(keras.layers.MaxPooling2D(pool_size=(3, 3), strides=(2, 2), padding='same'))

    AlexNet.add(keras.layers.Flatten())
    AlexNet.add(keras.layers.Dense(4096, input_shape=(32, 32, 3,), activation='relu'))
    AlexNet.add(keras.layers.Dropout(0.5))

    AlexNet.add(keras.layers.Dense(4096, activation='relu'))
    AlexNet.add(keras.layers.Dropout(0.5))

    AlexNet.add(keras.layers.Dense(100, activation='softmax'))

    AlexNet.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
    history = AlexNet.fit(X_train, y_train, batch_size=32, epochs=n_epochs)

    model_json = AlexNet.to_json()
    with open(json_file_name, "w") as json_file:
        json_file.write(model_json)
    AlexNet.save_weights(weights_file_name)

    y_predicted = AlexNet.predict(X_test)
    print("Score:", accuracy_score(np.argmax(y_predicted, axis=1), np.argmax(y_test, axis=1)))
    print_plot(history)
