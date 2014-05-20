package paramonov.valentin.fiction.collections;

import java.lang.reflect.Array;
import java.util.Iterator;

public abstract class QuadTree<T extends QuadTree<T, E>, E> implements Iterable<T> {
    private E element;
    T[] children;

    protected abstract void init();

    public boolean add(E element) {
        if(this.element == null) {
            this.element = element;
            init();
            return true;
        }

        if(children == null) {
            children = (T[]) Array.newInstance(getClass(), 4);
        }

        final int place = findPlace(element);
        if(place == -1) {
            return false;
        }

        if(children[place] == null) {
            try {
                children[place] = (T) getClass().newInstance();
            } catch(InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return children[place].add(element);
    }

    protected abstract int findPlace(E element);

    public E getElement() {
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
