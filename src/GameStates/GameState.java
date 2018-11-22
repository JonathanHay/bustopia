/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import Player.PlayerSettings;
import haykimfinal.GamePanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.event.MouseEvent;

/**
 *
 * @author Jonathan
 */
//A template for all of the gamestates. It requires these classes to make gamestate work (it has to know they exist)
public abstract class GameState {

    protected float alpha;
    protected int fadeCounter;
    public boolean initComplete = false;

    protected StateManager sm;

    //Sets all variable to default
    public abstract void init();

    //updates variables
    public abstract void update();

    //draws items
    public abstract void draw(java.awt.Graphics2D g);

    //passes keys from keylistener
    public abstract void keyPressed(int k);

    public abstract void keyReleased(int k);

    public abstract void keyTyped(int k);

    //passes mouse from mouselistener
    public abstract void mouseClicked(MouseEvent e);

    public abstract void mousePressed(MouseEvent e);

    public abstract void mouseEntered(MouseEvent e);

    public abstract void mouseMoved(MouseEvent e);

    public abstract void mouseReleased(MouseEvent e);

    public abstract void mouseDragged(MouseEvent e);

    //lowers alpha
    protected boolean fadeOut(int i) {
        fadeCounter++;
        if (fadeCounter >= i) {
            if ((alpha - 0.02f) >= 0f) {
                alpha -= 0.02f;
            } else {
                return true;
            }
            fadeCounter = 0;
        }
        return false;
    }

    //raises alpha
    protected boolean fadeIn(int i) {
        fadeCounter++;
        if (fadeCounter >= i) {
            if ((alpha + 0.02f) <= 1.0f) {
                alpha += 0.02f;
            } else {
                return true;
            }
            fadeCounter = 0;
        }
        return false;
    }

    //draws a black rectangle
    protected void drawBlack(java.awt.Graphics2D g, float a) {
        Color oldColor = g.getColor();
        g.setColor(Color.BLACK);
        g.setComposite(AlphaComposite.SrcOver.derive(a));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setComposite(AlphaComposite.SrcOver.derive(1.0f));
        g.setColor(oldColor);
    }

    //draws a blue rectangle
    protected void drawNight(java.awt.Graphics2D g, float a) {
        Color oldColor = g.getColor();
        g.setColor(Color.BLUE);
        g.setComposite(AlphaComposite.SrcOver.derive(a));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setComposite(AlphaComposite.SrcOver.derive(1.0f));
        g.setColor(oldColor);
    }

    //draws a colored rectangle
    protected void drawColorFill(java.awt.Graphics2D g, float a, Color c) {
        Color oldColor = g.getColor();
        g.setColor(c);
        g.setComposite(AlphaComposite.SrcOver.derive(a));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setComposite(AlphaComposite.SrcOver.derive(1.0f));
        g.setColor(oldColor);
    }

}
