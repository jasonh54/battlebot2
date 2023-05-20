package despacito7.map;

import despacito7.Constants.TileType;
import despacito7.util.Coord;
import despacito7.util.Pair;

public class PortalTile extends Tile {
    private Pair<String,Coord> terminus;
    public PortalTile(int spritenum, Coord startcoord, TileType t, String name, Coord ec) {
        super(spritenum, startcoord, t);
        terminus = new Pair<String,Coord>(name, ec);
    }

    public Pair<String,Coord> terminus() {
        return terminus;
    }
}