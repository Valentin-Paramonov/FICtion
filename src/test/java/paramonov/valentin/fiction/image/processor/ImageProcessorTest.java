package paramonov.valentin.fiction.image.processor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import paramonov.valentin.fiction.Resources;
import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.image.ImageUtils;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class ImageProcessorTest {
    private static final File TEST_OUTPUT_DIR = new File(Resources.TEST_OUT_PATH);

    @Before
    public void setUp() {
        if(!TEST_OUTPUT_DIR.exists()) {
            TEST_OUTPUT_DIR.mkdirs();
        }
    }

    @Test
    public void testLoadImageFromFile_ProducedImageDims() throws Exception {
        Image img = ImageProcessor.loadImageFromFile(Resources.TEST_IMG);

        int imgW = img.getWidth();
        int imgH = img.getHeight();

        assertThat(imgW, equalTo(5));
        assertThat(imgH, equalTo(5));
    }

    @Test
    public void testLoadImageFromFile_ColorArraySize() throws Exception {
        Image img = ImageProcessor.loadImageFromFile(Resources.TEST_IMG);

        int colorsLength = img.getARGB().length;

        assertThat(colorsLength, equalTo(5 * 5));
    }

    @Test
    public void testLoadImageFromFile_ImageOrientation() throws Exception {
        Image img = ImageProcessor.loadImageFromFile(Resources.TEST_IMG);

        int firstPixel = img.getARGB()[0];

        assertThat(firstPixel, equalTo(0xffffffff));
    }

    @Test
    public void testLoadImageFromFile_SolidPixel() throws Exception {
        Image img = ImageProcessor.loadImageFromFile(Resources.TEST_IMG);

        int solidPixel = img.getARGB()[5];

        assertThat(solidPixel, equalTo(0xff000000));
    }

    @Test
    public void testLoadImageFromFile_AlphaPixel() throws Exception {
        Image img = ImageProcessor.loadImageFromFile(Resources.TEST_IMG);

        int alphaPixel = img.getARGB()[24];

        assertThat(alphaPixel, equalTo(0x00000000));
    }

    @Test
    public void testWriteImageToFile_NoExtension() throws Exception {
        Image outImg = ImageProcessor.loadImageFromFile(Resources.TEST_IMG);

        String outFilePath = Resources.TEST_OUT_PATH + "/out image";

        ImageProcessor.writeImageToFile(outImg, outFilePath);

        File writtenFile = new File(outFilePath + ".png");

        assertTrue(writtenFile.exists());
    }

    @Test
    public void testWriteImageToFile_WithExtension() throws Exception {
        Image outImg = ImageProcessor.loadImageFromFile(Resources.TEST_IMG);

        String outFilePath = Resources.TEST_OUT_PATH + "/out image.png";

        ImageProcessor.writeImageToFile(outImg, outFilePath);

        File writtenFile = new File(outFilePath);

        assertTrue(writtenFile.exists());
    }

    @Test
    public void testToGrayscale_ProducesGrayscaleImage() throws Exception {
        Image colorImage = ImageProcessor.loadImageFromFile(Resources.LENNA);

        Image grayscaleImage = ImageUtils.toGrayscale(colorImage);

        ImageProcessor.writeImageToFile(grayscaleImage, Resources.TEST_OUT_PATH + "/grayscale.png");
    }

    @Test
    public void testWriteImageToDatFile() throws Exception {
        Image img = ImageProcessor.loadImageFromFile(Resources.LENNA);

        ImageProcessor
            .writeImageToDatFile(img, Resources.TEST_OUT_PATH + "/lenna.dat", img.getWidth(), img.getHeight());
    }

    @Test
    public void testPSNR_SameImageIsInfinity() throws Exception {
        Image img = ImageProcessor.loadImageFromFile(Resources.LENNA);

        double psnr = ImageUtils.psnr(img, img);

        Assert.assertThat(psnr, equalTo(Double.POSITIVE_INFINITY));
    }

    @Ignore
    @Test
    public void testName() throws Exception {
        Image img1 = ImageProcessor.loadImageFromFile("/home/valentine/" + "lenna_pgm.png");
        Image img2 = ImageProcessor.loadImageFromFile("/home/valentine/" + "lenna.jpg");

        double psnr = ImageUtils.psnr(img1, img2);

        Assert.assertThat(psnr, equalTo(0.));
    }
}
