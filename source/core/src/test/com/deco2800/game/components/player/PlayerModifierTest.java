package com.deco2800.game.components.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.extensions.GameExtension;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GameExtension.class)
public class PlayerModifierTest {

    PlayerActions actions;
    PlayerModifier modifier;
    @BeforeEach
    void init() {
        actions = new PlayerActions();
        actions.updateMaxSpeed(2);
        modifier = new PlayerModifier();
        modifier.jUnitAddPlayerActions(actions);
    }

    @Test
    void shouldNotCreateModifier () {

        assertFalse(modifier.createModifier("thisShouldntWork", 2, false, 20));
    }

    @Test
    void shouldCreateModifier () {

        assertTrue(modifier.createModifier("moveSpeed", 2, false, 20));
    }

    @Test
    void shouldHaveModifier () throws InterruptedException {

        modifier.createModifier("moveSpeed", 2, false, 50);

        modifier.update();
        Thread.sleep(10);
        modifier.update();

        assertTrue(modifier.checkModifier("moveSpeed", 2, false, 50));
    }

    @Test
    void shouldHaveAcceptedModifier () {

        modifier.createModifier("moveSpeed", 2, false, 0);
        modifier.update();

        assertFalse(modifier.checkModifier("moveSpeed", 2, false, 0));
        assertEquals(actions.getMaxSpeed(), modifier.getModified("moveSpeed"));
    }

    @Test
    void shouldHaveExpiredModification() throws InterruptedException {

        modifier.createModifier("moveSpeed", 2, false, 5);
        modifier.update();

        // Should be equal to 4
        assertEquals(actions.getMaxSpeed(), 4);
        assertEquals(actions.getMaxSpeed(), modifier.getModified("moveSpeed"));

        Thread.sleep(10);
        modifier.update();

        // Modified should have expired, now equal to 2
        assertEquals(actions.getMaxSpeed(), 2);
        assertEquals(actions.getMaxSpeed(), modifier.getModified("moveSpeed"));
    }

    @Test
    void shouldHaveIdenticalMoveSpeed () {

        modifier.createModifier("moveSpeed", 2, false, 20);
        modifier.update();

        assertEquals(actions.getMaxSpeed(), modifier.getModified("moveSpeed"));
    }

    @Test
    void shouldHaveIdenticalReduction () {
        CombatStatsComponent combat = new CombatStatsComponent(100, 1, 100, 20);
        PlayerModifier modifier = new PlayerModifier();
        modifier.jUnitAddCombatStats(combat);

        // Scalar should keep the value as 0
        modifier.createModifier("damageReduction", 2, true, 0);
        modifier.update();

        assertEquals(combat.getDamageReduction(), modifier.getModified("damageReduction"));
        assertEquals(combat.getDamageReduction(), modifier.getReference("damageReduction"));

        // Additive should make the value become 2
        modifier.createModifier("damageReduction", 2, false, 0);
        modifier.update();

        assertEquals(combat.getDamageReduction(), modifier.getModified("damageReduction"));
        assertEquals(combat.getDamageReduction(), modifier.getReference("damageReduction"));
    }
}
