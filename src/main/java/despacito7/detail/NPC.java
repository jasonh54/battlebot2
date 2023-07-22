package despacito7.detail;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import despacito7.FeatureLoader;
import despacito7.ResourceLoader;
import despacito7.Constants;
import despacito7.util.Character;
import despacito7.util.Coord;
import despacito7.util.Pair;

public class NPC extends Character {
    public final String id;
    private Map<String,String> topics = new HashMap<String,String>(); // keys should be topic name (CHAT, BATTLE, SHOP), value should be response
    private Map<Pair<String, String>,String> subtopics = new HashMap<Pair<String, String>,String>(); // key[0] should be source topic, key[1] should be new topic (YES, NO), value should be response
    private ArrayList<Pair<String,String>> movesequence;
    private int currentmove;

    public NPC(Map.Entry<String, JsonElement> entry) {
        super(
            Coord.ofJson(entry.getValue().getAsJsonObject().get("loc").getAsJsonArray()),
            ResourceLoader.createCharacterSprites(entry.getValue().getAsJsonObject().get("sprite").getAsInt())
        );

        this.id = entry.getKey();
        this.currentmove = 0;
        this.currentAnimation = "downIdle";

        JsonObject data = entry.getValue().getAsJsonObject();

        this.topics = new HashMap<>();

        //for each topic in the entryset
        for (Map.Entry<String, JsonElement> in : data.getAsJsonObject("topics").entrySet()) {
            String type = in.getValue().getAsJsonObject().get("type").getAsString();
            //store topics
            switch (type) {
                case "response":
                    subtopics.put(new Pair<String, String>(in.getValue().getAsJsonObject().get("source").getAsString(),in.getKey()),in.getValue().getAsJsonObject().get("text").getAsString());
                    break;
                case "line":
                    topics.put(in.getKey(),in.getValue().getAsJsonObject().get("text").getAsString());
                    break;
                case "question":
                    topics.put(in.getKey(),in.getValue().getAsJsonObject().get("text").getAsString());
                    break;
                default:
                    System.out.println("Unknown topic type " + type + " in NPC " + id + ".");
                    break;
            }
        }

        this.movesequence = new ArrayList<Pair<String,String>>();
        for (JsonElement ms : data.getAsJsonArray("movement")) {
            String[] step = ms.getAsString().split("/");
            movesequence.add(new Pair<String,String>(step[0],step[1]));
       }
        
        if (data.has("items")) {
            for (JsonElement te : data.getAsJsonArray("items")) {
                inventory.put(FeatureLoader.getItem(te.getAsString()),0);
                System.out.println(id + "has items: " + inventory.keySet());
                //update json to include # of items in the NPC.json
            }
        }

        if (data.has("monsters")) {
            for (JsonElement t : data.getAsJsonArray("monsters")) {
                monsters.add(FeatureLoader.getMonster(t.getAsString())); //monsters null?
                System.out.println(id + " has monsters: " + monsters);
            }
        }
    }

    public Coord getLoc() {
        return super.coord;
    }

    public String[] getTopics() {
        return this.topics.keySet().toArray(new String[]{});
    }

    public String[] getQuestionTopics(String q) {
        ArrayList<String> s = new ArrayList<String>();
        for (Pair<String, String> p : subtopics.keySet()) {
            if (p.getLeft().equals(q)) {
                s.add(p.getRight());
            }
        }
        return (String[]) s.toArray();
    }

    public String getResponse(String t) {
        if (topics.containsKey(t)) {
            return topics.get(t);
        } else {
            for (Pair<String, String> p : subtopics.keySet()) {
                if (p.getRight().equals(t)) {
                    return subtopics.get(p);
                }
            }
            return "Something is wrong; your topic does not exist!";
        }
    }

    public void update() { // change this -> json should supply animation names, not directions & movements (outdated)
        this.setDirection(Constants.Direction.valueOf(movesequence.get(currentmove).getRight()));
        this.setMovement(Constants.MoveState.valueOf(movesequence.get(currentmove).getLeft()));
        
        //sets direction, sitll need to set idle vs walk somehow
        if (currentmove == movesequence.size() - 1) {
            currentmove = 0;
        } else {
            currentmove++;
        }
    }
}