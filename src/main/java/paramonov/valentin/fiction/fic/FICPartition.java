package paramonov.valentin.fiction.fic;

import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.image.processor.ImageUtils;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class FICPartition extends RecursiveAction {
    private final FICTree tree;
    private final Image image;
    private final int minSubdivisions;
    private final int maxSubdivisions;
    private final double tolerance;
    private final int domainStep;
    private final int startX;
    private final int startY;
    private final int w;
    private final int h;

    private int currentSubdivision;

    public FICPartition(FICTree tree, Image image, int minSubdivisions, int maxSubdivisions, double tolerance,
        int domainStep, int startX, int startY, int w, int h, int currentSubdivision) {
        this.tree = tree;
        this.image = image;
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
            final DomainParams domainParams = computeCoefficients();
        }

        final int block1Width = (w + 1) / 2;
        final int block1Height = (h + 1) / 2;
        final int block4Width = w / 2;
        final int block4Height = h / 2;

        final FICPartition partition1 =
            new FICPartition(tree, image, minSubdivisions, maxSubdivisions, tolerance, domainStep, startX, startY,
                block1Width, block1Height, currentSubdivision);
        final FICPartition partition2 =
            new FICPartition(tree, image, minSubdivisions, maxSubdivisions, tolerance, domainStep, startX + block4Width,
                startY, block4Width, block1Height, currentSubdivision);
        final FICPartition partition3 =
            new FICPartition(tree, image, minSubdivisions, maxSubdivisions, tolerance, domainStep, startX + block1Width,
                startY + block1Height, block4Width, block4Height, currentSubdivision);
        final FICPartition partition4 =
            new FICPartition(tree, image, minSubdivisions, maxSubdivisions, tolerance, domainStep, startX,
                startY + block1Height, block1Width, block4Height, currentSubdivision);

        invokeAll(partition1, partition2, partition3, partition4);
    }

    private DomainParams computeCoefficients() {
        final Image range = image.subImage(startX, startY, w, h);
        double minRms = Double.MAX_VALUE;
        final DomainParams bestDomain = new DomainParams();
        bestDomain.setWidth(w * 2);

        for(int y = startY; y < h; y += domainStep) {
            for(int x = startX; x < w; x += domainStep) {
                final Image domain = image.subImage(x, y, w * 2, h * 2);
                final Image downsampledDomain = ImageUtils.downsample(domain, 2);
                final List<TransformationParams> parameters = FICUtils.computeDifferencesRms(range, downsampledDomain);
                final TransformationParams bestTransformationParameters = findMinimalRms(parameters);
                final double rms = bestTransformationParameters.getRms();
                if(rms >= minRms) {
                    continue;
                }
                minRms = rms;
                bestDomain.setTransformationParams(bestTransformationParameters);
                bestDomain.setX(x);
                bestDomain.setY(y);
            }
        }

        return bestDomain;
    }

    private TransformationParams findMinimalRms(List<TransformationParams> parameters) {
        final Iterator<TransformationParams> paramIterator = parameters.iterator();
        TransformationParams bestParams = paramIterator.next();

        while(paramIterator.hasNext()) {
            final TransformationParams params = paramIterator.next();
            final double rms = params.getRms();
            if(rms < bestParams.getRms()) {
                bestParams = params;
            }
        }

        return bestParams;
    }
}
