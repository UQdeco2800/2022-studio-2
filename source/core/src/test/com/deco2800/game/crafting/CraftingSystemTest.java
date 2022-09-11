/**
package com.deco2800.game.crafting;


import com.deco2800.game.extensions.GameExtension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

@ExtendWith(GameExtension.class)
public class CraftingSystemTest {

    @Test
    void constructorTest() {
        CraftingSystem testCraftingSystem = new CraftingSystem();
        assertNotNull(testCraftingSystem);
    }

    @Test
    void buildFaultyItemTest() {
        CraftingSystem testCraftingSystem = new CraftingSystem();
        testCraftingSystem.buildItem("HungryJacks");
        assertEquals(CraftingLogic.getPossibleBuilds(), new ArrayList<>());
    }

    @Test
    void buildItemTest() {
        CraftingSystem testCraftingSystem = new CraftingSystem();
        testCraftingSystem.buildItem("Sword");
        assertTrue(testCraftingSystem.getInventoryContents().contains(Materials.Wood)); // Wood is removed from the inventory after building a sword.
        assertTrue(testCraftingSystem.getInventoryContents().contains(Materials.Steel)); // Steel is removed from the inventory after building a sword.
    }


    @Test
    void getInventoryContentsTest() {
        CraftingSystem testCraftingSystem = new CraftingSystem();
        for (int i = 0; i < Materials.values().length; i++) {
            assertEquals(testCraftingSystem.getInventoryContents().get(i),Materials.values()[i]);
        }
    }

    @Test
    void setInventoryContentsTest() {
        CraftingSystem testCraftingSystem = new CraftingSystem();
        List<Materials> inputList = new ArrayList<>(Arrays.asList(Materials.values()));
        testCraftingSystem.setInventoryContents(inputList);
        assertEquals(testCraftingSystem.getInventoryContents(), inputList);
    }

//    @Test
//    void basicTestSynchronizedInventoryContents() throws InterruptedException {
//        CraftingSystem testCraftingSystem = new CraftingSystem();
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                testCraftingSystem.getInventoryContents();
//            }
//        }); // create a new thread to call getInventoryContents
//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                testCraftingSystem.getInventoryContents();
//            }
//        });
//
//        thread.start(); // start the thread
//        thread2.start();
//        thread.join(); // wait for the thread to finish
//        thread2.join();
//
//        assertEquals(testCraftingSystem.getInventoryContents().size(), Materials.values().length * 2);
//        // check that the size of the inventory is twice the number of materials
//    }
}
**/