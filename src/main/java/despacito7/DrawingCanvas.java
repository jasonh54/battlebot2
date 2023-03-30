package despacito7;

import java.awt.Graphics2D;

import javax.swing.JFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DrawingCanvas extends javax.swing.JComponent implements KeyListener, MouseListener {
    public static int[] dimensions;
    
    public JFrame parent;
    private long prevtime = 0;
    private boolean draw = false;

    public DrawingCanvas(JFrame f) {
        this.parent = f;
        this.setFocusable(true);
        this.requestFocus();
        this.setBackground(java.awt.Color.GRAY);
        DrawingCanvas.dimensions = new int[]{this.getWidth(), this.getHeight()};
    }

    public void startDraw() { // begin the draw loop!
        this.draw = true;
        while (this.draw) {
            if (!this.parent.isShowing())
                continue; // dont render in background
            if (System.currentTimeMillis() - this.prevtime >= 1000.0 / Constants.FPS) {
                this.repaint();
                this.prevtime = System.currentTimeMillis();
            }
        }
    }

    public void stopDraw() {
        this.draw = false;
    }

    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (App.isLoaded()) App.render(g2);

        g.dispose();
        g2.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        App.onKey(e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent me) { 
        // if (me.getButton() == MouseEvent.BUTTON1) {
        //     System.out.println("Button 1 pressed...");
        //     Constants.leftMouseClick = true;
        // }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        // if (me.getButton() == MouseEvent.BUTTON1) {
        //     System.out.println("Button 1 released...");
        //     Constants.leftMouseClick = false;
        // }
    }

    @Override
    public void mouseEntered(MouseEvent me) { }

    @Override
    public void mouseExited(MouseEvent me) { }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getButton() == MouseEvent.BUTTON1) {
            System.out.println("Button 1 pressed...");
            Constants.leftMouseClick = true;
        }
    }
}