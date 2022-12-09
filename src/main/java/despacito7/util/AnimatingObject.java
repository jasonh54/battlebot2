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
    private String direction = "";
    private String currentAnimation = "";

    public AnimatingObject(Coord coord, Image[] sprites) {
        super(coord, sprites[0]);
        position = coord.getPosition();
        this.sprites = sprites;
        animations = new HashMap<String, int[]>();
    }
    
    public void draw(Graphics2D g) {
        g.drawImage(sprites[frame], position.x, position.y, Constants.tilesize, Constants.tilesize, null);
        move();
        if(animations.containsKey(currentAnimation)) {
            play(currentAnimation, 12);
        }
    }

    public void createAnimation(String name, int[] frames){
        animations.put(name, frames);
    }

    private void play(String name, int frameDelay){
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
    //                        

    private void move(){
        if(movecounter == 0){
            locked = true;
        }
        
        if(locked){
            switch(direction){
                case "Up":
                case "up":
                    play("upWalk",8);
                    if(movecounter%3==1){
                        position.translate(0,-2);
                    }
                break;
                case "Down":
                case "down":
                    play("downWalk",8);
                    if(movecounter%3==1){
                        position.translate(0,2);
                    }
                break;
                case "Left":
                case "left":
                    play("leftWalk",8);
                    if(movecounter%3==1){
                        position.translate(-2,0);
                    }
                break;
                case "Right":
                case "right":
                    play("rightWalk",8);
                    if(movecounter%3==1){
                        position.translate(2,0);
                    }
                break;
            }
            if(movecounter>=24){
                System.out.println(locked);
                locked = false;
                movecounter=0;
            }
        }
        movecounter++;
        
    }

    public void setCurrentAnim(String name){
        currentAnimation=name;
    }

    public void setDirection(String d){
        if(!locked){
            direction = d;
            movecounter = 0;
        }
    }

}
