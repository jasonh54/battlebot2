package despacito7.detail;

import despacito7.ResourceLoader;
import despacito7.gameplay.Move;
import despacito7.util.AnimatingObject;
import despacito7.util.Coord;
import despacito7.Constants;

import java.util.Map;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Color;

import com.google.gson.JsonElement;

import despacito7.Constants.Stat;
import despacito7.FeatureLoader;

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
        for (Map.Entry<Stat,Number> t : stats.entrySet()) {
            t.setValue((float) 0);
        }
        //load moves
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
    public int getStat(Stat s) {
        System.out.println(s + ": " + stats.get(s));
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
    public void updateStatChange(Move m) { //this is for moves
        for (Stat s : m.getStats().keySet()) {
            statchanges.put(s, statchanges.get(s) + m.getStat(s));
        }
    }

    public void updateStatChange(Item i) { //this is for items
        for (Stat s : i.getStats().keySet()) {
            statchanges.put(s, statchanges.get(s) + i.getStat(s));
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