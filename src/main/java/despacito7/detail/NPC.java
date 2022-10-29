package despacito7.detail;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonObject;

import despacito7.FeatureLoader;
import despacito7.ResourceLoader;
import despacito7.util.AnimatingObject;
import despacito7.util.Coord;
@SuppressWarnings("unused")
public class NPC extends AnimatingObject{
    public final String id;
    private Map<String,String> topics = new HashMap<>(); // keys should be topic name (CHAT, BATTLE, SHOP), value should be response
    private Set<Monster> monsters;
    private Set<Item> items;
    private String[] movement;

    public NPC(String id, JsonObject data) {
        super(Coord.ofJson(data.get("loc").getAsJsonArray()), ResourceLoader.createCharacterSprites(data.get("sprite").getAsInt()));
        //name self
        this.id = id;
        //load topics
        String[] temp = (String[]) data.getAsJsonObject("topics").keySet().toArray();
        for (int i = 0; i < temp.length; i++) {
            topics.put(temp[i], data.getAsJsonObject("topics").get(temp[i]).toString());
        }
        
        //load monsters
        if (data.getAsJsonArray("monsters") != null) {
            for (int i = 0; i < data.getAsJsonArray("monsters").size(); i++) {
                monsters.add(new Monster(data.getAsJsonArray("monsters").get(i).toString()));
            }
        }
        //load items
        if (data.getAsJsonArray("items") != null) {
            for (int i = 0; i < data.getAsJsonArray("items").size(); i++) {
                items.add(FeatureLoader.getItem(data.getAsJsonArray("items").get(i).toString()));
            }
        }
    }

    public Coord getLoc() {
        return super.coord;
    }

    public String[] getTopics() {
        return this.topics.keySet().toArray(new String[]{});
    }

    public String getSingleResponse(String topic) {
        return this.topics.get(topic);
    }

    public String[] getItems() {
        return this.items.toArray(new String[]{});
    }
}