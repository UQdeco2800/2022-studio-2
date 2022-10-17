package com.deco2800.game.entities.configs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GymBroConfigTest {

    @Test
    void createTest() {
        GymBroConfig gymBroConfig = new GymBroConfig();
        assertEquals(2f, gymBroConfig.SPEED);
        assertEquals(5, gymBroConfig.BASEATTACKGYMBRO);
        assertEquals(70, gymBroConfig.HEALTHGYMBRO);
    }
}
