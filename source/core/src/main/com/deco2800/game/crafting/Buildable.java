package com.deco2800.game.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Public Interface that indicates what items are buildable and not in a game. To make an item buildable, implement the
 * interface and override its base method.
 */
public interface Buildable {
    /**
    Hashmap that contains the crafting recipe of the item. The key will contain the enum class material and
     the values will contain teh amount of the material required.
     */
    Map<Materials,Integer> craftingRecipe= new HashMap<Materials,Integer>() {
    };

    /**
     * The method that will set the required recipe to craft the item. Enum class materials were chosen as the key
     * as they would not be duplicated. The keys will store an integer stating the amount of the material required for
     * crafting.
     */
    public void setCraftingRecipe();


    /**
     * Default setter method that returns the crafting recipe for the class.
     * @return a map containing the crafting recipe
     */
    default Map<Materials,Integer> getRequiredMaterials(){
        return craftingRecipe;
    }

}
