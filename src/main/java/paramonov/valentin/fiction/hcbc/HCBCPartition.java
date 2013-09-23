package paramonov.valentin.fiction.hcbc;

import java.util.concurrent.RecursiveAction;

class HCBCPartition extends RecursiveAction {
    private HCBCTree tree;
    private double[][][] tc;
    private double tolerance;
    private int maxLevel, startX, startY, w, h;

    HCBCPartition(
        HCBCTree tree, double[][][] tc,
        double tolerance, int maxLevel,
        int startX, int startY, int w, int h) {

        this.tree = tree;
        this.tc = tc;
        this.tolerance = tolerance;
        this.maxLevel = maxLevel;
        this.startX = startX;
        this.startY = startY;
        this.w = w;
        this.h = h;
    }

    @Override
    protected void compute() {
        calculateMeanTC();
        calculateVariance();
    }

    protected double[] calculateMeanTC() {
        double[] meanTC = new double[3];
        int endX = startX + w;
        int endY = startY + h;

        for(int y = startY; y < endY; y++) {
            for(int x = startX; x < endX; x++) {
                meanTC[0] += tc[x][y][0];
                meanTC[1] += tc[x][y][1];
                meanTC[2] += tc[x][y][2];
            }
        }

        int blockSize = w * h;

        meanTC[0] /= blockSize;
        meanTC[1] /= blockSize;
        meanTC[2] /= blockSize;

        return meanTC;
    }

    protected void calculateVariance() {}
}
