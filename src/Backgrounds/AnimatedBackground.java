/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backgrounds;

import haykimfinal.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Jonathan
 */
public class AnimatedBackground {

    private BufferedImage img[];

    private double x;
    private double y;
    private double dx;
    private double dy;
    private boolean wrap;
    private int animFrame = 0;
    private int animSpeed;
    private int updateCounter = 0;

    private double parralaxMultiplier;

    public AnimatedBackground(String[] s, int speed, double pm, boolean w) {
        try {
            img = new BufferedImage[s.length];
            for (int i = 0; i < s.length; i++) {
                img[i] = ImageIO.read(ClassLoader.getSystemResource(s[i]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        wrap = w;
        parralaxMultiplier = pm;
        animSpeed = speed;
    }

    public void setPosition(double x1, double y1) {
        x = (x1 * parralaxMultiplier) % GamePanel.WIDTH;
        y = (y1 * parralaxMultiplier) % GamePanel.HEIGHT;
    }

    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void setX(int i) {
        x = i;
    }

    public void setY(int i) {
        y = i;
    }

    public void update() {
        x = (x + dx) % img[animFrame].getWidth();
        y = (y + dy) % img[animFrame].getHeight();
        if (updateCounter > 30 / (animSpeed % 61)) {
            updateCounter = 0;
            animFrame++;
        }
        if (animFrame >= img.length) {
            animFrame = 0;
        }
        updateCounter++;
    }

    public void draw(Graphics2D g) {
        g.drawImage(img[animFrame], (int) x, (int) y, null);

        if (wrap) {
            if (x < 0) {
                g.drawImage(img[animFrame], (int) x + img[animFrame].getWidth(), (int) y, null);
            }
            if (x > 0) {
                g.drawImage(img[animFrame], (int) x - img[animFrame].getWidth(), (int) y, null);
            }
            if (y > 0) {
                g.drawImage(img[animFrame], (int) x, (int) y - img[animFrame].getHeight(), null);
            }
            if (y < 0) {
                g.drawImage(img[animFrame], (int) x, (int) y + img[animFrame].getHeight(), null);
            }
        }
    }
}
