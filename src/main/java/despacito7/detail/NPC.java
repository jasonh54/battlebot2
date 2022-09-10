package despacito7.detail;

import despacito7.util.Coord;
import com.google.gson.Gson;
import java.util.ArrayList;
// import despacito7.util.AnimatingObject;

public class NPC {
    Gson gson = new Gson();
    public String id;
    Coord loc;

    public NPC(String id) {
        this.id = id;
        //ArrayList<NPC> npcs = gson.fromJson("npcs.json",ArrayList<NPC>.class);
        
        System.out.println("coords: ");
    }
}