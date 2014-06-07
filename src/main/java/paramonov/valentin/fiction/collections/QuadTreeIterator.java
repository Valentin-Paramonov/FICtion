package paramonov.valentin.fiction.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

final class QuadTreeIterator<T extends QuadTree<T, ?>> implements Iterator<T> {
    private final T tree;
    private Iterator<T> iterator;

    QuadTreeIterator(T tree) {
        this.tree = tree;
        init();
    }

    private void init() {
        int treeSize = tree.size();
        List<T> subTrees = new ArrayList<>(treeSize);

        if(treeSize != 0) {
            treeToArray(tree, subTrees);
        }

        iterator = subTrees.iterator();
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

    private void treeToArray(T tree, List<T> subTrees) {
        if(tree == null) {
            return;
        }

        subTrees.add(tree);
        if(!tree.hasChildren()) {
            return;
        }

        for(T subTree : tree.getChildren()) {
            treeToArray(subTree, subTrees);
        }
    }
}
