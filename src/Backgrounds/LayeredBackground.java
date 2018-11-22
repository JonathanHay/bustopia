/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backgrounds;

import haykimfinal.GamePanel;
import java.awt.Graphics2D;

/**
 *
 * @author Jonathan
 */
public class LayeredBackground {

    private Background layers[];

    //Creates a background array with common wrapping and scale
    public LayeredBackground(String s[], boolean wx, boolean wy, double sx, double sy) {

        layers = new Background[s.length];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = new Background(s[i], wx, wy, sx, sy);
        }
    }

    //sets position of all layers *SEE Background
    public void setPosition(double x, double y) {
        for (int i = 0; i < layers.length; i++) {
            layers[i].setPosition(x, y);
        }
    }

    //Moves all of the layers to a position *SEE Background
    public void moveToPosition(int x2, int y2, double speed, boolean mX, boolean mY) {
        for (int i = 0; i < layers.length; i++) {
            layers[i].moveToPosition(x2, y2, speed, mX, mY);
        }
    }

    //Returns if the bottom layer is moving (Same for all layers)
    public boolean getMoving() {
        return layers[0].getMoving();
    }

    //Updates all layers *SEE Background
    public void update() {
        for (int i = 0; i < layers.length; i++) {
            layers[i].update();
        }
    }

    //Draws all layers *SEE Background
    public void draw(Graphics2D g) {
        for (int i = 0; i < layers.length; i++) {
            layers[i].draw(g);
        }
    }
    //Sets velocity of all layers *SEE Background
    public void setVelocity(double dx, double dy) {
        for (int i = 0; i < layers.length; i++) {
            layers[i].setVelocity(dx, dy);
        }
    }

    //Sets velocity of a specific layer *SEE Background
    public void setLayerVelocity(int k, double dx, double dy) {
        try {
            layers[k].setVelocity(dx, dy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Sets position of a specific layer *SEE Background
    public void setLayerPosition(int k, double x, double y) {
        try {
            layers[k].setPosition(x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
