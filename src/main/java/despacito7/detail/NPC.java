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
    private Map<Pair<String, String>,String> subtopics = new HashMap<Pair<String, String>,String>(); // key[0] should be source topic (CONTESTS), key[1] should be new topic (YES, NO), value should be response
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
                //update json to include # of items in the NPC.json
            }
        }

        if (data.has("monsters")) {
            for (JsonElement t : data.getAsJsonArray("monsters")) {
                monsters.add(FeatureLoader.getMonster(t.getAsString())); //monsters null?
            }
        }

        printAllInformation();
    }

    public void printAllInformation() { //to confirm all information has been correctly stored and is accessible!!
        System.out.println("*** Printing for NPC " + id + " ***");
        System.out.println("Topics: " + this.topics.keySet().toArray(new String[]{}));
        for (String s : this.topics.keySet()) {
            System.out.println("Response to " + s + ": " + topics.get(s));
        }
        for (Pair<String,String> p : this.subtopics.keySet()) {
            System.out.println("Response to " + p.getRight() + " from " + p.getLeft() + ": " + subtopics.get(p));
        }
        System.out.println("Monsters: " + monsters);
        System.out.println("Items: " + inventory);
    }

    public Coord getLoc() {
        return super.coord;
    }

    public String[] getTopics() { //get a list of the NPC's initial topics -- this should not include suptopics, which should only appear after the NPC asks the player the associated question
        return this.topics.keySet().toArray(new String[]{});
    }

    public String[] getQuestionTopics(String q) { //this gets a list of the NPC's subtopics, which are associated with a particular question
        ArrayList<String> s = new ArrayList<String>();
        for (Pair<String, String> p : subtopics.keySet()) {
            if (p.getLeft().equals(q)) {
                s.add(p.getRight());
            }
        }
        return (String[]) s.toArray();
    }

    public String getResponse(String t, String source) { //this gets the NPC's statement, which is associated with the topic selected by the player. this is only needed for subtopics. for standard ones, use Map.get()
        for (Pair<String, String> p : subtopics.keySet()) {
            if (p.getRight().equals(t) && p.getLeft().equals(source)) {
                return subtopics.get(p);
            }
        }
        return "Something is wrong; your topic does not exist!";
    }


    public void update() {
        if(stopped){
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
}