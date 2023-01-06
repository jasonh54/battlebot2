package despacito7;

import despacito7.Constants.Direction;

// import java.util.HashMap;
// import java.util.Map;

// import despacito7.detail.Item;
import despacito7.util.AnimatingObject;
import despacito7.util.Coord;
import despacito7.detail.Monster;

public class Player extends AnimatingObject {
    private static Player instance;
    
    public static Player getPlayer() {
        if (instance == null) instance = new Player();
        return instance;
    }
    private Monster[] monsters = new Monster[6];
    // private Map<Item, Integer> inventory = new HashMap<>();

    private Player() {
        super(new Coord(0,0), ResourceLoader.createCharacterSprites(1));
        createAnimation("leftWalk",new int[]{0,1,2}); // i somewhat disagree with the string-based animation keys
        createAnimation("downWalk",new int[]{3,4,5});
        createAnimation("upWalk",new int[]{6,7,8});
        createAnimation("rightWalk",new int[]{9,10,11});
    }

    public boolean monstersFull(){
        for(int i = 0; i < monsters.length; i++){
            if(monsters[i] == null){
                return false;
            }
        }
        return true;
    }

    public void addMonster(Monster mon){
        if(monstersFull()){
            return;
        }
        for(int i = 0; i < monsters.length; i++){
            if(monsters[i] == null){
                monsters[i] = mon;
                break;
            }
        }
    }

    public Monster removeMonster(int index){
        Monster temp = monsters[index];
        monsters[index] = null;
        return temp;
    }

    public void onKey(char keyCode) {
        if (Direction.fromKey(keyCode) != null) this.setDirection(Direction.fromKey(keyCode));
    }
}