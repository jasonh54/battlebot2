class GroundItem {
  //variables
  protected String id;
  private Tile parent;
  private PImage sprite;
  //constructor
  public GroundItem(String id, Tile parent) {
    this.id = id;
    this.parent = parent;
    this.sprite = itemsprites.get(id);
  }
  
  void draw(){
    image(this.sprite,this.parent.location.loc.x,this.parent.location.loc.y,this.sprite.width *2, this.sprite.height*2);
  }
  
  public void update(Player player) {
    //if (this.parent.checkOverlap(player)){
    //  player.addItem(this.id);
    //  dqueue.add(this);
    //}
  }
}
