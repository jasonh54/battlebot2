package despacito7.menu;

import java.awt.Graphics2D;
import java.util.Set;

import despacito7.Constants;
import despacito7.util.Drawable;
import despacito7.util.Utils;

import java.awt.Image;
import java.awt.Point;

class Menu {
    protected Set<Button> buttons;


}

class RotaryMenu extends Menu implements Drawable {
    private Point origin;
    private int radius;

    public RotaryMenu(Point origin) {
        this.origin = origin;
        this.buttons = Set.of(
            new RotaryButton(Constants.Images.MonsterIcon, 340), // Monsters
            new RotaryButton(Constants.Images.ItemsIcon, 320), // Items
            new RotaryButton(Constants.Images.SettingsIcon, 300), // Settings
            new RotaryButton(Constants.Images.ExitIcon, 280) // Exit
        );
    }

    public void update() {
        this.buttons.forEach(b->((RotaryButton)b).update());
    }

    public void draw(Graphics2D g) {
        this.buttons.forEach(b->b.draw(g));
    }

    class RotaryButton extends Button {
        private static int size = 16;
        private Image icon;
        private double direction;
        private double animprog = 0;

        public RotaryButton(Image icon, double degrees) {
            this.icon = icon;
            this.direction = Math.toRadians(degrees);
        }

        public void update() {
            animprog = Utils.easeOutBack(animprog);
        }

        public void draw(Graphics2D g) {
            int x = (int)Math.round(Math.cos(direction) * animprog * radius + origin.getX());
            int y = (int)Math.round(Math.sin(direction) * animprog * radius + origin.getY());
            g.drawOval(x, y, size, size);
            g.drawImage(this.icon, x, y, null);
        }
    }
}
