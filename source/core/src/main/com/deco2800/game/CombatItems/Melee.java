package com.deco2800.game.CombatItems;

import java.util.Timer;
import java.util.TimerTask;

public class Melee extends Weapon {

    private double weight;

    public Melee(double damage, double coolDown, double weight, int level) {
        super(damage, coolDown, level);
        this.weight = weight; //affects movement speed of player
    }

    public double getWeight() {
        return this.weight;
    }

    //still work in progress
    @Override
    public void auraEffect(Aura auraToApply) {
        this.auraToApply = auraToApply;
        this.damage = this.damage * auraToApply.getDmgMultiplier();
        this.coolDown = this.coolDown * auraToApply.getCdMultiplier();
        this.weight = this.weight * auraToApply.getWeightMultiplier();

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
