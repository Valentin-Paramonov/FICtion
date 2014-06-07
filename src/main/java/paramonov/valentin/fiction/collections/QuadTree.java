package paramonov.valentin.fiction.collections;

import java.lang.reflect.Array;
import java.util.Iterator;

public abstract class QuadTree<T extends QuadTree<T, B>, B extends Block> implements Iterable<T> {
    private B element;
    T[] children;

    public boolean add(B block) {
        if(this.element == null) {
            this.element = block;
            return true;
        }

        if(children == null) {
            children = (T[]) Array.newInstance(getClass(), 4);
        }

        final int place = findPlace(block);
        if(place < 0) {
            return false;
        }

        if(children[place] == null) {
            try {
                children[place] = (T) getClass().newInstance();
            } catch(InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return children[place].add(block);
    }

    protected final int findPlace(B block) {
        final B element = this.element;
        final int elementX = element.getX();
        final int elementY = element.getY();
        final int elementW = element.getWidth();
        final int elementH = element.getHeight();
        final int blockX = block.getX();
        final int blockY = block.getY();
        final int blockW = block.getWidth();
        final int blockH = block.getHeight();

        return quad(elementX, elementW, blockX, blockW) + 2 * quad(elementY, elementH, blockY, blockH);
    }

    private int quad(int regionStart, int regionWidth, int blockX, int blockW) {
        if(regionStart == blockX && regionWidth == blockW) {
            return -1;
        }

        final int regionEnd = regionStart + regionWidth;
        final int blockEnd = blockX + blockW;

        if(blockX < regionStart || blockEnd > regionEnd) {
            throw new BlockDimensionsException();
        }

        final int regionHalf = (regionStart + regionEnd + 1) / 2;

        if(blockEnd <= regionHalf) {
            return 0;
        }

        return 1;
    }

    public B getElement() {
        return element;
    }

    public boolean hasChildren() {
        if(children == null) {
            return false;
        }

        for(T child : children) {
            if(child != null) {
                return true;
            }
        }

        return false;
    }

    public T[] getChildren() {
        return children;
    }

    public final int size() {
        int size = element != null ? 1 : 0;

        if(!hasChildren()) {
            return size;
        }

        for(T child : children) {
            if(child == null) {
                continue;
            }
            size += child.size();
        }

        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new QuadTreeIterator(this);
    }
}
