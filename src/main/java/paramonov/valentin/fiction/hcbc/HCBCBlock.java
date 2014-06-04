package paramonov.valentin.fiction.hcbc;

import paramonov.valentin.fiction.collections.Block;

public class HCBCBlock implements Block {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final double mtcR;
    private final double mtcG;
    private final double mtcB;

    public HCBCBlock(int x, int y, int width, int height, double mtcR, double mtcG, double mtcB) {

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

    public double getMtcR() {
        return mtcR;
    }

    public double getMtcG() {
        return mtcG;
    }

    public double getMtcB() {
        return mtcB;
    }
}
