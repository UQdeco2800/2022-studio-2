package com.deco2800.game.components.player;

import com.badlogic.gdx.Game;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.components.CombatItemsComponents.PhyiscalWeaponStatsComponent;
import com.deco2800.game.components.DefensiveItemsComponents.ArmourStatsComponent;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.*;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;

import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class InventoryComponentTest {

  private static GameArea gameArea;

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

    assertFalse(inventory.hasItem(player, inventory.getInventory()));
    assertTrue(inventory.hasItem(testWeapon, inventory.getInventory()));
    assertTrue(inventory.hasItem(testArmour, inventory.getInventory()));
    assertTrue(inventory.hasItem(testPotion, inventory.getInventory()));
  }

  @Test
  void getItemIndex() {
    Entity player = PlayerFactory.createTestPlayer();
    Entity testWeapon = WeaponFactory.createTestDagger();
    Entity testArmour = ArmourFactory.createBaseArmour();
    int expectedWeapon = 0;
    int expectedArmour = 1;
    InventoryComponent inventory = player.getComponent(InventoryComponent.class);

    inventory.addItem(testWeapon);
    inventory.addItem(testArmour);

    assertEquals(expectedArmour, inventory.getItemIndex(testArmour, inventory.getInventory()));
    assertEquals(expectedWeapon, inventory.getItemIndex(testWeapon, inventory.getInventory()));
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
  void sortInventory() {
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

  @Test
  void removeItem() {
    InventoryComponent testInventory3 = new InventoryComponent();
    List<Entity> expectedList = new ArrayList<>(16);

    Entity testWeapon = WeaponFactory.createTestDagger();
    Entity testArmour = ArmourFactory.createBaseArmour();

    testInventory3.addItem(testWeapon);
    testInventory3.addItem(testArmour);

    testInventory3.removeItem(testWeapon);
    testInventory3.removeItem(0);

    assertEquals(expectedList, testInventory3.getInventory());
  }

  @Test
  void getItemQuantity() {
    InventoryComponent testInventory4 = new InventoryComponent();
    final int expectedQuantity = 1;

    Entity testWeapon = WeaponFactory.createTestDagger();

    testInventory4.addItem(testWeapon);

    assertEquals(expectedQuantity, testInventory4.getItemQuantity(testWeapon));
    assertEquals(expectedQuantity, testInventory4.getItemQuantity(0));
  }

  @Test
  void applyWeaponEffect() {

  }

  @Test
  void applyArmourEffect() {
      
  }

  @Test
  void getEquipable() {
//    Entity player = PlayerFactory.createTestPlayer();
//    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
//    Entity testWeapon = WeaponFactory.createTestDagger();
//
//    Entity animator = new Entity();
//    animator.getComponent(AnimationRenderComponent.class);
//    Entity entity = mock(Entity.class);
//    PlayerFactory playerFactory = mock(PlayerFactory.class);
//    ServiceLocator serviceLocator = mock(ServiceLocator.class);
//
//    when(playerFactory.createCombatAnimator(entity)).thenReturn(animator);
//    when(serviceLocator.getGameArea()).thenReturn(gameArea);
//
//    inventory.addItem(testWeapon);
//    inventory.equipItem(testWeapon);
//
//    assertEquals(testWeapon, inventory.getEquipable(0));
  }

  @Test
  void getEquipables() {
    Entity player = PlayerFactory.createTestPlayer();
    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    Entity[] expectedList = new Entity[2];

    assertArrayEquals(expectedList, inventory.getEquipables());
  }

  @Test
  void removeEquipable() {
    Entity player = PlayerFactory.createTestPlayer();
    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    Entity testWeapon = WeaponFactory.createTestDagger();
    Entity[] expectedList = new Entity[2];

    inventory.equipItem(testWeapon);
    inventory.removeEquipable(0);

    assertArrayEquals(expectedList, inventory.getEquipables());
  }


  @Test
  void equipItem() {
    /*
     have no idea why methods still report errors after they have been mocked
     */

//    Entity player = PlayerFactory.createTestPlayer();
//    Entity testWeapon = WeaponFactory.createTestWeapon("hera");
//    Entity testArmour = ArmourFactory.createBaseArmour();
//    Entity animator = new Entity();
//    animator.getComponent(AnimationRenderComponent.class);
//    Entity entity = mock(Entity.class);
//    PlayerFactory playerFactory = mock(PlayerFactory.class);
//    ServiceLocator serviceLocator = mock(ServiceLocator.class);
//
//    when(playerFactory.createCombatAnimator(entity)).thenReturn(animator);
//    when(serviceLocator.getGameArea()).thenReturn(gameArea);
//
//    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
//    PlayerModifier pmComponent = player.getComponent(PlayerModifier.class);
//
//    ArmourStatsComponent armourStats = testArmour.getComponent(ArmourStatsComponent.class);
//    PhyiscalWeaponStatsComponent meleeStats = testWeapon.getComponent(PhyiscalWeaponStatsComponent.class);
//
//    inventory.addItem(testWeapon);
//    inventory.addItem(testArmour);
//
//    inventory.equipItem(testArmour);
//    assertTrue(pmComponent.
//            checkModifier(PlayerModifier.MOVESPEED, (-(float)armourStats.getWeight()), true, 0));
//
//    inventory.equipItem(testWeapon);
//    assertTrue(pmComponent.
//            checkModifier(PlayerModifier.MOVESPEED, (float) (-meleeStats.getWeight() / 15), true, 0));
  }

  @Test
  void swapItem(){

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
  void toggleInventoryDisplay() {
    /*
    not working but trying to fix it.
    if anyone can make it, just delete all the comment :)
     */

////    ServiceLocator serviceLocator = spy(ServiceLocator.class);
//    ServiceLocator serviceLocator = mock(ServiceLocator.class);
//    Entity player = PlayerFactory.createTestPlayer();
//    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
//
//    GameAreaDisplay gameArea = mock(GameAreaDisplay.class);
////    GameAreaDisplay gameArea = new GameAreaDisplay("1");
//
////    when(serviceLocator.getInventoryArea()).thenReturn(gameArea);
//    doThrow(new Exception("null")).when(serviceLocator.getInventoryArea());
//
////    GameAreaDisplay gameArea1 = mock(GameAreaDisplay.class);
////    GameAreaDisplay gameArea2 = mock(GameAreaDisplay.class);
////    GameAreaDisplay gameArea3 = mock(GameAreaDisplay.class);
////
////    String gameAreaName1 = "one";
////    String gameAreaName2 = "two";
////    String gameAreaName3 = "three";
////    gameArea1.setGameAreaName(gameAreaName1);
////    gameArea2.setGameAreaName(gameAreaName2);
////    gameArea3.setGameAreaName(gameAreaName3);
////
////    when(serviceLocator.getInventoryArea()).thenReturn(gameArea1)
////            .thenReturn(gameArea2)
////            .thenReturn(gameArea3);
//
//    inventory.toggleInventoryDisplay();
//
//    verify(serviceLocator.getInventoryArea()).displayInventoryMenu();
  }

  @Test
  void getQuickBarItems() {
    Entity player = PlayerFactory.createTestPlayer();
    Entity testPotion = PotionFactory.createTestSpeedPotion();

    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    List<Entity> expectedList = new ArrayList<>(3);

    inventory.addQuickBarItems(testPotion);

    assertNotEquals(expectedList, inventory.getQuickBarItems());
  }

  @Test
  void itemEquals () {
    InventoryComponent inventory = new InventoryComponent();
    Entity testWeapon = WeaponFactory.createTestDagger();
    Entity testArmour = ArmourFactory.createBaseArmour();
    Entity testPotion = PotionFactory.createTestSpeedPotion();

    assertTrue(inventory.itemEquals(testArmour, testArmour));
    assertFalse(inventory.itemEquals(testWeapon, testArmour));
    assertFalse(inventory.itemEquals(testWeapon, testPotion));
    assertFalse(inventory.itemEquals(testArmour, testPotion));
  }

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
  void getPotionIndex() {
    Entity player = PlayerFactory.createTestPlayer();
    Entity testSpeedPotion = PotionFactory.createTestSpeedPotion();

    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    inventory.addQuickBarItems(testSpeedPotion);
    int expectedIndex =  0;

    assertEquals(expectedIndex, inventory.getPotionIndex(testSpeedPotion));
  }

  @Test
  void removePotion() {
    Entity player = PlayerFactory.createTestPlayer();
    Entity testSpeedPotion = PotionFactory.createTestSpeedPotion();

    InventoryComponent testInventory6 = player.getComponent(InventoryComponent.class);
    List<Entity> expectedList = new ArrayList<>(3);

    testInventory6.addQuickBarItems(testSpeedPotion);

    testInventory6.removePotion(testInventory6.getPotionIndex(testSpeedPotion));

    assertEquals(expectedList, testInventory6.getInventory());
  }

  @Test
  void consumePotion() {
    Entity player = PlayerFactory.createTestPlayer();
    Entity testPotion1 = PotionFactory.createTestSpeedPotion();
    List<Entity> expectedList = new ArrayList<>(3);

    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    PlayerModifier pmComponent = player.getComponent(PlayerModifier.class);

    inventory.addItem(testPotion1);
    inventory.addQuickBarItems(testPotion1);

    //Tests if the potion effect is applied to the player
    inventory.consumePotion(1);
    assertTrue(pmComponent.
            checkModifier(PlayerModifier.MOVESPEED, 1.5f, true, 3000));
    assertEquals(expectedList, inventory.getQuickBarItems());
  }
}
