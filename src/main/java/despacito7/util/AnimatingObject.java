package despacito7.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

public abstract class AnimatingObject extends GameObject {
    protected Image[] sprites;
    private int frame = 0;

    public AnimatingObject(Coord coord) {
        super(coord);
    }

    public void draw(Graphics2D g) {
        Point position = super.coord.getPosition();
        g.drawImage(sprites[++frame], position.x, position.y, null);
    }
}
