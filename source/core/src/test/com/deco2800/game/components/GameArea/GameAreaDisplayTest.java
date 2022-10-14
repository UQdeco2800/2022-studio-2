package com.deco2800.game.components.GameArea;

import com.deco2800.game.areas.GameArea;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.entities.EntityService;
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

    GameArea gameArea;
    @Mock
    RenderService service;

    @BeforeEach
    void setUp() {
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerRenderService(new RenderService());
//        ServiceLocator.registerCraftArea(new GameAreaDisplay("TestGameArea"));
    }

//    @Test
//    void shouldCreate() {
//        ServiceLocator.registerGameArea(gameArea);
//        GameAreaDisplay gameAreaDisplay = spy(GameAreaDisplay.class);
//        gameAreaDisplay.create();
//        verify(gameAreaDisplay).create();
//    }

    @Test
    void openCraftingMenu() {
        GameAreaDisplay gameAreaDisplay = mock(GameAreaDisplay.class);
        gameAreaDisplay.openCraftingMenu();
        verify(gameAreaDisplay).openCraftingMenu();
    }

    @Test
    void setPauseMenu() {
    }

    @Test
    void setPlayerGuideMenu() {
    }

    @Test
    void disposePlayerGuideMenu() {
    }

    @Test
    void setKeyBindMenu() {
    }

    @Test
    void disposeKeyBindMenu() {
    }

    @Test
    void createKeyBindings() {
    }

    @Test
    void displayCatOne() {
    }

    @Test
    void disposeCraftingMenu() {
    }

    @Test
    void disposePauseMenu() {
    }

    @Test
    void toggleSkillTree() {
    }

    @Test
    void draw() {
    }

    @Test
    void dispose() {
    }
}