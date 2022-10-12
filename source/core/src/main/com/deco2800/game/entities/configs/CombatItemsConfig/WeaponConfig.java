package com.deco2800.game.entities.configs.CombatItemsConfig;

import com.deco2800.game.crafting.Materials;

import java.util.HashMap;
import java.util.Set;

/**
 * Defines a basic set of (melee weapon) properties stored in Weapon config files to be loaded by Weapon Factory.
 */
public class WeaponConfig {
    public static final String weapon = "pen15";
    public static final double damage = 10;
    public static final double coolDown = 2;
    public static final double weight = 1;
    public HashMap<Materials, Integer> materials;
}
