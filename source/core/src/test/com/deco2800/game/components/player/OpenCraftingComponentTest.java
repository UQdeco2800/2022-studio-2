package com.deco2800.game.components.player;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.PlayerFactory;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class OpenCraftingComponentTest {

    Entity player;
    OpenCraftingComponent openCraftingComponent;
    @BeforeEach
    void setUp() {
        ForestGameArea fga = mock(ForestGameArea.class);
        ServiceLocator.registerGameArea(fga);
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerRenderService(new RenderService());
        ResourceService resourceService = new ResourceService();
        ServiceLocator.registerResourceService(resourceService);
//        AssetManager assetManager = spy(AssetManager.class);
//        ResourceService resourceService = new ResourceService(assetManager);
//        ServiceLocator.registerResourceService(resourceService);
//        GameArea gameArea = spy(GameArea.class);
//        RenderService renderService = new RenderService();
//        renderService.setStage(mock(Stage.class));
//        GameAreaDisplay playerGuidArea = new GameAreaDisplay("Underground");
//        ServiceLocator.registerGameArea(gameArea);
//        ServiceLocator.registerRenderService(renderService);
//        ServiceLocator.registerPlayerGuideArea(playerGuidArea);
//        ResourceService resourceService1 = spy(ResourceService.class);
//        ServiceLocator.registerResourceService(resourceService1);
//        ServiceLocator.registerPhysicsService(new PhysicsService());

        String[] textures = {
                "images/Crafting-assets-sprint1/crafting table/crafting_inventory_lvl1.png",
                "images/Crafting-assets-sprint1/crafting table/crafting_inventory_lvl2.png",
                "images/Crafting-assets-sprint1/widgets/craft_button_lvl1.png",
                "images/Crafting-assets-sprint1/widgets/craft_button_lvl2.png",
                "images/Crafting-assets-sprint1/widgets/exit_button_lvl1.png",
                "images/Crafting-assets-sprint1/widgets/exit_button_lvl2.png",
                "images/PauseMenu/lvl2PauseScreen.png",
                "images/PauseMenu/newPauseScreen.png",
                "images/crafting_assets_sprint2/transparent-texture-buttonClick.png",
                "images/keybind/level_1/ControlPage.png",
                "images/keybind/level_2/ControlPage.png",
                "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/Hera.png",
                "images/Crafting-assets-sprint1/popups/number1_popup.png",
                "images/Crafting-assets-sprint1/popups/number2_popup.png",
                "images/Crafting-assets-sprint1/popups/number3_popup.png",
                "images/Crafting-assets-sprint1/popups/number4_popup.png",
                "images/Crafting-assets-sprint1/popups/number5_popup.png",
                "images/Crafting-assets-sprint1/popups/crafting_indicator.png",
                "images/Crafting-assets-sprint1/popups/arrow-top-right.png",
                "images/Crafting-assets-sprint1/popups/arrow-top-left.png",
                "images/Crafting-assets-sprint1/popups/first-mat-prompt.png",
                "images/Crafting-assets-sprint1/popups/second-mat-prompt.png",
                "images/Crafting-assets-sprint1/popups/craft-prompt.png",
                "images/Crafting-assets-sprint1/crafting table/crafting_catalogue_1_lvl1.png",
                "images/Crafting-assets-sprint1/crafting table/crafting_catalogue_1_lvl2.png",
                "images/Crafting-assets-sprint1/widgets/inventory_button_lvl1.png",
                "images/Crafting-assets-sprint1/widgets/inventory_button_lvl2.png",
                "images/Crafting-assets-sprint1/widgets/catalogue_page2_lvl1.png",
                "images/Crafting-assets-sprint1/widgets/catalogue_page2_lvl2.png",
                "images/Crafting-assets-sprint1/crafting table/crafting_catalogue_2_lvl1.png",
                "images/Crafting-assets-sprint1/crafting table/crafting_catalogue_2_lvl2.png",
                "images/Crafting-assets-sprint1/widgets/catalogue_page1_lvl1.png",
                "images/Crafting-assets-sprint1/widgets/catalogue_page1_lvl2.png",
//                "sounds/ItemClick.wav",
//                "sounds/Scroll.wav",
//                "sounds/new_Weapon_Crafted.wav",
                "images/CombatItems/Sprint-1/Level 2 Dagger 1.png",
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
                "images/Crafting-assets-sprint1/materials/silver.png"
        };
        resourceService.loadTextures(textures);
        String[] textureAtlases = {"images/CombatItems/animations/combatItemsAnimation.atlas"};
        resourceService.loadTextureAtlases(textureAtlases);
        resourceService.loadAll();

        GameArea gameArea = spy(GameArea.class);
        RenderService renderService = new RenderService();
        renderService.setStage(mock(Stage.class));
        GameAreaDisplay craftingArea = new GameAreaDisplay("Underground");
        ServiceLocator.registerGameArea(gameArea);
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerInventoryArea(craftingArea);
        craftingArea.create();
        player = PlayerFactory.createTestPlayer();
        openCraftingComponent = new OpenCraftingComponent();
        player.addComponent(openCraftingComponent);
        player.create();
        gameArea.setPlayer(player);
    }

    @Test
    void shouldCreate() {
        OpenCraftingComponent openCraftingComponent1 = mock(OpenCraftingComponent.class);
        openCraftingComponent1.create();
        verify(openCraftingComponent1).create();
        assertEquals(false, openCraftingComponent.getCraftingStatus());
        assertEquals(1, player.getEvents().getNumberOfListeners("can_open"));
    }

    @Test
    void shouldOpenCrafting() {
//        Entity player = PlayerFactory.createTestPlayer();
//        ServiceLocator serviceLocator = mock(ServiceLocator.class);
//
//        when(serviceLocator.getGameArea().getPlayer()).thenReturn(player);
//
//        OpenCraftingComponent openCraftingComponent1 = spy(OpenCraftingComponent.class);
//        openCraftingComponent1.openCrafting();
//
//        verify(openCraftingComponent1).openCrafting();
//        Entity player = PlayerFactory.createTestPlayer();
//        ServiceLocator serviceLocator = mock(ServiceLocator.class);
//
//        PhysicsService physicsService = new PhysicsService(new PhysicsEngine());
//        serviceLocator.registerPhysicsService(physicsService);
//
//        when(serviceLocator.getGameArea().getPlayer()).thenReturn(player);
//
//        OpenCraftingComponent craftingComponent = spy(OpenCraftingComponent.class);
//        craftingComponent.openCrafting();
//
//        verify(craftingComponent).openCrafting();
//        player.setPosition(1111, 15);
//        player.getEvents().trigger("can_open");
//        assertTrue(openCraftingComponent.getCraftingStatus());
//        player.getEvents().trigger("Opening Crafting Menu");
//        player.getEvents().trigger("Weapon added to inventory");
//        player.getEvents().trigger("check");
//        player.getEvents().trigger("Catalogue button pressed");
//        player.getEvents().trigger("Exit Crafting menu");
//        OpenCraftingComponent.setCraftingStatus();
//        assertFalse(openCraftingComponent.getCraftingStatus());

//        player.setPosition(100, 10);
//        OpenCraftingComponent.setCraftingStatus();
//
//        ServiceLocator serviceLocator = mock(ServiceLocator.class);
//
//        when(serviceLocator.getGameArea().getPlayer()).thenReturn(player);
//
//        OpenCraftingComponent openCraftingComponent1 = spy(OpenCraftingComponent.class);
//        openCraftingComponent.openCrafting();
//
//        verify(openCraftingComponent).openCrafting();
    }

    @Test
    void shouldInRange() {
        player.setPosition(90, 45);
        assertTrue(openCraftingComponent.inRange(player.getPosition()));

        AssetManager assetManager = spy(AssetManager.class);
        ResourceService resourceService = new ResourceService(assetManager);
        ServiceLocator.registerResourceService(resourceService);
        GameArea gameArea = spy(GameArea.class);
        RenderService renderService = new RenderService();
        renderService.setStage(mock(Stage.class));
        GameAreaDisplay playerGuidArea = new GameAreaDisplay("");
        ServiceLocator.registerGameArea(gameArea);
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerPlayerGuideArea(playerGuidArea);
        ServiceLocator.registerCraftArea(playerGuidArea);
        playerGuidArea.create();
        player = new Entity();
        openCraftingComponent = new OpenCraftingComponent();
        player.addComponent(openCraftingComponent);
        player.create();
        gameArea.setPlayer(player);
        player.setPosition(100, 10);
        assertTrue(openCraftingComponent.inRange(player.getPosition()));

        player.setPosition(1111, 1111);
        assertFalse(openCraftingComponent.inRange(player.getPosition()));
        assertTrue(openCraftingComponent.getCraftingStatus());
    }
    @Test
    void shouldGetCraftingStatus() {
        assertFalse(openCraftingComponent.getCraftingStatus());
    }
    @Test
    void shouldSetCraftingStatus() {
        OpenCraftingComponent.setCraftingStatus();
        assertTrue(openCraftingComponent.getCraftingStatus());
    }
}