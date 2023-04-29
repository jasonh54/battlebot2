package despacito7.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;

import despacito7.App;
import despacito7.Constants;
import despacito7.DrawingCanvas;
import despacito7.util.Drawable;
import despacito7.util.Utils;
import scala.Int;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.MouseInfo;

public abstract class Menu {
    public static final Menu cornerMenu = new RotaryMenu(new Point(0, 0));
    public static final Menu battleMenu = new BattleMenu();
    public static final Menu itemMenu = new BattleMenu();
    public static final Menu monsterMenu = new BattleMenu();
    public static final Menu moveMenu = new BattleMenu();
    protected Set<Button> buttons;
    protected boolean expanded;

    public void expand() {
        this.expanded = !this.expanded;
    }

    public abstract void update();

    public void draw(Graphics2D g) {
        for (Button b : this.buttons) b.draw(g);
    }

    public abstract void tick();

    public void addButton(Button b){
        buttons.add(b);
    }

    public void addButton(BattleButton b){
        battleMenu.addButton(b);
    }

    public void resetButtons(){
        buttons = new HashSet<Button>();
    }

    public int getLength(){
        return buttons.size();
    }

    public static interface Button extends Drawable {
        public void draw(Graphics2D g);
    }

    public static BattleButton generateButton(int x, int y, int w, int h, String text, ButtonCallback callback){
        return new BattleButton(x, y, w, h, text, callback);
    }

    public static interface ButtonCallback {
        void activate();
    }

    static class BattleButton implements Drawable {
        int x, y, w, h;
        String text;
        ButtonCallback c;
        public BattleButton(int x, int y, int w, int h, String text, ButtonCallback callback){
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.text = text;
            c = callback;
        }
        public void draw(Graphics2D g){
            Color buttonColor = Color.WHITE;
            if(hover()) buttonColor = Color.GRAY;
            g.setColor(buttonColor);
            g.fillRect(x, y, w, h);
            g.setColor(Color.BLACK);
            g.drawString(text, x, y+h);
        }
        public boolean hover(){
            Point coord = MouseInfo.getPointerInfo().getLocation();
            Point offset = App.f.getLocationOnScreen();
            int mouseX = ((int)coord.getX()+(int)offset.getX()/2)/2;

            int mouseY = (((int)coord.getY()+(int)offset.getY()*2)/2)-50;

            // System.out.println(mouseX + ", " + mouseY);
            if(mouseX > x && mouseX < x+w && mouseY > y && mouseY < y+h)return true;
            return false;
        }
        public void tick(){
            System.out.println("running tick");
            if(hover() && Constants.leftMouseClick){
                c.activate();
            }
        }
    }
}



class BattleMenu extends Menu implements Drawable {
    protected Set<BattleButton> buttons;
    
    public BattleMenu(){
        buttons = new HashSet<BattleButton>();
    }

    public void update() {}

    public void draw(Graphics2D g) {
        for (BattleButton b : this.buttons) b.draw(g);
    }
    
    public void tick(){
        for (BattleButton b : this.buttons) b.tick();
    }

    public void addButton(BattleButton b){
        buttons.add(b);
    }

    public int getLength(){
       return buttons.size();
    }

    public void resetButtons(){
        buttons = new HashSet<BattleButton>();
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
    public void tick() {}

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

    public void tick() {}

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
            double coeff = Utils.easeOutBack(animprog);
            int x = (int)Math.round(this.vectors[0] * coeff + origin.getX());
            int y = (int)Math.round(this.vectors[1] * coeff + origin.getY());
            g.drawOval(x, y, size, size);
            g.drawImage(this.icon, x, y, null);
        }
    }
}
