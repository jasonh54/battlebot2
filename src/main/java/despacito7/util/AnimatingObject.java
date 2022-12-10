package despacito7.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import java.util.HashMap;
import java.util.Map;

import despacito7.Constants;
import despacito7.Constants.Direction;

public abstract class AnimatingObject extends GameObject {
    protected Image[] sprites;
    private int frame = 0;
    private int animationFrame = 0;
    private int frameCount = 0;
    private Map<String, int[]> animations;
    private boolean locked = false;
    private int movecounter = 0;
    private Point renderPos;
    private Direction direction;

    public AnimatingObject(Coord coord, Image[] sprites) {
        super(coord, sprites[0]);
        this.renderPos = coord.getPosition();
        this.sprites = sprites;
        this.animations = new HashMap<String, int[]>();
        this.direction = Direction.UP;
    }
    
    public void draw(Graphics2D g) {
        g.drawImage(sprites[frame], renderPos.x, renderPos.y, Constants.tilesize, Constants.tilesize, null);
        move();
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

    //frame delay is 8 when switching animation
    //animation is done at 24 frames
    //movement needs to reach 16 px at 24 frames

    public void move(){
        if(movecounter == 0){
            locked = true;
        }
        
        if(locked){
            switch(direction) {
                case UP:
                    play("upWalk",8);
                    if(movecounter%3==1){
                        renderPos.translate(0,-2);
                    }
                break;
                case DOWN:
                    play("downWalk",8);
                    if(movecounter%3==1){
                        renderPos.translate(0,2);
                    }
                break;
                case LEFT:
                    play("leftWalk",8);
                    if(movecounter%3==1){
                        renderPos.translate(-2,0);
                    }
                break;
                case RIGHT:
                    play("rightWalk",8);
                    if(movecounter%3==1){
                        renderPos.translate(2,0);
                    }
                break;
            }
            if(movecounter>=24){
                locked = false;
                movecounter=0;
            }
        }
        movecounter++;
        
    }

    public void setDirection(Direction d){
        if(!locked){
            direction = d;
            movecounter = 0;
        }
    }

    
    public Point getRenderPos() {
        return this.renderPos;
    }
}
