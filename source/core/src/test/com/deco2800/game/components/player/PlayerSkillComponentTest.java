package com.deco2800.game.components.player;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.services.GameTime;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
public class PlayerSkillComponentTest {

    PlayerSkillComponent skillManager;
    Entity player;

    @BeforeEach
    void initialisation() {
        player = new Entity()
                        .addComponent(new PhysicsComponent(new PhysicsEngine(
                                new World(Vector2.Zero, true),
                                new GameTime())))
                        .addComponent(new PlayerActions())
                        .addComponent(new CombatStatsComponent(100, 5, 100, 100))
                        .addComponent(new KeyboardPlayerInputComponent())
                        .addComponent(new PlayerStatsDisplay())
                        .addComponent(new PlayerModifier());

        skillManager = new PlayerSkillComponent(player);
        skillManager.setSkillAnimator(new Entity());
    }

    @Test
    void testSkillCheck() {
        skillManager.update();
        skillManager.startDash(new Vector2(1,1));
        skillManager.update();
        skillManager.startTeleport();
        skillManager.update();
        Assert.assertTrue(skillManager.isTeleporting());
        Assert.assertTrue(skillManager.isDashing());
        skillManager.update();
        skillManager.update();
        skillManager.update();
        Assert.assertTrue(skillManager.isTeleporting());
        Assert.assertTrue(skillManager.isDashing());
    }

    @Test
    void testSkillEnd() throws InterruptedException {
        skillManager.startDash(new Vector2(1,1));
        skillManager.startTeleport();

        Assert.assertFalse(skillManager.checkSkillEnd("dash"));
        Assert.assertFalse(skillManager.checkSkillEnd("teleport"));

        skillManager.update();

        Assert.assertFalse(skillManager.checkSkillEnd("dash"));
        Assert.assertFalse(skillManager.checkSkillEnd("teleport"));

        Thread.sleep(1001);

        skillManager.update();

        Assert.assertFalse(skillManager.isTeleporting());
        Assert.assertFalse(skillManager.isDashing());

        // First poll on skill end should be true
        Assert.assertTrue(skillManager.checkSkillEnd("dash"));
        Assert.assertTrue(skillManager.checkSkillEnd("teleport"));

        // Second poll on skill end should be false
        Assert.assertFalse(skillManager.checkSkillEnd("dash"));
        Assert.assertFalse(skillManager.checkSkillEnd("teleport"));
    }

    @Test
    void testSkillEndWrongArguments() {
        Assert.assertFalse(skillManager.checkSkillEnd(
                "Greater good?' I am your wife! I'm the greatest good you're ever gonna get!"));
    }

    @Test
    void testMovementModifiedDash() {
        Assert.assertFalse(skillManager.movementIsModified());
        skillManager.startDash(new Vector2(1,1));
        Assert.assertTrue(skillManager.movementIsModified());
    }

    @Test
    void testMovementModifiedTeleport() {
        Assert.assertFalse(skillManager.movementIsModified());
        skillManager.startTeleport();
        Assert.assertTrue(skillManager.movementIsModified());
    }

    @Test
    void testMovementModifiedCombination() {
        Assert.assertFalse(skillManager.movementIsModified());
        skillManager.startTeleport();
        skillManager.startDash(new Vector2(1,1));
        Assert.assertTrue(skillManager.movementIsModified());
    }

    @Test
    void testSkillSet() {
        skillManager.setSkill(1, "teleport", player, player.getComponent(PlayerActions.class));
        assertEquals(player.getEvents().getNumberOfListeners("skill"), 1);
    }

    @Test
    void testSkillSetMultiple(){
        skillManager.setSkill(1, "teleport", player, player.getComponent(PlayerActions.class));
        skillManager.setSkill(1, "teleport", player, player.getComponent(PlayerActions.class));
        skillManager.setSkill(1, "teleport", player, player.getComponent(PlayerActions.class));
        assertEquals(player.getEvents().getNumberOfListeners("skill"), 3);
    }

    @Test
    void testInvulnerable() {
        assertFalse(skillManager.isInvulnerable());
        skillManager.startDash(new Vector2(1,1));
        assertTrue(skillManager.isInvulnerable());
        skillManager.update();
        assertTrue(skillManager.isInvulnerable());
    }

    @Test
    void testSkillSetWrong() {
        skillManager.setSkill(1, "mamma_jamma_bootsy_wiggle", player, player.getComponent(PlayerActions.class));
        assertEquals(player.getEvents().getNumberOfListeners("skill"), -1);
    }

    @Test
    void testSkillRemoval() {
        skillManager.setSkill(1, "teleport", player, player.getComponent(PlayerActions.class));
        skillManager.setSkill(1, "teleport", player, player.getComponent(PlayerActions.class));
        skillManager.setSkill(1, "teleport", player, player.getComponent(PlayerActions.class));
        skillManager.resetSkills(player);
        assertEquals(player.getEvents().getNumberOfListeners("skill"), 0);
    }

    @Test
    void testDashing() {
        skillManager.startDash(new Vector2(1,1));

        assertEquals(skillManager.isDashing(), true);
    }

    @Test
    void testDashingNegative() {
        assertEquals(skillManager.isDashing(), false);
    }

    @Test
    void testNoAlterations() {
        Vector2 modifiedMovement = skillManager.getModifiedMovement(new Vector2(1,1));
        assertEquals(1, modifiedMovement.x);
        assertEquals(1, modifiedMovement.y);

        modifiedMovement = skillManager.getModifiedMovement(new Vector2(-1,-1));
        assertEquals(-1, modifiedMovement.x);
        assertEquals(-1, modifiedMovement.y);

        modifiedMovement = skillManager.getModifiedMovement(new Vector2(0,0));
        assertEquals(0, modifiedMovement.x);
        assertEquals(0, modifiedMovement.y);
    }

    @Test
    void testDashingModifiedMovement() {
        skillManager.startDash(new Vector2(1,1));
        assertEquals(skillManager.getModifiedMovement(new Vector2(0,0)).x, 6);
        assertEquals(skillManager.getModifiedMovement(new Vector2(0,0)).y, 6);
        assertEquals(skillManager.getModifiedMovement(new Vector2(1,1)).x, 6.800000190734863);
        assertEquals(skillManager.getModifiedMovement(new Vector2(1,1)).y, 6.800000190734863);
        assertEquals(skillManager.getModifiedMovement(new Vector2(0,1)).x, 6.0);
        assertEquals(skillManager.getModifiedMovement(new Vector2(0,1)).y, 6.800000190734863);
        assertEquals(skillManager.getModifiedMovement(new Vector2(1,0)).y, 6.0);
        assertEquals(skillManager.getModifiedMovement(new Vector2(1,0)).x, 6.800000190734863);
        assertEquals(skillManager.getModifiedMovement(new Vector2(100,100)).x, 86);
        assertEquals(skillManager.getModifiedMovement(new Vector2(100,100)).y, 86);
        assertEquals(skillManager.getModifiedMovement(new Vector2(-100,-100)).x, -74);
        assertEquals(skillManager.getModifiedMovement(new Vector2(-100,-100)).y, -74);
    }

    @Test
    void testTeleportModifiedMovement() {
        skillManager.startTeleport();
        assertTrue(skillManager.getModifiedMovement(new Vector2(1,1)).x < 1);
        assertTrue(skillManager.getModifiedMovement(new Vector2(1,1)).y < 1);
        assertTrue(skillManager.getModifiedMovement(new Vector2(1,1)).x != 0);
        assertTrue(skillManager.getModifiedMovement(new Vector2(1,1)).y != 0);
        assertTrue(skillManager.getModifiedMovement(new Vector2(0,0)).x == 0);
        assertTrue(skillManager.getModifiedMovement(new Vector2(0,0)).y == 0);

    }

    @Test
    void testTeleportPlayer1() {
        player.getComponent(PlayerActions.class).walk(new Vector2(1,1));
        Vector2 beforePos = player.getPosition();
        skillManager.teleportPlayer();
        Vector2 afterPos = player.getPosition();
        assertEquals(new Vector2(beforePos.x + 4.0f, beforePos.y + 4.0f), afterPos);
    }

    @Test
    void testTeleportPlayer2() {
        player.getComponent(PlayerActions.class).walk(new Vector2(1,0));
        Vector2 beforePos = player.getPosition();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        Vector2 afterPos = player.getPosition();
        assertEquals(new Vector2(beforePos.x + 24.18f, beforePos.y + 0.11f), afterPos);
    }

    @Test
    void testTeleportPlayer3() {
        player.getComponent(PlayerActions.class).walk(new Vector2(-1,0));
        Vector2 beforePos = player.getPosition();
        skillManager.teleportPlayer();
        Vector2 afterPos = player.getPosition();
        assertEquals(new Vector2(beforePos.x - 0.08f, beforePos.y + 0.11f), afterPos);
    }

    @Test
    void testTeleportPlayer4() {
        player.getComponent(PlayerActions.class).walk(new Vector2(0,1));
        Vector2 beforePos = player.getPosition();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        Vector2 afterPos = player.getPosition();
        assertEquals(new Vector2(beforePos.x, beforePos.y + 24.68f), afterPos);
    }

    @Test
    void testTeleportPlayer5() {
        player.getComponent(PlayerActions.class).walk(new Vector2(0,-1));
        Vector2 beforePos = player.getPosition();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        skillManager.teleportPlayer();
        Vector2 afterPos = player.getPosition();
        assertEquals(new Vector2(beforePos.x, beforePos.y + 0.11f), afterPos);
    }

    @Test
    void getSkillComponent() {
        PlayerActions actions = player.getComponent(PlayerActions.class);
        actions.create();
        PlayerSkillComponent component = actions.getSkillComponent();
        assertEquals(component.getClass(), PlayerSkillComponent.class);
    }

    @Test
    void dashActivation() {
        PlayerActions actions = player.getComponent(PlayerActions.class);
        actions.create();
        PlayerSkillComponent component = actions.getSkillComponent();
        actions.dash();
        assertTrue(component.isDashing());

    }

    @Test
    void teleportActivation() {
        PlayerActions actions = player.getComponent(PlayerActions.class);
        actions.create();
        actions.setSkillAnimator(new Entity());
        PlayerSkillComponent component = actions.getSkillComponent();
        component.setSkill(1, "teleport", player, actions);
        actions.teleport();
        assertTrue(component.isTeleporting());

    }

    @Test
    void skillCooldownTest() {
        PlayerActions actions = player.getComponent(PlayerActions.class);
        actions.create();
        PlayerSkillComponent component = actions.getSkillComponent();
        //actions.setSkillCooldown("teleport", 100L);
        //assertFalse(actions.cooldownFinished("teleport", 100));

    }

    @Test
    void skillCooldownTestBadInput() {
        PlayerActions actions = player.getComponent(PlayerActions.class);
        actions.create();
        PlayerSkillComponent component = actions.getSkillComponent();
        //actions.setSkillCooldown("jibberish", 0L);
        //actions.cooldownFinished("jibberish", 0L);
    }
}
