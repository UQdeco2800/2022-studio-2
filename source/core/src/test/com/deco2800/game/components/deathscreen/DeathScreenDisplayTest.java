package com.deco2800.game.components.deathscreen;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
public class DeathScreenDisplayTest {
    Integer level;
    DeathScreenDisplay deathScreenDisplay;
    @Mock Entity entity;
    Entity dummy;

    /**
     * Does things before each test is run
     */
    @BeforeEach
    void init() {

        dummy = new Entity();
        deathScreenDisplay = new DeathScreenDisplay();

        // Create our service locator
        AssetManager assetManager = spy(AssetManager.class);
        ResourceService resourceService = new ResourceService(assetManager);
        ServiceLocator.registerResourceService(resourceService);

        String deathTextures[] = {"images/DeathScreens/lvl_1_w_buttons.png", "images/DeathScreens/lvl_2_w_buttons.png",
        "images/WinScreen/atlantis_sinks_no_island_win.png", };
        resourceService.loadTextures(deathTextures);
        resourceService.loadAll();

        dummy.addComponent(deathScreenDisplay);

    }

    /**
     * Tests if when level is 1 it sets the deathscreen image to level 1.
     */
    @Test
    void levelBackgroundOne() {

        TextureRegionDrawable levelOne = new TextureRegionDrawable(ServiceLocator.getResourceService()
                .getAsset("images/DeathScreens/lvl_1_w_buttons.png", Texture.class));

        deathScreenDisplay = new DeathScreenDisplay(1);
        assertEquals(levelOne.getName(), deathScreenDisplay.levelBackground().getName());

    }


    /**
     * Tests if when level is 2 it sets the deathscreen image to level 2.
     */
    @Test
    void levelBackgroundTwo() {

        TextureRegionDrawable levelTex = new TextureRegionDrawable(ServiceLocator.getResourceService()
                .getAsset("images/DeathScreens/lvl_2_w_buttons.png", Texture.class));
        deathScreenDisplay = new DeathScreenDisplay(2);
        assertEquals(levelTex.getName(), deathScreenDisplay.levelBackground().getName());

    }

    /**
     * Tests if when level is 3 it sets the deathscreen image to the win screen.
     */
    @Test
    void levelBackgroundWin() {

        TextureRegionDrawable levelTex = new TextureRegionDrawable(ServiceLocator.getResourceService()
                .getAsset("images/WinScreen/atlantis_sinks_no_island_win.png", Texture.class));
        deathScreenDisplay = new DeathScreenDisplay(3);
        assertEquals(levelTex.getName(), deathScreenDisplay.levelBackground().getName());

    }

    /**
     * Tests if when level is 4 it sets the deathscreen image to the default image level 1.
     */
    @Test
    void levelBackgroundDefault() {

        TextureRegionDrawable levelTex = new TextureRegionDrawable(ServiceLocator.getResourceService()
                .getAsset("images/DeathScreens/lvl_1_w_buttons.png", Texture.class));
        deathScreenDisplay = new DeathScreenDisplay(4);
        assertEquals(levelTex.getName(), deathScreenDisplay.levelBackground().getName());

    }


    /**
     * Tests if when level is 1 it sets the deathscreen level 1.
     */
    @Test
    void getLevelOne() {
        deathScreenDisplay = new DeathScreenDisplay(1);
        assertEquals(1, deathScreenDisplay.getLevel());

    }

    /**
     * Tests if when level is 2 it sets the deathscreen level 2.
     */
    @Test
    void getLevelTwo() {
        deathScreenDisplay = new DeathScreenDisplay(2);
        assertEquals(2, deathScreenDisplay.getLevel());

    }

    /**
     * Tests if when level is 3 it sets the deathscreen level 3 (win screen).
     */
    @Test
    void getLevelThree() {
        deathScreenDisplay = new DeathScreenDisplay(3);
        assertEquals(3, deathScreenDisplay.getLevel());

    }
}
