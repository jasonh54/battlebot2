enum AnimationStates {
  START,
  PLAYING,
  STOP
}
 

class Spritesheet{
  private PImage[] spritesheet;
  HashMap<String, PImage[]> animations = new HashMap<String, PImage[]>();
  int currentFrame = 0;
  float x, y, w, h;
  Timer time = new Timer();
  int interval = 0;
  AnimationStates currentState = AnimationStates.START;
  public Spritesheet(PImage[] images, int a){
    x = 0;
    y = 0;
    w = 0;
    h = 0;
    spritesheet = images;
    interval = a;
    time.setTimeInterval(interval);
  }
  public Spritesheet(PImage image, int a){
    x = 0;
    y = 0;
    w = 0;
    h = 0;
    int frameNum = image.width/16;
    spritesheet = new PImage[frameNum];
    for(int i = 0; i < frameNum; i++){
      spritesheet[i] = image.get(i*16, 0, 16, 16);
    }
    interval = a;
    time.setTimeInterval(interval);
  }
  public void setxywh(float x, float y, float w, float h){
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
  public void createAnimation(String name, int[] frameNum){
    PImage[] frames = new PImage[frameNum.length];
    for(int i = 0; i < frames.length; i++){
      frames[i] = spritesheet[frameNum[i]];
    }
    animations.put(name, frames);
  }
  public void play(String name){
    switch(currentState){
      case START:
        currentFrame = 0;
        time.timeStampNow();
        image(animations.get(name)[currentFrame%animations.get(name).length], x, y, w, h);
        currentState = AnimationStates.PLAYING;
        break;
      case PLAYING:
        image(animations.get(name)[currentFrame%animations.get(name).length], x, y, w, h);
        if(time.intervals()){
          currentFrame++;
          //if(currentFrame == animations.get(name).length){
          //  currentFrame = 0;
          //}
        }
        break;
      case STOP:
        break;
    } 
    
  }
  public boolean finished(String name){
    if(currentFrame >= animations.get(name).length){
      currentState = AnimationStates.START;
      return true;
    } else {
      return false;
    }
  }
}
