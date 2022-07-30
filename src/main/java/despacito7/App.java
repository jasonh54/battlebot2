package despacito7;

import java.awt.Graphics2D;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.JFrame;

public class App {
    public static final JFrame f = new JFrame("Battlebot");
    public static final DrawingCanvas dc = new DrawingCanvas(f);

    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        f.setVisible(true);
        f.add(dc);
        f.addKeyListener(dc);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setFocusable(true);
        // f.setIconImage(Utils.ICONIMG);
        f.setResizable(false);

        executor.scheduleAtFixedRate(App::tick, 0, (long) (1000 / Constants.TPS), java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    public static void render(Graphics2D g2) {
        g2.drawString("Hello World", 100, 100);
    }

    public static void tick() {

    }
}
