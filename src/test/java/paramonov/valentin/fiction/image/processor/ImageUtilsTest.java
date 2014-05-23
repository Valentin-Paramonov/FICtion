package paramonov.valentin.fiction.image.processor;

import org.junit.Before;
import org.junit.Test;
import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.image.ImageUtils;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ImageUtilsTest {
    private static final String RESOURCE_PATH = "src/test/resources/";
    private static final String TEST_IMG = RESOURCE_PATH + "lenna.png";
    private static final String TEST_OUTPUT_PATH = "target/test/out/";
    private static final File TEST_OUTPUT_DIR = new File(TEST_OUTPUT_PATH);

    @Before
    public void setUp() {
        if(!TEST_OUTPUT_DIR.exists()) {
            TEST_OUTPUT_DIR.mkdirs();
        }
    }

    @Test
    public void testDownsample_DownsampledImageHasExpectedDimensions() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(TEST_IMG);

        final Image downsampledImage = ImageUtils.downsample(image, 2);
        final int width = downsampledImage.getWidth();
        final int height = downsampledImage.getHeight();

        assertThat(width, equalTo(256));
        assertThat(height, equalTo(256));
    }

    @Test
    public void testDownsample_DownsampledImageHasExpectedPixelValues() {
        final int[] colors = {1, 1, 1, 1};
        final Image image = new Image(colors, 2, 2);

        final Image downsampledImage = ImageUtils.downsample(image, 2);
        final int color = downsampledImage.getColor(0, 0) & 0xffffff;

        assertThat(color, equalTo(1));
    }

    @Test
    public void testDownsample_VisualResults() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(TEST_IMG);

        final Image downsampledImage = ImageUtils.downsample(image, 8);
        ImageProcessor.writeImageToFile(downsampledImage, TEST_OUTPUT_PATH + "downsampled.png");
    }

    @Test
    public void testReflectVertically_Visual() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(TEST_IMG);

        final Image transformedImage = ImageUtils.reflectVertically(image);
        ImageProcessor.writeImageToFile(transformedImage, TEST_OUTPUT_PATH + "reflected vertically 01.png");
    }
}
