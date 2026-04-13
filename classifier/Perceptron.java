public class Perceptron {

    private double[] weights; // array of weights
    private int inputs; // number of inputs

    // Creates a perceptron with n inputs. It should create an array
    // of n weights and initialize them all to 0.
    public Perceptron(int n) {
        weights = new double[n];
        inputs = n;
    }

    // Returns the number of inputs n.
    public int numberOfInputs() {
        return inputs;
    }

    // Returns the weighted sum of the weight vector and x.
    public double weightedSum(double[] x) {
        double sum = 0;
        for (int i = 0; i < numberOfInputs(); i++) {
            sum += weights[i] * x[i];
        }
        return sum;
    }

    // Predicts the binary label (+1 or -1) of input x. It returns +1
    // if the weighted sum is positive and -1 if it is negative (or zero).
    public int predict(double[] x) {
        if (weightedSum(x) > 0) return 1;
        return -1;
    }

    // Trains this perceptron on the binary labeled (+1 or -1) input x.
    // The weights vector is updated accordingly.
    public void train(double[] x, int binaryLabel) {

        if (predict(x) == binaryLabel) {
            return;
        }

        if (predict(x) == 1 && binaryLabel == -1) {
            for (int i = 0; i < inputs; i++) {
                weights[i] = weights[i] - x[i];
            }
            return;
        }

        if (predict(x) == -1 && binaryLabel == 1) {
            for (int i = 0; i < inputs; i++) {
                weights[i] = weights[i] + x[i];
            }
        }
    }

    // Returns a String representation of the weight vector, with the
    // individual weights separated by commas and enclosed in parentheses.
    // Example: (2.0, 1.0, -1.0, 5.0, 3.0)
    public String toString() {
        String weightString = "(";
        for (int i = 0; i < inputs; i++) {
            weightString += weights[i];
            if (i != inputs - 1) weightString += ", ";
        }
        weightString += ")";
        return weightString;
    }

    // Tests this class by directly calling all instance methods.
    public static void main(String[] args) {
        double[] inputArr = { 3, 4, 5 };
        int len = inputArr.length;

        Perceptron p = new Perceptron(len);
        StdOut.println(p.toString());

        StdOut.println(p.numberOfInputs());
        StdOut.println(p.weightedSum(inputArr));

        // training
        p.train(inputArr, p.predict(inputArr));

    }
}
