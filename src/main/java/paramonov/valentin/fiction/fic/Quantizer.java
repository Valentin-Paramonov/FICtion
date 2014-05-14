package paramonov.valentin.fiction.fic;

public final class Quantizer {
    final int numLevels;
    final int minValue;
    final int maxValue;
    final double step;

    public Quantizer(int numLevels, int minValue, int maxValue) {
        this.numLevels = numLevels;
        this.minValue = minValue;
        this.maxValue = maxValue;
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
}
