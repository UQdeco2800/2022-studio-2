package com.deco2800.game.crafting;

import java.util.*;

public class CraftingLogic {

        private static List<Object> possibleBuilds =  new ArrayList<Object>();


        public static List<Object> getPossibleBuilds(){
            return new ArrayList<Object>(getPossibleBuilds());
        }

        public static void setPossibleBuilds(List<Object> weapons){
            possibleBuilds = weapons;
        }

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

            if (inventoryContents.contains(Materials.Wood) && inventoryContents.contains(Materials.Steel)){
                buildables.add("Sword");
            }
            return buildables;
         }

         private static List<Integer> inventoryCounter(List<Materials> Materials) {
             List<Integer> frequency = new ArrayList<Integer>();

             Set<Materials> distinct = new HashSet<>(Materials);
             for (Materials s : distinct) {
                 frequency.add(Collections.frequency(Materials, s));
             }
             return frequency;
         }



}
