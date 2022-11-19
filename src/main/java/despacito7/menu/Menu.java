package despacito7.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;

import despacito7.Constants;
import despacito7.DrawingCanvas;
import despacito7.util.Drawable;
import despacito7.util.Utils;

import java.awt.Image;
import java.awt.Point;

public abstract class Menu {
    public static final Menu cornerMenu = new RotaryMenu(new Point(0, 0));
    protected Set<Button> buttons;
    protected boolean expanded;

    public void expand() {
        this.expanded = !this.expanded;
    }

    public abstract void update();

    public void draw(Graphics2D g) {
        for (Button b : this.buttons) b.draw(g);
    }
    public static interface Button extends Drawable {
        public void draw(Graphics2D g);
    }
}

class DialogueMenu extends Menu implements Drawable {
    private int height;

    public DialogueMenu(Set<String> topics) {
        Set<Button> buttons = new HashSet<>();
        topics.forEach((String topic) -> buttons.add(new DialogueButton(topic)));
        this.buttons = buttons;
    }

    public void update() {}

    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(0x00000099, true));
        g.drawRect(0, DrawingCanvas.dimensions[1], DrawingCanvas.dimensions[0], height);
        super.draw(g);
    }

    private class DialogueButton implements Button {
        private DialogueButton(String text) {

        }

        @Override
        public void draw(Graphics2D g) {
            
        }
    }
}

class RotaryMenu extends Menu implements Drawable {
    private Point origin;
    private int radius = 32;

    public RotaryMenu(Point origin) {
        this.origin = origin;
        this.buttons = Set.of(
            new RotaryButton(Constants.Images.MonsterIcon, 20), // Monsters
            new RotaryButton(Constants.Images.ItemsIcon, 40), // Items
            new RotaryButton(Constants.Images.SettingsIcon, 60), // Settings
            new RotaryButton(Constants.Images.ExitIcon, 80) // Exit
        );
    }

    public void expand() {
        super.expand();
        this.buttons.forEach(b->((RotaryButton)b).expand());
    }

    public void update() {
        for (Button b : this.buttons) ((RotaryButton)b).update();
    }

    private class RotaryButton implements Button {
        private static int size = 12;
        private Image icon;
        private double direction;
        private double animprog = 0;
        private double[] vectors;

        private RotaryButton(Image icon, double degrees) {
            this.icon = icon;
            this.direction = Math.toRadians(degrees);
            this.vectors = new double[]{
                Math.cos(this.direction) * radius, 
                Math.sin(this.direction) * radius
            };
        }

        private void expand() {
            animprog = 0;
        }

        public void update() {
            animprog += 0.01;
            animprog = Math.min(animprog, 1);
        }

        public void draw(Graphics2D g) {
            double coeff = animprog == 0 ? 0 : (expanded ? Utils.easeOutElastic(animprog) : Utils.easeOutBack(animprog));
            int x = (int)Math.round(this.vectors[0] * coeff + origin.getX());
            int y = (int)Math.round(this.vectors[1] * coeff + origin.getY());
            g.drawOval(x, y, size, size);
            g.drawImage(this.icon, x, y, null);
        }
    }
}
