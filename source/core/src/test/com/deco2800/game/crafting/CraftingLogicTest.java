package com.deco2800.game.crafting;

import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.crafting.Materials;
import com.deco2800.game.crafting.CraftingLogic;

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
        CraftingLogic testCraftingLogic = new CraftingLogic();
        List<Object> expectedList = new ArrayList<>(16);
        assertEquals(testCraftingLogic.getPossibleBuilds(), expectedList);
    }

    @Test
    void setPossibleBuildsTest() {
        CraftingLogic testCraftingLogic = new CraftingLogic();
        List<Object> expectedList = new ArrayList<>(16);
        testCraftingLogic.setPossibleBuilds(expectedList);
        assertEquals(testCraftingLogic.getPossibleBuilds(), expectedList);
    }

    @Test
    void getPossibleWeaponsTest() {
        CraftingLogic testCraftingLogic = new CraftingLogic();
        List<Object> expectedList = new ArrayList<>(16);
        assertEquals(testCraftingLogic.getPossibleWeapons(), expectedList);
    }

    @Test
    void setPossibleWeaponsTest() {
        CraftingLogic testCraftingLogic = new CraftingLogic();
        Set<Object> expectedList = new HashSet<>(16);
        testCraftingLogic.setPossibleWeapons(expectedList);
        assertEquals(testCraftingLogic.getPossibleWeapons(), expectedList);
    }

    @Test
    void canBuildTest() {
        CraftingLogic testCraftingLogic = new CraftingLogic();
        List<Object> expectedList = new ArrayList<>();
        List<Materials> materials = new ArrayList<>();
        materials.add(Materials.Wood);
        assertEquals(testCraftingLogic.canBuild(materials), expectedList);
    }

}
