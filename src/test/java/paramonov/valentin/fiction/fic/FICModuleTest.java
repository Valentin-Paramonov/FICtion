package paramonov.valentin.fiction.fic;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import paramonov.valentin.fiction.Resources;
import paramonov.valentin.fiction.TestUtils;
import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.image.ImageUtils;
import paramonov.valentin.fiction.image.processor.ImageProcessor;
import paramonov.valentin.fiction.io.BitPacker;
import paramonov.valentin.fiction.io.BitUnPacker;
import paramonov.valentin.fiction.io.IOUtils;
import paramonov.valentin.fiction.transformation.Transformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static paramonov.valentin.fiction.TestUtils.listMatches;

public class FICModuleTest {
    private FICProperties properties;
    private FICModule ficModule;
    private FICTree tree;
    private DomainParams domainParams;
    private TransformationParams transformationParams;

    @Before
    public void setUp() {
        properties = new FICProperties();
        properties.setBrightnessLevels(128);
        properties.setMinBrightnessValue(-128);
        properties.setMaxBrightnessValue(128);
        properties.setContrastLevels(32);
        properties.setMinContrastValue(-1);
        properties.setMaxContrastValue(1);
        properties.setDomainStep(1.);
        properties.setMinSubdivisions(1);
        properties.setMaxSubdivisions(1);
        properties.setTolerance(0.1);
        ficModule = new FICModule(properties);
        tree = new FICTree();
        final RangeBlock rootBlock = new RangeBlock(0, 0, 2, 2);
        final RangeBlock element1 = new RangeBlock(0, 0, 1, 1);
        final RangeBlock element2 = new RangeBlock(1, 0, 1, 1);
        final RangeBlock element3 = new RangeBlock(0, 1, 1, 1);
        final RangeBlock element4 = new RangeBlock(1, 1, 1, 1);

        transformationParams = new TransformationParams(Transformation.REFLECTION_VERTICAL, 13.37, 0.5, 12);
        domainParams = new DomainParams();
        domainParams.setTransformationParams(transformationParams);

        tree.add(rootBlock);
        final List<RangeBlock> blocks = Arrays.asList(element1, element2, element3, element4);
        for(int i = 0; i < 4; i++) {
            final RangeBlock block = blocks.get(i);
            final DomainParams domain = new DomainParams();
            domain.setId(i);
            domain.setTransformationParams(transformationParams);
            block.setMappingDomain(domain);
            tree.add(block);
        }
    }

    @Test
    public void testEncode_ShouldNotFail() {
        final int[] colorArray = {1, 2, 3, 4};
        final Image image = new Image(colorArray, 2, 2);

        ficModule.encode(image);
    }

    @Test
    public void testPackTree_PackedBytes_HaveExpectedValues() throws Exception {
        final String filePath = Resources.TEST_OUT_PATH + "/packed 01.fic";
        final BitPacker bitPacker = new BitPacker();
        final int[] domainIndexBits = {0, 2};

        ficModule.packTree(bitPacker, tree, domainIndexBits, 0, 0);
        final byte[] bytes = bitPacker.seal();
        final List<Integer> integers = TestUtils.toIntegerList(bytes);

        assertThat(integers, listMatches(0x87, 0x11, 0x93, 0x88, 0xD1, 0xC4, 0x6C, 0xE2, 0x30));
    }

    @Test
    public void testUnPackTree_UnpackedTree_HasExpectedStructure() throws Exception {
        final BitUnPacker bitUnPacker = new BitUnPacker(Resources.PACKED_TREE);
        final FICTree tree = new FICTree();
        final int[] domainIndeBits = {0, 2};
        final List<Integer> integers = new ArrayList<>(4);
        FICModule.UnpackerParams.minSubdivisions = 0;
        FICModule.UnpackerParams.maxSubdivisions = 1;
        FICModule.UnpackerParams.imageWidth = 2;
        FICModule.UnpackerParams.imageHeight = 2;

        ficModule.unpackTree(tree, bitUnPacker, domainIndeBits, 0, 0, 2, 2, 0);
        for(FICTree subTree : tree) {
            if(!subTree.hasChildren()) {
                final RangeBlock block = subTree.getElement();
                final DomainParams domain = block.getMappingDomain();
                final int domainId = domain.getId();
                integers.add(domainId);
            }
        }

        assertThat(integers, listMatches(0, 1, 2, 3));
    }

    @Test
    public void testStoreTree_OutputArray_HasExpectedValues() {
        properties.setMinSubdivisions(0);
        properties.setMaxSubdivisions(1);
        domainParams.setId(3);

        final byte[] bytes = ficModule.storeTree(tree);
        List<Integer> integers = TestUtils.toIntegerList(bytes);
        integers = integers.subList(4, 5);

        assertThat(integers, listMatches(0x9C));
    }

    @Test
    public void testComputeDomainIndexBits_Bits_HaveExpectedValues() {
        final int[] bits = ficModule.computeDomainIndexBits(64, 64, 2, 4, 1);
        final List<Integer> bitsList = TestUtils.toIntegerList(bits);

        assertThat(bitsList, listMatches(2, 4, 6));
    }

    @Test
    public void testComputeDomainIndexBits() {
        final int[] bits = ficModule.computeDomainIndexBits(64, 64, 3, 5, 1);
        final List<Integer> bitsList = TestUtils.toIntegerList(bits);

        assertThat(bitsList, listMatches(4, 6, 8));
    }

    @Test
    public void testEncode_TreeSize_Equals85() throws Exception {
        properties.setMinSubdivisions(2);
        properties.setMaxSubdivisions(3);
        final Image image = ImageProcessor.loadImageFromFile(Resources.LENNA_GRAYSCALE);
        final Image downsampledImage = ImageUtils.downsample(image, 16);

        final FICTree tree = ficModule.encode(downsampledImage);
        final int treeSize = tree.size();

        assertThat(treeSize, equalTo(85));
    }

    @Test
    public void testEncode_Decode() throws Exception {
        properties.setMinSubdivisions(3);
        properties.setMaxSubdivisions(5);
        final Image image = ImageProcessor.loadImageFromFile(Resources.LENNA_GRAYSCALE);
        final Image downsampledImage = ImageUtils.downsample(image, 8);
        final String filePath = Resources.TEST_OUT_PATH + "/encoded.fic";

        final FICTree tree = ficModule.encode(downsampledImage);
        final byte[] bytes = ficModule.storeTree(tree);
        IOUtils.writeBytesToFile(bytes, filePath);
        final Image decodedImage = ficModule.decode(filePath, 64, 64, 10, Double.POSITIVE_INFINITY);

        ImageProcessor.writeImageToFile(decodedImage, Resources.TEST_OUT_PATH + "/decoded fic.png");
    }

    @Ignore
    @Test
    public void testEncode() throws Exception {
        properties.setMinSubdivisions(5);
        properties.setMaxSubdivisions(7);
        final Image image = ImageProcessor.loadImageFromFile(Resources.LENNA_GRAYSCALE);

        final long before = System.currentTimeMillis();
        final FICTree encodedTree = ficModule.encode(image);
        final long after = System.currentTimeMillis();
        final long executionTime = after - before;

        final byte[] bytes = ficModule.storeTree(encodedTree);
        IOUtils.writeBytesToFile(bytes, Resources.TEST_OUT_PATH + "/001.fic");

        assertThat(executionTime, equalTo(0l));
    }
}
