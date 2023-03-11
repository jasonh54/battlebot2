package despacito7.util;

import java.awt.Graphics2D;
import java.awt.Image;
import despacito7.Constants;
import despacito7.Constants.Direction;
import despacito7.Constants.MoveState;
import java.util.ArrayList;
import java.util.HashMap;

import despacito7.detail.Monster;
import despacito7.detail.Item;


public class Character extends AnimatingObject{
    private int movecounter = 0;
    private Direction direction;
    private MoveState moveState;
    private boolean locked = false;

    protected ArrayList<Monster> monsters = new ArrayList<Monster>();
    protected HashMap<Item,Integer> inventory = new HashMap<Item,Integer>();

    public Character(Coord coord, Image[] sprites){
        super(coord,  sprites);
        this.direction = Direction.UP;
        this.moveState = MoveState.WALK;
    }

    public void draw(Graphics2D g) {
        g.drawImage(sprites[frame], renderPos.x, renderPos.y, Constants.tilesize, Constants.tilesize, null);
        move();
        if(animations.containsKey(currentAnimation)) {
            play(currentAnimation, 12, false);
        }
    }
    private void RenderToCoord(){
        coord.setCoord(renderPos.y/Constants.tilesize, renderPos.x/Constants.tilesize);
        // coord.print();
    }
    
    private void move(){
        RenderToCoord();
        if(movecounter == 0){
            locked = true;
        }
        
        if(locked){
            switch(moveState) {
                case WALK: 
                    switch(direction) {
                        case UP:
                            play("upWalk",8,false);
                            if(movecounter%3==1){
                                renderPos.translate(0,-2);
                            }
                        break;
                        case DOWN:
                            play("downWalk",8,false);
                            if(movecounter%3==1){
                                renderPos.translate(0,2);
                            }
                        break;
                        case LEFT:
                            play("leftWalk",8,false);
                            if(movecounter%3==1){
                                renderPos.translate(-2,0);
                            }
                        break;
                        case RIGHT:
                            play("rightWalk",8,false);
                            if(movecounter%3==1){
                                renderPos.translate(2,0);
                            }
                        break;
                    }
                break;
                case IDLE:
                    switch(direction) {
                        case UP:
                            play("upIdle",8,true);
                        break;
                        case DOWN:
                            play("downIdle",8,true);
                        break;
                        case LEFT:
                            play("leftWalk",8,true);
                        break;
                        case RIGHT:
                            play("rightWalk",8,true);
                        break;
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
            setCurrentAnim(d.toString());
        }
    }
    public void setMovement(MoveState s){
        if(!locked){
            moveState = s;
            movecounter = 0;
            animationFrame = 0;
        }
    }

    public Monster getMonster(int n) {
        return monsters.get(n);
    }

    public Monster[] getMonsters() {
        return (Monster[]) monsters.toArray();
    }

    public String[] getMonsterNames() {
        ArrayList<String> names = new ArrayList<String>();
        for (Monster m : monsters) {
            names.add(m.getName());
        }
        return (String[]) names.toArray();
    }

    public Item[] getItemList() {
        return (Item[]) inventory.keySet().toArray();
    }

    public int getItemCount(Item item) {
        return inventory.get(item);
    }
}
