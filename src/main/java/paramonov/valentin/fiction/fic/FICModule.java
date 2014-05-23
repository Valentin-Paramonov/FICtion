package paramonov.valentin.fiction.fic;

import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.image.ImageUtils;
import paramonov.valentin.fiction.io.BitPacker;
import paramonov.valentin.fiction.io.BitUnPacker;
import paramonov.valentin.fiction.transformation.Transformation;

import java.io.EOFException;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

public class FICModule {
    private final FICProperties properties;
    private Quantizer brightnessQuantizer;
    private Quantizer contrastQuantizer;

    private static final class UnpackerParams {
        static int imageWidth;
        static int imageHeight;
        static int minSubdivisions;
        static int maxSubdivisions;
    }

    public FICModule(FICProperties properties) {
        this.properties = properties;
        init();
    }

    private void init() {
        final int brightnessLevels = properties.getBrightnessLevels();
        final int minBrightnessValue = properties.getMinBrightnessValue();
        final int maxBrightnessValue = properties.getMaxBrightnessValue();
        final int contrastLevels = properties.getContrastLevels();
        final int minContrastValue = properties.getMinContrastValue();
        final int maxContrastValue = properties.getMaxContrastValue();
        brightnessQuantizer = new Quantizer(brightnessLevels, minBrightnessValue, maxBrightnessValue);
        contrastQuantizer = new Quantizer(contrastLevels, minContrastValue, maxContrastValue);
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

    public byte[] storeTree(FICTree tree) {
        final RangeBlock element = tree.getElement();
        final int imageWidth = element.getWidth();
        final int imageHeight = element.getHeight();
        final int minSubdivisions = properties.getMinSubdivisions();
        final int maxSubdivisions = properties.getMaxSubdivisions();
        final double domainStep = properties.getDomainStep();
        final int[] domainIndexBits =
            computeDomainIndexBits(imageWidth, imageHeight, minSubdivisions, maxSubdivisions, domainStep);
        final BitPacker bitPacker = new BitPacker();
        bitPacker.pack(imageWidth, 12);
        bitPacker.pack(imageHeight, 12);
        bitPacker.pack(minSubdivisions, 4);
        bitPacker.pack(maxSubdivisions, 4);
        packTree(bitPacker, tree, domainIndexBits, minSubdivisions, 0);

        return bitPacker.seal();
    }

    private int[] computeDomainIndexBits(int imageWidth, int imageHeight, int minSubdivisions, int maxSubdivisions,
        double domainStep) {
        final int subdivisionRangeSize = maxSubdivisions - minSubdivisions;
        final int[] domainIndexBits = new int[subdivisionRangeSize];
        for(int i = 0; i < subdivisionRangeSize; i++) {
            domainIndexBits[i] = (int) (imageWidth / Math.pow(2, minSubdivisions + i) * domainStep);
        }
        return domainIndexBits;
    }

    void packTree(BitPacker bitpacker, FICTree tree, int[] domainIndexBits, int minSubdivisions, int subdivision) {
        if(tree.hasChildren()) {
            bitpacker.pack(1, 1);
            subdivision++;
            for(FICTree subTree : tree.getChildren()) {
                packTree(bitpacker, subTree, domainIndexBits, minSubdivisions, subdivision);
            }
            return;
        }

        final RangeBlock element = tree.getElement();
        final DomainParams domain = element.getMappingDomain();
        final int domainId = domain.getId();
        final TransformationParams transformationParams = domain.getTransformationParams();
        final Transformation transformation = transformationParams.getTransformation();
        final double contrastShift = transformationParams.getContrastShift();
        final double brightnessShift = transformationParams.getBrightnessShift();
        final int maxSubdivisions = properties.getMaxSubdivisions();
        if(subdivision != maxSubdivisions) {
            bitpacker.pack(0, 1);
        }
        final int bitsNeededForDomainIndex = domainIndexBits[subdivision - minSubdivisions - 1];
        final int quantizedContrast = contrastQuantizer.quantize(contrastShift);
        final int quantizedBrightness = brightnessQuantizer.quantize(brightnessShift);
        final int contrastStorageBits = contrastQuantizer.getStorageBits();
        final int brightnessStorageBits = brightnessQuantizer.getStorageBits();
        bitpacker.pack(domainId, bitsNeededForDomainIndex);
        bitpacker.pack(transformation.ordinal(), 3);
        bitpacker.pack(quantizedContrast, contrastStorageBits);
        bitpacker.pack(quantizedBrightness, brightnessStorageBits);
    }

    public Image decode(String filePath, int imageWidth, int imageHeight, int iterations) {
        final FICTree tree = new FICTree();
        try(final BitUnPacker unpacker = new BitUnPacker(filePath)) {
            readHeader(unpacker);
            final int[] domainIndexBits = computeDomainIndexBits(UnpackerParams.imageWidth, UnpackerParams.imageHeight,
                UnpackerParams.minSubdivisions, UnpackerParams.maxSubdivisions, 1.);
            unpackTree(tree, unpacker, domainIndexBits, 0, 0, imageWidth, imageHeight, 0);
        } catch(EOFException eofe) {
            throw new RuntimeException(eofe);
        } catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }

        return decodeTree(tree, imageWidth, imageHeight, iterations, 0);
    }

    private Image decodeTree(FICTree tree, int imageWidth, int imageHeight, int iterations, double tolerance) {
        final Image image = new Image(imageWidth, imageHeight);
        Image prevImage;
        int iteration = 0;

        while(iteration < iterations) {
            prevImage = image.copy();
            decodeOnce(tree, image);
            final double psnr = ImageUtils.psnr(image, prevImage);
            if(psnr < tolerance) {
                break;
            }
            iteration++;
        }

        return image;
    }

    private void decodeOnce(FICTree tree, Image image) {
        for(FICTree subTree : tree) {
            if(subTree.hasChildren()) {
                continue;
            }
            final int imageWidth = image.getWidth();
            final RangeBlock block = tree.getElement();
            final DomainParams domain = block.getMappingDomain();
            final TransformationParams transformationParams = domain.getTransformationParams();
            final Transformation transformation = transformationParams.getTransformation();
            final double contrastShift = transformationParams.getContrastShift();
            final double brightnessShift = transformationParams.getBrightnessShift();
            final int blockWidth = block.getWidth();
            final int blockHeight = block.getHeight();
            final int blockX = block.getX();
            final int blockY = block.getY();
            final int domainId = domain.getId();
            final int domainWidth = blockWidth * 2;
            final int domainHeight = blockHeight * 2;
            final int domainsHorizontally = imageWidth / domainWidth;
            final int domainX = domainId % domainsHorizontally * domainWidth;
            final int domainY = domainId / domainsHorizontally * domainHeight;
            final Image domainImage = image.subImage(domainX, domainY, domainWidth, domainHeight);

            ImageUtils.downsample(domainImage, 2);
            domainImage.shiftColors(contrastShift, brightnessShift);
            final Image transformedDomain = ImageUtils.transform(domainImage, transformation);
            image.replaceArea(blockX, blockY, transformedDomain);
        }
    }

    private void unpackTree(FICTree tree, BitUnPacker unpacker, int[] domainIndexBits, int x, int y, int width,
        int height, int subdivision) throws IOException {

        final RangeBlock block = new RangeBlock(x, y, width, height);
        tree.add(block);
        final int furtherRecursion = unpacker.read(1);

        if(subdivision != UnpackerParams.maxSubdivisions && furtherRecursion == 1) {
            subdivision++;
            final int block1W = (width + 1) / 2;
            final int block1H = (height + 1) / 2;
            final int block4W = width / 2;
            final int block4H = height / 2;
            unpackTree(tree, unpacker, domainIndexBits, 0, 0, block1W, block1H, subdivision);
            unpackTree(tree, unpacker, domainIndexBits, block1W, 0, block4W, block1H, subdivision);
            unpackTree(tree, unpacker, domainIndexBits, 0, block1H, block1W, block4H, subdivision);
            unpackTree(tree, unpacker, domainIndexBits, block1W, block1H, block4W, block4H, subdivision);
            return;
        }

        final int domainIndexSize = domainIndexBits[subdivision - UnpackerParams.minSubdivisions];
        final int domainIndex = unpacker.read(domainIndexSize);
        final int transformation = unpacker.read(3);
        final int contrastStorageBits = contrastQuantizer.getStorageBits();
        final int brightnessStorageBits = brightnessQuantizer.getStorageBits();
        final int quantizedContrastShift = unpacker.read(contrastStorageBits);
        final int quantizedBrightnessShift = unpacker.read(brightnessStorageBits);
        final double contrastShift = contrastQuantizer.deQuantize(quantizedContrastShift);
        final double brightnessShift = brightnessQuantizer.deQuantize(quantizedBrightnessShift);
        final DomainParams domain = new DomainParams();
        final TransformationParams transformationParams =
            new TransformationParams(Transformation.values()[transformation], 0, contrastShift, brightnessShift);
        domain.setId(domainIndex);
        domain.setTransformationParams(transformationParams);
    }

    private void readHeader(BitUnPacker unpacker) throws IOException {
        UnpackerParams.imageWidth = unpacker.read(12);
        UnpackerParams.imageHeight = unpacker.read(12);
        UnpackerParams.minSubdivisions = unpacker.read(4);
        UnpackerParams.maxSubdivisions = unpacker.read(4);
    }
}
