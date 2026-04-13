import java.awt.Color;

public class ImageClassifier {

    private int width; // width of image
    private int height; // height of image
    private String[] classNames; // class names
    private int numClasses; // num of class
    private MultiPerceptron m; // multi perceptron

    // Uses the provided configuration file to create an
    // ImageClassifier object.
    public ImageClassifier(String configFile) {
        In in = new In(configFile);

        width = in.readInt();
        height = in.readInt();
        numClasses = in.readInt();

        classNames = new String[numClasses];

        for (int i = 0; i < numClasses; i++) {
            classNames[i] = in.readString();
        }

        m = new MultiPerceptron(numClasses, width * height);
    }

    // Creates a feature vector (1D array) from the given picture.
    public double[] extractFeatures(Picture picture) {
        int w = picture.width();
        int h = picture.height();

        if (this.width != w || this.height != h) {
            throw new IllegalArgumentException("image’s dimensions are not "
                                                       + "equals to dimensions "
                                                       + "provided in the "
                                                       + "configuration file");
        }

        double[] features = new double[width * height];
        int counter = 0;

        for (int i = 0; i < height; i++) { // rows
            for (int j = 0; j < width; j++) {  // cols
                Color c = picture.get(j, i);
                features[counter] = c.getRed();
                counter++;
            }
        }
        return features;
    }

    // Trains the perceptron on the given training data file.
    public void trainClassifier(String trainFile) {
        In in = new In(trainFile);

        while (!in.isEmpty()) {
            String fileName = in.readString();
            Picture picture = new Picture(fileName);
            int classLabel = in.readInt();

            double[] features = extractFeatures(picture);

            m.trainMulti(features, classLabel);

        }

    }

    // Returns the name of the class for the given class label.
    public String classNameOf(int classLabel) {
        if (classLabel < 0 || classLabel >= numClasses) {
            throw new IllegalArgumentException("invalid class label");

        }
        return classNames[classLabel];
    }

    // Returns the predicted class for the given picture.
    public int classifyImage(Picture picture) {
        int w = picture.width();
        int h = picture.height();

        // exception of dimensions don't match
        if (this.width != w || this.height != h) {
            throw new IllegalArgumentException("dimensions don't match");
        }

        // features - input array
        double[] features = extractFeatures(picture);

        return m.predictMulti(features);
    }

    // Returns the error rate on the given testing data file.
    // Also prints the misclassified examples - see specification.
    public double testClassifier(String testFile) {
        double total = 0;
        double incorrect = 0;

        In in = new In(testFile);

        while (!in.isEmpty()) {
            String fileName = in.readString();
            Picture picture = new Picture(fileName);
            int classLabel = in.readInt();
            int classified = classifyImage(picture);

            if (classLabel != classified) {
                incorrect++;
                StdOut.println(fileName + ", label = " +
                                       classNames[classLabel] + ", predict = "
                                       + classNames[classified]);
            }

            total++;
        }
        return incorrect / total;
    }

    // Tests this class using a configuration file, training file and test file.
    // See below.
    public static void main(String[] args) {
        ImageClassifier classifier = new ImageClassifier(args[0]);
        classifier.trainClassifier(args[1]);
        double testErrorRate = classifier.testClassifier(args[2]);
        System.out.println("test error rate = " + testErrorRate);
    }
}

