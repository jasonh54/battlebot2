package despacito7.gameplay;

import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import despacito7.Constants;
import despacito7.Constants.Stat;

public record Move(Constants.Type type, java.util.Map<Stat, Float> stats, String id) implements Cloneable {
    public static Move fromEntry(Map.Entry<String, JsonElement> entry) {
        JsonObject v = entry.getValue().getAsJsonObject();
        return new Move(Constants.Type.valueOf(v.get("type").getAsString()), Stat.toMap(v.getAsJsonObject("stats")),entry.getKey());
    }

    public Move clone() {
        return new Move(this.type, this.stats, this.id); // does this pass stats by reference?
    }

    public java.util.Map<Stat, Float> getStats() {
        return stats;
    }

    public int getStat(Stat s) {
        return (int) stats.get(s).intValue();
    }
}