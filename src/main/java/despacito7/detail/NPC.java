package despacito7.detail;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;
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

        for (Map.Entry<String, JsonElement> te : data.getAsJsonObject("topics").entrySet())
            topics.put(te.getKey(), te.getValue().toString());
        
        if (data.has("items")) {
            for (JsonElement te : data.getAsJsonArray("items"))
                items.add(FeatureLoader.getItem(te.toString()));
        }
        
        if (data.has("monsters")) {
            for (JsonElement te : data.getAsJsonArray("monsters"))
                monsters.add(FeatureLoader.getMonster(te.toString()));
        }

        //load movement
        if (data.getAsJsonArray("movement") != null) {
            for (int i = 0; i < data.getAsJsonArray("movement").size(); i++) {
                movement.add(new Item(data.getAsJsonArray("movement").get(i).toString()));
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

    public void playWalkSequence() {
        try {
            //move
        } catch (Exception e) {
            System.out.println("This NPC " + id + " can't move!");
        }
    }


}