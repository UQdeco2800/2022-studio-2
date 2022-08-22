package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * A component intended to be used by the player to track their inventory.
 *
 * Currently only stores the gold amount but can be extended for more advanced functionality such as storing items.
 * Can also be used as a more generic component for other entities.
 */
public class InventoryComponent extends Component {
  private static final Logger logger = LoggerFactory.getLogger(InventoryComponent.class);
  private int gold;

  /**
   * Currently defined as String array because I forgot how to do a generic list. TO BE IMPLEMENTED
   */
  private String[] inventory = new String[16];

  public InventoryComponent(int gold) {
    setGold(gold);
  }

  /**
   * Returns the player's gold.
   *
   * @return entity's health
   */

  public String getToilet() {
    return "Toilet";
  }

  /**
   * Returns the current inventory
   *
   * @return inventory items
   * */
  public String[] getItems() {
    return Arrays.copyOf(inventory, 16);
  }

  public int getGold() {
    return this.gold;
  }

  /**
   * Returns if the player has a certain amount of gold.
   * @param gold required amount of gold
   * @return player has greater than or equal to the required amount of gold
   */
  public Boolean hasGold(int gold) {
    return this.gold >= gold;
  }

  /**
   * Sets the player's gold. Gold has a minimum bound of 0.
   *
   * @param gold gold
   */
  public void setGold(int gold) {
    if (gold >= 0) {
      this.gold = gold;
    } else {
      this.gold = 0;
    }
    logger.debug("Setting gold to {}", this.gold);
  }

  /**
   * Adds to the player's gold. The amount added can be negative.
   * @param gold gold to add
   */
  public void addGold(int gold) {
    setGold(this.gold + gold);
  }
}
