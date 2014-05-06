package paramonov.valentin.fiction.image.processor;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import paramonov.valentin.fiction.image.Image;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ImageUtilsTest {
    private ImageProcessor processor;
    private static final String RESOURCE_PATH = "src/test/resources/";
    private static final String TEST_IMG = RESOURCE_PATH + "lenna.png";
    private static final int[] COLORS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    private static final int IMG_WIDTH = 4;
    private static final int IMG_HEIGHT = 4;

    private Image testImage;

    private static final Matcher<List<Integer>> listMatches(final Object... list) {
        return new BaseMatcher<List<Integer>>() {
            @Override
            public boolean matches(Object o) {
                if(!(o instanceof List)) {
                    return false;
                }

                List<Object> matchList = (List<Object>) o;

                if(list.length != matchList.size()) {
                    return false;
                }

                for(int i = 0; i < list.length; i++) {
                    final Object dis = list[i];
                    final Object dat = matchList.get(i);
                    if(!dis.equals(dat)) {
                        return false;
                    }
                }

                return true;
            }

            @Override
            public void describeTo(Description description) {
                StringBuilder sb = new StringBuilder("<[");
                for(Object o : list) {
                    sb.append(o + ", ");
                }
                final int length = sb.length();
                if(length != 2) {
                    sb.setLength(length - 2);
                }
                sb.append("]>");
                description.appendText(sb.toString());
            }
        };
    }

    @Before
    public void setUp() {
        processor = ImageProcessorProvider.getImageProcessor();
        testImage = new Image(COLORS, IMG_WIDTH, IMG_HEIGHT);
    }

    @Test
    public void testCompareIdentical_SameImageRMSIsZero() {
        final int[] colors = {1, 2, 3, 4};
        Image one = new Image(colors, 2, 2);
        Image two = new Image(colors, 2, 2);

        final double rms = ImageUtils.compareIdenticalGrayscale(one, two);

        assertThat(rms, equalTo(0.));
    }

    @Test
    public void testCompareIdentical_DifferentImageRMSHasExpectedValue() {
        final int[] colorsOne = {1, 2, 3, 4};
        final int[] colorsTwo = {2, 1, 4, 3};
        Image one = new Image(colorsOne, 2, 2);
        Image two = new Image(colorsTwo, 2, 2);

        final double rms = ImageUtils.compareIdenticalGrayscale(one, two);

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
        processor.writeImageToFile(downsampledImage, RESOURCE_PATH + "downsampled.png");
    }

    @Test
    public void testTransformation_Identical() {
        final ArrayList<Integer> colorSequence = new ArrayList<>(IMG_WIDTH * IMG_HEIGHT);

        for(int j = 0; j < IMG_HEIGHT; j++) {
            for(int i = 0; i < IMG_WIDTH; i++) {
                colorSequence.add(testImage.getColor(i, j));
            }
        }

        assertThat(colorSequence, listMatches(1, 2, 3, 4, 5, 6));
    }

    /*
        ┌─┬─┐
        │ │ │
        └─┴─┘
    */
    @Test
    public void testTransformation_VerticalReflection() {
        final ArrayList<Integer> colorSequence = new ArrayList<>(IMG_WIDTH * IMG_HEIGHT);

        for(int j = 0; j < IMG_HEIGHT; j++) {
            for(int i = 0; i < IMG_WIDTH; i++) {
                colorSequence.add(testImage.getColor(IMG_WIDTH - 1 - i, j));
            }
        }

        assertThat(colorSequence, listMatches(3, 2, 1, 6, 5, 4));
    }

    @Ignore
    @Test
    public void testTransformation_VerticalReflectionVisual() throws Exception {
        final Image image = processor.loadImageFromFile(TEST_IMG);
        final int width = image.getWidth();
        final int height = image.getHeight();
        final Image outImage = new Image(width, height);

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                final int color = image.getColor(i, j);
                outImage.setColor(width - 1 - i, j, color);
            }
        }

        processor.writeImageToFile(outImage, RESOURCE_PATH + "reflected vertically.png");
    }

    /*
        ┌───┐
        ├───┤
        └───┘
    */
    @Test
    public void testTransformation_HorizontalReflection() {
        final ArrayList<Integer> colorSequence = new ArrayList<>(IMG_WIDTH * IMG_HEIGHT);

        for(int j = 0; j < IMG_HEIGHT; j++) {
            for(int i = 0; i < IMG_WIDTH; i++) {
                colorSequence.add(testImage.getColor(i, IMG_HEIGHT - 1 - j));
            }
        }

        assertThat(colorSequence, listMatches(4, 5, 6, 1, 2, 3));
    }

    @Ignore
    @Test
    public void testTransformation_HorizontalReflectionVisual() throws Exception {
        final Image image = processor.loadImageFromFile(TEST_IMG);
        final int width = image.getWidth();
        final int height = image.getHeight();
        final Image outImage = new Image(width, height);

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                final int color = image.getColor(i, j);
                outImage.setColor(i, height - 1 - j, color);
            }
        }

        processor.writeImageToFile(outImage, RESOURCE_PATH + "reflected horizontally.png");
    }

    /*
        ┌───┐
        │ ╲ │
        └───┘
    */
    @Test
    public void testTransformation_DiagonalReflection() {
        final ArrayList<Integer> colorSequence = new ArrayList<>(IMG_WIDTH * IMG_HEIGHT);

        for(int j = 0; j < IMG_HEIGHT; j++) {
            for(int i = 0; i < IMG_WIDTH; i++) {
                colorSequence.add(testImage.getColor(IMG_WIDTH - 1 - i, IMG_HEIGHT - 1 - j));
            }
        }

        assertThat(colorSequence, listMatches(1, 5, 4, 3, 2, 6));
    }
}
