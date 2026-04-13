public class MultiPerceptron {
    // Creates a multi-perceptron object with m classes and n inputs.
    // It creates an array of m perceptrons, each with n inputs.
    private Perceptron[] perceptrons;

    // constructor for MultiPerceptron
    public MultiPerceptron(int m, int n) {
        perceptrons = new Perceptron[m];
        for (int i = 0; i < m; i++) {
            perceptrons[i] = new Perceptron(n);
        }
    }

    // Returns the number of classes m.
    public int numberOfClasses() {
        return perceptrons.length;
    }

    // Returns the number of inputs n (length of the feature vector).
    public int numberOfInputs() {
        return perceptrons[0].numberOfInputs();
    }

    // Returns the predicted class label (between 0 and m-1) for the given input.
    public int predictMulti(double[] x) {

        int classLabel = 0;
        for (int i = 0; i < this.numberOfClasses(); i++) {

            if (perceptrons[i].weightedSum(x) >
                    perceptrons[classLabel].weightedSum(x)) {
                classLabel = i;
            }
        }
        return classLabel;
    }

    // Trains this multi-perceptron on the labeled (between 0 and m-1) input.
    public void trainMulti(double[] x, int classLabel) {
        for (int i = 0; i < this.numberOfClasses(); i++) {
            if (i == classLabel) {
                perceptrons[i].train(x, 1);
            }
            else {
                perceptrons[i].train(x, -1);
            }
        }

    }

    // Returns a String representation of this MultiPerceptron, with
    // the string representations of the perceptrons separated by commas
    // and enclosed in parentheses.
    // Example with m = 2 and n = 3: ((2.0, 0.0, -2.0), (3.0, 4.0, 5.0))
    public String toString() {
        String output = "(";

        for (int i = 0; i < this.numberOfClasses(); i++) {
            output += perceptrons[i];

            if (i == numberOfClasses() - 1) {
                output += ")";
                break;
            }
            output += ", ";
        }
        return output;
    }

    // Tests this class by directly calling all instance methods.
    public static void main(String[] args) {
        double[] inputArr = { 3, 4, 5 };

        int numClass = 2;
        int len = inputArr.length;
        MultiPerceptron m = new MultiPerceptron(numClass, len);

        StdOut.println(m.numberOfClasses());
        StdOut.println(m.predictMulti(inputArr));
        StdOut.println(m.numberOfInputs());
        m.trainMulti(inputArr, m.predictMulti(inputArr));
    }
}
