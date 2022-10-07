package com.deco2800.game.components.DefensiveItemsComponents;

import com.deco2800.game.components.Component;
import com.deco2800.game.crafting.Materials;

import java.util.HashMap;

public class ArmourStatsComponent extends Component {
    protected double phyResistance;
    protected double durability;
    protected double vitality;
    protected double dmgReturn;
    protected double weight;
    protected HashMap<Materials, Integer> materials;

    /**
     *
     * @param phyResistance Used to factor with damage reduction (to replace Vitality)
     * @param durability deteremines how long the item will last (SCHEDULED TO BE REMOVED)
     * @param vitality Determines how much extra HP the item gives the player
     * @param dmgReturn Determines the percentage of damage taken returns to enemy
     * @param weight Determines the speed at which the player moves when using the item
     * @param materials Determines what materials each armour is made of
     */

    public ArmourStatsComponent(double phyResistance, double durability, double vitality,
                                double dmgReturn, double weight, HashMap<Materials, Integer> materials){
        this.phyResistance = phyResistance;
        this.durability = durability;
        this.vitality = vitality;
        this.dmgReturn = dmgReturn;
        this.weight = weight;
        this.materials = materials;
    }

    /**
     * Sets the attributes of the armour to desired stats
     */

    public void setArmourStats (double phyResistance, double durability, double vitality,
                                double dmgReturn, double weight, HashMap<Materials, Integer> materials) {
        this.phyResistance = phyResistance;
        this.durability = durability;
        this.vitality = vitality;
        this.dmgReturn = dmgReturn;
        this.weight = weight;
        this.materials = materials;
    }

    /**
     *
     * @return the physResistance
     */

    public double getPhyResistance() {
        return phyResistance;
    }

    /**
     *
     * @param phyResistance sets the physResistance to desited value
     */

    public void setPhyResistance(double phyResistance) {
        this.phyResistance = phyResistance;
    }

    /**
     *
     * @return the durability parameter
     */

    public double getDurability() {
        return durability;
    }

    public void setDurability(double durability) {
        this.durability = durability;
    }

    /**
     *
     * @return the Vitality parameter
     */

    public double getVitality() {
        return vitality;
    }

    public void setVitality(double vitality) {
        this.vitality = vitality;
    }

    public double getDmgReturn() {
        return dmgReturn;
    }

    /**
     *
     * @param dmgReturn is set to the desired value
     */
    public void setDmgReturn(double dmgReturn) {
        this.dmgReturn = dmgReturn;
    }

    /**
     *
     * @return gets the value of weight
     */

    public double getWeight() {
        return weight;
    }

    /**
     *
     * @param weight sets weight to the desired value
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     *
     * @return gets the materials required to make the item
     */
    public HashMap<Materials, Integer> getMaterials() {
        return materials;
    }

    /**
     *
     * @param materials sets materials to make the item
     */
    public void setMaterials(HashMap<Materials, Integer> materials) {
        this.materials = materials;
    }

    /**
     * Checks if two ArmourStatsComponents have the same stat.
     * @param other ArmourStatSComponent
     * @return true if the stats are the same, false otherwise
     */
    public boolean equals (ArmourStatsComponent other) {
        return phyResistance == other.getPhyResistance()
                && durability == other.durability
                && vitality == other.vitality
                && dmgReturn == other.dmgReturn
                && weight == other.weight;
    }
}
