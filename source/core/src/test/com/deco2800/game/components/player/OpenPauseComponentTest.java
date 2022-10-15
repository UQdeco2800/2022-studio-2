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
class OpenPauseComponentTest {

    Entity player;
    OpenPauseComponent openPauseComponent;

    @BeforeEach
    void init() {
        // Create our service locator
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
        openPauseComponent = new OpenPauseComponent();
        player.addComponent(openPauseComponent);
        player.create();

        gameArea.setPlayer(player);
    }

    @Test
    void shouldCreate() {

        assertNotNull(openPauseComponent.getOpenKeyBinds());
        assertEquals(1, player.getEvents().getNumberOfListeners("escInput"));
        assertEquals(1, player.getEvents().getNumberOfListeners("toggleInventory"));
    }

    @Test
    void shouldAllBeClosed() {

        assertFalse(openPauseComponent.getPauseOpen());
        assertFalse(openPauseComponent.getKeyBindOpen());
        assertFalse(openPauseComponent.getPlayerGuideOpen());
        assertFalse(openPauseComponent.getInventoryToggled());
    }

    @Test
    void shouldOpenInventory() {

        player.getEvents().trigger("toggleInventory");
        assertTrue(openPauseComponent.getInventoryToggled());
    }

    @Test
    void shouldCloseInventory() {

        player.getEvents().trigger("toggleInventory");
        player.getEvents().trigger("toggleInventory");
        assertFalse(openPauseComponent.getInventoryToggled());
    }

    @Test
    void shouldOpenPauseMenu() {

        player.getEvents().trigger("escInput");
        assertTrue(openPauseComponent.getPauseOpen());
    }

    @Test
    void shouldOpenPauseMenuInventoryToggle() {

        player.getEvents().trigger("toggleInventory");
        player.getEvents().trigger("escInput");
        assertTrue(openPauseComponent.getPauseOpen());
    }

    @Test
    void shouldClosePauseMenu() {

        player.getEvents().trigger("escInput");
        player.getEvents().trigger("escInput");
        assertFalse(openPauseComponent.getPauseOpen());
    }

    @Test
    void shouldClosePauseMenuInventoryToggle() {

        player.getEvents().trigger("toggleInventory");
        player.getEvents().trigger("escInput");
        player.getEvents().trigger("escInput");
        assertFalse(openPauseComponent.getPauseOpen());
    }


    @Test
    void shouldNotOpenPlayerGuide() {

        assertTrue(openPauseComponent.openPlayerGuide(1,3));
        assertFalse(openPauseComponent.openPlayerGuide(1,9));
        assertFalse(openPauseComponent.openPlayerGuide(-1,3));
    }

    @Test
    void shouldOpenPlayerGuide() {

        for (int i = 1; i <= 8; i++) {
            assertTrue(openPauseComponent.openPlayerGuide(1, i));
            assertTrue(openPauseComponent.openPlayerGuide(2, i));
        }
    }

    @Test
    void shouldClosePlayerGuide() {

        for (int j = 1; j <=2; j++){
            for (int i = 1; i <= 8; i++) {
                openPauseComponent.openPlayerGuide(j, i);
                openPauseComponent.closePlayerGuide();
                assertNull(ServiceLocator.getPlayerGuidArea().getPlayerGuideMenu());
            }
        }
    }

    @Test
    void shouldLoadCorrectPlayerGuide() {

        String filePath;

        for (int j = 1; j <=2; j++){
            for (int i = 1; i <= 8; i++) {
                filePath = "images/Player Guide/level_";
                filePath += String.format("%d/%d.png", j, i);
                openPauseComponent.openPlayerGuide(j, i);
                assertEquals(filePath, ServiceLocator.getPlayerGuidArea().getPlayerGuideMenu());
            }
        }
    }

    @Test
    void shouldOpenKeyBindings() {

        openPauseComponent.openKeyBindings();
        assertTrue(openPauseComponent.getKeyBindOpen());
    }

    @Test
    void shouldCloseKeyBindings() {

        openPauseComponent.openKeyBindings();
        openPauseComponent.closeKeyBindings();
        assertFalse(openPauseComponent.getKeyBindOpen());
    }

    @Test
    void shouldToggleKeyBinds() {

        openPauseComponent.openKeyBindings();
        assertTrue(openPauseComponent.getKeyBindOpen());
        player.getEvents().trigger("escInput");
        assertFalse(openPauseComponent.getKeyBindOpen());
    }

    @Test
    void shouldTogglePlayerGuide() {

        openPauseComponent.openPlayerGuide(1,1);
        assertTrue(openPauseComponent.getPlayerGuideOpen());
        player.getEvents().trigger("escInput");
        assertFalse(openPauseComponent.getPlayerGuideOpen());
    }
}