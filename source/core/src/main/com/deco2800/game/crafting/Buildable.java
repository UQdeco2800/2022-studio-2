package com.deco2800.game.crafting;

import java.util.ArrayList;
import java.util.List;

public interface Buildable {

    List<Materials> requiredMaterials= new ArrayList <Materials>();

    public void setCraftingRecipe();

    default List<Materials> getRequiredMaterials(){
        return requiredMaterials;
    }

}
