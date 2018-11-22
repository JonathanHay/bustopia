/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import Constants.RegionsConstants;

/**
 *
 * @author Leo
 */
public class Regions {
    
    private int type;
    
    private int maxBuses;
    private int currentBuses;
    private double passengerRate;
    private double gasPrice;
    private double towPrice;
    private double chanceGas;
    private double chanceBusStop;
    
    private Bus[] buses;
    
    private int totalProfit;
    
    public Regions(int t) {
        type = t;
        
        totalProfit = 0;
        
        currentBuses = 0;
        maxBuses = RegionsConstants.MAXBUSES[type];
        passengerRate = RegionsConstants.PASSENGERRATE[type];
        gasPrice = RegionsConstants.GASPRICE[type];
        towPrice = RegionsConstants.TOWPRICE[type];
        chanceGas = RegionsConstants.CHANCE_GAS[type];
        chanceBusStop = RegionsConstants.CHANCE_BUSSTOP[type];
        
        buses = new Bus[maxBuses];
    }
    
    public void addBus(int t) {
        buses[currentBuses] = new Bus(t, type);
        currentBuses++;
    }
    
    public void tick() {
        totalProfit = 0;
        for (int i = 0; i < currentBuses; i++) {
            buses[i].tick();
            totalProfit += buses[i].getProfit();
        }
    }
    
    public void newDay() {
        for (int i = 0; i < currentBuses; i++) {
            buses[i].newDay();
        }
    }
    
    //////////////////////////////////////////////////////////////////////////////
    
    public String getRegionName() {
        return RegionsConstants.TYPE_NAME[type];
    }
    
    public int getCurrentBuses() {
        return currentBuses;
    }
    
    public int getTotalProfit() {
        return totalProfit;
    }
    
    public String getBusName(int b) {
        return buses[b].getName();
    }
    
    public int getGasLevel(int b) {
        return buses[b].getGasLevel();
    }
    
    public int getMaxGas(int b) {
        return buses[b].getMaxGas();
    }
    
    public double getGasPercentage(int b) {
        return buses[b].getGasPercentage();
    }
    
    public int getAction(int b) {
        return buses[b].getAction();
    }
    
    public int getTotalDistance(int b) {
        return buses[b].getTotalDistance();
    }
    
    public int getDistance(int b) {
        return buses[b].getDistance();
    }
    
    public int getPassengers(int b) {
        return buses[b].getPassengers();
    }
    
    public int getMaxPassengers(int b) {
        return buses[b].getMaxPassengers();
    }
    
    public int getProfit(int b) {
        return buses[b].getProfit();
    }
    
}
