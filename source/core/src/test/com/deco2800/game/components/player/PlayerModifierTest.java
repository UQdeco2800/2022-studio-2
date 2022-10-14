package com.deco2800.game.components.player;

import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.PlayerFactory;
import com.deco2800.game.extensions.GameExtension;

import static org.junit.jupiter.api.Assertions.*;

import net.dermetfan.gdx.scenes.scene2d.ui.CircularGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Modifier;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class PlayerModifierTest {

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
        // Register our input services
        // this must be done each time otherwise the physics service is lost
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerRenderService(new RenderService());

        // Create our player
        Entity player = PlayerFactory.createTestPlayer();
        player.create();
        modifier = player.getComponent(PlayerModifier.class);
        actions = player.getComponent(PlayerActions.class);
        combat = player.getComponent(CombatStatsComponent.class);
    }

    @Test
    void shouldCreate() {
        // Check initial array, should be empty!
        assertEquals(0, modifier.getModifiers().size());

        // Check initial components
        assertEquals(actions, modifier.getPlayerActions());
        assertEquals(combat, modifier.getCombatStatsComponent());

        // Check all reference and modified variables are identical to components
        assertEquals(actions.getMaxSpeed(), modifier.getModified(PlayerModifier.MOVESPEED));
        assertEquals(actions.getMaxSpeed(), modifier.getReference(PlayerModifier.MOVESPEED));

        assertEquals(combat.getDamageReduction(), modifier.getModified(PlayerModifier.DMGREDUCTION));
        assertEquals(combat.getDamageReduction(), modifier.getReference(PlayerModifier.DMGREDUCTION));

        assertEquals(combat.getManaRegenerationRate(), modifier.getModified(PlayerModifier.MANAREGEN));
        assertEquals(combat.getManaRegenerationRate(), modifier.getReference(PlayerModifier.MANAREGEN));

        assertEquals(combat.getMaxMana(), modifier.getModified(PlayerModifier.MANAMAX));
        assertEquals(combat.getMaxMana(), modifier.getReference(PlayerModifier.MANAMAX));

        assertEquals(combat.getStaminaRegenerationRate(), modifier.getModified(PlayerModifier.STAMINAREGEN));
        assertEquals(combat.getStaminaRegenerationRate(), modifier.getReference(PlayerModifier.STAMINAREGEN));

        assertEquals(combat.getMaxStamina(), modifier.getModified(PlayerModifier.STAMINAMAX));
        assertEquals(combat.getMaxStamina(), modifier.getReference(PlayerModifier.STAMINAMAX));
    }

    @Test
    void shouldIncrementHealth() {
        combat.setHealth(80);
        modifier.createModifier("health", 2, true, 0);
        assertEquals(81, combat.getHealth());
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
        assertEquals(5, actions.getMaxSpeed());
    }

    @Test
    void shouldHaveExpiredModification() {

        modifier.createModifier("moveSpeed", 2, false, 5);
        modifier.update();

        assertEquals(actions.getMaxSpeed(), modifier.getModified("moveSpeed"));

        custom_wait(10);
        modifier.update();

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
        assertEquals(9, modifier.getReference("moveSpeed")); //3 * (1(temp) + 2(ref)) = 9

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
        assertEquals(5, modifier.getReference("moveSpeed")); //3(default) + 2(ref) = 5

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
        assertEquals(0, modifier.getReference("damageReduction")); // Will be 0 since that is the default

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
        assertEquals(2, modifier.getReference("damageReduction"));

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
        assertEquals(3, modifier.getReference("manaRegen"));

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
        assertEquals(3, modifier.getReference("manaRegen"));

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
        assertEquals(300, modifier.getReference("manaMax"));

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
        assertEquals(102, modifier.getReference("manaMax"));

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
        assertEquals(3, modifier.getReference("staminaRegen"));

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
        assertEquals(3, modifier.getReference("staminaRegen"));

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
        assertEquals(300, modifier.getReference("staminaMax"));

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
        assertEquals(102, modifier.getReference("staminaMax"));

        // Check for temporary to be removed
        custom_wait(20);
        modifier.update();
        assertFalse(modifier.checkModifier("staminaMax", 2, false, 0));
        assertFalse(modifier.checkModifier("staminaMax", 1, false, 20));
        assertEquals(combat.getMaxStamina(), modifier.getModified("staminaMax"));
    }
}
