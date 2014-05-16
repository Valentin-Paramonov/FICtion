package paramonov.valentin.fiction.collections;

import java.lang.reflect.Array;
import java.util.Iterator;

public abstract class QuadTree<T> implements Iterable<T> {
    private T element;
    private QuadTree<T>[] children;

    protected abstract void init();

    public boolean add(T t) {
        if (element == null) {
            element = t;
            init();
            return true;
        }

        if (children == null) {
            children = (QuadTree<T>[]) Array.newInstance(getClass(), 4);
        }

        final int place = findPlace(t);
        if (place == -1) {
            return false;
        }

        if (children[place] == null) {
            try {
                children[place] = (QuadTree<T>) getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return children[place].add(t);
    }

    protected abstract int findPlace(T t);

    protected T getElement() {
        return element;
    }

    protected boolean hasChildren() {
        if (getChildren() == null) return false;

        for (Object o : getChildren()) {
            if (o != null) return true;
        }

        return false;
    }

    protected QuadTree<T>[] getChildren() {
        return children;
    }

    public final int size() {
        if (!hasChildren()) {
            return element != null ? 1 : 0;
        }

        int size = 0;
        for (QuadTree<T> child : children) {
            if (child == null) continue;
            size += child.size();
        }

        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new QuadTreeIterator<T>(this);
    }
}
