/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haykimfinal;

import GameStates.StateManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author Jonathan
 */
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 3;
    private Thread t;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;
    private BufferedImage img;
    private Graphics2D g;
    private StateManager sm;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();

    }

    public void addNotify() {
        super.addNotify();
        if (t == null) {
            t = new Thread(this);
            addKeyListener(this);
            addMouseListener(this);
            addMouseMotionListener(this);          
            t.start();
        }
    }

    private void init() {
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) img.getGraphics();
        running = true;
        sm = new StateManager();
    }

    public void run() {
        init();

        long start;
        long deltaTime;
        long waitTime;
        int frames = 0;
        int ticks = 0;
        start = System.nanoTime();
        double unprocessed = 0;
        double nsPerSec = 1000000000.0 / 60;
        long timer = System.currentTimeMillis();

        sm.draw(g);
        drawToScreen();
        sm.setState(1);

        while (running) {

            long now = System.nanoTime();
            unprocessed += (now - start) / nsPerSec;

            start = now;

            deltaTime = System.nanoTime() - start;
            waitTime = targetTime - deltaTime / 1000000;
            if (unprocessed > 0) {
                int tempUnprocessed = (int) unprocessed;
                for (int i = 0; i < tempUnprocessed; i++) {
                    ticks++;
                    update();
                    unprocessed--;
                    draw();
                }

            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            drawToScreen();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                System.out.print("Frames: " + frames);
                System.out.println(" | Ticks:  " + ticks);
                ticks = 0;
                frames = 0;
                timer += 1000;
            }
        }
    }

    private void update() {
        sm.update();
    }

    private void draw() {
        sm.draw(g);
    }

    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(img, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        sm.keyPressed(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        sm.keyReleased(e.getKeyCode());
    }

    public void mouseClicked(MouseEvent e) {
        sm.mouseClicked(e);
    }

    public void mousePressed(MouseEvent e) {
        sm.mousePressed(e);
    }

    public void mouseReleased(MouseEvent e) {
       
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {
        
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        sm.mouseMoved(e);
    }
}
