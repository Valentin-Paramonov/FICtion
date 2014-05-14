package paramonov.valentin.fiction.image.processor;

import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.transformation.Transformation;

import java.util.HashMap;
import java.util.Map;

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
}
