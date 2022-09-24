package com.deco2800.game.components.player;

import com.deco2800.game.components.CombatItemsComponents.MeleeStatsComponent;
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
    assertEquals( new ArrayList<>(16), testInventory1.getInventory());
  }

  @Test
  void hasItem() {
    Entity player = PlayerFactory.createTestPlayer();
    Entity testWeapon = WeaponFactory.createTestDagger();
    Entity testArmour = ArmourFactory.createBaseArmour();
    Entity testPotion = PotionFactory.createTestSpeedPotion();

    InventoryComponent inventory = player.getComponent(InventoryComponent.class);

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

    assertEquals(expectedList, inventory.getInventory());
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

    assertEquals(expectedList, testInventory3.getInventory());
  }

  @Test
  void getItemQuantity() {
    InventoryComponent testInventory4 = new InventoryComponent();
    final int expectedQuantity = 1;

    Entity testWeapon = WeaponFactory.createTestDagger();

    testInventory4.addItem(testWeapon);

    assertEquals(expectedQuantity, testInventory4.getItemQuantity(testWeapon));
  }

  @Test
  void equipItem() {
    Entity player = PlayerFactory.createTestPlayer();
    Entity testWeapon = WeaponFactory.createTestWeapon("Dumbbell");
    Entity testArmour = ArmourFactory.createBaseArmour();

    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    PlayerModifier pmComponent = player.getComponent(PlayerModifier.class);

    ArmourStatsComponent armourStats = testArmour.getComponent(ArmourStatsComponent.class);
    MeleeStatsComponent meleeStats = testWeapon.getComponent(MeleeStatsComponent.class);

    inventory.addItem(testWeapon);
    inventory.addItem(testArmour);

    inventory.equipItem(testArmour);
    assertTrue(pmComponent.
            checkModifier(PlayerModifier.MOVESPEED, (-(float)armourStats.getWeight()), true, 0));

    inventory.equipItem(testWeapon);
    assertTrue(pmComponent.
            checkModifier(PlayerModifier.MOVESPEED, (float) (-meleeStats.getWeight() / 15), true, 0));
  }
  /** Currently not working since mock is not implemented
  @Test
  void unequip() {
    Entity player = PlayerFactory.createTestPlayer();
    Entity testWeapon = WeaponFactory.createTestWeapon("Dumbbell");
    Entity testArmour = ArmourFactory.createBaseArmour();

    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    PlayerModifier pmComponent = player.getComponent(PlayerModifier.class);

    inventory.addItem(testWeapon);
    inventory.addItem(testArmour);

    final float refSpeed = pmComponent.getReference(PlayerModifier.MOVESPEED);

    //Test case 1 and 2: unequip single item armour/weapon
    inventory.equipItem(testArmour);
    inventory.unequipItem(1);
    assertEquals(refSpeed, pmComponent.getModified(PlayerModifier.MOVESPEED));

    inventory.equipItem(testWeapon);
    inventory.unequipItem(0);
    assertEquals(refSpeed, pmComponent.getModified(PlayerModifier.MOVESPEED));

    //Test case 3: unequip single item while equipping multiple items
    inventory.equipItem(testArmour);
    inventory.equipItem(testWeapon);
    inventory.unequipItem(0);
    assertEquals(refSpeed, pmComponent.getModified(PlayerModifier.MOVESPEED));

    //Test case 4: unequip all items equipped while all item slots are full
    inventory.equipItem(testArmour);
    inventory.equipItem(testWeapon);
    inventory.unequipItem(1);
    inventory.unequipItem(0);
    assertEquals(refSpeed, pmComponent.getModified(PlayerModifier.MOVESPEED));

    //Test case 5: unequip while inventory is full
    //Currently unavailable since the total number of items existing in this game is < 16
  }
   */

  @Test
  void addQuickBarItems() {
    Entity player = PlayerFactory.createTestPlayer();
    Entity testPotion = PotionFactory.createTestSpeedPotion();

    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    List<Entity> expectedList = new ArrayList<>(3);

    inventory.addQuickBarItems(testPotion);
    expectedList.add(testPotion);

    assertEquals(expectedList, inventory.getQuickBarItems());
  }

  @Test
  void removePotion() {
    Entity player = PlayerFactory.createTestPlayer();
    Entity testSpeedPotion = PotionFactory.createTestSpeedPotion();

    InventoryComponent testInventory6 = player.getComponent(InventoryComponent.class);
    List<Entity> expectedList = new ArrayList<>(3);

    expectedList.add(testSpeedPotion);
    testInventory6.addQuickBarItems(testSpeedPotion);

    expectedList.remove(testSpeedPotion);
    testInventory6.removePotion(testInventory6.getPotionIndex(testSpeedPotion));

    assertEquals(expectedList, testInventory6.getInventory());
  }

  @Test
  void consumePotion() {
    Entity player = PlayerFactory.createTestPlayer();
    Entity testPotion1 = PotionFactory.createTestSpeedPotion();

    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    PlayerModifier pmComponent = player.getComponent(PlayerModifier.class);

    inventory.addItem(testPotion1);
    inventory.addQuickBarItems(testPotion1);

    //Tests if the potion effect is applied to the player
    inventory.consumePotion(1);
    assertTrue(pmComponent.
            checkModifier(PlayerModifier.MOVESPEED, 1.5f, true, 3000));
  }

  @Test
  void testSortingAlgorithm() {
    Entity player = PlayerFactory.createTestPlayer();
    Entity testWeapon = WeaponFactory.createTestDagger();
    Entity testPotion = PotionFactory.createTestSpeedPotion();
    final int expectQuantity = 4;

    InventoryComponent inventory = player.getComponent(InventoryComponent.class);

    inventory.addItem(testWeapon);
    //Add 4 potions to the inventory
    for (int i = 0; i < 4; ++i) inventory.addItem(testPotion);

    inventory.removeItem(testWeapon);
    inventory.getItemQuantity(testPotion);
    assertEquals(expectQuantity, inventory.getItemQuantity(testPotion));
  }



}
