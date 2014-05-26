package paramonov.valentin.fiction.image;

import paramonov.valentin.fiction.transformation.Transformation;

public class ImageUtils {
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

    public static double mse(Image original, Image comparable) {
        double mse = 0;

        int[] originalRGB = original.getARGB();
        int[] comparableRGB = comparable.getARGB();

        if(originalRGB.length != comparableRGB.length) {
            return -1;
        }

        for(int i = 0; i < originalRGB.length; i++) {
            int originalR = (originalRGB[i] >> 16) & 0xff;
            int comparableR = (comparableRGB[i] >> 16) & 0xff;
            int originalG = (originalRGB[i] >> 8) & 0xff;
            int comparableG = (comparableRGB[i] >> 8) & 0xff;
            int originalB = originalRGB[i] & 0xff;
            int comparableB = comparableRGB[i] & 0xff;

            double diffR = originalR - comparableR;
            double diffG = originalG - comparableG;
            double diffB = originalB - comparableB;

            mse += diffR * diffR + diffG * diffG + diffB * diffB;
        }

        mse /= 3 * originalRGB.length;

        return mse;
    }

    public static double psnr(Image original, Image comparable) {
        final double mse = mse(original, comparable);
        final double psnr = 10 * Math.log10((255 * 255) / Math.sqrt(mse));

        return psnr;
    }

    public static Image toGrayscale(Image img) {
        int[] gray = img.getARGB();

        for(int i = 0; i < gray.length; i++) {
            int color = gray[i];

            int r = (color >> 16) & 0xff;
            int g = (color >> 8) & 0xff;
            int b = color & 0xff;

            int luma = (int) (.2126 * r + .7153 * g + .0721 * b);

            gray[i] = (color & 0xff000000) | (luma << 16) | (luma << 8) | luma;
        }

        return new Image(gray, img.getWidth(), img.getHeight());
    }

    public static Image transform(Image image, Transformation transformation) {
        switch(transformation) {
            case IDENTITY:
                return image.copy();

            case REFLECTION_VERTICAL:
                return reflectVertically(image);

            case REFLECTION_HORIZONTAL:
                return reflectHorizontally(image);

            case REFLECTION_DIAGONAL:
                return reflectDiagonally(image);

            case REFLECTION_NEGATIVE_DIAGONAL:
                return reflectDiagonallyNegative(image);

            case ROTATION_90CW:
                return rotate90CCW(image);

            case ROTATION_90CCW:
                return rotate90CW(image);

            case ROTATION_180:
                return rotate180(image);

            default:
                return null;
        }
    }

    public static Image reflectVertically(Image image) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        final Image transformedImage = new Image(width, height);
        final int[] imageColors = image.getARGB();
        final int[] transformedImageColors = transformedImage.getARGB();

        for(int i = 0; i < imageColors.length; i += width) {
            for(int j = 0; j < width; j++) {
                transformedImageColors[i + width - 1 - j] = imageColors[i + j];
            }
        }

        return transformedImage;
    }

    public static Image reflectHorizontally(Image image) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        final Image transformedImage = new Image(width, height);

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                final int color = image.getColor(i, j);
                transformedImage.setColor(i, height - 1 - j, color);
            }
        }

        return transformedImage;
    }

    public static Image reflectDiagonally(Image image) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        final Image transformedImage = new Image(height, width);

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                final int color = image.getColor(i, j);
                transformedImage.setColor(j, i, color);
            }
        }

        return transformedImage;
    }

    public static Image reflectDiagonallyNegative(Image image) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        final Image transformedImage = new Image(height, width);

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                final int color = image.getColor(i, j);
                transformedImage.setColor(height - 1 - j, width - 1 - i, color);
            }
        }

        return transformedImage;
    }

    public static Image rotate90CW(Image image) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        final Image transformedImage = new Image(height, width);

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                final int color = image.getColor(i, j);
                transformedImage.setColor(height - 1 - j, i, color);
            }
        }

        return transformedImage;
    }

    public static Image rotate90CCW(Image image) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        final Image transformedImage = new Image(height, width);

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                final int color = image.getColor(i, j);
                transformedImage.setColor(j, width - 1 - i, color);
            }
        }

        return transformedImage;
    }

    public static Image rotate180(Image image) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        final Image transformedImage = new Image(width, height);

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                final int color = image.getColor(i, j);
                transformedImage.setColor(width - 1 - i, height - 1 - j, color);
            }
        }

        return transformedImage;
    }
}
