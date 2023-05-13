package despacito7.map;

import despacito7.Constants.TileType;
import despacito7.util.Coord;
import despacito7.util.Pair;

public class PortalTile extends Tile {
    private Pair<Map,Coord> terminus;
    public PortalTile(int spritenum, Coord startcoord, TileType t, Map ta, Coord ec) {
        super(spritenum, startcoord, t);
        terminus = new Pair<Map,Coord>(ta, ec);
    }

    public Pair<Map,Coord> terminus() {
        return terminus;
    }
}