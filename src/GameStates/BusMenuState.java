/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import Backgrounds.*;
import Constants.BusConstants;
import Constants.EngineConstants;
import Constants.FuelTankConstants;
import Constants.Images;
import Constants.OtherConstants;
import Constants.WheelsConstants;
import Player.Inventory;
import Player.PlayerSettings;
import haykimfinal.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 *
 * @author Jonathan
 */

public class BusMenuState extends GameState {

    private Background background;
    private HayButton goBack;
    private boolean fading;
    private Point mBox = new Point();

    private HayButton[] itemButtons;
    private HayButton[] emptyItems;

    private static int maxInventory = 180;

    private String[] itemName;
    private String[] busDescription;
    private String[] eName;
    private String[] eStat1;
    private String[] eStat2;
    private String[] wName;
    private String[] wStat1;
    private String[] wStat2;
    private String[] fName;
    private String[] fStat1;
    private String[] fStat2;

    private int currentChoice;
    private boolean itemSelected;
    
    private FontMetrics metrics;
    //Sets array sizes and instantiates buttons and backgrounds that will not change
    public BusMenuState(StateManager sm) {
        this.sm = sm;

        emptyItems = new HayButton[maxInventory];
        itemButtons = new HayButton[maxInventory];
        itemName = new String[maxInventory];
        busDescription = new String[maxInventory];
        eName = new String[maxInventory];
        eStat1 = new String[maxInventory];
        eStat2 = new String[maxInventory];
        wName = new String[maxInventory];
        wStat1 = new String[maxInventory];
        wStat2 = new String[maxInventory];
        fName = new String[maxInventory];
        fStat1 = new String[maxInventory];
        fStat2 = new String[maxInventory];

        for (int i = 0; i < emptyItems.length; i++) {
            emptyItems[i] = new HayButton(Images.EMPTYITEM_IMAGE, Images.LOCKEDITEM_IMAGE, "", OtherConstants.DEFAULT_FONT, 1, 1);
            emptyItems[i].setClickable(false);
        }

        background = new Background("Resources/InventorySprites/inventory.png", false, false, 1, 1);
    }

    //Sets all names, descriptions, and stats for the player's buses and sets the position of the items in columns and rows
    public void init() {
        for (int i = 0; i < maxInventory; i++) {
            itemName[i] = "";
            busDescription[i] = "";
            eName[i] = "";
            eStat1[i] = "";
            eStat2[i] = "";
            wName[i] = "";
            wStat1[i] = "";
            wStat2[i] = "";
            fName[i] = "";
            fStat1[i] = "";
            fStat2[i] = "";
        }
        currentChoice = -1;
        alpha = 1.0f;
        fading = false;
        goBack = new HayButton("Resources/NightBackground/backButton.png", "Resources/NightBackground/backButton.png", "Go Back", OtherConstants.DEFAULT_FONT, 1, 1);
        goBack.setPosition(555, 300);
        goBack.setClickable(true);

        int row = 0;
        int column = 0;

        int currentSlot = 0;

        if (Inventory.playerBuses.size() > 0) {
            for (int i = 0; i < Inventory.playerBuses.size(); i++) {
                itemButtons[currentSlot] = new HayButton(Images.BUSES_IMAGE[Inventory.playerBuses.get(i).getType()], Images.BUSES_IMAGE[Inventory.playerBuses.get(i).getType()], "", OtherConstants.DEFAULT_FONT, 0.4, 0.4);
                emptyItems[currentSlot].setClickable(true);
                itemName[currentSlot] = Inventory.playerBuses.get(i).getName();
                busDescription[currentSlot] = "DESCRIPTION: " + BusConstants.DESCRIPTION[Inventory.playerBuses.get(i).getType()];
                eName[currentSlot] = "Engine:" + EngineConstants.TYPE_NAME[Inventory.playerBuses.get(i).getEngine().getType()] + " (Tier " + String.valueOf(Inventory.playerBuses.get(i).getEngine().getTier() + 1) + " )";
                eStat1[currentSlot] = "-Toughness:" + String.valueOf(Inventory.playerBuses.get(i).getEngine().getToughness());
                eStat2[currentSlot] = "-Efficiency:" + String.valueOf(Inventory.playerBuses.get(i).getEngine().getEfficiency());
                wName[currentSlot] = "Wheels:" + WheelsConstants.TYPE_NAME[Inventory.playerBuses.get(i).getWheels().getType()] + " (Tier " + String.valueOf(Inventory.playerBuses.get(i).getWheels().getTier() + 1) + " )";
                wStat1[currentSlot] = "-Toughness:" + String.valueOf(Inventory.playerBuses.get(i).getWheels().getToughness());
                wStat2[currentSlot] = "-Speed:" + String.valueOf(Inventory.playerBuses.get(i).getWheels().getSpeed());
                fName[currentSlot] = "Fuel Tank:" + FuelTankConstants.TYPE_NAME[Inventory.playerBuses.get(i).getFuelTank().getType()] + " (Tier " + String.valueOf(Inventory.playerBuses.get(i).getFuelTank().getTier() + 1) + " )";
                fStat1[currentSlot] = "-Toughness:" + String.valueOf(Inventory.playerBuses.get(i).getFuelTank().getToughness());
                fStat2[currentSlot] = "-Tank Size:" + String.valueOf(Inventory.playerBuses.get(i).getFuelTank().getFuelTankSize());
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
    }

    //Fades out when the state is opened, fades in when it is left, loops music if it has ended
    public void update() {
        if (!fading) {
            fadeOut(1);
        }
        if (fading) {
            if (fadeIn(1)) {
                sm.setState(StateManager.NIGHTMENU);
            }
        }
        
        if (OtherConstants.nightMusic.getStatus() != 0) {
            try {
                OtherConstants.nightMusic.play();
                OtherConstants.nightMusic.setGain(PlayerSettings.volume);
            } catch (BasicPlayerException ex) {
                Logger.getLogger(NightMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Draws a black rectangle to wipe the screen (This is always done in draw so I will not mention it every time), then draws the background, buttons, and items as well as the title, and the item stats if an item is selected
    public void draw(Graphics2D g) {
        drawBlack(g, 1.0f);
        background.draw(g);
        g.setFont(OtherConstants.getFont(32F));
        g.drawString("Bus Inventory", 50, 43);
        
        metrics = g.getFontMetrics(new Font("Arial", Font.TRUETYPE_FONT, 14));
              

        if (itemSelected && currentChoice >= 0) {
            g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));          
            g.drawString(itemName[currentChoice], 507 -(metrics.stringWidth(itemName[currentChoice]) / 2), 90);
            g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 10));
            drawWrappedString(g, busDescription[currentChoice], 420, 105, 180);
            Color oldColor = g.getColor();
            g.setColor(Color.BLUE);
            g.drawString(eName[currentChoice], 435, 165);
            g.setColor(Color.GREEN);
            g.drawString(eStat1[currentChoice], 440, 180);
            g.drawString(eStat2[currentChoice], 440, 190);

            g.setColor(Color.BLUE);
            g.drawString(wName[currentChoice], 435, 205);
            g.setColor(Color.GREEN);
            g.drawString(wStat1[currentChoice], 440, 220);
            g.drawString(wStat2[currentChoice], 440, 230);

            g.setColor(Color.BLUE);
            g.drawString(fName[currentChoice], 435, 245);
            g.setColor(Color.GREEN);
            g.drawString(fStat1[currentChoice], 440, 260);
            g.drawString(fStat2[currentChoice], 440, 270);

            g.setColor(oldColor);
        }

        goBack.draw(g);

        for (int i = 0; i < emptyItems.length; i++) {
            emptyItems[i].draw(g);
            itemButtons[i].draw(g);
        }

        drawBlack(g, alpha);
    }

    //Draws a string that wraps to a certain width as opposed to continuing indefinitely (used for descriptions)
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

    //Checks the hitboxes of all of the buttons (performs actions such as going back to menu, fading, and item selection
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

        for (int i = 0; i < maxInventory; i++) {
            if (emptyItems[i].checkClick(mBox)) {
                currentChoice = i;
                itemSelected = true;
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
