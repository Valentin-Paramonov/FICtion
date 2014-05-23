package paramonov.valentin.fiction.fic;

import org.junit.Before;
import org.junit.Test;
import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.transformation.Transformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static paramonov.valentin.fiction.ListMatcher.listMatches;

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
        final RangeBlock element2 = new RangeBlock(0, 1, 1, 1);
        final RangeBlock element3 = new RangeBlock(1, 0, 1, 1);
        final RangeBlock element4 = new RangeBlock(1, 1, 1, 1);

        transformationParams = new TransformationParams(Transformation.REFLECTION_VERTICAL, 13.37, 0.5, 12);
        domainParams = new DomainParams();
        domainParams.setTransformationParams(transformationParams);

        tree.add(rootBlock);
        final List<RangeBlock> blocks = Arrays.asList(element1, element2, element3, element4);
        for(RangeBlock block : blocks) {
            block.setMappingDomain(domainParams);
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
    public void testStoreTree_OutputArray_HasExpectedValues() {
        properties.setMinSubdivisions(0);
        properties.setMaxSubdivisions(1);
        domainParams.setId(3);

        final byte[] bytes = ficModule.storeTree(tree);
        List<Integer> integers = new ArrayList<>(bytes.length);
        for(byte b : bytes) {
            integers.add(0xff & b);
        }
        integers = integers.subList(4,5);

        assertThat(integers, listMatches(0xE7));
    }
}
