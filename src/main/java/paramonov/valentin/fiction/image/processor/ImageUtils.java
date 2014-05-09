package paramonov.valentin.fiction.image.processor;

import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.transformation.Transformation;

import java.util.HashMap;
import java.util.Map;

public class ImageUtils {
    public static Map<Transformation, Double> computeDifferencesRms(Image dis, Image dat) {
        final int width = dis.getWidth();
        final int height = dis.getHeight();
        if(width != dat.getWidth() || height != dat.getHeight()) {
            throw new ImageDimensionException();
        }

        final Transformation[] transformations = Transformation.values();
        final int transformationCount = transformations.length;
        final HashMap<Transformation, Double> rmsMap = new HashMap<>(transformationCount);
        final double[] diffs = new double[transformationCount];
        final double diffCount = width * height;

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                final int disColor = dis.getColor(i, j) & 0xff;
                addDiff(transformations, diffs, dat, width, height, disColor, i, j);
            }
        }

        for(int i = 0; i < transformationCount; i++) {
            final double rms = Math.sqrt(diffs[i] / diffCount);
            rmsMap.put(transformations[i], rms);
        }

        return rmsMap;
    }

    private static void addDiff(Transformation[] transformations, double[] diffs, Image datImage, int width, int height,
        int color, int i, int j) {

        for(int t = 0; t < transformations.length; t++) {
            final int datColor;
            switch(transformations[t]) {
                case IDENTITY:
                    datColor = datImage.getColor(i, j) & 0xff;
                    break;

                case REFLECTION_HORIZONTAL:
                    datColor = datImage.getColor(i, height - 1 - j) & 0xff;
                    break;

                case REFLECTION_VERTICAL:
                    datColor = datImage.getColor(width - 1 - i, j) & 0xff;
                    break;

                case REFLECTION_DIAGONAL:
                    datColor = datImage.getColor(j, i) & 0xff;
                    break;

                case REFLECTION_NEGATIVE_DIAGONAL:
                    datColor = datImage.getColor(height - 1 - j, width - 1 - i) & 0xff;
                    break;

                case ROTATION_90CW:
                    datColor = datImage.getColor(height - j - 1, i) & 0xff;
                    break;

                case ROTATION_90CCW:
                    datColor = datImage.getColor(j, width - 1 - i) & 0xff;
                    break;

                case ROTATION_180:
                    datColor = datImage.getColor(width - 1 - i, height - 1 - j) & 0xff;
                    break;

                default:
                    throw new IllegalArgumentException("Unknown transformation: " + transformations[t]);
            }

            final int diff = color - datColor;
            diffs[t] += diff * diff;
        }
    }

    public static Image downsample(Image img, int factor) {
        final int width = img.getWidth();
        final int height = img.getHeight();

        final int newWidth = width / factor;
        final int newHeight = height / factor;
        final Image newImage = new Image(newWidth, newHeight);
        final int factorSquared = factor * factor;

        for(int x = 0; x < newWidth; x++) {
            for(int y = 0; y < newHeight; y++) {
                final int[] colors = new int[factorSquared];
                final int oldImgX = x * factor;
                final int oldImgY = y * factor;
                for(int i = 0; i < factor; i++) {
                    for(int j = 0; j < factor; j++) {
                        final int pos = i * factor + j;
                        colors[pos] = img.getColor(oldImgX + i, oldImgY + j);
                    }
                }

                int redColor = 0;
                int greenColor = 0;
                int blueColor = 0;
                for(int i = 0; i < factorSquared; i++) {
                    redColor += (colors[i] & 0xff0000) >>> 16;
                    greenColor += (colors[i] & 0xff00) >>> 8;
                    blueColor += (colors[i] & 0xff);
                }
                redColor /= factorSquared;
                greenColor /= factorSquared;
                blueColor /= factorSquared;

                final int newColor = 0xff000000 | redColor << 16 | greenColor << 8 | blueColor;
                newImage.setColor(x, y, newColor);
            }
        }

        return newImage;
    }
}
