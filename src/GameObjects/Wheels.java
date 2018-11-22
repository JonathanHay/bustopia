/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import Constants.WheelsConstants;

/**
 *
 * @author Leo
 */
public class Wheels {

    private int toughness;
    private int speed;
    private int toughnessMultiplier;
    private int speedMultiplier;
    private int tier;
    private int type;
    private int durability;

    private double randomMultiplier() {
        return (Math.random() * 0.1) + 0.95;
    }
    
    private boolean randomChance(int t) {
        if (Math.random() < 100 / (100 + t)) {
            return true;
        } else {
            return false;
        }
    }
    
    public Wheels(int ty, int ti) {
        tier = ti;
        type = ty;
        
        durability = 10;
        
        toughness = (int) (WheelsConstants.TOUGHNESS[ty] * (1 + tier * WheelsConstants.TOUGHNESS_MULTIPLIER[ty] / 100) * randomMultiplier());
        speed = (int) (WheelsConstants.SPEED[ty] * (1 + tier * WheelsConstants.SPEED_MULTIPLIER[ty] / 100) * randomMultiplier());
    }

    public int getToughness() {
        return toughness;
    }

    public int getSpeed() {
        return speed;
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
    
    public void setSpeed(int i){
        speed = i;
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
