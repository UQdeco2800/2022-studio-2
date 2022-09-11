package com.deco2800.game.components.CombatItemsComponents;

import com.deco2800.game.components.Component;
import com.deco2800.game.crafting.Materials;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.WeaponFactory;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Component used to store information and methods related to combat such as health, attack, etc. Any entities
 * which engage it combat should have an instance of this class registered. This class can be
 * extended for more specific combat needs.
 */
public abstract class WeaponStatsComponent extends Component {

  private static final Logger logger = LoggerFactory.getLogger(WeaponStatsComponent.class);
  protected double damage;
  protected double coolDown;
  protected HashMap<Materials, Integer> materials;

  /**
   *
   * @param damage damage of the weapon
   * @param coolDown duration before the next attack instance can be called
   * @param materials materials needed to craft the weapon
   */
  public WeaponStatsComponent(double damage, double coolDown, HashMap<Materials, Integer> materials) {
    setDamage(damage);
    setCoolDown(coolDown);
    setMaterials(materials);
  }

  /**
   * Returns the damage of the weapon
   * @return damage of the weapon
   */
  public double getDamage() {
    return damage;
  }

  /**
   * Returns the cooldown of the weapon
   * @return duration before the next attack instance can be called
   */
  public double getCoolDown() {
    return coolDown;
  }

  /**
   * Sets the damage of the weapon
   * @param damage damage of the weapon
   */
  public void setDamage(double damage) {
    this.damage = damage;
  }

  /**
   * Sets the cooldown of the weapon
   * @param coolDown duration before the next attack instance can be called
   */
  public void setCoolDown(double coolDown) {
    this.coolDown = coolDown;
  }

  /**
   * Sets the materials required to craft the weapon
   * @param materials materials needed to craft the weapon
   */
  public void setMaterials(HashMap<Materials, Integer> materials) {
    this.materials = materials;
  }

  /**
   * Returns the materials required to craft the weapon
   * @return materials needed to craft the weapon
   */
  public HashMap<Materials, Integer> getMaterials() {
    return materials;
  }


  /**
   * Applies the given aura to the weapon
   * @param auraToApply aura (buff, debuff, curse) to implement to the weapon
   */
  public abstract void auraEffect(Entity auraToApply);


}
