package despacito7.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import despacito7.FeatureLoader;
import despacito7.Constants.TileType;
import despacito7.Constants;
import despacito7.detail.*;
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
    private Set<Item.GroundItem> items = new HashSet<>();
    public String id;

    public Map(JsonObject data) {
        id = data.get("id").getAsString();
        System.out.println("*****Beginning map "+id+"*****");
        for (java.util.Map.Entry<String, JsonElement> layerdata : data.get("layers").getAsJsonObject().entrySet()) {
            LayerType layertype = LayerType.valueOf(layerdata.getKey().toUpperCase());
            switch (layertype) {
                case INTERACT:
                    layers.put(layertype, new InteractLayer(layerdata.getValue().getAsJsonArray()));
                break;
                default:
                    layers.put(layertype, new Layer(layerdata.getValue().getAsJsonArray())); //eventually should be a regular layer
            }
        }
        for (JsonElement npcid : data.get("npcs").getAsJsonArray()) {
            npcs.add(FeatureLoader.getNPC(npcid.getAsString()));
        }
        for (JsonElement itemd : data.get("items").getAsJsonArray()) {
            JsonObject itemdata = itemd.getAsJsonObject();
            items.add(new Item.GroundItem(itemdata.get("variant").getAsString(), Coord.ofJson(itemdata.get("location").getAsJsonArray())));
        }
        for (JsonElement portal : data.get("portals").getAsJsonArray()) {
            JsonObject p = portal.getAsJsonObject(); //48-51 are json data retrieval
            String tarmap = p.get("target").getAsJsonObject().get("map").getAsString(); //get map terminus (string)
            JsonArray tarcj = p.get("target").getAsJsonObject().get("loc").getAsJsonArray(); //get coord terminus (jarray)
            Coord tarcoord = new Coord(tarcj.get(0).getAsInt(),tarcj.get(1).getAsInt()); //get coord terminus (coord)
            JsonArray c = p.get("loc").getAsJsonArray(); //get tile coord (jarray)

            Tile copy = layers.get(LayerType.INTERACT).tiles[c.get(0).getAsInt()][c.get(1).getAsInt()]; //exists as a reference to copy
            
            PortalTile newtile = new PortalTile(copy.spnum,copy.coord,copy.type,FeatureLoader.getMap(tarmap),tarcoord); //create portaltile replacement with all same info

            System.out.println("map " + this + " attempting to replace tile with portal tile");
            System.out.println("coords of replacement action are " + copy.coord().getComponents()[0] + "," + copy.coord().getComponents()[1]);
            System.out.println("map # " + this + " attempting a portal swap at coords " + copy.coord().getComponents()[0] + "," + copy.coord().getComponents()[1]);

            layers.get(LayerType.INTERACT).replaceTile(copy.coord(), newtile); //replace tile in layer with new portaltile
            //next two lines should be uncommented once the interactlayer/layer system is functioning again and portals can be added to the portal coord set
            /* layers.get(LayerType.INTERACT).specials.get(TileType.PORTAL).add(copy.coord()); //add portals to set
            System.out.println("new portaltile successfully placed; terminus at " + (PortalTile) layers.get(LayerType.INTERACT).tiles[c.get(0).getAsInt()][c.get(1).getAsInt()].terminus()); //finish test */
        }
    }

    public void draw(java.awt.Graphics2D g) {
        layers.get(LayerType.BASE).draw(g);
        layers.get(LayerType.INTERACT).draw(g);
        items.forEach(item -> item.draw(g));
        npcs.forEach(npc -> npc.draw(g));
    }

    public void postDraw(java.awt.Graphics2D g) {
        layers.get(LayerType.TOP).draw(g);
    }

    public void update() {
        npcs.forEach(npc -> npc.update());
    }

    public boolean collides(Coord pos) {
        return ((InteractLayer)layers.get(LayerType.INTERACT)).has(TileType.COLLIDE, pos);
    }

    public boolean monsters(Coord pos) {
        // System.out.println("Player coord: ");
        // pos.print();

        return ((InteractLayer)layers.get(LayerType.INTERACT)).has(TileType.MONSTER,pos);
    }

    public boolean portals(Coord pos) {
        return ((InteractLayer)layers.get(LayerType.INTERACT)).has(TileType.PORTAL,pos);
    } 

    public PortalTile getPortal(Coord pos){
        return (PortalTile)((InteractLayer)layers.get(LayerType.INTERACT)).get(pos);
    }

    private class Layer implements Drawable {
        protected Tile[][] tiles;
        public Layer(JsonArray data) {
            this.tiles = new Tile[data.size()][];
            for (int r = 0; r < data.size(); r++) {
                JsonArray row = data.get(r).getAsJsonArray();
                this.tiles[r] = new Tile[row.size()];
                for (int c = 0; c < row.size(); c++) {
                    int sprite = row.get(c).getAsInt();
                    //eventually, skip blank tiles
                    tiles[r][c] = new Tile(sprite, new Coord(r, c), TileType.NORMAL);
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

        public void replaceTile(Coord co, PortalTile p) {
            if (tiles[co.getComponents()[0]][co.getComponents()[1]].type != TileType.PORTAL) {
                System.out.println("WARNING: Attempting to replace non-portal tile with portaltile at coords " + co.getComponents()[0] + "," + co.getComponents()[1] + "; original type is " + tiles[co.getComponents()[0]][co.getComponents()[1]].type);
            } else {
                tiles[co.getComponents()[0]][co.getComponents()[1]] = p;
            }
        }
    }

    private class InteractLayer extends Layer {

        private java.util.Map<TileType, Set<Coord>> specials = java.util.Map.of(
            TileType.COLLIDE, new HashSet<>(),
            TileType.MONSTER, new HashSet<>(),
            TileType.PORTAL, new HashSet<>()
        );
        public InteractLayer(JsonArray data) {
            this.tiles = new Tile[data.size()][];
            for (int r = 0; r < data.size(); r++) {
                JsonArray row = data.get(r).getAsJsonArray();
                this.tiles[r] = new Tile[row.size()];
                for (int c = 0; c < row.size(); c++) {
                    int sprite = row.get(c).getAsInt();
                    TileType type = TileType.NORMAL;
                    // for (java.util.Map.Entry<TileType, Set<Integer>> typesprites : specialSprites.entrySet()) {
                    //     if (typesprites.getValue().contains(sprite)) {
                    //         type = typesprites.getKey();
                    //         break;
                    //     }
                    // }]
                    if (Constants.monsterTiles.contains(sprite)){
                        type = TileType.MONSTER;
                    } else if (Constants.collideTiles.contains(sprite)){
                        type = TileType.COLLIDE;
                    } else if (Constants.portalTiles.contains(sprite)) {
                        type = TileType.PORTAL;
                        //portals are only given a type here; they receive further instruction in the map constructor
                    } else {
                        //blank tile -- check if not
                        if (sprite != -1) {
                            System.out.println("WARNING: nonblank NORMAL tile in interactlayer at " + r + "," + c);
                        }
                    }
                    //eventually, this should skip blank tiles
                    Tile tile = new Tile(sprite, new Coord(r, c), type);
                    tiles[r][c] = tile;

                    if (sprite != -1) {

                        this.specials.get(tile.type()).add(tile.coord());
                    }
                }
            }
        }

        public boolean has(TileType type, Coord coord) {
            // coord.print();
            // System.out.println(specials.get(type));
            //Object at parameter is not the same object as the object in the Set
            for(Coord c : specials.get(type)){
                // c.print();
                if(c.compare(coord)){
                    return true;
                }
            }
            return false;
        }

        public Tile get(Coord co){
            return tiles[co.getComponents()[0]][co.getComponents()[1]];
        }

        public Set<Coord> getS(TileType type){
            return specials.get(type);
        }
    }
}