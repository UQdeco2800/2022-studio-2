package com.deco2800.game.entities.configs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MegaPoopConfigTest {
    @Test
    void createTest() {
        MegaPoopConfig megaPoopConfig = new MegaPoopConfig();
        assertEquals(100f, megaPoopConfig.SPEED);
    }
}
