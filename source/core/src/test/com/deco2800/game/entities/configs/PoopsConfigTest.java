package com.deco2800.game.entities.configs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PoopsConfigTest {
    @Test
    void createTest() {
        PoopsConfig poopsConfig = new PoopsConfig();
        assertEquals(1f, poopsConfig.speed);
        assertEquals(60, poopsConfig.healthPoops);
    }
}
