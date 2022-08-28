package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;


class quickBar<Potions> {

  /**
   * TO BE IMPLEMENTED
   */
  private List<Potions> quickBarItems = new ArrayList<>();

  /**
   * TO BE IMPLEMENTED
   * By default every element in the array is 0.
   */
  int[] itemQuantity = new int[6];

  /**
   * Adding potion to the quickbar.
   * TO BE IMPLEMENTED
   */
  public void setQuickBarItems(Potions potion) {

    if (quickBarItems.contains(potion)) {
      ++itemQuantity[quickBarItems.indexOf(potion)];
    } else if (quickBarItems.size() == 6) {
      //Error code here
      System.out.println("Quickbar is full.");
    } else {
      quickBarItems.add(potion);
      ++itemQuantity[quickBarItems.indexOf(potion)];
    }
  }

  /**
   * Removes the potion from the quickbar based on the input index
   *
   * @param inputIndex the index that is returned from user actions(TO BE IMPLEMENTED)
   */
  public void removePotion(int inputIndex) {
    quickBarItems.remove(inputIndex);
    itemQuantity[inputIndex] = 0;
  }

  /**
   * Consume the potion rom quickbar based on the input index
   *
   * @param inputIndex the index that is returned from user actions(TO BE IMPLEMENTED)
   *                   <p>
   *                   ****To be implemented by potion team or player team.****
   */
  public void consumePotion(int inputIndex) {
    if (quickBarItems.get(inputIndex) != null
            && itemQuantity[inputIndex] == 1) {
      //Potion consumption effect code here
      removePotion(inputIndex);
    } else if (quickBarItems.get(inputIndex) != null
            && itemQuantity[inputIndex] > 1) {
      //Potion consumption effect code here
      --itemQuantity[quickBarItems.indexOf(inputIndex)];
    }
    //Do nothing if there is no potion on the selected slot or the quantity ! >= 1
  }

}

/**
 * A component intended to be used by the player to track their inventory.
 *
 * Currently only stores the items that implement the Buildable interface. IN_PROGRESS
 * Can also be used as a more generic component for other entities.
 */
public class InventoryComponent extends Component {
  private static final Logger logger = LoggerFactory.getLogger(InventoryComponent.class);

  private final int inventorySize = 16;

  /**
   * Currently only takes ite  ms that implement the Buildable interface. TO BE IMPLEMENTED
   */
  private List<Entity> inventory = new ArrayList<>(inventorySize);

  /**
   * TO BE IMPLEMENTED
   * By default every inventory item in the array has a quantity of 0.
   */
  int[] itemQuantity = new int[16];

  /**
   * Returns the current inventory
   * @return inventory items
   */
  public List<Entity> getItems() {
    return List.copyOf(inventory);
  }

  /**
   * Adds an item to player's inventory.
   * @param item item to add
   */
  public void addItem(Entity item) {
    inventory.add(item);
  }

  /**
   * Removes an item to player's inventory.
   * @param item item to remove
   */
  public void removeItem(Entity item) {
    //Currently taking item as parameter, may take in index in the future
    inventory.remove(item);
  }

  /**
   *
   */
  void displayInventory() {}

  /**
   * Implemented by potion team or player team.
   */
  void consumeItem() {}

  /**
   * Implemented by weapon team or player team.
   */
  void equipWeapon() {}

}
