/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backgrounds;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Jonathan
 */

//REMEMBER THE TOGGLE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//*******************************************
public class HayButton {

    private Background buttonBgUp;
    private Background buttonBgDown;

    private boolean pressed;
    private boolean hovered;
    private boolean clickable;

    private FontMetrics metrics;
    private String buttonWord;

    private Color dColor;
    private Color hColor;
    private Color pColor;
    private Color lastColor;

    private int xInd;
    private int yInd;

    private int xOffset;
    private int yOffset;

    private Font font;

    public HayButton(String s1, String s2, String t, Font f, double sx, double sy) {
        buttonBgUp = new Background(s1, 1, false, false, sx, sy);
        buttonBgDown = new Background(s2, 1, false, false, sx, sy);
        buttonBgUp.generateHitbox();
        pressed = false;
        font = f;
        buttonWord = t;
        xInd = 0;
        yInd = 0;
        clickable = true;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setFontColor(Color c1, Color c2, Color c3) {
        dColor = c1;
        hColor = c2;
        pColor = c3;
    }

    public void setClickable(boolean b) {
        clickable = b;
    }

    public boolean getClickable() {
        return clickable;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setIndent(int ix, int iy) {
        xInd = ix;
        yInd = iy;
    }

    public void setOffset(int ix, int iy) {
        xOffset = ix;
        yOffset = iy;
    }

    public void update() {
        buttonBgUp.update();
        buttonBgDown.update();
    }

    public void boop() {
        hovered = false;
        pressed = false;
    }

    public void toggle() {
        if (pressed) {
            pressed = false;
        } else if (!pressed) {
            pressed = true;
        }
    }

    public void setPosition(double d1, double d2) {
        buttonBgUp.setPosition(d1, d2);
        buttonBgDown.setPosition(d1, d2);
        update();
    }

    public void moveToPosition(int x2, int y2, double speed, boolean mX, boolean mY) {
        buttonBgUp.moveToPosition(x2, y2, speed, mX, mY);
        buttonBgDown.moveToPosition(x2, y2, speed, mX, mY);
    }

    public void setHovered(boolean b) {
        hovered = b;
    }

    public void setPressed(boolean b) {
        pressed = b;
    }

    public boolean checkHover(Point p) {
        if (buttonBgUp.getHitbox().contains(p)) {
            hovered = true;
        }
        return hovered;
    }

    public boolean checkClick(Point p) {
        if (buttonBgUp.getHitbox().contains(p) && clickable) {
            pressed = !pressed;
            return true;
        }else{
            return false;
        }
        
    }

    public void draw(Graphics2D g) {
        metrics = g.getFontMetrics(font);

        int centerIndentX = metrics.stringWidth(buttonWord) / 2;
        int centerIndentY = (font.getSize() / 2) + 1;

        lastColor = g.getColor();

        if (pressed) {
            buttonBgDown.draw(g);
            g.setColor(pColor);
            centerIndentX -= xInd;
            centerIndentY -= yInd;
        } else if (hovered) {
            buttonBgUp.draw(g);
            g.setColor(hColor);
        } else {
            buttonBgUp.draw(g);
            g.setColor(dColor);
        }
        g.setFont(font);
        g.drawString(buttonWord, buttonBgUp.getHitbox().x + (buttonBgUp.getHitbox().width / 2) - centerIndentX + xOffset, buttonBgUp.getHitbox().y + buttonBgUp.getHitbox().height - centerIndentY + yOffset);
        g.setColor(dColor);
    }
}
