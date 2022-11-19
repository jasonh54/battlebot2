package despacito7.detail;


import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Set;
import java.util.HashSet;


public class Monster extends AnimatingObject implements Cloneable {
    private final String id;
    Set moveset = new HashSet<Move>();
    public Monster(Entry<String, JsonElement> entry) {
      super(null, ResourceLoader.createCharacterSprites(data.get("sprite")));
        this.id = entry.getKey();
    }
    private Monster(String id) {
        this.id = id;
    }

    public Monster clone() {
        return new Monster(id);
    }

}
