package com.deco2800.game.components.leveltransition;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.GdxGame;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;

import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.screens.LevelTransitionScreen;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class LevelTransitionDisplayTest {

    private void custom_wait (long sleep) {
        long start = System.currentTimeMillis();
        while ((System.currentTimeMillis() - start) < sleep);
    }

    LevelTransitionDisplay levelTransitionDisplay;
    LevelTransitionScreen levelTransitionScreen;
    GdxGame game;


    // Commented out until I find a solution to the server runtime error
    @BeforeEach
    void init() {
        // Load the service locator and resource service thangs
        AssetManager assetManager = spy(AssetManager.class);
        ResourceService resourceService = new ResourceService(assetManager);
        ServiceLocator.registerResourceService(resourceService);

        // Render service handling
        RenderService renderService = new RenderService();
        ServiceLocator.registerRenderService(renderService);

        game = new GdxGame();
        levelTransitionScreen = mock(LevelTransitionScreen.class);
        levelTransitionScreen.loadAssets();
        levelTransitionDisplay = new LevelTransitionDisplay();
        levelTransitionDisplay.jUnitCreate();
    }

    @Test
    void shouldGetZIndex () {

        assertEquals(2, levelTransitionDisplay.getZIndex());
    }

    @Test
    void shouldGetFrameAfterCreate() {

        assertEquals(1, levelTransitionDisplay.getFrame());
    }

    @Test
    void shouldUpdateFrameWait() {

        assertEquals(1, levelTransitionDisplay.getFrame());
        custom_wait(levelTransitionDisplay.getFrameDuration()+10);
        levelTransitionDisplay.jUnitUpdate();
        assertEquals(2, levelTransitionDisplay.getFrame());
    }

    @Test
    void shouldNotUpdateFrameWait() {

        assertEquals(1, levelTransitionDisplay.getFrame());
        custom_wait(levelTransitionDisplay.getFrameDuration() / 2);
        levelTransitionDisplay.jUnitUpdate();
        assertEquals(1, levelTransitionDisplay.getFrame());
    }

    @Test
    void shouldFillTable() {

        Table table = levelTransitionDisplay.getTable();
        assertTrue(table.hasChildren());
    }

    @Test
    void shouldEmptyTable() {

        Table table = levelTransitionDisplay.getTable();
        levelTransitionDisplay.dispose();
        assertFalse(table.hasChildren());
    }

    @Test
    void shouldNotExceedFrameLimit() {

        levelTransitionDisplay.setFrame(levelTransitionScreen.FRAME_COUNT);
        assertEquals(55, levelTransitionDisplay.getFrame());
        custom_wait(levelTransitionDisplay.getFrameDuration()+10);
        levelTransitionDisplay.jUnitUpdate();
        assertEquals(55, levelTransitionDisplay.getFrame());
    }
}
