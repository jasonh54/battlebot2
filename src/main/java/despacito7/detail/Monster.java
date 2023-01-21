package despacito7.detail;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import com.google.gson.JsonElement;

import despacito7.Constants.Stat;
import despacito7.FeatureLoader;
import despacito7.ResourceLoader;
import despacito7.gameplay.Move;
import despacito7.util.AnimatingObject;
import despacito7.util.Coord;

public class Monster extends AnimatingObject implements Cloneable {
    private final String id;
    Set<Move> moveset;
    private Map<Stat, Number> stats;

    public Monster(Map.Entry<String, JsonElement> entry) {
        super(new Coord(10,10), ResourceLoader.cutSprites(ResourceLoader.getMonsterSprite(entry.getValue().getAsJsonObject().get("sprite").getAsString())));
        //name self
        this.id = entry.getKey();
        //load stats
        this.stats = Stat.toMap(entry.getValue().getAsJsonObject().getAsJsonObject("stats"));
        //load moves
        this.moveset = new HashSet<>();
        for (JsonElement t : entry.getValue().getAsJsonObject().get("moves").getAsJsonArray()) {
            moveset.add(FeatureLoader.getMove(t.getAsString()));
        }
        //set up animations
        int[] frames = new int[sprites.length];
        for(int i = 0; i < sprites.length;i++) frames[i] = i;
        createAnimation("idle", frames);
        setCurrentAnim("idle");
    }

    //one million getters
    /* public setHealth(int h) {
        stats.get(HEALTH)
    } */

    // public Monster clone() {
    //     return new Monster();
    // }
}
