package despacito7.util;

import com.google.gson.JsonArray;

import despacito7.Constants;

public class Coord {
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
        return new int[]{r*Constants.tilesize,c*Constants.tilesize};
    }

    public java.awt.Point getPosition() {
        return new java.awt.Point(r * Constants.tilesize, c * Constants.tilesize);
    }

    // may have to override hashCode
}
