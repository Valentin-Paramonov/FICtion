package paramonov.valentin.fiction.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

final class QuadTreeIterator<T> implements Iterator<T> {
    private final QuadTree<T> tree;
    private Iterator<T> iterator;

    QuadTreeIterator(QuadTree<T> tree) {
        this.tree = tree;
        init();
    }

    private void init() {
        int treeSize = tree.size();
        List<T> blockList = new ArrayList<>(treeSize);

        if(treeSize != 0) {
            treeToArray(tree, blockList);
        }

        iterator = blockList.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() throws NoSuchElementException {
        return iterator.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private void treeToArray(QuadTree<T> tree, List<T> blocks) {
        blocks.add(tree.getElement());
        if(!tree.hasChildren()) {
            return;
        }

        for(QuadTree<T> child : tree.getChildren()) {
            treeToArray(child, blocks);
        }
    }
}
