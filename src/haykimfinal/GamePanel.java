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
import javax.swing.JFrame;

/**
 *
 * @author Jonathan
 */
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    public static int WIDTH = 640;
    public static int HEIGHT = 360;
    public static double SCALE = 2;
    private static boolean fullscreen;
    public boolean lastFullscreen;
    public static int frames;
    public static int ticks;
    private Thread t;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;
    private BufferedImage img;
    private Graphics2D g;
    private StateManager sm;
    private static int extraX;
    private static int extraY;
    private static Dimension screenSize;
    protected JFrame f;

    public GamePanel(JFrame f) {
        super();
        this.f = f;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        SCALE = screenSize.width / WIDTH / 2;
        setPreferredSize(new Dimension((int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)));
        setFocusable(true);
        requestFocus();
        fullscreen = false;
        lastFullscreen = fullscreen;
        f.setUndecorated(fullscreen);
        extraX = 0;
        extraY = 0;
    }

    public void addNotify() {
        super.addNotify();
        if (t == null) {
            t = new Thread(this);
            t.start();

        }
    }

    private void init() {
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) img.getGraphics();
        running = true;
        sm = new StateManager();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public static void toggleFullscreen() {
        if (fullscreen == true) {
            SCALE = screenSize.width / WIDTH / 2;
            extraX = 6;
            extraY = 29;
            fullscreen = false;
        } else if (fullscreen == false) {
            SCALE = screenSize.width / WIDTH;       
            extraX = 0;
            extraY = 0;
            fullscreen = true;
        }

    }

    public void run() {
        init();

        long start;
        long deltaTime;
        long waitTime;
        frames = 0;
        ticks = 0;
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
                    unprocessed--;
                    update();
                    if (fullscreen != lastFullscreen) {
                        f.dispose();
                        f.setUndecorated(fullscreen);
                        f.pack();
                        f.setSize(new Dimension((int) (WIDTH * SCALE) + extraX, (int) (HEIGHT * SCALE) + extraY));
                        lastFullscreen = fullscreen;
                        f.setLocationRelativeTo(null);
                        f.setVisible(true);
                    }
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
        g2.drawImage(img, 0, 0, (int) (WIDTH * SCALE), (int) (HEIGHT * SCALE), null);
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
