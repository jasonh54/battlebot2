package despacito7.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import despacito7.ResourceLoader;
import despacito7.detail.*;
import despacito7.map.Tile.TileType;
import despacito7.util.Coord;
import despacito7.util.Drawable;

public class Map implements Drawable {
    public enum LayerType { BASE(Layer.class), INTERACT(InteractLayer.class), TOP(Layer.class);
        public final Class<? extends Layer> clazz;
        LayerType(Class<? extends Layer> clazz) {
            this.clazz = clazz;
        }
    }
    private java.util.Map<LayerType, Layer> layers = new HashMap<>();
    private Set<NPC> npcs = new HashSet<>();
    private Set<Item> items = new HashSet<>();

    public Map(JsonObject data) {
        for (java.util.Map.Entry<String, JsonElement> layerdata : data.get("layers").getAsJsonObject().entrySet()) {
            LayerType layertype = LayerType.valueOf(layerdata.getKey().toUpperCase());
            switch (layertype) {
                case INTERACT:
                    layers.put(layertype, new InteractLayer(layerdata.getValue().getAsJsonArray()));
                break;
                default:
                    layers.put(layertype, new Layer(layerdata.getValue().getAsJsonArray()));
            }
        }
        for (JsonElement npcid : data.get("npcs").getAsJsonArray()) {
            npcs.add(new NPC(npcid.getAsString()));
        }
        for (JsonElement itemd : data.get("items").getAsJsonArray()) {
            JsonObject itemdata = itemd.getAsJsonObject();
            items.add(new Item(itemdata.get("variant").getAsString(), Coord.ofJson(itemdata.get("location").getAsJsonArray())));
        }
    }

    public void draw(java.awt.Graphics2D g) {
        layers.get(LayerType.BASE).draw(g);
        layers.get(LayerType.INTERACT).draw(g);
    }

    public void postDraw(java.awt.Graphics2D g) {
        layers.get(LayerType.TOP).draw(g);
    }

    public boolean collides(Coord pos) {
        return ((InteractLayer)layers.get(LayerType.INTERACT)).has(TileType.COLLIDE, pos);
    }

    public boolean monsters(Coord pos) {
        return ((InteractLayer)layers.get(LayerType.INTERACT)).has(TileType.MONSTER, pos);
    }

    private class Layer implements Drawable {
        protected Tile[][] tiles;
        public Layer(JsonArray data) {
            for (int r = 0; r < data.size(); r++) {
                JsonArray row = data.get(r).getAsJsonArray();
                for (int c = 0; c < row.size(); c++) {
                    int sprite = row.get(c).getAsInt();
                    tiles[r][c] = new Tile(ResourceLoader.getTileSprite(sprite), new Coord(r, c));
                }
            }
        }
        protected Layer() {};

        public void draw(java.awt.Graphics2D g) {
            for (int r = 0; r < tiles.length; r++) {
                Tile[] row = tiles[r];
                for (int c = 0; c < row.length; c++) {
                    Tile tile = row[c];
                    tile.draw(g);
                }
            }
        }
    }

    private class InteractLayer extends Layer {
        private static java.util.Map<TileType, Set<Integer>> specialSprites = java.util.Map.of(TileType.COLLIDE, Set.of(69));
        private java.util.Map<TileType, Set<Coord>> specials = java.util.Map.of(
            TileType.COLLIDE, new HashSet<>(),
            TileType.MONSTER, new HashSet<>()
        );
        public InteractLayer(JsonArray data) {
            for (int r = 0; r < data.size(); r++) {
                JsonArray row = data.get(r).getAsJsonArray();
                for (int c = 0; c < row.size(); c++) {
                    int sprite = row.get(c).getAsInt();
                    TileType type = TileType.NORMAL;
                    for (java.util.Map.Entry<TileType, Set<Integer>> typesprites : specialSprites.entrySet()) {
                        if (typesprites.getValue().contains(sprite)) {
                            type = typesprites.getKey();
                            break;
                        }
                    }
                    Tile tile = new Tile(ResourceLoader.getTileSprite(sprite), new Coord(r, c), type);
                    tiles[r][c] = tile;
                    if (!tile.type().equals(TileType.NORMAL)) this.specials.get(tile.type()).add(tile.coord());
                }
            }
        }

        public boolean has(TileType type, Coord coord) {
            return specials.get(type).contains(coord);
        }
    }
}
