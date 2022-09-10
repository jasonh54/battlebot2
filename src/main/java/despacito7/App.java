package despacito7;

import java.awt.Graphics2D;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.JFrame;

import com.google.gson.Gson;


public class App {
    public static final JFrame f = new JFrame("Battlebot");
    public static final DrawingCanvas dc = new DrawingCanvas(f);

    public static final Gson gson = new Gson();
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private static final ResourceLoader resourceLoader = new ResourceLoader();

    private static final FeatureLoader featureLoader = new FeatureLoader();

    public static void main(String[] args) {
        resourceLoader.loadResources();


        f.setVisible(true);
        f.add(dc);
        f.addKeyListener(dc);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setFocusable(true);
        f.setResizable(false);
        // f.setIconImage(Utils.ICONIMG);

        featureLoader.loadFeatures();

        executor.scheduleAtFixedRate(App::tick, 0, (long) (1000 / Constants.TPS), java.util.concurrent.TimeUnit.MILLISECONDS);
        f.setVisible(true);
        f.requestFocus();
        dc.startDraw();
        f.setEnabled(true);
    }


    public static void render(Graphics2D g) {
        g.drawString("Hello World", 100, 100);
        // g2.drawImage(instance.loadImage("sprites/items/PotionArmor.png"), 50, 50, null);

    }

    public static void tick() {

    }
}
