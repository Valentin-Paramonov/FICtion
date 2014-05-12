package paramonov.valentin.fiction.fic;

import paramonov.valentin.fiction.collections.QuadTree;

import java.util.Iterator;

public class FICTree extends QuadTree<RangeBlock> implements Iterable<RangeBlock> {
    @Override
    public Iterator<RangeBlock> iterator() {
        return null;
    }

    @Override
    public boolean add(RangeBlock rangeBlock) {
        return false;
    }
}
