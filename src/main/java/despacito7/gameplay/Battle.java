package despacito7.gameplay;

import despacito7.FeatureLoader;
import despacito7.detail.*;
import despacito7.menu.Menu;

import java.awt.Graphics2D;

public class Battle {
    private Monster currentEnemy, currentMonster;
    private NPC currentNPC;

    private enum BattleStates {
        YOURTURN, ENEMYTURN, 
        FIRSTMOVE, SECONDMOVE, SELECTMONSTER, END
    }

    private BattleStates currentState = BattleStates.YOURTURN;

    public Battle(NPC npc){
        currentNPC = npc;
    }
    public Battle(Monster monster){
        currentMonster = monster;
    }
    public void draw(Graphics2D g){
        currentMonster.draw(g);
        if(currentNPC != null) {
            currentNPC.draw(g);
        }
    }
    public void tick(){
        
    }
}
