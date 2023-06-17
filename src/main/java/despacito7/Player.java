package despacito7;

import java.util.Random;

import despacito7.Constants.Direction;

import despacito7.util.Coord;
import despacito7.detail.Item;
import despacito7.detail.Monster;
import despacito7.gameplay.Battle;
import despacito7.map.PortalTile;
import despacito7.util.Character;
import despacito7.Constants.GameState;


public class Player extends Character {
    private static Player instance;

    
    public static Player getPlayer() {
        if (instance == null) instance = new Player();
        return instance;
    }

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
    public boolean monstersFull() {
        if(monsters.size() == 6) {
            return true;
        } 
        return false;
    }

    public void addMonster(Monster mon) {
        if(monstersFull()) {
            return;
        }
        monsters.add(mon);
    }

    public Monster removeMonster(int index) {
        Monster temp = monsters.get(index);
        monsters.remove(index);
        return temp;
    }

    public void addItem(Item i){
        inventory.put(i, 1);
    }

    public void onKey(char keyCode) {
        if (Direction.fromKey(keyCode) != null) this.setDirection(Direction.fromKey(keyCode));
    }
    
    public void update(){
        if (FeatureLoader.getMap(App.currentmap).collides(coord)) {
            System.out.println("You are colliding");
        }


        if (FeatureLoader.getMap(App.currentmap).monsters(coord)) {
            System.out.println("You are touching grass");
            int rand = new Random().nextInt(App.featureLoader.getMonsterIds().length);
            App.currentMonster = App.featureLoader.getMonster(App.featureLoader.getMonsterIds()[rand]);
            App.currentBattle = new Battle(App.currentMonster.clone());
            App.currentGameState = GameState.BATTLE;
            System.out.println("A new battle has started");
        }

        if (FeatureLoader.getMap(App.currentmap).portals(coord)) {
            System.out.println("standing on portal");
            PortalTile pt =FeatureLoader.getMap(App.currentmap).getPortal(coord);
            System.out.println(pt.terminus().getLeft());
            App.currentmap = pt.terminus().getLeft().id;
            setCoord(pt.terminus().getRight());

        }
    }
        
}