package despacito7.detail;

import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import despacito7.ResourceLoader;
import despacito7.util.AnimatingObject;

import java.util.Set;
import java.util.HashSet;

public class Monster extends AnimatingObject implements Cloneable {
    // private final String id;
    // Set<Move> moveset = new HashSet<>();

    public Monster(JsonElement entry) {
        //monster sprites are not loaded in yet in ResourceLoader
        super(null, ResourceLoader.cutSprites(ResourceLoader.getMonsterSprite(entry.getAsJsonObject().get("sprite").getAsString())));
        // this.id = entry.getKey();
    }

    // public Monster clone() {
    //     return new Monster();
    // }
}