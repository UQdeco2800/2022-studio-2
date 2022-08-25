package com.deco2800.game.components.tasks.CombatItemsComponents;

import com.deco2800.game.crafting.Materials;
import com.deco2800.game.entities.Entity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class RangedStatsComponent extends WeaponStatsComponent {

    protected double weight;
    protected boolean arrowState;
    protected Entity auraToApply;

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
    public void auraEffect(Entity auraToApply) {
        this.auraToApply = auraToApply;
        setDamage(this.getDamage() * auraToApply.getComponent(WeaponAuraComponent.class).getDmgMultiplier());
        setCoolDown(this.getCoolDown() * auraToApply.getComponent(WeaponAuraComponent.class).getCdMultiplier());
        setWeight(this.getWeight() * auraToApply.getComponent(WeaponAuraComponent.class).getWeightMultiplier());

        if (auraToApply.getComponent(WeaponAuraComponent.class).getAuraDuration() != -1) {
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
    }
}
