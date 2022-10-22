package despacito7.menu;

import java.awt.Graphics2D;
import java.util.Set;

import despacito7.Constants;
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

    public void update() {
        for (Button b : this.buttons) ((RotaryButton)b).update();
    }

    class RotaryButton extends Button {
        private static int size = 12;
        private Image icon;
        private double direction;
        private double animprog = 0;
        private double[] vectors;

        public RotaryButton(Image icon, double degrees) {
            this.icon = icon;
            this.direction = Math.toRadians(degrees);
            this.vectors = new double[]{
                Math.cos(this.direction) * radius, 
                Math.sin(this.direction) * radius
            };
        }

        public void update() {
            animprog = expanded ? Utils.easeOutBack(animprog) : 1-Utils.easeOutBack(1-animprog);
        }

        public void draw(Graphics2D g) {
            int x = (int)Math.round(this.vectors[0] * animprog + origin.getX());
            int y = (int)Math.round(this.vectors[1] * animprog + origin.getY());
            g.drawOval(x, y, size, size);
            g.drawImage(this.icon, x, y, null);
        }
    }
}
