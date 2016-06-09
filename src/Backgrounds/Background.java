/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backgrounds;

import haykimfinal.GamePanel;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

/**
 *
 * @author Jonathan
 */
public class Background {

    private BufferedImage img;

    private double x;
    private double y;
    private double dx;
    private double dy;
    private boolean wrap;

    private double parralaxMultiplier;

    public Background(String s, double pm, boolean w) {
        try {          
            img = ImageIO.read(ClassLoader.getSystemResource(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
        wrap = w;
        parralaxMultiplier = pm;
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
        x = (x + dx) % img.getWidth();
        y = (y + dy) % img.getHeight();
    }

    public void draw(Graphics2D g) {
        g.drawImage(img, (int) x, (int) y, null);

        if (wrap) {
            if (x < 0) {
                g.drawImage(img, (int) x + img.getWidth(), (int) y, null);
            }
            if (x > 0) {
                g.drawImage(img, (int) x - img.getWidth(), (int) y, null);
            }
            if (y > 0) {
                g.drawImage(img, (int) x, (int) y - img.getHeight(), null);
            }
            if (y < 0) {
                g.drawImage(img, (int) x, (int) y + img.getHeight(), null);
            }
        }
    }
}
