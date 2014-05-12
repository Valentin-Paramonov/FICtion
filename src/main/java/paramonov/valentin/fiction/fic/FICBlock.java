package paramonov.valentin.fiction.fic;

public class FICBlock {
    private final int x;
    private final int y;
    private final int w;
    private final int h;

    private RangeBlock rangeBlock;

    public FICBlock(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public RangeBlock getRangeBlock() {
        return rangeBlock;
    }

    public void setRangeBlock(RangeBlock rangeBlock) {
        this.rangeBlock = rangeBlock;
    }
}
