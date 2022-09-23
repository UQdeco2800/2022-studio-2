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

    private long auraEndTime;

    Entity auraApplied;
    /**
     *
     * @param damage damage of the melee weapon
     * @param coolDown duration before the next attack instance can be called
     * @param materials materials needed to craft the weapon
     * @param weight weight of the weapon (affects player's movement speed)
     */
    public MeleeStatsComponent(double damage, double coolDown, HashMap<Materials, Integer> materials, double weight) {
       super(damage, coolDown, materials);
        setWeight(weight);
    }

    @Override
    public void update() {
        checkAuraEffect();
        System.out.println(entity.getComponent(MeleeStatsComponent.class).getDamage());
        System.out.println(auraApplied != null);
    }

    /**
     * Returns the weight of the weapon
     * @return weight of the weapon
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the weaponS
     * @param weight set the weight of the weapon
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void checkAuraEffect() {
        if (auraApplied != null) {
            System.out.println("dasdad");
            System.out.println("dasdad");
            System.out.println("dasdad");
            System.out.println("dasdad");
            System.out.println("dasdad");
            System.out.println("dasdad");
            System.out.println("dasdad");
            System.out.println("dasdad");
            System.out.println("dasdad");
            System.out.println("dasdad");
            System.out.println("dasdad");
            System.out.println("dasdad");
            System.out.println("dasdad");
            System.out.println("dasdad");
            setDamage(getDamage() / auraApplied.getComponent(WeaponAuraComponent.class).getDmgMultiplier());
            setCoolDown(getCoolDown() / auraApplied.getComponent(WeaponAuraComponent.class).getCdMultiplier());
            setWeight(getWeight() / auraApplied.getComponent(WeaponAuraComponent.class).getWeightMultiplier());
            auraApplied = null;
            auraEndTime = 0;
        }
    }

    @Override
    public void auraEffect(Entity auraToApply) {
        auraEndTime = System.currentTimeMillis() + auraToApply.getComponent(WeaponAuraComponent.class).getAuraDuration();
        auraApplied = auraToApply;
        setDamage(this.getDamage() * auraToApply.getComponent(WeaponAuraComponent.class).getDmgMultiplier());
        setCoolDown(this.getCoolDown() * auraToApply.getComponent(WeaponAuraComponent.class).getCdMultiplier());
        setWeight(this.getWeight() * auraToApply.getComponent(WeaponAuraComponent.class).getWeightMultiplier());
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
