package com.deco2800.game.crafting;

import java.util.ArrayList;
import java.util.List;

public class CraftLogic {

        private static List<String> buildableWeapons = new ArrayList<String>();

        public CraftLogic(){
            buildableWeapons.add("Sword");
        }

        public static List<String> getBuildableWeapons(){
            return new ArrayList<String>(buildableWeapons);
        }


         public static List<String> canBuild(List<Materials> inventoryContents){
            List<String >buildables = new ArrayList<String>();

            if (inventoryContents.contains(Materials.Wood) && inventoryContents.contains(Materials.Steel)){
                buildables.add("Sword");
            }
            return buildables;
         }




}
