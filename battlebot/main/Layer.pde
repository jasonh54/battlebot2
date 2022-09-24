class Layer {
  int colsize;
  int rowsize;
  Maps parent;

  //variables for the movement
  final int tileh = 16;
  final int tilew = 16;
  final int tilehh = tileh/2;
  final int tileww = tilew/2;
  char currentKey = ' ';
  int framecounter = 0;

  //variables for the layer code
  private int[][] tileArray;
  private Tile [][] layerTiles;
  boolean lock = false;

  //arraylists for types of tiles within a layer
  ArrayList<Tile> collideTiles = new ArrayList<Tile>();
  ArrayList<Portal> portalTiles = new ArrayList<Portal>();
  ArrayList<Grass> grassTiles = new ArrayList<Grass>();

  //constructor
  public Layer() {
  
  }
  
  void draw() {
    //loops through all tiles and draws, skipping transparent tiles because lag
    for (int i = 0; i < rowsize; i++) {
      for (int k = 0; k < colsize; k++) {
        if(tileArray[i][k] != 486){
          layerTiles[i][k].draw();
        }  
      }
    }
  }
  
  Tile getTile(int lx, int ly){
    return layerTiles[lx][ly];
  }
  
  //layer generation
  void generateBaseLayer(int[][] tileArray) {
    //prepping the tile array for use
    this.tileArray = tileArray;
    rowsize = tileArray.length;
    colsize = tileArray[0].length;
    //layerTiles logs all tiles in the layer as a 2d array
    layerTiles = new Tile[rowsize][colsize];
    //navigating the rows
    for (int row = 0; row < rowsize; row++) {
      //navigating the columns
      for(int col = 0; col < colsize; col++) {
        //skips transparent tiles
        if(tileArray[row][col] != 486) {
          //creates a new tile and assigns values
          Integer tileid = tileArray[row][col];
          Location loc = new Location(tilew * col + tileww, tileh * row + tilehh);
          Tile current;
          if (collidableTiles.contains(tileid)) {
            current = new Tile(tileid, loc, true);
            collideTiles.add(current);
          } else if (specialTiles.get(TileType.ENEMY).contains(tileid)) {
            current = new Grass(tileid, loc);
            grassTiles.add((Grass)current);
          }
          //else if (specialTiles.get(TileType.PORTAL).contains(tileid)) {
          //  current = new Portal();
          //  portalTiles.add((Portal)current);
          //} 
          else {
            current = new Tile(tileid, loc, false);
          }
          //saves the tile to the layerTiles and moves on
          layerTiles[row][col] = current;
        }
      }
    }
  }

  //generic check for whether a tile matches a type
  boolean tileTypeCheck (int[] spriteArray, int[][] arrayoftiles, int row, int col) {
    return binarySearch(spriteArray, arrayoftiles[row][col], 0, spriteArray.length - 1);
  }
  
  //ALWAYS set min = 0 and max = [array].length - 1
  //for sorting tiles into types such as collidable, grass, etc
  boolean binarySearch(int[] arr, int goal, int min, int max) {
    int index = (min + max)/2;
    if (min > max) {
      return false;
    }
    if (arr[index] == goal) {
      return true;
    } else if (arr[index] < goal) {
      return binarySearch(arr, goal, index + 1, max);
    } else if (arr[index] > goal) {
      return binarySearch(arr, goal, min, index - 1);
    }
    return false;
  }

  //update loop
  void update() {
  }

  //start a new movement by returning a key
  void newMove(char currentKey) {
    this.currentKey = currentKey;
  }

  //returns the related key for the movement
  char getCurrentKey() {
    return currentKey;
  }
  
  //code for checking overlap w/ archetypical tiles
  int checkOverlap(ArrayList<? extends Tile> array, Player player) {
    int overlapint;
    //loops through each iteration in array
    for (int i = 0; i < array.size(); i++) {
      //if they're overlapping, return an associated string
      if (array.get(i).checkCollisions(player.getLocation()) == true) {
        //returns the index if true, returns -1 if false
        overlapint = i;
        return overlapint;
      }
    }
    overlapint = -1;
    return overlapint;
  }
}
