package com.deco2800.game.CombatItems;

import java.util.Timer;
import java.util.TimerTask;

public class AreaOfEffect extends Weapon { //for future sprints

    private double areaRange;
    private double duration;

    public AreaOfEffect(double damage,
                        double areaRange,
                        double duration,
                        double coolDown,
                        int level) {
        super(damage, coolDown, level);
        this.areaRange = areaRange;
        this.duration = duration;
    }

    @Override
    public void auraEffect(Aura auraToApply) {
        this.auraToApply = auraToApply;
        this.damage = this.damage * auraToApply.getDmgMultiplier();
        this.areaRange = this.areaRange * auraToApply.getAreaMultiplier();
        this.duration = this.duration * auraToApply.getDurationMultiplier();
        this.coolDown = this.coolDown * auraToApply.getCdMultiplier();

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
