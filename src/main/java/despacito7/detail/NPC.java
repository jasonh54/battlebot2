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
    String[] movement;

    public NPC(JsonObject data) {
        super(loc);
        //name self
        this.id = data.getKey();
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

}