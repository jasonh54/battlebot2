package despacito7.detail;

import despacito7.App;
import despacito7.util.AnimatingObject;
import despacito7.util.Coord;

import java.awt.Graphics2D;
import com.google.gson.Gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
// import despacito7.util.AnimatingObject;

public class NPC extends AnimatingObject{
    Gson gson = new Gson();
    public String id;
    static Coord loc;
    HashMap<String,String> topics = new HashMap<>(); // keys should be topic name (CHAT, BATTLE, SHOP), value should be response
    ArrayList<Monster> monsters;
    ArrayList<Item> items;
    ArrayList<String> movement;

    public NPC(JsonObject data) {
        super(loc, ResourceLoader.createCharacterSprites(data.get("sprite")));
        //name self
        this.id = data.getKey();

        //create animations
        createAnimation("LEFT",new int[]{0,1,2});
        createAnimation("DOWN",new int[]{3,4,5});
        createAnimation("UP",new int[]{6,7,8});
        createAnimation("RIGHT",new int[]{9,10,11});

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
                items.add(new Item(data.getAsJsonArray("items").get(i).toString()));
            }
        }

        //load movement
        if (data.getAsJsonArray("movement") != null) {
            for (int i = 0; i < data.getAsJsonArray("movement").size(); i++) {
                movement.add(new Item(data.getAsJsonArray("movement").get(i).toString()));
            }
        }
    }

    public Coord getLoc() {
        return loc;
    }

    public String[] getTopics() {
        ArrayList<String> temp = new ArrayList<String>();
        for (Object o : topics.keySet()) {
            temp.add(o.toString());
        }
        return (String[]) temp.toArray();
    }

    public String getSingleResponse(String topic) {
        return topics.get(topic);
    }

    public String[] getItems() {
        return (String[]) items.toArray();
    }

    public void playWalkSequence() {
        try {
            //move
        } catch (Exception e) {
            System.out.println("This NPC " + id + " can't move!");
        }
    }

}