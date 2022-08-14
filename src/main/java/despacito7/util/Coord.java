package despacito7.util;

public class Coord {
    private final int r, c;

    public Coord(int r, int c) {
        this.r = r; this.c = c;
    }

    public int[] getComponents() {
        return new int[]{r, c};
    }

    // may have to override hashCode
}
