package com.deco2800.game.crafting;

import com.deco2800.game.crafting.craftingDisplay.CraftingDisplay;

import java.util.ArrayList;
import java.util.List;

public class CraftingSystem implements Runnable{
    private List<String> builtItems;
    private  List<Materials>  inventoryContents;

    public void CraftingSystem() {

         builtItems = new ArrayList<String>();
         //Set Possible Builds by finding all weapons that implement Buildable
        List<Object>weapons = new ArrayList<Object>();
        weapons.add("Sword");
        CraftLogic.setPossibleBuilds(weapons);

         //List<Materials> inventoryContents = getInventoryContents(Inventory inventory);
        inventoryContents = new ArrayList<Materials>(); inventoryContents.add(Materials.Wood); inventoryContents.add(Materials.Steel); inventoryContents.add(Materials.Steel);

        CraftingDisplay UI = new CraftingDisplay();

        Thread background = new Thread(this);
        Thread display = new Thread(UI);

        background.setDaemon(true);
        background.start();
        display.start();

    }

    public void buildItem(Object Item){
        if ((Item instanceof Buildable) && CraftLogic.getPossibleBuilds().contains(Item)){
            builtItems.add("Sword");
        }
    }

    public synchronized List<Materials> getInventoryContents(){
        return inventoryContents;
    }

    public synchronized void setInventoryContents(List<Materials> materials){
        inventoryContents = materials;
    }

    @Override
    public void run() {
        while (true) {
            CraftLogic.setPossibleBuilds(CraftLogic.canBuild(getInventoryContents()));
        }
    }
}
