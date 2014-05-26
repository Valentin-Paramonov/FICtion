package paramonov.valentin.fiction.image.processor;

import org.junit.Before;
import org.junit.Test;
import paramonov.valentin.fiction.Resources;
import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.image.ImageUtils;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ImageUtilsTest {
    private static final File TEST_OUTPUT_DIR = new File(Resources.TEST_OUT_PATH);

    @Before
    public void setUp() {
        if(!TEST_OUTPUT_DIR.exists()) {
            TEST_OUTPUT_DIR.mkdirs();
        }
    }

    @Test
    public void testDownsample_DownsampledImageHasExpectedDimensions() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(Resources.LENNA);

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
        final Image image = ImageProcessor.loadImageFromFile(Resources.LENNA);

        final Image downsampledImage = ImageUtils.downsample(image, 8);
        ImageProcessor.writeImageToFile(downsampledImage, Resources.TEST_OUT_PATH + "/downsampled.png");
    }

    @Test
    public void testReflectVertically_Visual() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(Resources.LENNA);

        final Image transformedImage = ImageUtils.reflectVertically(image);
        ImageProcessor.writeImageToFile(transformedImage, Resources.TEST_OUT_PATH + "/reflected vertically 01.png");
    }

    @Test
    public void testReflectHorizontally_Visual() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(Resources.LENNA);

        final Image transformedImage = ImageUtils.reflectHorizontally(image);
        ImageProcessor.writeImageToFile(transformedImage, Resources.TEST_OUT_PATH + "/reflected horizontally 01.png");
    }

    @Test
    public void testReflectDiagonally_Visual() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(Resources.LENNA);

        final Image transformedImage = ImageUtils.reflectDiagonally(image);
        ImageProcessor.writeImageToFile(transformedImage, Resources.TEST_OUT_PATH + "/reflected diagonally 01.png");
    }

    @Test
    public void testReflectDiagonallyNegative_Visual() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(Resources.LENNA);

        final Image transformedImage = ImageUtils.reflectDiagonallyNegative(image);
        ImageProcessor
            .writeImageToFile(transformedImage, Resources.TEST_OUT_PATH + "/reflected diagonally negative 01.png");
    }

    @Test
    public void testRotate90CW_Visual() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(Resources.LENNA);

        final Image transformedImage = ImageUtils.rotate90CW(image);
        ImageProcessor.writeImageToFile(transformedImage, Resources.TEST_OUT_PATH + "/rotated 90cw 01.png");
    }

    @Test
    public void testRotate90CCW_Visual() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(Resources.LENNA);

        final Image transformedImage = ImageUtils.rotate90CCW(image);
        ImageProcessor.writeImageToFile(transformedImage, Resources.TEST_OUT_PATH + "/rotated 90ccw 01.png");
    }

    @Test
    public void testRotate180_Visual() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(Resources.LENNA);

        final Image transformedImage = ImageUtils.rotate180(image);
        ImageProcessor.writeImageToFile(transformedImage, Resources.TEST_OUT_PATH + "/rotated 180 01.png");
    }
}
