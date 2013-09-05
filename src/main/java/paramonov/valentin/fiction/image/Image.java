package paramonov.valentin.fiction.image;

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
}
