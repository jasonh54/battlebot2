package despacito7.map;

import java.util.HashMap;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import despacito7.App;
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
    }

    public void draw(java.awt.Graphics2D g) {
        layers.get(LayerType.BASE).draw(g);
        layers.get(LayerType.INTERACT).draw(g);
    }

    public void postDraw(java.awt.Graphics2D g) {
        layers.get(LayerType.TOP).draw(g);
    }

    public boolean collides(Coord pos) {
        return ((InteractLayer)layers.get(LayerType.INTERACT)).collides(pos);
    }

    private class Layer implements Drawable {
        protected Tile[][] tiles;
        public Layer(JsonArray data) {
            for (int r = 0; r < data.size(); r++) {
                JsonArray row = data.get(r).getAsJsonArray();
                for (int c = 0; c < row.size(); c++) {
                    int sprite = row.get(c).getAsInt();
                    tiles[r][c] = new Tile(App.instance.loadImage(String.valueOf(sprite)), new Coord(r, c));
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
        private Set<Coord> collidables;
        public InteractLayer(JsonArray data) {
            for (int r = 0; r < data.size(); r++) {
                JsonArray row = data.get(r).getAsJsonArray();
                for (int c = 0; c < row.size(); c++) {
                    int sprite = row.get(c).getAsInt();
                    Tile tile = new Tile(App.instance.loadImage(String.valueOf(sprite)), new Coord(r, c));
                    tiles[r][c] = tile;
                    if (tile.isCollidable()) collidables.add(tile.coord());
                }
            }
        }

        public boolean collides(Coord coord) {
            return collidables.contains(coord);
        }
    }
}
