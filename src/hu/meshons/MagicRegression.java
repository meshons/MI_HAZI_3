package hu.meshons;

import java.util.Scanner;

public class MagicRegression {
    public static final int SAMPLE_NUMBER = 17011;
    public static final int DIMENSION = 81;
    public static final int TEST_SAMPLE_NUMBER = 4252;
    public static final double LEARN_RATE = 0.0068;

    private static final double ERROR_MAX = 8.5;

    private MinMaxHelper[] inputMinMaxHelpers = new MinMaxHelper[DIMENSION];
    private MinMaxHelper outputMinMaxHelper = new MinMaxHelper();

    private double[][] sampleInputs = new double[SAMPLE_NUMBER][DIMENSION];
    private double[] sampleOutputs = new double[SAMPLE_NUMBER];
    private double[][] testInputs = new double[TEST_SAMPLE_NUMBER][DIMENSION];

    private double[] weights = new double[DIMENSION];

    public MagicRegression() {
        for (int i = 0; i < DIMENSION; ++i) {
            weights[i] = Math.random();
            inputMinMaxHelpers[i] = new MinMaxHelper();
        }
    }

    public void collectTheData() {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < SAMPLE_NUMBER; ++i)
            sampleInputs[i] = interpretInputLine(scanner.nextLine());

        for (int i = 0; i < SAMPLE_NUMBER; ++i)
            sampleOutputs[i] = interpretOutputLine(scanner.nextLine());

        for (int i = 0; i < TEST_SAMPLE_NUMBER; ++i)
            testInputs[i] = interpretInputLine(scanner.nextLine());
    }

    public void normalizeToSaveTheWorldFaster() {
        for (int i = 0; i < SAMPLE_NUMBER; ++i) {
            for (int j = 0; j < DIMENSION; ++j)
                sampleInputs[i][j] = inputMinMaxHelpers[j].normalize(sampleInputs[i][j]);
            sampleOutputs[i] = outputMinMaxHelper.normalize(sampleOutputs[i]);
        }
        for (int i = 0; i < TEST_SAMPLE_NUMBER; ++i)
            for (int j = 0; j < DIMENSION; ++j)
                testInputs[i][j] = inputMinMaxHelpers[j].normalize(testInputs[i][j]);
    }

    public void measureTheData() {
        boolean error = true;

        while (error) {
            error = false;
            for (int i = 0; i < SAMPLE_NUMBER; ++i) {
                double output = Megatron.hitOptimus(weights, sampleInputs[i]);

                double errorOfInput = sampleOutputs[i] - output;

                weights = Megatron.optimizeWeapons(weights, sampleInputs[i], errorOfInput);

                if (isThereGlitchInTheMatrix(errorOfInput))
                    error = true;
            }
        }
    }

    public void fight() {
        for (int i = 0; i < TEST_SAMPLE_NUMBER; ++i)
            System.out.println(
                    outputMinMaxHelper.denormalize(
                            Megatron.hitOptimus(weights, testInputs[i])
                    )
            );
    }

    private double interpretOutputLine(String line) {
        return outputMinMaxHelper.input(Double.parseDouble(line));
    }

    private double[] interpretInputLine(String line) {
        double[] inputValues = new double[DIMENSION];

        String[] values = line.split("\t");

        for (int i = 0; i < values.length; ++i)
            inputValues[i] = inputMinMaxHelpers[i].input(Double.parseDouble(values[i]));

        return inputValues;
    }

    private boolean isThereGlitchInTheMatrix(double error) {
        error *= error;
        error /= SAMPLE_NUMBER;
        error = Math.sqrt(error);
        return error > ERROR_MAX;
    }
}
