package despacito7.util;

import com.google.gson.JsonArray;

import despacito7.Constants;

public class Coord {
    public int r, c;

    public Coord(int r, int c) {
        this.r = r; this.c = c;
    }

    public Coord offset(int r, int c){
        return new Coord(this.r + r, this.c + c);
    }

    public void setCoord(int r, int c){
        this.r = r;
        this.c = c;
    }
    public void setCoord(Coord co){
        this.r = co.r;
        this.c = co.c;
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
        return new java.awt.Point(c * Constants.tilesize, r * Constants.tilesize);
    }
    public void print(){
        System.out.println("r:"+r + " c:"+c);
    }
    public boolean compare(Coord coord){
        if(coord.r == r && coord.c == c){
            return true;
        }
        return false;
    }
    // may have to override hashCode
}
