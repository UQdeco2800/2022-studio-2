package com.deco2800.game.components.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenCraftingComponentTest {

    @BeforeEach
    void setUp() {
    }

    /*@Test
    void create() {

    }*/
    @Test
    void getCraftingStatusTest() {
        assertEquals(OpenCraftingComponent.getCraftingStatus(), false);
    }

    @Test
    void setCraftingStatus() {
        if (OpenCraftingComponent.getCraftingStatus()) {
            OpenCraftingComponent.setCraftingStatus();
            assertEquals(OpenCraftingComponent.getCraftingStatus(), false);
        }
    }


}