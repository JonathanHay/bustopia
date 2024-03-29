/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backgrounds;

import haykimfinal.GamePanel;
import java.awt.*;
import java.awt.geom.AffineTransform;
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

    private boolean wrapX;
    private boolean wrapY;
    private boolean hasHitbox;

    private Rectangle hitbox;

    private int x21;
    private int y21;
    private double speed1;
    private boolean mX1;
    private boolean mY1;
    private double dx0;
    private double dy0;
    private boolean moving;
    private boolean visible;
    
    //Constructs static background. Sets image based the string (which is the resource ), and scales the image
    public Background(String s, boolean wx, boolean wy, double sx, double sy) {
        hitbox = new Rectangle(0, 0, 0, 0);
        hasHitbox = false;
        try {
            img = ImageIO.read(ClassLoader.getSystemResource(s));
            AffineTransform tx = new AffineTransform();
            tx.scale(sx, sy);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            img = op.filter(img, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        wrapX = wx;
        wrapY = wy;
        moving = false;
        visible = true;
    }

    //Constructs static background. Sets image based the BufferedImage (which is the resource loaded into an image), and scales the image
    public Background(BufferedImage image, boolean wx, boolean wy, double sx, double sy) {
        img = image;

        try {
            AffineTransform tx = new AffineTransform();
            tx.scale(sx, sy);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            img = op.filter(img, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        hitbox = new Rectangle(0, 0, 0, 0);
        hasHitbox = false;
        wrapX = wx;
        wrapY = wy;
        moving = false;
        visible = true;
    }

    //sets X, Y coords (with wrap)
    public void setPosition(double x1, double y1) {
        x = x1 % GamePanel.WIDTH;
        y = y1 % GamePanel.HEIGHT;
    }

    //sets X, Y coords (without wrap)
    public void forceSetPosition(double x1, double y1) {
        x = x1;
        y = y1;
    }

    //Sets the background visible/invisible
    public void setVisible(boolean b) {
        visible = b;
    }

    //returns x coord
    public double getX() {
        return x;
    }

    //returns y coord
    public double getY() {
        return y;
    }

    //Moves the object to a position relative to its current position, and sets the speed at which it does this and the directions it applies to
    public void moveToPosition(int x2, int y2, double speed, boolean mX, boolean mY) {
        if (!moving) {
            dx0 = dx;
            dy0 = dy;
            x21 = x2 + (int) x;
            y21 = y2 + (int) y;
            speed1 = speed;
            mX1 = mX;
            mY1 = mY;
            moving = true;
        }
    }
    
    //Called every time the background updates and moves the item based on params from moveToPosition
    private void move(int x2, int y2, double speed, boolean mX, boolean mY) {
        if (mX) {
            if (x > x2) {
                dx += -speed;
            } else if (x < x2) {
                dx += speed;
            }
        }
        if (mY) {
            if (y > y2) {
                dy = -speed;
            } else if (y < y2) {
                dy = speed;
            }
        }

        if (mX && !mY) {
            if (x == x2) {
                moving = false;
                dx = dx0;
            }
        } else if (!mX && mY) {
            if (y == y2) {
                moving = false;
                dy = dy0;
            }
        } else if (x == x2 && y == y2) {
            moving = false;
            dx = dx0;
            dy = dy0;
        }
    }

    //returns state of moving/not moving
    public boolean getMoving() {
        return moving;
    }

    //sets velocity
    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    //sets X coord
    public void setX(int i) {
        x = i;
    }

    //generates a hitbox based on the size of the image that will be used to check mouse clicks
    public void generateHitbox() {
        hitbox = new Rectangle((int) x, (int) y, img.getWidth(), img.getHeight());
        hasHitbox = true;
    }

    //Updates hitbox position
    private void updateHitbox() {
        hitbox.setLocation((int) x, (int) y);
    }

    //returns the hitbox for use with mouse
    public Rectangle getHitbox() {
        return hitbox;
    }

    //Sets Y coord
    public void setY(int i) {
        y = i;
    }

    //updates position based on velocity and wraps, updates hitbox and moves if in a state of moving
    public void update() {
        if (img.getWidth() > GamePanel.WIDTH) {
            x = (x + dx) % img.getWidth();
        } else {
            x = (x + dx) % GamePanel.WIDTH;
        }

        if (img.getHeight() > GamePanel.HEIGHT) {
            y = (y + dy) % img.getHeight();
        } else {
            y = (y + dy) % GamePanel.HEIGHT;
        }

        if (hasHitbox) {
            updateHitbox();
        }
        if (moving) {
            move(x21, y21, speed1, mX1, mY1);
        }
    }

    //Draws (if visible), and wraps the image if necessary (set to wrap)
    public void draw(Graphics2D g) {

        if (visible) {

            g.drawImage(img, (int) x, (int) y, null);

            if (wrapX) {
                if (x < 0) {
                    g.drawImage(img, (int) x + img.getWidth(), (int) y, null);
                }
                if (x > 0) {
                    g.drawImage(img, (int) x - img.getWidth(), (int) y, null);
                }
            }
            if (wrapY) {
                if (y > 0) {
                    g.drawImage(img, (int) x, (int) y - img.getHeight(), null);
                }
                if (y < 0) {
                    g.drawImage(img, (int) x, (int) y + img.getHeight(), null);
                }
            }

        }
    }
}
