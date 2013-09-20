package paramonov.valentin.fiction.collections;

public abstract class QuadTree<T> {
    protected T element;
    protected QuadTree<T>[] children;

    public abstract boolean add(T t);

    public T getElement() {
        return element;
    }

    public abstract int size();
}
