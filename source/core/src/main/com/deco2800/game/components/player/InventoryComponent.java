package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.crafting.Buildable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * A component intended to be used by the player to track their inventory.
 *
 * Currently only stores the items that implement the Buildable interface. IN_PROGRESS
 * Can also be used as a more generic component for other entities.
 */
public class InventoryComponent <T extends Buildable> extends Component {
  private static final Logger logger = LoggerFactory.getLogger(InventoryComponent.class);

  /**
   * Currently only takes ite  ms that implement the Buildable interface. TO BE IMPLEMENTED
   */
  private List<T> inventory = new ArrayList<>(16);


  /**
   * Returns the current inventory
   *
   * @return inventory items
   * */
  public List<T> getItems() {
    return List.copyOf(inventory);
  }

  /**
   * Adds an item to player's inventory.
   * @param item item to add
   */
  public void addItem(T item) {
    inventory.add(item);
  }

  /**
   * Removes an item to player's inventory.
   * @param item item to remove
   */
  public void removeItem(T item) {
    //Currently taking item as parameter, may take in index in the future
    inventory.remove(item);
  }


}
