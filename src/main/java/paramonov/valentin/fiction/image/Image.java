package paramonov.valentin.fiction.image;

import java.nio.IntBuffer;

public class Image {
    private final int[] argb;
    private final int w;
    private final int h;

    public Image(int imageWidth, int imageHeight) {
        argb = new int[imageWidth * imageHeight];
        w = imageWidth;
        h = imageHeight;
    }

    public Image(int[] colorArray, int imgW, int imgH) {
        argb = colorArray;
        w = imgW;
        h = imgH;
    }

    public int[] getARGB() {
        return argb;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public double getAspect() {
        return (double) w / (h != 0 ? h : 1);
    }

    public IntBuffer getBuffer() {
        return IntBuffer.wrap(argb);
    }

    public byte[] getRedChannel() {
        byte[] redChannel = new byte[argb.length];

        for(int i = 0; i < argb.length; i++) {
            redChannel[i] = (byte) ((argb[i] >> 16) & 0xff);
        }

        return redChannel;
    }

    public byte[] getGreenChannel() {
        byte[] greenChannel = new byte[argb.length];

        for(int i = 0; i < argb.length; i++) {
            greenChannel[i] = (byte) ((argb[i] >> 8) & 0xff);
        }

        return greenChannel;
    }

    public byte[] getBlueChannel() {
        byte[] blueChannel = new byte[argb.length];

        for(int i = 0; i < argb.length; i++) {
            blueChannel[i] = (byte) (argb[i] & 0xff);
        }

        return blueChannel;
    }

    public int getColor(int x, int y) {
        if(x < 0 || y < 0 || x >= w || y >= h) {
            final String errorMessage = String.format("x: %d, y: %d", x, y);
            throw new ImageDimensionException(errorMessage);
        }
        final int pos = w * y + x;

        return argb[pos];
    }

    public void setColor(int x, int y, int color) {
        if(x < 0 || y < 0 || x >= w || y >= h) {
            final String errorMessage = String.format("x: %d, y: %d", x, y);
            throw new ImageDimensionException(errorMessage);
        }
        final int pos = w * y + x;

        argb[pos] = color;
    }

    public Image copy() {
        final int[] argbCopy = new int[argb.length];
        for(int i = 0; i < argb.length; i++) {
            argbCopy[i] = argb[i];
        }

        return new Image(argbCopy, w, h);
    }

    public Image subImage(int startX, int startY, int width, int height) {
        final int[] subColors = new int[width * height];
        int subIndex = 0;
        for(int y = startY; y < startY + height; y++) {
            final int offset = w * y + startX;
            for(int x = offset; x < offset + width; x++) {
                final int color = argb[x];
                subColors[subIndex++] = color;
            }
        }

        return new Image(subColors, width, height);
    }

    public void replaceArea(int x, int y, Image image) {
        if(x < 0 || y < 0 || x >= w || y >= h) {
            throw new ImageDimensionException();
        }

        final int imageWidth = image.getWidth();
        final int imageHeight = image.getHeight();

        if(x + imageWidth > w || y + imageHeight > h) {
            throw new ImageDimensionException();
        }

        for(int j = y; j < y + imageHeight; j++) {
            final int startX = j * h;
            final int imageStartX = (j - y) * imageHeight;
            for(int i = x; i < x + imageWidth; i++) {
                argb[startX + i] = image.argb[imageStartX + i - x];
            }
        }
    }

    public void shiftColors(double contrast, double brightness) {
        for(int i = 0; i < argb.length; i++) {
            final int color = argb[i];
            final int r = (color & (0xff << 16)) >>> 16;
            final int g = (color & (0xff << 8)) >>> 8;
            final int b = color & 0xff;
            int shiftedR = (int) Math.round(r * contrast + brightness);
            int shiftedG = (int) Math.round(g * contrast + brightness);
            int shiftedB = (int) Math.round(b * contrast + brightness);
            shiftedR = shiftedR > 255 ? 255 : shiftedR;
            shiftedG = shiftedG > 255 ? 255 : shiftedG;
            shiftedB = shiftedB > 255 ? 255 : shiftedB;

            argb[i] = color & 0xff000000 | shiftedR << 16 | shiftedG << 8 | shiftedB;
        }
    }
}
