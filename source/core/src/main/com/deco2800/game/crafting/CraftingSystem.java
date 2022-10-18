package com.deco2800.game.crafting;

import com.deco2800.game.entities.configs.combatitemsconfig.WeaponConfigSetup;
import com.deco2800.game.files.FileLoader;

import java.util.*;

/**
 * Public class that creates a new crafting system which allows users to combine materials in their inventory to make
 * new items. Implements runnable in order to create multiple threads for concurrent processing.
 */
public class CraftingSystem implements Runnable{

    public static final WeaponConfigSetup configs =
            FileLoader.readClass(WeaponConfigSetup.class, "configs/Weapons.json");

    /**
     * List of all the items the user has built
     */
    private List<String> builtItems;

    /**
     *List containing the users' inventory
     */
    private static List<Materials> inventoryContents = new ArrayList<>();

    /**
     * Constructor that creates an instance of the crafting system class which creates a set of the users inventory and
     * determines what items the users can build with it. Class then calls an instance of the display to be made and
     * creates a daemon that checks the users inventory.
     */
    public CraftingSystem() {

        builtItems = new ArrayList<>();

        inventoryContents.add(Materials.Wood); inventoryContents.add(Materials.Steel);

        CraftingLogic.setPossibleBuilds(CraftingLogic.canBuild(inventoryContents));

        Thread background = new Thread(this);
        background.setDaemon(true);
    }

    /**
     * Checks if an item can be build, then adds it to the list of built items if possible.
     * @param item
     */
    public void buildItem(Object item){
        if (CraftingLogic.getPossibleBuilds().contains(item)){
            builtItems.add("Sword");
            inventoryContents.remove(Materials.Steel);
            inventoryContents.remove(Materials.Wood);

            CraftingLogic.setPossibleBuilds(CraftingLogic.canBuild(inventoryContents)); // For sprint one only a sword can be built.
        }
    }

    /**
     * Returns the instance of inventory contents made and used by the class. Synchronised to prevent thread write
     * conflicts.
     */
    public synchronized List<Materials> getInventoryContents(){
        inventoryContents = new ArrayList<>(Arrays.asList(Materials.values()));
        return inventoryContents;
    }


    /**
     * Sets the users inventory contents to be used by the crafting classes. Synchronised to prevent write conflicts.
     * @param materials
     */
    public synchronized void setInventoryContents(List<Materials> materials){
        inventoryContents = materials;
    }

    /**
     * Run method that creates a daemon to update the users possible builds.
     */
    @Override
    public void run() {
            CraftingLogic.setPossibleBuilds(CraftingLogic.canBuild(getInventoryContents()));
    }
}
