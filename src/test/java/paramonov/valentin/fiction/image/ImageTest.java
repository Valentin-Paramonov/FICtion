package paramonov.valentin.fiction.image;

import org.junit.Before;
import org.junit.Test;
import paramonov.valentin.fiction.image.processor.ImageProcessor;
import paramonov.valentin.fiction.image.processor.ImageProcessorProvider;

import java.nio.IntBuffer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ImageTest {
    private ImageProcessor processor;
    private Image img;
    private static final String PATH = "src/test/resources/";
    private static final String TEST_IMG = PATH + "test image.png";

    @Before
    public void setUp() throws Exception {
        processor =
            ImageProcessorProvider.getImageProcessor();

        img = processor.loadImageFromFile(TEST_IMG);
    }

    @Test
    public void testGetBuffer_HasExpectedLength() {
        IntBuffer buff = img.getBuffer();

        int buffSize = buff.array().length;

        assertThat(buffSize, equalTo(25));
    }

    @Test
    public void testGetBuffer_HasData() {
        IntBuffer buff = img.getBuffer();

        int firstPixel = buff.get(0);

        assertThat(firstPixel, equalTo(0xffffffff));
    }

    @Test
    public void testGetBuffer_HasValidData() {
        IntBuffer buff = img.getBuffer();

        int firstPixel = buff.get(3);

        assertThat(firstPixel, equalTo(0xff000000));
    }

    @Test
    public void testGetAspect_ImageHasExpectedAspect() {
        double aspect = img.getAspect();

        assertThat(aspect, equalTo(1.));
    }

    @Test
    public void testGetARGB_ImageCanBeAltered() {
        int[] color = img.getARGB();

        color[0] = 0xff00ff;

        int pixel = img.getARGB()[0];

        assertThat(pixel, equalTo(0xff00ff));
    }
}
