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

    final int quantize(double value) {
        if(value < minValue) {
            return 0;
        }

        if(value >= maxValue) {
            return numLevels - 1;
        }

        return (int) Math.floor((value - minValue) / step);
    }

    final double deQuantize(int level) {
        return level * step + step / 2 + minValue;
    }

    public int getStorageBits() {
        return storageBits;
    }
}
