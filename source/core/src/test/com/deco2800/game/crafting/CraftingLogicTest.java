package com.deco2800.game.crafting;

//import com.deco2800.game.CombatItems.Weapon;
import com.deco2800.game.entities.configs.CombatItemsConfig.MeleeConfig;
import com.deco2800.game.entities.configs.CombatItemsConfig.WeaponConfig;
import com.deco2800.game.extensions.GameExtension;

import com.deco2800.game.files.FileLoader;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
public class CraftingLogicTest {

     List<Materials> inventory;


    @Before
    public void setup() {
    }
    @Test
    void basicGetPossibleBuildsTest() {
        List<MeleeConfig> imaginaryWeapons = new ArrayList<>();
        CraftingLogic.setPossibleBuilds(imaginaryWeapons);
        assertEquals(imaginaryWeapons, CraftingLogic.getPossibleBuilds());
    }

    @Test
    void basicSetPossibleBuildsTest() {
        List<MeleeConfig> inputList = new ArrayList<>();
        CraftingLogic.setPossibleBuilds(inputList);
        assertEquals(inputList, CraftingLogic.getPossibleBuilds());
    }

    @Test
    void getPossibleWeaponsTest() {
        inventory = new ArrayList<>();
        inventory.add(Materials.Gold);
        inventory.add(Materials.Iron);
        ArrayList<MeleeConfig> inputList = new ArrayList<>();
        WeaponConfig configs = FileLoader.readClass(WeaponConfig.class, "configs/Weapons.json");
        assertEquals(CraftingLogic.canBuild(inventory).get(0).damage, configs.SwordLvl2.damage);
    }

}
