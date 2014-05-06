package paramonov.valentin.fiction.image.processor;

import paramonov.valentin.fiction.image.Image;

public class ImageUtils {
    public static double compareIdentical(Image dis, Image dat) {
        final int width = dis.getWidth();
        final int height = dis.getHeight();
        if(width != dat.getWidth() || height != dat.getHeight()) {
            throw new ImageDimensionException();
        }

        final int[] disColors = dis.getARGB();
        final int[] datColors = dat.getARGB();

        double diff = 0;
        for(int h = 0; h < height; h++) {
            final int startPos = h * width;
            for(int w = 0; w < width; w++) {
                final int pos = startPos + w;
                final int disColor = disColors[pos];
                final int datColor = datColors[pos];
                final int diffColor = disColor - datColor;
                diff += diffColor * diffColor;
            }
        }

        return Math.sqrt(diff / (width * height));
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
                    redColor += (colors[i] & 0xff0000) >> 16;
                    greenColor += (colors[i] & 0xff00) >> 8;
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
