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
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.*;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 *
 * @author Jonathan
 */
public class MainMenuState extends GameState {

    private Background title;
    private Background bg;
    private Background bg2;
    private Background bg3;
    private Background bg4;
    private AnimatedBackground wheels;
    private AnimatedBackground bus;
    private Background menubox;
    private Background menuboxtop;
    private Background optionbox;

    private HayButton buttons[];
    private HayButton fullButton;

    private Point mBox;
    private boolean optionsSelected = false;
    private String citybus[] = {"Resources/City_Bus/9.png"};
    private String wheelImg[] = {"Resources/Wheel1/1.png", "Resources/Wheel1/2.png", "Resources/Wheel1/3.png", "Resources/Wheel1/4.png"};
    private int counter = 0;
    private double volume = 0.1;
    private int currentChoice = -1;
    private int hoverChoice = -1;
    private String[] options = {"NEW GAME", "LOAD GAME", "OPTIONS", "CREDITS", "QUIT"};
    private Color c;
    private Font f;
    private Font f2;
    private String fps = " ";
    private URL resource;
    private URL resource2;
    private BasicPlayer music;
    private FontMetrics metrics;
    private File fontfile;
    private float alpha;
    private int fadeCounter = 0;

    private boolean newGame;

    public MainMenuState(StateManager sm) {
        this.sm = sm;

        try {
            newGame = false;
            alpha = 0;
            resource = ClassLoader.getSystemResource("Resources/music2.mp3");
            music = new BasicPlayer();
            music.open(resource);
            music.play();
            music.setGain(volume);
            title = new Background("Resources/MenuBackground/title.png", 1, false, false, 1, 1);
            bg = new Background("Resources/MenuBackground/menulayer1.png", 1, true, false, 1, 1);
            bg2 = new Background("Resources/MenuBackground/layer2.png", 1, true, false, 1, 1);
            bg3 = new Background("Resources/MenuBackground/layer3.png", 1, true, false, 1, 1);
            bg4 = new Background("Resources/MenuBackground/layer4.png", 1, true, false, 1, 1);
            wheels = new AnimatedBackground(wheelImg, 5, 1, false, false, AnimatedBackground.AnimType.NORMAL, 1, 1);
            bus = new AnimatedBackground(citybus, 5, 1, false, false, AnimatedBackground.AnimType.BOB, 2, 2);
            menubox = new Background("Resources/MenuBackground/menu.png", 1, false, false, 1, 1);
            menuboxtop = new Background("Resources/MenuBackground/menutop.png", 1, false, false, 1, 1);
            optionbox = new Background("Resources/MenuBackground/options.png", 1, false, false, 2, 2);
            buttons = new HayButton[options.length];

            c = new Color(128, 0, 0);
            f = Font.createFont(Font.PLAIN, ClassLoader.getSystemResourceAsStream("Resources/Fonts/pixelate.ttf"));
            f = f.deriveFont(f.getSize() * 16F);
            f2 = new Font("Arial", Font.PLAIN, 10);

            for (int i = 0; i < buttons.length; i++) {
                buttons[i] = new HayButton("Resources/MenuBackground/button1.png", "Resources/MenuBackground/button1down.png", options[i], f, 1, 1);
                buttons[i].setFontColor(Color.DARK_GRAY, Color.GRAY, Color.WHITE);
                buttons[i].setIndent(0, 1);
                buttons[i].setPosition(20 + i * 120, 280);
            }

            fullButton = new HayButton("Resources/TickBox/slider.png", "Resources/TickBox/sliderdown.png", "Fullscreen", f, 2, 2);
            fullButton.setOffset(0, 20);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void init() {

        mBox = new Point(0, 0);

        title.setPosition(125, 15);
        bus.setPosition(275, 145);
        wheels.setPosition(275, 146);
        menubox.setPosition(0, 140);
        menuboxtop.setPosition(0, 260);
        bg2.setPosition(0, 110);
        bg3.setPosition(0, 110);
        bg4.setPosition(0, 110);
        bg.setVelocity(0.01, 0);
        bg2.setVelocity(0.1, 0);
        bg3.setVelocity(0.3, 0);
        bg4.setVelocity(0.8, 0);
        optionbox.setPosition(-100, -120);
        fullButton.setPosition(400, 250);
        fullButton.setClickable(false);

    }

    public void update() {
        bg.update();
        bg2.update();
        bg3.update();
        bg4.update();
        if (newGame) {
            fadeIn(1);
            if (alpha >= 0.99) {
                try {
                    music.stop();
                } catch (BasicPlayerException ex) {
                    ex.printStackTrace();
                }
                sm.setState(2);
            }
        }

        if (music.getStatus() != 0 && !newGame) {
            try {
                music.play();
                music.setGain(volume);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                music.setGain(volume);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        optionbox.update();
        menuboxtop.update();
        wheels.update();
        bus.update();
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].update();
        }
        fullButton.update();
        clickables();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setComposite(AlphaComposite.SrcOver.derive(1.0f));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        menubox.draw(g);
        menuboxtop.draw(g);

        bg.draw(g);
        bg2.draw(g);
        bg3.draw(g);
        bg4.draw(g);

        bus.draw(g);
        wheels.draw(g);
        title.draw(g);

        g.setFont(f);
        metrics = g.getFontMetrics(f);

        for (int i = 0; i < options.length; i++) {
            if (i == hoverChoice) {
                buttons[i].setHovered(true);
            } else {
                buttons[i].setHovered(false);
            }
            if (i == currentChoice) {
                buttons[i].setPressed(true);
            } else {
                buttons[i].setPressed(false);
            }
            buttons[i].draw(g);
        }
        if (GamePanel.ticks == 60) {
            fps = String.valueOf(GamePanel.frames);
        }

        if (fullButton.getClickable() && !bg.getMoving()) {
            optionbox.draw(g);
            fullButton.draw(g);
        }

        g.setFont(f2);
        g.drawString(fps, 1, 8);
        g.setColor(Color.BLACK);
        g.setComposite(AlphaComposite.SrcOver.derive(alpha));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

    }

    public void clickables() {
        if (bg.getMoving()) {
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setClickable(false);
            }
            fullButton.setClickable(false);
        } else if (!bg.getMoving()) {
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setClickable(true);
            }
            if (currentChoice == 2) {
                fullButton.setClickable(true);
            } else {
                fullButton.setClickable(false);
            }
        }
    }

    private void menuUp() {
        bg.moveToPosition(0, -120, 4, false, true);
        bg2.moveToPosition(0, -120, 4, false, true);
        bg3.moveToPosition(0, -120, 4, false, true);
        bg4.moveToPosition(0, -120, 4, false, true);
        bus.moveToPosition(0, -120, 4, false, true);
        wheels.moveToPosition(0, -120, 4, false, true);
        menuboxtop.moveToPosition(0, -120, 4, false, true);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].moveToPosition(0, -120, 4, false, true);
        }
    }

    private void fadeOut(int i) {
        fadeCounter++;
        if (fadeCounter >= i) {
            if ((alpha - 0.01) >= 0) {
                alpha -= 0.01;
            }
            fadeCounter = 0;
        }
    }

    private void fadeIn(int i) {
        fadeCounter++;
        if (fadeCounter >= i) {
            if ((alpha + 0.01) <= 1.0) {
                alpha += 0.01;
            }
            fadeCounter = 0;
        }
    }

    private void menuDown() {
        bg.moveToPosition(0, 120, 4, false, true);
        bg2.moveToPosition(0, 120, 4, false, true);
        bg3.moveToPosition(0, 120, 4, false, true);
        bg4.moveToPosition(0, 120, 4, false, true);
        bus.moveToPosition(0, 120, 4, false, true);
        wheels.moveToPosition(0, 120, 4, false, true);
        menuboxtop.moveToPosition(0, 120, 4, false, true);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].moveToPosition(0, 120, 4, false, true);
        }
    }

    private void select() {
        if (!bg.getMoving()) {
            currentChoice = hoverChoice;
        }
        switch (currentChoice) {
            case 0:
                newGame = true;
                if (!optionsSelected && !bg.getMoving()) {
                    menuUp();
                    optionsSelected = true;
                }
                break;
            case 1:
                if (!optionsSelected && !bg.getMoving()) {
                    menuUp();
                    optionsSelected = true;
                }
                break;
            case 2:
                if (!optionsSelected && !bg.getMoving()) {
                    menuUp();
                    optionsSelected = true;
                }
                break;
            case 3:
                if (!optionsSelected && !bg.getMoving()) {
                    menuUp();
                    optionsSelected = true;
                }
                break;
            case 4:
                System.exit(0);
        }
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            select();
        }
        if (k == KeyEvent.VK_LEFT) {
            if (hoverChoice - 1 < 0) {
                hoverChoice = 0;
            } else {
                hoverChoice--;
            }
        }
        if (k == KeyEvent.VK_RIGHT) {
            if (hoverChoice + 1 > options.length - 1) {
                hoverChoice = options.length - 1;
            } else {
                hoverChoice++;
            }
        }
        if (k == KeyEvent.VK_ESCAPE && optionsSelected && !bg.getMoving()) {
            optionsSelected = false;
            fullButton.setClickable(false);
            currentChoice = -1;
            menuDown();
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
            buttons[i].setPressed(false);
            if (buttons[i].checkClick(mBox)) {
                currentChoice = i;
                select();
            }
            if (fullButton.checkClick(mBox)) {
                GamePanel.toggleFullscreen();
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        mBox.setLocation(e.getX() / GamePanel.SCALE, e.getY() / GamePanel.SCALE);

        for (int i = 0; i < options.length; i++) {
            buttons[i].setHovered(false);
            if (buttons[i].checkHover(mBox)) {
                hoverChoice = i;
            }
        }
    }
}
