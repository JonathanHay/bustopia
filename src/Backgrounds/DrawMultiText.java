/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Backgrounds;

import haykimfinal.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.net.URL;
 import javafx.scene.media.AudioClip;

/**
 *
 * @author 246622
 */
public class DrawMultiText {

    private double x;
    private double y;
    private double dx;
    private double dy;

    private boolean wrapX;
    private boolean wrapY;

    private int x21;
    private int y21;
    private double speed1;
    private boolean mX1;
    private boolean mY1;
    private double dx0;
    private double dy0;
    private boolean moving;
    private boolean doneWrite;
    private DrawText text[];
    private int currentText;
    private int spacing;
    
    //THIS CLASS IS ALMOST FUNCTIONALLY THE SAME AS DrawText BUT CAN HAVE A VARYING SPEED *SEE DrawText*

    public DrawMultiText(String[] s, Color c, int[] speed, String sound, double volume, Font f) {
        currentText = 0;
        text = new DrawText[s.length];

        for (int i = 0; i < text.length; i++) {
            text[i] = new DrawText(s[i], c, speed[i], sound, volume, f);
        }

        moving = false;
        doneWrite = false;
        spacing = 0;
    }

    public void moveToPosition(int x2, int y2, double speed, boolean mX, boolean mY) {
        if (!moving) {
            dx0 = dx;
            dy0 = dy;
            x21 = x2 + (int) x;
            y21 = y2 + (int) y;
            speed1 = speed;
            mX1 = mX;
            mY1 = mY;
            moving = true;
        }

    }

    private void move(int x2, int y2, double speed, boolean mX, boolean mY) {
        if (mX) {
            if (x > x2) {
                dx += -speed;
            } else if (x < x2) {
                dx += speed;
            }
        }
        if (mY) {
            if (y > y2) {
                dy = -speed;
            } else if (y < y2) {
                dy = speed;
            }
        }

        if (mX && !mY) {
            if (x == x2) {
                moving = false;
                dx = dx0;
            }
        } else if (!mX && mY) {
            if (y == y2) {
                moving = false;
                dy = dy0;
            }
        } else if (x == x2 && y == y2) {
            moving = false;
            dx = dx0;
            dy = dy0;
        }
    }

    public boolean isFinished() {
        return doneWrite;
    }

    public void setMaxTypedLength(int i) {
        text[text.length - 1].setMaxLength(i);
    }

    public int getSpacing() {
        return spacing;
    }

    public void setPosition(double x1, double y1) {
        x = x1 % GamePanel.WIDTH;
        y = y1 % GamePanel.HEIGHT;
    }

    public boolean getMoving() {
        return moving;
    }

    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void reset() {
        doneWrite = false;
    }

    public void update() {

        text[currentText].update();
        if (currentText > 0) {
            text[currentText].setPosition(x + spacing, y);
        }

        if (text[currentText].isFinished() && currentText != text.length - 1) {
            spacing += text[currentText].getWidth();
            currentText++;
        }

        if (text[text.length - 1].isFinished()) {
            doneWrite = true;
        }

        text[0].setPosition(x, y);

    }

    public void manualAdd(int k) {
        if (k == 8 && doneWrite) {
            removeLetter();
        } else if (k >= 32 && k <= 126 && doneWrite) {
            text[text.length - 1].manualAdd(k);
        }

    }

    public void removeLetter() {
        if (doneWrite) {
            text[text.length - 1].removeLetter();
        }
    }

    public void draw(Graphics2D g) {
        text[0].draw(g);
        for (int i = 1; i < text.length; i++) {
            if (text[i - 1].isFinished()) {
                text[i].draw(g);
            }
        }
    }

    public int getLength() {
        return text.length;
    }

    public String getWord(int i) {
        if (i < text.length && i >= 0) {
            return text[i].getWord();
        }
        return null;
    }

}
