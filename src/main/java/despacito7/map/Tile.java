package despacito7.map;

import java.awt.Point;

import despacito7.util.Coord;
import despacito7.util.Drawable;

public record Tile(java.awt.Image sprite, Coord coord, TileType type) implements Drawable {
    public Tile(java.awt.Image sprite, Coord coord) {
        this(sprite, coord, TileType.NORMAL);
    }

    public void draw(java.awt.Graphics2D g){
        Point rc = this.coord.getPosition();
        g.drawImage(this.sprite, rc.x, rc.y, Coord.tilesize, Coord.tilesize, null);
    }

    public static enum TileType {
        NORMAL, COLLIDE, MONSTER
    }
}