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
import despacito7.util.Pair;

public class NPC extends AnimatingObject {
    public final String id;
    private Map<String,String> topics = new HashMap<String,String>(); // keys should be topic name (CHAT, BATTLE, SHOP), value should be response
    private Map<Pair,String> subtopics = new HashMap<Pair,String>(); // key[0] should be source topic, key[1] should be new topic (YES, NO), value should be response
    // private Set<Monster> monsters;
    private Set<Item> items;
    private ArrayList<String> movesequence;
    private int currentmove;

    public NPC(Map.Entry<String, JsonElement> entry) {
        super(
            Coord.ofJson(entry.getValue().getAsJsonObject().get("loc").getAsJsonArray()),
            ResourceLoader.createCharacterSprites(entry.getValue().getAsJsonObject().get("sprite").getAsInt())
        );

        this.id = entry.getKey();
        this.currentmove = 0;

        //create animations
        createAnimation("left",new int[]{0,1,2});
        createAnimation("down",new int[]{3,4,5});
        createAnimation("up",new int[]{6,7,8});
        createAnimation("right",new int[]{9,10,11});

        JsonObject data = entry.getValue().getAsJsonObject();

        this.topics = new HashMap<>();

        //for each NPC in the entryset
        for (Map.Entry<String, JsonElement> in : data.getAsJsonObject("topics").entrySet()) {
            String type = in.getValue().getAsJsonObject().get("type").getAsString();
            switch (type) {
                case "response":
                    subtopics.put(new Pair(in.getValue().getAsJsonObject().get("source").getAsString(),in.getKey()),in.getValue().getAsJsonObject().get("text").getAsString());
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

        this.movesequence = new ArrayList<String>();
        for (JsonElement te : data.getAsJsonArray("movement")) {
            movesequence.add(te.toString());
       }
        
        this.items = new HashSet<>();
        if (data.has("items")) {
            for (JsonElement te : data.getAsJsonArray("items")) {
                items.add(FeatureLoader.getItem(te.getAsString()));
            }
        }

        System.out.println(this.id + ": Hello, world! \n Spritenum is " + entry.getValue().getAsJsonObject().get("sprite").getAsInt() + ". \n Movement is " + movesequence + ". \n Topics are " + topics.keySet() + ". \n Coord is " + entry.getValue().getAsJsonObject().get("loc").getAsJsonArray() + ". \n Items are " + items + ". \n");
    }

    public Coord getLoc() {
        return super.coord;
    }

    public String[] getTopics() {
        return this.topics.keySet().toArray(new String[]{});
    }

    public String[] getQuestionTopics(String q) {
        ArrayList<String> s = new ArrayList<String>();
        for (Pair p : subtopics.keySet()) {
            if (p.getLeft() == q) {
                s.add((String) p.getRight());
            }
        }
        return (String[]) s.toArray();
    }

    public String getResponse(String t) {
        if (topics.containsKey(t)) {
            return topics.get(t);
        } else {
            for (Pair p : subtopics.keySet()) {
                if (p.getRight() == t) {
                    return subtopics.get(p);
                }
            }
            return "Something is wrong; your topic does not exist!";
        }
    }

    public String[] getItems() {
        return this.items.toArray(new String[]{});
    }

    public void update() {
        //setDirection(movesequence.get(currentmove));
        if (currentmove == movesequence.size() - 1) {
            currentmove = 0;
        } else {
            currentmove++;
        }
    }

}