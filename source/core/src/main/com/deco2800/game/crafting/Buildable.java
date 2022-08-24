package com.deco2800.game.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Buildable {

    Map<Materials,Integer> craftingRecipe = new HashMap<Materials,Integer>() {
    };

    public void setCraftingRecipe();

    default Map<Materials,Integer> getRequiredMaterials(){
        return craftingRecipe;
    }

}
