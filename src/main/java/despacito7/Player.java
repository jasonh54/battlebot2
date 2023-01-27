package despacito7;

import despacito7.Constants.Direction;
import java.awt.Graphics2D;

// import java.util.HashMap;
// import java.util.Map;
import java.util.ArrayList;

// import despacito7.detail.Item;
import despacito7.util.AnimatingObject;
import despacito7.util.Coord;
import despacito7.detail.Monster;

import despacito7.Constants.MoveState;
import despacito7.util.Character;

public class Player extends Character {
    private static Player instance;
    
    
    public static Player getPlayer() {
        if (instance == null) instance = new Player();
        return instance;
    }
    private ArrayList<Monster> monsters = new ArrayList<Monster>();
    // private Map<Item, Integer> inventory = new HashMap<>();

    private Player() {
        super(new Coord(0,0), ResourceLoader.createCharacterSprites(1));
        createAnimation("leftWalk",new int[]{0,1,2}); // i somewhat disagree with the string-based animation keys
        createAnimation("downWalk",new int[]{3,4,5});
        createAnimation("upWalk",new int[]{6,7,8});
        createAnimation("rightWalk",new int[]{9,10,11});
        createAnimation("leftIdle", new int[]{0});
        createAnimation("downIdle", new int[]{3});
        createAnimation("upIdle", new int[]{6});
        createAnimation("rightIdle", new int[]{9});
    }


    public boolean monstersFull(){
        if(monsters.size() == 6) {
            return true;
        } 
        return false;
    }

    public void addMonster(Monster mon){
        if(monstersFull()){
            return;
        }
        monsters.add(mon);
    }

    public Monster removeMonster(int index){
        Monster temp = monsters.get(index);
        monsters.remove(index);
        return temp;
    }

    public void onKey(char keyCode) {
        if (Direction.fromKey(keyCode) != null) this.setDirection(Direction.fromKey(keyCode));
    }

    public Monster getMonster(int n){
        return monsters.get(n);
    }
}