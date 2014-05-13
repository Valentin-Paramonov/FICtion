package paramonov.valentin.fiction.image;

import org.junit.Before;
import org.junit.Test;
import paramonov.valentin.fiction.image.processor.ImageProcessor;
import paramonov.valentin.fiction.image.processor.ImageProcessorProvider;

import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static paramonov.valentin.fiction.ListMatcher.listMatches;

public class ImageTest {
    private Image img;
    private static final String PATH = "src/test/resources/";
    private static final String TEST_IMG = PATH + "test image.png";

    @Before
    public void setUp() throws Exception {
        ImageProcessor processor = ImageProcessorProvider.getImageProcessor();

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

    @Test
    public void testGetColor_PixelHasExpectedValue() throws Exception {
        final int[] colors = {1, 2, 3, 4, 5, 6};
        final Image image = new Image(colors, 3, 2);

        final int color = image.getColor(1, 1);

        assertThat(color, equalTo(5));
    }

    @Test
    public void testSetColor_PixelValueChanges() throws Exception {
        final int[] colors = {1, 2, 3, 4, 5, 6};
        final Image image = new Image(colors, 3, 2);

        image.setColor(1, 1, 7);
        final ArrayList<Integer> colorList = new ArrayList<Integer>(colors.length);
        for(int i = 0; i < colors.length; i++) {
            colorList.add(colors[i]);
        }

        assertThat(colorList, hasItems(1, 2, 3, 4, 7, 6));
    }

    @Test
    public void testSubImage_SquareImage() {
        int[] colors = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        final Image testImage = new Image(colors, 3, 3);
        final Image subImage = testImage.subImage(1, 1, 2, 2);
        final int[] subColors = subImage.getARGB();
        final ArrayList<Integer> colorList = new ArrayList<>(9);
        for(int color : subColors) {
            colorList.add(color);
        }

        assertThat(colorList, listMatches(5, 6, 8, 9));
    }

    @Test
    public void testSubImage_RectangleImage() {
        int[] colors = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        final Image testImage = new Image(colors, 4, 3);
        final Image subImage = testImage.subImage(2, 1, 2, 2);
        final int[] subColors = subImage.getARGB();
        final ArrayList<Integer> colorList = new ArrayList<>(9);
        for(int color : subColors) {
            colorList.add(color);
        }

        assertThat(colorList, listMatches(7, 8, 11, 12));
    }
}
