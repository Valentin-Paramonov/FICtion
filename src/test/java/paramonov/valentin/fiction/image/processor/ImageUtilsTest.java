package paramonov.valentin.fiction.image.processor;

import org.junit.Before;
import org.junit.Test;
import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.transformation.Transformation;

import java.io.File;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ImageUtilsTest {
    private ImageProcessor processor;
    private static final String RESOURCE_PATH = "src/test/resources/";
    private static final String TEST_IMG = RESOURCE_PATH + "lenna.png";
    private static final String TEST_OUTPUT_PATH = "target/test/out/";
    private static final File TEST_OUTPUT_DIR = new File(TEST_OUTPUT_PATH);
    private static final int[] COLORS = {1, 2, 3, 4};
    private static final Image TEST_IMAGE = new Image(COLORS, 2, 2);

    @Before
    public void setUp() {
        if(!TEST_OUTPUT_DIR.exists()) {
            TEST_OUTPUT_DIR.mkdirs();
        }

        processor = ImageProcessorProvider.getImageProcessor();
    }

    @Test
    public void testComputeDifferencesRms_SameImageRMSIsZero() {
        Image transformationImage = new Image(COLORS, 2, 2);

        final Map<Transformation, Double> rmsMap = ImageUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final Double rmsIdentity = rmsMap.get(Transformation.IDENTITY);

        assertThat(rmsIdentity, equalTo(0.));
    }

    @Test
    public void testComputeDifferencesRms_DifferentImageRMSHasExpectedValue() {
        final int[] colors = {2, 1, 4, 3};
        Image transformationImage = new Image(colors, 2, 2);

        final Map<Transformation, Double> rmsMap = ImageUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final Double rmsIdentity = rmsMap.get(Transformation.IDENTITY);

        assertThat(rmsIdentity, equalTo(1.));
    }

    @Test
    public void testComputeDifferencesRms_ReflectedHorizontally() {
        final int[] colors = {3, 4, 1, 2};
        Image transformationImage = new Image(colors, 2, 2);

        final Map<Transformation, Double> rmsMap = ImageUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final Double rmsIdentity = rmsMap.get(Transformation.REFLECTION_HORIZONTAL);

        assertThat(rmsIdentity, equalTo(0.));
    }

    @Test
    public void testComputeDifferencesRms_ReflectedVertically() {
        final int[] colors = {2, 1, 4, 3};
        Image transformationImage = new Image(colors, 2, 2);

        final Map<Transformation, Double> rmsMap = ImageUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final Double rmsIdentity = rmsMap.get(Transformation.REFLECTION_VERTICAL);

        assertThat(rmsIdentity, equalTo(0.));
    }

    @Test
    public void testComputeDifferencesRms_ReflectedDiagonally() {
        final int[] colors = {1, 3, 2, 4};
        Image transformationImage = new Image(colors, 2, 2);

        final Map<Transformation, Double> rmsMap = ImageUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final Double rmsIdentity = rmsMap.get(Transformation.REFLECTION_DIAGONAL);

        assertThat(rmsIdentity, equalTo(0.));
    }

    @Test
    public void testComputeDifferencesRms_ReflectedDiagonallyNegative() {
        final int[] colors = {4, 2, 3, 1};
        Image transformationImage = new Image(colors, 2, 2);

        final Map<Transformation, Double> rmsMap = ImageUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final Double rmsIdentity = rmsMap.get(Transformation.REFLECTION_NEGATIVE_DIAGONAL);

        assertThat(rmsIdentity, equalTo(0.));
    }

    @Test
    public void testComputeDifferencesRms_Rotated90CW() {
        final int[] colors = {3, 1, 4, 2};
        Image transformationImage = new Image(colors, 2, 2);

        final Map<Transformation, Double> rmsMap = ImageUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final Double rmsIdentity = rmsMap.get(Transformation.ROTATION_90CW);

        assertThat(rmsIdentity, equalTo(0.));
    }

    @Test
    public void testComputeDifferencesRms_Rotated90CCW() {
        final int[] colors = {2, 4, 1, 3};
        Image transformationImage = new Image(colors, 2, 2);

        final Map<Transformation, Double> rmsMap = ImageUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final Double rmsIdentity = rmsMap.get(Transformation.ROTATION_90CCW);

        assertThat(rmsIdentity, equalTo(0.));
    }

    @Test
    public void testComputeDifferencesRms_Rotated180() {
        final int[] colors = {4, 3, 2, 1};
        Image transformationImage = new Image(colors, 2, 2);

        final Map<Transformation, Double> rmsMap = ImageUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final Double rmsIdentity = rmsMap.get(Transformation.ROTATION_180);

        assertThat(rmsIdentity, equalTo(0.));
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
    public void testDownsample_DownsampledImageHasExpectedPixelValues() {
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
        processor.writeImageToFile(downsampledImage, TEST_OUTPUT_PATH + "downsampled.png");
    }
}
