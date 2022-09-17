package despacito7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

class FeatureLoader {
    private static java.util.Map<String, despacito7.map.Map> maps;
    private static java.util.Map<String, despacito7.detail.NPC> npcs;

    public static despacito7.map.Map getMap(String mapid) {return maps.get(mapid);}
    public static despacito7.detail.NPC getNPC(String npcid) {return npcs.get(npcid);}

    public void loadFeatures() {
        JsonObject mapdata = loadJson("maps.json");
        JsonObject npcsdata = loadJson("npcs.json");
        FeatureLoader.maps = new HashMap<>(mapdata.size(), 0.99f);
        FeatureLoader.npcs = new HashMap<>(npcsdata.size(), 0.99f);
        for (java.util.Map.Entry<String, JsonElement> entry : mapdata.entrySet()) {
            FeatureLoader.maps.put(entry.getKey(), new despacito7.map.Map(entry.getValue().getAsJsonObject()));
        }
    }

    public JsonObject loadJson(String filename) {
        try (Reader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("data/"+filename))) {
            String text = new BufferedReader(reader).lines().collect(Collectors.joining("\n"));
            return App.gson.fromJson(text, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
