package com.deco2800.game.components.tasks.CombatItemsComponents;

import com.deco2800.game.CombatItems.Aura;
import com.deco2800.game.components.Component;
import com.deco2800.game.crafting.Materials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Component used to store information related to combat such as health, attack, etc. Any entities
 * which engage it combat should have an instance of this class registered. This class can be
 * extended for more specific combat needs.
 */
public abstract class WeaponStatsComponent extends Component {

  private static final Logger logger = LoggerFactory.getLogger(WeaponStatsComponent.class);
  protected double damage;
  protected double coolDown;
  protected HashMap<Materials, Integer> materials;

  public WeaponStatsComponent(double damage, double coolDown, HashMap<Materials, Integer> materials) {
    setDamage(damage);
    setCoolDown(coolDown);
    setMaterials(materials);
  }

  public double getDamage() {
    return damage;
  }

  public double getCoolDown() {
    return coolDown;
  }

  public void setDamage(double damage) {
    this.damage = damage;
  }

  public void setCoolDown(double coolDown) {
    this.coolDown = coolDown;
  }

  public void setMaterials(HashMap<Materials, Integer> materials) {
    this.materials = materials;
  }


  /*public void setCraftingRecipe() {

  }*/

  public abstract void auraEffect(Aura auraToApply);

  public void revertAuraEffect(Aura auraToRevert) {
    this.auraEffect(auraToRevert.inverseEffect());
  }
}
