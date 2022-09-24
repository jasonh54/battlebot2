class Monster {
  //variables
  private String id;
  private Type type;
  private int level;
  private JSONObject basestats;
  private HashMap<Stat,Float> stats;
  private float chealth;
  private Move[] moveset = new Move[4];
  
  private PImage image;
  final int h = 16;
  final int w = 16;
  float x = 400;
  float y = 400;
  Spritesheet animations;
  Timer time;
  float speedX = 0;
  float speedY = 0;
  int moveCount = 0;
  
  //constructor
  public Monster(String id, float x, float y) {
    JSONObject data = monsterDatabase.get(id);

    this.image = monstersprites.get(data.getString("image"));
    this.id = id;
    this.level = 1;
    this.type = Type.valueOf(data.getString("type").toUpperCase());
    this.basestats = JSONCopy(data).setFloat("agility",1.0f);
    this.stats = new HashMap<Stat,Float>();
    for (Stat stat : Stat.values()) {
      String sstat = stat.name().toLowerCase();
      if (!this.basestats.isNull(sstat)) {
        this.stats.put(stat,this.basestats.getFloat(sstat));
      }
    }
    this.chealth = this.stats.get(Stat.MAXHEALTH);
    for (int i = 0; i<this.moveset.length; i++) {
      this.moveset[i] = new Move(this,monsterDatabase.get(id).getJSONArray("moves").getString(i));
    }
    
    animations = new Spritesheet(this.image, 120);
    animations.setxywh(x, y, w, h);
    int frameNum = image.width/16;
    int[] frameNums = new int[frameNum];
    for(int i = 0; i < frameNum; i++){frameNums[i] = i;}
    animations.createAnimation("default", frameNums);
    time = new Timer();
    
    this.x = x;
    this.y = y;
  }
  
  public float getCHealth() {
    return this.chealth;
  }

  public void display(){
    color(0,0,200);
    fill(250, 229, 127);
    rect(x-60,y-100,170,60);
    fill(158,0,0);
    rect(x-55,y-75,150,25);
    fill(0,158,0);
    rect(x-55,y-75,(this.chealth/this.stats.get(Stat.MAXHEALTH))*150,25);
    fill(0,0,0);
    textAlign(LEFT, UP);
    text(id,x-54,y-80);
    textAlign(CENTER, CENTER);
    text(this.chealth + "/" + this.stats.get(Stat.MAXHEALTH), x+20, y-62);
    //pop();
    //if(animations.animationTimer.countDownUntil(animations.stoploop)){
    //  animations.changeDisplay(true);
    //}
    animations.play("default");
  }
  
  public void addHp(float hp){
    this.chealth += constrain(hp,-this.chealth,this.stats.get(Stat.MAXHEALTH));
  }
  public void modStats(HashMap<Stat,Float> mod){
    if (mod.containsKey(Stat.MAXHEALTH)) addHp(mod.get(Stat.MAXHEALTH));
    if (mod.get(Stat.ATTACK)  != 1.0f) this.stats.put(Stat.ATTACK ,this.stats.get(Stat.ATTACK) *mod.get(Stat.ATTACK) );
    if (mod.get(Stat.SPEED)   != 1.0f) this.stats.put(Stat.SPEED  ,this.stats.get(Stat.SPEED)  *mod.get(Stat.SPEED)  );
    if (mod.get(Stat.DEFENSE) != 1.0f) this.stats.put(Stat.DEFENSE,this.stats.get(Stat.DEFENSE)*mod.get(Stat.DEFENSE));
    if (mod.get(Stat.AGILITY) != 1.0f) this.stats.put(Stat.AGILITY,this.stats.get(Stat.AGILITY)*mod.get(Stat.AGILITY));
    //System.out.printf("Modifying Stats of %s:\nHP: +%f | Atk: x%f | Spd: x%f | Def: x%f | Agl: x%f\n",this.id,mod.getFloat("health"),mod.getFloat("attack"),mod.getFloat("speed"),mod.getFloat("defense"),mod.getFloat("agility"));
  }

  public void startAnimation(MoveType type) {
    switch (type) { // start the animation
      case DEFEND:
        this.defendStart();
      break;
      case HEAL:
        this.healStart();
      break;
      case DODGE:
        this.dodgeStart();
      break;
      default:
        warn("Please register attacks with the overloaded method.");
    }
  }
  public void startAnimation(MoveType type, Monster target) {
    switch (type) { // start the animation
      case ATTACK:
        this.attackStart(target); 
      break;
      case DEFEND:
        this.defendStart();
      break;
      case HEAL:
        this.healStart();
      break;
      case DODGE:
        this.dodgeStart();
      break;
    }
  }
  
  private void attackStart(Monster target){
    time.timeStampNow();
    speedX = target.x - this.x;
    speedY = target.y - this.y;
    time.setTimeInterval(75);
  }
  
  public boolean attackAnimation(Monster target){
    if(time.intervals()){
      animations.x += speedX/10;
      animations.y += speedY/10;
      if(speedX < 0 && speedY < 0){
        if(animations.x <= target.x && animations.y <= target.y){
          speedX *= -1;
          speedY *= -1;
        }
        if(animations.x <= this.x && animations.y <= this.y){
          animations.x = this.x;
          animations.y = this.y;
          speedX = 0;
          speedY = 0;
          return true;
        }
      } else {
        if(animations.x >= target.x && animations.y >= target.y){
          speedX *= -1;
          speedY *= -1;
        }
        if(animations.x >= this.x && animations.y >= this.y){
          animations.x = this.x;
          animations.y = this.y;
          speedX = 0;
          speedY = 0;
          return true;
        }
      }
    }
    return false;
  }
  
  private void healStart(){
    time.timeStampNow();
    speedX = 4;
    time.setTimeInterval(100);
    moveCount = 0;
  }
  
  public boolean healAnimation(){
    animations.x += speedX;
    if(time.intervals()){
      speedX *= -1;
      if(moveCount == 6){
        animations.x = this.x;
        return true; 
      }
      moveCount++;
    }
    return false;
  }
  
  private void defendStart(){
    time.timeStampNow();
    speedY = 4;
    time.setTimeInterval(300);
    moveCount = 0;
  }
  
  public boolean defendAnimation(){
    animations.y += speedY;
    if(time.intervals()){
      if(moveCount == 3){
        animations.y = this.y;
        return true;
      }
      speedY *= -1;
      moveCount++;
    }
    return false;
  }
  
  private void dodgeStart(){
    time.timeStampNow();
    speedX = 4;
    speedY = 4;
    time.setTimeInterval(150);
    moveCount = 0;
  }
  
  public boolean dodgeAnimation(){
    animations.x += speedX;
    animations.y += speedY;
    if(time.intervals()){
      if(moveCount == 3){
        speedX = -4;
        speedY = -4;
      } else if(moveCount == 5){
        animations.x = this.x;
        animations.y = this.y;
        return true;
      } else {
        speedX = 0;
        speedY = 0;
      }
      moveCount++;
    }
    return false;
  }
}
