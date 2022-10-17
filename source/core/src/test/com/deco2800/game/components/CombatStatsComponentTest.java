package com.deco2800.game.components;

import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.player.PlayerTouchAttackComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.WeaponFactory;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(GameExtension.class)
class CombatStatsComponentTest {

  @Mock
  RenderService renderService;

  @Test
  void shouldSetGetHealth() {
    CombatStatsComponent combat = new CombatStatsComponent(100, 20, 100, 100);
    assertEquals(100, combat.getHealth());

    //Modified because health cannot go over 100. Modified by Peter
    combat.setHealth(150);
    assertEquals(100, combat.getHealth());

    combat.setHealth(-50);
    assertEquals(0, combat.getHealth());
  }

  @Test
  void shouldCheckIsDead() {
    CombatStatsComponent combat = new CombatStatsComponent(100, 20, 100, 100);
    assertFalse(combat.isDead());

    combat.setHealth(0);
    assertTrue(combat.isDead());
  }

  @Test
  void shouldAddHealth() {
    CombatStatsComponent combat = new CombatStatsComponent(100, 20, 100, 100);
    combat.addHealth(-500);
    assertEquals(0, combat.getHealth());

    combat.addHealth(100);
    combat.addHealth(-20);
    assertEquals(80, combat.getHealth());
  }

  @Test
  void shouldSetGetBaseAttack() {
    CombatStatsComponent combat = new CombatStatsComponent(100, 20, 100, 100);
    assertEquals(20, combat.getBaseAttack());

    combat.setBaseAttack(150);
    assertEquals(150, combat.getBaseAttack());

    combat.setBaseAttack(-50);
    assertEquals(150, combat.getBaseAttack());
  }

  @Test
  void shouldSetGetStamina() {
    CombatStatsComponent combat = new CombatStatsComponent(100, 20, 100, 100);
    assertEquals(100, combat.getStamina());

    combat.setStamina(80);
    assertEquals(80, combat.getStamina());
    combat.setStamina(120);
    assertEquals(100, combat.getStamina());
    combat.setStamina(-50);
    assertEquals(0, combat.getStamina());
  }

  @Test
  void shouldSetGetStaminaRegenerationRate(){
    CombatStatsComponent combat = new CombatStatsComponent(100, 20, 100, 100);
    assertEquals(1, combat.getStaminaRegenerationRate());
    combat.setStaminaRegenerationRate(2);
    assertEquals(2, combat.getStaminaRegenerationRate());
  }

  @Test
  void shouldSetGetMaxStamina() {
    CombatStatsComponent combat = new CombatStatsComponent(100, 20, 100, 100);
    assertEquals(100, combat.getMaxStamina());

    combat.setStamina(80);
    assertEquals(100, combat.getMaxStamina());

    combat.setMaxStamina(80);
    assertEquals(80, combat.getMaxStamina());

    combat.setMaxStamina(-50);
    assertEquals(0, combat.getMaxStamina());
  }

  @Test
  void shouldSetGetMana() {
    CombatStatsComponent combat = new CombatStatsComponent(100, 20, 100, 100);
    assertEquals(100, combat.getMana());

    combat.setMana(80);
    assertEquals(80, combat.getMana());
    combat.setMana(120);
    assertEquals(100, combat.getMana());
    combat.setMana(-50);
    assertEquals(0, combat.getMana());
  }

  @Test
  void shouldSetGetManaRegenerationRate(){
    CombatStatsComponent combat = new CombatStatsComponent(100, 20, 100, 100);
    assertEquals(1, combat.getManaRegenerationRate());
    combat.setManaRegenerationRate(2);
    assertEquals(2, combat.getManaRegenerationRate());
  }

  @Test
  void shouldSetGetMaxMana() {
    CombatStatsComponent combat = new CombatStatsComponent(100, 20, 100, 100);
    assertEquals(100, combat.getMaxMana());

    combat.setMana(80);
    assertEquals(100, combat.getMaxMana());

    combat.setMaxMana(80);
    assertEquals(80, combat.getMaxMana());

    combat.setMaxMana(-50);
    assertEquals(0, combat.getMaxMana());
  }
  @Test
  void shouldAddMana() {
      CombatStatsComponent combat = new CombatStatsComponent(100, 20, 100, 100);
      combat.addMana(-500);
      assertEquals(0, combat.getMana());

      combat.addMana(100);
      combat.addMana(-20);
      assertEquals(80, combat.getMana());
    }
     @Test
      void shouldAddStamina() {
          CombatStatsComponent combat = new CombatStatsComponent(100, 20, 100, 100);
          combat.addStamina(-500);
          assertEquals(0, combat.getStamina());

          combat.addStamina(100);
          combat.addStamina(-20);
          assertEquals(80, combat.getStamina());
        }
  @Test
  void shouldCheckMana(){
      CombatStatsComponent combat = new CombatStatsComponent(100, 20, 100, 100);
      assertTrue(combat.checkMana(80));
      assertFalse(combat.checkMana(120));
  }
  @Test
  void shouldCheckStamina(){
      CombatStatsComponent combat = new CombatStatsComponent(100, 20, 100, 100);
      assertTrue(combat.checkStamina(80));
      assertFalse(combat.checkStamina(120));
  }


  /*
  void playerDropWeaponTest() {
    // mock a weapon and drop it using mockito
    ServiceLocator.registerPhysicsService(new PhysicsService());
    ServiceLocator.registerEntityService(new EntityService());
    ServiceLocator.registerRenderService(new RenderService());

    short targetLayer = (1 << 3);

    Entity player =  createPlayer(targetLayer);

    player.getEvents().trigger("dropWeapon");

    assertEquals(0, 0);

  }*/

  Entity createPlayer(short targetLayer) {
    Entity entity =
            new Entity()
                    .addComponent(new TouchAttackComponent(targetLayer))
                    .addComponent(new PlayerTouchAttackComponent(targetLayer))
                    .addComponent(new CombatStatsComponent(100, 10, 1, 1))
                    .addComponent(new PhysicsComponent())
                    .addComponent(new HitboxComponent());
    entity.setEntityType(EntityTypes.PLAYER);
    entity.create();
    return entity;
  }

}
