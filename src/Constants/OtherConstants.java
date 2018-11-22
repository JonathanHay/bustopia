/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Constants;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jlgui.basicplayer.BasicPlayer;

/**
 *
 * @author Jonathan
 */

//Misc constants that are used throughout the program (Not actually final because of type)
public class OtherConstants {

    public static Font DEFAULT_FONT;
    private static Font FONT2;
    
    public static URL nightResource;
    public static BasicPlayer nightMusic;
    
    public static final int APPLYENGINE = 0;
    public static final int APPLYWHEELS = 1;
    public static final int APPLYFUELTANK = 2;

    static {
        try {
            nightResource = ClassLoader.getSystemResource("Resources/NightMusic2.mp3");
            nightMusic = new BasicPlayer();
            DEFAULT_FONT = Font.createFont(Font.PLAIN, ClassLoader.getSystemResourceAsStream("Resources/Fonts/pixelate.ttf"));
            DEFAULT_FONT = DEFAULT_FONT.deriveFont(DEFAULT_FONT.getSize() * 16F);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Font getFont(float size) {
        
        try {
            FONT2 = Font.createFont(Font.PLAIN, ClassLoader.getSystemResourceAsStream("Resources/Fonts/pixelate.ttf"));
            FONT2 = FONT2.deriveFont(FONT2.getSize() * size);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return FONT2;
    }
}
