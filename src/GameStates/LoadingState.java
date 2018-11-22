/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import Backgrounds.AnimatedBackground;
import Backgrounds.Background;
import haykimfinal.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Jonathan
 */
public class LoadingState extends GameState {


    private Color c;
    private Font tf;
    private Font f;

    public LoadingState(StateManager sm) {
        this.sm = sm;

        try {
            c = Color.WHITE;
            tf = new Font("Century Gothic", Font.BOLD, 32);           
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void init() {
        
    }

    public void update() {
        
    }

    //Draws the word 'LOADING...' before the game starts
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, GamePanel.WIDTH, GamePanel.WIDTH);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.WIDTH);

        g.setColor(c);
        g.setFont(tf);
        g.drawString("LOADING...", 85, 120);
        
        g.setFont(f);      
    }


    public void keyPressed(int k) {
        
    }

    public void keyReleased(int k) {

    }

    public void mouseClicked(MouseEvent e) {
       
    }

    public void mousePressed(MouseEvent e) {
        
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(int k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
