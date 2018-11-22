/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;

import GameObjects.Engine;
import GameObjects.FuelTank;
import GameObjects.Wheels;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Jonathan
 */
public class PlayerSettings {

    public static double volume;
    public static String playerName;
    public static int day;
    public static int shopLevel;

    //Sets default player settings at launch
    static {
        playerName = "Shrek";
        volume = 0.2;
        day = 0;
        shopLevel = 0;
    }

    //Saves game in formatted CSV
    public static void saveGame() {
        try {
            File fileQ = new File(playerName + "_SAVE.csv");
            if (!fileQ.exists()) {
                fileQ.createNewFile();
            }
            FileWriter fw = new FileWriter(fileQ.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(playerName);
            bw.newLine();
            bw.write(String.valueOf(Inventory.playerMoney));
            bw.newLine();
            bw.write(String.valueOf(Inventory.playerScrap));
            bw.newLine();
            bw.write(String.valueOf(day));
            bw.newLine();
            bw.write(String.valueOf(shopLevel));
            bw.newLine();

            bw.write(String.valueOf(Inventory.playerBuses.size()));
            bw.newLine();
            if (Inventory.playerBuses.size() > 0) {
                for (int i = 0; i < Inventory.playerBuses.size(); i++) {
                    bw.write(String.valueOf(Inventory.playerBuses.get(i).getType()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerBuses.get(i).getRegion()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerBuses.get(i).getEngine().getType()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerBuses.get(i).getEngine().getTier()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerBuses.get(i).getEngine().getToughness()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerBuses.get(i).getEngine().getEfficiency()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerBuses.get(i).getWheels().getType()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerBuses.get(i).getWheels().getTier()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerBuses.get(i).getWheels().getToughness()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerBuses.get(i).getWheels().getSpeed()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerBuses.get(i).getFuelTank().getType()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerBuses.get(i).getFuelTank().getTier()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerBuses.get(i).getFuelTank().getToughness()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerBuses.get(i).getFuelTank().getFuelTankSize()));
                    bw.newLine();
                }
            }

            bw.write(String.valueOf(Inventory.playerEngines.size()));
            bw.newLine();
            if (Inventory.playerEngines.size() > 0) {
                for (int i = 0; i < Inventory.playerEngines.size(); i++) {
                    bw.write(String.valueOf(Inventory.playerEngines.get(i).getType()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerEngines.get(i).getTier()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerEngines.get(i).getToughness()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerEngines.get(i).getEfficiency()));
                    bw.newLine();
                }
            }

            bw.write(String.valueOf(Inventory.playerWheels.size()));
            bw.newLine();
            if (Inventory.playerWheels.size() > 0) {
                for (int i = 0; i < Inventory.playerWheels.size(); i++) {
                    bw.write(String.valueOf(Inventory.playerWheels.get(i).getType()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerWheels.get(i).getTier()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerWheels.get(i).getToughness()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerWheels.get(i).getSpeed()));
                    bw.newLine();
                }
            }

            bw.write(String.valueOf(Inventory.playerTanks.size()));
            bw.newLine();
            if (Inventory.playerTanks.size() > 0) {
                for (int i = 0; i < Inventory.playerTanks.size(); i++) {
                    bw.write(String.valueOf(Inventory.playerTanks.get(i).getType()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerTanks.get(i).getTier()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerTanks.get(i).getToughness()));
                    bw.write(",");
                    bw.write(String.valueOf(Inventory.playerTanks.get(i).getFuelTankSize()));
                    bw.newLine();
                }
            }

            bw.close();

            System.out.println("GAME SAVED");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Loads game from formatted CSV
    public static void loadGame(String s) {

        try {
            FileReader file = new FileReader(s + "_SAVE.csv");
            Inventory.resetInventory();
            BufferedReader buffer = new BufferedReader(file);
            String[] input;
            playerName = buffer.readLine();
            Inventory.playerMoney = Integer.parseInt(buffer.readLine());
            Inventory.playerScrap = Integer.parseInt(buffer.readLine());
            day = Integer.parseInt(buffer.readLine());
            shopLevel = Integer.parseInt(buffer.readLine());
            int numOfItem = Integer.parseInt(buffer.readLine());

            if (numOfItem > 0) {
                for (int i = 0; i < numOfItem; i++) {
                    input = buffer.readLine().split(",");
                    Inventory.addBus(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
                    
                    Engine tempEngine = new Engine(Integer.parseInt(input[2]), Integer.parseInt(input[3]));
                    tempEngine.setToughness(Integer.parseInt(input[4]));
                    tempEngine.setEfficiency(Integer.parseInt(input[5]));
                    Inventory.playerBuses.get(i).setEngine(tempEngine);
                    
                    Wheels tempWheels = new Wheels(Integer.parseInt(input[6]), Integer.parseInt(input[7]));
                    tempWheels.setToughness(Integer.parseInt(input[8]));
                    tempWheels.setSpeed(Integer.parseInt(input[9]));
                    Inventory.playerBuses.get(i).setWheels(tempWheels);
                    
                    FuelTank tempFuelTank = new FuelTank(Integer.parseInt(input[1]), Integer.parseInt(input[11]));
                    tempFuelTank.setToughness(Integer.parseInt(input[12]));
                    tempFuelTank.setFuelTankSize(Integer.parseInt(input[13]));
                    Inventory.playerBuses.get(i).setFuelTank(tempFuelTank);
                                   
                }
            }

            numOfItem = Integer.parseInt(buffer.readLine());
            if (numOfItem > 0) {
                for (int i = 0; i < numOfItem; i++) {
                    input = buffer.readLine().split(",");
                    Inventory.addEngine(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
                    Inventory.playerEngines.get(i).setToughness(Integer.parseInt(input[2]));
                    Inventory.playerEngines.get(i).setEfficiency(Integer.parseInt(input[3]));
                }
            }
            numOfItem = Integer.parseInt(buffer.readLine());
            if (numOfItem > 0) {
                for (int i = 0; i < numOfItem; i++) {
                    input = buffer.readLine().split(",");
                    Inventory.addWheels(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
                    Inventory.playerWheels.get(i).setToughness(Integer.parseInt(input[2]));
                    Inventory.playerWheels.get(i).setSpeed(Integer.parseInt(input[3]));
                }
            }
            numOfItem = Integer.parseInt(buffer.readLine());
            if (numOfItem > 0) {
                for (int i = 0; i < numOfItem; i++) {
                    input = buffer.readLine().split(",");
                    Inventory.addFuelTank(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
                    Inventory.playerTanks.get(i).setToughness(Integer.parseInt(input[2]));
                    Inventory.playerTanks.get(i).setFuelTankSize(Integer.parseInt(input[3]));
                }
            }
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
