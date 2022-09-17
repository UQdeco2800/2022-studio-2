package com.deco2800.game.components.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
class PlayerSkillComponentTest {

    PlayerSkillComponent skillManager;
    Entity player;

    private void customWait(long time) {
        long startTime = ServiceLocator.getTimeSource().getTime();
        while(ServiceLocator.getTimeSource().getTimeSince(startTime) < time) {
            // Do nothing
            continue;
        }
    }

    @BeforeEach
    void initialisation() {
        player = new Entity()
                        .addComponent(new PhysicsComponent(new PhysicsEngine(
                                new World(Vector2.Zero, true),
                                new GameTime())))
                        .addComponent(new PlayerActions())
                        .addComponent(new CombatStatsComponent(100, 5, 100, 100))
                        .addComponent(new KeyboardPlayerInputComponent())
                        .addComponent(new PlayerModifier());

        skillManager = new PlayerSkillComponent(player);
        skillManager.setSkillAnimator(new Entity());
        ServiceLocator.registerTimeSource(new GameTime());
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
    void testSkillEnd() {
        skillManager.startDash(new Vector2(1,1));
        skillManager.startTeleport();
        skillManager.startBlock();
        skillManager.startDodge(new Vector2(1,1));
        skillManager.startBleed();
        skillManager.startRoot();

        Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.DASH));
        Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.TELEPORT));
        Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.DODGE));
        Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.BLOCK));
        Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.BLEED));
        Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.ROOT));

        skillManager.update();

        Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.DASH));
        Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.TELEPORT));
        Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.DODGE));
        Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.BLOCK));
        Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.BLEED));
        Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.ROOT));

        customWait(1001);

        skillManager.update();

        Assert.assertFalse(skillManager.isTeleporting());
        Assert.assertFalse(skillManager.isDashing());
        Assert.assertFalse(skillManager.isBlocking());
        Assert.assertFalse(skillManager.isDodging());

        // First poll on skill end should be true
        Assert.assertTrue(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.DASH));
        Assert.assertTrue(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.TELEPORT));
        Assert.assertTrue(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.DODGE));
        Assert.assertTrue(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.BLOCK));
        //Assert.assertTrue(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.BLEED));
        //Assert.assertTrue(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.ROOT));

        // Second poll on skill end should be false
        Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.DASH));
        Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.TELEPORT));
        Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.DODGE));
        Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.BLOCK));
        //Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.BLEED));
        //Assert.assertFalse(skillManager.checkSkillEnd(PlayerSkillComponent.SkillTypes.ROOT));
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
        skillManager.setSkill(1, PlayerSkillComponent.SkillTypes.TELEPORT, player, player.getComponent(PlayerActions.class));
        assertEquals(1, player.getEvents().getNumberOfListeners("skill"));
        skillManager.setSkill(3, PlayerSkillComponent.SkillTypes.DODGE, player, player.getComponent(PlayerActions.class));
        assertEquals(2, player.getEvents().getNumberOfListeners("skill"));
        skillManager.setSkill(2, PlayerSkillComponent.SkillTypes.TELEPORT, player, player.getComponent(PlayerActions.class));
        assertEquals(1, player.getEvents().getNumberOfListeners("skill2"));

    }

    @Test
    void testSkillSetMultiple(){
        skillManager.setSkill(1, PlayerSkillComponent.SkillTypes.TELEPORT, player, player.getComponent(PlayerActions.class));
        skillManager.setSkill(1, PlayerSkillComponent.SkillTypes.TELEPORT, player, player.getComponent(PlayerActions.class));
        skillManager.setSkill(1, PlayerSkillComponent.SkillTypes.TELEPORT, player, player.getComponent(PlayerActions.class));
        assertEquals(3, player.getEvents().getNumberOfListeners("skill"));
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
        skillManager.setSkill(1, PlayerSkillComponent.SkillTypes.DASH, player, player.getComponent(PlayerActions.class));
        assertEquals(-1, player.getEvents().getNumberOfListeners("skill"));
    }

    @Test
    void testSkillRemoval() {
        skillManager.setSkill(1, PlayerSkillComponent.SkillTypes.TELEPORT, player, player.getComponent(PlayerActions.class));
        skillManager.setSkill(1, PlayerSkillComponent.SkillTypes.TELEPORT, player, player.getComponent(PlayerActions.class));
        skillManager.setSkill(1, PlayerSkillComponent.SkillTypes.TELEPORT, player, player.getComponent(PlayerActions.class));
        skillManager.resetSkills(player);
        assertEquals(0, player.getEvents().getNumberOfListeners("skill"));
    }

    @Test
    void testDashing() {
        skillManager.startDash(new Vector2(1,1));

        assertTrue(skillManager.isDashing());
    }

    @Test
    void testDashingNegative() {
        assertFalse(skillManager.isDashing());
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
        customWait(1);
        skillManager.startDash(new Vector2(1,1));
        assertEquals(6, skillManager.getModifiedMovement(new Vector2(0,0)).x);
        assertEquals(6, skillManager.getModifiedMovement(new Vector2(0,0)).y);
        assertEquals(6.800000190734863, skillManager.getModifiedMovement(new Vector2(1,1)).x);
        assertEquals(6.800000190734863, skillManager.getModifiedMovement(new Vector2(1,1)).y);
        assertEquals(6.0, skillManager.getModifiedMovement(new Vector2(0,1)).x);
        assertEquals(6.800000190734863, skillManager.getModifiedMovement(new Vector2(0,1)).y);
        assertEquals(6.0, skillManager.getModifiedMovement(new Vector2(1,0)).y);
        assertEquals(6.800000190734863, skillManager.getModifiedMovement(new Vector2(1,0)).x);
        assertEquals(86, skillManager.getModifiedMovement(new Vector2(100,100)).x);
        assertEquals(86, skillManager.getModifiedMovement(new Vector2(100,100)).y);
        assertEquals(-74, skillManager.getModifiedMovement(new Vector2(-100,-100)).x);
        assertEquals(-74, skillManager.getModifiedMovement(new Vector2(-100,-100)).y);
    }

    @Test
    void testTeleportModifiedMovement() {
        skillManager.startTeleport();
        customWait(1);
        skillManager.startTeleport();
        assertTrue(skillManager.getModifiedMovement(new Vector2(1,1)).x < 1);
        assertTrue(skillManager.getModifiedMovement(new Vector2(1,1)).y < 1);
        assertNotEquals(0, skillManager.getModifiedMovement(new Vector2(1,1)).x);
        assertNotEquals(0, skillManager.getModifiedMovement(new Vector2(1,1)).y);
        assertEquals(0,skillManager.getModifiedMovement(new Vector2(0,0)).x);
        assertEquals(0,skillManager.getModifiedMovement(new Vector2(0,0)).y);

    }

    @Test
    void testDodgeModifiedMovement() {
        skillManager.startDodge(new Vector2(1,1));
        customWait(1);
        skillManager.startDodge(new Vector2(1,1));
        assertEquals(-4.5f, skillManager.getModifiedMovement(new Vector2(1,1)).x);
        assertEquals(-4.5f, skillManager.getModifiedMovement(new Vector2(1,1)).y);
    }

    @Test
    void testDodgeSpeedBoost() {
        skillManager.skillDamageTrigger();
        skillManager.startDodge(new Vector2(1,1));
        skillManager.update();
        skillManager.skillDamageTrigger();
        customWait(301);
        skillManager.update();
        assertTrue(skillManager.movementIsModified());
        assertEquals(1.5f, skillManager.getModifiedMovement(new Vector2(1,1)).x);
        assertEquals(1.5f, skillManager.getModifiedMovement(new Vector2(1,1)).y);
        customWait(1500);
        skillManager.update();
        assertFalse(skillManager.movementIsModified());
        assertEquals(1.0f, skillManager.getModifiedMovement(new Vector2(1,1)).x);
        assertEquals(1.0f, skillManager.getModifiedMovement(new Vector2(1,1)).y);
    }

    @Test
    void testVectorNormalisation() {
        skillManager.startDodge(new Vector2(0,0));
        assertEquals(0.0, skillManager.getModifiedMovement(new Vector2(0,1)).x);
        assertEquals(3.0, skillManager.getModifiedMovement(new Vector2(0,1)).y);
        assertEquals(0.0, skillManager.getModifiedMovement(new Vector2(1,0)).y);
        assertEquals(3.0, skillManager.getModifiedMovement(new Vector2(1,0)).x);
    }

    @Test
    void testBlock() {
        assertFalse(skillManager.isInvulnerable());
        skillManager.startBlock();
        skillManager.startBlock();
        skillManager.update();
        assertTrue(skillManager.isInvulnerable());
        customWait(2000);
        skillManager.update();
        assertFalse(skillManager.isInvulnerable());
    }

    @Test
    void testBlockDamageCooldowns() {
        skillManager.skillDamageTrigger();
        skillManager.startBlock();
        skillManager.update();
        skillManager.skillDamageTrigger();
        skillManager.update();
        //customWait(2);
        assertTrue(skillManager.cooldownFinished("dodge", 500L));
    }

    @Test
    void testBleed() {
        assertFalse(skillManager.bleedActive());
        assertFalse(skillManager.isBleeding());
        skillManager.hitBleed(null);
        assertFalse(skillManager.isBleeding());
        skillManager.startBleed();
        assertTrue(skillManager.bleedActive());
        skillManager.hitBleed(null);
        assertTrue(skillManager.isBleeding());
    }

    @Test
    void testCheckBleed() {
        ServiceLocator.registerEntityService(new EntityService());
        Entity enemy =
                new Entity()
                        .addComponent(new CombatStatsComponent(15, 1, 1, 1));
        enemy.create();

        skillManager.startBleed();
        skillManager.hitBleed(enemy);
        assertTrue(skillManager.isBleeding());
        skillManager.checkBleed(enemy);
        assertEquals(10, enemy.getComponent(CombatStatsComponent.class).getHealth());
        skillManager.checkBleed(enemy);
        assertEquals(10, enemy.getComponent(CombatStatsComponent.class).getHealth());
        customWait(1001);
        skillManager.checkBleed(enemy);
        assertEquals(5, enemy.getComponent(CombatStatsComponent.class).getHealth());
        customWait(1001);
        skillManager.checkBleed(enemy);
        assertEquals(0, enemy.getComponent(CombatStatsComponent.class).getHealth());
        skillManager.update();
        assertFalse(skillManager.isBleeding());
    }

    @Test
    void testRoot() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerRenderService(new RenderService());
        Entity enemy =
                new Entity()
                        .addComponent(new AITaskComponent());
        enemy.create();

        assertFalse(skillManager.rootActive());
        assertFalse(skillManager.isRooted());
        skillManager.hitRoot(enemy);
        assertFalse(skillManager.isRooted());
        skillManager.startRoot();
        assertTrue(skillManager.rootActive());
        skillManager.hitRoot(enemy);
        assertTrue(skillManager.isRooted());
        assertFalse(skillManager.rootActive());
        customWait(5001);
        skillManager.update();
        assertFalse(skillManager.isRooted());
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
        actions.setSkillAnimator(new Entity());
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
        component.setSkill(1, PlayerSkillComponent.SkillTypes.TELEPORT, player, actions);
        actions.teleport();
        assertTrue(component.isTeleporting());

    }

    @Test
    void skillCooldownTest() {
        PlayerActions actions = player.getComponent(PlayerActions.class);
        actions.create();
        PlayerSkillComponent component = actions.getSkillComponent();
        component.setSkillCooldown("teleport");
        assertFalse(component.cooldownFinished("teleport", 1));
        customWait(2);
        assertTrue(component.cooldownFinished("teleport", 1));

    }

    @Test
    void skillCooldownTestBadInput() {
        PlayerActions actions = player.getComponent(PlayerActions.class);
        actions.create();
        PlayerSkillComponent component = actions.getSkillComponent();
        component.setSkillCooldown("jibberish");
        customWait(1);
        Assert.assertTrue(component.cooldownFinished("jibberish", 0L));
    }
}