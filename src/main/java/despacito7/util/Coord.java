package despacito7.util;

import com.google.gson.JsonArray;

public class Coord {
    public static final int tilesize = 16;
    private final int r, c;

    public Coord(int r, int c) {
        this.r = r; this.c = c;
    }

    public static Coord ofJson(JsonArray ja) {
        return new Coord(ja.get(0).getAsInt(), ja.get(1).getAsInt());
    }

    public int[] getComponents() {
        return new int[]{r, c};
    }

    public int[] getRealComponents() {
        return new int[]{r*tilesize,c*tilesize};
    }

    public java.awt.Point getPosition() {
        return new java.awt.Point(r * tilesize, c * tilesize);
    }

    // may have to override hashCode
}
