package paramonov.valentin.fiction.fic;

public class DomainParams {
    private TransformationParams transformationParams;
    private int x;
    private int y;
    private int width;

    public TransformationParams getTransformationParams() {
        return transformationParams;
    }

    public void setTransformationParams(TransformationParams transformationParams) {
        this.transformationParams = transformationParams;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
