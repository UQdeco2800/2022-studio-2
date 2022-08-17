package com.deco2800.game.crafting;

import java.util.ArrayList;
import java.util.List;

public class CraftingSystem {
    private List<String> builtItems;

    public void CraftingSystem(){
         builtItems = new ArrayList<String>();
         //Set Possible Builds by finding all weapons that implement Buildable\
        List<Object> weapons = new ArrayList<Object>();
        weapons.add("Sword");
        CraftLogic.setPossibleBuilds(weapons);

         //List<Materials> inventoryContents = getInventoryContents(Inventory inventory);
        List<Materials> inventoryContents = new ArrayList<Materials>(); inventoryContents.add(Materials.Wood); inventoryContents.add(Materials.Steel); inventoryContents.add(Materials.Steel);
        List<Object> possibleBuilds = CraftLogic.canBuild(inventoryContents);

        /**
         * The Display Calling Goes Here
         **/

    }

    /*
    public List<Materials> getInventoryContents(Inventory inventory){


    }
    */

    public void buildItem(Object Item){
        if ((Item instanceof Buildable) && CraftLogic.getPossibleBuilds().contains(Item)){
            builtItems.add("Sword");
        }
    }


}
