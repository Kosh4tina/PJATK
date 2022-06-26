import java.io.*;
import java.util.*;
import java.util.function.UnaryOperator;

public class NeuralNetwork {

    public double learningRate; // alfa
    private final Layer[] layers;
    static int num_of_answers = 10;
    UnaryOperator<Double> activation = x -> 1 / (1 + Math.exp(-x));
    UnaryOperator<Double> derivative = y -> y * (1 - y);
    static NeuralNetwork nn = new NeuralNetwork(0.01, 30, 12, num_of_answers);

    public static void main(String[] args) throws IOException {

        // LEARNING
        ArrayList data = ReadFile.read("dataset.txt");
        int epochs = 1000000;
       // ArrayList<Double> errors = new ArrayList<Double>();

        for (int i = 1; i <= epochs; i++) {
            double errorSum = 0;

            int imgIndex = (int) ((Math.random()) * data.size()); // random picture from dataset
            ArrayList curr_data = (ArrayList) data.get(imgIndex);

            double[] targets = new double[10];
            int digit = (int) curr_data.get(curr_data.size() - 1);
            targets[digit] = 1.0; // targets na pozicii prawilnogo otweta stawim 1, ostalnyje 0
            double inputs[] = new double[30];
            for (int k = 0; k < inputs.length; k++) {
                inputs[k] = (double) ((int) curr_data.get(k));
            }
            double[] outputs = nn.feedForward(inputs);
            int maxDigit = 0;
            double maxDigitWeight = -1;
            for (int k = 0; k < num_of_answers; k++) {
                if (outputs[k] > maxDigitWeight) {
                    maxDigitWeight = outputs[k];
                    maxDigit = k;
                }
            }
            if (digit == maxDigit) ;

            // schitajem koof oshybki (nie procent a koof)
            for (int k = 0; k < 10; k++) {
                errorSum += Math.abs(targets[k] - outputs[k]);
            }
            nn.backpropagation(targets); // correct for each layer
            System.out.println("epoch: " + i + "/" + epochs + " error_coefficient: " + errorSum);
           // errors.add(errorSum);
        }

        // TEST + accuracy
        int error_in_answer = 0;
        ArrayList test_data_all = ReadFile.read("test_set.txt");
        for (int i = 0; i < test_data_all.size(); i++) {
            System.out.println();
            ArrayList test_data = (ArrayList) test_data_all.get(i);
            double test[] = new double[30];
            for (int k = 0; k < test.length; k++) {
                test[k] = (double) ((int) test_data.get(k));
            }
            int answer = check(test);
            System.out.println("right answer = " + test_data.get(test_data.size() - 1));
            if(answer == (int)test_data.get(test_data.size() - 1)){
                System.out.println("Network gave right answer!");
            }else {
                System.out.println("Network failed!");
                error_in_answer++;
            }
        }
        if(error_in_answer == 0){
            System.out.println("100% accuracy");
        }else {
            System.out.println((int)(error_in_answer*100/test_data_all.size()) + "% of errors");
        }

//        System.out.println(errors);
//        try {
//            FileWriter myWriter = new FileWriter("filename.txt");
//            for (int i = 0; i < errors.size(); i = i + 1000) {
//                myWriter.write(String.valueOf(errors.get(i)) + "\n");
//            }
//            myWriter.close();
//            System.out.println("Successfully wrote to the file.");
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//        double[] cheker = new double[]{
//                1, 1, 1, 1, 1,
//                1, 0, 0, 0, 1,
//                1, 0, 0, 0, 1,
//                1, 1, 1, 1, 1,
//                0, 0, 0, 0, 1,
//                1, 1, 1, 1, 1};
//
//        check(cheker);


    }

    public static int check(double[] inputs) {
        double[] outputs = NeuralNetwork.nn.feedForward(inputs);

        int maxDigit = 0;
        double maxDigitWeight = -1;
        for (int i = 0; i < num_of_answers; i++) {
            if (outputs[i] > maxDigitWeight) {
                maxDigitWeight = outputs[i];
                maxDigit = i;
            }
        }

        System.out.println("Answer = " + (maxDigit));
        for (int i = 0; i < num_of_answers; i++) {
            System.out.println(i + " - " + outputs[i]);
        }
        return maxDigit;
    }


    public NeuralNetwork(double learningRate, int... sizes) {
        this.learningRate = learningRate;
        layers = new Layer[sizes.length];
        for (int i = 0; i < sizes.length; i++) {
            int nextSize = 0;

            if (i < sizes.length - 1) {
                nextSize = sizes[i + 1];
            }

            layers[i] = new Layer(sizes[i], nextSize);

            for (int j = 0; j < sizes[i]; j++) {
                layers[i].biases[j] = Math.random() * 2.0 - 1.0;
                for (int k = 0; k < nextSize; k++) {
                    layers[i].weights[j][k] = Math.random() * 2.0 - 1.0;
                }
            }
        }
    }

    public double[] feedForward(double[] inputs) {
        System.arraycopy(inputs, 0, layers[0].neurons, 0, inputs.length); // gruzim toczki w perwyj sloj
        for (int i = 1; i < layers.length; i++) { //prochodim po wsem slojam
            Layer l = layers[i - 1];
            Layer l1 = layers[i];
            for (int j = 0; j < l1.size; j++) {
                l1.neurons[j] = 0;
                for (int k = 0; k < l.size; k++) {
                    l1.neurons[j] += l.neurons[k] * l.weights[k][j];
                }
                l1.neurons[j] += l1.biases[j]; // szagaem (sm. class Layer)
                l1.neurons[j] = activation.apply(l1.neurons[j]); // po formulam
            }
        }
        return layers[layers.length - 1].neurons; //wozwraszajem poslednij (s otwetami po kazdoj cyfre)
    }

    public void backpropagation(double[] targets) {
        // gruzim oszybki iz posledniego sloja ( prawilnyj_otwet - poluczenyj_otwet = error )
        double[] errors = new double[layers[layers.length - 1].size];
        for (int i = 0; i < layers[layers.length - 1].size; i++) {
            errors[i] = targets[i] - layers[layers.length - 1].neurons[i];
        }

        // s konca w samyj perwyj
        for (int k = layers.length - 2; k >= 0; k--) {
            Layer l = layers[k];
            Layer l1 = layers[k + 1];
            double[] errorsNext = new double[l.size];
            double[] gradients = new double[l1.size];
            // po formulam
            for (int i = 0; i < l1.size; i++) {
                gradients[i] = errors[i] * derivative.apply(layers[k + 1].neurons[i]);
                gradients[i] *= learningRate;
            }
            double[][] deltas = new double[l1.size][l.size];
            for (int i = 0; i < l1.size; i++) {
                for (int j = 0; j < l.size; j++) {
                    deltas[i][j] = gradients[i] * l.neurons[j];
                }
            }
            for (int i = 0; i < l.size; i++) {
                errorsNext[i] = 0;
                for (int j = 0; j < l1.size; j++) {
                    errorsNext[i] += l.weights[i][j] * errors[j];
                }
            }
            errors = new double[l.size];
            System.arraycopy(errorsNext, 0, errors, 0, l.size);
            double[][] weightsNew = new double[l.weights.length][l.weights[0].length];
            for (int i = 0; i < l1.size; i++) {
                for (int j = 0; j < l.size; j++) {
                    weightsNew[j][i] = l.weights[j][i] + deltas[i][j];
                }
            }
            l.weights = weightsNew;
            for (int i = 0; i < l1.size; i++) {
                l1.biases[i] += gradients[i];
            }
        }
    }
}

class Layer {
    public int size;
    public double[] neurons; // toczki w sloje (100x100 -> neurons.length = 1000)
    public double[] biases; // bound tol'ko dla kazdoj koordinaty swoj ( bound eto wspomogatelnyj neuron (tupo po formulam) )
    public double[][] weights;

    public Layer(int size, int nextSize) {
        this.size = size;
        neurons = new double[size];
        biases = new double[size];
        weights = new double[size][nextSize];
    }
}

class ReadFile {
    public static void main(String[] args) {
        read("/home/gattto113/Documents/NAI_3/dataset.txt");
    }

    public static ArrayList read(String filePath) {
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            String strings_from_file = myReader.nextLine() + '\n';
            while (myReader.hasNextLine()) {
                strings_from_file += myReader.nextLine();
                strings_from_file += '\n';
            }
            myReader.close();
            String[] parts = strings_from_file.split("\n");
            String tmp = null;
            ArrayList<String> tmp_data = new ArrayList<String>();
            ArrayList<Integer> tmp_answer = new ArrayList<Integer>();
            int j = 0;
            for (String part : parts) {
                if (j < 6) {
                    if (tmp == null) {
                        tmp = part;
                    } else {
                        tmp += part;
                    }
                    tmp += '\n';
                    j++;
                } else {
                    tmp_data.add(tmp);
                    tmp = null;
                    tmp_answer.add(Integer.parseInt(part.trim()));
                    j = 0;
                }
            }

            ArrayList<ArrayList<Integer>> data = new ArrayList<ArrayList<Integer>>();
            for (int i = 0; i < tmp_data.size(); i++) {
                String[] data_parts = tmp_data.get(i).replaceAll("\n", " ").split(" ");
                ArrayList<Integer> data_int = new ArrayList<>();
                for (String part : data_parts) {
                    data_int.add(Integer.parseInt(part));
                }
                data_int.add(tmp_answer.get(i));
                data.add(data_int);
            }


            for (ArrayList list : data) {
                int t = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (t != 4) {
                        t++;
                        System.out.print(list.get(i));
                    } else {
                        System.out.println(list.get(i));
                        t = 0;
                    }
                }

                System.out.println("\n");
            }

            return data;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }
}

