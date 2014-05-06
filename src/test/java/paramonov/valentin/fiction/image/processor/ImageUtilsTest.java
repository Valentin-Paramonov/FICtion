package paramonov.valentin.fiction.image.processor;

import org.junit.Before;
import org.junit.Test;
import paramonov.valentin.fiction.image.Image;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ImageUtilsTest {
    private ImageProcessor processor;
    private static final String RESOURCE_PATH = "src/test/resources/";
    private static final String TEST_IMG = RESOURCE_PATH + "lenna.png";

    @Before
    public void setUp() {
        processor =
            ImageProcessorProvider.getImageProcessor();
    }

    @Test
    public void testCompareIdentical_SameImageRMSIsZero() throws Exception {
        final int[] colors = {1, 2, 3, 4};
        Image one = new Image(colors, 2, 2);
        Image two = new Image(colors, 2, 2);

        final double rms = ImageUtils.compareIdentical(one, two);

        assertThat(rms, equalTo(0.));
    }

    @Test
    public void testCompareIdentical_DifferentImageRMSHasExpectedValue() throws Exception {
        final int[] colorsOne = {1, 2, 3, 4};
        final int[] colorsTwo = {2, 1, 4, 3};
        Image one = new Image(colorsOne, 2, 2);
        Image two = new Image(colorsTwo, 2, 2);

        final double rms = ImageUtils.compareIdentical(one, two);

        assertThat(rms, equalTo(1.));
    }

    @Test
    public void testDownsample_DownsampledImageHasExpectedDimensions() throws Exception {
        final Image image = processor.loadImageFromFile(TEST_IMG);

        final Image downsampledImage = ImageUtils.downsample(image, 2);
        final int width = downsampledImage.getWidth();
        final int height = downsampledImage.getHeight();

        assertThat(width, equalTo(256));
        assertThat(height, equalTo(256));
    }

    @Test
    public void testDownsample_DownsampledImageHasExpectedPixelValues() throws Exception {
        final int[] colors = {1, 1, 1, 1};
        final Image image = new Image(colors, 2, 2);

        final Image downsampledImage = ImageUtils.downsample(image, 2);
        final int color = downsampledImage.getColor(0, 0) & 0xffffff;

        assertThat(color, equalTo(1));
    }

    @Test
    public void testDownsample_VisualResults() throws Exception {
        final Image image = processor.loadImageFromFile(TEST_IMG);

        final Image downsampledImage = ImageUtils.downsample(image, 8);
        processor.writeImageToFile(downsampledImage, RESOURCE_PATH + "downsampled.png");
    }
}
