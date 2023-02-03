package despacito7.map;

import java.awt.Point;

import despacito7.Constants;
import despacito7.util.Coord;
import despacito7.util.Drawable;
import despacito7.ResourceLoader;

public record Tile(java.awt.Image sprite, Coord coord, TileType type) implements Drawable {
    public Tile(int spritenum, Coord coord, TileType t) {
        this(ResourceLoader.getTileSprite(spritenum), coord, t);
    }

    public void draw(java.awt.Graphics2D g){
        Point rc = this.coord.getPosition();
        g.drawImage(this.sprite, rc.x, rc.y, Constants.tilesize, Constants.tilesize, null);
    }

    public static enum TileType {
        NORMAL, COLLIDE, MONSTER
    }
}