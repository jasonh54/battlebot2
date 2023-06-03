package despacito7.detail;

import java.awt.Point;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import despacito7.Constants;
import despacito7.FeatureLoader;
import despacito7.ResourceLoader;
import despacito7.Constants.Stat;
import despacito7.util.Coord;
import despacito7.util.Drawable;

public class Item implements Cloneable {
    public final String id;
    private java.awt.Image sprite;
    private Map<Stat, Float> stats;
    private String target;
    public Item(Map.Entry<String, JsonElement> entry) {
        this.id = entry.getKey();
        JsonObject v = entry.getValue().getAsJsonObject();
        this.sprite = ResourceLoader.getItemSprite(id);
        this.stats = Stat.toMap(v.get("stats").getAsJsonObject());
        this.target = v.get("TARGET").getAsString();
    }
    private Item(String id, java.awt.Image sprite, Map<Stat, Float> stats, String target) {
        this.id = id; this.sprite = sprite; this.stats = stats; this.target = target;
    }

    public void apply(Object monster) { // placeholder
        System.out.println(this.stats.toString());
    }

    public java.util.Map<Stat, Float> getStats() {
        return stats;
    }

    public int getStat(Stat s) {
        return (int) stats.get(s).intValue();
    }

    public static record GroundItem(Item item, Coord coord) implements Drawable {
        public GroundItem(String id, Coord coord) {
            this(FeatureLoader.getItem(id), coord);
        }
    
        public void draw(java.awt.Graphics2D g) {
            Point rc = this.coord.getPosition();
            g.drawImage(this.item.sprite, rc.x, rc.y, Constants.tilesize, Constants.tilesize, null);
        }
    }

    public String getTarget(){
        return target;
    }

    public Item clone() {
        return new Item(this.id, this.sprite, this.stats, this.target);
    }
}