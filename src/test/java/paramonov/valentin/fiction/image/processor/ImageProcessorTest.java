package paramonov.valentin.fiction.image.processor;

import org.junit.Before;
import org.junit.Test;
import paramonov.valentin.fiction.image.Image;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class ImageProcessorTest {
    private ImageProcessor processor;
    private static final String PATH = "src/test/resources/";
    private static final String TEST_IMG = PATH + "test image.png";

    @Before
    public void setUp() {
        processor =
            ImageProcessorProvider.getImageProcessor();
    }

    @Test
    public void testLoadImageFromFile_ProducedImageDims() throws Exception {
        Image img =
            processor.loadImageFromFile(TEST_IMG);

        int imgW = img.getWidth();
        int imgH = img.getHeight();

        assertThat(imgW, equalTo(5));
        assertThat(imgH, equalTo(5));
    }

    @Test
    public void testLoadImageFromFile_ColorArraySize() throws Exception {
        Image img =
            processor.loadImageFromFile(TEST_IMG);

        int colorsLength = img.getARGB().length;

        assertThat(colorsLength, equalTo(5 * 5));
    }

    @Test
    public void testLoadImageFromFile_ImageOrientation() throws Exception {
        Image img =
            processor.loadImageFromFile(TEST_IMG);

        int firstPixel = img.getARGB()[0];

        assertThat(firstPixel, equalTo(0xffffffff));
    }

    @Test
    public void testLoadImageFromFile_SolidPixel() throws Exception {
        Image img =
            processor.loadImageFromFile(TEST_IMG);

        int solidPixel = img.getARGB()[5];

        assertThat(solidPixel, equalTo(0xff000000));
    }

    @Test
    public void testLoadImageFromFile_AlphaPixel() throws Exception {
        Image img =
            processor.loadImageFromFile(TEST_IMG);

        int alphaPixel = img.getARGB()[24];

        assertThat(alphaPixel, equalTo(0x00000000));
    }

    @Test
    public void testWriteImageToFile_NoExtension() throws Exception {
        Image outImg =
            processor.loadImageFromFile(TEST_IMG);

        String outFilePath = PATH + "out image";

        processor.writeImageToFile(outImg, outFilePath);

        File writtenFile = new File(outFilePath + ".png");

        assertTrue(writtenFile.exists());
    }

    @Test
    public void testWriteImageToFile_WithExtension() throws Exception {
        Image outImg =
            processor.loadImageFromFile(TEST_IMG);

        String outFilePath = PATH + "out image.png";

        processor.writeImageToFile(outImg, outFilePath);

        File writtenFile = new File(outFilePath);

        assertTrue(writtenFile.exists());
    }
}
