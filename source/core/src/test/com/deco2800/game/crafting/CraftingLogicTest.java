package com.deco2800.game.crafting;

import com.deco2800.game.extensions.GameExtension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
public class CraftingLogicTest {

    @Test
    void getPossibleBuildsTest() {
        assertEquals(CraftingLogic.getPossibleBuilds().get(0), "Sword");
    }

    @Test
    void setPossibleBuildsTest() {
        List<Object> inputList = new ArrayList<>();
        CraftingLogic.setPossibleBuilds(inputList);
        assertEquals(inputList, CraftingLogic.getPossibleBuilds());
    }

    @Test
    void getPossibleWeaponsTest() {
        Set<Object> inputList = new HashSet<>();
        inputList.add("Sword");
        CraftingLogic.setPossibleWeapons(inputList);
        assertEquals(CraftingLogic.getPossibleWeapons(), inputList);
    }

    @Test
    void canBuildTest() {
        List<Materials> inputInventory = new ArrayList<>();
        inputInventory.add(Materials.Wood);
        inputInventory.add(Materials.Steel);
        assertEquals(CraftingLogic.canBuild(inputInventory).get(0), "Sword");
    }
}
