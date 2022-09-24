enum GameStates{
  WALKING,
  COMBAT,
  MENU,
  LOSE
}

public static class GameState{
  public static GameStates currentState = GameStates.WALKING;
  //public static boolean lock = false;
  public static void switchState(GameStates s) {
    GameState.currentState = s;
  }
}
