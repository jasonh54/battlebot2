package despacito7.util;

import java.awt.Image;

public abstract class GameObject implements Drawable {
    protected Coord coord;
    protected Image sprite;

    public GameObject(Coord coord, Image sprite){
        this.coord = coord;
        this.sprite = sprite;
    }

    public abstract void draw(java.awt.Graphics2D g);
}
