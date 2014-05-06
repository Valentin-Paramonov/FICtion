package paramonov.valentin.fiction.image;

import paramonov.valentin.fiction.image.processor.ImageDimensionException;

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
//        IntBuffer buff = IntBuffer.wrap(argb);
//
////        buff.flip();
//
//        return buff;
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
}
