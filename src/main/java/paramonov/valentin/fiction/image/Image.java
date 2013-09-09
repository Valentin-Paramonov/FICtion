package paramonov.valentin.fiction.image;

import java.nio.IntBuffer;

public class Image {
    private int[] argb;
    private int w;
    private int h;

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
}
