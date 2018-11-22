/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;

import Backgrounds.HayButton;
import GameObjects.*;
import java.util.ArrayList;

/**
 *
 * @author Jonathan
 */
public class Inventory {

    public static ArrayList<Bus> playerBuses;
    public static ArrayList<Engine> playerEngines;
    public static ArrayList<FuelTank> playerTanks;
    public static ArrayList<Wheels> playerWheels;
    public static int numOfItems;
    public static int numOfBuses;
    public static int playerMoney;
    public static int playerScrap;
    public static boolean regenShop;
    
    public static Engine applyEngine;
    public static Wheels applyWheels;
    public static FuelTank applyTank;
    
    public static int applyChoice;  
    public static int applyType;

    // creates arraylists of items and buses and sets amounts to zero
    static {
        playerBuses = new ArrayList<Bus>();
        playerEngines = new ArrayList<Engine>();
        playerTanks = new ArrayList<FuelTank>();
        playerWheels = new ArrayList<Wheels>();
        numOfItems = 0;
        numOfBuses = 0;
        playerMoney = 0;
        playerScrap = 0;
        regenShop = true;
    }   

    // creates arraylists of items and buses and sets amounts to zero
    public static void resetInventory() {
        playerBuses = new ArrayList<Bus>();
        playerEngines = new ArrayList<Engine>();
        playerTanks = new ArrayList<FuelTank>();
        playerWheels = new ArrayList<Wheels>();
        numOfItems = 0;
        numOfBuses = 0;
        playerMoney = 0;
        playerScrap = 0;
        regenShop = true;
        PlayerSettings.day = 0;
        PlayerSettings.shopLevel = 0;
    }
    // adds a bus to arraylist and increases the amount of buses *SEE Bus for constructor
    public static void addBus(int t, int r) {
        playerBuses.add(new Bus(t, r));
        numOfBuses++;
    }

    // adds an engine to arraylist and increases the amount of items *SEE Bus for constructor
    public static void addEngine(int ty, int ti) {
        playerEngines.add(new Engine(ty, ti));
        numOfItems++;
    }

    // adds a fueltank to arraylist and increases the amount of items *SEE Bus for constructor
    public static void addFuelTank(int ty, int ti) {
        playerTanks.add(new FuelTank(ty, ti));
        numOfItems++;
    }

    // adds wheels to arraylist and increases the amount of items *SEE Bus for constructor
    public static void addWheels(int ty, int ti) {
        playerWheels.add(new Wheels(ty, ti));
        numOfItems++;
    }

}
