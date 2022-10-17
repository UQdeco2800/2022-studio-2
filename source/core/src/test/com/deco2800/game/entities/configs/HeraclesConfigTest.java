package com.deco2800.game.entities.configs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeraclesConfigTest {
    @Test
    void createTest() {
        HeraclesConfig heraclesConfig = new HeraclesConfig();
        assertEquals(100, heraclesConfig.HEALTHHERACLES);
        assertEquals(10, heraclesConfig.BASEATTACKHERACLES);
        assertEquals(2f, heraclesConfig.SPEED);
    }
}
