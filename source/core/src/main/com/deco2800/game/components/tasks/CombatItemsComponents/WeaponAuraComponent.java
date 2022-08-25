package com.deco2800.game.components.tasks.CombatItemsComponents;

import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;

public class WeaponAuraComponent extends Component {
    private double weightMultiplier;
    private double durationMultiplier;
    private double dmgMultiplier;
    private double cdMultiplier;
    private double areaMultiplier;
    private int auraDuration; //in milliseconds preferably

    public WeaponAuraComponent(int auraDuration, double dmgMultiplier, double cdMultiplier,
                double weightMultiplier) {
        setAuraDuration(auraDuration);
        setDmgMultiplier(dmgMultiplier);
        setCdMultiplier(cdMultiplier);
        setWeightMultiplier(weightMultiplier);
    }

    //for aoe weapons
    public WeaponAuraComponent(int auraDuration, double dmgMultiplier, double areaMultiplier,
                double durationMultiplier, double cdMultiplier) {
        setAuraDuration(auraDuration);
        setDmgMultiplier(dmgMultiplier);
        setCdMultiplier(cdMultiplier);
        setDurationMultiplier(durationMultiplier);
        setAreaMultiplier(areaMultiplier);
    }

    public void setAuraDuration(int auraDuration) {
        this.auraDuration = auraDuration;
    }

    public void setDmgMultiplier(double dmgMultiplier) {
        this.dmgMultiplier = dmgMultiplier;
    }

    public void setCdMultiplier(double cdMultiplier) {
        this.cdMultiplier = cdMultiplier;
    }

    public void setWeightMultiplier(double weightMultiplier) {
        this.weightMultiplier = weightMultiplier;
    }

    public void setDurationMultiplier(double durationMultiplier) {
        this.durationMultiplier = durationMultiplier;
    }

    public void setAreaMultiplier(double areaMultiplier) {
        this.areaMultiplier = areaMultiplier;
    }

    public double getDmgMultiplier() {
        return dmgMultiplier;
    }

    public double getCdMultiplier() {
        return cdMultiplier;
    }

    //only for area of effect
    public double getAreaMultiplier() {
        return areaMultiplier;
    }

    //only for area of effect
    public double getDurationMultiplier() {
        return durationMultiplier;
    }

    public int getAuraDuration() {
        return auraDuration;
    }

    public double getWeightMultiplier() {
        return weightMultiplier;
    }

    /*//reverts the stats to pre-buff
    public WeaponAuraComponent inverseEffect() {
        if (this.getDurationMultiplier() != 0) { //if the aura is for aoe weapons
            return new WeaponAuraComponent(-1, 1/getDmgMultiplier(), 1/getAreaMultiplier(),
                    1/getDurationMultiplier(), 1/getCdMultiplier());
        } else{
            return new WeaponAuraComponent(-1, 1/getDmgMultiplier(), 1/getCdMultiplier(),
                    1/getWeightMultiplier());
        }

     */
}
