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
     * List of all weapons that exist in the game
     */
    private static Set<Object> possibleWeapons =  new HashSet<Object>();

    /**
     * Returns the list of all possible builds the user can make with their given inventory.
     * @return list
     */
    public static List<Object> getPossibleBuilds(){
            return new ArrayList<Object>(possibleBuilds);
        }

    /**
     * Sets a list of all the possible builds the user can make with the items contained in their inventory
     * @param weapons, a list of the items they can craft
     */
    public static void setPossibleBuilds(List<Object> weapons){
            possibleBuilds = weapons;
    }

    /**
     * Returns the list of all possible builds the user can make with their given inventory.
     * @return list
     */
    public static Set<Object> getPossibleWeapons(){
        return new HashSet<Object>(possibleWeapons);
    }

    /**
     * Sets a list of all the possible builds the user can make with the items contained in their inventory
     * @param weapons, a list of the items they can craft
     */
    public static void setPossibleWeapons(Set<Object> weapons){
        possibleWeapons = weapons;
    }

    /**
     * Method that determines what items can be built based on items present in users' inventory. Checks if
     * object first implements buildable interface and then checks if user has required materials to build it.
     * For first sprint user will always have enough materials in their inventory.
     *
     * @param inventoryContents, the contents of the users' inventory
     * @return List of the buildable items
     */
    public static boolean canBuild(List<Materials> inventoryContents){
        List<Object >buildables = new ArrayList<Object>();
        List<Object> weapons = new ArrayList<>();

        weapons.addAll(possibleWeapons);

        for (int i = 0 ; i < weapons.size(); i++){

            if (/*weapons.get(i) instanceof Buildable*/ true){
                Map<Materials,Integer> materialValues =
                /*((Buildable) weapons.get(i)).getRequiredMaterials();*/
                new HashMap<>();

                for (Map.Entry<Materials, Integer> entry : materialValues.entrySet()){

                    if (!inventoryContents.contains(entry.getKey())){
                    }

                    else if (Collections.frequency(inventoryContents, entry.getKey()) < entry.getValue()){
                    }

                    else {
                        buildables.add(weapons.get(i));
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
