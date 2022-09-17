package despacito7.detail;

import despacito7.util.Coord;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
// import despacito7.util.AnimatingObject;

public class NPC {
    Gson gson = new Gson();
    public String id;
    Coord loc;
    HashMap<String,String> topics = new HashMap<>(); // keys should be topic name (CHAT, BATTLE, SHOP), value should be response
    HashMap<String,Monster> monsters;
    String[] movement;

    public NPC(String id) {
        this.id = id;
        //fetch loc, movement, topics, etc from json
        String[] monsterids = {"AirA", "ZombieG"}; //retrieved list of monster names, get from json. if no monsters, should feed as null?
        if (monsterids != null) {
            for (String mon : monsterids) {
                monsters.put(mon, new Monster(mon));
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

    public void test() {
        System.out.println("movement: " + this.movement);
    }
}