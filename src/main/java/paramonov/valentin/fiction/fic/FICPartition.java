package paramonov.valentin.fiction.fic;

import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.image.ImageUtils;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.RecursiveAction;

class FICPartition extends RecursiveAction {
    private final FICTree tree;
    private final Image image;
    private final FICProperties properties;
    private final int startX;
    private final int startY;
    private final int w;
    private final int h;

    private int currentSubdivision;

    public FICPartition(FICTree tree, Image image, FICProperties properties, int startX, int startY, int w, int h,
        int currentSubdivision) {

        this.tree = tree;
        this.image = image;
        this.properties = properties;
        this.startX = startX;
        this.startY = startY;
        this.w = w;
        this.h = h;
        this.currentSubdivision = currentSubdivision;
    }

    @Override
    protected void compute() {
        final RangeBlock rangeBlock = new RangeBlock(startX, startY, w, h);
        synchronized(tree) {
            tree.add(rangeBlock);
        }

        final int minSubdivisions = properties.getMinSubdivisions();
        final int maxSubdivisions = properties.getMaxSubdivisions();
        final double tolerance = properties.getTolerance();

        if(currentSubdivision >= minSubdivisions) {
            final DomainParams domainParams = computeCoefficients();
            final TransformationParams transformationParams = domainParams.getTransformationParams();
            final double rms = transformationParams.getRms();
            if(rms <= tolerance || currentSubdivision == maxSubdivisions) {
                rangeBlock.setMappingDomain(domainParams);
                return;
            }
        }

        currentSubdivision++;
        final int block1Width = (w + 1) / 2;
        final int block1Height = (h + 1) / 2;
        final int block4Width = w / 2;
        final int block4Height = h / 2;

        final FICPartition partition1 =
            new FICPartition(tree, image, properties, startX, startY, block1Width, block1Height, currentSubdivision);
        final FICPartition partition2 =
            new FICPartition(tree, image, properties, startX + block4Width, startY, block4Width, block1Height,
                currentSubdivision);
        final FICPartition partition3 =
            new FICPartition(tree, image, properties, startX + block1Width, startY + block1Height, block4Width,
                block4Height, currentSubdivision);
        final FICPartition partition4 =
            new FICPartition(tree, image, properties, startX, startY + block1Height, block1Width, block4Height,
                currentSubdivision);

        invokeAll(partition1, partition2, partition3, partition4);
    }

    DomainParams computeCoefficients() {
        final Image range = image.subImage(startX, startY, w, h);
        double minRms = Double.MAX_VALUE;
        final DomainParams bestDomain = new DomainParams();
        final int domainWidth = w * 2;
        final int domainHeight = h * 2;
        final double domainStep = properties.getDomainStep();
        final int imageWidth = image.getWidth();
        final int imageHeight = image.getHeight();

        final int horizontalSpacing = (int) Math.round(domainStep * domainWidth);
        final int verticalSpacing = (int) Math.round(domainStep * domainHeight);
        for(int y = 0; y < imageHeight; y += verticalSpacing) {
            for(int x = 0; x < imageWidth; x += horizontalSpacing) {
                final Image domain = image.subImage(x, y, domainWidth, domainHeight);
                final Image downsampledDomain = ImageUtils.downsample(domain, 2);
                final List<TransformationParams> parameters = FICUtils.computeDifferencesRms(range, downsampledDomain);
                final TransformationParams bestTransformationParameters = findMinimalRms(parameters);
                final double rms = bestTransformationParameters.getRms();
                if(rms >= minRms) {
                    continue;
                }
                minRms = rms;
                final int domainIndex =
                    (y / verticalSpacing) * (imageWidth / horizontalSpacing) + x / horizontalSpacing;
                bestDomain.setId(domainIndex);
                bestDomain.setTransformationParams(bestTransformationParameters);
            }
        }

        return bestDomain;
    }

    TransformationParams findMinimalRms(List<TransformationParams> parameters) {
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
