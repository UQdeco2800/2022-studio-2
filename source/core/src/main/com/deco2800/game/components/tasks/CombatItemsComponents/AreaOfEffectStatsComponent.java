package com.deco2800.game.components.tasks.CombatItemsComponents;

import com.deco2800.game.crafting.Materials;
import com.deco2800.game.entities.Entity;

import java.awt.geom.Area;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class AreaOfEffectStatsComponent extends WeaponStatsComponent {

    private double areaRange;
    private double duration;
    private Entity auraToApply;

    public AreaOfEffectStatsComponent(double damage,
                                      double areaRange,
                                      double duration,
                                      double coolDown,
                                      HashMap<Materials, Integer> materials) {
        super(damage, coolDown, materials);
        setAreaRange(areaRange);
        setDuration(duration);
    }

    public double getAreaRange() {
        return this.areaRange;
    }

    public double getDuration() {
        return this.duration;
    }

    public void setAreaRange(double areaRange) {
        this.areaRange = areaRange;
    }

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
