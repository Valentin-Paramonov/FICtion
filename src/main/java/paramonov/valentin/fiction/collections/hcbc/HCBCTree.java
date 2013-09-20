package paramonov.valentin.fiction.collections.hcbc;

import paramonov.valentin.fiction.collections.QuadTree;

public class HCBCTree extends QuadTree<HCBCBlock> {
    public HCBCTree(int width, int height) {
        this(new HCBCBlock(
            0, 0, width, height, 0, 0, 0));
    }

    protected HCBCTree(HCBCBlock block) {
        element = block;
        children = new HCBCTree[4];
    }

    @Override
    public boolean add(HCBCBlock block) {

        if(element == null) {
            element = block;
            return true;
        }

        int index = findPlace(block);

        if(index == -1) return false;

        if(children[index] == null) {
            children[index] = new HCBCTree(block);
            return true;
        }

        return children[index].add(block);
    }

    protected int findPlace(HCBCBlock block) {
        int hQuad = quad(
            element.getX(), element.getWidth(),
            block.getX(), block.getWidth());

        if(hQuad < 0) return -1;

        int vQuad = quad(
            element.getY(), element.getHeight(),
            block.getY(), block.getHeight());

        if(vQuad < 0) return -1;

        return hQuad + 2*vQuad;
    }

    protected int quad(
        int regionStart, int regionSize,
        int blockStart, int blockSize) {

        int halfSize = (regionSize + 1) / 2;
        int regionMid = regionStart + halfSize;
        int regionEnd = regionStart + regionSize;

        if (fits(regionStart, regionMid, blockStart)) {
            if (blockSize > halfSize) return -1;
            return 0;
        } else if (fits(regionMid, regionEnd, blockStart)) {
            if (blockSize > regionSize / 2) return -1;
            return 1;
        }

        return -1;
    }

    protected boolean fits(int regionStart, int regionEnd, int coord) {
        return coord >= regionStart && coord < regionEnd;
    }
}
