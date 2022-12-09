package despacito7.detail;

import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import despacito7.ResourceLoader;
import despacito7.util.AnimatingObject;
import despacito7.util.Coord;

import java.util.Set;
import java.util.HashSet;
import java.awt.Graphics2D;

public class Monster extends AnimatingObject implements Cloneable {
    // private final String id;
    // Set<Move> moveset = new HashSet<>();
    private int maxHealth = 0;
    private int defense = 0;

    public Monster(JsonElement entry) {
        super(new Coord(10,10), ResourceLoader.cutSprites(ResourceLoader.getMonsterSprite(entry.getAsJsonObject().get("sprite").getAsString())));
        // super(new Coord(10,10), ResourceLoader.createCharacterSprites(1));
        System.out.println(ResourceLoader.getMonsterSprite(entry.getAsJsonObject().get("sprite").getAsString()));
        // this.id = entry.getKey();
        maxHealth = entry.getAsJsonObject().get("stats").getAsJsonObject().get("MAX_HEALTH").getAsInt();
        defense = entry.getAsJsonObject().get("stats").getAsJsonObject().get("DEFENSE").getAsInt();
        int[] frames = new int[sprites.length];
        for(int i = 0; i < sprites.length;i++){
            frames[i] = i;
        }
        createAnimation("idle", frames);
        setCurrentAnim("idle");
    }

    
    // public Monster clone() {
    //     return new Monster();
    // }
}