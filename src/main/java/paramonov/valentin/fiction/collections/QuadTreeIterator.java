package paramonov.valentin.fiction.collections;

import paramonov.valentin.fiction.collections.QuadTree;
import paramonov.valentin.fiction.hcbc.HCBCTree;

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

        if (treeSize != 0) {
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
        if (!tree.hasChildren()) {
            blocks.add(tree.getElement());
            return;
        }

        QuadTree<T>[] children = tree.getChildren();

        treeToArray(children[0], blocks);
        treeToArray(children[1], blocks);
        treeToArray(children[2], blocks);
        treeToArray(children[3], blocks);
    }
}
