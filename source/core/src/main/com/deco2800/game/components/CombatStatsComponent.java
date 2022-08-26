package com.deco2800.game.components;

import com.badlogic.gdx.utils.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component used to store information related to combat such as health, attack, etc. Any entities
 * which engage it combat should have an instance of this class registered. This class can be
 * extended for more specific combat needs.
 */
public class CombatStatsComponent extends Component {

  private static final Logger logger = LoggerFactory.getLogger(CombatStatsComponent.class);
  private int health;
  private int mana;
  private int maxMana;
  private int manaRegenerationRate=1;
  private int maxStamina;
  private int stamina;
  private int staminaRegenerationRate=1;
  private int baseAttack;
  private float damageReduction;

  @Override
  public void create(){
    entity.getEvents().addListener("increaseStamina",this::addStamina);
    entity.getEvents().addListener("decreaseStamina",this::addStamina);
    entity.getEvents().addListener("increaseMana",this::addMana);
    entity.getEvents().addListener("decreaseMana",this::addMana);
  }

  public CombatStatsComponent(int health, int baseAttack, int stamina, int mana) {
    setHealth(health);
    setBaseAttack(baseAttack);
    setMaxStamina(stamina);
    setStamina(stamina);
    setMaxMana(mana);
    setMana(mana);
    setDamageReduction(0); // Damage reduction is always 0 unless specified otherwise
  }

  /**
   * Returns true if the entity's has 0 health, otherwise false.
   *
   * @return is player dead
   */
  public Boolean isDead() {
    return health == 0;
  }

  /**
   * Returns the entity's health.
   *
   * @return entity's health
   */
  public int getHealth() {
    return health;
  }

  /**
   * Sets the entity's health. Health has a minimum bound of 0.
   *
   * @param health health
   */
  public void setHealth(int health) {
    if (health >= 0) {
      this.health = health;
    } else {
      this.health = 0;
    }
    if (entity != null) {
      entity.getEvents().trigger("updateHealth", this.health);

    }
  }

  /**
   * Adds to the player's health. The amount added can be negative.
   *
   * @param health health to add
   */
  public void addHealth(int health) {
    setHealth(this.health + health);
  }

  /**
   * Returns the entity's base attack damage.
   *
   * @return base attack damage
   */
  public int getBaseAttack() {
    return baseAttack;
  }

  /**
   * Sets the entity's attack damage. Attack damage has a minimum bound of 0.
   *
   * @param attack Attack damage
   */
  public void setBaseAttack(int attack) {
    if (attack >= 0) {
      this.baseAttack = attack;
    } else {
      logger.error("Can not set base attack to a negative attack value");
    }
  }

  /**
   * Reduce entity health due to an attack. Decreases by the damage reduction multiplier.
   *
   * @param attacker  Attacking entity combatstats component
   */
  public void hit(CombatStatsComponent attacker) {
    int newHealth = getHealth() - (int)((1 - damageReduction) * attacker.getBaseAttack());
    setHealth(newHealth);
  }

  /**
   * Returns the entity's stamina.
   *
   * @return entity's stamina
   */
  public int getStamina() {
    return stamina;
  }

  /**
   * Sets the entity's stamina. Stamina has a minimum bound of 0 and a maximum bound of the max stamina.
   *
   * @param stamina stamina
   */
  public void setStamina(int stamina) {
    if (stamina >= 0 && stamina <= maxStamina) {
      this.stamina = stamina;
    } else {
      this.stamina = 0;
    }
    if (entity != null) {
      entity.getEvents().trigger("updateStamina", this.stamina);
    }
  }

  /**
   * Adds to the player's stamina. The amount added can be negative.
   *
   * @param stamina stamina to add
   */
  public void addStamina(int stamina) {
    setStamina(this.stamina + stamina);
  }

  /**
   * Gets the player's maximum stamina.
   * @return maxStamina player's maximum stamina.
   */
  public int getMaxStamina(){
    return this.maxStamina;
  }

  /**
   * Sets the entity's maximum stamina. Max stamina has a minimum bound of 0.
   * @param maxStamina player's maximum stamina.
   */
  public void setMaxStamina(int maxStamina){
    if(maxStamina>=0) {
      this.maxStamina = maxStamina;
    }
    else{
      this.maxStamina = 0;
    }
  }


  /**
   * Sets the entity's stamina regeneration rate.
   * @param staminaRegenerationRate entity's stamina regeneration rate
   */
  public void setStaminaRegenerationRate(int staminaRegenerationRate){
    this.staminaRegenerationRate=staminaRegenerationRate;
    if (entity != null) {
      entity.getEvents().trigger("getStaminaRegenerationRate", this.staminaRegenerationRate);
    }
  }

  /**
   * Gets the entity's stamina regeneration rate.
   * @return staminaRegenerationRate entity's stamina regeneration rate
   */
  public int getStaminaRegenerationRate(){
    return staminaRegenerationRate;
  }

  /**
   * Returns the entity's mana.
   *
   * @return entity's mana
   */
  public int getMana() {
    return mana;
  }

  /**
   * Sets the entity's mana. Stamina has a minimum bound of 0 and a maximum bound of the max mana.
   *
   * @param mana mana
   */
  public void setMana(int mana) {
    if (mana >= 0 && mana <= mana) {
      this.mana = mana;
    } else {
      this.mana = 0;
    }
    if (entity != null) {
      entity.getEvents().trigger("updateMana", this.mana);
    }
  }

  /**
   * Adds to the player's mana. The amount added can be negative.
   *
   * @param mana mana to add
   */
  public void addMana(int mana) {
    setMana(this.mana + mana);
  }

  /**
   * Gets the player's maximum mana
   * @return maxMana player's maximum mana.
   */
  public int getMaxMana(){
    return this.maxMana;
  }

  /**
   * Sets the entity's maximum mana. Max mana has a minimum bound of 0.
   * @param maxMana player's maximum mana.
   */
  public void setMaxMana(int maxMana){
    if(maxMana>=0) {
      this.maxMana = maxMana;
    }
    else{
      this.maxMana = 0;
    }
  }


  /**
   * Sets the entity's RegenerationRate regeneration rate.
   * @param manaRegenerationRate entity's stamina regeneration rate
   */
  public void setManaRegenerationRate(int manaRegenerationRate){
    this.manaRegenerationRate=manaRegenerationRate;
    if (entity != null) {
      entity.getEvents().trigger("getManaRegenerationRate", this.manaRegenerationRate);
    }
  }

  /**
   * Gets the entity's mana regeneration rate.
   * @return manaRegenerationRate entity's mana regeneration rate
   */
  public int getManaRegenerationRate(){
    return manaRegenerationRate;
  }

  /**
   * Sets the entity's damage reduction. Damage reduction damage has a minimum bound of 0.
   *
   * @param damageReduction Attack damage
   */
  public void setDamageReduction(float damageReduction) {
    if (damageReduction >= 0) {
      this.damageReduction = damageReduction;
    } else {
      logger.error("Can not set damage reduction to a negative value");
    }
  }

  /**
   * Returns the current damageReduction stat.
   *
   * @return The float value of damageReduction.
   */
  public float getDamageReduction() { return damageReduction; }

}
