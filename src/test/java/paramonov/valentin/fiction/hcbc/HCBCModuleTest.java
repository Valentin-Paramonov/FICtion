package paramonov.valentin.fiction.hcbc;

import org.junit.Before;
import org.junit.Test;
import paramonov.valentin.fiction.collections.Pair;
import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.image.processor.ImageProcessor;
import paramonov.valentin.fiction.image.processor.ImageProcessorProvider;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class HCBCModuleTest {
    private ImageProcessor processor;
    private static final String RESOURCE_PATH = "src/test/resources/";
    private static final String TEST_IMG = RESOURCE_PATH + "test image.png";
    private HCBCModule hcbc;

    @Before
    public void setUp() {
        processor = ImageProcessorProvider.getImageProcessor();
        hcbc = new HCBCModule();
    }

    @Test
    public void testCalculateTC_ArrayHasExpectedDims() throws Exception {
        Image img =
            processor.loadImageFromFile(TEST_IMG);

        double[][][] tc = hcbc.calculateTC(img, true);

        int tcSize = tc.length * tc[0].length;

        assertThat(tcSize, equalTo(25));
    }

    @Test
    public void testCalculateTC_TCForWhitePixel() throws Exception {
        Image img =
            processor.loadImageFromFile(TEST_IMG);

        double[][][] tc = hcbc.calculateTC(img, true);

        double tcB = tc[0][0][1];

        assertThat(tcB, equalTo(1 / 3.));
    }

    @Test
    public void testCalculateTC_TCForBlackPixel() throws Exception {
        Image img =
            processor.loadImageFromFile(TEST_IMG);

        double[][][] tc = hcbc.calculateTC(img);

        double tcB = tc[0][1][1];

        assertThat(tcB, equalTo(0.));
    }

    @Test
    public void testCalculateTC_ProducesGrayLevelImage() throws Exception {
        Image colorImage = processor.loadImageFromFile(RESOURCE_PATH + "lenna.png");

        hcbc.calculateTC(colorImage, true);

        processor.writeImageToFile(colorImage, RESOURCE_PATH + "gray.png");
    }

    @Test
    public void testQTPartitionTest() throws Exception {

    }

    @Test
    public void testHCBCEncode() throws Exception {
        Image testImg = processor.loadImageFromFile(RESOURCE_PATH + "lenna.png");

        Pair<HCBCTree, Image> encodeData = hcbc.hcbcEncode(testImg, 10e-5, 8);

        Image encodedImage = encodeData.getSnd();

        processor.writeImageToFile(encodedImage, RESOURCE_PATH + "encoded.png");
    }

    @Test
    public void testHCBCDecode() throws Exception {
        //Image testImg = processor.loadImageFromFile(RESOURCE_PATH + "lenna.png");
        Image testImg = processor.loadImageFromFile("/home/valentine/tst.png");

        Pair<HCBCTree, Image> encodeData = hcbc.hcbcEncode(testImg, 10e-5, 3);

        Image decodedImage = hcbc.hcbcDecode(encodeData.getFst(), encodeData.getSnd());

        processor.writeImageToFile(decodedImage, RESOURCE_PATH + "decoded.png");
    }
}
