package com.deco2800.game.crafting;

import com.deco2800.game.crafting.craftingDisplay.CraftingDisplay;

import java.util.ArrayList;
import java.util.List;

public class CraftingSystem implements Runnable{
    private List<String> builtItems;
    private List<Object> possibleBuilds;
    private List<Materials> inventoryContents;

    public void CraftingSystem(){
         builtItems = new ArrayList<String>();
         //Set Possible Builds by finding all weapons that implement Buildable\
        List<Object> weapons = new ArrayList<Object>();
        weapons.add("Sword");
        CraftLogic.setPossibleBuilds(weapons);

         //List<Materials> inventoryContents = getInventoryContents(Inventory inventory);
        inventoryContents = new ArrayList<Materials>(); inventoryContents.add(Materials.Wood); inventoryContents.add(Materials.Steel); inventoryContents.add(Materials.Steel);

        CraftingDisplay UI = new CraftingDisplay();

        Thread background = new Thread(this);
        Thread display = new Thread(UI);

        background.start();
        display.start();
    }

    /*
    public List<Materials> getInventoryContents(Inventory inventory){


    }
    */

    public void buildItem(Object Item){
        if ((Item instanceof Buildable) && this.possibleBuilds.contains(Item)){
            builtItems.add("Sword");
        }
    }


    @Override
    public void run() {
        possibleBuilds = CraftLogic.canBuild(inventoryContents);
    }
}
