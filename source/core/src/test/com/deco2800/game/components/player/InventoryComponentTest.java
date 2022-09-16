package com.deco2800.game.components.player;

import com.deco2800.game.components.CombatItemsComponents.MeleeStatsComponent;
import com.deco2800.game.components.CombatItemsComponents.WeaponStatsComponent;
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
  void getItemQuantity() {
    InventoryComponent testInventory4 = new InventoryComponent();
    int expectedQuantity = 1;

    Entity testWeapon = WeaponFactory.createBaseWeapon();

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
             checkModifier(PlayerModifier.MOVESPEED, (float)armourStats.getWeight(), true, 0));
  }

  @Test
  void getPotionIndex() {
    Entity player = PlayerFactory.createTestPlayer();
    int expectedNumber = 0;

    Entity testPotion = PotionFactory.createTestSpeedPotion();

    player.getComponent(InventoryComponent.class).addQuickBarItems(testPotion);

    assertEquals(player.getComponent(InventoryComponent.class).getPotionIndex(testPotion), expectedNumber);
  }

    @Test
  void getPotion() {
    Entity player = PlayerFactory.createTestPlayer();

    Entity testPotion = PotionFactory.createTestSpeedPotion();

    player.getComponent(InventoryComponent.class).addQuickBarItems(testPotion);

    assertEquals(player.getComponent(InventoryComponent.class).getPotion(testPotion), testPotion);
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

//  @Test
//  void removePotion() {
//    InventoryComponent testInventory6 = new InventoryComponent();
//    List<Entity> expectedList = new ArrayList<>(16);
//
//    Entity testPotion = PotionFactory.createBasePotion();
//
//    expectedList.add(testPotion);
//    testInventory6.addItem(testPotion);
//
//    expectedList.remove(testPotion);
//    testInventory6.removePotion();
//
//    assertEquals(testInventory6.getInventory(), expectedList);
//  }
//
//  @Test
//  void consumePotion() {
//    InventoryComponent testInventory7 = new InventoryComponent();
//    List<Entity> expectedList = new ArrayList<>(16);
//
//    Entity testPotion1 = PotionFactory.createBasePotion();
//
//    expectedList.add(testPotion1);
//    testInventory7.addItem(testPotion1);
//
//    expectedList.add(testPotion1);
//    testInventory7.addItem(testPotion1);
//
//    expectedList.remove(testPotion1);
//    testInventory7.consumePotion();
//
//    assertEquals(testInventory7.getInventory(), expectedList);
//  }
}
