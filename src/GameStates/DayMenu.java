/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import Backgrounds.AnimatedBackground;
import Backgrounds.Background;
import Backgrounds.HayButton;
import Backgrounds.LayeredBackground;
import Constants.BusConstants;
import Constants.Images;
import Constants.OtherConstants;
import Player.Inventory;
import Player.PlayerSettings;
import haykimfinal.GamePanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 *
 * @author Jonathan
 */
public class DayMenu extends GameState {

    private int dayTicks;
    private int timeScale;
    int hours;
    int minutes;
    private String amPm;
    private Background dayMenu;

    private Point mBox = new Point();

    private LayeredBackground region1;
    private LayeredBackground region2;
    private LayeredBackground region3;
    private Background nightTime;
    private Background dayTime;

    private HayButton left;
    private HayButton right;

    private AnimatedBackground[] wheels;
    private AnimatedBackground[] bus;

    private String WheelNames[][] = new String[12][4];

    private boolean fadeNight;

    private int tickMoney;
    private int tickScrap;

    private int currentBus;

    private float alpha2;

    private boolean isRed;

    private String[] backgroundNames1 = {"Resources/MenuBackground/layer2.png", "Resources/MenuBackground/layer3.png", "Resources/MenuBackground/layer4.png"};
    private String[] backgroundNames2 = {"Resources/TownBackground/layer2.png", "Resources/TownBackground/layer3.png", "Resources/TownBackground/layer4.png"};
    private String[] backgroundNames3 = {"Resources/MountainRegionImages/layer2.png", "Resources/MountainRegionImages/layer3.png", "Resources/MountainRegionImages/layer4.png", "Resources/MountainRegionImages/layer5.png"};

    private int fadeCounter2;

    public DayMenu(StateManager sm) {
        this.sm = sm;

        //Loads all of the wheels resources into string arrays
        WheelNames[0][0] = "Resources/Wheels/nothing.png";
        WheelNames[0][1] = "Resources/Wheels/nothing.png";
        WheelNames[0][2] = "Resources/Wheels/nothing.png";
        WheelNames[0][3] = "Resources/Wheels/nothing.png";

        WheelNames[1][0] = "Resources/Wheels/Type1_1.png";
        WheelNames[1][1] = "Resources/Wheels/Type1_2.png";
        WheelNames[1][2] = "Resources/Wheels/Type1_3.png";
        WheelNames[1][3] = "Resources/Wheels/Type1_4.png";

        WheelNames[2][0] = "Resources/Wheels/Type2_1.png";
        WheelNames[2][1] = "Resources/Wheels/Type2_2.png";
        WheelNames[2][2] = "Resources/Wheels/Type2_3.png";
        WheelNames[2][3] = "Resources/Wheels/Type2_4.png";

        WheelNames[3][0] = "Resources/Wheels/Type3_1.png";
        WheelNames[3][1] = "Resources/Wheels/Type3_2.png";
        WheelNames[3][2] = "Resources/Wheels/Type3_3.png";
        WheelNames[3][3] = "Resources/Wheels/Type3_4.png";

        WheelNames[4][0] = "Resources/Wheels/Type4_1.png";
        WheelNames[4][1] = "Resources/Wheels/Type4_2.png";
        WheelNames[4][2] = "Resources/Wheels/Type4_3.png";
        WheelNames[4][3] = "Resources/Wheels/Type4_4.png";

        WheelNames[5][0] = "Resources/Wheels/Type5_1.png";
        WheelNames[5][1] = "Resources/Wheels/Type5_2.png";
        WheelNames[5][2] = "Resources/Wheels/Type5_3.png";
        WheelNames[5][3] = "Resources/Wheels/Type5_4.png";

        WheelNames[6][0] = "Resources/Wheels/Type6_1.png";
        WheelNames[6][1] = "Resources/Wheels/Type6_2.png";
        WheelNames[6][2] = "Resources/Wheels/Type6_3.png";
        WheelNames[6][3] = "Resources/Wheels/Type6_4.png";

        WheelNames[7][0] = "Resources/Wheels/Type7_1.png";
        WheelNames[7][1] = "Resources/Wheels/Type7_2.png";
        WheelNames[7][2] = "Resources/Wheels/Type7_3.png";
        WheelNames[7][3] = "Resources/Wheels/Type7_4.png";

        WheelNames[8][0] = "Resources/Wheels/Type8_1.png";
        WheelNames[8][1] = "Resources/Wheels/Type8_2.png";
        WheelNames[8][2] = "Resources/Wheels/Type8_3.png";
        WheelNames[8][3] = "Resources/Wheels/Type8_4.png";

        WheelNames[9][0] = "Resources/Wheels/Type9_1.png";
        WheelNames[9][1] = "Resources/Wheels/Type9_2.png";
        WheelNames[9][2] = "Resources/Wheels/Type9_3.png";
        WheelNames[9][3] = "Resources/Wheels/Type9_4.png";

        WheelNames[10][0] = "Resources/Wheels/Type10_1.png";
        WheelNames[10][1] = "Resources/Wheels/Type10_2.png";
        WheelNames[10][2] = "Resources/Wheels/Type10_3.png";
        WheelNames[10][3] = "Resources/Wheels/Type10_4.png";

        WheelNames[11][0] = "Resources/Wheels/Type11_1.png";
        WheelNames[11][1] = "Resources/Wheels/Type11_2.png";
        WheelNames[11][2] = "Resources/Wheels/Type11_3.png";
        WheelNames[11][3] = "Resources/Wheels/Type11_4.png";

        //Sets the backgrounds for the menu, the 3 regions, the day and night sky, the buttons, and the buses and wheels
        dayMenu = new Background("Resources/DayBackgrounds/dayMenu.png", false, false, 1, 1);
        region1 = new LayeredBackground(backgroundNames1, true, false, 1, 1);
        region2 = new LayeredBackground(backgroundNames2, true, false, 1, 1);
        region3 = new LayeredBackground(backgroundNames3, true, false, 1, 1);
        dayTime = new Background("Resources/MenuBackground/layer1.png", true, false, 1, 1);
        nightTime = new Background("Resources/NightBackground/nightsky.png", true, false, 1, 1);
        left = new HayButton("Resources/DayBackgrounds/left.png", "Resources/DayBackgrounds/left.png", "", OtherConstants.DEFAULT_FONT, 1, 1);
        right = new HayButton("Resources/DayBackgrounds/right.png", "Resources/DayBackgrounds/right.png", "", OtherConstants.DEFAULT_FONT, 1, 1);
        bus = new AnimatedBackground[28];
        wheels = new AnimatedBackground[12];

        for (int i = 0; i < Images.BUSES_IMAGE.length; i++) {
            bus[i] = new AnimatedBackground(Images.BUSES_IMAGE[i], 20, 1, false, false, AnimatedBackground.AnimType.BOB, 2, 2);
        }
        for (int i = 0; i < WheelNames.length; i++) {
            wheels[i] = new AnimatedBackground(WheelNames[i], 20, 1, false, false, AnimatedBackground.AnimType.NORMAL, 1, 1);
        }
    }

    //Sets starting positions and velocities of all of the backgrounds, buttons, etc, resets buses *SEE Bus.newDay, resets money and scrap and time before day starts
    public void init() {
        isRed = false;
        Inventory.regenShop = true;
        PlayerSettings.day++;
        currentBus = 0;
        tickMoney = 0;
        tickScrap = 0;
        fadeCounter2 = 0;
        alpha2 = 0f;
        dayTicks = 0;
        timeScale = 0;
        hours = 6;
        minutes = 0;
        amPm = "am";
        fadeNight = false;

        if (Inventory.playerBuses.size() > 0) {
            for (int i = 0; i < Inventory.playerBuses.size(); i++) {
                Inventory.playerBuses.get(i).newDay();
            }
        }

        for (int i = 0; i < backgroundNames1.length; i++) {
            region1.setLayerPosition(i, 0, 40);
            region2.setLayerPosition(i, 0, 40);
        }

        for (int i = 0; i < backgroundNames3.length; i++) {
            region3.setLayerPosition(i, 0, 40);
        }

        dayTime.setPosition(0, 40);
        nightTime.setPosition(0, 40);

        left.setPosition(390, 200);
        right.setPosition(490, 200);

        dayTime.setVelocity(0.01, 0);
        nightTime.setVelocity(0.01, 0);

        region1.setLayerVelocity(0, 0.1, 0);
        region1.setLayerVelocity(1, 0.3, 0);
        region1.setLayerVelocity(2, 0.8, 0);

        region2.setLayerVelocity(0, 0.1, 0);
        region2.setLayerVelocity(1, 0.3, 0);
        region2.setLayerVelocity(2, 0.8, 0);

        region3.setLayerVelocity(0, 0.1, 0);
        region3.setLayerVelocity(1, 0.3, 0);
        region3.setLayerVelocity(2, 0.6, 0);
        region3.setLayerVelocity(3, 0.8, 0);

        for (int i = 0; i < bus.length; i++) {
            bus[i].setPosition(450, 70);
        }
        for (int i = 0; i < wheels.length; i++) {
            wheels[i].setPosition(450, 71);
        }
    }

    //Updates all items based on a timescale of 30, (30 ticks per update), loops music if needed, if the day reaches 8pm (84 ticks) returns to night menu
    public void update() {

        if (dayTicks >= 84) {
            for (int i = 0; i < Inventory.playerBuses.size(); i++) {
                Inventory.playerMoney += Inventory.playerBuses.get(i).getProfit();
                Inventory.playerScrap += Inventory.playerBuses.get(i).getScrap();
            }
            sm.setState(StateManager.NIGHTMENU);
        }
        if (timeScale > 30) {
            dayTicks++;
            minutes += 10;
            timeScale = 0;
            tickMoney = 0;
            tickScrap = 0;

            if (Inventory.playerBuses.size() > 0) {
                for (int i = 0; i < Inventory.playerBuses.size(); i++) {
                    Inventory.playerBuses.get(i).tick();
                }
            }

            for (int i = 0; i < Inventory.playerBuses.size(); i++) {
                tickMoney += Inventory.playerBuses.get(i).getProfit();
                tickScrap += Inventory.playerBuses.get(i).getScrap();
            }

            if (minutes >= 60) {
                minutes = 0;
                hours++;
            }
            if (hours > 12) {
                hours = 1;

            }
            if (hours == 12 && minutes == 0) {
                if (amPm.equals("am")) {
                    amPm = "pm";
                } else if (amPm.equals("pm")) {
                    amPm = "am";
                }
            }
        }
        timeScale++;
        region1.update();
        region2.update();
        region3.update();
        dayTime.update();

        if (fadeNight) {
            fadeCounter2++;
        }
        if (fadeCounter2 >= 10) {
            if ((alpha2 + 0.02f) <= 1.0f) {
                alpha2 += 0.02f;
                fadeCounter2 = 0;
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

    //Draws the buses and regions based on the one currently selected as well as all stats and buttons. Fades between night and day sky after 6pm and makes time text red
    public void draw(Graphics2D g) {

        drawBlack(g, 1.0f);
        dayTime.draw(g);
        g.setComposite(AlphaComposite.SrcOver.derive(alpha2));
        nightTime.draw(g);
        g.setComposite(AlphaComposite.SrcOver.derive(1.0f));
        if (Inventory.playerBuses.get(currentBus).getRegion() == 0) {
            region1.draw(g);
        } else if (Inventory.playerBuses.get(currentBus).getRegion() == 1) {
            region2.draw(g);
        } else if (Inventory.playerBuses.get(currentBus).getRegion() == 2) {
            region3.draw(g);
        }      
        Color lastColor = g.getColor();
        bus[Inventory.playerBuses.get(currentBus).getType()].draw(g);
        wheels[BusConstants.WHEELTYPE[Inventory.playerBuses.get(currentBus).getType()]].draw(g);
        g.setComposite(AlphaComposite.SrcOver.derive(alpha2 / 2f));
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setColor(lastColor);

        g.setComposite(AlphaComposite.SrcOver.derive(1.0f));
        dayMenu.draw(g);
        left.draw(g);
        right.draw(g);

        String timeString = String.format("%02d:%02d", hours, minutes);

        if (hours == 6 && amPm.equals("pm")) {
            isRed = true;
            fadeNight = true;
        }
        if (isRed) {
            g.setColor(Color.RED);
        }
        g.drawString("Time: " + timeString + amPm, 150, 320);

        g.setColor(Color.WHITE);

        g.setFont(OtherConstants.DEFAULT_FONT);

        String action = "";

        if (Inventory.playerBuses.get(currentBus).getAction() == -1) {
            action = "Not Operational";
        } else if (Inventory.playerBuses.get(currentBus).getAction() == 0) {
            action = "None";
        } else if (Inventory.playerBuses.get(currentBus).getAction() == 1) {
            action = "Filled Up";
        } else if (Inventory.playerBuses.get(currentBus).getAction() == 2) {
            action = "Passed Bus Stop";
        } else if (Inventory.playerBuses.get(currentBus).getAction() == 3) {
            action = "Filled Up/Bus Stop";
        }

        g.drawString(BusConstants.TYPE_NAME[Inventory.playerBuses.get(currentBus).getType()], 50, 107);
        g.drawString("Passengers: " + String.valueOf(Inventory.playerBuses.get(currentBus).getPassengers()) + "/" + String.valueOf(Inventory.playerBuses.get(currentBus).getMaxPassengers()), 50, 130);
        g.drawString("Fuel Level: " + String.valueOf(Inventory.playerBuses.get(currentBus).getGasPercentage()) + "%", 50, 150);
        g.drawString("Last Action: " + action, 50, 170);
        g.drawString("Bus Profit: " + String.valueOf(Inventory.playerBuses.get(currentBus).getProfit()) + " Dollars", 50, 190);
        g.drawString("Bus Scrap: " + String.valueOf(Inventory.playerBuses.get(currentBus).getScrap()) + " Scrap", 50, 210);
        g.drawString("Total Distance: " + String.valueOf(Inventory.playerBuses.get(currentBus).getTotalDistance()) + " kilomeme-ters", 50, 230);

        g.drawString(String.valueOf(tickMoney), 135, 295);
        g.drawString(String.valueOf(tickScrap), 235, 295);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("$", 125, 295);
        g.drawString("§", 225, 294);
    }

    public void keyPressed(int k) {

    }

    public void keyReleased(int k) {

    }

    public void keyTyped(int k) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    //Checks left and right buttons and cycles current bus accordingly
    public void mousePressed(MouseEvent e) {
        mBox.setLocation(e.getX() / GamePanel.SCALEX, e.getY() / GamePanel.SCALEY);
        if (left.checkClick(mBox)) {
            if (currentBus - 1 >= 0) {
                currentBus--;
            } else {
                currentBus = Inventory.playerBuses.size() - 1;
            }
        }
        if (right.checkClick(mBox)) {
            if (currentBus + 1 < Inventory.playerBuses.size()) {
                currentBus++;
            } else {
                currentBus = 0;
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
