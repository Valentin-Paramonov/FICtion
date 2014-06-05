package paramonov.valentin.fiction.fic;

public final class Quantizer {
    private final int numLevels;
    private final int minValue;
    private final int maxValue;
    private final int storageBits;
    private final double step;

    public Quantizer(int numLevels, int minValue, int maxValue) {
        this.numLevels = numLevels;
        this.minValue = minValue;
        this.maxValue = maxValue;
        storageBits = (int) (Math.log(numLevels) / Math.log(2));
        step = (double) (maxValue - minValue) / numLevels;
    }

    public final int quantize(double value) {
        if (value < minValue) {
            return 0;
        }

        if (value >= maxValue) {
            return numLevels - 1;
        }

        int level = (int) Math.floor((value - minValue) / step + 0.5);

        return level < numLevels ? level : numLevels - 1;
    }

    public final double deQuantize(int level) {
        return (level) * step + minValue / step;
    }

    public int getStorageBits() {
        return storageBits;
    }
}
