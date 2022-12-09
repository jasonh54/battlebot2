package despacito7.detail;

import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import despacito7.ResourceLoader;
import despacito7.util.AnimatingObject;

import java.util.Set;
import java.util.HashSet;
import despacito7.util.Coord;

public class Monster extends AnimatingObject implements Cloneable {
    // private final String id;
    // Set<Move> moveset = new HashSet<>();

    public Monster(JsonElement entry) {
        super(new Coord(10,10), ResourceLoader.cutSprites(ResourceLoader.getMonsterSprite(entry.getAsJsonObject().get("sprite").getAsString())));
        // super(new Coord(10,10), ResourceLoader.createCharacterSprites(1));
        System.out.println(ResourceLoader.getMonsterSprite(entry.getAsJsonObject().get("sprite").getAsString()));
        // this.id = entry.getKey();
    }

    // public Monster clone() {
    //     return new Monster();
    // }
}