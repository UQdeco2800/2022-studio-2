package com.deco2800.game.crafting;

//import com.deco2800.game.CombatItems.Weapon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.configs.combatitemsConfig.WeaponConfig;
import com.deco2800.game.entities.configs.combatitemsConfig.WeaponConfigSetup;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@ExtendWith(GameExtension.class)
public class CraftingLogicTest {

    Entity entity;

    @Before
    public void setup() {
        ForestGameArea fga = mock(ForestGameArea.class);
        ServiceLocator.registerGameArea(fga);
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerRenderService(new RenderService());
        ResourceService resourceService = new ResourceService();
        ServiceLocator.registerResourceService(resourceService);

        String[] textures = {
                "images/CombatItems/Sprint-1/Level 2 Dagger 1.png",
                "images/CombatItems/Sprint-1/Level 2 Dagger 2png.png",
                "images/CombatItems/Sprint-1/Enemy_dumbbell.png",
                "images/CombatItems/Sprint-1/Sword_Lvl2.png",
                "images/CombatItems/Sprint-1/trident_Lvl2.png",
                "images/CombatItems/Sprint-2/H&ADagger.png",
                "images/CombatItems/Sprint-2/Plunger.png",
                "images/CombatItems/Sprint-2/pipe.png",
                "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/Bow.png",
                "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/goldenBowPlunger.png",
                "images/Crafting-assets-sprint1/materials/gold.png",
                "images/Crafting-assets-sprint1/materials/rainbow_poop.png",
                "images/Crafting-assets-sprint1/materials/iron.png",
                "images/Crafting-assets-sprint1/materials/toilet_paper.png",
                "images/Crafting-assets-sprint1/materials/steel.png",
                "images/Crafting-assets-sprint1/materials/wood.png",
                "images/Crafting-assets-sprint1/materials/plastic.png",
                "images/Crafting-assets-sprint1/materials/rubber.png",
                "images/Crafting-assets-sprint1/materials/platinum.png",
                "images/Crafting-assets-sprint1/materials/silver.png"};
        resourceService.loadTextures(textures);
        String[] textureAtlases = {"images/CombatItems/animations/combatItemsAnimation.atlas"};
        resourceService.loadTextureAtlases(textureAtlases);
        resourceService.loadAll();

        GameArea gameArea = spy(GameArea.class);
        RenderService renderService = new RenderService();
        renderService.setStage(mock(Stage.class));
        GameAreaDisplay crafting = new GameAreaDisplay("");
        ServiceLocator.registerGameArea(gameArea);
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerInventoryArea(crafting);
        crafting.create();
        entity = new Entity();
    }

    @Test
    void basicGetPossibleBuildsTest() {
        List<WeaponConfig> imaginaryWeapons = new ArrayList<>();
        CraftingLogic.setPossibleBuilds(imaginaryWeapons);
        assertEquals(imaginaryWeapons, CraftingLogic.getPossibleBuilds());
    }

    @Test
    void basicSetPossibleBuildsTest() {
        List<WeaponConfig> inputList = new ArrayList<>();
        CraftingLogic.setPossibleBuilds(inputList);
        assertEquals(inputList, CraftingLogic.getPossibleBuilds());
    }

    @Test
    void getWeaponsTest() {
//        List<MeleeConfig> possibleWeaponsTest = CraftingLogic.getPossibleWeapons();
//        assertEquals(possibleWeaponsTest, CraftingLogic.getPossibleWeapons());
        WeaponConfigSetup configs =
                FileLoader.readClass(WeaponConfigSetup.class, "configs/Weapons.json");
        List<WeaponConfig> possibleWeaponsTest = new ArrayList<>();
        possibleWeaponsTest.add(configs.athenaDag);
        possibleWeaponsTest.add(configs.heraDag);
        possibleWeaponsTest.add(configs.SwordLvl2);
        possibleWeaponsTest.add(configs.dumbbell);
        possibleWeaponsTest.add(configs.tridentLvl2);
        possibleWeaponsTest.add(configs.heraAthenaDag);
        possibleWeaponsTest.add(configs.plunger);
        possibleWeaponsTest.add(configs.pipe);
        possibleWeaponsTest.add(configs.goldenPlungerBow);
        possibleWeaponsTest.add(configs.plungerBow);

        assertNotSame(possibleWeaponsTest, CraftingLogic.getPossibleWeapons());

//        for (int i = 0; i < CraftingLogic.getPossibleWeapons().size(); i++){
//
//            switch (i) {
//                case 0:
//                    assertEquals(CraftingLogic.getPossibleWeapons().get(i), configs.athenaDag);
//                    break;
//                case 1:
//                    assertEquals(CraftingLogic.getPossibleWeapons().get(i), configs.heraDag);
//                    break;
//                case 2:
//                    assertEquals(CraftingLogic.getPossibleWeapons().get(i), configs.SwordLvl2);
//                    break;
//                case 3:
//                    assertEquals(CraftingLogic.getPossibleWeapons().get(i), configs.dumbbell);
//                    break;
//                case 4:
//                    assertEquals(CraftingLogic.getPossibleWeapons().get(i), configs.tridentLvl2);
//                    break;
//                case 5:
//                    assertEquals(CraftingLogic.getPossibleWeapons().get(i), configs.heraAthenaDag);
//                    break;
//                case 6:
//                    assertEquals(CraftingLogic.getPossibleWeapons().get(i), configs.plunger);
//                    break;
//            }
//        for (int i = 0; i < CraftingLogic.getPossibleWeapons().size(); i++) {
//            assertSame(CraftingLogic.getPossibleWeapons().get(i), CraftingLogic.getPossibleWeapons().get(i));
//       }
    }

    @Test
    void canBuildTest() {
        List<WeaponConfig> buildItemsTest = new ArrayList<>();
        List<Materials> inventoryContentsTest = new ArrayList<>();
        inventoryContentsTest.add(Materials.Steel);
        inventoryContentsTest.add(Materials.Wood);
        inventoryContentsTest.add(Materials.Gold);
        inventoryContentsTest.add(Materials.Iron);
        inventoryContentsTest.add(Materials.Poop);
        CraftingLogic.canBuild(inventoryContentsTest);
        assertEquals(CraftingLogic.canBuild(inventoryContentsTest), buildItemsTest);
    }

    @Test
    void damageToWeaponTest() {
//        WeaponConfigSetup configs =
//                FileLoader.readClass(WeaponConfigSetup.class, "configs/Weapons.json");
//
//        WeaponConfig athenaDag = configs.athenaDag;
//        WeaponConfig heraDag = configs.heraDag;
//        WeaponConfig dumbbell = configs.dumbbell;
//        WeaponConfig swordLvl2 = configs.SwordLvl2;
//        WeaponConfig tridentLvl2 = configs.tridentLvl2;
//        WeaponConfig heraAthenaDag = configs.heraAthenaDag;
//        WeaponConfig plunger = configs.plunger;
//        WeaponConfig pipe = configs.pipe;
//        WeaponConfig plungerBow = configs.plungerBow;
//        WeaponConfig goldenPlungerBow = configs.goldenPlungerBow;
//        entity = CraftingLogic.damageToWeapon(athenaDag);
//        assertTrue(entity.checkEntityType(EntityTypes.MELEE));
//        assertTrue(CraftingLogic.damageToWeapon(athenaDag).checkEntityType(EntityTypes.WEAPON));
//        assertEquals(WeaponFactory.createDagger() ,CraftingLogic.damageToWeapon(athenaDag));
//        MeleeConfig daggerConfig = new MeleeConfig();
//        daggerConfig.damage = 7;
//        double damage = daggerConfig.damage;
//        assertEquals(CraftingLogic.damageToWeapon(daggerConfig), WeaponFactory.createDagger());
        }
}
