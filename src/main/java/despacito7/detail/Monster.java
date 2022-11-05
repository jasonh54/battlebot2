package despacito7.detail;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Set;

public class Monster extends AnimatingObject {
    String id;
    Set moveset = new ArrayList<Move>();
    public Monster(JsonObject data) {
        super(null, ResourceLoader.createCharacterSprites(data.get("sprite")));
        this.id = data.getKey();
    }


    
}
