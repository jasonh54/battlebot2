package despacito7.util;


import java.awt.Image;
import java.awt.Point;




public abstract class GameObject implements Drawable {
    public Coord coord;
    Image sprite;

    public GameObject(Coord coord, Image sprite){
        this.coord = coord;
        this.sprite = sprite;
    }


    public void draw(java.awt.Graphics2D g){
        Point rc = this.coord.getPosition();
        g.drawImage(this.sprite, rc.x, rc.y, Coord.tilesize, Coord.tilesize, null);
    }

}
