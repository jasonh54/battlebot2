class Move {
  private String name;
  private HashMap<Stat,Float> stats;
  private MoveType movetype;
  private Type type;
  private Monster parent;
  
  //constructor for everyday use
  public Move(Monster parent, String name) {
    this.name = name;
    this.parent = parent;
    JSONObject basestats = moveDatabase.get(name);
    this.stats = new HashMap<Stat,Float>();
    for (Stat stat : Stat.values()) {
      String sstat = stat.name().toLowerCase();
      this.stats.put(stat,basestats.getFloat(sstat));
    }
    if (this.stats == null) throw new Error("Move '"+name+"' was not in the moves databse.");
    this.type = Type.valueOf(basestats.getString("type").toUpperCase());
    this.movetype = MoveType.valueOf(basestats.getString("movetype").toUpperCase());
  }
  
  void useMove(Monster target){
    float hpmod = this.stats.get(Stat.MAXHEALTH);
    if (hpmod < 0) {hpmod = moveEfficency(target);}
    HashMap<Stat,Float> statmod = ((HashMap<Stat,Float>)this.stats.clone());
    statmod.put(Stat.MAXHEALTH,hpmod);
    target.modStats(statmod);
  }
  
  //various moves - just one for now
  private float moveEfficency(Monster target) {
    //determine values of coefficients: type advantage, type efficiency, random value
    Random r = new Random();
    float ranval = r.nextInt(10) + 90;
    ranval = ranval/100;
    //determine if move hits
    if (r.nextInt(100) < this.stats.get(Stat.ACCURACY)) {
      //calculate total power
      //System.out.printf("Monster Damage: %f | Move Damage: %f | Target Defense: %f",this.parent.stats.getFloat("attack"),(float)this.stats.getInt("health"),target.stats.getFloat("defense"));
      float power = (this.parent.stats.get(Stat.ATTACK) * this.stats.get(Stat.MAXHEALTH))/target.stats.get(Stat.DEFENSE) * ranval * typeEfficency(target);
      return power;
    } else {
      return 0;
    }
  }
  
  private float typeEfficency(Monster target) {
    float moveEfficency = this.type.equals(this.parent.type) ? 1.5 : 1.0;
    float monsterEfficency = efficencies.get(this.type).get(target.type);
    return moveEfficency*monsterEfficency;
  }
}
