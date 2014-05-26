package paramonov.valentin.fiction;

import org.junit.Before;
import org.junit.Test;
import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.image.processor.ImageProcessor;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertThat;
import static paramonov.valentin.fiction.TestUtils.listMatches;

public class TransformationTest {
    private static final String RESOURCE_PATH = "src/test/resources/";
    private static final String TEST_IMG = RESOURCE_PATH + "lenna rect.png";
    private static final String TEST_OUTPUT_PATH = "target/test/out/";
    private static final int[] COLORS = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private static final int IMG_WIDTH = 3;
    private static final int IMG_HEIGHT = 3;
    private static final File TEST_OUTPUT_DIR = new File(TEST_OUTPUT_PATH);

    private Image testImage;

    @Before
    public void setUp() {
        if(!TEST_OUTPUT_DIR.exists()) {
            TEST_OUTPUT_DIR.mkdirs();
        }
        testImage = new Image(COLORS, IMG_WIDTH, IMG_HEIGHT);
    }

    @Test
    public void testTransformation_Identical() {
        final ArrayList<Integer> colorSequence = new ArrayList<>(IMG_WIDTH * IMG_HEIGHT);

        for(int j = 0; j < IMG_HEIGHT; j++) {
            for(int i = 0; i < IMG_WIDTH; i++) {
                colorSequence.add(testImage.getColor(i, j));
            }
        }

        assertThat(colorSequence, listMatches(1, 2, 3, 4, 5, 6, 7, 8, 9));
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

        assertThat(colorSequence, listMatches(3, 2, 1, 6, 5, 4, 9, 8, 7));
    }

    @Test
    public void testTransformation_VerticalReflectionVisual() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(TEST_IMG);
        final int width = image.getWidth();
        final int height = image.getHeight();
        final Image outImage = new Image(width, height);

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                final int color = image.getColor(i, j);
                outImage.setColor(width - 1 - i, j, color);
            }
        }

        ImageProcessor.writeImageToFile(outImage, TEST_OUTPUT_PATH + "reflected vertically.png");
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

        assertThat(colorSequence, listMatches(7, 8, 9, 4, 5, 6, 1, 2, 3));
    }

    @Test
    public void testTransformation_HorizontalReflectionVisual() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(TEST_IMG);
        final int width = image.getWidth();
        final int height = image.getHeight();
        final Image outImage = new Image(width, height);

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                final int color = image.getColor(i, j);
                outImage.setColor(i, height - 1 - j, color);
            }
        }

        ImageProcessor.writeImageToFile(outImage, TEST_OUTPUT_PATH + "reflected horizontally.png");
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
                colorSequence.add(testImage.getColor(j, i));
            }
        }

        assertThat(colorSequence, listMatches(1, 4, 7, 2, 5, 8, 3, 6, 9));
    }

    @Test
    public void testTransformation_DiagonalReflectionVisual() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(TEST_IMG);
        final int width = image.getWidth();
        final int height = image.getHeight();
        final Image outImage = new Image(height, width);

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                final int color = image.getColor(i, j);
                outImage.setColor(j, i, color);
            }
        }

        ImageProcessor.writeImageToFile(outImage, TEST_OUTPUT_PATH + "reflected diagonally.png");
    }

    /*
        ┌───┐
        │ ╱ │
        └───┘
    */
    @Test
    public void testTransformation_NegativeDiagonalReflection() {
        final ArrayList<Integer> colorSequence = new ArrayList<>(IMG_WIDTH * IMG_HEIGHT);

        for(int j = 0; j < IMG_HEIGHT; j++) {
            for(int i = 0; i < IMG_WIDTH; i++) {
                colorSequence.add(testImage.getColor(IMG_HEIGHT - 1 - j, IMG_WIDTH - 1 - i));
            }
        }

        assertThat(colorSequence, listMatches(9, 6, 3, 8, 5, 2, 7, 4, 1));
    }

    @Test
    public void testTransformation_NegativeDiagonalReflectionVisual() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(TEST_IMG);
        final int width = image.getWidth();
        final int height = image.getHeight();
        final Image outImage = new Image(height, width);

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                final int color = image.getColor(i, j);
                outImage.setColor(height - 1 - j, width - 1 - i, color);
            }
        }

        ImageProcessor.writeImageToFile(outImage, TEST_OUTPUT_PATH + "reflected diagonally negative.png");
    }

    /*
        ┌───┐
        │ ↷ │
        └───┘
    */
    @Test
    public void testTransformation_Rotation90CW() {
        final ArrayList<Integer> colorSequence = new ArrayList<>(IMG_WIDTH * IMG_HEIGHT);

        for(int j = 0; j < IMG_HEIGHT; j++) {
            for(int i = 0; i < IMG_WIDTH; i++) {
                colorSequence.add(testImage.getColor(j, IMG_WIDTH - 1 - i));
            }
        }

        assertThat(colorSequence, listMatches(7, 4, 1, 8, 5, 2, 9, 6, 3));
    }

    @Test
    public void testTransformation_Rotation90CWVisual() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(TEST_IMG);
        final int width = image.getWidth();
        final int height = image.getHeight();
        final Image outImage = new Image(height, width);

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                final int color = image.getColor(i, j);
                outImage.setColor(height - 1 - j, i, color);
            }
        }

        ImageProcessor.writeImageToFile(outImage, TEST_OUTPUT_PATH + "rotated 90 cw.png");
    }

    /*
        ┌───┐
        │ ↶ │
        └───┘
    */
    @Test
    public void testTransformation_Rotation90CCW() {
        final ArrayList<Integer> colorSequence = new ArrayList<>(IMG_WIDTH * IMG_HEIGHT);

        for(int j = 0; j < IMG_HEIGHT; j++) {
            for(int i = 0; i < IMG_WIDTH; i++) {
                colorSequence.add(testImage.getColor(IMG_HEIGHT - j - 1, i));
            }
        }

        assertThat(colorSequence, listMatches(3, 6, 9, 2, 5, 8, 1, 4, 7));
    }

    @Test
    public void testTransformation_Rotation90CCWVisual() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(TEST_IMG);
        final int width = image.getWidth();
        final int height = image.getHeight();
        final Image outImage = new Image(height, width);

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                final int color = image.getColor(i, j);
                outImage.setColor(j, width - 1 - i, color);
            }
        }

        ImageProcessor.writeImageToFile(outImage, TEST_OUTPUT_PATH + "rotated 90 ccw.png");
    }

    /*
        ┌───┐
        │ ↻ │
        └───┘
    */
    @Test
    public void testTransformation_Rotation180() {
        final ArrayList<Integer> colorSequence = new ArrayList<>(IMG_WIDTH * IMG_HEIGHT);

        for(int j = 0; j < IMG_HEIGHT; j++) {
            for(int i = 0; i < IMG_WIDTH; i++) {
                colorSequence.add(testImage.getColor(IMG_WIDTH - 1 - i, IMG_HEIGHT - 1 - j));
            }
        }

        assertThat(colorSequence, listMatches(9, 8, 7, 6, 5, 4, 3, 2, 1));
    }

    @Test
    public void testTransformation_Rotation180Visual() throws Exception {
        final Image image = ImageProcessor.loadImageFromFile(TEST_IMG);
        final int width = image.getWidth();
        final int height = image.getHeight();
        final Image outImage = new Image(width, height);

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                final int color = image.getColor(i, j);
                outImage.setColor(width - 1 - i, height - 1 - j, color);
            }
        }

        ImageProcessor.writeImageToFile(outImage, TEST_OUTPUT_PATH + "rotated 180.png");
    }
}
