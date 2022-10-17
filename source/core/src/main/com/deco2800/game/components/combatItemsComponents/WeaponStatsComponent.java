package com.deco2800.game.components.combatItemsComponents;

import com.deco2800.game.components.Component;
import com.deco2800.game.crafting.Materials;

import java.util.Map;

/**
 * Component used to store information and methods related to combat such as health, attack, etc. Any entities
 * which engage it combat should have an instance of this class registered. This class can be
 * extended for more specific combat needs.
 */
public abstract class WeaponStatsComponent extends Component {

  protected double damage;
  protected double coolDown;
  protected Map<Materials, Integer> materials;
  private String description;


  /**
   *
   * @param damage damage of the weapon
   * @param coolDown duration before the next attack instance can be called
   * @param materials materials needed to craft the weapon
   */
  protected WeaponStatsComponent(double damage, double coolDown, Map<Materials, Integer> materials, String description) {
    setDamage(damage);
    setCoolDown(coolDown);
    setMaterials(materials);
    setDescription(description);
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
   * Sets the description of the weapon
   * @param description description of the weapon
   */
  public void setDescription(String description) { this.description = description; }

  /**
   * Sets the materials required to craft the weapon
   * @param materials materials needed to craft the weapon
   */
  public void setMaterials(Map<Materials, Integer> materials) {
    this.materials = materials;
  }

  /**
   * Returns the weapon description
   * @return description of the weapon
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the materials required to craft the weapon
   * @return materials needed to craft the weapon
   */
  public Map<Materials, Integer> getMaterials() {
    return materials;
  }

}

