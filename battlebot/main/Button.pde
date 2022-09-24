class Button implements Clickable,Drawable {
  //variables :>
  String txt;
  float x;
  float y;
  float h;
  float w;
  String func;
  String buttontype;
  Menu mymenu;
  Monster monster;
  
  //menu button
  public Button(Menu m, float x, float y, float h, float w, String f) { //this, this.x + 50, tempy, buttonh, buttonw, "0"
    this.x = x;
    this.y = y;
    this.h = h;
    this.w = w;
    this.mymenu = m;
    this.buttontype = "default";
    this.func = f;
  }
  //sandwich
  public Button(float x, float y, String f) {
    this.x = x;
    this.y = y;
    this.h = 5;
    this.w = 30;
    this.buttontype = "sandwich";
    this.func = f;
  }
  
  void draw() {
    fill(255,255,255);
    switch (this.buttontype) {
      case "sandwich":
        rect(this.x, this.y, this.w, this.h);
        rect(this.x, this.y + (this.h * 2), this.w, this.h);
        rect(this.x, this.y + (this.h * 4), this.w, this.h);
      break;
      default:
        if (this.func == "botswap"){ // makeshift solution that doesnt work for duplicate monsters. needs fix.
          if (this.monster == null) {
            for (int i = 0;i<player.monsters.size();i++){
              Monster monster = player.monsters.get(i);
              if (monster.id != this.txt) continue;
              this.monster = monster;
              break;
            }
          }
          
          if (player.getSelectedMonster()==this.monster){
            fill(150,230,150);
          }
        }
        rect(this.x, this.y, this.w, this.h);
        fill(0, 0, 0);
        if (this.txt != null) {text(this.txt, this.x + mymenu.buttonw/4, this.y + mymenu.buttonh/2);}
        
        if (this.func == "botswap") {
          color(0,0,200);
          fill(158,0,0);
          rect(this.x+this.w/2.5,this.y+(this.h*0.6)-this.h/4,this.w/2.5,this.h/4);
          fill(0,158,0);
          rect(this.x+this.w/2.5,this.y+(this.h*0.6)-this.h/4,(this.monster.getCHealth()/this.monster.stats.get(Stat.MAXHEALTH))*(this.w/2.5),this.h/4);
        }
        fill(256, 256, 256);
      break;
    }
  }
  
  float[] getDimensions() {
    return new float[]{this.x,this.y,this.w,this.h};
  }
  boolean isClickable() {return true;};
  
  //use whatever function is stored in the f variable
  public void onClick(){
    switch (this.func) {
      case "0":
        warn("Empty function has been run!");
      break;
      case "useitem"://use an item
        if (player.items.containsKey(this.txt.split("x")[0].trim()) && player.items.get(this.txt.split("x")[0].trim()) > 0){ // more than 0 items (check database instead of button)
          player.useItem(this.txt.split("x")[0].trim());
          currentbattle.switchState(BattleStates.AI);
        }
      break;
      case "toggle":// enter/exit menu
        if (GameState.currentState == GameStates.WALKING) {
          GameState.switchState(GameStates.MENU);
        } else if (GameState.currentState == GameStates.MENU) {
          GameState.switchState(GameStates.WALKING);
        }
      break;
      case "return":// return to the actions menu
        currentbattle.switchState(BattleStates.OPTIONS);
      break;
      case "fight": // switch menu to show moves
        currentbattle.switchState(BattleStates.FIGHT);
      break;
      case "item":  // switch menu to show items
        currentbattle.switchState(BattleStates.ITEM);
      break;
      case "bot":   // switch menu to show battlebots
        currentbattle.switchState(BattleStates.BATTLEBOT);
      break;
      case "run":
        currentbattle.switchState(BattleStates.RUN);
      break;
      case "botswap":// switch battlebot
        for (int n = 0; n < player.monsters.size(); n++) {
          //in the botswap func, program will find a monster in testPlayer.monsters with a monster ID that is equal to testPlayer.swapto
          if (player.monsters.get(n).id == this.txt&&player.monsters.get(n).getCHealth()>0) {
            player.selectedmonster = n;
            currentbattle.switchState(BattleStates.OPTIONS);
            return;
          }
        }
      break;
      default:
        if (this.func.startsWith("callmove")){ // execute a move in the battle
          Integer i = Integer.parseInt(this.func.substring(this.func.length()-1));
          currentbattle.doMove(i,i != 0);
        }else{
          warn("Unrecognized ButtonFunction: "+this.func);
        }
    }
  }
}
