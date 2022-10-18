package com.deco2800.game.components.CombatItemsComponents;

import com.deco2800.game.crafting.Materials;

import java.util.Map;

/**
 * Component used to store information and methods related to combat for aoe weapons.
 */
public class AreaOfEffectStatsComponent extends WeaponStatsComponent {

    private double areaRange;
    private double duration;

    /**
     *
     * @param damage damage of the weapon
     * @param areaRange radius that the weapon can deal damage to
     * @param duration how long the aoe is activated
     * @param coolDown duration before the next attack instance can be called (should be > than duration)
     * @param materials materials needed to craft the weapon
     */
    public AreaOfEffectStatsComponent(double damage,
                                      double areaRange,
                                      double duration,
                                      double coolDown,
                                      Map<Materials, Integer> materials,
                                      String description) {
        super(damage, coolDown, materials, description);
        setAreaRange(areaRange);
        setDuration(duration);
    }

    /**
     * Returns the radius the weapon can deal damage to
     * @return radius the weapon can deal damage to
     */
    public double getAreaRange() {
        return this.areaRange;
    }

    /**
     * Returns thr duration the aoe is activated
     * @return how long the aoe is activated
     */
    public double getDuration() {
        return this.duration;
    }

    /**
     * Sets the radius the weapon can deal damage to
     * @param areaRange radius that the weapon can deal damage to
     */
    public void setAreaRange(double areaRange) {
        this.areaRange = areaRange;
    }

    /**
     * Sets the duration the aoe weapon can be activated for at one attack instance
     * @param duration how long the aoe is activated
     */
    public void setDuration(double duration) {
        this.duration = duration;
    }

}
