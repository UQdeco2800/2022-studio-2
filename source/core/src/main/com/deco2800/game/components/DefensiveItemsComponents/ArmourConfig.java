package com.deco2800.game.components.DefensiveItemsComponents;

import com.deco2800.game.crafting.Materials;

import java.util.HashMap;

public class ArmourConfig {
    public String armourName = "armour";
    //damage reduction modififer
    public double phyResistance = 10;
    //vitality is staminamax modifier
    public double vitality = 10;
    public double durability = 2;
    //move speed modifier
    public double weight = 1;
    public double dmgReturn = 1;
    public HashMap<Materials, Integer> materials;
}
