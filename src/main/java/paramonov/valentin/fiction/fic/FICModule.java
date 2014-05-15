package paramonov.valentin.fiction.fic;

import paramonov.valentin.fiction.image.Image;

import java.util.concurrent.ForkJoinPool;

public class FICModule {
    private final FICProperties properties;

    public FICModule(FICProperties properties) {
        this.properties = properties;
    }

    public FICTree encode(Image image) {
        final int imageWidth = image.getWidth();
        final int imageHeight = image.getHeight();
        final FICTree tree = new FICTree();
        final ForkJoinPool forkJoinPool = new ForkJoinPool();

        final int minSubdivisions = properties.getMinSubdivisions();
        final int maxSubdivisions = properties.getMaxSubdivisions();
        final double tolerance = properties.getTolerance();
        final double domainStep = properties.getDomainStep();
        final FICPartition partitionTask =
            new FICPartition(tree, image, minSubdivisions, maxSubdivisions, tolerance, domainStep, 0, 0, imageWidth,
                imageHeight, 0);
        forkJoinPool.invoke(partitionTask);

        return tree;
    }
}
