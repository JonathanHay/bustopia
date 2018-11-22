/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import Backgrounds.DrawMultiText;
import Backgrounds.DrawText;
import Player.PlayerSettings;
import haykimfinal.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
 import javafx.scene.media.AudioClip;

/**
 *
 * @author Jonathan
 */
public class LoadGameState extends GameState {

    private DrawMultiText test;
    boolean letsGo;
    boolean holdingShift;

    String[] s = {"Hello", "... ", "Welcome back to Bustopia. ", "E", "nter Name: ", ""};
    int[] speed = {5, 5, 5, 5, 5, 5};

    URL resource;
    AudioClip SFX;
    private Font f;

    public LoadGameState(StateManager sm) {
        this.sm = sm;
    }

    //sets default alpha, loads font,music, and instantiates the line of text
    public void init() {
        alpha = 0;
        try {
            f = Font.createFont(Font.PLAIN, ClassLoader.getSystemResourceAsStream("Resources/Fonts/pixelate.ttf"));
            f = f.deriveFont(f.getSize() * 16F);
        } catch (Exception e) {
            e.printStackTrace();
        }

        test = new DrawMultiText(s, Color.WHITE, speed, "Resources/SoundEffects/Menu_Navigate_03.wav", PlayerSettings.volume, f);
        test.setPosition(100, 150);
        test.setMaxTypedLength(12);
        letsGo = false;

        try {
            resource = ClassLoader.getSystemResource("Resources/SoundEffects/Jingle_Achievement_01.wav");
             SFX = new AudioClip(resource.toString());
             SFX.setVolume(0.3);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //Updates the text drawer and fades in to black when a name is entered
    public void update() {
        test.update();
        if (letsGo) {
            fadeIn(1);
        }
    }
    
    //Draws the intro words/user input
    public void draw(Graphics2D g) {
        drawBlack(g, 1.0f);
        test.draw(g);
        drawBlack(g, alpha);
    }

    //Takes user keypresses and adds them to the word, when enter is pressed sets name and loads game *SEE PlayerSettings
    public void keyPressed(int k) {

        if (k != KeyEvent.VK_ENTER && !letsGo && k != KeyEvent.VK_SHIFT && k != KeyEvent.VK_CONTROL && test.isFinished()) {
            test.manualAdd(k);
        } else if (k == KeyEvent.VK_ENTER && !letsGo && test.isFinished() && test.getWord(test.getLength() - 1).length() >= 1) {
            letsGo = true;
            PlayerSettings.loadGame(test.getWord(test.getLength() - 1));
             SFX.play();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(NewGameState.class.getName()).log(Level.SEVERE, null, ex);
            }
            sm.setState(StateManager.NIGHTMENU);
        }
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

    public void mouseDragged(MouseEvent e) {

    }

    public void keyTyped(int k) {

    }

}
