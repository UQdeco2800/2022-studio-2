package com.deco2800.game.components.tasks.CombatItemsComponents;

import com.deco2800.game.CombatItems.Aura;
import com.deco2800.game.crafting.Materials;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class RangedStatsComponent extends WeaponStatsComponent {

    protected double weight;
    protected boolean arrowState;
    protected Aura auraToApply;

    public RangedStatsComponent(double damage, double coolDown, HashMap<Materials, Integer> materials, double weight) {
        super(damage, coolDown, materials);
        setWeight(weight);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public void auraEffect(Aura auraToApply) {
        this.auraToApply = auraToApply;
        setDamage(this.getDamage() * auraToApply.getDmgMultiplier());
        setCoolDown(this.getCoolDown() * auraToApply.getCdMultiplier());
        setWeight(this.getWeight() * auraToApply.getWeightMultiplier());

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
