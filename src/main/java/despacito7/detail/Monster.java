package despacito7.detail;

import java.util.Map;
import java.util.ArrayList;

import com.google.gson.JsonElement;

import despacito7.Constants.Stat;
import despacito7.ResourceLoader;
import despacito7.gameplay.Move;
import despacito7.util.AnimatingObject;
import despacito7.util.Coord;

public class Monster extends AnimatingObject implements Cloneable {
    private final String id;
    private ArrayList<Move> moveset;
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
        this.moveset = new ArrayList<Move>();
        for (JsonElement t : entry.getValue().getAsJsonObject().get("moves").getAsJsonArray()) {
            //moveset.add(FeatureLoader.getMove(t.getAsString())); // t is currently null???
        }
        //set up animations
        int[] frames = new int[sprites.length];
        for(int i = 0; i < sprites.length;i++) frames[i] = i;
        createAnimation("idle", frames);
        setCurrentAnim("idle");
    }

    //getters
    public int getStat(Stat s) {
        return (int) stats.get(s);
    }

    public ArrayList<Move> getMoves() {
        return moveset;
    }

    //setters
    //update base stat - USE SPARINGLY; eg  when levelling up
    public void updateStat(Stat s, int i) {
        stats.put(s, stats.get(s).intValue() + i);
    }

    //set a coefficient of stat - use when clearing status effects during or post-battle
    public void setStatChange(Stat s, float f) {
        statchanges.put(s,f);
    }

    //update coefficient of stat - use during battle for status effects
    public void updateStatChange(Stat s, float f) {
        statchanges.put(s, statchanges.get(s) + f);
    }

    private void learnMove(Move n) {
        if (moveset.size() < 4) {
            moveset.add(n);
        } else {
            forgetMove();
            learnMove(n);
        }
    }

    private void forgetMove() {
        Move o = moveset.get(3); //currently gets last one in the moveset; later should prompt player to choose
        if (moveset.contains(o)) {
            moveset.remove(o);
        }
    }

    // public Monster clone() {
    //     return new Monster();
    // }
}