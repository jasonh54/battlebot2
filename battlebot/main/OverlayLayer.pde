class OverlayLayer extends Layer {
  //constructor
  public OverlayLayer() {
    super();
  }
  
  //void generateItems() { // TODO, make this actually random
  //  if (random){
  //    items[random] = new GroundItem(random,this.getTile(random));
  //  }
  //}
  
  //these funcs each check collision in a different direction
  //public boolean collideLeft(Player player) {
  //  for (int i = 0; i < collidableTiles.size(); i++) {
  //    if (collidableTiles.get(i).y == player.y) {
  //      if (collidableTiles.get(i).x <= player.x - 8 && collidableTiles.get(i).x >= player.x - 32) {
  //        leftcollidetracker = true;
  //        return leftcollidetracker;
  //      }
  //    }
  //  }
  //  return leftcollidetracker;
  //}

  //public boolean collideRight(Player player) {
  //  for (int i = 0; i < collidableTiles.size(); i++) {
  //    if (collidableTiles.get(i).y == player.y) {
  //      if (collidableTiles.get(i).x >= player.x + 8 && collidableTiles.get(i).x <= player.x + 32) {
  //        rightcollidetracker = true;
  //        return rightcollidetracker;
  //      }
  //    }
  //  }
  //  return rightcollidetracker;
  //}

  //public boolean collideDown(Player player) {
  //  for (int i = 0; i < collidableTiles.size(); i++) {
  //    if (collidableTiles.get(i).x == player.x) {
  //      if (collidableTiles.get(i).y >= player.y + 8 && collidableTiles.get(i).y <= player.y + 32) {
  //        downcollidetracker = true;
  //        return downcollidetracker;
  //      }
  //    }
  //  }
  //  return downcollidetracker;
  //}

  //public boolean collideUp(Player player) {
  //  for (int i = 0; i < collidableTiles.size(); i++) {
  //    if (collidableTiles.get(i).x == player.x) {
  //      if (collidableTiles.get(i).y <= player.y - 8 && collidableTiles.get(i).y >= player.y - 32) {
  //        upcollidetracker = true;
  //        return upcollidetracker;
  //      }
  //    }
  //  }
  //  return upcollidetracker;
  //}
}
