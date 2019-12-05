package hu.meshons;

public class Megatron {

    public static double[] optimizeWeapons(double[] weights, double[] input, double error) {
        double[] optimizedWeights = new double[MagicRegression.DIMENSION];

        for (int i = 0; i < MagicRegression.DIMENSION; ++i) {
            optimizedWeights[i] = MagicRegression.LEARN_RATE * error * input[i] + weights[i];
        }

        return optimizedWeights;
    }


    public static double hitOptimus(double[] weights, double[] input) {
        double hitOutput = 0;

        for (int i = 0; i < MagicRegression.DIMENSION; ++i)
            hitOutput += weights[i] * input[i];

        return hitOutput;
    }
}
