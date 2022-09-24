class Maps {
  String id;
  Maps nextmap = null;
  //layer objects - 5 to each map
  OverlayLayer collidelayer = new OverlayLayer();
  Layer baselayer = new Layer();
  Layer coverlayer = new Layer();
  Layer portallayer = new Layer();
  Layer toplayer = new Layer();
  
  private ArrayList<GroundItem> items;
  //ABCDE
  //items = new ArrayList<GroundItem>();
  Layer[] layerset = {collidelayer, portallayer, baselayer, coverlayer, toplayer};

  HashMap<Tile, Maps> portalPairs = new HashMap<Tile, Maps>();
  
  //constructor for most maps
  public Maps(String id) {
    //name the self
    this.id = id;
    //retrieve the map to copy from the database
    JSONObject thismap = mapsDatabase.get(id);
    generateAllLayers(loadTileArray(thismap.getJSONArray("collide")),loadTileArray(thismap.getJSONArray("portal")),loadTileArray(thismap.getJSONArray("base")),loadTileArray(thismap.getJSONArray("cover")),loadTileArray(thismap.getJSONArray("top")));
    pairPortals();
    imprintLayers();
    //upon preparation, add self to a masterlist
    maps.put(this.id,this);
  }
  
  //constructor for currentmap, which starts blank
  public Maps() {
    
  }
  
  //zipper the connections array with the portaltiles list into a singular, navigable hashmap
  void pairPortals() {
    //JSONArray connections = mapsDatabase.get(id).getJSONArray("connections");
    //for (int i = 0; i < connections.size(); i++) {
    //  portalPairs.put(portallayer.portalTiles.get(i),maps.get(connections.get(i)));
    //}
  }
  
  //allow the layers to imprint to this map
  void imprintLayers() {
    for (int i = 0; i < layerset.length; i++) {
      layerset[i].parent = this;
    }
  }
  
  Maps getPortalConnection(Tile tile) {
    return portalPairs.get(tile);
  }
  
  public void addItem(GroundItem gi) {
    //ABCDE
    //this.items.add(gi);
  }
  public void removeItem(GroundItem gi) {
    this.items.remove(gi);
  }
  
  void generateAllLayers(int[][] collide, int[][] portal, int[][] base, int[][] cover, int[][] top) {
    collidelayer.generateBaseLayer(collide);
    portallayer.generateBaseLayer(portal);
    baselayer.generateBaseLayer(base);
    coverlayer.generateBaseLayer(cover);
    toplayer.generateBaseLayer(top);
  }
  
  //default does not update toplayer as it must update after the player
  void update() {
    nextmap = null;
    baselayer.update();
    coverlayer.update();
    collidelayer.update();
    portallayer.update();
    //ABCDE
    /* for (GroundItem i : this.items) {
      i.update(testPlayer);
      i.draw();
    } */
  }
  
  void updateLast() {
    toplayer.update();
    int checkit = portallayer.checkOverlap(portallayer.portalTiles, player);
        //checking special tile-related conditions and activating events if they are met
        if (checkit >= 0) {
          //have a variable save portalTiles.get(overlapint);
          nextmap = this.portalPairs.get(portallayer.portalTiles.get(checkit));
          //figure out what map is associated with that tile and generate it
        }
  }
  
  void totalDraw() {
    baselayer.draw();
    coverlayer.draw();
    collidelayer.draw();
    toplayer.draw();
  }

  //given a 2d JSONArray, copy contents and return as a 2d intarray
  //note: this works for ONE LAYER; does not process an entire map
  int[][] loadTileArray(JSONArray ogarr) {
    //int[size of outer JSONArray][size of first JSONArray within the outer JSONArray]
    int[][] arr = new int[ogarr.size()][ogarr.getJSONArray(0).size()];
    //for each JSONArray within the original JSONArray
    for (int i = 0; i < ogarr.size(); i++) {
      //create a dummy int[] identical to an int-only version of the current JSONArray within the outer JSONArray (ogarr)
      int[]  dummy = ogarr.getJSONArray(i).getIntArray();
      //set the current row to be equal to the current dummy array
      for (int k = 0; k < dummy.length; k++) {
        arr[i][k] = dummy[k];
      }
    }
    return arr;
  }
  
  public boolean collides(Location l) {
    for (Tile tile : this.collidelayer.collideTiles) {
      if (tile.checkCollisions(l)) return true;
    }
    return false;
  }
}
