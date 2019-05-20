/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import Backgrounds.*;
import Constants.Buses;
import Constants.OtherConstants;
import Player.Inventory;
import Player.PlayerSettings;
import haykimfinal.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 *
 * @author Jonathan
 */

public class NightMenu extends GameState {

    private Background UI;
    private Background nightSceneBox;
    private Background wheels;

    private AnimatedBackground bus;

    private LayeredBackground nightScene;

    private HayButton dayButton;
    private HayButton toMenu;
    private HayButton[] buttons;
    private HayButton shopUpgrade;

    private Point mBox = new Point();

    private URL resource;
    private BasicPlayer music;

    private String[] buttonNames = {"Inventory", "Buses", "Map", "Shop", "Arena (Coming Soon)", "Leaderboards(Coming Soon)"};
    private String[] nightSceneNames = {"Resources/NightBackground/nightsky.png", "Resources/NightBackground/snowy.png"};
    private String citybus[] = {"Resources/NightBackground/SleepyBus/sleepybus.png", "Resources/NightBackground/SleepyBus/sleepybus1.png", "Resources/NightBackground/SleepyBus/sleepybus2.png", "Resources/NightBackground/SleepyBus/sleepybus3.png"};

    private int nextState;

    private boolean inUse;
    private boolean fading;

    public NightMenu(StateManager sm) {
        System.out.println(PlayerSettings.playerName);
        resource = ClassLoader.getSystemResource("Resources/NightMusic2.mp3");
        music = new BasicPlayer();
        this.sm = sm;
        UI = new Background("Resources/NightBackground/nightMenu.png", false, false, 1, 1);
        nightSceneBox = new Background("Resources/NightBackground/nightScene.png", false, false, 1, 1);
        dayButton = new HayButton("Resources/NightBackground/dayButton.png", "Resources/NightBackground/dayButtonDown.png", "Next Day", OtherConstants.DEFAULT_FONT, 1, 1);
        shopUpgrade = new HayButton("Resources/MenuBackground/button1.png", "Resources/MenuBackground/button1down.png", "Upgrade Shop", OtherConstants.DEFAULT_FONT, 1, 1);
        dayButton.setIndent(0, 3);
        toMenu = new HayButton("Resources/NightBackground/backButton.png", "Resources/NightBackground/backButton.png", "Menu", OtherConstants.DEFAULT_FONT, 1, 1);
        buttons = new HayButton[buttonNames.length];
        nightScene = new LayeredBackground(nightSceneNames, true, false, 0.5, 0.5);

        wheels = new Background(Buses.WHEEL1[0], false, false, 0.5, 0.5);
        bus = new AnimatedBackground(citybus, 1, 1, false, false, AnimatedBackground.AnimType.BOB, 1, 1);
        bus.setBobSpeed(5);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new HayButton("Resources/NightBackground/stateButton.png", "Resources/NightBackground/stateButton.png", buttonNames[i], OtherConstants.DEFAULT_FONT, 1, 1);
            buttons[i].setFontColor(Color.DARK_GRAY, Color.GRAY, Color.WHITE);
            buttons[i].setIndent(0, 3);
        }

    }

    //Sets all buttons to be unclickable unless it is not fading, sets full alpha, positions, plays music
    public void init() {
        clickables(false);
        inUse = true;
        fading = false;
        alpha = 1.0f;
        dayButton.setPosition(280, 28);
        shopUpgrade.setPosition(380, 150);
        if (PlayerSettings.shopLevel < 5) {
            shopUpgrade.setClickable(true);
        } else {
            shopUpgrade.setClickable(false);
            shopUpgrade.setFontColor(Color.GRAY, Color.GRAY, Color.GRAY);
        }
        dayButton.setFontColor(Color.WHITE, Color.GREEN, Color.RED);
        toMenu.setPosition(555, 300);
        nightSceneBox.setPosition(90, 215);

        nightScene.setLayerPosition(0, 90, 225);
        nightScene.setLayerPosition(1, 90, 255);

        bus = new AnimatedBackground(citybus, 5, 1, false, false, AnimatedBackground.AnimType.BOB, 1, 1);
        bus.setPosition(250, 273);
        bus.setBobSpeed(5);
        wheels.setPosition(250, 274);
        dayButton.setPressed(false);

        int row = 0;
        int column = 0;

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setPosition(25 + 110 * column, 90 + 60 * row);
            column++;
            if (column >= 3) {
                column = 0;
                row++;
            }
        }

        if (PlayerSettings.playerName.equals("REEEEEEEEEEE")) {
            OtherConstants.nightResource = ClassLoader.getSystemResource("Resources/Norm.mp3");
        }

        try {
            if (OtherConstants.nightMusic.getStatus() != 0) {
                OtherConstants.nightMusic.open(OtherConstants.nightResource);
                OtherConstants.nightMusic.play();
            }
            OtherConstants.nightMusic.setGain(PlayerSettings.volume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //updates the scene of sleepybus, loops music if ended, sets all buttons to be clickable once fade is done
    public void update() {
        if (!fading) {
            if (fadeOut(1)) {
                clickables(true);
            }
        }

        if (OtherConstants.nightMusic.getStatus() != 0 && inUse) {
            try {
                OtherConstants.nightMusic.play();
                OtherConstants.nightMusic.setGain(PlayerSettings.volume);
            } catch (BasicPlayerException ex) {
                Logger.getLogger(NightMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (fading) {
            if (fadeIn(1)) {
                sm.setState(nextState);
            }
        }

        nightScene.update();
        bus.update();
        wheels.update();
    }

    //Draws the user interface, the night scene(Background, bus, wheels), the titles
    public void draw(Graphics2D g) {
        drawBlack(g, 1.0f);
        nightScene.draw(g);
        bus.draw(g);
        wheels.draw(g);
        drawNight(g, 0.3f);
        UI.draw(g);
        nightSceneBox.draw(g);
        g.setFont(OtherConstants.DEFAULT_FONT);
        g.drawString(PlayerSettings.playerName + "'S COMPANY", 50, 35);
        g.drawString("Days Passed : " + PlayerSettings.day, 85, 65);
        g.drawString(String.valueOf(Inventory.playerMoney), 420, 65);
        g.drawString(String.valueOf(Inventory.playerScrap), 520, 65);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("$", 410, 65);
        g.drawString("§", 510, 63);

        g.drawString("Shop Level (Max 6): " + String.valueOf(PlayerSettings.shopLevel + 1), 380, 120);
        g.drawString("- $10000", 400, 140);

        dayButton.draw(g);
        shopUpgrade.draw(g);
        toMenu.draw(g);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].draw(g);
        }

        drawBlack(g, alpha);
    }

    //Sets all buttons to be clickable or not based on boolean input
    public void clickables(boolean b) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setClickable(b);
        }
        dayButton.setClickable(b);
        toMenu.setClickable(b);
        shopUpgrade.setClickable(b);
    }

    //Selects actions such as setting state and fading when a button is pressed
    public void select(int i) {
        switch (i) {
            case 0:
                fading = true;
                inUse = false;
                clickables(false);
                nextState = StateManager.INVENTORY;
                break;
            case 1:
                fading = true;
                inUse = false;
                clickables(false);
                nextState = StateManager.BUSMENU;
                break;
            case 2:
                fading = true;
                inUse = false;
                clickables(false);
                nextState = StateManager.MAP;
                break;
            case 3:
                fading = true;
                inUse = false;
                clickables(false);
                nextState = StateManager.SHOPMENU;
                break;
            default:
                break;
        }
    }

    public void keyPressed(int k) {

    }

    public void keyReleased(int k) {

    }

    public void keyTyped(int k) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    //checks button presses and selects, checks if user has enough money when upgrade is pressed and upgrades
    public void mousePressed(MouseEvent e) {
        mBox.setLocation(e.getX() / GamePanel.SCALEX, e.getY() / GamePanel.SCALEY);

        if (dayButton.checkClick(mBox)) {
            sm.setState(StateManager.DAYMENU);
        }

        if (toMenu.checkClick(mBox)) {
            inUse = false;
            clickables(false);
            fading = true;
            try {
                OtherConstants.nightMusic.stop();
            } catch (BasicPlayerException ex) {
                ex.printStackTrace();
            }
            PlayerSettings.saveGame();
            nextState = StateManager.MAINMENU;
        }

        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].checkClick(mBox)) {
                select(i);
            }
        }

        if (shopUpgrade.checkClick(mBox)) {
            if (PlayerSettings.shopLevel + 1 <= 5 && Inventory.playerMoney - 10000 >= 0) {
                Inventory.playerMoney -= 10000;
                PlayerSettings.shopLevel++;
            } else if (PlayerSettings.shopLevel == 5) {
                shopUpgrade.setFontColor(Color.GRAY, Color.GRAY, Color.GRAY);
                shopUpgrade.setClickable(false);
            }
        }

    }

    public void mouseEntered(MouseEvent e) {

    }

    //Checks for hovering on each button
    public void mouseMoved(MouseEvent e) {
        mBox.setLocation(e.getX() / GamePanel.SCALEX, e.getY() / GamePanel.SCALEY);

        if (!dayButton.checkHover(mBox)) {
            dayButton.setHovered(false);
        }

        for (int i = 0; i < buttons.length; i++) {
            if (!buttons[i].checkHover(mBox)) {
                buttons[i].setHovered(false);
            }
        }

    }

    //Sets buttons to be unpressed when mouse is released
    public void mouseReleased(MouseEvent e) {
        dayButton.setPressed(false);
        shopUpgrade.setPressed(false);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setPressed(false);
        }
    }

    public void mouseDragged(MouseEvent e) {

    }

}
