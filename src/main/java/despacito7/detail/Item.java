package despacito7.detail;

import java.awt.Point;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;

import despacito7.Constants;
import despacito7.FeatureLoader;
import despacito7.ResourceLoader;
import despacito7.Constants.Stat;
import despacito7.util.Coord;
import despacito7.util.Drawable;

public class Item {
    public final String id;
    private java.awt.Image sprite;
    private Map<Stat, Number> stats;
    public Item(String id, JsonObject data) {
        this.id = id;
        this.sprite = ResourceLoader.getItemSprite(id);
        this.stats = data.entrySet().stream()
            .map(e -> Map.<Stat, Number>entry(Stat.valueOf(e.getKey()), e.getValue().getAsNumber()))
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    public void apply(Object monster) {
        System.out.println(this.stats.toString());
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
}