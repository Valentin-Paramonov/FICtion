package paramonov.valentin.fiction.collections.hcbc;

public class HCBCBlock {
    private int x;
    private int y;
    private int width;
    private int height;
    private int mtcR;
    private int mtcG;
    private int mtcB;

    public HCBCBlock(
        int x, int y, int width, int height,
        int mtcR, int mtcG, int mtcB) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mtcR = mtcR;
        this.mtcG = mtcG;
        this.mtcB = mtcB;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMtcR() {
        return mtcR;
    }

    public int getMtcG() {
        return mtcG;
    }

    public int getMtcB() {
        return mtcB;
    }
}
