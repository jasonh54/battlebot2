package despacito7;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.File;
// import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.google.gson.Gson;
import com.google.gson.JsonObject;



public class App {
    public static final JFrame f = new JFrame("Battlebot");
    public static final DrawingCanvas dc = new DrawingCanvas(f);

    public static final App instance = new App();
    private App() {};

    public static final Gson gson = new Gson();
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private static final ResourceLoader resourceLoader = new ResourceLoader();
    

    public static void main(String[] args) {
        resourceLoader.loadResources();



        instance.init();

        f.setVisible(true);
        f.add(dc);
        f.addKeyListener(dc);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setFocusable(true);
        f.setResizable(false);
        // f.setIconImage(Utils.ICONIMG);
        executor.scheduleAtFixedRate(App::tick, 0, (long) (1000 / Constants.TPS), java.util.concurrent.TimeUnit.MILLISECONDS);
        f.setVisible(true);
        f.requestFocus();
        dc.startDraw();
        f.setEnabled(true);
    }

    public static void render(Graphics2D g2) {
        g2.drawString("Hello World", 100, 100);
    }

    public static void tick() {

    }

    private void init() {
         // TODO: no filestructure!! can't list all
    }

    public JsonObject loadJson(String filename) {
        try (Reader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename))) {
            String text = new BufferedReader(reader).lines().collect(Collectors.joining("\n"));
            return gson.fromJson(text, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public java.awt.Image loadImage(String filename) {
        try {
            return ImageIO.read(getClass().getClassLoader().getResourceAsStream(filename));
        } catch (IOException e) {
            System.out.println("Image Failed To Load: "+filename);
            return null;
        }
    }
}
