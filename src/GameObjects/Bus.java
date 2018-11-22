/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import Constants.BusConstants;
import Constants.RegionsConstants;

/**
 *
 * @author Leo
 */
public class Bus {

    public static final int NOT_OPERATIONAL = -1;
    public static final int NONE = 0;
    public static final int GAS = 1;
    public static final int BUSSTOP = 2;
    public static final int BOTH = 3;

    private Engine engine;
    private FuelTank fuelTank;
    private Wheels wheels;
    private Accessory accessory;

    private int operational;
    private boolean gas;
    private boolean busStop;

    private int gasLevel;
    private int gasPercentage;

    private int distance;
    private int totalDistance;

    private int passengers;
    private int maxPassengers;
    private int tempPassengers;

    private int ticketPrice;
    private int profit;
    private int scrap;

    private int baseSpeed;
    private int baseEfficiency;
    private int baseFuelTank;
    private int baseAppeal;
    private int baseScrapRate;

    private int totalSpeed;
    private int totalEfficiency;
    private int totalFuelTank;
    private int totalAppeal;
    private int totalScrapRate;

    private int type;
    private int region;

    private int passengerRate;
    private int gasPrice;
    private int towPrice;
    private int chanceGas;
    private int chanceBusStop;

    private double random;

    public Bus(int t, int r) {
        type = t;
        region = r;

        engine = new Engine(BusConstants.BASE_ENGINE[type], BusConstants.BASE_ENGINE_TIER[type]);
        wheels = new Wheels(BusConstants.BASE_WHEELS[type], BusConstants.BASE_WHEELS_TIER[type]);
        fuelTank = new FuelTank(BusConstants.BASE_FUELTANK[type], BusConstants.BASE_FUELTANK_TIER[type]);
        accessory = new Accessory(BusConstants.BASE_ACCESORY[type], BusConstants.BASE_ACCESORY_TIER[type]);

        baseSpeed = BusConstants.BASE_SPEED[type];
        baseEfficiency = BusConstants.BASE_EFFICIENCY[type];
        baseFuelTank = BusConstants.BASE_FUELTANKSIZE[type];
        baseAppeal = BusConstants.BASE_APPEAL[type];
        baseScrapRate = BusConstants.BASE_SCRAP[type];

        totalSpeed = wheels.getSpeed() + baseSpeed;
        totalEfficiency = engine.getEfficiency() + baseEfficiency;
        totalFuelTank = fuelTank.getFuelTankSize() + baseFuelTank;
        totalAppeal = baseAppeal;
        totalScrapRate = baseScrapRate;

        ticketPrice = 10;
        profit = 0;
        scrap = 0;
        operational = 0;
        gasPercentage = 100;
        gasLevel = totalFuelTank;

        passengerRate = RegionsConstants.PASSENGERRATE[region];
        gasPrice = RegionsConstants.GASPRICE[region];
        towPrice = RegionsConstants.TOWPRICE[region];
        chanceGas = RegionsConstants.CHANCE_GAS[region];
        chanceBusStop = RegionsConstants.CHANCE_BUSSTOP[region];

        maxPassengers = BusConstants.MAX_PASSENGERS[type];
    }

    public void setRegion(int i) {
        region = i;

        passengerRate = RegionsConstants.PASSENGERRATE[region];
        gasPrice = RegionsConstants.GASPRICE[region];
        towPrice = RegionsConstants.TOWPRICE[region];
        chanceGas = RegionsConstants.CHANCE_GAS[region];
        chanceBusStop = RegionsConstants.CHANCE_BUSSTOP[region];
    }

    public void tick() {
        if (operational == 0) {
            distance = (int) (totalSpeed * (Math.random() + .5));
            totalDistance += distance;

            gasLevel -= distance * 10 / (totalEfficiency);
            if (gasLevel <= 0) {
                gasLevel = 0;
                gasPercentage = 0;
                profit -= passengers * ticketPrice;
                passengers = 0;
                operational = (int) (Math.random() * 5) + 5;
            } else {
                gasPercentage = gasLevel * 100 / totalFuelTank;

                random = Math.random();
                if (random < 0.1) {
                    engine.durabilityTick();
                } else if (random < 0.2) {
                    wheels.durabilityTick();
                } else if (random < 0.3) {
                    fuelTank.durabilityTick();
                }

                random = Math.random();
                if (random * 10 < totalScrapRate) {
                    scrap += (int) (Math.random() * 10);
                }

                random = Math.random() * 1000;
                if (random < chanceGas * Math.sqrt(distance)) {
                    fillGas();
                    gas = true;
                } else {
                    gas = false;
                }

                random = Math.random() * 1000;
                if (random < chanceBusStop * Math.sqrt(distance)) {
                    busStop();
                    busStop = true;
                } else {
                    busStop = false;
                }
            }
        } else {
            distance = 0;
            operational--;
            if (operational == 0) {
                fillGas();
                profit -= towPrice;
                gas = true;
            }
        }
    }

    public void fillGas() {
        profit -= (totalFuelTank - gasLevel) * gasPrice / 250;
        gasLevel = totalFuelTank;
        gasPercentage = 100;
    }

    public void busStop() {
        tempPassengers = (int) (Math.random() * passengerRate * totalAppeal / 100);
        if (tempPassengers > passengers) {
            tempPassengers = passengers;
        }
        passengers -= tempPassengers;

        tempPassengers = (int) (Math.random() * passengerRate * totalAppeal / 100);
        if (tempPassengers + passengers > maxPassengers) {
            tempPassengers = maxPassengers - passengers;
        }
        passengers += tempPassengers;
        profit += tempPassengers * ticketPrice;
    }

    public void newDay() {
        passengers = 0;
        profit = 0;
        scrap = 0;
        gasLevel = totalFuelTank;
    }

    public void setEngine(Engine e) {
        engine = e;
        totalEfficiency = engine.getEfficiency() + baseEfficiency;

    }

    public void setFuelTank(FuelTank f) {
        fuelTank = f;
        totalFuelTank = fuelTank.getFuelTankSize() + baseFuelTank;
    }

    public void setWheels(Wheels w) {
        wheels = w;
        totalSpeed = wheels.getSpeed() + baseSpeed;
    }

    public void setAccesory(Accessory a) {
        accessory = a;
    }

    public Engine getEngine() {
        return engine;
    }

    public FuelTank getFuelTank() {
        return fuelTank;
    }

    public Wheels getWheels() {
        return wheels;
    }

    //////////////////////////////////////////////////////////////////////////////
    public int getGasLevel() {
        return gasLevel;
    }

    public int getMaxGas() {
        return totalFuelTank;
    }

    public double getGasPercentage() {
        return gasPercentage;
    }

    public int getAction() {
        int temp = 0;
        // -1 = not operational
        // 0 = nothing
        // 1 = gas station
        // 2 = bus stop
        // 3 = both

        if (operational > 0) {
            return -1;
        }
        if (gas) {
            temp += 1;
        }
        if (busStop) {
            temp += 2;
        }

        return temp;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public int getDistance() {
        return distance;
    }

    public int getPassengers() {
        return passengers;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public int getProfit() {
        return profit;
    }

    public int getScrap() {
        return scrap;
    }

    public String getName() {
        return BusConstants.TYPE_NAME[type];
    }

    public int getType() {
        return type;
    }

    public int getRegion() {
        return region;
    }

}
