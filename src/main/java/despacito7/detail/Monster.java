package despacito7.detail;

import despacito7.ResourceLoader;
import despacito7.util.AnimatingObject;
import despacito7.util.Coord;
import scala.concurrent.impl.FutureConvertersImpl.P;
import despacito7.Constants.*;
import despacito7.Constants;
import despacito7.gameplay.Move;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import java.awt.Graphics2D;
import java.awt.Color;

import com.google.gson.JsonElement;

import despacito7.Constants.Stat;
import despacito7.FeatureLoader;

public class Monster extends AnimatingObject implements Cloneable {
    private final String id;
    Set<Move> moveset = new HashSet<Move>();
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
            //moveset.add(FeatureLoader.getMove(t.getAsString())); // t is currently null???
        }
        //set up animations
        int[] frames = new int[sprites.length];
        for(int i = 0; i < sprites.length;i++) frames[i] = i;
        createAnimation("idle", frames);
        setCurrentAnim("idle");
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
        g.fillRect((int)coord.getPosition().getX(), (int)coord.getPosition().getY(), 50, 5);
        g.setColor(Color.GREEN);
        g.fillRect((int)coord.getPosition().getX(), (int)coord.getPosition().getY(), (int)(stats.get(Stat.HEALTH).intValue()*0.5), 5);
    }
    //one million getters
    /* public setHealth(int h) {
        stats.get(HEALTH)
    } */

    // public Monster clone() {
    //     return new Monster();
    // }
}
