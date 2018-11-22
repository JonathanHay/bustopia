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
import Constants.WheelsConstants;
import GameObjects.Bus;
import GameObjects.Engine;
import GameObjects.FuelTank;
import GameObjects.Wheels;
import Player.Inventory;
import Player.PlayerSettings;
import haykimfinal.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.AudioClip;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 *
 * @author Jonathan
 */
public class ShopState extends GameState {

    private static final int NOTYPE = -1;
    private static final int BUSTYPE = 0;
    private static final int ENGINETYPE = 1;
    private static final int WHEELTYPE = 2;
    private static final int TANKTYPE = 3;

    private int[] itemsType;

    private Background background;
    private Background sold;
    private boolean isSold[];
    private HayButton goBack;
    private boolean fading;
    private Point mBox = new Point();
    private Background[] tierSymbols;

    private HayButton[] itemButtons;
    private HayButton[] emptyItems;
    private HayButton buyItem;

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

    private String[] itemCost;

    private String[] stat1;
    private String[] stat2;

    private boolean buyable;

    private int cost = 0;

    private static ArrayList<Bus> shopBuses;
    private static ArrayList<Engine> shopEngines;
    private static ArrayList<FuelTank> shopTanks;
    private static ArrayList<Wheels> shopWheels;

    private int currentChoice;
    private boolean itemSelected;

    private FontMetrics metrics;

    URL resource;
    AudioClip SFX;

    URL resource2;
    AudioClip SFX2;

    public ShopState(StateManager sm) {
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
        stat1 = new String[maxInventory];
        stat2 = new String[maxInventory];
        itemsType = new int[maxInventory];
        itemCost = new String[maxInventory];
        isSold = new boolean[maxInventory];

        tierSymbols = new Background[maxInventory];

        for (int i = 0; i < emptyItems.length; i++) {
            emptyItems[i] = new HayButton(Images.EMPTYITEM_IMAGE, Images.LOCKEDITEM_IMAGE, "", OtherConstants.DEFAULT_FONT, 1, 1);
            emptyItems[i].setClickable(false);
        }

        buyItem = new HayButton(Images.STATEBUTTON_IMAGE, Images.STATEBUTTON_IMAGE, "BUY", OtherConstants.DEFAULT_FONT, 1, 1);

        background = new Background("Resources/InventorySprites/inventory.png", false, false, 1, 1);
        sold = new Background("Resources/InventorySprites/sold.png", false, false, 1, 1);
    }

    //Nearly identical to BusMenuState *SEE BusMenuState but creates a new array of shopbuses instead of using global inventory if a day has passed. Generates based on shop level
    public void init() {

        if (Inventory.regenShop) {

            shopBuses = new ArrayList<Bus>();
            shopEngines = new ArrayList<Engine>();
            shopTanks = new ArrayList<FuelTank>();
            shopWheels = new ArrayList<Wheels>();

            int randAmnt = randomInt(45, 1);

            int maxRarity = 1;

            if (PlayerSettings.shopLevel == 0) {
                maxRarity = 5;
            } else if (PlayerSettings.shopLevel == 1) {
                maxRarity = 10;
            } else if (PlayerSettings.shopLevel == 2) {
                maxRarity = 15;
            } else if (PlayerSettings.shopLevel == 3) {
                maxRarity = 20;
            } else if (PlayerSettings.shopLevel == 4) {
                maxRarity = 26;
            } else if (PlayerSettings.shopLevel == 5) {
                maxRarity = 27;
            }

            for (int i = 0; i < randAmnt; i++) {
                shopBuses.add(new Bus(randomInt(maxRarity, 0), randomInt(2, 0)));
            }
            randAmnt = randomInt(45, 1);
            for (int i = 0; i < randomInt(45, 1); i++) {
                shopEngines.add(new Engine(randomInt(10, 0), randomInt(2, 0)));
            }
            randAmnt = randomInt(45, 1);
            for (int i = 0; i < randomInt(45, 1); i++) {
                shopWheels.add(new Wheels(randomInt(7, 0), randomInt(2, 0)));
            }
            randAmnt = randomInt(45, 1);
            for (int i = 0; i < randomInt(45, 1); i++) {
                shopTanks.add(new FuelTank(randomInt(8, 0), randomInt(2, 0)));
            }
        }

        buyable = false;
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
            stat1[i] = "";
            stat2[i] = "";
            itemsType[i] = NOTYPE;
            itemCost[i] = "";
            if (Inventory.regenShop) {
                isSold[i] = false;
            }
        }

        alpha = 1.0f;
        fading = false;
        goBack = new HayButton("Resources/NightBackground/backButton.png", "Resources/NightBackground/backButton.png", "Go Back", OtherConstants.DEFAULT_FONT, 1, 1);
        goBack.setPosition(555, 300);
        goBack.setClickable(true);
        buyItem.setPosition(400, 295);
        buyItem.setClickable(true);

        int row = 0;
        int column = 0;

        int currentSlot = 0;

        if (shopBuses.size() > 0) {
            for (int i = 0; i < shopBuses.size(); i++) {
                itemButtons[currentSlot] = new HayButton(Images.BUSES_IMAGE[shopBuses.get(i).getType()], Images.BUSES_IMAGE[shopBuses.get(i).getType()], "", OtherConstants.DEFAULT_FONT, 0.4, 0.4);

                if (Inventory.regenShop) {
                    emptyItems[currentSlot].setClickable(true);
                }

                itemName[currentSlot] = shopBuses.get(i).getName();
                busDescription[currentSlot] = "DESCRIPTION: " + BusConstants.DESCRIPTION[shopBuses.get(i).getType()];
                eName[currentSlot] = "Engine:" + EngineConstants.TYPE_NAME[shopBuses.get(i).getEngine().getType()] + " (Tier " + String.valueOf(shopBuses.get(i).getEngine().getTier() + 1) + " )";
                eStat1[currentSlot] = "-Toughness:" + String.valueOf(shopBuses.get(i).getEngine().getToughness());
                eStat2[currentSlot] = "-Efficiency:" + String.valueOf(shopBuses.get(i).getEngine().getEfficiency());
                wName[currentSlot] = "Wheels:" + WheelsConstants.TYPE_NAME[shopBuses.get(i).getWheels().getType()] + " (Tier " + String.valueOf(shopBuses.get(i).getWheels().getTier() + 1) + " )";
                wStat1[currentSlot] = "-Toughness:" + String.valueOf(shopBuses.get(i).getWheels().getToughness());
                wStat2[currentSlot] = "-Speed:" + String.valueOf(shopBuses.get(i).getWheels().getSpeed());
                fName[currentSlot] = "Fuel Tank:" + FuelTankConstants.TYPE_NAME[shopBuses.get(i).getFuelTank().getType()] + " (Tier " + String.valueOf(shopBuses.get(i).getFuelTank().getTier() + 1) + " )";
                fStat1[currentSlot] = "-Toughness:" + String.valueOf(shopBuses.get(i).getFuelTank().getToughness());
                fStat2[currentSlot] = "-Tank Size:" + String.valueOf(shopBuses.get(i).getFuelTank().getFuelTankSize());

                tierSymbols[currentSlot] = new Background(Images.TIER1_IMAGE, false, false, 1, 1);
                tierSymbols[currentSlot].setVisible(false);
                itemsType[currentSlot] = BUSTYPE;
                itemCost[currentSlot] = "- $" + String.valueOf(BusConstants.COST[shopBuses.get(i).getType()]);
                currentSlot++;
            }
        }

        if (shopEngines.size() > 0) {
            for (int i = 0; i < shopEngines.size(); i++) {
                itemButtons[currentSlot] = new HayButton(Images.ENGINE_IMAGE, Images.ENGINE_IMAGE, "", OtherConstants.DEFAULT_FONT, 1, 1);
                if (shopEngines.get(i).getTier() == 0) {
                    tierSymbols[currentSlot] = new Background(Images.TIER1_IMAGE, false, false, 1, 1);
                } else if (shopEngines.get(i).getTier() == 1) {
                    tierSymbols[currentSlot] = new Background(Images.TIER2_IMAGE, false, false, 1, 1);
                } else if (shopEngines.get(i).getTier() == 2) {
                    tierSymbols[currentSlot] = new Background(Images.TIER3_IMAGE, false, false, 1, 1);
                }

                if (Inventory.regenShop) {
                    emptyItems[currentSlot].setClickable(true);
                }

                itemName[currentSlot] = EngineConstants.TYPE_NAME[shopEngines.get(i).getType()] + " (Tier " + String.valueOf(shopEngines.get(i).getTier() + 1) + " )";
                stat1[currentSlot] = "Toughness: " + String.valueOf(shopEngines.get(i).getToughness());
                stat2[currentSlot] = "Efficiency: " + String.valueOf(shopEngines.get(i).getEfficiency());
                itemsType[currentSlot] = ENGINETYPE;
                itemCost[currentSlot] = "- $" + String.valueOf(EngineConstants.COST[shopEngines.get(i).getType()] * (shopEngines.get(i).getTier() + 1));
                currentSlot++;
            }
        }

        if (shopWheels.size() > 0) {
            for (int i = 0; i < shopWheels.size(); i++) {
                itemButtons[currentSlot] = new HayButton(Images.WHEEL_IMAGE, Images.WHEEL_IMAGE, "", OtherConstants.DEFAULT_FONT, 1, 1);
                if (shopWheels.get(i).getTier() == 0) {
                    tierSymbols[currentSlot] = new Background(Images.TIER1_IMAGE, false, false, 1, 1);
                } else if (shopWheels.get(i).getTier() == 1) {
                    tierSymbols[currentSlot] = new Background(Images.TIER2_IMAGE, false, false, 1, 1);
                } else if (shopWheels.get(i).getTier() == 2) {
                    tierSymbols[currentSlot] = new Background(Images.TIER3_IMAGE, false, false, 1, 1);
                }

                if (Inventory.regenShop) {
                    emptyItems[currentSlot].setClickable(true);
                }

                itemName[currentSlot] = WheelsConstants.TYPE_NAME[shopWheels.get(i).getType()] + " (Tier " + String.valueOf(shopWheels.get(i).getTier() + 1) + " )";
                stat1[currentSlot] = "Toughness: " + String.valueOf(shopWheels.get(i).getToughness());
                stat2[currentSlot] = "Speed: " + String.valueOf(shopWheels.get(i).getSpeed());
                itemsType[currentSlot] = WHEELTYPE;
                itemCost[currentSlot] = "- $" + String.valueOf(WheelsConstants.COST[shopWheels.get(i).getType()] * (shopWheels.get(i).getTier() + 1));
                currentSlot++;
            }
        }

        if (shopTanks.size() > 0) {
            for (int i = 0; i < shopTanks.size(); i++) {
                itemButtons[currentSlot] = new HayButton(Images.FUELTANK_IMAGE, Images.FUELTANK_IMAGE, "", OtherConstants.DEFAULT_FONT, 1, 1);
                if (shopTanks.get(i).getTier() == 0) {
                    tierSymbols[currentSlot] = new Background(Images.TIER1_IMAGE, false, false, 1, 1);
                } else if (shopTanks.get(i).getTier() == 1) {
                    tierSymbols[currentSlot] = new Background(Images.TIER2_IMAGE, false, false, 1, 1);
                } else if (shopTanks.get(i).getTier() == 2) {
                    tierSymbols[currentSlot] = new Background(Images.TIER3_IMAGE, false, false, 1, 1);
                }

                if (Inventory.regenShop) {
                    emptyItems[currentSlot].setClickable(true);
                }

                itemName[currentSlot] = FuelTankConstants.TYPE_NAME[shopTanks.get(i).getType()] + " (Tier " + String.valueOf(shopTanks.get(i).getTier() + 1) + " )";
                stat1[currentSlot] = "Toughness: " + String.valueOf(shopTanks.get(i).getToughness());
                stat2[currentSlot] = "Tank Size: " + String.valueOf(shopTanks.get(i).getFuelTankSize());
                itemsType[currentSlot] = TANKTYPE;
                itemCost[currentSlot] = "- $" + String.valueOf(FuelTankConstants.COST[shopTanks.get(i).getType()] * (shopTanks.get(i).getTier() + 1));
                currentSlot++;
            }
        }

        Inventory.regenShop = false;

        for (int i = currentSlot; i < maxInventory; i++) {
            itemButtons[i] = new HayButton(Images.EMPTYITEM_IMAGE, Images.EMPTYITEM_IMAGE, "", OtherConstants.DEFAULT_FONT, 1, 1);
            itemButtons[i].setVisible(false);
            tierSymbols[i] = new Background(Images.TIER1_IMAGE, false, false, 1, 1);
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

    //Generates random integer
    public int randomInt(int max, int min) {
        return (int) Math.round(Math.random() * max + min);
    }

    //Sets state when done fading, loops music if ended
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

    //Draws background, items, money, and buy button. Also sold symbol if item is purchased
    public void draw(Graphics2D g) {
        drawBlack(g, 1.0f);
        background.draw(g);
        g.setFont(OtherConstants.getFont(32F));
        g.drawString("Shop", 50, 43);
        Color oldColor = g.getColor();
        g.setFont(OtherConstants.DEFAULT_FONT);
        g.setColor(Color.GREEN);
        g.drawString("$" + String.valueOf(Inventory.playerMoney), 430, 40);
        g.setColor(oldColor);

        
        if(!buyable){
            currentChoice = -1;
        }
        if (itemSelected && currentChoice >= 0) {
            g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
            metrics = g.getFontMetrics(new Font("Arial", Font.TRUETYPE_FONT, 14));
            g.drawString(itemName[currentChoice], 507 - (metrics.stringWidth(itemName[currentChoice]) / 2), 90);
            g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 10));
            drawWrappedString(g, busDescription[currentChoice], 420, 105, 180);
            g.setColor(Color.BLUE);
            g.drawString(eName[currentChoice], 435, 165);
            g.setColor(Color.GREEN);
            g.drawString(stat1[currentChoice], 440, 110);
            g.drawString(stat2[currentChoice], 440, 120);
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
            g.setColor(Color.RED);
            g.drawString(itemCost[currentChoice], 430, 50);

            g.setColor(oldColor);
        }

        goBack.draw(g);

        for (int i = 0; i < emptyItems.length; i++) {
            emptyItems[i].draw(g);
            itemButtons[i].draw(g);
            tierSymbols[i].draw(g);
        }

        int row = 0;
        int column = 0;

        for (int i = 0; i < emptyItems.length; i++) {
            if (isSold[i]) {
                sold.setPosition(50 + 21 * column, 63 + 21 * row);
                sold.draw(g);
            }
            column++;
            if (column >= 15) {
                column = 0;
                row++;
            }
        }

        buyItem.draw(g);

        drawBlack(g, alpha);
    }

    //*SEE BusMenuState
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

    //Checks clicks for items and buy button. If item is selected, buy button is clicked, and the user has enough money and inventory space the item is sold and added to player inventory, otherwise a failed sound is played
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
        if (!buyItem.checkClick(mBox)) {
            buyable = false;
        }

        for (int i = 0; i < maxInventory; i++) {
            if (emptyItems[i].checkClick(mBox)) {
                currentChoice = i;
                itemSelected = true;
                buyable = true;
            }
        }
        if (buyItem.checkClick(mBox)) {
            if (currentChoice >= 0) {
                if (itemsType[currentChoice] == BUSTYPE && Inventory.playerMoney >= BusConstants.COST[shopBuses.get(currentChoice).getType()]) {
                    SFX.play();
                    if (Inventory.numOfBuses < 180) {
                        Inventory.playerMoney -= BusConstants.COST[shopBuses.get(currentChoice).getType()];
                        Inventory.playerBuses.add(shopBuses.get(currentChoice));
                        Inventory.numOfBuses++;
                        emptyItems[currentChoice].setClickable(false);
                        isSold[currentChoice] = true;
                        buyable = false;
                    }
                } else if (itemsType[currentChoice] == ENGINETYPE && Inventory.playerMoney >= EngineConstants.COST[shopEngines.get(currentChoice - shopBuses.size()).getType()] * (shopEngines.get(currentChoice - shopBuses.size()).getTier() + 1)) {
                    SFX.play();
                    if (Inventory.numOfItems < 180) {
                        Inventory.playerMoney -= EngineConstants.COST[shopEngines.get(currentChoice - shopBuses.size()).getType()] * (shopEngines.get(currentChoice - shopBuses.size()).getTier() + 1);
                        Inventory.playerEngines.add(shopEngines.get(currentChoice - shopBuses.size()));
                        Inventory.numOfItems++;
                        emptyItems[currentChoice].setClickable(false);
                        isSold[currentChoice] = true;
                        buyable = false;
                    }
                } else if (itemsType[currentChoice] == WHEELTYPE && Inventory.playerMoney >= WheelsConstants.COST[shopWheels.get(currentChoice - (shopBuses.size() + shopEngines.size())).getType()] * (shopWheels.get(currentChoice - (shopBuses.size() + shopEngines.size())).getTier() + 1)) {
                    SFX.play();
                    if (Inventory.numOfItems < 180) {
                        Inventory.playerMoney -= WheelsConstants.COST[shopWheels.get(currentChoice - (shopBuses.size() + shopEngines.size())).getType()] * (shopWheels.get(currentChoice - (shopBuses.size() + shopEngines.size())).getTier() + 1);
                        Inventory.playerWheels.add(shopWheels.get(currentChoice - (shopBuses.size() + shopEngines.size())));
                        Inventory.numOfItems++;
                        emptyItems[currentChoice].setClickable(false);
                        isSold[currentChoice] = true;
                        buyable = false;
                    }
                } else if (itemsType[currentChoice] == TANKTYPE && Inventory.playerMoney >= FuelTankConstants.COST[shopTanks.get(currentChoice - (shopBuses.size() + shopEngines.size() + shopWheels.size())).getType()] * (shopTanks.get(currentChoice - (shopBuses.size() + shopEngines.size() + shopWheels.size())).getTier() + 1)) {
                    SFX.play();
                    if (Inventory.numOfItems < 180) {
                        Inventory.playerMoney -= FuelTankConstants.COST[shopTanks.get(currentChoice - (shopBuses.size() + shopEngines.size() + shopWheels.size())).getType()] * (shopTanks.get(currentChoice - (shopBuses.size() + shopEngines.size() + shopWheels.size())).getTier() + 1);
                        Inventory.playerTanks.add(shopTanks.get(currentChoice - (shopBuses.size() + shopEngines.size() + shopWheels.size())));
                        Inventory.numOfItems++;
                        emptyItems[currentChoice].setClickable(false);
                        isSold[currentChoice] = true;
                        buyable = false;
                    }
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
