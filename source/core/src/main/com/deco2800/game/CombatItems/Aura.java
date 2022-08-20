package com.deco2800.game.CombatItems;

public class Aura {

    private double weightMultiplier;
    private double durationMultiplier;
    private double dmgMultiplier;
    private double cdMultiplier;
    private double areaMultiplier;
    private int auraDuration; //in milliseconds preferably

    //for melee and ranged weapons
    public Aura(int auraDuration, double dmgMultiplier, double cdMultiplier,
                double weightMultiplier) {
        this.dmgMultiplier = dmgMultiplier;
        this.auraDuration = auraDuration; //how long the aura lasts
        this.cdMultiplier = cdMultiplier;
        this.weightMultiplier = weightMultiplier;
    }

    //for aoe weapons
    public Aura(int auraDuration, double dmgMultiplier, double areaMultiplier,
                double durationMultiplier, double cdMultiplier) {
        this.dmgMultiplier = dmgMultiplier;
        this.areaMultiplier = areaMultiplier;
        this.auraDuration = auraDuration;
        this.durationMultiplier = durationMultiplier;
        this.cdMultiplier = cdMultiplier;
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

    //reverts the stats to pre-buff
    public Aura inverseEffect() {
        if (this.getDurationMultiplier() != 0) { //if the aura is for aoe weapons
            return new Aura(-1, 1/getDmgMultiplier(), 1/getAreaMultiplier(),
                    1/getDurationMultiplier(), 1/getCdMultiplier());
        } else{
            return new Aura(-1, 1/getDmgMultiplier(), 1/getCdMultiplier(), 1/getWeightMultiplier());
        }
    }
}
