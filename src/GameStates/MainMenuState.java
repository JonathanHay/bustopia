/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import Backgrounds.*;
import haykimfinal.GamePanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.net.URL;
import javafx.scene.media.*;

/**
 *
 * @author Jonathan
 */
public class MainMenuState extends GameState{

    private Background bg;
    private Background bg2;
    private Background bg3;
    private Background bg4;
    private AnimatedBackground bus;
    private Background menubox;
    private Background optionbox;
    private Rectangle textHitbox[];
    private Point mBox;
    private boolean optionsSelected = false;
    private String citybus[] = {"Resources/City_Bus/1.png", "Resources/City_Bus/2.png", "Resources/City_Bus/3.png", "Resources/City_Bus/4.png", "Resources/City_Bus/5.png", "Resources/City_Bus/6.png", "Resources/City_Bus/7.png", "Resources/City_Bus/8.png"};
    private int counter = 0;
    private int animNumber = 0;
    private double volume = 0.1;
    private int currentChoice = 0;
    private int hoverChoice = -1;
    private String[] options = {"NEW GAME", "LOAD GAME", "OPTIONS", "QUIT"};
    private Color c;
    private Font tf;
    private Font f;
    URL resource;
    AudioClip music;

    public MainMenuState(StateManager sm) {
        this.sm = sm;

        try {
            resource = ClassLoader.getSystemResource("Resources/music2.mp3");
            music = new AudioClip(resource.toString());

            bg = new Background("Resources/MenuBackground/layer1.png", 1, true);
            bg2 = new Background("Resources/MenuBackground/layer2.png", 1, true);
            bg3 = new Background("Resources/MenuBackground/layer3.png", 1, true);
            bg4 = new Background("Resources/MenuBackground/layer4.png", 1, true);
            bus = new AnimatedBackground(citybus, 10, 1, false);
            menubox = new Background("Resources/MenuBackground/menu.png", 1, false);
            optionbox = new Background("Resources/MenuBackground/options.png", 1, false);

            c = new Color(128, 0, 0);
            tf = new Font("Century Gothic", Font.BOLD, 32);
            f = new Font("Arial", Font.TRUETYPE_FONT, 14);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void init() {
        try {
            music.setCycleCount(-1);
            music.setVolume(volume);
            music.play();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        textHitbox = new Rectangle[4];
        for (int i = 0; i < 4; i++) {
            textHitbox[i] = new Rectangle(20, 167 + i * 15, 90, 11);
        }
        mBox = new Point(0, 0);
    }

    public void update() {
        bg.update();
        bg2.update();
        bg3.update();
        bg4.update();
        bus.update();
        menubox.update();
        optionbox.update();      
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        menubox.draw(g);
        if (currentChoice == 2) {
            optionbox.draw(g);
        }

        bg.setVelocity(0.01, 0);
        bg.draw(g);

        bg2.setVelocity(0.1, 0);
        bg2.draw(g);

        bg3.setVelocity(0.3, 0);
        bg3.draw(g);

        bg4.setVelocity(0.8, 0);
        bg4.draw(g);

        g.scale(0.5, 0.5);
        bus.setX(220);
        bus.setY(70);
        bus.draw(g);
        g.scale(2, 2);

        g.setColor(c);
        g.setFont(tf);
        g.drawString("BUSTOPIA", 91, 50);

        g.setFont(f);
        for (int i = 0; i < options.length; i++) {
            int indent = 0;
            if (i == currentChoice) {
                g.setColor(Color.RED);
                indent = 10;
            } else if (i == hoverChoice) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.DARK_GRAY);
                indent = 0;
            }
            g.drawString(options[i], 20 + indent, 178 + i * 15);
            //g.draw(textHitbox[i]);
        }
    }

    private void select() {
        switch (currentChoice) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                optionsSelected = true;
                break;
            case 3:
                System.exit(0);
        }
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            select();
        }
        if (k == KeyEvent.VK_UP) {
            currentChoice--;
            if (currentChoice < 0) {
                currentChoice = 0;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if (currentChoice > 3) {
                currentChoice = 3;
            }
        }

    }

    public void keyReleased(int k) {
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        mBox.setLocation(e.getX() / GamePanel.SCALE, e.getY() / GamePanel.SCALE);
        for (int i = 0; i < options.length; i++) {
            if (textHitbox[i].contains(mBox)) {
                currentChoice = i;
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        mBox.setLocation(e.getX() / GamePanel.SCALE, e.getY() / GamePanel.SCALE);
        boolean hovering = false;
        for (int i = 0; i < options.length; i++) {
            if (textHitbox[i].contains(mBox)) {
                hoverChoice = i;
                hovering = true;
            }
        }
        if(!hovering){
            hoverChoice = -1;
        }
    }
}
