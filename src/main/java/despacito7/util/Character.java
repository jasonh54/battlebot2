package despacito7.util;

import java.awt.Graphics2D;
import java.awt.Image;

import despacito7.App;
import despacito7.Constants;
import despacito7.Constants.Direction;
import despacito7.Constants.MoveState;
import despacito7.FeatureLoader;

import java.util.ArrayList;
import java.util.HashMap;

import despacito7.detail.Monster;
import despacito7.detail.Item;


public class Character extends AnimatingObject{
    private int movecounter = 0;
    private Direction direction;
    private MoveState moveState;
    private boolean locked = false;
    public boolean justStopped = false;
    public boolean stopped = true;
    public HashMap<Direction,Boolean> moveableDirections = new HashMap<Direction, Boolean>();

    protected ArrayList<Monster> monsters = new ArrayList<Monster>();
    protected HashMap<Item,Integer> inventory = new HashMap<Item,Integer>();

    public Character(Coord coord, Image[] sprites){
        super(coord,  sprites);
        this.direction = Direction.UP;
        this.moveState = MoveState.WALK;
        moveableDirections.put(Direction.UP, true);
        moveableDirections.put(Direction.DOWN, true);
        moveableDirections.put(Direction.LEFT, true);
        moveableDirections.put(Direction.RIGHT, true);
        moveableDirections.put(Direction.IDLE, true);
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
        justStopped = false;
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
                justStopped = true;
                stopped = true;
                movecounter=0;
            }else{
                stopped = false;
            }
        }
        movecounter++;
        
    }

    public void checkUnavaliableDirections(){
        if(FeatureLoader.getMap(App.currentmap).collides(coord.offset(1,0))){           
            moveableDirections.put(Direction.DOWN, false);
        }
        else{
            moveableDirections.put(Direction.DOWN, true);
        }
        if(FeatureLoader.getMap(App.currentmap).collides(coord.offset(-1,0))){
            moveableDirections.put(Direction.UP, false);
        }
        else{
            moveableDirections.put(Direction.UP, true);
        }
        if(FeatureLoader.getMap(App.currentmap).collides(coord.offset(0,1))){
            moveableDirections.put(Direction.RIGHT, false);
        }
        else{
            moveableDirections.put(Direction.RIGHT, true);
        }
        if(FeatureLoader.getMap(App.currentmap).collides(coord.offset(0,-1))){
            moveableDirections.put(Direction.LEFT, false);
        }
        else{
            moveableDirections.put(Direction.LEFT, true);
        }
    }

    public void setDirection(Direction d){
        checkUnavaliableDirections();
        if(!locked){
            if(moveableDirections.get(d)){
                direction = d;
                movecounter = 0;
                setCurrentAnim(d.toString());
            }
        }
    }
    public void setMovement(MoveState s){
        if(!locked){
            moveState = s;
            movecounter = 0;
            animationFrame = 0;
        }
    }

    public void setItemCount(Item item, int n){
        inventory.replace(item, n);
    }

    public Monster getMonster(int n) {
        return monsters.get(n);
    }

    public Monster[] getMonsters() {
        return (Monster[]) monsters.toArray();
    }

    public String[] getMonsterNames() {
        String[] names = new String[monsters.size()];
        for (int m = 0; m < monsters.size(); m++) {
            names[m] = monsters.get(m).getName();
        }
        return names;
    }

    public Item[] getItemList() {
        Item[] items = new Item[inventory.size()];
        Object[] objs = inventory.keySet().toArray();
        int i = 0;
        int j = 0;
        for (Object o : objs){
            if(inventory.get(objs[j]) != 0){
                items[i] = (Item) o;
                i++;
            }
            j++;
        }
        return items;
    }

    public int getItemCount(Item item) {
        return inventory.get(item);
    }
}
