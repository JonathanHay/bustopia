/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backgrounds;

import haykimfinal.GamePanel;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
    private boolean wrapX;
    private boolean wrapY;
    private int animFrame = 0;
    private int animSpeed;
    private int updateCounter = 0;
    private int bobFrame = 0;
    private int bobSpeed;
    private double bobSwitch = 1;
    private AnimType type;

    private int x21;
    private int y21;
    double speed1;
    boolean mX1;
    boolean mY1;
    private double dx0;
    private double dy0;
    private boolean moving;

    public enum AnimType {
        NORMAL, BOB, SHAKE
    }

    private double parallaxMultiplier;

    //Constructs animated background. Sets number for frames bases on the size of the string array(which is the resource names) as well as sets the speed at which the animation cycles and the type (normal or bob), and scales the images
    public AnimatedBackground(String[] s, int speed, double pm, boolean wx, boolean wy, AnimType a, double sx, double sy) {
        type = a;
        moving = false;
        bobSpeed = 1;
        try {
            img = new BufferedImage[s.length];
            for (int i = 0; i < s.length; i++) {
                img[i] = ImageIO.read(ClassLoader.getSystemResource(s[i]));
                AffineTransform tx = new AffineTransform();
                tx.scale(sx, sy);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                img[i] = op.filter(img[i], null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        wrapX = wx;
        wrapY = wy;
        parallaxMultiplier = pm;
        animSpeed = speed;
    }

    //Same as other constructor, except takes in buffered image instead of string array
    public AnimatedBackground(BufferedImage image, int speed, double pm, boolean wx, boolean wy, AnimType a, double sx, double sy) {
        type = a;
        moving = false;
        bobSpeed = 1;
        try {
            img = new BufferedImage[1];
            img[0] = image;
            AffineTransform tx = new AffineTransform();
            tx.scale(sx, sy);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            img[0] = op.filter(img[0], null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        wrapX = wx;
        wrapY = wy;
        parallaxMultiplier = pm;
        animSpeed = speed;
    }

    //sets the position and wraps it within the gamepanel's width and height
    public void setPosition(double x1, double y1) {
        x = (x1 * parallaxMultiplier) % GamePanel.WIDTH;
        y = (y1 * parallaxMultiplier) % GamePanel.HEIGHT;
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
    
    
    //Called every time the animatedbackground updates and moves the item based on params from moveToPosition
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

    //gets if the background is moving or not
    public boolean getMoving() {
        return moving;
    }

    //sets Velocity   
    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    //Sets X coord
    public void setX(int i) {
        x = i;
    }

    //Sets Y coord
    public void setY(int i) {
        y = i;
    }

    //Updates the background's position based on velocity and calls move if the background is in a moving state
    public void update() {

        if (img[animFrame].getWidth() > GamePanel.WIDTH) {
            x = (x + dx) % img[animFrame].getWidth();
        } else {
            x = (x + dx) % GamePanel.WIDTH;
        }

        if (img[animFrame].getHeight() > GamePanel.HEIGHT) {
            y = (y + dy) % img[animFrame].getHeight();
        } else {
            y = (y + dy) % GamePanel.HEIGHT;
        }

        if (moving) {
            move(x21, y21, speed1, mX1, mY1);
        }
    }
    
    //Performs operations to make item bob
    public void bob() {
        bobFrame++;
        y = (double) y + ((double) bobSwitch / (double) bobSpeed);
        if (bobFrame > bobSpeed) {
            bobFrame = 0;
            bobSwitch *= -1;
        }
    }

    //Sets the speed at which the item bobs
    public void setBobSpeed(int i) {
        bobSpeed = i;
    }

    //Cycles frames and bob based on animation and bob speeds
    public void animate() {
        updateCounter++;
        if (updateCounter > 120 / (animSpeed % 121)) {
            updateCounter = 0;
            switch (type) {
                case BOB:
                    nextFrame();
                    if (!moving) {
                        bob();
                    }
                    break;
                case NORMAL:
                    nextFrame();
                    break;
            }
        }
    }

    //moves to the next animation frame
    public void nextFrame() {
        animFrame++;
        if (animFrame >= img.length) {
            animFrame = 0;
        }
    }

    //Animates, draws, and wraps the image if necessary (set to wrap)
    public void draw(Graphics2D g) {
        animate();
        g.drawImage(img[animFrame], (int) x, (int) y, null);

        if (wrapX) {
            if (x < 0) {
                g.drawImage(img[animFrame], (int) x + img[animFrame].getWidth(), (int) y, null);
            }
            if (x > 0) {
                g.drawImage(img[animFrame], (int) x - img[animFrame].getWidth(), (int) y, null);
            }

        }
        if (wrapY) {
            if (y > 0) {
                g.drawImage(img[animFrame], (int) x, (int) y - img[animFrame].getHeight(), null);
            }
            if (y < 0) {
                g.drawImage(img[animFrame], (int) x, (int) y + img[animFrame].getHeight(), null);
            }
        }
    }
}
