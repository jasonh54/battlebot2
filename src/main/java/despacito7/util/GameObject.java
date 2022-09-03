package despacito7.util;

public abstract class GameObject implements Drawable {
    public Coord coord;

    public GameObject(Coord coord){
        this.coord = coord;
    }

    public abstract void draw(java.awt.Graphics2D g);
}
