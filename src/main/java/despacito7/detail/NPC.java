package despacito7.detail;

import despacito7.util.Coord;
import com.google.gson.Gson;
import java.util.ArrayList;

public class NPC {
    Gson gson = new Gson();
    public String id;
    Coord loc;

    public NPC(String id) {
        this.id = id;
        //ArrayList<NPC> npcs = gson.fromJson("npcs.json",ArrayList<NPC>.class);
        
        //loc = new Coord(temp.fromJson("loc")[0],temp.getJsonArray("loc")[1]);
        System.out.println("coords: ");
    }

}
