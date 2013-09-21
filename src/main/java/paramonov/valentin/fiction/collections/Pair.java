package paramonov.valentin.fiction.collections;

public class Pair<F, S> {
    private F fst;
    private S snd;

    public Pair(F first, S second) {
        fst = first;
        snd = second;
    }

    public F getFst() {
        return fst;
    }

    public S getSnd() {
        return snd;
    }
}
