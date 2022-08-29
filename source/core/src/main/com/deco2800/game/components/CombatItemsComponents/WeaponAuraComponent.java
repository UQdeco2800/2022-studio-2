package com.deco2800.game.components.CombatItemsComponents;

import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;

/**
 * Component used to store information and methods related to weapon buffs, debuffs and curses.
 */

public class WeaponAuraComponent extends Component {
    private double weightMultiplier;
    private double durationMultiplier;
    private double dmgMultiplier;
    private double cdMultiplier;
    private double areaMultiplier;
    private int auraDuration; //in milliseconds preferably

    /**
     *
     * @param auraDuration how long the aura is activated for the weapon
     * @param dmgMultiplier damage multiplier effect on the weapon
     * @param cdMultiplier cooldown multiplier effect on the weapon
     * @param weightMultiplier weight multiplier effect on the weapon
     */
    public WeaponAuraComponent(int auraDuration, double dmgMultiplier, double cdMultiplier,
                double weightMultiplier) {
        setAuraDuration(auraDuration);
        setDmgMultiplier(dmgMultiplier);
        setCdMultiplier(cdMultiplier);
        setWeightMultiplier(weightMultiplier);
    }

    /**
     *
     * @param auraDuration how long the aura is activated for the weapon
     * @param dmgMultiplier damage multiplier effect on the weapon
     * @param areaMultiplier multiplier on the radius that the weapon can deal damage to
     * @param durationMultiplier multiplier on the duration before the next attack instance can be called
     * @param cdMultiplier cooldown multiplier effect on the weapon
     */
    //for aoe weapons
    public WeaponAuraComponent(int auraDuration, double dmgMultiplier, double areaMultiplier,
                double durationMultiplier, double cdMultiplier) {
        setAuraDuration(auraDuration);
        setDmgMultiplier(dmgMultiplier);
        setCdMultiplier(cdMultiplier);
        setDurationMultiplier(durationMultiplier);
        setAreaMultiplier(areaMultiplier);
    }

    /**
     * Set how long the aura is activated for the weapon
     * @param auraDuration how long the aura is activated for the weapon
     */
    public void setAuraDuration(int auraDuration) {
        this.auraDuration = auraDuration;
    }

    /**
     * Set the damage multiplier on the weapon
     * @param dmgMultiplier damage multiplier effect on the weapon
     */
    public void setDmgMultiplier(double dmgMultiplier) {
        this.dmgMultiplier = dmgMultiplier;
    }

    /**
     * Set the cooldown multiplier on the weapon
     * @param cdMultiplier cooldown multiplier on the weapon
     */
    public void setCdMultiplier(double cdMultiplier) {
        this.cdMultiplier = cdMultiplier;
    }

    /**
     * Set the weight multiplier on the weapon
     * @param weightMultiplier weight multiplier on the weapon
     */
    public void setWeightMultiplier(double weightMultiplier) {
        this.weightMultiplier = weightMultiplier;
    }

    /**
     * Set the multiplier on the duration before the next attack instance can be called
     * @param durationMultiplier multiplier on the duration before the next attack instance can be called
     */
    public void setDurationMultiplier(double durationMultiplier) {
        this.durationMultiplier = durationMultiplier;
    }

    /**
     * Set the multiplier on the radius that the weapon can deal damage to
     * @param areaMultiplier multiplier on the radius that the weapon can deal damage to
     */
    public void setAreaMultiplier(double areaMultiplier) {
        this.areaMultiplier = areaMultiplier;
    }

    /**
     * Return the damage multiplier on the weapon
     * @return damage multiplier on the weapon
     */
    public double getDmgMultiplier() {
        return dmgMultiplier;
    }

    /**
     * Return the cooldown multiplier on the weapon
     * @return cooldown multiplier on the weapon
     */
    public double getCdMultiplier() {
        return cdMultiplier;
    }

    /**
     * Return the multiplier on the radius that the weapon can deal damage to
     * @return multiplier on the radius that the weapon can deal damage to
     */
    //only for area of effect
    public double getAreaMultiplier() {
        return areaMultiplier;
    }

    /**
     * Return the multiplier on the duration before the next attack instance can be called
     * @return multiplier on the duration before the next attack instance can be called
     */
    //only for area of effect
    public double getDurationMultiplier() {
        return durationMultiplier;
    }

    /**
     * Return how long the aura is activated for the weapon
     * @return duration the aura is activated for the weapon
     */
    public int getAuraDuration() {
        return auraDuration;
    }

    /**
     * Return the weight multiplier on the weapon
     * @return weight multiplier on the weapon
     */
    public double getWeightMultiplier() {
        return weightMultiplier;
    }

}
