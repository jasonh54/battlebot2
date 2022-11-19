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
    private boolean locked = false;
    private int movecounter = 0;
    private Point position;

    public AnimatingObject(Coord coord, Image[] sprites) {
        super(coord, sprites[0]);
        position = coord.getPosition();
        this.sprites = sprites;
        animations = new HashMap<String, int[]>();
    }
    
    public void draw(Graphics2D g) {
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

    //move for 48 frames
    //each tile is 16x16
    //adding 0.0625f to the row/col means 1px as 1/16 is 0.0625
    //movement has 3 frames, switch frames at every 16th frame
    //after 48 frames would be one animation completion
    //change coordinate by 1px every 3rd frame(0.0635 of 1 row)
    //after 48 frames would be 16px worth of movement
    public void move(String direction){
        if(movecounter == 0){
            locked = true;
        }
        
        if(locked){
            switch(direction){
                case "Up":
                case "up":
                    play("upWalk",16);
                    if(movecounter%3==0){
                        position.translate(0,1);
                    }
                break;
                case "Down":
                case "down":
                    play("downWalk",16);
                    if(movecounter%3==0){
                        position.translate(0,-1);
                    }
                break;
                case "Left":
                case "left":
                    play("leftWalk",16);
                    if(movecounter%3==0){
                        position.translate(-1,0);
                    }
                break;
                case "Right":
                case "right":
                    play("rightWalk",16);
                    if(movecounter%3==0){
                        position.translate(1,0);
                    }
                break;
            }
            if(movecounter>=48){
                locked = false;
                movecounter=0;
            }
        }
        
        movecounter++;
    }

    
}
