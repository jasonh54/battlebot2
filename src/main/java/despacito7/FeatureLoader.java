package despacito7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import despacito7.detail.Item;
import despacito7.detail.Monster;
import despacito7.detail.NPC;
import despacito7.util.Loader;

public class FeatureLoader implements Loader {
    private static java.util.Map<String, despacito7.map.Map> maps;
    private static java.util.Map<String, Item> items;
    private static java.util.Map<String, NPC> npcs;
    private static java.util.Map<String, Monster> monsters;
    private static java.util.Map<String, java.util.Map<Constants.Stat, Number>> moves;
    private static boolean loaded = false;

    public static despacito7.map.Map getMap(String id) {return maps.get(id);}
    public static Item getItem(String id) {
        if (items.containsKey(id)) return items.get(id).clone();
        System.out.println("Invalid Item with ID '"+id+"'! Existing Items: "+items.keySet().toString());
        System.exit(-1);
        return null;
    }
    public static NPC getNPC(String id) {return npcs.get(id);}
    public static Monster getMonster(String id) {return monsters.get(id);} // unfinished
    public static java.util.Map<Constants.Stat, Number> getMove(String id) {return new HashMap<Constants.Stat, Number>(moves.get(id));}

    public static Player player;

    public boolean isLoaded() {
        return FeatureLoader.loaded;
    }

    public void load() {
        JsonObject movedata = loadJson("moves.json");
        FeatureLoader.moves = new HashMap<>(movedata.size(), 0.99f);
        for (java.util.Map.Entry<String, JsonElement> entry : movedata.entrySet()) {
            java.util.Map<Constants.Stat, Number> stats = new HashMap<>();
            for (java.util.Map.Entry<String, JsonElement> e : entry.getValue().getAsJsonObject().entrySet())
                stats.put(Constants.Stat.valueOf(e.getKey()), e.getValue().getAsNumber());
            FeatureLoader.moves.put(entry.getKey(), stats);
        }

        JsonObject itemdata = loadJson("items.json");
        FeatureLoader.items = new HashMap<>(itemdata.size(), 0.99f);
        for (java.util.Map.Entry<String, JsonElement> entry : itemdata.entrySet()) {
            FeatureLoader.items.put(entry.getKey(), new Item(entry));
        }

        JsonObject npcdata = loadJson("npcs.json");
        FeatureLoader.npcs = new HashMap<>(npcdata.size(), 0.99f);
        for (java.util.Map.Entry<String, JsonElement> entry : npcdata.entrySet()) {
            FeatureLoader.npcs.put(entry.getKey(), new NPC(entry));
        }

        JsonObject mapdata = loadJson("maps.json");
        FeatureLoader.maps = new HashMap<>(mapdata.size(), 0.99f);
        for (java.util.Map.Entry<String, JsonElement> entry : mapdata.entrySet()) {
            FeatureLoader.maps.put(entry.getKey(), new despacito7.map.Map(entry.getValue().getAsJsonObject()));
        }

        JsonObject monsterdata = loadJson("monsters.json");
        FeatureLoader.monsters = new HashMap<>(monsterdata.size(), 0.99f);
        for (java.util.Map.Entry<String, JsonElement> entry : monsterdata.entrySet()) {
            FeatureLoader.monsters.put(entry.getKey(), new Monster(entry));
        }

        player = Player.getPlayer();
        FeatureLoader.loaded = true;
    }

    private JsonObject loadJson(String filename) {
        try (Reader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("data/"+filename))) {
            String text = new BufferedReader(reader).lines().collect(Collectors.joining("\n"));
            return App.gson.fromJson(text, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
