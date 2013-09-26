package paramonov.valentin.fiction.hcbc;

public class HCBCBlock {
    private int x;
    private int y;
    private int width;
    private int height;
    private double mtcR;
    private double mtcG;
    private double mtcB;

    public HCBCBlock(
        int x, int y, int width, int height,
        double mtcR, double mtcG, double mtcB) {

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
