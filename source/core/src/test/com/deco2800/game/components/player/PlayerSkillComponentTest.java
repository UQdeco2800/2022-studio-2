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
                        .addComponent(new PlayerStatsDisplay());

        skillManager = new PlayerSkillComponent(player);
    }

    @Test
    void testSkillCheck() {
        skillManager.startDash(new Vector2(1,1));
        skillManager.startTeleport();
        Assert.assertTrue(skillManager.isTeleporting());
        Assert.assertTrue(skillManager.isDashing());
        skillManager.update();
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
        Thread.sleep(1001);
        skillManager.update();
        Assert.assertFalse(skillManager.isTeleporting());
        Assert.assertFalse(skillManager.isDashing());
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
        skillManager.setSkill("teleport", player, player.getComponent(PlayerActions.class));
        assertEquals(player.getEvents().getNumberOfListeners("skill"), 1);
    }

    @Test
    void testSkillSetMultiple(){
        skillManager.setSkill("teleport", player, player.getComponent(PlayerActions.class));
        skillManager.setSkill("teleport", player, player.getComponent(PlayerActions.class));
        skillManager.setSkill("teleport", player, player.getComponent(PlayerActions.class));
        assertEquals(player.getEvents().getNumberOfListeners("skill"), 3);
    }

    @Test
    void testSkillSetWrong() {
        skillManager.setSkill("mamma_jamma_bootsy_wiggle", player, player.getComponent(PlayerActions.class));
        assertEquals(player.getEvents().getNumberOfListeners("skill"), -1);
    }

    @Test
    void testSkillRemoval() {
        skillManager.setSkill("teleport", player, player.getComponent(PlayerActions.class));
        skillManager.setSkill("teleport", player, player.getComponent(PlayerActions.class));
        skillManager.setSkill("teleport", player, player.getComponent(PlayerActions.class));
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
    void testTeleportPlayer() {
        // function needs editing
    }

}
