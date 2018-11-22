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
public class InventoryState extends GameState {

    private static final int NOTYPE = -1;
    private static final int BUSTYPE = 0;
    private static final int ENGINETYPE = 1;
    private static final int WHEELTYPE = 2;
    private static final int TANKTYPE = 3;

    private Background background;
    private HayButton goBack;
    private HayButton applyItem;
    private boolean fading;
    private Point mBox = new Point();

    private HayButton[] itemButtons;
    private HayButton[] emptyItems;
    private Background[] tierSymbols;

    private static int maxInventory = 180;

    private String[] itemName;
    private String[] stat1;
    private String[] stat2;

    private int itemsType[];

    private boolean noChoice;

    private int currentChoice;
    private boolean itemSelected;

    private FontMetrics metrics;

    public InventoryState(StateManager sm) {
        this.sm = sm;

        emptyItems = new HayButton[maxInventory];
        itemButtons = new HayButton[maxInventory];
        tierSymbols = new Background[maxInventory];
        itemName = new String[maxInventory];
        stat1 = new String[maxInventory];
        stat2 = new String[maxInventory];
        itemsType = new int[maxInventory];

        for (int i = 0; i < emptyItems.length; i++) {
            emptyItems[i] = new HayButton(Images.EMPTYITEM_IMAGE, Images.LOCKEDITEM_IMAGE, "", OtherConstants.DEFAULT_FONT, 1, 1);
            emptyItems[i].setClickable(false);
        }

        background = new Background("Resources/InventorySprites/inventory.png", false, false, 1, 1);
    }

    //Sets stats, names, positions, and buttons to default, loads from global inventory
    public void init() {
        noChoice = true;
        for (int i = 0; i < maxInventory; i++) {
            itemName[i] = "";
            stat1[i] = "";
            stat2[i] = "";
            itemsType[i] = NOTYPE;
        }
        alpha = 1.0f;
        fading = false;
        goBack = new HayButton("Resources/NightBackground/backButton.png", "Resources/NightBackground/backButton.png", "Go Back", OtherConstants.DEFAULT_FONT, 1, 1);
        applyItem = new HayButton("Resources/MenuBackground/button1.png", "Resources/MenuBackground/button1down.png", "Install in Bus", OtherConstants.DEFAULT_FONT, 1, 1);
        applyItem.setPosition(445, 200);
        applyItem.setClickable(false);
        goBack.setPosition(555, 300);
        goBack.setClickable(true);

        int row = 0;
        int column = 0;

        int currentSlot = 0;

        if (Inventory.playerEngines.size() > 0) {
            for (int i = 0; i < Inventory.playerEngines.size(); i++) {
                itemButtons[currentSlot] = new HayButton(Images.ENGINE_IMAGE, Images.ENGINE_IMAGE, "", OtherConstants.DEFAULT_FONT, 1, 1);
                if (Inventory.playerEngines.get(i).getTier() == 0) {
                    tierSymbols[currentSlot] = new Background(Images.TIER1_IMAGE, false, false, 1, 1);
                } else if (Inventory.playerEngines.get(i).getTier() == 1) {
                    tierSymbols[currentSlot] = new Background(Images.TIER2_IMAGE, false, false, 1, 1);
                } else if (Inventory.playerEngines.get(i).getTier() == 2) {
                    tierSymbols[currentSlot] = new Background(Images.TIER3_IMAGE, false, false, 1, 1);
                }
                itemsType[currentSlot] = ENGINETYPE;
                emptyItems[currentSlot].setClickable(true);
                itemName[currentSlot] = EngineConstants.TYPE_NAME[Inventory.playerEngines.get(i).getType()] + " (Tier " + String.valueOf(Inventory.playerEngines.get(i).getTier() + 1) + " )";
                stat1[currentSlot] = "Toughness: " + String.valueOf(Inventory.playerEngines.get(i).getToughness());
                stat2[currentSlot] = "Efficiency: " + String.valueOf(Inventory.playerEngines.get(i).getEfficiency());
                currentSlot++;
            }
        }

        if (Inventory.playerWheels.size() > 0) {
            for (int i = 0; i < Inventory.playerWheels.size(); i++) {
                itemButtons[currentSlot] = new HayButton(Images.WHEEL_IMAGE, Images.WHEEL_IMAGE, "", OtherConstants.DEFAULT_FONT, 1, 1);
                if (Inventory.playerWheels.get(i).getTier() == 0) {
                    tierSymbols[currentSlot] = new Background(Images.TIER1_IMAGE, false, false, 1, 1);
                } else if (Inventory.playerWheels.get(i).getTier() == 1) {
                    tierSymbols[currentSlot] = new Background(Images.TIER2_IMAGE, false, false, 1, 1);
                } else if (Inventory.playerWheels.get(i).getTier() == 2) {
                    tierSymbols[currentSlot] = new Background(Images.TIER3_IMAGE, false, false, 1, 1);
                }
                itemsType[currentSlot] = WHEELTYPE;
                emptyItems[currentSlot].setClickable(true);
                itemName[currentSlot] = WheelsConstants.TYPE_NAME[Inventory.playerWheels.get(i).getType()] + " (Tier " + String.valueOf(Inventory.playerWheels.get(i).getTier() + 1) + " )";
                stat1[currentSlot] = "Toughness: " + String.valueOf(Inventory.playerWheels.get(i).getToughness());
                stat2[currentSlot] = "Speed: " + String.valueOf(Inventory.playerWheels.get(i).getSpeed());
                currentSlot++;
            }
        }

        if (Inventory.playerTanks.size() > 0) {
            for (int i = 0; i < Inventory.playerTanks.size(); i++) {
                itemButtons[currentSlot] = new HayButton(Images.FUELTANK_IMAGE, Images.FUELTANK_IMAGE, "", OtherConstants.DEFAULT_FONT, 1, 1);
                if (Inventory.playerTanks.get(i).getTier() == 0) {
                    tierSymbols[currentSlot] = new Background(Images.TIER1_IMAGE, false, false, 1, 1);
                } else if (Inventory.playerTanks.get(i).getTier() == 1) {
                    tierSymbols[currentSlot] = new Background(Images.TIER2_IMAGE, false, false, 1, 1);
                } else if (Inventory.playerTanks.get(i).getTier() == 2) {
                    tierSymbols[currentSlot] = new Background(Images.TIER3_IMAGE, false, false, 1, 1);
                }
                itemsType[currentSlot] = TANKTYPE;
                emptyItems[currentSlot].setClickable(true);
                itemName[currentSlot] = FuelTankConstants.TYPE_NAME[Inventory.playerTanks.get(i).getType()] + " (Tier " + String.valueOf(Inventory.playerTanks.get(i).getTier() + 1) + " )";
                stat1[currentSlot] = "Toughness: " + String.valueOf(Inventory.playerTanks.get(i).getToughness());
                stat2[currentSlot] = "Tank Size: " + String.valueOf(Inventory.playerTanks.get(i).getFuelTankSize());
                currentSlot++;
            }
        }

        for (int i = currentSlot; i < maxInventory; i++) {
            itemButtons[i] = new HayButton(Images.EMPTYITEM_IMAGE, Images.EMPTYITEM_IMAGE, "", OtherConstants.DEFAULT_FONT, 1, 1);
            tierSymbols[i] = new Background(Images.TIER1_IMAGE, false, false, 1, 1);
            itemButtons[i].setVisible(false);
            tierSymbols[i].setVisible(false);
        }

        for (int i = 0; i < emptyItems.length; i++) {
            emptyItems[i].setPosition(50 + 21 * column, 63 + 21 * row);
            itemButtons[i].setPosition(50 + 21 * column, 63 + 21 * row);
            tierSymbols[i].setPosition(50 + 21 * column, 63 + 21 * row);
            column++;
            if (column >= 15) {
                column = 0;
                row++;
            }
        }
    }

    //Fades in and out, loops music if ended
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

    //Draws background, items, and itemstats if item is selected
    public void draw(Graphics2D g) {
        drawBlack(g, 1.0f);
        background.draw(g);
        g.setFont(OtherConstants.getFont(32F));
        g.drawString("Inventory", 50, 43);
        g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));

        if (noChoice) {
            currentChoice = -1;
            applyItem.setClickable(false);
        }

        if (itemSelected && currentChoice >= 0) {
            applyItem.setClickable(true);
            metrics = g.getFontMetrics(new Font("Arial", Font.TRUETYPE_FONT, 14));
            g.drawString(itemName[currentChoice], 507 - (metrics.stringWidth(itemName[currentChoice]) / 2), 90);
            Color oldColor = g.getColor();
            g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 10));
            g.setColor(Color.GREEN);
            g.drawString(stat1[currentChoice], 440, 110);
            g.drawString(stat2[currentChoice], 440, 120);
            g.setColor(oldColor);
            applyItem.draw(g);
        }

        goBack.draw(g);

        for (int i = 0; i < emptyItems.length; i++) {
            emptyItems[i].draw(g);
            itemButtons[i].draw(g);
            tierSymbols[i].draw(g);
        }

        drawBlack(g, alpha);
    }

    //*SEE drawWrappedString in BusMenuState
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

    //Checks item button presses and sets the current item choice to pressed buttons 
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
        if (!applyItem.checkClick(mBox)) {
            System.out.println("DIDN'T CLICK APPLY");
            noChoice = true;
        }

        for (int i = 0; i < maxInventory; i++) {
            if (emptyItems[i].checkClick(mBox)) {
                currentChoice = i;
                noChoice = false;
                itemSelected = true;
                System.out.println("APPLYCLICKABLE");
                applyItem.setClickable(true);
            }
        }

        if (applyItem.checkClick(mBox)) {
            System.out.println("CLICKEDAPPLY");
            System.out.println(currentChoice);
            if (currentChoice >= 0) {
                System.out.println("CHECKS");
                System.out.println(itemsType[currentChoice]);
                if (itemsType[currentChoice] == ENGINETYPE) {
                    System.out.println("ApplyEngine");
                    Inventory.applyChoice = OtherConstants.APPLYENGINE;
                    Inventory.applyEngine = Inventory.playerEngines.get(currentChoice);
                    Inventory.applyType = currentChoice;
                    noChoice = true;
                    sm.setState(StateManager.APPLYSTATE);
                } else if (itemsType[currentChoice] == WHEELTYPE) {
                    System.out.println("ApplyWheels");
                    Inventory.applyChoice = OtherConstants.APPLYWHEELS;
                    Inventory.applyWheels = Inventory.playerWheels.get(currentChoice - Inventory.playerEngines.size());
                    Inventory.applyType = currentChoice - Inventory.playerEngines.size();
                    noChoice = true;
                    sm.setState(StateManager.APPLYSTATE);
                } else if (itemsType[currentChoice] == TANKTYPE) {
                    System.out.println("ApplyTank");
                    Inventory.applyChoice = OtherConstants.APPLYFUELTANK;
                    Inventory.applyTank = Inventory.playerTanks.get(currentChoice - (Inventory.playerEngines.size() + Inventory.playerWheels.size()));       
                    Inventory.applyType = currentChoice - (Inventory.playerEngines.size() + Inventory.playerWheels.size());
                    noChoice = true;
                    sm.setState(StateManager.APPLYSTATE);
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
