package despacito7.detail;

import java.util.Map;

import com.google.gson.JsonElement;

import despacito7.ResourceLoader;
import despacito7.util.AnimatingObject;

import java.util.Set;
import java.util.HashSet;

public class Monster extends AnimatingObject implements Cloneable {
    private final String id;
    private Set<String> moveset = new HashSet<>();

    public Monster(Map.Entry<String, JsonElement> entry) {
        super(null, ResourceLoader.createCharacterSprites(entry.getValue().getAsJsonObject().get("sprite").getAsInt()));
        this.id = entry.getKey();
    }
}