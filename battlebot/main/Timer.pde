class Timer{
  private int timeInterval;
  private int timeStamp;
  public Timer(){
    timeInterval = 0;
    timeStamp = 0;
  }
  public void setTimeInterval(int a){
    timeInterval = a;
  }
  public void timeStampNow(){
    timeStamp = millis();
  }
  public boolean intervals(){
    if(millis() > timeStamp + timeInterval){
      timeStamp = millis();
      return true;
    } else {
      return false;
    }
  }
}
