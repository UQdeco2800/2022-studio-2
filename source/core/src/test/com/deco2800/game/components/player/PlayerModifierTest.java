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

    /**
     * Awaitility hides its secrets from me. So here is a custom delay function.
     * Should work on any machine since it uses System time
     * @param sleep Millisecond time delay to sleep
     */
    private void custom_wait (long sleep) {
        long start = System.currentTimeMillis();
        while ((System.currentTimeMillis() - start) < sleep);
    }

    PlayerActions actions;
    PlayerModifier modifier;
    CombatStatsComponent combat;
    @BeforeEach
    void init() {
        actions = new PlayerActions();
        actions.updateMaxSpeed(2);
        modifier = new PlayerModifier();
        modifier.jUnitAddPlayerActions(actions);
        combat = new CombatStatsComponent(100, 1, 100, 100);
        combat.setDamageReduction(0.5f);
        modifier.jUnitAddCombatStats(combat);
    }

    @Test
    void shouldNotGetModified() {
        assertEquals(-1, modifier.getModified("move_speed"));
    }

    @Test
    void shouldNotGetReference() {
        assertEquals(-1, modifier.getReference("move_speed"));
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
    void shouldHaveModifier () {

        modifier.createModifier("moveSpeed", 2, false, 50);

        modifier.update();
        custom_wait(10);
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
    void shouldNotHaveExpiredModification () {
        modifier.createModifier("moveSpeed", 2, false, 100);
        modifier.update();
        custom_wait(90);
        modifier.update();
        assertEquals(4, actions.getMaxSpeed());
    }

    @Test
    void shouldHaveExpiredModification() {

        modifier.createModifier("moveSpeed", 2, false, 5);
        modifier.update();

        assertEquals(4, actions.getMaxSpeed());
        assertEquals(actions.getMaxSpeed(), modifier.getModified("moveSpeed"));

        custom_wait(10);
        modifier.update();

        assertEquals(2, actions.getMaxSpeed());
        assertEquals(actions.getMaxSpeed(), modifier.getModified("moveSpeed"));
    }

    @Test
    void shouldHaveCaughtNegativeMovespeed () {

        modifier.createModifier("moveSpeed", -4, false, 20);
        modifier.update();

        assertEquals(0.1f, modifier.getModified("moveSpeed"));
    }

    @Test
    void scalarMovespeed () {

        // Check modifiers are created
        modifier.createModifier("moveSpeed", 2, true, 0);
        modifier.createModifier("moveSpeed", 1, true, 20);
        assertTrue(modifier.checkModifier("moveSpeed", 2, true, 0));
        assertTrue(modifier.checkModifier("moveSpeed", 1, true, 20));
        modifier.update();

        // Permanent modifier should now be gone
        assertFalse(modifier.checkModifier("moveSpeed", 2, true, 0));
        assertTrue(modifier.checkModifier("moveSpeed", 1, true, 20));
        assertEquals(actions.getMaxSpeed(), modifier.getModified("moveSpeed"));

        // Check for temporary to be removed
        custom_wait(20);
        modifier.update();
        assertFalse(modifier.checkModifier("moveSpeed", 2, true, 0));
        assertFalse(modifier.checkModifier("moveSpeed", 1, true, 20));
        assertEquals(actions.getMaxSpeed(), modifier.getModified("moveSpeed"));
    }

    @Test
    void additiveMovespeed () {

        // Check modifiers are created
        modifier.createModifier("moveSpeed", 2, false, 0);
        modifier.createModifier("moveSpeed", 1, false, 20);
        assertTrue(modifier.checkModifier("moveSpeed", 2, false, 0));
        assertTrue(modifier.checkModifier("moveSpeed", 1, false, 20));
        modifier.update();

        // Permanent modifier should now be gone
        assertFalse(modifier.checkModifier("moveSpeed", 2, false, 0));
        assertTrue(modifier.checkModifier("moveSpeed", 1, false, 20));
        assertEquals(actions.getMaxSpeed(), modifier.getModified("moveSpeed"));

        // Check for temporary to be removed
        custom_wait(20);
        modifier.update();
        assertFalse(modifier.checkModifier("moveSpeed", 2, false, 0));
        assertFalse(modifier.checkModifier("moveSpeed", 1, false, 20));
        assertEquals(actions.getMaxSpeed(), modifier.getModified("moveSpeed"));
    }

    @Test
    void scalarDamageReduction () {

        // Check modifiers are created
        modifier.createModifier("damageReduction", 2, true, 0);
        modifier.createModifier("damageReduction", 1, true, 20);
        assertTrue(modifier.checkModifier("damageReduction", 2, true, 0));
        assertTrue(modifier.checkModifier("damageReduction", 1, true, 20));
        modifier.update();

        // Permanent modifier should now be gone
        assertFalse(modifier.checkModifier("damageReduction", 2, true, 0));
        assertTrue(modifier.checkModifier("damageReduction", 1, true, 20));
        assertEquals(combat.getDamageReduction(), modifier.getModified("damageReduction"));

        // Check for temporary to be removed
        custom_wait(20);
        modifier.update();
        assertFalse(modifier.checkModifier("damageReduction", 2, true, 0));
        assertFalse(modifier.checkModifier("damageReduction", 1, true, 20));
        assertEquals(combat.getDamageReduction(), modifier.getModified("damageReduction"));
    }

    @Test
    void additiveDamageReduction () {

        // Check modifiers are created
        modifier.createModifier("damageReduction", 2, false, 0);
        modifier.createModifier("damageReduction", 1, false, 20);
        assertTrue(modifier.checkModifier("damageReduction", 2, false, 0));
        assertTrue(modifier.checkModifier("damageReduction", 1, false, 20));
        modifier.update();

        // Permanent modifier should now be gone
        assertFalse(modifier.checkModifier("damageReduction", 2, false, 0));
        assertTrue(modifier.checkModifier("damageReduction", 1, false, 20));
        assertEquals(combat.getDamageReduction(), modifier.getModified("damageReduction"));

        // Check for temporary to be removed
        custom_wait(20);
        modifier.update();
        assertFalse(modifier.checkModifier("damageReduction", 2, false, 0));
        assertFalse(modifier.checkModifier("damageReduction", 1, false, 20));
        assertEquals(combat.getDamageReduction(), modifier.getModified("damageReduction"));
    }

    @Test
    void scalarManaRegen () {

        // Check modifiers are created
        modifier.createModifier("manaRegen", 2, true, 0);
        modifier.createModifier("manaRegen", 1, true, 20);
        assertTrue(modifier.checkModifier("manaRegen", 2, true, 0));
        assertTrue(modifier.checkModifier("manaRegen", 1, true, 20));
        modifier.update();

        // Permanent modifier should now be gone
        assertFalse(modifier.checkModifier("manaRegen", 2, true, 0));
        assertTrue(modifier.checkModifier("manaRegen", 1, true, 20));
        assertEquals(combat.getManaRegenerationRate(), modifier.getModified("manaRegen"));

        // Check for temporary to be removed
        custom_wait(20);
        modifier.update();
        assertFalse(modifier.checkModifier("manaRegen", 2, true, 0));
        assertFalse(modifier.checkModifier("manaRegen", 1, true, 20));
        assertEquals(combat.getManaRegenerationRate(), modifier.getModified("manaRegen"));
    }

    @Test
    void additiveManaRegen () {

        // Check modifiers are created
        modifier.createModifier("manaRegen", 2, false, 0);
        modifier.createModifier("manaRegen", 1, false, 20);
        assertTrue(modifier.checkModifier("manaRegen", 2, false, 0));
        assertTrue(modifier.checkModifier("manaRegen", 1, false, 20));
        modifier.update();

        // Permanent modifier should now be gone
        assertFalse(modifier.checkModifier("manaRegen", 2, false, 0));
        assertTrue(modifier.checkModifier("manaRegen", 1, false, 20));
        assertEquals(combat.getManaRegenerationRate(), modifier.getModified("manaRegen"));

        // Check for temporary to be removed
        custom_wait(20);
        modifier.update();
        assertFalse(modifier.checkModifier("manaRegen", 2, false, 0));
        assertFalse(modifier.checkModifier("manaRegen", 1, false, 20));
        assertEquals(combat.getManaRegenerationRate(), modifier.getModified("manaRegen"));
    }

    @Test
    void scalarManaMax () {

        // Check modifiers are created
        modifier.createModifier("manaMax", 2, true, 0);
        modifier.createModifier("manaMax", 1, true, 20);
        assertTrue(modifier.checkModifier("manaMax", 2, true, 0));
        assertTrue(modifier.checkModifier("manaMax", 1, true, 20));
        modifier.update();

        // Permanent modifier should now be gone
        assertFalse(modifier.checkModifier("manaMax", 2, true, 0));
        assertTrue(modifier.checkModifier("manaMax", 1, true, 20));
        assertEquals(combat.getMaxMana(), modifier.getModified("manaMax"));

        // Check for temporary to be removed
        custom_wait(20);
        modifier.update();
        assertFalse(modifier.checkModifier("manaMax", 2, true, 0));
        assertFalse(modifier.checkModifier("manaMax", 1, true, 20));
        assertEquals(combat.getMaxMana(), modifier.getModified("manaMax"));
    }

    @Test
    void additiveManaMax () {

        // Check modifiers are created
        modifier.createModifier("manaMax", 2, false, 0);
        modifier.createModifier("manaMax", 1, false, 20);
        assertTrue(modifier.checkModifier("manaMax", 2, false, 0));
        assertTrue(modifier.checkModifier("manaMax", 1, false, 20));
        modifier.update();

        // Permanent modifier should now be gone
        assertFalse(modifier.checkModifier("manaMax", 2, false, 0));
        assertTrue(modifier.checkModifier("manaMax", 1, false, 20));
        assertEquals(combat.getMaxMana(), modifier.getModified("manaMax"));

        // Check for temporary to be removed
        custom_wait(20);
        modifier.update();
        assertFalse(modifier.checkModifier("manaMax", 2, false, 0));
        assertFalse(modifier.checkModifier("manaMax", 1, false, 20));
        assertEquals(combat.getMaxMana(), modifier.getModified("manaMax"));
    }

    @Test
    void scalarStaminaRegen () {

        // Check modifiers are created
        modifier.createModifier("staminaRegen", 2, true, 0);
        modifier.createModifier("staminaRegen", 1, true, 20);
        assertTrue(modifier.checkModifier("staminaRegen", 2, true, 0));
        assertTrue(modifier.checkModifier("staminaRegen", 1, true, 20));
        modifier.update();

        // Permanent modifier should now be gone
        assertFalse(modifier.checkModifier("staminaRegen", 2, true, 0));
        assertTrue(modifier.checkModifier("staminaRegen", 1, true, 20));
        assertEquals(combat.getStaminaRegenerationRate(), modifier.getModified("staminaRegen"));

        // Check for temporary to be removed
        custom_wait(20);
        modifier.update();
        assertFalse(modifier.checkModifier("staminaRegen", 2, true, 0));
        assertFalse(modifier.checkModifier("staminaRegen", 1, true, 20));
        assertEquals(combat.getStaminaRegenerationRate(), modifier.getModified("staminaRegen"));
    }

    @Test
    void additiveStaminaRegen () {

        // Check modifiers are created
        modifier.createModifier("staminaRegen", 2, false, 0);
        modifier.createModifier("staminaRegen", 1, false, 20);
        assertTrue(modifier.checkModifier("staminaRegen", 2, false, 0));
        assertTrue(modifier.checkModifier("staminaRegen", 1, false, 20));
        modifier.update();

        // Permanent modifier should now be gone
        assertFalse(modifier.checkModifier("staminaRegen", 2, false, 0));
        assertTrue(modifier.checkModifier("staminaRegen", 1, false, 20));
        assertEquals(combat.getStaminaRegenerationRate(), modifier.getModified("staminaRegen"));

        // Check for temporary to be removed
        custom_wait(20);
        modifier.update();
        assertFalse(modifier.checkModifier("staminaRegen", 2, false, 0));
        assertFalse(modifier.checkModifier("staminaRegen", 1, false, 20));
        assertEquals(combat.getStaminaRegenerationRate(), modifier.getModified("staminaRegen"));
    }

    @Test
    void scalarStaminaMax () {

        // Check modifiers are created
        modifier.createModifier("staminaMax", 2, true, 0);
        modifier.createModifier("staminaMax", 1, true, 20);
        assertTrue(modifier.checkModifier("staminaMax", 2, true, 0));
        assertTrue(modifier.checkModifier("staminaMax", 1, true, 20));
        modifier.update();

        // Permanent modifier should now be gone
        assertFalse(modifier.checkModifier("staminaMax", 2, true, 0));
        assertTrue(modifier.checkModifier("staminaMax", 1, true, 20));
        assertEquals(combat.getMaxStamina(), modifier.getModified("staminaMax"));

        // Check for temporary to be removed
        custom_wait(20);
        modifier.update();
        assertFalse(modifier.checkModifier("staminaMax", 2, true, 0));
        assertFalse(modifier.checkModifier("staminaMax", 1, true, 20));
        assertEquals(combat.getMaxStamina(), modifier.getModified("staminaMax"));
    }

    @Test
    void additiveStaminaMax () {

        // Check modifiers are created
        modifier.createModifier("staminaMax", 2, false, 0);
        modifier.createModifier("staminaMax", 1, false, 20);
        assertTrue(modifier.checkModifier("staminaMax", 2, false, 0));
        assertTrue(modifier.checkModifier("staminaMax", 1, false, 20));
        modifier.update();

        // Permanent modifier should now be gone
        assertFalse(modifier.checkModifier("staminaMax", 2, false, 0));
        assertTrue(modifier.checkModifier("staminaMax", 1, false, 20));
        assertEquals(combat.getMaxStamina(), modifier.getModified("staminaMax"));

        // Check for temporary to be removed
        custom_wait(20);
        modifier.update();
        assertFalse(modifier.checkModifier("staminaMax", 2, false, 0));
        assertFalse(modifier.checkModifier("staminaMax", 1, false, 20));
        assertEquals(combat.getMaxStamina(), modifier.getModified("staminaMax"));
    }
}
