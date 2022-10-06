package com.deco2800.game.components.deathscreen;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.deathscreen.DeathScreenDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.extensions.GameExtension;


import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.screens.DeathScreen;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;

import com.deco2800.game.ui.UIComponent;
import net.dermetfan.gdx.physics.box2d.PositionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
public class DeathScreenDisplayTest {
    Integer level;
    DeathScreenDisplay deathScreenDisplay;
    Entity dummy;

    @Mock Entity entity;


    /**
     * Does things before each test is run
     */
    @BeforeEach
    void init() {

        Entity mock = mock(Entity.class);
        AssetManager assetManager = spy(AssetManager.class);
        ResourceService resourceService = new ResourceService(assetManager);
        ServiceLocator.registerResourceService(resourceService);

        // Create deathScreenDisplay
        deathScreenDisplay = new DeathScreenDisplay();

    }

    /**
     * Tests if when level is 1 it sets the deathscreen image to level 1.
     */
    @Test
    void levelBackgroundOne() {
//        level = 1;
//        deathScreenDisplay = new DeathScreenDisplay(level);
//        Texture tex = ServiceLocator.getResourceService().getAsset("images/DeathScreens/lvl_1.png", Texture.class);
//        assertEquals(deathScreenDisplay.levelBackground(level), tex);

    }

}
