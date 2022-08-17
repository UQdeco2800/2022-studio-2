package com.deco2800.game.crafting;

import java.util.ArrayList;
import java.util.List;

public class CraftingSystem {
    private List<String> builtItems;
    private List<String> possibleBuilds;

    public void CraftingSystem(){
         builtItems = new ArrayList<String>();

         //List<String> inventoryContents = getInventoryContents(Inventory inventory);
        List<Materials> inventoryContents = new ArrayList<Materials>(); inventoryContents.add(Materials.Wood); inventoryContents.add(Materials.Steel); inventoryContents.add(Materials.Steel);
        possibleBuilds = CraftLogic.canBuild(inventoryContents);

        /**
         * The Display Calling Goes Here
         **/

    }

    /* public List<String> getInventoryContents(Inventory inventory){} */

    public void buildItem(String Item){
        if (Item.equals("Sword") && possibleBuilds.contains("Sword")){
            builtItems.add("Sword");
        }
    }
}
