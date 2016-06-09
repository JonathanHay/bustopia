/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class StateManager {

    private ArrayList<GameState> states;
    private int currentState;

    public static final int LOADING = 0;
    public static final int MAINMENU = 1;
    public static final int NIGHTMENU = 2;
    public static final int DAYMENU = 3;
    public static final int SHOP = 4;
    public static final int MECHANIC = 5;
    public static final int LOT = 6;
    public static final int ROAD = 7;
    public static final int DIALOGUE = 8;
    
    
    public StateManager() {
        states = new ArrayList<GameState>();
        currentState = LOADING;
        states.add(new LoadingState(this));
        states.get(currentState).init();       
        states.add(new MainMenuState(this));
    }
    

    public void setState(int state) {
        currentState = state;
        states.get(currentState).init();
    }

    public void update() {
        states.get(currentState).update();
    }

    public void draw(java.awt.Graphics2D g) {
        states.get(currentState).draw(g);
    }

    public void keyPressed(int k) {
        states.get(currentState).keyPressed(k);
    }

    public void keyReleased(int k) {
        states.get(currentState).keyReleased(k);
    }
    
    public void mouseClicked(MouseEvent e){
        states.get(currentState).mouseClicked(e);
    }
    public void mousePressed(MouseEvent e){
        states.get(currentState).mousePressed(e);
    }
    
    public void mouseEntered(MouseEvent e) {
        states.get(currentState).mouseEntered(e);
    }
    
    public void mouseMoved(MouseEvent e){
        states.get(currentState).mouseMoved(e);
    }
}
