package com.deco2800.game.components.player;


import com.deco2800.game.components.DefensiveItemsComponents.ArmourStatsComponent;
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
    private static final int equipSlots = 2;

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
     * Checks if there is an item with the same type in the storage
     *
     * @param item the Entity to be checked
     * @param storage the List of storage(e.g. can be quick bar, inventory)
     * @return true if there is a same kind of Entity, false otherwise
     */
    public boolean hasItem(Entity item, List<Entity> storage) {
        for (Entity other: storage) {
            if (itemequals(item, other)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the index in the storage if there is one with the same Entity type
     *
     * @param item item to be found
     * @param storage the List of storage(e.g. can be quick bar, inventory)
     * @return index of the item, or -1 if item is not in the storage
     */
    public int getItemIndex(Entity item, List<Entity> storage) {
        int index = -1;
        for (int i = 0; i < storage.size(); ++i) {
            if (itemequals(item, storage.get(i))) index = i;
        }
        return index;
    }

    /**
     * Adds an item to player's inventory.
     *
     * @param item item to add
     */
    public void addItem(Entity item) {
        if (inventory.size() == inventorySize) {
            logger.info("Inventory if full");
        } else if (!hasItem(item, inventory)) {
            if (item.checkEntityType(EntityTypes.POTION)
                    || item.checkEntityType(EntityTypes.CRAFTABLE)) {
                inventory.add(item);
                logger.info(String.format("Added %s", item.getEntityTypes().toString()));
            } else if (item.checkEntityType(EntityTypes.WEAPON)
                    || item.checkEntityType(EntityTypes.ARMOUR)) {
                inventory.add(item);
                ++itemQuantity[inventory.indexOf(item)];
            }
        }
        //Stacking Potions and Craftables NOT FINISHED
        if (getItemIndex(item, inventory) != -1
                && (item.checkEntityType(EntityTypes.POTION)
                || item.checkEntityType(EntityTypes.CRAFTABLE))) {
            ++itemQuantity[getItemIndex(item, inventory)];
        }
    }

    /**
     * Adds item to player's inventory with the specified quantity. Use this for testing purposes
     *
     * @param item     item to add
     * @param quantity item's quantity
     */
    public void addItem (Entity item, int quantity) {
        if (inventory.size() == inventorySize) {
            logger.info("Inventory if full");
            //Error should end this block of code
        } else if (!inventory.contains(item)) {
            inventory.add(item);
            itemQuantity[inventory.indexOf(item)] = quantity;
        }
    }

    /**
     * Sort the item quantity array once an item is removed from the inventory.
     *      *
     * @param index    index of the item
     * @param list     the list of the inventory storage
     * @param quantity the quantity array of corresponding inventory
     */
    public void sortInventory(int index, List list, int[] quantity) {
        if (list.size() > index) {
            for (int i = index; i < list.size(); ) {
                quantity[i] = quantity[++i];
            }
        }
    }

    /**
     * Removes an item to player's inventory.
     *
     * @param item item to remove
     * @requires getItemQuantity(item) >= 1
     */
    public boolean removeItem(Entity item) {
        boolean removed = false;
        int index = inventory.indexOf(item);
        --itemQuantity[index];
        if (getItemQuantity(item) == 0) {
            sortInventory(index, inventory, itemQuantity);
            inventory.remove(item);
            removed = true;
        }
        return removed;
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
            sortInventory(index, inventory, itemQuantity);
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
                break;
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
     * add
     * Modify the player's stat according to the weapon stat.
     * Credit to Team 4
     *
     * @param weapon the weapon that is going to be equipped on
     * @param equip  boolean to determine equip or unequip item
     */
    private void applyWeaponEffect(Entity weapon, boolean equip) {
        WeaponStatsComponent weaponStats;
        PlayerModifier pmComponent = entity.getComponent(PlayerModifier.class);
        if ((weaponStats = weapon.getComponent(MeleeStatsComponent.class)) != null) {
            MeleeStatsComponent meleeStats = (MeleeStatsComponent) weaponStats;
            if (equip) {
                //Equip weapon
                pmComponent.createModifier(PlayerModifier.MOVESPEED, (float) (meleeStats.getWeight() / 15), true, 0);
            } else {
                //Unequip
                pmComponent.createModifier(PlayerModifier.MOVESPEED, (float) (meleeStats.getWeight() * 15), true, 0);
            }
        }
    }

    /**
     * Modify the player's stat according to the armour stat.
     *
     * @param armour the armour that is equipped
     * @param equip  boolean to determine equip or unequip item
     */
    private void applyArmourEffect(Entity armour, boolean equip) {
        ArmourStatsComponent armourStats;
        PlayerModifier pmComponent = entity.getComponent(PlayerModifier.class);
        //Applying the weight of the armour to player
        if ((armourStats = armour.getComponent(ArmourStatsComponent.class)) != null) {
            if (equip) {
                pmComponent.createModifier(PlayerModifier.MOVESPEED, (float) armourStats.getWeight(), true
                        , 0);
                pmComponent.createModifier(PlayerModifier.DMGREDUCTION, (float) armourStats.getPhyResistance(), true, 0);
                pmComponent.createModifier(PlayerModifier.STAMINAMAX, (float) armourStats.getVitality(), true, 0);
            } else {
                pmComponent.createModifier(PlayerModifier.MOVESPEED, 15 / (float) armourStats.getWeight(),
                        true, 0);
                pmComponent.createModifier(PlayerModifier.DMGREDUCTION, 1 / (float) armourStats.getPhyResistance(), true, 0);
                pmComponent.createModifier(PlayerModifier.STAMINAMAX, 1 / (float) armourStats.getVitality(), true, 0);
            }
        }
    }

    /**
     * Returns the item at the given index or
     *
     * @param index the index of the item
     * @return entity
     * @requires equipables[index] != null
     */
    public Entity getEquipable(int index) {
        return equipables[index];
    }

    /**
     * Returns the items the player equipped
     *
     * @return the equipable array
     */
    public Entity[] getEquipables() {
        return Arrays.copyOf(equipables, 2);
    }

    /**
     * Remove the item in the given itemSlot.
     *
     * @param itemSlot
     * @return true if the item is correctly removed, false otherwise
     */
    public boolean removeEquipable(int itemSlot) {
        boolean removed = false;
        if (equipables[itemSlot] != null) {
            equipables[itemSlot] = null;
            removed = true;
        }
        return removed;
    }

    /**
     * Equip the item and apply effect of the item to the player.
     *
     * @param item the item to be equipped
     *             NOTE: This should check if the player has equipped a weapon or amour.
     */
    public boolean equipItem(Entity item) {
        boolean equipped = false;
        if (inventory.contains(item)) {
            if (item.checkEntityType(EntityTypes.WEAPON) && equipables[0] == null) {
                equipables[0] = item;
                //Slot 1 - Reserved for combat items
                equipped = true;
                applyWeaponEffect(item, equipped);
            } else if (item.checkEntityType(EntityTypes.ARMOUR) && equipables[1] == null) {
                equipables[1] = item;
                //Slot 2 - Reserved for armour
                equipped = true;
                applyArmourEffect(item, equipped);
            } else {
                logger.info("Slot is occupied");
            }
            if (equipped) removeItem(item);
        }
        return equipped;
    }


    /**
     * Unequips the item in the given item slot.
     * Does nothing if the inventory is full.
     *
     * @param itemSlot the index of the item slot
     * @requires itemSlot >= 0 and itemSlot less than or equal to 1
     * NOT FINISHED!!!!!
     */
    public boolean unequipItem(int itemSlot) {
        boolean unequipped = false;
        if (inventory.size() == inventorySize) {
            logger.info("Inventory if full, cannot unequip");
        } else if (equipables[itemSlot] != null) {
            Entity item = equipables[itemSlot];
            if (item.checkEntityType(EntityTypes.WEAPON)) {
                applyWeaponEffect(item, unequipped);
            } else if (item.checkEntityType(EntityTypes.ARMOUR)) {
                applyArmourEffect(item, unequipped);
            }
            addItem(item);
            equipables[itemSlot] = null;
            unequipped = true;
        }
        return unequipped;
    }

    /**
     * Displays the inventory menu if it is not opened. Closes it otherwise.
     *
     * @requires the player is created and has an InventoryComponent.
     */
    public void toggleInventoryDisplay() {
        if (!inventoryIsOpened) {
            ServiceLocator.getInventoryArea().displayInventoryMenu();
            ServiceLocator.getInventoryArea().displayItems();
            ServiceLocator.getInventoryArea().displayEquipables();
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
     * Check if two items are the same kind
     * @param item the item to be checked
     * @param other the comparison item
     * @return true if two items are the same type, false otherwise
     */
    public boolean itemequals (Entity item, Entity other) {
        boolean equals = false;
        if (item.checkEntityType(EntityTypes.POTION)
        && other.checkEntityType(EntityTypes.POTION)){
            equals = item.getComponent(PotionEffectComponent.class).equals(other);
            logger.info(String.format("%s", equals));
        } else if (item.checkEntityType(EntityTypes.ARMOUR)
        && other.checkEntityType(EntityTypes.ARMOUR)) {
            equals = item.getComponent(ArmourStatsComponent.class)
                    .equals(other.getComponent(ArmourStatsComponent.class));
        } else if (item.checkEntityType(EntityTypes.WEAPON)) {
            //Partially implemented since each weapon will be only spawned once
            equals = item.getId() == other.getId();
        } else if (item.checkEntityType(EntityTypes.CRAFTABLE)
        && other.checkEntityType(EntityTypes.CRAFTABLE)){
            for (EntityTypes type: other.getEntityTypes()) {
                if (type != EntityTypes.CRAFTABLE) {
                    equals = item.checkEntityType(type);
                }
            }
        }
        return equals;
    }

    /**
     * Adding potion to the quickbar.
     * @param potion the potion to be added
     */
    public boolean addQuickBarItems(Entity potion) {
        boolean hasPotion = hasItem(potion, quickBarItems);
        boolean added = false;

        if (hasPotion) {
            logger.info("Added 1 to an existing potion");
            if(quickBarQuantity[getItemIndex(potion, quickBarItems)] < 9) {// Maximum quantity for one potion
                ++quickBarQuantity[getItemIndex(potion, quickBarItems)];
                added = true;
            }
        } else {
            if (quickBarItems.size() == quickBarSize)  // Check if inventory is full
            {
                logger.info("Inventory is full");
            } else {
                logger.info("Added to quick bar");
                quickBarItems.add(potion);
                ++quickBarQuantity[getItemIndex(potion, quickBarItems)];
                added = true;
            }
        }
        return added;
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
     * Consume the potion from quickbar based on the input index.
     *
     * @param inputIndex the index that is returned from user actions(TO BE IMPLEMENTED)
     *                   NOTE: I have changed the accessor of applyEffect in PotionEffectComponent to make this compile.
     *                   ****STILL NEEDS TO BE FIXED.****
     */
    public void consumePotion(int inputIndex) {
        //Does nothing if there is no potion on the selected slot or the quantity < 1
        if (quickBarItems.size() >= inputIndex) {
            quickBarItems.get(--inputIndex).getComponent(PotionEffectComponent.class).applyEffect(entity);
            if (quickBarQuantity[inputIndex] == 1) {
                removePotion(inputIndex);
                sortInventory(inputIndex, quickBarItems, quickBarQuantity);
            } else if (quickBarQuantity[inputIndex] > 1) {
                --quickBarQuantity[inputIndex];
            }
        }
    }
}