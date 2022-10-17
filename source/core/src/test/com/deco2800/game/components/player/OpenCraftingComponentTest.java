package com.deco2800.game.components.player;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
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
        AssetManager assetManager = spy(AssetManager.class);
        ResourceService resourceService = new ResourceService(assetManager);
        ServiceLocator.registerResourceService(resourceService);
        // Add necessary components to the service locator
        GameArea gameArea = spy(GameArea.class);
        RenderService renderService = new RenderService();
        renderService.setStage(mock(Stage.class));
        GameAreaDisplay playerGuidArea = new GameAreaDisplay("");
        ServiceLocator.registerGameArea(gameArea);
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerPlayerGuideArea(playerGuidArea);
        playerGuidArea.create();
        // Create appropriate variables exclusive to init
        player = new Entity();
        openCraftingComponent = new OpenCraftingComponent();
        player.addComponent(openCraftingComponent);
        player.create();
//        gameArea.setPlayer(player);
    }

    /*@Test
    void create() {

    }*/
    @Test
    void getCraftingStatusTest() {
        assertEquals(OpenCraftingComponent.getCraftingStatus(), false);
    }

    @Test
    void setCraftingStatus() {
        if (OpenCraftingComponent.getCraftingStatus()) {
            OpenCraftingComponent.setCraftingStatus();
            assertEquals(OpenCraftingComponent.getCraftingStatus(), false);
        }
    }


}