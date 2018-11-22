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
public class BusConstants {
    
    public static final String[] DESCRIPTION =
        {"A common and reliable bus for transporting everyday citizens.",
        "A faster alternative for travel. Its wavy decals makes it go faster!",
        "When you need a little more room, the greyhound bus is a great choice.",
        "This junky truck is perfect for picking up more junk.",
        "A bus made for traversing drier climates (has quality air conditioning!).",
        
        "A typical bus for local transportation in small towns.",
        "It doesn't fly, but maybe some day it will grow wings and become an air-bus!",
        "Safely driving kids to and from school since... whenever.",
        "For children who need extra care.",
        "When not delivering name brand drinks, it can also get people places.",
        
        "It might smell bad, but it's got a lot of room for junk.",
        "A tour bus for underwater attractions. It's even got a picture of a manatee on it!",
        "Wait, what bus?",
        "An iconic symbol of England that attracts tourists and locals alike.",
        "Looks like someone was holding the blueprint the wrong way...",
        
        "If you wanted to look at lions and long horses up close, this is the bus for you.",
        "When it's not helping citizens on their commutes, it serves justice to criminals.",
        "A bus that is capable of clearing the roads on a snowy winter day.",
        "A bus made completely out of wood. How does it not burn down?",
        "It's a bus that looks like a hot dog on a bun! Too bad it's actually not edible...",
                
        "Serves creamy frozen snacks to its customers. Flavours come in greasy gasoline, rubbery tire, and strawberry.",
        "Its gigantic wheels are no joke! It can traverse any terrain with ease.",
        "When you're running late to work, this bus will take you to your destination in the blink of an eye!",
        "Wait, why are there wheels on this thing?",
        "A magnificent feat of excellent engineering and bad ideas. Seriously, how does this even work?",
        
        "Fitted with solar panels, this energy-efficient bus is helping the environment one passenger at a time.",
        "Using state of the art technologies, this bus sniffs out potential passengers in every corner.",
        "A futuristic bus that runs not on gasoline, but on a perpetual sub-particle accelerator that generates kinetic energy."};
    
    public static final String[] TYPE_NAME =
        {"City Bus", "Speedy Bus", "Greyhound Bus", "Rusty Bus", "Sandy Bus",
        "Town Bus", "Airport Bus", "School Bus", "Short School Bus", "Delivery Bus",
        "Garbage Bus", "Aquarium Bus", "Camouflage Bus", "Double Decker Bus", "Reverse Bus",
        "Safari Bus", "Police Bus", "Snowplow Bus", "Wooden Bus", "Weiner Bus",
        "Ice Cream Bus", "Monster Bus", "Formula 1 Bus", "Sailboat Bus", "Ferris Wheel Bus",
        "Green Bus", "Surveillance Bus", "Hover Bus"};
    
    public static final int[] WHEELTYPE =
        {1, 3, 4, 2, 2,
        2, 2, 3, 5, 8,
        8, 3, 2, 2, 7,
        1, 2, 9, 10, 2,
        2, 6, 11, 9, 1,
        2, 3, 0};
    
    public static final int[] COST =
        {5500, 6100, 6800, 5200, 5700,
        6200, 6800, 7000, 6100, 7200,
        6500, 6600, 7200, 7800, 7300,
        7100, 7900, 8100, 5300, 7500,
        8100, 9200, 9000, 10200, 11000,
        12600, 9700, 50000};
    
    public static final int[] MAX_PASSENGERS =
        {25, 17, 52, 12, 20,
        20, 27, 32, 16, 30,
        18, 28, 27, 58, 35,
        30, 22, 15, 26, 29,
        19, 25, 12, 21, 20,
        34, 29, 40};                                        //in # of passengers
    
    public static final int[] BASE_WHEELS =
        {0, 1, 2, 5, 3,
        0, 5, 0, 0, 6,
        6, 2, 3, 4, 4,
        0, 1, 4, 0, 2,
        3, 6, 1, 2, 4,
        5, 0, 7};                                           //in type
    public static final int[] BASE_ENGINE =
        {0, 2, 4, 8, 9,
        0, 3, 0, 6, 7,
        7, 1, 4, 3, 3,
        2, 0, 8, 6, 9,
        0, 1, 2, 3, 7,
        5, 6, 10};
    public static final int[] BASE_FUELTANK =
        {0, 4, 1, 2, 0,
        5, 0, 5, 6, 7,
        7, 0, 2, 5, 6,
        4, 2, 1, 0, 5,
        0, 7, 4, 2, 0,
        2, 5, 8};
    public static final int[] BASE_ACCESORY =
        {0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0};
        
    public static final int[] BASE_WHEELS_TIER =
        {0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 1, 0, 0, 0,
        1, 0, 1, 1, 0,
        0, 2, 2, 0, 1,
        1, 1, 0};                                           //in tier
    public static final int[] BASE_ENGINE_TIER =
        {0, 0, 1, 0, 0,
        0, 0, 0, 0, 0,
        1, 0, 0, 0, 0,
        0, 1, 0, 0, 1,
        1, 0, 1, 2, 0,
        2, 1, 0};
    public static final int[] BASE_FUELTANK_TIER =
        {0, 0, 0, 0, 0,
        1, 0, 0, 0, 0,
        0, 1, 0, 0, 0,
        0, 0, 1, 0, 1,
        2, 1, 0, 0, 1,
        0, 1, 0};
    public static final int[] BASE_ACCESORY_TIER =
        {0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0};
    
    public static final int[] BASE_SPEED =
        {6, 12, 7, 3, 5,
        5, 7, 5, 4, 6,
        6, 8, 10, 4, 4,
        7, 11, 5, 7, 6,
        7, 9, 21, 5, 6,
        14, 13, 18};                                        //in km per tick
    public static final int[] BASE_EFFICIENCY =
        {20, 17, 15, 27, 22,
        22, 20, 25, 35, 15,
        18, 30, 28, 22, 19,
        20, 25, 17, 33, 26,
        21, 10, 12, 16, 24,
        45, 28, 200};                                       //in gas used per 10/efficiency
    public static final int[] BASE_FUELTANKSIZE =
        {150, 140, 220, 100, 175,
        140, 185, 160, 125, 190,
        205, 170, 135, 150, 150,
        185, 120, 210, 70, 115,
        145, 220, 85, 130, 150,
        90, 160, 100};                                      //in fuel tank size
    public static final int[] BASE_APPEAL =
        {100, 120, 125, 50, 90,
        110, 115, 100, 100, 80,
        60, 145, 110, 150, 120,
        90, 85, 80, 100, 150,
        140, 110, 100, 105, 160,
        130, 90, 160};                                      //in passenger boarding multiplier
    
    public static final int[] BASE_SCRAP =
        {0, 0, 0, 1, 0,
        0, 0, 0, 0, 0,
        2, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        3, 0, 0};
}
