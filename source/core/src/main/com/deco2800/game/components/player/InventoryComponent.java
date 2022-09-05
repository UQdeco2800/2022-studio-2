package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.EntityTypes;
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
public class InventoryComponent extends Component {
  private static final Logger logger = LoggerFactory.getLogger(InventoryComponent.class);

  /**
   * Initial inventory size
   */
  private final int inventorySize = 16;

  /**
   * Initial item equipment slot
   */
  private final int equipSlots = 2;

  /**
   * An inventory unit for players to inspect and store their items.
   */
  private List<Entity> inventory = new ArrayList<>(inventorySize);

  /**
   * Slot 1(index 0) is set to be weapon and slot2(index 2) for armour.
   */
  private Entity[] equippables = new Entity[equipSlots];

  /**
   * Items' quantity, the indices of inventory are corresponded to itemQuantity's indices.
   */
  private int[] itemQuantity = new int[inventorySize];

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
    if (inventory.size() == inventorySize) {
      //Error code here.
      //Error should end this block of code
    } else if (!inventory.contains(item)) {
        inventory.add(item);
    } else if ((item.checkEntityType(EntityTypes.MELEE)
            || item.checkEntityType(EntityTypes.RANGED))
            && inventory.contains(item)) {
      //Does weapon have quantity?
    }

    //Item quantity undefined. TO BE IMPLEMENTED
    ++itemQuantity[inventory.indexOf(item)];
  }

  /**
   * Removes an item to player's inventory.
   * @param item item to remove
   * @requires getItemQuantity(item) >= 1
   */
  public void removeItem(Entity item) {
    --itemQuantity[inventory.indexOf(item)];
    if (getItemQuantity(item) == 0) {
      inventory.remove(item);
    }
  }

  /**
   * Removes an item to player's inventory.
   * @param index item's index stored in inventory
   * @requires inventory.indexOf(index) != -1 && getItemQuantity(index) >= 1
   */
  public void removeItem(int index) {
    --itemQuantity[index];
    if (getItemQuantity(index) == 0) {
      inventory.remove(index);
    }
  }

  /**
   * Returns the item's quantity
   * @param item item to be checked
   * @return item's quantity
   * @require inventory.contains(item) == true
   */
  public int getItemQuantity (Entity item) {
    return itemQuantity[inventory.indexOf(item)];
  }

  /**
   * Returns the item's quantity
   * @param index item's index stored in inventory
   * @return item's quantity
   * @require inventory.indexOf(index) != -1
   */
  public int getItemQuantity (int index) {
    return itemQuantity[index];
  }

  /**
   * Assuming weapon's max quantity is one
   * NOT FINISHED!!!!!
   */
  public void equipItem(Entity item) {
    if ((item.checkEntityType(EntityTypes.MELEE)
            || item.checkEntityType(EntityTypes.RANGED))
            && inventory.contains(item)) {
      //Equipment
      equippables[0] = item;
      removeItem(item);
    }
  }

  class quickBar {

    /**
     * The initial size of quick bar.
     */
      private final int quickBarSize = 3;

    /**
     * Temporary storage for players to store their potions.
     */
      private List<Entity> quickBarItems = new ArrayList<>(quickBarSize);

    /**
     * Items' quantity, the indices of quick bar are corresponded to itemQuantity's indices
     */
      private int[] itemQuantity = new int[quickBarSize];

      /**
       * Adding potion to the quickbar.
       * Is there a limit of potion quantities? To be discussed with team
       */
      public void addQuickBarItems(Entity potion) {

        //Max quantity undefined. TO BE IMPLEMENTED
        if (quickBarItems.contains(potion)) {
          ++itemQuantity[quickBarItems.indexOf(potion)];
        } else if (quickBarItems.size() == quickBarSize) {
          //Error code here
          System.out.println("Quickbar is full.");
        } else {
          quickBarItems.add(potion);
          ++itemQuantity[quickBarItems.indexOf(potion)];
        }
      }

      /**
       * Removes the potion from the quickbar based on the input index
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

}
