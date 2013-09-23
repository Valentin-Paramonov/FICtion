package paramonov.valentin.fiction.collections;

public abstract class QuadTree<T> {
    protected T element;
    protected QuadTree<T>[] children;
    protected int size;

    public abstract boolean add(T t);

    protected boolean noChildren() {
        if(getChildren() == null) return true;

        for(Object o : getChildren()) {
            if(o != null) return false;
        }

        return true;
    }

    protected QuadTree<T>[] getChildren() {
        return children;
    }

    public final int size() {
        return size;
    }
}
