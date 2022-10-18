package com.deco2800.game.components.player;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.components.DefensiveItemsComponents.ArmourStatsComponent;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.components.combatitemscomponents.PhysicalWeaponStatsComponent;
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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class InventoryComponentTest {


  @BeforeEach
  void beforeEach() {
    ForestGameArea fga = mock(ForestGameArea.class);
    ServiceLocator.registerGameArea(fga);
    ServiceLocator.registerEntityService(new EntityService());
    ServiceLocator.registerPhysicsService(new PhysicsService());
    ServiceLocator.registerInputService(new InputService());
    ServiceLocator.registerRenderService(new RenderService());
    ResourceService resourceService = new ResourceService();
    ServiceLocator.registerResourceService(resourceService);
    String[] textures = {"images/CombatItems/Sprint-1/Level 2 Dagger 1.png",
                        "images/CombatItems/Sprint-1/Level 2 Dagger 2png.png",
                        "images/CombatItems/Sprint-1/Enemy_dumbbell.png",
                        "images/CombatItems/Sprint-1/Level 2 Dagger 2png.png",
                        "images/CombatItems/Sprint-1/Sword_Lvl2.png",
                        "images/Crafting-assets-sprint1/materials/gold.png",
                        "images/Crafting-assets-sprint1/materials/rainbow_poop.png",
                        "images/Crafting-assets-sprint1/materials/iron.png",
                        "images/Crafting-assets-sprint1/materials/toilet_paper.png",
                        "images/Crafting-assets-sprint1/materials/steel.png",
                        "images/Crafting-assets-sprint1/materials/wood.png",
                        "images/Crafting-assets-sprint1/materials/plastic.png",
                        "images/Crafting-assets-sprint1/materials/rubber.png",
                        "images/Crafting-assets-sprint1/materials/platinum.png",
                        "images/Crafting-assets-sprint1/materials/silver.png",
                        "images/Potions/agility_potion.png",
                        "images/Potions/health_potion.png",
                        "images/Potions/defence_potion.png",
                        "images/Potions/swiftness_potion.png",
                        "images/Armour-assets-sprint2/Dark_Armour.png",
                        "images/Armour-assets-sprint2/slowDiamond.png",
                        "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/goldenBowPlunger.png"};
    resourceService.loadTextures(textures);
    String[] textureAtlases = {"images/CombatItems/animations/combatItemsAnimation.atlas"};
    resourceService.loadTextureAtlases(textureAtlases);
    resourceService.loadAll();

    GameArea gameArea = spy(GameArea.class);
    RenderService renderService = new RenderService();
    renderService.setStage(mock(Stage.class));
    GameAreaDisplay inventoryArea = new GameAreaDisplay("");
    ServiceLocator.registerGameArea(gameArea);
    ServiceLocator.registerRenderService(renderService);
    ServiceLocator.registerInventoryArea(inventoryArea);
    inventoryArea.create();
  }

  @Test
  void createEmptyInventory() {
    InventoryComponent testInventory1 = new InventoryComponent();
    assertEquals( new ArrayList<>(16), testInventory1.getInventory());
  }

  @Test
  void setCombatAnimator() {
    InventoryComponent inventory = new InventoryComponent();
    Entity combatAnimator = new InventoryComponent().getEntity();
    inventory.setCombatAnimator(combatAnimator);
    assertEquals(inventory.getEntity(), combatAnimator);
  }

  @Test
  void registerAnimation() {
    Entity player = PlayerFactory.createTestPlayer();
    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    Entity combatAnimator = mock(Entity.class);
    inventory.setCombatAnimator(combatAnimator);

    Entity weapon = WeaponFactory.createHera();
    Entity goldPlungerBow = WeaponFactory.createGoldenPlungerBow();

    inventory.registerAnimation(weapon);
    inventory.registerAnimation(goldPlungerBow);
  }

  @Test
  void removeEquipable(){
    Entity player = PlayerFactory.createTestPlayer();
    Entity testWeapon = WeaponFactory.createTestWeapon("hera");
    Entity testArmour = ArmourFactory.createBaseArmour();
    Entity notAddedWeapon = WeaponFactory.createDagger();
    Entity wood = MaterialFactory.createWood();

    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    inventory.addItem(testWeapon);
    inventory.equipItem(testWeapon);
    assertTrue(inventory.removeEquipable(0));
    assertFalse(inventory.removeEquipable(1));
  }
  @Test
  void cancelAnimation() {
    Entity player = PlayerFactory.createTestPlayer();
    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    Entity combatAnimator = mock(Entity.class);

    inventory.cancelAnimation();

    AnimationRenderComponent animator = new AnimationRenderComponent(
            ServiceLocator.getResourceService().getAsset(
                    "images/CombatItems/animations/combatItemsAnimation.atlas", TextureAtlas.class));
    animator.addAnimation("athena", 0.1f);
    combatAnimator.addComponent(animator);

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
    Entity testSword = WeaponFactory.createSwordLvl2();
    Entity testDumbbell = WeaponFactory.createDumbbell();
    Entity testHera = WeaponFactory.createHera();
    Entity testArmour = ArmourFactory.createBaseArmour();
    Entity testSpeedPotion = PotionFactory.createTestSpeedPotion();
    Entity testHealthPotion = PotionFactory.createTestHealthPotion();
    Entity gold = MaterialFactory.createGold();
    Entity iron = MaterialFactory.createIron();
    Entity steel = MaterialFactory.createSteel();
    Entity wood = MaterialFactory.createWood();
    Entity plastic = MaterialFactory.createPlastic();
    Entity rubber = MaterialFactory.createRubber();
    Entity platinum = MaterialFactory.createPlatinum();
    Entity silver = MaterialFactory.createSilver();
    Entity poop = MaterialFactory.createPoop();
    Entity toiletPaper = MaterialFactory.createToiletPaper();

    expectedList.add(testWeapon);
    expectedList.add(testSword);
    expectedList.add(testDumbbell);
    expectedList.add(testHera);
    expectedList.add(testArmour);
    expectedList.add(testSpeedPotion);
    expectedList.add(testHealthPotion);
    expectedList.add(gold);
    expectedList.add(iron);
    expectedList.add(steel);
    expectedList.add(wood);
    expectedList.add(plastic);
    expectedList.add(rubber);
    expectedList.add(platinum);
    expectedList.add(silver);
    expectedList.add(poop);
    expectedList.add(toiletPaper);
  //Test case 1 adding player to inventory should do nothing
    inventory.addItem(player);
    for (Entity entity : expectedList) inventory.addItem(entity);
    for (int i = 0; i < 9; i++) inventory.addItem(testSpeedPotion);

    expectedList.remove(toiletPaper);
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
    Entity testPotion = PotionFactory.createTestHealthPotion();

    testInventory3.removeItem(testWeapon);

    testInventory3.addItem(testWeapon);
    testInventory3.addItem(testArmour);

    testInventory3.removeItem(testWeapon);
    testInventory3.removeItem(0);

    testInventory3.removeItem(testWeapon);
    testInventory3.removeItem(0);
    testInventory3.removeItem(testPotion);

    assertEquals(expectedList, testInventory3.getInventory());
  }

  @Test
  void removeItem2() {
    InventoryComponent testInventory3 = new InventoryComponent();
    Entity iron = MaterialFactory.createIron();

    testInventory3.removeItem(EntityTypes.IRON);

    testInventory3.addItem(iron);
    testInventory3.removeItem(EntityTypes.IRON);
    assertFalse(testInventory3.hasItem(iron, testInventory3.getInventory()));

    //Should do nothing
    testInventory3.addItem(iron);
    testInventory3.removeItem(EntityTypes.WOOD);
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
    Entity player = PlayerFactory.createTestPlayer();
    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    Entity testWeapon = WeaponFactory.createDagger();
    Entity testBaseWeapon = WeaponFactory.createBaseWeapon();

    PlayerModifier pmComponent = player.getComponent(PlayerModifier.class);
    PhysicalWeaponStatsComponent stat = testWeapon.getComponent(PhysicalWeaponStatsComponent.class);
    inventory.applyWeaponEffect(testWeapon, true);
    inventory.applyWeaponEffect(testBaseWeapon, true);
    assertTrue(pmComponent.checkModifier(PlayerModifier.MOVESPEED, (float) (-stat.getWeight() / 15), true, 0));

    inventory.applyWeaponEffect(testWeapon, false);
    inventory.applyWeaponEffect(testBaseWeapon, false);
    assertTrue(pmComponent.checkModifier(PlayerModifier.MOVESPEED, 3 * (float) (stat.getWeight() / 15) , false, 0));
  }

  @Test
  void applyArmourEffect() {
    Entity player = PlayerFactory.createTestPlayer();
    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    Entity testBaseArmour = ArmourFactory.createBaseArmour();
    Entity testArmour = ArmourFactory.createTestArmour();

    PlayerModifier pmComponent = player.getComponent(PlayerModifier.class);

    inventory.applyArmourEffect(testBaseArmour, true);
    inventory.applyArmourEffect(testArmour, true);
    assertTrue(pmComponent.checkModifier(PlayerModifier.MOVESPEED, 0, false,0));

    inventory.applyArmourEffect(testBaseArmour, false);
    inventory.applyArmourEffect(testArmour, false);
    assertTrue(pmComponent.checkModifier(PlayerModifier.MOVESPEED, 0, false, 0));
  }

  @Test
  void getEquipable() {
    Entity player = PlayerFactory.createTestPlayer();
    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    Entity testWeapon = WeaponFactory.createTestDagger();
    inventory.addItem(testWeapon);
    inventory.equipItem(testWeapon);

    assertEquals(testWeapon, inventory.getEquipable(0));
  }

  @Test
  void getEquipables() {
    Entity player = PlayerFactory.createTestPlayer();
    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    Entity[] expectedList = new Entity[2];

    assertArrayEquals(expectedList, inventory.getEquipables());
  }

  @Test
  void equipItem() {
    Entity player = PlayerFactory.createTestPlayer();
    Entity testWeapon = WeaponFactory.createTestWeapon("hera");
    Entity testArmour = ArmourFactory.createBaseArmour();
    Entity notAddedWeapon = WeaponFactory.createDagger();
    Entity wood = MaterialFactory.createWood();
    List<Entity> expectedList = new ArrayList<>(16);

    Entity animator = new Entity();
    animator.getComponent(AnimationRenderComponent.class);
    Entity entity = mock(Entity.class);
    PlayerFactory playerFactory = mock(PlayerFactory.class);
    ServiceLocator serviceLocator = mock(ServiceLocator.class);

    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    PlayerModifier pmComponent = player.getComponent(PlayerModifier.class);

    ArmourStatsComponent armourStats = testArmour.getComponent(ArmourStatsComponent.class);
    PhysicalWeaponStatsComponent meleeStats = testWeapon.getComponent(PhysicalWeaponStatsComponent.class);

    inventory.addItem(wood);
    assertFalse(inventory.equipItem(wood));
    inventory.removeItem(wood);

    inventory.addItem(testArmour);
    inventory.equipItem(testArmour);
    assertTrue(pmComponent.checkModifier(PlayerModifier.MOVESPEED, 0, false,0));
    assertEquals(expectedList, inventory.getInventory());

    inventory.addItem(testWeapon);
    inventory.equipItem(testWeapon);
    assertTrue(pmComponent.
            checkModifier(PlayerModifier.MOVESPEED, (float) (-meleeStats.getWeight() / 15), true, 0));


    assertFalse(inventory.equipItem(notAddedWeapon));

    inventory.addItem(notAddedWeapon);
    assertTrue(inventory.equipItem(notAddedWeapon));
    inventory.equipItem(testWeapon);
  }

  @Test
  void swapItem(){
    Entity player = PlayerFactory.createTestPlayer();
    Entity testWeapon = WeaponFactory.createTestWeapon("hera");
    Entity testArmour = ArmourFactory.createBaseArmour();
    Entity slowDiamond = ArmourFactory.createArmour(ArmourFactory.ArmourType.slowDiamond);
    Entity dumbbell = WeaponFactory.createDumbbell();
    Entity wood = MaterialFactory.createWood();

    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    PlayerModifier pmComponent = player.getComponent(PlayerModifier.class);

    ArmourStatsComponent armourStats = slowDiamond.getComponent(ArmourStatsComponent.class);
    PhysicalWeaponStatsComponent meleeStats = dumbbell.getComponent(PhysicalWeaponStatsComponent.class);

    inventory.addItem(testWeapon);
    inventory.addItem(testArmour);
    inventory.addItem(dumbbell);

    inventory.swapItem(testWeapon);
    assertFalse(pmComponent.
            checkModifier(PlayerModifier.MOVESPEED, (float) (-meleeStats.getWeight() / 15), true, 0));
    inventory.equipItem(dumbbell);

    assertTrue(pmComponent.
            checkModifier(PlayerModifier.MOVESPEED, (float) (-meleeStats.getWeight() / 15), true, 0));
    inventory.swapItem(slowDiamond);
    assertFalse(pmComponent.checkModifier(PlayerModifier.MOVESPEED, 5, true,0));
    inventory.equipItem(testArmour);
    
    inventory.swapItem(slowDiamond);
    assertFalse(pmComponent.checkModifier(PlayerModifier.MOVESPEED, (float)-5 / 10, true,0));

  }


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

    //Test case 5 unequiping slot with no item
    assertFalse(inventory.unequipItem(0));

    //Test case 6: unequip while inventory is full
    Entity testSword = WeaponFactory.createSwordLvl2();
    Entity testDumbbell = WeaponFactory.createDumbbell();
    Entity testHera = WeaponFactory.createHera();
    Entity testSpeedPotion = PotionFactory.createTestSpeedPotion();
    Entity testHealthPotion = PotionFactory.createTestHealthPotion();
    Entity staminaPotion = PotionFactory.createStaminaPotion();
    Entity gold = MaterialFactory.createGold();
    Entity iron = MaterialFactory.createIron();
    Entity steel = MaterialFactory.createSteel();
    Entity wood = MaterialFactory.createWood();
    Entity plastic = MaterialFactory.createPlastic();
    Entity rubber = MaterialFactory.createRubber();
    Entity platinum = MaterialFactory.createPlatinum();
    Entity silver = MaterialFactory.createSilver();
    Entity poop = MaterialFactory.createPoop();
    Entity toiletPaper = MaterialFactory.createToiletPaper();
    inventory.addItem(testSword);
    inventory.addItem(testDumbbell);
    inventory.addItem(testHera);
    inventory.addItem(testSpeedPotion);
    inventory.addItem(testHealthPotion);
    inventory.addItem(gold);
    inventory.addItem(iron);
    inventory.addItem(steel);
    inventory.addItem(wood);
    inventory.addItem(plastic);
    inventory.addItem(rubber);
    inventory.addItem(platinum);
    inventory.addItem(silver);
    inventory.addItem(poop);
    inventory.addItem(toiletPaper);
    inventory.equipItem(testWeapon);
    inventory.addItem(staminaPotion);
    inventory.unequipItem(0);
  }


  @Test
  void toggleInventoryDisplay() {

    Entity player = PlayerFactory.createTestPlayer();
    ServiceLocator serviceLocator = mock(ServiceLocator.class);

    when(serviceLocator.getGameArea().getPlayer()).thenReturn(player);

    InventoryComponent inventory = spy(InventoryComponent.class);
    inventory.toggleInventoryDisplay();
    inventory.toggleInventoryDisplay();

    verify(inventory, times(2)).toggleInventoryDisplay();
  }

  /**
   * Checks whether the quickbar items are what they are expected to be when items are added to
   * the quickbar
   */
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
  void itemEquals() {
    InventoryComponent inventory = new InventoryComponent();
    Entity testWeapon = WeaponFactory.createTestDagger();
    Entity testArmour = ArmourFactory.createBaseArmour();
    Entity testPotion = PotionFactory.createTestSpeedPotion();

    assertTrue(inventory.itemEquals(testArmour, testArmour));
    assertFalse(inventory.itemEquals(testWeapon, testArmour));
    assertFalse(inventory.itemEquals(testWeapon, testPotion));
    assertFalse(inventory.itemEquals(testArmour, testPotion));
  }

  /**
   * Checks that the item has correctly been added to the quickbar
   */
  @Test
  void addQuickBarItems() {
    Entity player = PlayerFactory.createTestPlayer();
    Entity testPotion = PotionFactory.createTestSpeedPotion();
    Entity staminaPotion = PotionFactory.createStaminaPotion();
    Entity healthPotion = PotionFactory.createHealthPotion();
    Entity defencePotion = PotionFactory.createDefencePotion();

    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    List<Entity> expectedList = new ArrayList<>(3);
    List<Entity> expectedInventory = new ArrayList<>(16);
    for (int i = 0; i < 9; ++i) inventory.addQuickBarItems(testPotion);
    expectedList.add(testPotion);

    assertFalse(inventory.addQuickBarItems(testPotion));

    assertEquals(expectedList, inventory.getQuickBarItems());
    assertEquals(expectedInventory, inventory.getInventory());

    inventory.addQuickBarItems(staminaPotion);

    inventory.addQuickBarItems(healthPotion);

    assertFalse(inventory.addQuickBarItems(defencePotion));
  }

  /**
   * Tests whether the potion is at the correct place in the quickbar so that it may be used
   * correctly with hotkeys
   */
  @Test
  void getPotionIndex() {
    Entity player = PlayerFactory.createTestPlayer();
    Entity testSpeedPotion = PotionFactory.createTestSpeedPotion();
    Entity healthPotion = PotionFactory.createHealthPotion();
    int expectedSentinel = -1;
    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    assertEquals(-1, inventory.getPotionIndex(testSpeedPotion));
    inventory.addQuickBarItems(testSpeedPotion);
    int expectedIndex =  0;

    assertEquals(expectedIndex, inventory.getPotionIndex(testSpeedPotion));
    assertEquals(expectedSentinel, inventory.getPotionIndex(healthPotion));
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
  void setPotionQuantity() {
    Entity player = PlayerFactory.createTestPlayer();
    Entity testPotion1 = PotionFactory.createTestSpeedPotion();
    Entity speedPotion = PotionFactory.createSpeedPotion();
    int[] expectedList = new int[3];
    int expectedQuantity = 3;
    expectedList[0] = expectedQuantity;
    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    inventory.setPotionQuantity(0, expectedQuantity);
    //Edge cases input > threshold
    inventory.setPotionQuantity(0, 10);
    inventory.setPotionQuantity(3, expectedQuantity);
    assertArrayEquals(expectedList, inventory.getQuickBarQuantity());
  }

  /**
   * This test checks that when a potion is consumed that it is removed from the quickbar and
   * applies the intended effect to the player
   */
  @Test
  void consumePotion() {
    Entity player = PlayerFactory.createTestPlayer();
    Entity testPotion1 = PotionFactory.createTestSpeedPotion();
    Entity speedPotion = PotionFactory.createSpeedPotion();
    List<Entity> expectedList = new ArrayList<>(3);
    int expectedQuantity = 8;

    InventoryComponent inventory = player.getComponent(InventoryComponent.class);
    PlayerModifier pmComponent = player.getComponent(PlayerModifier.class);

    inventory.addItem(testPotion1);
    inventory.addQuickBarItems(testPotion1);

    //Tests if the potion effect is applied to the player
    inventory.consumePotion(1);
    //Test if the function has properly ended or not
    inventory.consumePotion(5);

    assertTrue(pmComponent.
            checkModifier(PlayerModifier.MOVESPEED, 1.5f, true, 3000));
    assertEquals(expectedList, inventory.getQuickBarItems());

    for (int i = 0; i < 9; ++i) {
      inventory.addItem(testPotion1);
      inventory.addQuickBarItems(speedPotion);
    }

    inventory.consumePotion(1);
    assertEquals(expectedQuantity, inventory.getQuickBarQuantity()[inventory.getPotionIndex(speedPotion)]);
    inventory.setPotionQuantity(0, -1);
    inventory.consumePotion(1);
  }
}
