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
import java.awt.image.BufferedImage;

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
    private boolean visible;

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
    
    //Sets 2 backgrounds (up and down) to new backgrounds based on two strings which are resources, sets scaling, and wrapping, and creates hitbox. SEE Background
    public HayButton(String s1, String s2, String t, Font f, double sx, double sy) {
        buttonBgUp = new Background(s1, false, false, sx, sy);
        buttonBgDown = new Background(s2, false, false, sx, sy);
        buttonBgUp.generateHitbox();
        pressed = false;
        font = f;
        buttonWord = t;
        xInd = 0;
        yInd = 0;
        clickable = true;
        visible = true;
    }

    //Sets 2 backgrounds (up and down) to new backgrounds based on two strings which are resources as images, sets scaling, and wrapping, and creates hitbox. SEE Background
    public HayButton(BufferedImage image1, BufferedImage image2, String t, Font f, double sx, double sy) {
        buttonBgUp = new Background(image1, false, false, sx, sy);
        buttonBgDown = new Background(image2, false, false, sx, sy);
        buttonBgUp.generateHitbox();
        pressed = false;
        font = f;
        buttonWord = t;
        xInd = 0;
        yInd = 0;
        clickable = true;
        visible = true;
    }

    //Returns if the button is pressed or not
    public boolean isPressed() {
        return pressed;
    }

    //Sets the font colors for default, hovering, and pressed states
    public void setFontColor(Color c1, Color c2, Color c3) {
        dColor = c1;
        hColor = c2;
        pColor = c3;
    }

    //sets the button to be clickable or not
    public void setClickable(boolean b) {
        clickable = b;
    }

    //returns if the button is currently clickable
    public boolean getClickable() {
        return clickable;
    }

    //returns if the mouse cursor is currently hovering over the button
    public boolean isHovered() {
        return hovered;
    }

    //sets the indent of the text in the x and y axis which will occur when the button is pressed to give the illusion of depth
    public void setIndent(int ix, int iy) {
        xInd = ix;
        yInd = iy;
    }
    
    //sets an offset of the button text from center
    public void setOffset(int ix, int iy) {
        xOffset = ix;
        yOffset = iy;
    }

    //Updates up and down backgrounds *SEE Background
    public void update() {
        buttonBgUp.update();
        buttonBgDown.update();
    }

    //Sets both hovered and pressed to false
    public void boop() {
        hovered = false;
        pressed = false;
    }

    //toggles the buttons state (pressed/not pressed)
    public void toggle() {
        if (pressed) {
            pressed = false;
        } else if (!pressed) {
            pressed = true;
        }
    }

    //Sets posiotion of up and down backgrounds and updates *SEE Background
    public void setPosition(double d1, double d2) {
        buttonBgUp.setPosition(d1, d2);
        buttonBgDown.setPosition(d1, d2);
        update();
    }

    //Calls moveToPosition on both backgrounds *SEE Background
    public void moveToPosition(int x2, int y2, double speed, boolean mX, boolean mY) {
        buttonBgUp.moveToPosition(x2, y2, speed, mX, mY);
        buttonBgDown.moveToPosition(x2, y2, speed, mX, mY);
    }

    //sets the button's hovered state
    public void setHovered(boolean b) {
        hovered = b;
    }

    //sets the button's pressed state
    public void setPressed(boolean b) {
        pressed = b;
    }

    //checks if a point is intersecting with a hitbox, returns true or false as well as sets the hovered state
    public boolean checkHover(Point p) {
        if (buttonBgUp.getHitbox().contains(p)) {
            hovered = true;
            return true;
        }
        return false;
    }

    //checks if a point is intersecting with a hitbox, returns true or false as well as sets the pressed state
    public boolean checkClick(Point p) {
        if (buttonBgUp.getHitbox().contains(p) && clickable) {
            pressed = !pressed;
            return true;
        } else {
            return false;
        }

    }

    //SEE Background
    public double getX() {
        return buttonBgUp.getX();
    }

    //SEE Background
    public double getY() {
        return buttonBgUp.getY();
    }

    //Sets both backgrounds to be visible or not *SEE Background
    public void setVisible(boolean b) {
        buttonBgUp.setVisible(b);
        buttonBgDown.setVisible(b);
    }

    //draws the button in its required state and centers the text (+ offset)
    public void draw(Graphics2D g) {
        if (visible) {
            metrics = g.getFontMetrics(font);

            int centerIndentX = metrics.stringWidth(buttonWord) / 2;
            int centerIndentY = (buttonBgUp.getHitbox().height / 2) - (font.getSize() / 2 - 2);

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
            g.setColor(lastColor);
        }
    }
}
