package com.deco2800.game.components.CombatItemsComponents;


import com.deco2800.game.crafting.Materials;

import java.util.HashMap;

/**
 * Component used to store information and methods related to combat for melee weapons.
 */
public class PhyiscalWeaponStatsComponent extends WeaponStatsComponent {

    private double weight;
    /**
     * @param damage    damage of the melee weapon
     * @param coolDown  duration before the next attack instance can be called
     * @param materials materials needed to craft the weapon
     * @param weight    weight of the weapon (affects player's movement speed)
     * @param description description of the weapon (used to target weapon animations more effectively)
     */
    public PhyiscalWeaponStatsComponent(double damage, double coolDown, HashMap<Materials, Integer> materials, double weight, String description) {
       super(damage, coolDown, materials, description);
        setWeight(weight);
    }

    /**
     * Returns the weight of the weapon
     * @return weight of the weapon
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the weapons
     * @param weight set the weight of the weapon
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Checks if two melee weapons are the same
     * @param other other melee weapon
     * @return true if they have the same stat, false otherwise //implemented by peter from team 02
     */
    public boolean equals(PhyiscalWeaponStatsComponent other) {
        return this.damage == other.getDamage()
                && this.weight == other.getWeight()
                && this.coolDown == other.getCoolDown();
    }
}
