package com.deco2800.game.crafting;

import java.util.*;

/**
 * Supporting class containing static methods that can be used to support other classes in using the crafting system.
 * Contains information about possible builds and contains algorithms that determine what items can and can't be
 * build based on the users inventory contents
 */
public  class CraftingLogic {
    /**
     * List containing the possible builds the user can make with their given inventory
     */
    private static List<Object> possibleBuilds =  new ArrayList<Object>();

    /**
     * Returns the list of all possible builds the user can make with their given inventory.
     * @return list
     */
    public static List<Object> getPossibleBuilds(){
            return new ArrayList<Object>(getPossibleBuilds());
        }

    /**
     * Sets a list of all the possible builds the user can make with the items contained in their inventory
     * @param weapons, a list of the items they can craft
     */
    public static void setPossibleBuilds(List<Object> weapons){
            possibleBuilds = weapons;
    }

    /**
     * Method that determines what items can be built based on items present in users' inventory. Checks if
     * object first implements buildable interface and then checks if user has required materials to build it.
     * For first sprint user will always have enough materials in their inventory.
     * @param inventoryContents, the contents of the users' inventory
     * @return List of the buildable items
     */
    public static List<Object> canBuild(List<Materials> inventoryContents){
        List<Object >buildables = new ArrayList<Object>();
        List<Object >possibleBuilds = getPossibleBuilds();

        for (int i = 0 ; i < getPossibleBuilds().size(); i++){
            boolean craftable = true;
            if (possibleBuilds.get(i) instanceof Buildable){
                for (int x = 0; x < ((Buildable) possibleBuilds.get(i)).getRequiredMaterials().size(); x++){
                    if (!inventoryContents.contains(((Buildable)
                            possibleBuilds.get(i)).getRequiredMaterials().get(x))){
                        craftable = false;
                    }
                    if (craftable == true){
                        possibleBuilds.add(possibleBuilds.get(i));
                    }
                }
            }
        }
        /*
        Conditional statement for first sprint  as classes for weapons have not yet been implemented/
         */
        if (inventoryContents.contains(Materials.Wood) && inventoryContents.contains(Materials.Steel)){
            buildables.add("Sword");
        }
        return buildables;
    }


}
