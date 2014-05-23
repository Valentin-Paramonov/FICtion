package paramonov.valentin.fiction.fic;

public class FICHeader {
    private final int imageWidth;
    private final int imageHeight;
    private final int minSubdivisions;
    private final int maxSubdivisions;

    public FICHeader(int imageWidth, int imageHeight, int minSubdivisions, int maxSubdivisions) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.minSubdivisions = minSubdivisions;
        this.maxSubdivisions = maxSubdivisions;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getMinSubdivisions() {
        return minSubdivisions;
    }

    public int getMaxSubdivisions() {
        return maxSubdivisions;
    }
}
