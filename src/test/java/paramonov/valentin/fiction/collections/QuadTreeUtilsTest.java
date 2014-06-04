package paramonov.valentin.fiction.collections;

import org.junit.Before;
import org.junit.Test;
import paramonov.valentin.fiction.Resources;
import paramonov.valentin.fiction.fic.FICTree;
import paramonov.valentin.fiction.fic.RangeBlock;
import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.image.processor.ImageProcessor;

import java.io.File;

public class QuadTreeUtilsTest {
    private static final File TEST_OUTPUT_DIR = new File(Resources.TEST_OUT_PATH);

    @Before
    public void setUp() throws Exception {
        if(!TEST_OUTPUT_DIR.exists()) {
            TEST_OUTPUT_DIR.mkdirs();
        }
    }

    @Test
    public void testVisualize() throws Exception {
        final FICTree tree = new FICTree();
        tree.add(new RangeBlock(0, 0, 16, 16));
        tree.add(new RangeBlock(4, 8, 4, 4));

        final Image visualization = QuadTreeUtils.visualize(tree);

        ImageProcessor.writeImageToFile(visualization, Resources.TEST_OUT_PATH + "/tree visualization 01.png");
    }
}
