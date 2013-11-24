package paramonov.valentin.fiction.hcbc;

import java.util.concurrent.RecursiveAction;

class HCBCPartition extends RecursiveAction {
    private final HCBCTree tree;
    private double[][][] tc;
    private double tolerance;
    private int maxLevel, currentLevel, startX, startY, w, h;

    HCBCPartition(HCBCTree tree, double[][][] tc, double tolerance, int maxLevel, int currentLevel, int startX,
        int startY, int w, int h) {

        this.tree = tree;
        this.tc = tc;
        this.tolerance = tolerance;
        this.maxLevel = maxLevel;
        this.currentLevel = currentLevel;
        this.startX = startX;
        this.startY = startY;
        this.w = w;
        this.h = h;
    }

    @Override
    protected void compute() {
        double[] meanTC = calculateMeanTC();
        double[] variance = calculateVariance(meanTC);

        synchronized(tree) {
            tree.add(new HCBCBlock(startX, startY, w, h, meanTC[0], meanTC[1], meanTC[2]));
        }

        currentLevel++;
        if(currentLevel == maxLevel) return;

        if(variance[0] <= tolerance && variance[1] <= tolerance && variance[2] <= tolerance) return;

        int block1W = (w + 1) / 2;
        int block1H = (h + 1) / 2;
        int block4W = w / 2;
        int block4H = h / 2;

        invokeAll(
            new HCBCPartition(tree, tc, tolerance, maxLevel, currentLevel,
                startX, startY, block1W, block1H),
            new HCBCPartition(tree, tc, tolerance, maxLevel, currentLevel,
                startX + block1W, startY, block4W, block1H),
            new HCBCPartition(tree, tc, tolerance, maxLevel, currentLevel,
                startX, startY + block1H, block1W, block4H),
            new HCBCPartition(tree, tc, tolerance, maxLevel, currentLevel,
                startX + block1W, startY + block1H, block4W, block4H));
    }

    protected double[] calculateMeanTC() {
        double[] meanTC = new double[3];
        int endX = startX + w;
        int endY = startY + h;

        for(int x = startX; x < endX; x++) {
            for(int y = startY; y < endY; y++) {
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

    protected double[] calculateVariance(double[] meanTC) {
        double[] variance = new double[3];

        int endX = startX + w;
        int endY = startY + h;

        for(int x = startX; x < endX; x++) {
            for(int y = startY; y < endY; y++) {
                double diffR = tc[x][y][0] - meanTC[0];
                double diffG = tc[x][y][1] - meanTC[1];
                double diffB = tc[x][y][2] - meanTC[2];

                variance[0] += diffR * diffR;
                variance[1] += diffG * diffG;
                variance[2] += diffB * diffB;
            }
        }

        int blockSize = w * h;

        variance[0] /= blockSize;
        variance[1] /= blockSize;
        variance[2] /= blockSize;

        return variance;
    }
}
