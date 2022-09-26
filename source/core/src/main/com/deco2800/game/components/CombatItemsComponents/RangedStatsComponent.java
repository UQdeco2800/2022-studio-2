package com.deco2800.game.components.CombatItemsComponents;

import com.deco2800.game.crafting.Materials;
import com.deco2800.game.entities.Entity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * /**
 *  * Component used to store information and methods related to combat for ranged weapons.
 */
//delapidated DO NOT USE THIS
public class RangedStatsComponent extends WeaponStatsComponent {

    protected double weight;
    protected boolean arrowState;

    /**
     *
     * @param damage damage of the ranged weapon
     * @param coolDown duration before the next attack instance can be called
     * @param materials materials needed to craft the weapon
     * @param weight weight of the weapon (affects player's movement speed)
     */
    public RangedStatsComponent(double damage, double coolDown, HashMap<Materials, Integer> materials, double weight, String description) {
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
     * Sets the weight of the weapon
     * @param weight set the weight of the weapon
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Checks if two ranged weapons are the same
     * @param other other melee weapon
     * @return true if they have the same stat, false otherwise //implemented by peter from team 02
     */
    public boolean equals(RangedStatsComponent other) {
        return this.damage == other.getDamage()
                && this.weight == other.getWeight()
                && this.coolDown == other.getCoolDown();
    }

}
