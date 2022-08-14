package despacito7.map;

import java.util.Set;

import despacito7.util.Coord;
import despacito7.util.Drawable;

public class Map implements Drawable {
    private Layer[] layers = new Layer[3]; // Base, Interact, Top

    public void draw(java.awt.Graphics2D g) {
        layers[0].draw(g);
        layers[1].draw(g);
    }

    public void postDraw(java.awt.Graphics2D g) {
        layers[2].draw(g);
    }

    private class Layer implements Drawable {
        protected Tile[][] tiles;
        public Layer(int[][] data) {
            for (int r = 0; r < data.length; r++) {
                int[] row = data[r];
                for (int c = 0; c < row.length; c++) {
                    int sprite = row[c];
                    tiles[r][c] = new Tile(sprite, new Coord(r, c));
                }
            }
        }

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
        public InteractLayer(int[][] data) {super(data);}

        public boolean collides(Coord coord) {
            return collidables.contains(coord);
        }
    }
}
