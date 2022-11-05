package despacito7.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import java.util.HashMap;
import java.util.Map;

import despacito7.Constants;

public abstract class AnimatingObject extends GameObject {
    protected Image[] sprites;
    private int frame = 0;
    private int animationFrame = 0;
    private int frameCount = 0;
    private Map<String, int[]> animations;

    public AnimatingObject(Coord coord, Image[] sprites) {
        super(coord, sprites[0]);
        this.sprites = sprites;
        animations = new HashMap<String, int[]>();
    }

    public void draw(Graphics2D g) {
        Point position = super.coord.getPosition();
        g.drawImage(sprites[frame], position.x, position.y, Constants.tilesize, Constants.tilesize, null);
    }

    public void createAnimation(String name, int[] frames){
        animations.put(name, frames);
    }

    public void play(String name, int frameDelay){
        //play the animation based on the name
        frame = animations.get(name)[animationFrame];
        frameCount++;
        if (frameCount >= frameDelay) {
            animationFrame=(animationFrame+1)%animations.get(name).length;
            frameCount = 0;
        }
    }
}
