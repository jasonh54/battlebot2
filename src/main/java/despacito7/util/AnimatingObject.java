package despacito7.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import java.util.HashMap;
import java.util.Map;

import despacito7.Constants;
import despacito7.Constants.Direction;
import despacito7.Constants.MoveState;

public abstract class AnimatingObject extends GameObject {
    protected Image[] sprites;
    protected int frame = 0;

    protected int animationFrame = 0;
    private int frameCount = 0;
    protected Map<String, int[]> animations;
    
    
    protected Point renderPos;
    

    protected String currentAnimation = "";

    public AnimatingObject(Coord coord, Image[] sprites) {
        super(coord, sprites[0]);
        this.renderPos = coord.getPosition();
        this.sprites = sprites;
        this.animations = new HashMap<String, int[]>();

    }
    
    public void draw(Graphics2D g) {
        g.drawImage(sprites[frame], renderPos.x, renderPos.y, Constants.tilesize, Constants.tilesize, null);
        if(animations.containsKey(currentAnimation)) {
            play(currentAnimation, 12, false);
        }
    }

    public void createAnimation(String name, int[] frames){
        animations.put(name, frames);
    }


    protected void play(String name, int frameDelay, boolean repeat){

        //play the animation based on the name
        currentAnimation = name;
        frameCount++;
        if(animationComplete() && !repeat){

        }
        else if (frameCount >= frameDelay) {
            frame = animations.get(name)[animationFrame%animations.get(name).length];
            animationFrame=(animationFrame+1);
            frameCount = 0;
        }

    }

    public boolean animationComplete(){
        
        if(animationFrame == animations.get(currentAnimation).length){
            return true;
        }
        return false;
    }

    public void setCurrentAnim(String name){
        currentAnimation=name;
        animationFrame = 0;
    }


    public Point getRenderPos() {
        return this.renderPos;
    }

    public void setCoord(int r, int c){
        coord.setCoord(r, c);
        renderPos.setLocation(c*Constants.tilesize, r*Constants.tilesize);
    }
    public void setCoord(Coord co){
        coord.setCoord(co);
        renderPos.setLocation(co.c*Constants.tilesize, co.r*Constants.tilesize);
    }
}
