package paramonov.valentin.fiction.hcbc;

import org.junit.Before;
import org.junit.Test;
import paramonov.valentin.fiction.collections.Pair;
import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.image.processor.ImageProcessor;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class HCBCModuleTest {
    private static final String RESOURCE_PATH = "src/test/resources/";
    private static final String TEST_IMG = RESOURCE_PATH + "test image.png";
    private static final String TEST_OUTPUT_PATH = "target/test/out/";
    private static final File TEST_OUTPUT_DIR = new File(TEST_OUTPUT_PATH);

    private HCBCModule hcbc;

    @Before
    public void setUp() {
        if(!TEST_OUTPUT_DIR.exists()) {
            TEST_OUTPUT_DIR.mkdirs();
        }
        hcbc = new HCBCModule();
    }

    @Test
    public void testCalculateTC_ArrayHasExpectedDims() throws Exception {
        Image img = ImageProcessor.loadImageFromFile(TEST_IMG);

        double[][][] tc = hcbc.calculateTC(img, true);

        int tcSize = tc.length * tc[0].length;

        assertThat(tcSize, equalTo(25));
    }

    @Test
    public void testCalculateTC_TCForWhitePixel() throws Exception {
        Image img = ImageProcessor.loadImageFromFile(TEST_IMG);

        double[][][] tc = hcbc.calculateTC(img, true);

        double tcB = tc[0][0][1];

        assertThat(tcB, equalTo(1 / 3.));
    }

    @Test
    public void testCalculateTC_TCForBlackPixel() throws Exception {
        Image img = ImageProcessor.loadImageFromFile(TEST_IMG);

        double[][][] tc = hcbc.calculateTC(img);

        double tcB = tc[0][1][1];

        assertThat(tcB, equalTo(0.));
    }

    @Test
    public void testCalculateTC_ProducesGrayLevelImage() throws Exception {
        Image colorImage = ImageProcessor.loadImageFromFile(RESOURCE_PATH + "lenna.png");

        hcbc.calculateTC(colorImage, true);

        ImageProcessor.writeImageToFile(colorImage, TEST_OUTPUT_PATH + "gray.png");
    }

    @Test
    public void testQTPartitionTest() throws Exception {

    }

    @Test
    public void testHCBCEncode() throws Exception {
        Image testImg = ImageProcessor.loadImageFromFile(RESOURCE_PATH + "lenna.png");

        Pair<HCBCTree, Image> encodeData = hcbc.hcbcEncode(testImg, 10e-5, 8);

        Image encodedImage = encodeData.getSnd();

        ImageProcessor.writeImageToFile(encodedImage, TEST_OUTPUT_PATH + "encoded.png");
    }

    @Test
    public void testHCBCDecode() throws Exception {
        Image testImg = ImageProcessor.loadImageFromFile(RESOURCE_PATH + "lenna.png");

        Pair<HCBCTree, Image> encodeData = hcbc.hcbcEncode(testImg, 10e-4, 8);

        Image decodedImage = hcbc.hcbcDecode(encodeData.getFst(), encodeData.getSnd());

        ImageProcessor.writeImageToFile(decodedImage, TEST_OUTPUT_PATH + "decoded.png");
    }
}
