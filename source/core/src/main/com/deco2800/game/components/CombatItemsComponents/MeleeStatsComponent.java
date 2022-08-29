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

    @Override
    public void auraEffect(Entity auraToApply) {
        setDamage(this.getDamage() * auraToApply.getComponent(WeaponAuraComponent.class).getDmgMultiplier());
        setCoolDown(this.getCoolDown() * auraToApply.getComponent(WeaponAuraComponent.class).getCdMultiplier());
        setWeight(this.getWeight() * auraToApply.getComponent(WeaponAuraComponent.class).getWeightMultiplier());


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
