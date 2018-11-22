/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Constants;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Jonathan
 */

//Loads in resources that are frequently required into buffered images that can be accessed by the entire program (Not actually final because of type)
public class Images {

    public static BufferedImage EMPTYITEM_IMAGE;
    public static BufferedImage LOCKEDITEM_IMAGE;

    public static BufferedImage ENGINE_IMAGE;
    public static BufferedImage WHEEL_IMAGE;
    public static BufferedImage FUELTANK_IMAGE;

    public static BufferedImage TIER1_IMAGE;
    public static BufferedImage TIER2_IMAGE;
    public static BufferedImage TIER3_IMAGE;

    public static BufferedImage[] BUSES_IMAGE;
    
    
    public static BufferedImage STATEBUTTON_IMAGE;

    static {
        BUSES_IMAGE = new BufferedImage[28];
        try {
            EMPTYITEM_IMAGE = ImageIO.read(ClassLoader.getSystemResource("Resources/InventorySprites/empty.png"));
            LOCKEDITEM_IMAGE = ImageIO.read(ClassLoader.getSystemResource("Resources/InventorySprites/locked.png"));
            ENGINE_IMAGE = ImageIO.read(ClassLoader.getSystemResource("Resources/InventorySprites/engine.png"));
            WHEEL_IMAGE = ImageIO.read(ClassLoader.getSystemResource("Resources/InventorySprites/wheels.png"));
            FUELTANK_IMAGE = ImageIO.read(ClassLoader.getSystemResource("Resources/InventorySprites/fueltank.png"));
            TIER1_IMAGE = ImageIO.read(ClassLoader.getSystemResource("Resources/InventorySprites/tier1.png"));
            TIER2_IMAGE = ImageIO.read(ClassLoader.getSystemResource("Resources/InventorySprites/tier2.png"));
            TIER3_IMAGE = ImageIO.read(ClassLoader.getSystemResource("Resources/InventorySprites/tier3.png"));

            BUSES_IMAGE[0] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/citybus.png"));
            BUSES_IMAGE[1] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/speedybus.png"));
            BUSES_IMAGE[2] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/greyhoundbus.png"));
            BUSES_IMAGE[3] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/rustybus.png"));
            BUSES_IMAGE[4] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/sandybus.png"));
            BUSES_IMAGE[5] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/townbus.png"));
            BUSES_IMAGE[6] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/airportbus.png"));
            BUSES_IMAGE[7] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/schoolbus.png"));
            BUSES_IMAGE[8] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/shortschoolbus.png"));
            BUSES_IMAGE[9] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/deliverybus.png"));
            BUSES_IMAGE[10] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/garbagebus.png"));
            BUSES_IMAGE[11] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/aquariumbus.png"));
            BUSES_IMAGE[12] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/camobus.png"));
            BUSES_IMAGE[13] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/doubledeckerbus.png"));
            BUSES_IMAGE[14] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/reversebus.png"));
            BUSES_IMAGE[15] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/safaribus.png"));
            BUSES_IMAGE[16] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/policebus.png"));
            BUSES_IMAGE[17] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/snowplowbus.png"));
            BUSES_IMAGE[18] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/woodbus.png"));
            BUSES_IMAGE[19] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/weinerbus.png"));
            BUSES_IMAGE[20] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/icecreambus.png"));
            BUSES_IMAGE[21] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/monsterbus.png"));
            BUSES_IMAGE[22] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/formula1bus.png"));
            BUSES_IMAGE[23] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/sailboatbus.png"));
            BUSES_IMAGE[24] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/ferriswheelbus.png"));
            BUSES_IMAGE[25] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/greenbus.png"));
            BUSES_IMAGE[26] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/surveillancebus.png"));
            BUSES_IMAGE[27] = ImageIO.read(ClassLoader.getSystemResource("Resources/Buses/hoverbus.png"));
            
            STATEBUTTON_IMAGE = ImageIO.read(ClassLoader.getSystemResource("Resources/NightBackground/stateButton.png"));
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
