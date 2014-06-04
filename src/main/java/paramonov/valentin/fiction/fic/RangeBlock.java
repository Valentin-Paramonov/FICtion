package paramonov.valentin.fiction.fic;

import paramonov.valentin.fiction.collections.Block;

public class RangeBlock implements Block {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private DomainParams mappingDomain;

    public RangeBlock(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public DomainParams getMappingDomain() {
        return mappingDomain;
    }

    public void setMappingDomain(DomainParams mappingDomain) {
        this.mappingDomain = mappingDomain;
    }
}
