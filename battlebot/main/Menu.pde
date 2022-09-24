class Menu implements Clickable,Drawable {
  //variables
  float x;
  float y;
  int buttonh;
  int buttonw;
  int spacing;
  ArrayList<Button> buttons = new ArrayList<Button>();
  
  //constructor
  public Menu(float x, float y, int menulength, int bh, int bw, int s) {
    this.x = x;
    this.y = y;
    this.buttonh = bh;
    this.buttonw = bw;
    this.spacing = s;
    for (int i = 0; i < menulength; i++) {
      buttons.add(new Button(this, x, y+((bh+s)*i), bh, bw, "0"));
    }
  }
  
  void draw() {
    pushMatrix();
    translate(0,0);
    for (Button button : this.buttons) {
      updateDrawables(button);
    }
    popMatrix();
  }
  boolean isClickable() {return true;};
  float[] getDimensions() {
    return new float[]{this.x,this.y,this.buttonw,(this.buttonh+this.spacing)*(this.buttons.size()+1)};
  }
  void onClick(){
    // doesnt work for some reason updateClickables(this.buttons);
    for (Clickable button : this.buttons){
      updateClickables(button);
    }
  }
}
