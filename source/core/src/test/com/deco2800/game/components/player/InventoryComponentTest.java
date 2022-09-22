package com.deco2800.game.components.player;

import com.deco2800.game.components.CombatItemsComponents.MeleeStatsComponent;
import com.deco2800.game.components.CombatItemsComponents.WeaponStatsComponent;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.DefensiveItemsComponents.ArmourStatsComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.*;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ResourceService;
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
    ServiceLocator.registerInputService(new InputService());
    ServiceLocator.registerResourceService(new ResourceService());
    ServiceLocator.registerRenderService(new RenderService());
  }

  @Test
  void createEmptyInventory() {
    InventoryComponent testInventory1 = new InventoryComponent();
    assertEquals(testInventory1.getInventory(), new ArrayList<>(16));
  }

  @Test
  void hasItem() {
    Entity player = PlayerFactory.createTestPlayer();
    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    Entity testWeapon = WeaponFactory.createTestDagger();
    Entity testArmour = ArmourFactory.createBaseArmour();
    Entity testPotion = PotionFactory.createTestSpeedPotion();

    inventory.addItem(testWeapon);
    inventory.addItem(testArmour);
    inventory.addItem(testPotion);

    assertTrue(inventory.hasItem(testWeapon, inventory.getInventory()));
    assertTrue(inventory.hasItem(testArmour, inventory.getInventory()));
    assertTrue(inventory.hasItem(testPotion, inventory.getInventory()));
  }

  @Test
  void addItem() {
    Entity player = PlayerFactory.createTestPlayer();
    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    List<Entity> expectedList = new ArrayList<>(16);

    Entity testWeapon = WeaponFactory.createTestDagger();
    Entity testArmour = ArmourFactory.createBaseArmour();
    Entity testPotion = PotionFactory.createTestSpeedPotion();

    inventory.addItem(testWeapon);
    inventory.addItem(testArmour);
    inventory.addItem(testPotion);
    expectedList.add(testWeapon);
    expectedList.add(testArmour);
    expectedList.add(testPotion);

    assertEquals(inventory.getInventory(), expectedList);
  }

  @Test
  void removeItem() {
    InventoryComponent testInventory3 = new InventoryComponent();
    List<Entity> expectedList = new ArrayList<>(16);

    Entity testWeapon = WeaponFactory.createTestDagger();

    expectedList.add(testWeapon);
    testInventory3.addItem(testWeapon);

    expectedList.remove(testWeapon);
    testInventory3.removeItem(EntityTypes.WEAPON);

    assertEquals(testInventory3.getInventory(), expectedList);
  }

  @Test
  void getItemQuantity() {
    InventoryComponent testInventory4 = new InventoryComponent();
    int expectedQuantity = 1;

    Entity testWeapon = WeaponFactory.createTestDagger();

    testInventory4.addItem(testWeapon);

    assertEquals(testInventory4.getItemQuantity(testWeapon), expectedQuantity);
  }

  @Test
  void equipItem() {
    Entity testArmour = ArmourFactory.createBaseArmour();
    Entity player = PlayerFactory.createTestPlayer();
    ArmourStatsComponent armourStats = testArmour.getComponent(ArmourStatsComponent.class);

    player.getComponent(InventoryComponent.class).addItem(testArmour);
    player.getComponent(InventoryComponent.class).equipItem(testArmour);

    PlayerModifier pmComponent =
            player.getComponent(PlayerModifier.class);

     assertTrue(pmComponent.
             checkModifier(PlayerModifier.MOVESPEED, (-(float)armourStats.getWeight()), true, 0));
  }

  @Test
  void addQuickBarItems() {
    Entity player = PlayerFactory.createTestPlayer();
    List<Entity> expectedList = new ArrayList<>(3);

    Entity testPotion = PotionFactory.createTestSpeedPotion();

    player.getComponent(InventoryComponent.class).addQuickBarItems(testPotion);
    expectedList.add(testPotion);

    assertEquals(player.getComponent(InventoryComponent.class).getQuickBarItems(), expectedList);
  }

  @Test
  void removePotion() {
    Entity player = PlayerFactory.createTestPlayer();
    InventoryComponent testInventory6 = player.getComponent(InventoryComponent.class);
    List<Entity> expectedList = new ArrayList<>(3);

    Entity testSpeedPotion = PotionFactory.createTestSpeedPotion();

    expectedList.add(testSpeedPotion);
    testInventory6.addQuickBarItems(testSpeedPotion);

    expectedList.remove(testSpeedPotion);
    testInventory6.removePotion(testInventory6.getPotionIndex(testSpeedPotion));

    assertEquals(testInventory6.getInventory(), expectedList);
  }

//  @Test
//  void consumePotion() {
//    Entity player = PlayerFactory.createTestPlayer();
//    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
//    PlayerModifier pmComponent = player.getComponent(PlayerModifier.class);
//    List<Entity> expectedList = new ArrayList<>(3);
//
//    Entity testPotion1 = PotionFactory.createTestSpeedPotion();
//
//    inventory.addItem(testPotion1);
//    inventory.addQuickBarItems(testPotion1);
//    inventory.consumePotion(inventory.getPotionIndex(testPotion1));
//
//    assertEquals(inventory.getQuickBarItems(), expectedList);
//    assertTrue(pmComponent.
//            checkModifier(PlayerModifier.MOVESPEED, 1.5f, true, 3000));
//  }
}
