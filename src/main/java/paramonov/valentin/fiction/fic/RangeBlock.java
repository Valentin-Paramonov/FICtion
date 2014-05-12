package paramonov.valentin.fiction.fic;

public class RangeBlock {
    private final float contrast;
    private final int brightness;
    private final int domainX;
    private final int domainY;
    private final int domainW;
    private final byte transformation;

    public RangeBlock(float contrast, int brightness, int domainX, int domainY, int domainW, byte transformation) {
        this.contrast = contrast;
        this.brightness = brightness;
        this.domainX = domainX;
        this.domainY = domainY;
        this.domainW = domainW;
        this.transformation = transformation;
    }

    public float getContrast() {
        return contrast;
    }

    public int getBrightness() {
        return brightness;
    }

    public int getDomainX() {
        return domainX;
    }

    public int getDomainY() {
        return domainY;
    }

    public int getDomainW() {
        return domainW;
    }

    public byte getTransformation() {
        return transformation;
    }
}
