/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import Constants.FuelTankConstants;

/**
 *
 * @author Leo
 */
public class FuelTank {

    private int toughness;
    private int size;
    private int toughnessMultiplier;
    private int sizeMultiplier;
    private int tier;
    private int type;
    private int durability;
    
    private double randomMultiplier() {
        return (Math.random() * 0.1) + 0.95;
    }
    
    private boolean randomChance(double t) {
        if (Math.random() < 100 / (100 + t)) {
            return true;
        } else {
            return false;
        }
    }
    
    public FuelTank(int ty, int ti) { 
        tier = ti;
        type = ty;
        
        durability = 10;
        
        toughness = (int) (FuelTankConstants.TOUGHNESS[ty] * (1 + tier * FuelTankConstants.TOUGHNESS_MULTIPLIER[ty] / 100) * randomMultiplier());
        size = (int) (FuelTankConstants.SIZE[ty] * (1 + tier * FuelTankConstants.SIZE_MULTIPLIER[ty] / 100) * randomMultiplier());
    }

    public int getToughness() {
        return toughness;
    }

    public int getFuelTankSize() {
        return size;
    }

    public int getTier() {
        return tier;
    }
    
    public int getType() {
        return type;
    }
    
    public void setToughness(int i){
        toughness = i;
    }
    
    public void setFuelTankSize(int i){
        size = i;
    }
    
    public void durabilityTick() {
        if (randomChance(toughness)) {
            durability -= 1;
        }
    }
    
    public void repair() {
        durability = 10;
    }
}
