/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Constants;

/**
 *
 * @author Leo
 */
public class EngineConstants {
    
    public static final String[] TYPE_NAME =
        {"Standard Engine", "Powerful Engine", "Speedy Engine", "Efficient Engine", "Tough Engine",
        "Hybrid Engine", "Light Engine", "Heavy Engine", "Guzzler Engine", "Fragile Engine",
        "Perpetual Engine"};
    public static final int[] COST =
        {1000, 1200, 1500, 1700, 1600,
        2400, 1100, 1400, 600, 900,
        28000};
    public static final int[] TOUGHNESS =
        {200, 190, 100, 150, 280,
        170, 120, 210, 70, 20,
        250};                                    // 0 = 100% chance, 100 = 50% chance, 200 = 33% chance
    public static final int[] EFFICIENCY =
        {15, 7, 3, 19, 5,
        24, 12, 4, 1, 6,
        30};                                   //in gas used per 10/efficiency
    public static final int[] TOUGHNESS_MULTIPLIER =
        {15, 45, 20, 0, 65,
        30, 25, 35, 50, 40,
        50};
    public static final int[] EFFICIENCY_MULTIPLIER =
        {10, 25, 30, 60, 10,
        30, 40, 45, 0, 35,
        65};
    
    
    
}
