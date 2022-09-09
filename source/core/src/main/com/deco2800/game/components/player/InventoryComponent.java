package com.deco2800.game.components.player;


import com.deco2800.game.components.CombatItemsComponents.MeleeStatsComponent;
import com.deco2800.game.components.CombatItemsComponents.WeaponStatsComponent;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.entities.factories.EntityTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Arrays;
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
   * The status of inventory display.
   */
  private boolean inventoryIsOpened = false;

  /**
   * Initial inventory size
   */
  private final int inventorySize = 16;

  /**
   * The initial size of quick bar.
   */
  private final int quickBarSize = 3;

  /**
   * Initial item equipment slot
   */
  private final int equipSlots = 2;

  /**
   * An inventory unit for players to inspect and store their items.
   */
  private List<Entity> inventory = new ArrayList<>(inventorySize);

  /**
   * Temporary storage for players to store their potions.
   */
  private List<Entity> quickBarItems = new ArrayList<>(quickBarSize);

  /**
   * Slot 1(index 0) is set to be weapon and slot2(index 2) for armour.
   */
  private Entity[] equipables = new Entity[equipSlots];

  /**
   * Items' quantity, the indices of inventory are corresponded to itemQuantity's indices.
   */
  private int[] itemQuantity = new int[inventorySize];

  /**
   * Items' quantity, the indices of quick bar are corresponded to itemQuantity's indices
   */
  private int[] quickBarQuantity = new int[quickBarSize];

  /**
   * Returns the current inventory
   *
   * @return inventory items
   */
  public List<Entity> getInventory() {
    return List.copyOf(inventory);
  }

  /**
   * Adds an item to player's inventory.
   *
   * @param item item to add
   */
  public void addItem(Entity item) {
    if (inventory.size() == inventorySize) {
      logger.info("Inventory if full");
    } else if (!inventory.contains(item)) {
      inventory.add(item);
    } else if ((item.checkEntityType(EntityTypes.MELEE)
            || item.checkEntityType(EntityTypes.RANGED))
            && !inventory.contains(item)) {
      inventory.add(item);
    }
    //Do nothing if the weapon is already in the inventory.
    //Consider adding a console message to player.

    //Item quantity undefined. TO BE IMPLEMENTED
    ++itemQuantity[inventory.indexOf(item)];
  }

  /**
   * Adds item to player's inventory with the specified quantity.
   *
   * @param item     item to add
   * @param quantity item's quantity
   */
  public void addItem(Entity item, int quantity) {
    if (inventory.size() == inventorySize) {
      logger.info("Inventory if full");
      //Error should end this block of code
    } else if (!inventory.contains(item)) {
      inventory.add(item);
      itemQuantity[inventory.indexOf(item)] = quantity;
    }
  }

  /**
   * Removes an item to player's inventory.
   *
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
   *
   * @param index item's index stored in inventory
   * @requires inventory.indexOf(index) != -1 and getItemQuantity(index) >= 1
   */
  public void removeItem(int index) {
    --itemQuantity[index];
    if (getItemQuantity(index) == 0) {
      inventory.remove(index);
    }
  }

  /**
   * Removes an item to player's inventory.
   *
   * @param type type of the item that is to be removed
   *             NOTE: Currently only work with crafting materials EntityTypes
   */
  public void removeItem(EntityTypes type) {
    for (int i = 0; i < inventory.size(); ++i) {
      if (inventory.get(i).checkEntityType(type)) {
        removeItem(i);
      }
    }
  }

  /**
   * Returns the item's quantity
   *
   * @param item item to be checked
   * @return item's quantity
   * @requires inventory.contains(item) == true
   */
  public int getItemQuantity(Entity item) {
    return itemQuantity[inventory.indexOf(item)];
  }

  /**
   * Returns the item's quantity
   *
   * @param index item's index stored in inventory
   * @return item's quantity
   * @requires inventory.indexOf(index) != -1
   */
  public int getItemQuantity(int index) {
    return itemQuantity[index];
  }

  /**
   * Modify the player's stat according to the weapon stat.
   *
   * @param weapon the weapon that is going to be equipped on
   */
  private void applyWeaponEffect(Entity weapon) {
    WeaponStatsComponent weaponStats = weapon.getComponent(WeaponStatsComponent.class);
    if (weaponStats instanceof MeleeStatsComponent) {
      MeleeStatsComponent meleeStats = (MeleeStatsComponent) weaponStats;
      PlayerModifier pmComponent = ServiceLocator.getGameArea().getPlayer()
              .getComponent(PlayerModifier.class);

      //dk if requires dmg stat or not think about it
      pmComponent.createModifier(PlayerModifier.MOVESPEED, (float) (1 / meleeStats.getWeight()), true, 0);
      //for duration
    }
  }

  /**
   * Waiting for stat modification implementation of armour
   *
   * @param armour
   */
  private void applyArmourEffect(Entity armour) {
  }

  /**
   * Assuming weapon's max quantity is one.
   * NOT FINISHED!!!!!
   * NOTE: This should check if the player has equipped a weapon or amour.
   */
  public void equipItem(Entity item) {
    if (inventory.contains(item)) {
      if (item.checkEntityType(EntityTypes.WEAPON) && equipables[0] == null) {
        equipables[0] = item;
        //Slot 1 - Reserved for combat items
        applyWeaponEffect(item);
      }
    } else if (item.checkEntityType(EntityTypes.ARMOUR) && equipables[1] == null) {
      equipables[1] = item;
      //Slot 2 - Reserved for armour
      //Equipment
    }
    removeItem(item);
  }


  /**
   * Unequips the item in the given item slot.
   * Does nothing if the inventory is full.
   *
   * @param itemSlot the index of the item slot
   * @requires itemSlot >= 0 and itemSlot <= 1
   * NOT FINISHED!!!!!
   */
  public void unequipItem(int itemSlot) {
    if (inventory.size() == inventorySize) {
      logger.info("Inventory if full, cannot unequip");
    } else {
      inventory.add(equipables[itemSlot]);
      equipables[itemSlot] = null;
      //Modify player stat
    }
  }

  /**
   * Displays the inventory menu if it is not opened. Closes it otherwise.
   *
   * @requires the player is created and has an InventoryComponent.
   */
  public void toggleInventoryDisplay() {
    if (!inventoryIsOpened) {
      ServiceLocator.getInventoryArea().displayInventoryMenu();
      ServiceLocator.getInventoryArea().showItem();
      EntityService.pauseAndResume();
    } else {
      ServiceLocator.getInventoryArea().disposeInventoryMenu();
    }
    EntityService.pauseAndResume();
    inventoryIsOpened = !inventoryIsOpened;
  }

  /**
   * Returns the current quick bar items
   *
   * @return quick bar items
   */
  public List<Entity> getQuickBarItems() {
    return List.copyOf(quickBarItems);
  }


  /**
   * Adding potion to the quickbar.
   * Is there a limit of potion quantities? To be discussed with team
   */
  public void addQuickBarItems(Entity potion) {
    int itemIndex = quickBarItems.indexOf(potion);
    //Max quantity undefined. TO BE IMPLEMENTED
    if (quickBarItems.contains(potion) && quickBarQuantity[itemIndex] < 9) {
      ++quickBarQuantity[itemIndex];
    } else if (quickBarItems.size() == quickBarSize) {
      logger.info("Inventory if full");
    } else {
      quickBarItems.add(potion);
      ++quickBarQuantity[itemIndex];
    }
  }

  /**
   * Removes the potion from the quickbar based on the input index
   *
   * @param inputIndex the index that is returned from user actions(TO BE IMPLEMENTED)
   */
  public void removePotion(int inputIndex) {
    quickBarItems.remove(inputIndex);
    quickBarQuantity[inputIndex] = 0;
  }

  /**
   * Consume the potion rom quickbar based on the input index.
   *
   * @param inputIndex the index that is returned from user actions(TO BE IMPLEMENTED)
   * NOTE: I have changed the accessor of applyEffect in PotionEffectComponent to make this compile.
   *                   ****To be implemented by potion team.****
   */
  public void consumePotion(int inputIndex) {
    //Does nothing if there is no potion on the selected slot or the quantity < 1
    if (quickBarItems.get(inputIndex) != null) {
      quickBarItems.get(inputIndex).getComponent(PotionEffectComponent.class).applyEffect(entity);
      if (quickBarQuantity[inputIndex] == 1) {
        removePotion(inputIndex);
      } else if (quickBarQuantity[inputIndex] > 1) {
        --quickBarQuantity[quickBarItems.indexOf(inputIndex)];
      }
    }
  }

}
