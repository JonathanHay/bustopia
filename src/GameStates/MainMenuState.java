/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import Backgrounds.*;
import Constants.Buses;
import Player.PlayerSettings;
import haykimfinal.GamePanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
 import javafx.scene.media.*;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 *
 * @author Jonathan
 */
public class MainMenuState extends GameState {

    private Background title;

    private LayeredBackground backgroundScene;

    private String[] backgroundNames = {"Resources/MenuBackground/menulayer1.png", "Resources/MenuBackground/layer2.png", "Resources/MenuBackground/layer3.png", "Resources/MenuBackground/layer4.png"};

    private AnimatedBackground wheels;
    private AnimatedBackground bus;
    private Background menubox;
    private Background menuboxtop;
    private Background optionbox;

    private HayButton buttons[];
    private HayButton fullButton;
    private HayButton musicSlider;

    private Point mBox;
    private boolean optionsSelected = false;
    private String citybus[] = {Buses.CITYBUS};
    private int counter = 0;
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

    private boolean newGame;
    private boolean loadGame;

    public MainMenuState(StateManager sm) {
        this.sm = sm;

        try {
            PlayerSettings.volume = 0.15;
            resource = ClassLoader.getSystemResource("Resources/music3.mp3");
            music = new BasicPlayer();
            title = new Background("Resources/MenuBackground/title.png", false, false, 1, 1);

            backgroundScene = new LayeredBackground(backgroundNames, true, false, 1, 1);

            wheels = new AnimatedBackground(Buses.WHEEL1, 20, 1, false, false, AnimatedBackground.AnimType.NORMAL, 1, 1);
            bus = new AnimatedBackground(citybus, 20, 1, false, false, AnimatedBackground.AnimType.BOB, 2, 2);
            menubox = new Background("Resources/MenuBackground/menu.png", false, false, 1, 1);
            menuboxtop = new Background("Resources/MenuBackground/menutop.png", false, false, 1, 1);
            optionbox = new Background("Resources/MenuBackground/options2.png", false, false, 2, 2);
            buttons = new HayButton[options.length];

            c = new Color(128, 0, 0);
            f = Font.createFont(Font.PLAIN, ClassLoader.getSystemResourceAsStream("Resources/Fonts/pixelate.ttf"));
            f = f.deriveFont(f.getSize() * 16F);
            f2 = new Font("Arial", Font.PLAIN, 10);

            for (int i = 0; i < buttons.length; i++) {
                buttons[i] = new HayButton("Resources/MenuBackground/button1.png", "Resources/MenuBackground/button1down.png", options[i], f, 1, 1);
                buttons[i].setFontColor(Color.DARK_GRAY, Color.GRAY, Color.WHITE);
                buttons[i].setIndent(0, 1);
            }

            fullButton = new HayButton("Resources/TickBox/checker_up.png", "Resources/TickBox/checker_down.png", "Fullscreen", f, 2, 2);
            fullButton.setOffset(50, 5);
            fullButton.setFontColor(Color.DARK_GRAY, Color.DARK_GRAY, Color.WHITE);

            musicSlider = new HayButton("Resources/TickBox/slider.png", "Resources/TickBox/sliderdown.png", " ", f, 2, 2);
            musicSlider.setPosition(375, 210);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //sets positions, starts music
    public void init() {

        newGame = false;
        loadGame = false;
        alpha = 1;
        fadeCounter = 0;
        optionsSelected = false;
        currentChoice = -1;
        hoverChoice = -1;

        mBox = new Point(0, 0);
        title.setPosition(125, 15);
        bus.setPosition(275, 145);
        wheels.setPosition(275, 146);
        menubox.setPosition(0, 140);
        menuboxtop.setPosition(0, 260);

        backgroundScene.setLayerPosition(0, 0, 0);
        for (int i = 1; i < backgroundNames.length; i++) {
            backgroundScene.setLayerPosition(i, 0, 110);
        }

        backgroundScene.setLayerVelocity(0, 0.01, 0);
        backgroundScene.setLayerVelocity(1, 0.1, 0);
        backgroundScene.setLayerVelocity(2, 0.3, 0);
        backgroundScene.setLayerVelocity(3, 0.8, 0);

        optionbox.generateHitbox();
        optionbox.setPosition((GamePanel.WIDTH - optionbox.getHitbox().width - 6) / 2, 200);
        fullButton.setPosition(380, 235);
        fullButton.setClickable(false);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].boop();
            buttons[i].setPosition(20 + i * 120, 280);
        }

        try {
            music.open(resource);
            music.play();
            music.setGain(PlayerSettings.volume);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    //updates scene, buttons, and alpha if newgame or loadgame is selected, updates volume based on slider position, loops music if ended
    public void update() {

        backgroundScene.update();

        if (!newGame && !loadGame) {
            fadeOut(1);
        }
        if (newGame || loadGame) {
            fadeIn(1);
            if (alpha >= 0.99) {
                try {
                    music.stop();
                } catch (BasicPlayerException ex) {
                    ex.printStackTrace();
                }
                if (newGame) {
                    sm.setState(2);
                }else if (loadGame) {
                    sm.setState(StateManager.LOADGAME);
                }
            }
        }

        if (music.getStatus() != 0 && !newGame && !loadGame) {
            try {
                music.play();
                music.setGain(PlayerSettings.volume);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                volumeControl();
                music.setGain(PlayerSettings.volume);
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
        if (musicSlider.isPressed()) {
            musicSlider.setPosition(mBox.x - 9, 210);
            if (musicSlider.getX() > 460) {
                musicSlider.setPosition(460, 210);
            } else if (musicSlider.getX() < 358) {
                musicSlider.setPosition(358, 210);
            }
        }

        musicSlider.update();

        clickables();
    }

    //draws background and scene, the title, the framerate, and sets the buttons' hover state
    public void draw(Graphics2D g) {

        drawBlack(g, 1.0f);

        menubox.draw(g);
        menuboxtop.draw(g);

        backgroundScene.draw(g);

        bus.draw(g);
        wheels.draw(g);

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

        if (fullButton.getClickable() && !backgroundScene.getMoving()) {
            optionbox.draw(g);
            fullButton.draw(g);
            musicSlider.draw(g);
        }

        title.draw(g);
        g.setFont(f2);
        g.drawString(fps, 1, 8);
        drawBlack(g, alpha);

    }

    //Sets buttons only to be clickable if they are not moving and visible
    public void clickables() {
        if (backgroundScene.getMoving()) {
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setClickable(false);
            }
            fullButton.setClickable(false);
            musicSlider.setClickable(false);
        } else if (!backgroundScene.getMoving()) {
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setClickable(true);
            }
            musicSlider.setClickable(true);
            if (currentChoice == 2) {
                fullButton.setClickable(true);
            } else {
                fullButton.setClickable(false);
            }
        }
    }

    //Moves the entire menu up *SEE Background.moveToPosition
    private void menuUp() {

        backgroundScene.moveToPosition(0, -120, 4, false, true);
        bus.moveToPosition(0, -120, 4, false, true);
        wheels.moveToPosition(0, -120, 4, false, true);
        menuboxtop.moveToPosition(0, -120, 4, false, true);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].moveToPosition(0, -120, 4, false, true);
        }
    }
    
    //Moves the entire menu down *SEE Background.moveToPosition
    private void menuDown() {
        backgroundScene.moveToPosition(0, 120, 4, false, true);
        bus.moveToPosition(0, 120, 4, false, true);
        wheels.moveToPosition(0, 120, 4, false, true);
        menuboxtop.moveToPosition(0, 120, 4, false, true);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].moveToPosition(0, 120, 4, false, true);
        }
    }

    //Makes a selection based upon the button that is pressed. (Menu up, menu down, new game, exit, etc.)
    private void select() {
        if (!backgroundScene.getMoving()) {
            currentChoice = hoverChoice;
        }
        switch (currentChoice) {
            case 0:
                newGame = true;
                if (!optionsSelected && !backgroundScene.getMoving()) {
                    menuUp();
                    optionsSelected = true;
                }
                break;
            case 1:
                loadGame = true;
                if (!optionsSelected && !backgroundScene.getMoving()) {
                    menuUp();
                    optionsSelected = true;
                }
                break;
            case 2:
                if (!optionsSelected && !backgroundScene.getMoving()) {
                    menuUp();
                    optionsSelected = true;
                }
                break;
            case 3:
                if (!optionsSelected && !backgroundScene.getMoving()) {
                    menuUp();
                    optionsSelected = true;
                }
                break;
            case 4:
                System.exit(0);
        }
    }

    //Sets the volume based upon slider position
    public void volumeControl() {
        if (musicSlider.getX() <= 358) {
            PlayerSettings.volume = 0;
        } else if (musicSlider.getX() < 360) {
            PlayerSettings.volume = 0.05;
        } else if (musicSlider.getX() < 370) {
            PlayerSettings.volume = 0.1;
        } else if (musicSlider.getX() < 380) {
            PlayerSettings.volume = 0.15;
        } else if (musicSlider.getX() < 390) {
            PlayerSettings.volume = 0.2;
        } else if (musicSlider.getX() < 400) {
            PlayerSettings.volume = 0.4;
        } else if (musicSlider.getX() < 410) {
            PlayerSettings.volume = 0.5;
        } else if (musicSlider.getX() < 420) {
            PlayerSettings.volume = 0.6;
        } else if (musicSlider.getX() < 430) {
            PlayerSettings.volume = 0.7;
        } else if (musicSlider.getX() < 440) {
            PlayerSettings.volume = 0.8;
        } else if (musicSlider.getX() < 450) {
            PlayerSettings.volume = 0.9;
        } else if (musicSlider.getX() < 460) {
            PlayerSettings.volume = 1.0;
        }
    }


    //Checks keyPresses and moves the choice left and right, and selects when enter is pressed
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
        if (k == KeyEvent.VK_ESCAPE && optionsSelected && !backgroundScene.getMoving()) {
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

    //Checks button hiboxes for clicks and selects
    public void mousePressed(MouseEvent e) {
        mBox.setLocation(e.getX() / GamePanel.SCALEX, e.getY() / GamePanel.SCALEY);
        for (int i = 0; i < options.length; i++) {
            buttons[i].setPressed(false);
            if (buttons[i].checkClick(mBox)) {
                currentChoice = i;
                select();
            }
            if (fullButton.checkClick(mBox)) {
                GamePanel.toggleFullscreen();
            }
            musicSlider.checkClick(mBox);
        }
    }

    //Checks button hitboxes and sets hovers
    public void mouseMoved(MouseEvent e) {
        mBox.setLocation(e.getX() / GamePanel.SCALEX, e.getY() / GamePanel.SCALEY);

        for (int i = 0; i < options.length; i++) {
            buttons[i].setHovered(false);
            if (buttons[i].checkHover(mBox)) {
                hoverChoice = i;
            }
        }
    }

    //Sets the pressed state of the music slider to false when you let go of a click
    public void mouseReleased(MouseEvent e) {
        mBox.setLocation(e.getX() / GamePanel.SCALEX, e.getY() / GamePanel.SCALEY);
        musicSlider.setPressed(false);
    }

    //Updates the mouse point when mouse is clicked and dragged (For slider)
    public void mouseDragged(MouseEvent e) {
        mBox.setLocation(e.getX() / GamePanel.SCALEX, e.getY() / GamePanel.SCALEY);
    }

    public void keyTyped(int k) {

    }
}
