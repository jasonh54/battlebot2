package despacito7;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.concurrent.Executors;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.JFrame;

import com.google.gson.Gson;

import despacito7.menu.Menu;
import despacito7.Constants.GameState;
import despacito7.detail.Monster;
import despacito7.gameplay.Battle;

public class App {
    public static final JFrame f = new JFrame("Battlebot");
    public static final DrawingCanvas dc = new DrawingCanvas(f);
    public static int width, height;

    public static final Gson gson = new Gson();
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private static final ResourceLoader resourceLoader = new ResourceLoader();
    private static final FeatureLoader featureLoader = new FeatureLoader();

    //game objects
    public static String currentmap = "citymap";
    static Monster currentMonster;
    public static GameState currentGameState = GameState.WORLD;
    public static Battle currentBattle;
    public static void main(String[] args) {
        resourceLoader.load();

        f.setVisible(true);
        f.add(dc);
        f.addKeyListener(dc);
        f.addMouseListener(dc);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setFocusable(true);
        f.setResizable(false);
        width = f.getWidth();
        height = f.getHeight();
        // f.setIconImage(Utils.ICONIMG);

        featureLoader.load();
        currentMonster = featureLoader.getMonster("Air");
        featureLoader.player.addMonster(featureLoader.getMonster("Ball"));

        featureLoader.player.addItem(featureLoader.getItem("PotionHealth"));
        featureLoader.player.addItem(featureLoader.getItem("PotionDefense"));
        featureLoader.player.addItem(featureLoader.getItem("PotionAgility"));
        // currentBattle = new Battle(App.currentMonster);
        // currentGameState = GameState.BATTLE;

        executor.scheduleAtFixedRate(App::tick, 0, (long) (1000 / Constants.TPS), java.util.concurrent.TimeUnit.MILLISECONDS);
        f.setVisible(true);
        f.requestFocus();
        dc.startDraw();
        f.setEnabled(true);
    }

    public static boolean isLoaded() {
        return featureLoader.isLoaded() && resourceLoader.isLoaded();
    }

    public static void render(Graphics2D g) {
        if (!isLoaded()) return;
        Point p = FeatureLoader.player.getRenderPos();
        g.scale(2, 2);

        switch (currentGameState) {
            case WORLD:
                g.translate(-p.x+f.getWidth()/4f-Constants.tilesize/2f, -p.y+f.getHeight()/4f-Constants.tilesize/2f);
                FeatureLoader.getMap(currentmap).draw(g);
                FeatureLoader.player.draw(g);
                currentMonster.draw(g);
                // player movement enums conflicting with monsters animation as monster does not require movement enums
        
                FeatureLoader.getMap(currentmap).postDraw(g);
        
                Menu.cornerMenu.draw(g);
            break;
            case BATTLE:
                // g.clearRect(0, 0, f.getWidth(), f.getHeight());
                g.translate(0, 0);
                currentBattle.draw(g);
            break;
            case MENU:
            break;
            case TALK:
            break;
        }


    }

    public static void tick() {
        switch (currentGameState) {
            case WORLD:
                // Menu.cornerMenu.update();
                FeatureLoader.getMap(currentmap).update();
                FeatureLoader.player.update();
            break;
            case BATTLE:
                currentBattle.tick();
            break;
            case MENU:
            break;
            case TALK:
            break;
        }
        Constants.leftMouseClick = false;
    }

    public static void onKey(char keyCode) {
        switch (keyCode) {
            case 'm' -> Menu.cornerMenu.expand();
        }
        FeatureLoader.player.onKey(keyCode);
    }

    // public void click(){
    //     f.addMouseListener(new MouseAdapter() {
    //         public void mouseClicked(MouseEvent m) {
    //             if (m.getButton() == MouseEvent.BUTTON1) {
    //                 System.out.println("Button 1 clicked...");
    //                 Constants.leftMouseClick = true;
    //             } else {
    //                 System.out.println("No Button...");
    //                 Constants.leftMouseClick = false;
    //             }
    //         }
    //     });
    // }
}
