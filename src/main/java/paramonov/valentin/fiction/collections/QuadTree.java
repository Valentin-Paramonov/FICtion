package paramonov.valentin.fiction.collections;

public abstract class QuadTree<T> {
    protected T element;
    protected QuadTree<T>[] children;

    public abstract boolean add(T t);

    public T getElement() {
        return element;
    }

    public int size() {
        if(element == null) return 0;

        int size = 1;

        for(QuadTree qt : children) {
            if(qt != null) {
                size += qt.size();
            }
        }

        return size;
    }

    protected boolean noChildren() {
        for(Object o : getChildren()) {
            if(o != null) return false;
        }

        return true;
    }

    protected QuadTree<T>[] getChildren() {
        return children;
    }
}
