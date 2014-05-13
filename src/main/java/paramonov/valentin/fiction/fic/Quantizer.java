package paramonov.valentin.fiction.fic;

public final class Quantizer {
    final int numLevels;
    final int maxValue;

    public Quantizer(int numLevels, int maxValue) {
        this.numLevels = numLevels;
        this.maxValue = maxValue;
    }

    final int quantize(double value) {
        if(value > maxValue) {
            return numLevels;
        }

        value *= numLevels;
        value /= maxValue;

        return (int) Math.ceil(value);
    }

    final double deQuantize(int level) {
        return (double) level / numLevels * maxValue;
    }
}
