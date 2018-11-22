/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import Constants.EngineConstants;

/**
 *
 * @author Leo
 */
public class Engine {

    private int toughness;
    private int efficiency;
    private int toughnessMultiplier;
    private int efficiencyMultiplier;
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
    
    public Engine(int ty, int ti) {
        tier = ti;
        type = ty;
        
        durability = 10;
        
        toughness = (int) (EngineConstants.TOUGHNESS[ty] * (1 + tier * EngineConstants.TOUGHNESS_MULTIPLIER[ty] / 100) * randomMultiplier());
        efficiency = (int) (EngineConstants.EFFICIENCY[ty] * (1 + tier * EngineConstants.EFFICIENCY_MULTIPLIER[ty] / 100) * randomMultiplier());
    }
    
    public int getToughness(){
        return toughness;
    }
    
    public int getEfficiency(){
        return efficiency;
    }
    
    public int getTier(){
        return tier;
    }
    
    public int getType() {
        return type;
    }
    
    public void setToughness(int i){
        toughness = i;
    }
    
    public void setEfficiency(int i){
        efficiency = i;
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
