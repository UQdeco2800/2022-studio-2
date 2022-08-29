package com.deco2800.game.components.CombatItemsComponents;

import com.deco2800.game.crafting.Materials;
import com.deco2800.game.entities.Entity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Component used to store information and methods related to combat for aoe weapons.
 */
public class AreaOfEffectStatsComponent extends WeaponStatsComponent {

    private double areaRange;
    private double duration;
    private Entity auraToApply;

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
                                      HashMap<Materials, Integer> materials) {
        super(damage, coolDown, materials);
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

    @Override
    public void auraEffect(Entity auraToApply) {
        this.auraToApply = auraToApply;
        setDamage(this.getDamage() * auraToApply.getComponent(WeaponAuraComponent.class).getDmgMultiplier());
        setCoolDown(this.getCoolDown() * auraToApply.getComponent(WeaponAuraComponent.class).getCdMultiplier());
        setDuration(this.getDuration() * auraToApply.getComponent(WeaponAuraComponent.class).getDurationMultiplier());
        setAreaRange(this.getAreaRange() * auraToApply.getComponent(WeaponAuraComponent.class).getAreaMultiplier());

        if (this.getAreaRange() * auraToApply.getComponent(WeaponAuraComponent.class).getAuraDuration() != -1) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                               @Override
                               public void run() {
                                   setDamage(getDamage() / auraToApply.getComponent(WeaponAuraComponent.class).getDmgMultiplier());
                                   setCoolDown(getCoolDown() / auraToApply.getComponent(WeaponAuraComponent.class).getCdMultiplier());
                                   setDuration(getDuration() / auraToApply.getComponent(WeaponAuraComponent.class).getDurationMultiplier());
                                   setAreaRange(getAreaRange() / auraToApply.getComponent(WeaponAuraComponent.class).getAreaMultiplier());
                                   timer.cancel();
                               }
                           }
                    , auraToApply.getComponent(WeaponAuraComponent.class).getAuraDuration());
        }
    }
}
