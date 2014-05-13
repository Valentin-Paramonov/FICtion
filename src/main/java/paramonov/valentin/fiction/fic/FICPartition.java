package paramonov.valentin.fiction.fic;

import java.util.concurrent.RecursiveAction;

public class FICPartition extends RecursiveAction {
    private final FICTree tree;
    private final int minSubdivisions;
    private final int maxSubdivisions;
    private final double tolerance;
    private final int domainStep;
    private final int startX;
    private final int startY;
    private final int w;
    private final int h;

    private int currentSubdivision;

    public FICPartition(FICTree tree, int minSubdivisions, int maxSubdivisions, double tolerance, int domainStep,
        int startX, int startY, int w, int h, int currentSubdivision) {
        this.tree = tree;
        this.minSubdivisions = minSubdivisions;
        this.maxSubdivisions = maxSubdivisions;
        this.tolerance = tolerance;
        this.domainStep = domainStep;
        this.startX = startX;
        this.startY = startY;
        this.w = w;
        this.h = h;
        this.currentSubdivision = currentSubdivision;
    }

    @Override
    protected void compute() {
        currentSubdivision++;

        if(currentSubdivision >= minSubdivisions) {
            computeCoefficients();
        }

        final int block1Width = (w + 1) / 2;
        final int block1Height = (h + 1) / 2;
        final int block4Width = w / 2;
        final int block4Height = h / 2;

        final FICPartition partition1 =
            new FICPartition(tree, minSubdivisions, maxSubdivisions, tolerance, domainStep, startX, startY, block1Width,
                block1Height, currentSubdivision);
        final FICPartition partition2 =
            new FICPartition(tree, minSubdivisions, maxSubdivisions, tolerance, domainStep, startX + block4Width,
                startY, block4Width, block1Height, currentSubdivision);
        final FICPartition partition3 =
            new FICPartition(tree, minSubdivisions, maxSubdivisions, tolerance, domainStep, startX + block1Width,
                startY + block1Height, block4Width, block4Height, currentSubdivision);
        final FICPartition partition4 =
            new FICPartition(tree, minSubdivisions, maxSubdivisions, tolerance, domainStep, startX,
                startY + block1Height, block1Width, block4Height, currentSubdivision);

        invokeAll(partition1, partition2, partition3, partition4);
    }

    private void computeCoefficients() {

    }
}
