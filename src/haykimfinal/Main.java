/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haykimfinal;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Jonathan
 */
public class Main {

    public Main() {
        JFrame f = new JFrame("BUSTOPIA");
        f.setContentPane(new GamePanel());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        try {
            Image image = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Resources/icon.png"));
            f.setIconImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
