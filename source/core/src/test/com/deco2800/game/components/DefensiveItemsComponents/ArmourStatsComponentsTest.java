package com.deco2800.game.components.DefensiveItemsComponents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import com.deco2800.game.crafting.Materials;
import java.util.HashMap;

import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.components.player.PlayerModifier;
import com.deco2800.game.extensions.GameExtension;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GameExtension.class)
public class ArmourStatsComponentsTest {
    /**
     * Setting up armour component so that tests can be made on it
     */
    ArmourStatsComponent armourStats;
    @BeforeEach
    void init() {
        armourStats = new ArmourStatsComponent(10, 1, 10, 10, 1, new HashMap<Materials,
                Integer>());
    }

    /**
     * Test to make sure that weight works as expected with Armour
     */
    @Test
    void shouldSetGetWeight() {
        assertEquals(1, armourStats.getWeight());

        armourStats.setWeight(10);
        assertEquals(10, armourStats.getWeight());
    }
}
