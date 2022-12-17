package despacito7.gameplay;

import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import despacito7.Constants;
import despacito7.Constants.Stat;

public record Move(Constants.Type type, java.util.Map<Constants.Stat, Number> stats) implements Cloneable {
    public static Move fromEntry(Map.Entry<String, JsonElement> entry) {
        JsonObject v = entry.getValue().getAsJsonObject();
        return new Move(Constants.Type.valueOf(v.get("type").getAsString()), Stat.toMap(v.getAsJsonObject("stats")));
    }

    public Move clone() {
        return new Move(this.type, this.stats); // does this pass stats by reference?
    }
}