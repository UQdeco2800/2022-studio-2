package com.deco2800.game.crafting;

import com.deco2800.game.crafting.craftingDisplay.CraftingDisplay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Public class that creates a new crafting system which allows users to combine materials in their inventory to make
 * new items. Implements runnable in order to create multiple threads for concurrent processing.
 */
public class CraftingSystem implements Runnable{

    /**
     * List of all the items the user has built
     */
    private List<String> builtItems;

    /**
     *List containing the users' inventory
     */
    private  List<Materials>  inventoryContents;

    /**
     * Constructor that creates an instance of the crafting system class which creates a set of the users inventory and
     * determines what items the users can build with it. Class then calls an instance of the display to be made and
     * creates a daemon that checks the users inventory.
     */
    public void CraftingSystem() {

         builtItems = new ArrayList<String>();

         //Set Possible Builds by finding all weapons that implement Buildable component
        HashSet<Object> possibleWeapons = new HashSet<Object>();
        possibleWeapons.add("Sword");
        CraftingLogic.setPossibleWeapons(possibleWeapons);

         //List<Materials> inventoryContents = getInventoryContents();
        inventoryContents = new ArrayList<>(); inventoryContents.add(Materials.Wood); inventoryContents.add(Materials.Steel); inventoryContents.add(Materials.Steel);

        CraftingLogic.setPossibleBuilds(CraftingLogic.canBuild(inventoryContents));

        Thread background = new Thread(this);
        background.setDaemon(true);
    }

    /**
     * Checks if an item can be build and adds it to the list of built items if possible.
     * @param Item
     */
    public void buildItem(Object Item){
        if (CraftingLogic.getPossibleBuilds().contains(Item)){
            builtItems.add("Sword");
            inventoryContents.remove(Materials.Steel);
            inventoryContents.remove(Materials.Steel);
            inventoryContents.remove(Materials.Wood);

            CraftingLogic.setPossibleBuilds(CraftingLogic.canBuild(inventoryContents));

        }
    }

    /**
     * Returns the instance of inventory contents made and used by the class. Synchronised to prevent thread write
     * conflicts.
     */
    public synchronized List<Materials> getInventoryContents(){
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