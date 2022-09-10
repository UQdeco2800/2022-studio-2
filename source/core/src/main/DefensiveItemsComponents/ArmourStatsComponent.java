package DefensiveItemsComponents;

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

    public ArmourStatsComponent(double phyResistance, double durability, double vitality,
                                double dmgReturn, double weight, HashMap<Materials, Integer> materials){
        this.phyResistance = phyResistance;
        this.durability = durability;
        this.vitality = vitality;
        this.dmgReturn = dmgReturn;
        this.weight = weight;
        this.materials = materials;
    }

    public double getPhyResistance() {
        return phyResistance;
    }

    public void setPhyResistance(double phyResistance) {
        this.phyResistance = phyResistance;
    }

    public double getDurability() {
        return durability;
    }

    public void setDurability(double durability) {
        this.durability = durability;
    }

    public double getVitality() {
        return vitality;
    }

    public void setVitality(double vitality) {
        this.vitality = vitality;
    }

    public double getDmgReturn() {
        return dmgReturn;
    }

    public void setDmgReturn(double dmgReturn) {
        this.dmgReturn = dmgReturn;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public HashMap<Materials, Integer> getMaterials() {
        return materials;
    }

    public void setMaterials(HashMap<Materials, Integer> materials) {
        this.materials = materials;
    }
}
