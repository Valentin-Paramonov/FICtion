package paramonov.valentin.fiction.hcbc;

import paramonov.valentin.fiction.collections.QuadTree;

import java.util.Iterator;

public class HCBCTree extends QuadTree<HCBCBlock> implements Iterable<HCBCBlock> {
    public HCBCTree() {}

    protected HCBCTree(HCBCBlock block) {
        element = block;
    }

    @Override
    public boolean add(HCBCBlock block) {
        if(element == null) {
            element = block;
            size++;
            return true;
        }

        int placeForChild = findPlace(block);

        if(placeForChild == -1) return false;

        if(children == null) {
            size--;
            children = new HCBCTree[4];
        }

        if(children[placeForChild] == null) {
            children[placeForChild] = new HCBCTree(block);
            size++;
            return true;
        }

        return children[placeForChild].add(block);
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

        return horizontalQuad + 2 * verticalQuad;
    }

    protected int quad(
        int regionStart, int regionSize,
        int blockStart, int blockSize) {

        int halfSize = (regionSize + 1) / 2;
        int regionMid = regionStart + halfSize;
        int regionEnd = regionStart + regionSize;

        if(belongs(regionStart, regionMid, blockStart)) {
            if(blockSize > halfSize) return -1;
            return 0;
        } else if(belongs(regionMid, regionEnd, blockStart)) {
            if(blockSize > regionSize / 2) return -1;
            return 1;
        }

        return -1;
    }

    protected boolean belongs(int regionStart, int regionEnd, int coord) {
        return coord >= regionStart && coord < regionEnd;
    }

    @Override
    public Iterator<HCBCBlock> iterator() {
        return new HCBCIterator(this);
    }

    @Override
    public boolean hasChildren() {
        return super.hasChildren();
    }

    @Override
    protected HCBCBlock getElement() {
        return super.getElement();
    }

    @Override
    protected QuadTree<HCBCBlock>[] getChildren() {
        return super.getChildren();
    }

    public int getBlockWidth() {
        return element.getWidth();
    }

    public int getBlockHeight() {
        return element.getHeight();
    }
}
