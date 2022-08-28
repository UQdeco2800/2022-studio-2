package com.deco2800.game.entities.configs.CombatItemsConfig;

import com.deco2800.game.crafting.Materials;

import java.util.HashMap;

/**
 * Defines a basic set of (melee weapon) properties stored in Weapon config files to be loaded by Weapon Factory.
 */
public class MeleeConfig {
    public String weapon = "pen15";
    public double damage = 10;
    public double coolDown = 2;
    public double weight = 1;
    public HashMap<Materials, Integer> materials;
}
