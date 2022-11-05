package despacito7.util;

import java.awt.Image;
import java.awt.Point;

import despacito7.Constants;

public abstract class GameObject implements Drawable {
    protected Coord coord;
    protected Image sprite;

    public GameObject(Coord coord, Image sprite){
        this.coord = coord;
        this.sprite = sprite;
    }

    public void draw(java.awt.Graphics2D g){
        Point rc = this.coord.getPosition();
        g.drawImage(this.sprite, rc.x, rc.y, Constants.tilesize, Constants.tilesize, null);
    }

    // public abstract void draw(java.awt.Graphics2D g);
}
