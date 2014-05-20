package paramonov.valentin.fiction.fic;

import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.io.BitPacker;
import paramonov.valentin.fiction.transformation.Transformation;

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

        final FICPartition partitionTask = new FICPartition(tree, image, properties, 0, 0, imageWidth, imageHeight, 0);
        forkJoinPool.invoke(partitionTask);

        return tree;
    }

    private void packTree(BitPacker bitpacker, FICTree tree, int imageWidth, int subdivision) {
        if(tree.hasChildren()) {
            bitpacker.pack(1, 1);
            subdivision++;
            for(FICTree subTree : tree.getChildren()) {
                packTree(bitpacker, subTree, imageWidth, subdivision);
            }
        }

        final RangeBlock element = tree.getElement();
        final DomainParams domain = element.getMappingDomain();
        final int domainId = domain.getId();
        final TransformationParams transformationParams = domain.getTransformationParams();
        final Transformation transformation = transformationParams.getTransformation();
        final int maxSubdivisions = properties.getMaxSubdivisions();
        final double domainStep = properties.getDomainStep();
        if(subdivision != maxSubdivisions) {
            bitpacker.pack(0, 1);
        }
        final int bitsNeededForDomainIndex = (int) (imageWidth / Math.pow(2, subdivision - 1) * domainStep);
        bitpacker.pack(domainId, bitsNeededForDomainIndex);
        bitpacker.pack(transformation.ordinal(), 3);
    }
}
