package despacito7.detail;

import despacito7.ResourceLoader;
import despacito7.util.AnimatingObject;
import despacito7.util.Coord;
import scala.concurrent.impl.FutureConvertersImpl.P;
import despacito7.Constants.*;
import despacito7.Constants;

import java.util.Map;

import java.awt.Graphics2D;

import com.google.gson.JsonElement;

public class Monster extends AnimatingObject implements Cloneable {
    private final String id;
    // Set<Move> moveset = new HashSet<>();
    private Map<Stat, Number> stats;

    public Monster(Map.Entry<String, JsonElement> entry) {
        super(new Coord(10,10), ResourceLoader.cutSprites(ResourceLoader.getMonsterSprite(entry.getValue().getAsJsonObject().get("sprite").getAsString())));
        this.id = entry.getKey();
        this.stats = Stat.toMap(entry.getValue().getAsJsonObject().getAsJsonObject("stats"));
        for(Stat stat : stats.keySet()){
            System.out.print(stat);
            System.out.println(": " + stats.get(stat));
        }

        int[] frames = new int[sprites.length];
        for(int i = 0; i < sprites.length;i++) frames[i] = i;
        createAnimation("idle", frames);
        setCurrentAnim("idle");
    }

    public void draw(Graphics2D g) {
        g.drawImage(sprites[frame], renderPos.x, renderPos.y, Constants.tilesize, Constants.tilesize, null);
        if(animations.containsKey(currentAnimation)) {
            play(currentAnimation, 12);
        }
    }

    // public Monster clone() {
    //     return new Monster();
    // }
}
