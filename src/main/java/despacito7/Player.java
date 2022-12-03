package despacito7;

// import java.util.HashMap;
// import java.util.Map;

// import despacito7.Constants.Direction;
// import despacito7.detail.Item;
import despacito7.util.AnimatingObject;
import despacito7.util.Coord;

class Player extends AnimatingObject {
    private static Player instance;
    
    public static Player getPlayer() {
        if (instance == null) instance = new Player();
        return instance;
    }
    // private Direction dir = Direction.DOWN;
    // private int movestate;
    // private Map<Item, Integer> inventory = new HashMap<>();

    private Player() {
        super(new Coord(10,10), ResourceLoader.createCharacterSprites(1));
        createAnimation("leftWalk",new int[]{0,1,2}); // i somewhat disagree with the string-based animation keys
        createAnimation("downWalk",new int[]{3,4,5});
        createAnimation("upWalk",new int[]{6,7,8});
        createAnimation("rightWalk",new int[]{9,10,11});
    }

    
}