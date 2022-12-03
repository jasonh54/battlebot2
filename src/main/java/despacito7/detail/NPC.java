package despacito7.detail;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import despacito7.FeatureLoader;
import despacito7.ResourceLoader;
import despacito7.util.AnimatingObject;
import despacito7.util.Coord;

public class NPC extends AnimatingObject {
    public final String id;
    private Map<String,String> topics; // keys should be topic name (CHAT, BATTLE, SHOP), value should be response
    // private Set<Monster> monsters;
    private Set<Item> items;
    private ArrayList<String> movesequence;

    public NPC(Map.Entry<String, JsonElement> entry) {
        super(
            Coord.ofJson(entry.getValue().getAsJsonObject().get("loc").getAsJsonArray()),
            ResourceLoader.createCharacterSprites(entry.getValue().getAsJsonObject().get("sprite").getAsInt())
        );
        this.id = entry.getKey();

        //create animations
        createAnimation("LEFT",new int[]{0,1,2});
        createAnimation("DOWN",new int[]{3,4,5});
        createAnimation("UP",new int[]{6,7,8});
        createAnimation("RIGHT",new int[]{9,10,11});

        JsonObject data = entry.getValue().getAsJsonObject();

        this.topics = new HashMap<>();
        for (Map.Entry<String, JsonElement> te : data.getAsJsonObject("topics").entrySet())
            topics.put(te.getKey(), te.getValue().toString());
        
        this.items = new HashSet<>();
        if (data.has("items")) {
            for (JsonElement te : data.getAsJsonArray("items"))
                items.add(FeatureLoader.getItem(te.getAsString()));
        }

        this.movesequence = new ArrayList<String>();
        if (data.has("movement")) {
            for (JsonElement te : data.getAsJsonArray("movement"))
                movesequence.add(te.toString());
        }
        
        // this.monsters = new HashSet<>();
        // if (data.has("monsters")) {
        //     for (JsonElement te : data.getAsJsonArray("monsters"))
        //         monsters.add(FeatureLoader.getMonster(te.toString()));
        // }
    }

    public Coord getLoc() {
        return super.coord;
    }

    public String[] getTopics() {
        return this.topics.keySet().toArray(new String[]{});
    }

    public String getTopicResponse(String topic) {
        return this.topics.get(topic);
    }

    public String[] getItems() {
        return this.items.toArray(new String[]{});
    }

    public void playWalkSequence() { // what does this do??
        try {
            //for (String step : )
        } catch (Exception e) {
            System.out.println("This NPC " + id + " can't move!");
        }
    }
}