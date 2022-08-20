package despacito7.util;
import despacito7.util.Coord;
import despacito7.util.Drawable;

public class GameObject implements Drawable {
    public Coord coord;

    public GameObject(Coord coord){
        this.coord = coord;
    }

    public void draw(java.awt.Graphics2D g){

    }
}
