/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backgrounds;

import haykimfinal.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.net.URL;
 import javafx.scene.media.AudioClip;

/**
 *
 * @author Jonathan
 */
public class DrawText {

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
    private String word;
    private char letters[];
    private Color textColor;
    private int writeSpeed;
    private int counter;
    private int currentLetter;
    private boolean doneWrite;
    private Font theFont;
    private FontMetrics metrics;
    private int maxLength;
    private static final int MAXLENGTH_INFINITE = -1;

    URL resource;
    AudioClip SFX;

    //Sets the string that will type, its color, the typespeed, the volume of the sound effect, and the font that will be used
    public DrawText(String s, Color c, int speed, String sound, double volume, Font f) {
        word = s;
        textColor = c;
        writeSpeed = speed;
        letters = word.toCharArray();
        word = "";
        moving = false;       
        theFont = f;
        maxLength = MAXLENGTH_INFINITE;

        try {
            resource = ClassLoader.getSystemResource(sound);
             SFX = new AudioClip(resource.toString());
             SFX.setVolume(volume);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        doneWrite = false;
    }

    //See Background
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

    //Returns the width of the string at the current time
    public int getWidth() {
        return metrics.stringWidth(word);
    }

    //See Background
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

    //Returns if the word is done being written or not
    public boolean isFinished() {
        return doneWrite;
    }

    //see background
    public void setPosition(double x1, double y1) {
        x = x1 % GamePanel.WIDTH;
        y = y1 % GamePanel.HEIGHT;
    }

    //see background
    public boolean getMoving() {
        return moving;
    }

    //see background
    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    //starts the write over again
    public void reset() {
        doneWrite = false;
        counter = 0;
        currentLetter = 0;
        word = "";
    }
    
    //Sets maximum length of the string. Infinite by default but useful so that user input does not pass out of screen
    public void setMaxLength(int i){
        maxLength = i;
    }

    //Plays a sound when a letter is written, and updates the word
    public void update() {
        if (letters.length == 0) {
            doneWrite = true;
        }
        if (!doneWrite) {
            counter++;
            if (counter >= writeSpeed || letters[currentLetter] == ' ') {
                if (currentLetter < letters.length) {
                    word += letters[currentLetter];
                    if (letters[currentLetter] != ' ') {
                         SFX.play();
                    }
                    currentLetter++;
                    if (currentLetter == letters.length) {
                        doneWrite = true;
                    }
                }
                counter = 0;
            }
        }
    }

    //adds a letter to the word
    public void manualAdd(int k) {
         SFX.play();

        if (k == 8) {
            removeLetter();
        } else if (k >= 32 && k <= 126) {
            word += Character.toString((char) KeyEvent.getExtendedKeyCodeForChar(k));
        }
        if (maxLength >= 1 && word.length() > maxLength) {
            word = word.substring(0, word.length() - 1);
        }

    }

    //Functions as backspace. Removes last letter.
    public void removeLetter() {
        if (word.length() - 1 >= 0) {
             SFX.play();
            word = word.substring(0, word.length() - 1);
        }
    }

    //Returns the word at the current point in time
    public String getWord() {
        return word;
    }

    //Draws the word based on font, color, and position
    public void draw(Graphics2D g) {
        g.setFont(theFont);
        metrics = g.getFontMetrics(theFont);
        g.setColor(textColor);
        g.drawString(word, (int) x, (int) y);
    }
}
