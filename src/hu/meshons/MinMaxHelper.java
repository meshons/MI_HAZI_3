package hu.meshons;

public class MinMaxHelper {

    private double min = 0, max = 0;
    private double halfDistance = 0;
    private double middle = 0;
    private boolean initialized = false;

    public MinMaxHelper() { }

    public void init(double value) {
        min = value;
        max = value;
        middle = value;
    }

    private void calcMiddleAndHalfDistance() {
        halfDistance = (max - min) / 2;
        middle = min + halfDistance;
    }

    public double input(double value) {
        if (!initialized) {
            initialized = true;
            init(value);
        }
        if (value < min) {
            min = value;
            calcMiddleAndHalfDistance();
        } else if (value > max) {
            max = value;
            calcMiddleAndHalfDistance();
        }
        return value;
    }

    public double normalize(double value) {
        value -= middle;
        value /= halfDistance;
        return value;
    }

    public double denormalize(double value) {
        value *= halfDistance;
        value += middle;
        return value;
    }
}
