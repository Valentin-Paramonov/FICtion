package paramonov.valentin.fiction.fic;

import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.image.ImageDimensionException;
import paramonov.valentin.fiction.transformation.Transformation;

import java.util.ArrayList;
import java.util.List;

public class FICUtils {
    public static List<TransformationParams> computeDifferencesRms(Image dis, Image dat) {
        final int width = dis.getWidth();
        final int height = dis.getHeight();
        if(width != dat.getWidth() || height != dat.getHeight()) {
            throw new ImageDimensionException();
        }

        final Transformation[] transformations = Transformation.values();
        final int transformationCount = transformations.length;
        final List<TransformationParams> rmsParams = new ArrayList<>(transformationCount);
        final double[][] diffs = new double[transformationCount][5];
        final double n = width * height;

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                final int disColor = dis.getColor(i, j) & 0xff;
                addDiff(transformations, diffs, dat, width, height, disColor, i, j);
            }
        }

        for(int i = 0; i < transformationCount; i++) {
            final double aSum = diffs[i][0];
            final double bSum = diffs[i][1];
            final double a2Sum = diffs[i][2];
            final double b2Sum = diffs[i][3];
            final double abSum = diffs[i][4];

            final double s = (n * abSum - aSum * bSum) / (n * a2Sum - aSum * aSum);
            final double o = (bSum - s * aSum) / n;
            final double rms =
                Math.sqrt(1 / n * (b2Sum + s * (s * a2Sum - 2 * abSum + 2 * o * aSum) + o * (n * o - 2 * bSum)));

            final TransformationParams transformationParams = new TransformationParams(transformations[i], rms, s, o);
            rmsParams.add(transformationParams);
        }

        return rmsParams;
    }

    private static void addDiff(Transformation[] transformations, double[][] diffs, Image datImage, int width,
        int height, int color, int i, int j) {

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

            diffs[t][0] += color;
            diffs[t][1] += datColor;
            diffs[t][2] += color * color;
            diffs[t][3] += datColor * datColor;
            diffs[t][4] += color * datColor;
        }
    }

    public static void shiftColors(Image image, double contrast, double brightness) {
        final int[] colors = image.getARGB();

        for(int i = 0; i < colors.length; i++) {
            final int color = colors[i];
            final int gray = color & 0xff;
            int grayShifted = (int) Math.round(gray * contrast + brightness);
            grayShifted = (grayShifted > 255) ? 255 : (grayShifted < 0) ? 0 : grayShifted;

            colors[i] = color & 0xff000000 | grayShifted << 16 | grayShifted << 8 | grayShifted;
        }
    }
}
