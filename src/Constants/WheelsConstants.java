/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Constants;

/**
 *
 * @author Jonathan
 */

//Contains all of the constants for wheel stats
public class WheelsConstants {
        
    public static final String[] TYPE_NAME =
        {"Standard Wheels", "Speedy Wheels", "Tough Wheels", "Light Wheels", "Heavy Wheels",
        "Rubbery Wheels", "Truck Wheels", "Plasma Wheels"};
    public static final int[] COST =
        {1000, 1600, 1400, 1100, 1200,
        1500, 1200, 29000};
    public static final int[] TOUGHNESS =
        {150, 100, 230, 120, 180,
        130, 170, 250};               // 0 = 100% chance, 100 = 50% chance, 200 = 33% chance
    public static final int[] SPEED =
        {15, 22, 12, 17, 15,
        18, 10, 25};
    public static final int[] TOUGHNESS_MULTIPLIER =
        {10, 30, 50, 25, 30,
        30, 40, 60};
    public static final int[] SPEED_MULTIPLIER =
        {15, 40, 10, 15, 20,
        25, 30, 70};
    
}
