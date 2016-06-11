/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 *
 * @author Jonathan
 */
public class NewGameState extends GameState {

    public NewGameState(StateManager sm) {
        this.sm = sm;
    }

    public void init() {
        
    }

    public void update() {
        
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawString("UR BAD", 50, 50);
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

}
