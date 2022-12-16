package despacito7.detail;

import despacito7.ResourceLoader;
import despacito7.util.AnimatingObject;
import despacito7.util.Coord;
import despacito7.Constants.Stat;

import java.util.Map;

import com.google.gson.JsonElement;

public class Monster extends AnimatingObject implements Cloneable {
    private final String id;
    // Set<Move> moveset = new HashSet<>();
    private Map<Stat, Number> stats;

    public Monster(Map.Entry<String, JsonElement> entry) {
        super(new Coord(10,10), ResourceLoader.cutSprites(ResourceLoader.getMonsterSprite(entry.getValue().getAsJsonObject().get("sprite").getAsString())));
        this.id = entry.getKey();
        this.stats = Stat.toMap(entry.getValue().getAsJsonObject().getAsJsonObject("stats"));
        
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
