package com.deco2800.game.components.player;

import com.deco2800.game.components.CombatItemsComponents.MeleeStatsComponent;
import com.deco2800.game.components.CombatItemsComponents.WeaponStatsComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.ArmourFactory;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.entities.factories.WeaponFactory;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(GameExtension.class)
class InventoryComponentTest {

  @BeforeEach
  void beforeEach() {
    ServiceLocator.registerEntityService(new EntityService());
    ServiceLocator.registerPhysicsService(new PhysicsService());
  }

  @Test
  void createEmptyInventory() {
    InventoryComponent testInventory1 = new InventoryComponent();
    assertEquals(testInventory1.getInventory(), new ArrayList<>(16));
  }



  @Test
  void addItem() {
    InventoryComponent testInventory2 = new InventoryComponent();
    List<Entity> expectedList = new ArrayList<>(16);

    Entity testWeapon = WeaponFactory.createBaseWeapon();

    testInventory2.addItem(testWeapon);
    expectedList.add(testWeapon);

    assertEquals(testInventory2.getInventory(), expectedList);
  }

  @Test
  void removeItem() {
    InventoryComponent testInventory3 = new InventoryComponent();
    List<Entity> expectedList = new ArrayList<>(16);

    Entity testWeapon = WeaponFactory.createBaseWeapon();

    expectedList.add(testWeapon);
    testInventory3.addItem(testWeapon);

    expectedList.remove(testWeapon);
    testInventory3.removeItem(EntityTypes.WEAPON);

    assertEquals(testInventory3.getInventory(), expectedList);
  }
  @Test
  void equipItem() {
    InventoryComponent testInventory4 = new InventoryComponent();
    Entity testArmour = ArmourFactory.createBaseArmour();
    testInventory4.addItem(testArmour);
    testInventory4.equipItem(testArmour);

    PlayerModifier pmComponent =
            ServiceLocator.getGameArea().getPlayer().getComponent(PlayerModifier.class);
    MeleeStatsComponent meleeStats =
            (MeleeStatsComponent) testArmour.getComponent(WeaponStatsComponent.class);
     assertTrue(pmComponent.checkModifier(PlayerModifier.MOVESPEED,
             (float) (meleeStats.getWeight()), true
             , 0));
  }
}
