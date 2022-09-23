package com.deco2800.game.components.CombatItemsComponents;


import com.deco2800.game.crafting.Materials;
import com.deco2800.game.entities.Entity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Component used to store information and methods related to combat for melee weapons.
 */
public class MeleeStatsComponent extends WeaponStatsComponent {

    private double weight;
    private String description;

    /**
     * @param damage    damage of the melee weapon
     * @param coolDown  duration before the next attack instance can be called
     * @param materials materials needed to craft the weapon
     * @param weight    weight of the weapon (affects player's movement speed)
     * @param description description of the weapon (used to target weapon animations more effectively)
     */
    public MeleeStatsComponent(double damage, double coolDown, HashMap<Materials, Integer> materials, double weight, String description) {
       super(damage, coolDown, materials);
        setWeight(weight);
        setDescription(description);
    }

    /**
     * Returns the weight of the weapon
     * @return weight of the weapon
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Returns the weapon description
     * @return description of the weapon
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the weight of the weaponS
     * @param weight set the weight of the weapon
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setDescription(String description) { this.description = description; }

    @Override
    public void auraEffect(Entity auraToApply) {
        setDamage(this.getDamage() * auraToApply.getComponent(WeaponAuraComponent.class).getDmgMultiplier());
        setCoolDown(this.getCoolDown() * auraToApply.getComponent(WeaponAuraComponent.class).getCdMultiplier());
        setWeight(this.getWeight() * auraToApply.getComponent(WeaponAuraComponent.class).getWeightMultiplier());


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               setDamage(getDamage() / auraToApply.getComponent(WeaponAuraComponent.class).getDmgMultiplier());
                               setCoolDown(getCoolDown() / auraToApply.getComponent(WeaponAuraComponent.class).getCdMultiplier());
                               setWeight(getWeight() / auraToApply.getComponent(WeaponAuraComponent.class).getWeightMultiplier());
                               timer.cancel();
                           }
                       }
                , auraToApply.getComponent(WeaponAuraComponent.class).getAuraDuration());
    }

    /**
     * Checks if two melee weapons are the same
     * @param other other melee weapon
     * @return true if they have the same stat, false otherwise
     */
    public boolean equals(MeleeStatsComponent other) {
        return this.damage == other.getDamage()
                && this.weight == other.getWeight()
                && this.coolDown == other.getCoolDown();
    }
}
