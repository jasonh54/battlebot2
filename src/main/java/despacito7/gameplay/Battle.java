package despacito7.gameplay;

import despacito7.FeatureLoader;
import despacito7.detail.*;
import despacito7.menu.Menu;

import java.awt.Graphics2D;

public class Battle {
    private Monster currentEnemy, currentMonster;
    private NPC currentNPC;

    private enum BattleStates {
        ENTER, YOURTURN, ENEMYTURN, 
        SELECTITEM, SELECTMONSTER, SELECTMOVE, END
    }

    private BattleStates currentState = BattleStates.ENTER;

    public Battle(NPC npc){
        currentNPC = npc;
        currentEnemy = currentNPC.getMonster(0);
    }

    public Battle(Monster monster){
        currentEnemy = monster;
    }

    public void draw(Graphics2D g){
        currentMonster.draw(g);
        if(currentNPC != null) {
            currentNPC.draw(g);
            currentEnemy.draw(g);
        }
    }
    
    public void tick(){
       switch(currentState){
        case ENTER:
        break;
        case YOURTURN:
        break;
        case ENEMYTURN:
        break;
        case SELECTITEM:
        break;
        case SELECTMONSTER: 
        break;
        case SELECTMOVE: 
        break;
        case END:
        break;
       } 
    }
}
