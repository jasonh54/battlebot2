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
        int menuX = 200;
        int menuY = 200;
        Menu.battleMenu.addButton(Menu.generateButton(menuX, menuY, 100, 20, "Attack", new Menu.ButtonCallback(){
            public void activate(){
                currentState = BattleStates.SELECTMOVE;
            }
        }));
        Menu.battleMenu.addButton(Menu.generateButton(menuX, menuY+22, 100, 20, "Pick Item", new Menu.ButtonCallback(){
            public void activate(){
                currentState = BattleStates.SELECTITEM;
            }
        }));
        Menu.battleMenu.addButton(Menu.generateButton(menuX, menuY+44, 100, 20, "Switch Monster", new Menu.ButtonCallback(){
            public void activate(){
                currentState = BattleStates.SELECTMONSTER;
            }
        }));
        Menu.battleMenu.addButton(Menu.generateButton(menuX, menuY+66, 100, 20, "Run Away", new Menu.ButtonCallback(){
            public void activate(){
                currentState = BattleStates.END;
            }
        }));
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
        Menu.battleMenu.tick();
        switch(currentState){
            case ENTER:
            break;
            case YOURTURN:
            break;
            case ENEMYTURN:
            break;
            case SELECTITEM:
                System.out.println("pick item works");
            break;
            case SELECTMONSTER: 
                System.out.println("switch monster works");
            break;
            case SELECTMOVE: 
                System.out.println("attack works");
            break;
            case END:
                System.out.println("run away works");
            break;
       } 
    }
}
