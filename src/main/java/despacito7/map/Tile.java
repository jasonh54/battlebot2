package despacito7.map;

import despacito7.util.Coord;
import despacito7.util.Drawable;

public record Tile(java.awt.Image sprite, Coord coord) implements Drawable {
    public void draw(java.awt.Graphics2D g){
        g.drawImage(this.sprite, null, null);
    }

    public boolean isCollidable() {
        return false;
    }
}
