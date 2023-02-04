package despacito7.detail;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;

import despacito7.Constants.Stat;
import despacito7.ResourceLoader;
import despacito7.gameplay.Move;
import despacito7.util.AnimatingObject;
import despacito7.util.Coord;

public class Monster extends AnimatingObject implements Cloneable {
    private final String id;
    Set<Move> moveset;
    private Map<Stat, Number> stats;
    private Map<Stat, Float> statchanges;

    public Monster(Map.Entry<String, JsonElement> entry) {
        super(new Coord(10,10), ResourceLoader.cutSprites(ResourceLoader.getMonsterSprite(entry.getValue().getAsJsonObject().get("sprite").getAsString())));
        //name self
        id = entry.getKey();
        //load stats
        stats = Stat.toMap(entry.getValue().getAsJsonObject().getAsJsonObject("stats"));
        for (Map.Entry<Stat,Float> t : statchanges.entrySet()) {
            t.setValue((float) 0);
        }
        //load moves
        this.moveset = new HashSet<>();
        for (JsonElement t : entry.getValue().getAsJsonObject().get("moves").getAsJsonArray()) {
            //moveset.add(FeatureLoader.getMove(t.getAsString())); // t is currently null???
        }
        //set up animations
        int[] frames = new int[sprites.length];
        for(int i = 0; i < sprites.length;i++) frames[i] = i;
        createAnimation("idle", frames);
        setCurrentAnim("idle");
    }

    //one million getters
    public int getStat(Stat s) {
        return (int) stats.get(s);
    }

    public void modStat(Stat s, float f) {
        statchanges.put(s,f);
    }

    // public Monster clone() {
    //     return new Monster();
    // }
}
