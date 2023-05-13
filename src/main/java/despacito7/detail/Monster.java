package despacito7.detail;

import despacito7.ResourceLoader;
import despacito7.gameplay.Move;
import despacito7.util.AnimatingObject;
import despacito7.util.Coord;
import despacito7.Constants;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Graphics2D;
import java.awt.Color;

import com.google.gson.JsonElement;

import despacito7.Constants.Stat;
import despacito7.FeatureLoader;

public class Monster extends AnimatingObject implements Cloneable {
    private final String id;
    private ArrayList<Move> moveset;
    private Map<Stat, Float> stats;
    private Map<Stat, Float> statchanges;

    public Monster(Map.Entry<String, JsonElement> entry) {
        super(new Coord(1,10), ResourceLoader.cutSprites(ResourceLoader.getMonsterSprite(entry.getValue().getAsJsonObject().get("sprite").getAsString())));
        //name self
        id = entry.getKey();
        //load stats
        stats = Stat.toMap(entry.getValue().getAsJsonObject().getAsJsonObject("stats"));
        // for (Map.Entry<Stat,Number> t : stats.entrySet()) {
        //     t.setValue(FeatureLoader.getStat);
        // }
        //load moves
        statchanges = new HashMap<Stat, Float>();
        statchanges.put(Stat.AGILITY, 1f);
        statchanges.put(Stat.DEFENSE, 1f);
        statchanges.put(Stat.DAMAGE, 1f);
        this.moveset = new ArrayList<Move>();
        for (JsonElement t : entry.getValue().getAsJsonObject().get("moves").getAsJsonArray()) {
            moveset.add(FeatureLoader.getMove(t.getAsString()));
        }
        //set up animations
        int[] frames = new int[sprites.length];
        for(int i = 0; i < sprites.length;i++) frames[i] = i;
        createAnimation("idle", frames);
        setCurrentAnim("idle");
    }

    //getters
    public String getName() {
        return id;
    }

    public float getStat(Stat s) {
        System.out.println(s + ": " + stats.get(s));
        return stats.get(s).floatValue();
    }

    public ArrayList<Move> getMoves() {
        return moveset;
    }

    //setters
    //update base stat - USE SPARINGLY; eg  when levelling up *exception is currenthealth, please use this function for that*
    public void updateStat(Stat s, float i) {
    //update base stat - this adds/subtracts the permanent stat value.
    //use this to update currenthealth, or to update other stats permanently (ex. levelling up)
        stats.put(s, stats.get(s) + i);
    }

    //set a coefficient of stat - this resets the value of a stat coefficient to a number
    //use this to reset/clear modifiers after a battle, or after healing (ex. move that clears status effects)
    public void setStatChange(Stat s, float f) {
        statchanges.put(s,f);
    }

    //update coefficient of stat - use during battle for temporary status effect changes
    //use this when a move or an item temporarily changes a stat (ex. speed boost, defense boost)
    //do NOT use this for currenthealth, which should NEVER have a coefficient
    public void updateStatChange(Move m) { //this is for moves
        for (Stat s : m.getStats().keySet()) {
            if(s.equals(Stat.HEALTH)){
                if(stats.get(Stat.HEALTH) + m.getStat(s) <= stats.get(Stat.MAX_HEALTH)){
                    updateStat(Stat.HEALTH, m.getStat(s));
                }
                System.out.println("health change: " + stats.get(Stat.HEALTH));
            } else {
                statchanges.put(s, statchanges.get(s) + m.getStat(s));
                System.out.println("stat change: " + statchanges.get(s));
            }
        }
    }

    public void updateStatChange(Item i) { //this is for items
        for (Stat s : i.getStats().keySet()) {
            System.out.println(s);
            if(s.equals(Stat.HEALTH)){
                if(stats.get(Stat.HEALTH) + i.getStat(s) <= stats.get(Stat.MAX_HEALTH)){
                    updateStat(Stat.HEALTH, i.getStat(s));
                }
                System.out.println("health change: " + stats.get(Stat.HEALTH));
            } else {
                statchanges.put(s, statchanges.get(s) + i.getStat(s));
                System.out.println("stat change: " + statchanges.get(s));
            }
        }
    }

    //move changes
    public void learnMove(Move n, Move o) {
        if (moveset.size() < 4) {
            moveset.add(n);
        } else {
            forgetMove(o);
            learnMove(n, o);
        }
    }

    private void forgetMove(Move o) {
        if (moveset.contains(o)) {
            moveset.remove(o);
        }
    }
    public void draw(Graphics2D g) {
        g.drawImage(sprites[frame], renderPos.x, renderPos.y, Constants.tilesize, Constants.tilesize, null);
        drawHealthBar(g);
        if(animations.containsKey(currentAnimation)) {
            play(currentAnimation, 12, true);
        }
    }

    public void drawHealthBar(Graphics2D g){
        g.setColor(Color.RED);
        g.fillRect((int)coord.getPosition().getX()-18, (int)coord.getPosition().getY()-10, 50, 5);
        g.setColor(Color.GREEN);
        g.fillRect((int)coord.getPosition().getX()-18, (int)coord.getPosition().getY()-10, (int)(stats.get(Stat.HEALTH).intValue()*0.5), 5);
        g.setColor(Color.BLACK);
    }

    // public Monster clone() {
    //     return new Monster();
    // }
}