package com.deco2800.game.crafting;

import com.deco2800.game.entities.configs.CombatItemsConfig.MeleeConfig;

import java.util.*;

/**
 * Supporting class containing static methods that can be used to support other classes in using the crafting system.
 * Contains information about possible builds and contains algorithms that determine what items can and can't be
 * build based on the users inventory contents
 */
public class CraftingLogic {

    /**
     * List containing the possible builds the user can make with their given inventory
     */
    private static List<MeleeConfig> possibleBuilds =  new ArrayList<MeleeConfig>();

    /**
     * List of all weapons that exist in the game
     */
    private static ArrayList<MeleeConfig> possibleWeapons =  new ArrayList<>();

    /**
     * Returns the list of all possible builds the user can make with their given inventory.
     * @return list
     */
    public static List<MeleeConfig> getPossibleBuilds(){
            return new ArrayList<>(possibleBuilds);
        }

    /**
     * Sets a list of all the possible builds the user can make with the items contained in their inventory
     * @param weapons, a list of the items they can craft
     */
    public static void setPossibleBuilds(List<MeleeConfig> weapons){
            possibleBuilds = weapons;
    }

    /**
     * Returns the list of all possible builds the user can make with their given inventory.
     * @return list
     */
    public static List<MeleeConfig> getPossibleWeapons(){
        return new ArrayList<>(possibleWeapons);
    }

    /**
     * Sets a list of all the possible builds the user can make with the items contained in their inventory
     * @param weapons, a list of the items they can craft
     */
    public static void setPossibleWeapons(ArrayList<MeleeConfig> weapons){
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
    public static List<MeleeConfig> canBuild(List<Materials> inventoryContents){
        List<MeleeConfig> buildables = new ArrayList<>();

        for (int i = 0 ; i < possibleWeapons.size(); i++){

                Map<Materials,Integer> materialValues = possibleWeapons.get(i).materials;

                for (Map.Entry<Materials,Integer> entry : materialValues.entrySet()) {

                    if (!inventoryContents.contains(entry.getKey())){
                    }

                    else if (Collections.frequency(inventoryContents, entry.getKey()) < entry.getValue()){
                    }

                    else {
                        buildables.add(possibleWeapons.get(i));
                    }
                }
        }
        return buildables;
    }

}
