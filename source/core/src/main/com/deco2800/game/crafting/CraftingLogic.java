package com.deco2800.game.crafting;

import com.deco2800.game.entities.configs.CombatItemsConfig.MeleeConfig;
import com.deco2800.game.entities.configs.CombatItemsConfig.WeaponConfig;
import com.deco2800.game.files.FileLoader;

import java.util.*;

/**
 * Supporting class containing static methods that can be used to support other classes in using the crafting system.
 * Contains information about possible builds and contains algorithms that determine what items can and can't be
 * build based on the users inventory contents
 */
public class CraftingLogic {

    public static final WeaponConfig configs =
            FileLoader.readClass(WeaponConfig.class, "configs/Weapons.json");
    /**
     * List containing the possible builds the user can make with their given inventory
     */
    private static List<MeleeConfig> possibleBuilds =  new ArrayList<MeleeConfig>();


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
        ArrayList<MeleeConfig> possibleWeapons =  new ArrayList<>();
        possibleWeapons.add(configs.athenaDag);
        possibleWeapons.add(configs.herraDag);
        possibleWeapons.add(configs.SwordLvl2);
        possibleWeapons.add(configs.dumbbell);
        possibleWeapons.add(configs.tridentLvl2);
        possibleWeapons.add(configs.herraAthenaDag);
        possibleWeapons.add(configs.plunger);
        return possibleWeapons;

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
        List<MeleeConfig> possibleWeapons = getPossibleWeapons();
        List<MeleeConfig> buildables = new ArrayList<>();

        for (int i = 0 ; i < possibleWeapons.size(); i++){

                Map<Materials,Integer> materialValues = possibleWeapons.get(i).materials;

                Boolean buildable = true;

                for (Map.Entry<Materials,Integer> entry : materialValues.entrySet()) {

                    if (!inventoryContents.contains(entry.getKey())){
                        buildable = false;
                    }

                }
                if (buildable)
                    buildables.add(possibleWeapons.get(i));
        }
        return buildables;
    }

}
