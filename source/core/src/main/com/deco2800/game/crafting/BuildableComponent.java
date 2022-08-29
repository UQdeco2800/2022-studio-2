package com.deco2800.game.crafting;

import com.deco2800.game.components.Component;

import java.util.HashMap;
import java.util.Map;

public class BuildableComponent extends Component {

    /**
     Hashmap that contains the crafting recipe of the item. The key will contain the enum class material and
     the values will contain teh amount of the material required.
     */
    Map<Materials,Integer> craftingRecipe= new HashMap<>();

    /**
     * The method that will set the required recipe to craft the item. Enum class materials were chosen as the key
     * as they would not be duplicated. The keys will store an integer stating the amount of the material required for
     * crafting.
     */

    public void setCraftingRecipe(Map<Materials,Integer> recipe){
        this.craftingRecipe = recipe;
    }


    /**
     * Default setter method that returns the crafting recipe for the class.
     * @return a map containing the crafting recipe
     */
    public Map<Materials,Integer> getRequiredMaterials(){
        return craftingRecipe;
    }


}
