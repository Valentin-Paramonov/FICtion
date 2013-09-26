package paramonov.valentin.fiction.hcbc;

import paramonov.valentin.fiction.collections.QuadTree;

public class HCBCTree extends QuadTree<HCBCBlock> {
    public HCBCTree() {}

    protected HCBCTree(HCBCBlock block) {
        element = block;
    }

    @Override
    public boolean add(HCBCBlock block) {
        if(element == null) {
            element = block;
            return true;
        }

        if(children == null) {
            children = new HCBCTree[4];
        }

        int index = findPlace(block);

        if(index == -1) return false;

        if(children[index] == null) {
            children[index] = new HCBCTree(block);
            size++;
            return true;
        }

        return children[index].add(block);
    }

    protected int findPlace(HCBCBlock block) {
        int horizontalQuad = quad(
            element.getX(), element.getWidth(),
            block.getX(), block.getWidth());

        if(horizontalQuad < 0) return -1;

        int verticalQuad = quad(
            element.getY(), element.getHeight(),
            block.getY(), block.getHeight());

        if(verticalQuad < 0) return -1;

        return horizontalQuad + 2*verticalQuad;
    }

    protected int quad(
        int regionStart, int regionSize,
        int blockStart, int blockSize) {

        int halfSize = (regionSize + 1) / 2;
        int regionMid = regionStart + halfSize;
        int regionEnd = regionStart + regionSize;

        if (belongs(regionStart, regionMid, blockStart)) {
            if (blockSize > halfSize) return -1;
            return 0;
        } else if (belongs(regionMid, regionEnd, blockStart)) {
            if (blockSize > regionSize / 2) return -1;
            return 1;
        }

        return -1;
    }

    protected boolean belongs(int regionStart, int regionEnd, int coord) {
        return coord >= regionStart && coord < regionEnd;
    }
}
