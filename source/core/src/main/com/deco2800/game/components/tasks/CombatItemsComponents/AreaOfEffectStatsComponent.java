package com.deco2800.game.components.tasks.CombatItemsComponents;

import com.deco2800.game.CombatItems.Aura;
import com.deco2800.game.crafting.Materials;

import java.awt.geom.Area;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class AreaOfEffectStatsComponent extends WeaponStatsComponent {

    private double areaRange;
    private double duration;
    private Aura auraToApply;

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
    public void auraEffect(Aura auraToApply) {
        this.auraToApply = auraToApply;
        setDamage(this.getDamage() * auraToApply.getDmgMultiplier());
        setCoolDown(this.getCoolDown() * auraToApply.getCdMultiplier());
        setDuration(this.getDuration() * auraToApply.getDurationMultiplier());
        setAreaRange(this.getAreaRange() * auraToApply.getAreaMultiplier());

        if (auraToApply.getAuraDuration() != -1) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                               @Override
                               public void run() {
                                   revertAuraEffect(auraToApply);
                                   timer.cancel();
                               }
                           }
                    , auraToApply.getAuraDuration());
        }
    }
}
