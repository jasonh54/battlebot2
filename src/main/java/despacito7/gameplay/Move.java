package despacito7.gameplay;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import despacito7.Constants;

public record Move(Constants.Type type, java.util.Map<Constants.Stat, Number> stats) implements Cloneable {
    public static Move fromEntry(Map.Entry<String, JsonElement> entry) {
        JsonObject v = entry.getValue().getAsJsonObject();
        java.util.Map<Constants.Stat, Number> stats = new HashMap<>();
        for (java.util.Map.Entry<String, JsonElement> e : v.getAsJsonObject("stats").entrySet())
            stats.put(Constants.Stat.valueOf(e.getKey()), e.getValue().getAsNumber());
        return new Move(Constants.Type.valueOf(v.get("type").getAsString()), stats);
    }

    public Move clone() {
        return new Move(this.type, this.stats); // does this pass stats by reference?
    }
}