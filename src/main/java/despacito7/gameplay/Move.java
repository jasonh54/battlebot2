package despacito7.gameplay;

import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import despacito7.Constants;
import despacito7.Constants.Stat;

public class Move implements Cloneable {
    Constants.Type type;
    String target;
    java.util.Map<Stat, Float> stats;
    String id;
    public Move(Map.Entry<String, JsonElement> entry){
        JsonObject v = entry.getValue().getAsJsonObject();
        this.type = Constants.Type.valueOf(v.get("type").getAsString());
        this.target = v.get("target").getAsString();
        this.stats = Stat.toMap(v.getAsJsonObject("stats"));
        this.id = entry.getKey();
    }

    public java.util.Map<Stat, Float> getStats() {
        return stats;
    }

    public int getStat(Stat s) {
        return (int) stats.get(s).intValue();
    }

    public String getId(){
        return id;
    }

    public String getTarget(){
        return target;
    }
}