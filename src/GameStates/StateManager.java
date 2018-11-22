/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author Jonathan
 */
public class StateManager {

    private ArrayList<GameState> states;
    private int currentState;

    public static final int LOADING = 0;
    public static final int MAINMENU = 1;
    public static final int NEWGAME = 2;
    public static final int NIGHTMENU = 3;
    public static final int INVENTORY = 4;
    public static final int LOADGAME = 5;
    public static final int BUSMENU = 6;
    public static final int DAYMENU = 7;
    public static final int SHOPMENU = 8;
    public static final int MAP = 9;
    public static final int APPLYSTATE = 10;

    //Adds all of the game states
    public StateManager() {
        states = new ArrayList<GameState>();
        currentState = LOADING;
        states.add(new LoadingState(this));
        states.get(currentState).init();
        states.get(currentState).initComplete = true;
        states.add(new MainMenuState(this));
        states.add(new NewGameState(this));
        states.add(new NightMenu(this));
        states.add(new InventoryState(this));
        states.add(new LoadGameState(this));
        states.add(new BusMenuState(this));
        states.add(new DayMenu(this));
        states.add(new ShopState(this));
        states.add(new MapState(this));
        states.add(new ApplyItemState(this));
    }

    //Sets the state to a chosen state and inits it
    public void setState(int state) {
        currentState = state;
        states.get(currentState).initComplete = false;
        states.get(currentState).init();
        states.get(currentState).initComplete = true;
    }

    //Calls update on current state
    public void update() {
        if (states.get(currentState).initComplete) {
            states.get(currentState).update();
        }
    }

    //Calls draw on current state
    public void draw(java.awt.Graphics2D g) {
        if (states.get(currentState).initComplete) {
            states.get(currentState).draw(g);
        }
    }

    //Calls key functions on current state
    public void keyPressed(int k) {
        if (states.get(currentState).initComplete) {
            states.get(currentState).keyPressed(k);
        }
    }

    public void keyReleased(int k) {
        if (states.get(currentState).initComplete) {
            states.get(currentState).keyReleased(k);
        }
    }

    public void keyTyped(int k) {
        if (states.get(currentState).initComplete) {
            states.get(currentState).keyTyped(k);
        }
    }

    //Calls mouse functions on current state
    public void mouseClicked(MouseEvent e) {
        if (states.get(currentState).initComplete) {
            states.get(currentState).mouseClicked(e);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (states.get(currentState).initComplete) {
            states.get(currentState).mousePressed(e);
        }
    }

    public void mouseEntered(MouseEvent e) {
        if (states.get(currentState).initComplete) {
            states.get(currentState).mouseEntered(e);
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (states.get(currentState).initComplete) {
            states.get(currentState).mouseMoved(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (states.get(currentState).initComplete) {
            states.get(currentState).mouseReleased(e);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (states.get(currentState).initComplete) {
            states.get(currentState).mouseDragged(e);
        }
    }

}
