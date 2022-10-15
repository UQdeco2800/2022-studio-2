package com.deco2800.game.entities.configs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GymBroConfigTest {

    @Test
    void createTest() {
        GymBroConfig gymBroConfig = new GymBroConfig();
        assertEquals(2f, gymBroConfig.speed);
        assertEquals(5, gymBroConfig.baseAttackGymBro);
        assertEquals(70, gymBroConfig.healthGymBro);
    }
}
