package despacito7;

import despacito7.util.AnimatingObject;
import despacito7.util.Coord;

class Player extends AnimatingObject{ //extends AnimatingObject
    public Player(){
        super(new Coord(10,10), ResourceLoader.createCharacterSprites(1));
        createAnimation("rightWalk",new int[]{0,4,8});
    }
}
