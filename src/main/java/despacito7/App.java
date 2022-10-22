package despacito7;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.JFrame;

import com.google.gson.Gson;

import despacito7.menu.Menu;

public class App {
    public static final JFrame f = new JFrame("Battlebot");
    public static final DrawingCanvas dc = new DrawingCanvas(f);

    public static final Gson gson = new Gson();
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private static final ResourceLoader resourceLoader = new ResourceLoader();
    private static final FeatureLoader featureLoader = new FeatureLoader();

    public static void main(String[] args) {
        resourceLoader.load();

        f.setVisible(true);
        f.add(dc);
        f.addKeyListener(dc);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setFocusable(true);
        f.setResizable(false);
        // f.setIconImage(Utils.ICONIMG);

        featureLoader.load();

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
        g.setTransform(AffineTransform.getScaleInstance(2, 2));
        FeatureLoader.getMap("citymap").draw(g);

        FeatureLoader.getMap("citymap").postDraw(g);

        FeatureLoader.player.draw(g);
        FeatureLoader.player.play("downWalk",60);

        Menu.cornerMenu.draw(g);

    }

    public static void tick() {
        Menu.cornerMenu.update();
    }

    public static void onKey(char keyCode) {
        switch (keyCode) {
            case 'm' -> Menu.cornerMenu.expand();
        }
    }
}
