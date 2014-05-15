package paramonov.valentin.fiction.fic;

public class RangeBlock {
    private final int x;
    private final int y;
    private final int w;
    private final int h;
    private DomainParams mappingDomain;

    public RangeBlock(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public DomainParams getMappingDomain() {
        return mappingDomain;
    }

    public void setMappingDomain(DomainParams mappingDomain) {
        this.mappingDomain = mappingDomain;
    }
}
