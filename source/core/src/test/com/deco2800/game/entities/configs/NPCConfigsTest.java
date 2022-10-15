package com.deco2800.game.entities.configs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NPCConfigsTest {
    @Test
    void gymBroConfigTest() {
        NPCConfigs npcConfigs = new NPCConfigs();
        assertEquals(npcConfigs.gymBro.getClass(), new GymBroConfig().getClass());
    }

    @Test
    void heraclesConfigTest() {
        NPCConfigs npcConfigs = new NPCConfigs();
        assertEquals(npcConfigs.heracles.getClass(), new HeraclesConfig().getClass());
    }

    @Test
    void poopsConfigTest() {
        NPCConfigs npcConfigs = new NPCConfigs();
        assertEquals(npcConfigs.poops.getClass(), new PoopsConfig().getClass());
    }

    @Test
    void megaPoopConfigTest() {
        NPCConfigs npcConfigs = new NPCConfigs();
        assertEquals(npcConfigs.megaPoop.getClass(), new MegaPoopConfig().getClass());
    }
}
