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

    public AnimatedBackground(String[] s, int speed, double pm, boolean wx, boolean wy, AnimType a, double sx, double sy) {
        type = a;
        moving = false;
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

    public void setPosition(double x1, double y1) {
        x = (x1 * parallaxMultiplier) % GamePanel.WIDTH;
        y = (y1 * parallaxMultiplier) % GamePanel.HEIGHT;
    }

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

    public boolean getMoving() {
        return moving;
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

    public void bob() {
        bobFrame++;
        y = y + bobSwitch;
        if (bobFrame > 1) {
            bobFrame = 0;
            bobSwitch *= -1;
        }
    }

    public void animate() {
        updateCounter++;
        if (updateCounter > 30 / (animSpeed % 61)) {
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

    public void nextFrame() {
        animFrame++;
        if (animFrame >= img.length) {
            animFrame = 0;
        }
    }

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
