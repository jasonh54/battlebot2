package despacito7.gameplay;

import despacito7.App;
import despacito7.Constants;
import despacito7.FeatureLoader;
import despacito7.detail.*;
import despacito7.menu.Menu;

import java.awt.Graphics2D;
import java.awt.Color;

public class Battle {
    private Monster currentMonster;
    private Monster playerMonster;
    private NPC currentNPC;

    private enum BattleStates {
        ENTER, YOURTURN, ENEMYTURN, 
        SELECTITEM, SELECTMONSTER, SELECTMOVE, END
    }

    private BattleStates currentState = BattleStates.ENTER;

    public Battle(NPC npc){
        currentNPC = npc;
        currentMonster = currentNPC.getMonster(0);
        currentMonster.setCoord(20, 10);
        playerMonster = FeatureLoader.player.getMonster(0);
        playerMonster.setCoord(10, 20);
        createMenu();
    }

    public Battle(Monster monster){
        currentMonster = monster;
        currentMonster.setCoord(20, 10);
        playerMonster = FeatureLoader.player.getMonster(0);
        playerMonster.setCoord(10, 20);
        createMenu();
    }

    public void createMenu(){
        Menu.battleMenu.addButton(Menu.generateButton(0, 0, 100, 20, "Attack"));
        Menu.battleMenu.addButton(Menu.generateButton(0, 22, 100, 20, "Pick Item"));
        Menu.battleMenu.addButton(Menu.generateButton(0, 44, 100, 20, "Switch Monster"));
        Menu.battleMenu.addButton(Menu.generateButton(0, 66, 100, 20, "Run Away"));
    }

    public void draw(Graphics2D g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, App.width*Constants.tilesize, App.height*Constants.tilesize);
        playerMonster.draw(g);
        if(currentNPC != null) {
            currentNPC.draw(g);
        }
        currentMonster.draw(g);
        Menu.battleMenu.draw(g);
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
