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
  private int baseAttack;
  private float damageReduction;

  public CombatStatsComponent(int health, int baseAttack) {
    setHealth(health);
    setBaseAttack(baseAttack);
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

  public void hit(CombatStatsComponent attacker) {
    int newHealth = getHealth() - (int)((1 - damageReduction) * attacker.getBaseAttack());
    setHealth(newHealth);
  }

  /**
   * Returns the entity's base attack damage.
   *
   * @return base attack damage
   */
  public float damageReduction() {
    return damageReduction;
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
