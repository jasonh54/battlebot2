package despacito7.detail;

import java.util.Map.Entry;

import com.google.gson.JsonElement;

public class Monster implements Cloneable {
    private final String id;
    public Monster(Entry<String, JsonElement> entry) {
        this.id = entry.getKey();
    }
    private Monster(String id) {
        this.id = id;
    }

    public Monster clone() {
        return new Monster(id);
    }
}
