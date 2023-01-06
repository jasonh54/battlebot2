package despacito7.util;

public class Pair<E1, E2> {
    E1 left;
    E2 right;
    public Pair(E1 l, E2 r) {
        this.left = l;
        this.right = r;
    }

    public E1 getLeft() {
        return left;
    }

    public E2 getRight() {
        return right;
    }
}
