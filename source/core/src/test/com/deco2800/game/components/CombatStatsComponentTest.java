package com.deco2800.game.components;

import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(GameExtension.class)
class CombatStatsComponentTest {
  @Test
  void shouldSetGetHealth() {
    CombatStatsComponent combat = new CombatStatsComponent(100, 20, 100, 100);
    assertEquals(100, combat.getHealth());

    combat.setHealth(150);
    assertEquals(150, combat.getHealth());

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
}
