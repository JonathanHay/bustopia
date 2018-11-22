/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

/**
 *
 * @author Jonathan
 */
import Backgrounds.*;
import Constants.BusConstants;
import Constants.EngineConstants;
import Constants.FuelTankConstants;
import Constants.Images;
import Constants.OtherConstants;
import Constants.RegionsConstants;
import Constants.WheelsConstants;
import Player.Inventory;
import haykimfinal.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import javafx.scene.media.AudioClip;

/**
 *
 * @author Jonathan
 */

//NEARLY IDENTICAL TO BusMenuState therefore *SEE BusMenuState
//Differences: Stats are not displayed when item is selected, only region and name. Region buttons exist which set region if pressed.
public class MapState extends GameState {

    private Background background;
    private HayButton goBack;

    private HayButton set1;
    private HayButton set2;
    private HayButton set3;

    private boolean fading;
    private Point mBox = new Point();

    private HayButton[] itemButtons;
    private HayButton[] emptyItems;

    private static int maxInventory = 180;

    private String[] itemName;
    private String[] busDescription;

    private int currentChoice;
    private boolean itemSelected;

    private FontMetrics metrics;

    URL resource;
    AudioClip SFX;

    URL resource2;
    AudioClip SFX2;

    public MapState(StateManager sm) {
        this.sm = sm;

        emptyItems = new HayButton[maxInventory];
        itemButtons = new HayButton[maxInventory];
        itemName = new String[maxInventory];
        busDescription = new String[maxInventory];

        for (int i = 0; i < emptyItems.length; i++) {
            emptyItems[i] = new HayButton(Images.EMPTYITEM_IMAGE, Images.LOCKEDITEM_IMAGE, "", OtherConstants.DEFAULT_FONT, 1, 1);
            emptyItems[i].setClickable(false);
        }

        set1 = new HayButton(Images.STATEBUTTON_IMAGE, Images.STATEBUTTON_IMAGE, "To City", OtherConstants.DEFAULT_FONT, 1, 1);
        set2 = new HayButton(Images.STATEBUTTON_IMAGE, Images.STATEBUTTON_IMAGE, "To Rural", OtherConstants.DEFAULT_FONT, 1, 1);
        set3 = new HayButton(Images.STATEBUTTON_IMAGE, Images.STATEBUTTON_IMAGE, "To Mountains", OtherConstants.DEFAULT_FONT, 1, 1);

        background = new Background("Resources/InventorySprites/inventory.png", false, false, 1, 1);
    }

    public void init() {
        for (int i = 0; i < maxInventory; i++) {
            itemName[i] = "";
            busDescription[i] = "";
        }
        currentChoice = -1;
        alpha = 1.0f;
        fading = false;
        goBack = new HayButton("Resources/NightBackground/backButton.png", "Resources/NightBackground/backButton.png", "Go Back", OtherConstants.DEFAULT_FONT, 1, 1);
        goBack.setPosition(555, 300);
        goBack.setClickable(true);

        set1.setPosition(455, 120);
        set2.setPosition(455, 170);
        set3.setPosition(455, 220);

        int row = 0;
        int column = 0;

        int currentSlot = 0;

        if (Inventory.playerBuses.size() > 0) {
            for (int i = 0; i < Inventory.playerBuses.size(); i++) {
                itemButtons[currentSlot] = new HayButton(Images.BUSES_IMAGE[Inventory.playerBuses.get(i).getType()], Images.BUSES_IMAGE[Inventory.playerBuses.get(i).getType()], "", OtherConstants.DEFAULT_FONT, 0.4, 0.4);
                emptyItems[currentSlot].setClickable(true);
                itemName[currentSlot] = Inventory.playerBuses.get(i).getName();
                busDescription[currentSlot] = "Current Region: " + RegionsConstants.TYPE_NAME[Inventory.playerBuses.get(i).getRegion()];
                currentSlot++;
            }
        }

        for (int i = currentSlot; i < maxInventory; i++) {
            itemButtons[i] = new HayButton(Images.EMPTYITEM_IMAGE, Images.EMPTYITEM_IMAGE, "", OtherConstants.DEFAULT_FONT, 1, 1);
            itemButtons[i].setVisible(false);
        }

        for (int i = 0; i < emptyItems.length; i++) {
            emptyItems[i].setPosition(50 + 21 * column, 63 + 21 * row);
            itemButtons[i].setPosition(50 + 21 * column, 63 + 21 * row);
            column++;
            if (column >= 15) {
                column = 0;
                row++;
            }
        }

        try {
            resource = ClassLoader.getSystemResource("Resources/SoundEffects/Pickup_01.wav");
            SFX = new AudioClip(resource.toString());
            SFX.setVolume(0.3);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            resource2 = ClassLoader.getSystemResource("Resources/SoundEffects/Hit_01.wav");
            SFX2 = new AudioClip(resource2.toString());
            SFX2.setVolume(0.3);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update() {
        if (!fading) {
            fadeOut(1);
        }
        if (fading) {
            if (fadeIn(1)) {
                sm.setState(StateManager.NIGHTMENU);
            }
        }
    }

    public void draw(Graphics2D g) {
        drawBlack(g, 1.0f);
        background.draw(g);
        g.setFont(OtherConstants.getFont(32F));
        g.drawString("Region Map", 50, 43);

        metrics = g.getFontMetrics(new Font("Arial", Font.TRUETYPE_FONT, 14));

        if (itemSelected && currentChoice >= 0) {
            g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
            g.drawString(itemName[currentChoice], 507 - (metrics.stringWidth(itemName[currentChoice]) / 2), 90);
            g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 10));
            drawWrappedString(g, busDescription[currentChoice], 420, 105, 180);
        }

        goBack.draw(g);
        if (itemSelected) {
            set1.draw(g);
            set2.draw(g);
            set3.draw(g);
        }

        for (int i = 0; i < emptyItems.length; i++) {
            emptyItems[i].draw(g);
            itemButtons[i].draw(g);
        }

        drawBlack(g, alpha);
    }

    public void drawWrappedString(Graphics2D g, String s, int x, int y, int width) {

        FontMetrics fm = g.getFontMetrics();

        int lineHeight = fm.getHeight();

        int curX = x;
        int curY = y;

        String[] words = s.split(" ");

        for (String word : words) {

            int wordWidth = fm.stringWidth(word + " ");

            if (curX + wordWidth >= x + width) {
                curY += lineHeight;
                curX = x;
            }

            g.drawString(word, curX, curY);

            curX += wordWidth;
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

    public void mousePressed(MouseEvent e) {

        mBox.setLocation(e.getX() / GamePanel.SCALEX, e.getY() / GamePanel.SCALEY);

        if (goBack.checkClick(mBox)) {
            fading = true;
            goBack.setClickable(false);
        }

        for (int i = 0; i < maxInventory; i++) {
            emptyItems[i].setPressed(false);
        }

        itemSelected = false;
        if (!set1.checkClick(mBox) && !set2.checkClick(mBox) && !set3.checkClick(mBox)) {
            currentChoice = -1;
        }

        for (int i = 0; i < maxInventory; i++) {
            if (emptyItems[i].checkClick(mBox)) {
                currentChoice = i;
                itemSelected = true;
            }
        }

        if (set1.checkClick(mBox)) {
            if (currentChoice >= 0) {
                if (!(Inventory.playerBuses.get(currentChoice).getRegion() == 0)) {
                    SFX.play();
                    Inventory.playerBuses.get(currentChoice).setRegion(0);
                    busDescription[currentChoice] = "Current Region: " + RegionsConstants.TYPE_NAME[Inventory.playerBuses.get(currentChoice).getRegion()];
                    currentChoice = -1;
                } else {
                    SFX2.play();
                }
            }
        } else if (set2.checkClick(mBox)) {
            if (currentChoice >= 0) {
                if (!(Inventory.playerBuses.get(currentChoice).getRegion() == 1)) {
                    SFX.play();
                    Inventory.playerBuses.get(currentChoice).setRegion(1);
                    busDescription[currentChoice] = "Current Region: " + RegionsConstants.TYPE_NAME[Inventory.playerBuses.get(currentChoice).getRegion()];
                    currentChoice = -1;
                } else {
                    SFX2.play();
                }
            }
        } else if (set3.checkClick(mBox)) {
            if (currentChoice >= 0) {
                if (!(Inventory.playerBuses.get(currentChoice).getRegion() == 2)) {
                    SFX.play();
                    Inventory.playerBuses.get(currentChoice).setRegion(2);
                    busDescription[currentChoice] = "Current Region: " + RegionsConstants.TYPE_NAME[Inventory.playerBuses.get(currentChoice).getRegion()];
                    currentChoice = -1;
                } else {
                    SFX2.play();
                }
            }
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

}
