package paramonov.valentin.fiction.collections;

public abstract class QuadTree<T> {
    protected T element;
    protected QuadTree<T>[] children;
    protected int size;

    public abstract boolean add(T t);

    protected T getElement() {
        return element;
    }

    protected boolean hasChildren() {
        if(getChildren() == null) return false;

        for(Object o : getChildren()) {
            if(o != null) return true;
        }

        return false;
    }

    protected QuadTree<T>[] getChildren() {
        return children;
    }

    public final int size() {
        return size;
    }
}
