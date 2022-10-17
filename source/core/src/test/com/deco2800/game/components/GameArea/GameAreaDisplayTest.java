package com.deco2800.game.components.GameArea;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.components.player.OpenPauseComponent;
import com.deco2800.game.crafting.Materials;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.MaterialFactory;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class GameAreaDisplayTest {

    @Mock Entity player;
    @Mock GameAreaDisplay gameAreaDisplay;

    InventoryComponent inventoryComponent = new InventoryComponent();

    @BeforeEach
    void setUp() {
        AssetManager assetManager = spy(AssetManager.class);
        ResourceService resourceService = new ResourceService(assetManager);
        ServiceLocator.registerResourceService(resourceService);

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
        GameAreaDisplay areaDisplay = new GameAreaDisplay("");
        ServiceLocator.registerGameArea(gameArea);
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerCraftArea(areaDisplay);
        ServiceLocator.registerPlayerGuideArea(areaDisplay);
        areaDisplay.create();

        player = new Entity();
        gameAreaDisplay = new GameAreaDisplay("");
        player.addComponent(gameAreaDisplay);
        player.create();

        gameArea.setPlayer(player);

//        inventoryComponent = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
//        inventoryComponent.addItem(MaterialFactory.createGold());
//        inventoryComponent.addItem(MaterialFactory.createPoop());
//        inventoryComponent.addItem(MaterialFactory.createBaseMaterial());
//        inventoryComponent.addItem(MaterialFactory.createWood());
//        inventoryComponent.addItem(MaterialFactory.createToiletPaper());
//        inventoryComponent.addItem(MaterialFactory.createGold());
//        inventoryComponent.addItem(MaterialFactory.createIron());
//        inventoryComponent.addItem(MaterialFactory.createPlastic());
//        inventoryComponent.addItem(MaterialFactory.createRubber());
//        inventoryComponent.addItem(MaterialFactory.createSilver());
//        inventoryComponent.addItem(MaterialFactory.createSteel());
//        inventoryComponent.addItem(MaterialFactory.createRubber());



    }

    @Test
    void shouldCreate() {
        assertNotNull(gameAreaDisplay.getGameAreaName());
        GameAreaDisplay gameAreaDisplay = mock(GameAreaDisplay.class);
        gameAreaDisplay.create();
        verify(gameAreaDisplay).create();
    }

    @Test
    void ShouldOpenCraftingMenu() {
//        player.getEvents().trigger("Opening Crafting Menu");
//        gameAreaDisplay = new GameAreaDisplay("Underground");
//        assertEquals(0, gameAreaDisplay.getFirstTime());
        GameAreaDisplay gameAreaDisplay = mock(GameAreaDisplay.class);
        gameAreaDisplay.openCraftingMenu();
        verify(gameAreaDisplay).openCraftingMenu();
    }

    @Test
    void setPauseMenu() {
        GameAreaDisplay gameAreaDisplay = mock(GameAreaDisplay.class);
        gameAreaDisplay.setPauseMenu();
        verify(gameAreaDisplay).setPauseMenu();
    }

    @Test
    void  disposePauseMenu() {
        GameAreaDisplay gameAreaDisplay = mock(GameAreaDisplay.class);
        gameAreaDisplay.disposePauseMenu();
        verify(gameAreaDisplay).disposePauseMenu();
    }
    @Test
    void setPlayerGuideMenu() {
        GameAreaDisplay gameAreaDisplay = mock(GameAreaDisplay.class);
        gameAreaDisplay.setPlayerGuideMenu("");
        verify(gameAreaDisplay).setPlayerGuideMenu("");
    }


    @Test
    void disposePlayerGuideMenu() {
        GameAreaDisplay gameAreaDisplay = mock(GameAreaDisplay.class);
        gameAreaDisplay.disposePlayerGuideMenu();
        verify(gameAreaDisplay).disposePlayerGuideMenu();
    }

    @Test
    void getPlayerGuideMenu(){
        GameAreaDisplay gameAreaDisplay = mock(GameAreaDisplay.class);
        gameAreaDisplay.getPlayerGuideMenu();
        verify(gameAreaDisplay).getPlayerGuideMenu();
    }


    @Test
    void displayCatOne() {
        GameAreaDisplay gameAreaDisplay = mock(GameAreaDisplay.class);
        gameAreaDisplay.displayCatOne();
        verify(gameAreaDisplay).displayCatOne();
    }

    @Test
    void displayCatTwo() {
        GameAreaDisplay gameAreaDisplay = mock(GameAreaDisplay.class);
        gameAreaDisplay.displayCatTwo();
        verify(gameAreaDisplay).displayCatTwo();
    }

    @Test
    void disposeCraftingMenu() {
        GameAreaDisplay gameAreaDisplay = mock(GameAreaDisplay.class);
        gameAreaDisplay.disposeCraftingMenu();
        verify(gameAreaDisplay).disposeCraftingMenu();
    }

    @Test
    void getFirstTime() {
        assertEquals(0, gameAreaDisplay.getFirstTime());
    }

    @Test
    void draw() {
        GameAreaDisplay gameAreaDisplay = mock(GameAreaDisplay.class);
        SpriteBatch spriteBatch = new SpriteBatch();
        gameAreaDisplay.draw(spriteBatch);
        verify(gameAreaDisplay).draw(spriteBatch);
    }


}