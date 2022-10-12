package com.deco2800.game.components.deathscreen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.GdxGame;
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
class DeathScreenActionsTest {
    Integer level;
    DeathScreenActions deathScreenActions;
    @Mock
    Entity entity;
    //Entity dummy;
    @Mock
    GdxGame dummy;

    /**
     * Does things before each test is run
     */
    @BeforeEach
    void init() {

        dummy = new GdxGame();
        deathScreenActions = new DeathScreenActions(dummy);

        // Create our service locator
        AssetManager assetManager = spy(AssetManager.class);
        ResourceService resourceService = new ResourceService(assetManager);
        ServiceLocator.registerResourceService(resourceService);

        String deathTextures[] = {"images/DeathScreens/lvl_1_w_buttons.png", "images/DeathScreens/lvl_2_w_buttons.png",
                "images/WinScreen/atlantis_sinks_no_island_win.png",};
        resourceService.loadTextures(deathTextures);
        resourceService.loadAll();
    }

    /**
     * Tests if when level is 1 it sets the deathscreen image to level 1.
     */
    @Test
    void levelBackgroundOne() {


    }
}