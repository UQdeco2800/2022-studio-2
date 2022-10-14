package com.deco2800.game.components.player;

import com.badlogic.gdx.assets.AssetManager;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.services.ServiceLocator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
public class PlayerStatsDisplayTest{
    @Mock Entity dummy;
    PlayerStatsDisplay playStatDisplay;

    /**
     * Does things before each test is run
     */
    @BeforeEach
    void init() {

        AssetManager assetManager = spy(AssetManager.class);
        ResourceService resourceService = new ResourceService(assetManager);
        ServiceLocator.registerResourceService(resourceService);

        /*
        Okay so current problem is the constructor of PlayStatDisplay
        loads images as textures and for some reason
        below isn't loading them as textures?
         */
        // hey eli im just leaving this message in, it seems to be the rendering service was null?
        // I suspect it was due to you calling the playStatDisplay object before you registered
        // the services and hence its resource service it had was null at the time of creation!
        // Should be good now :P
        String statImages[] = {"images/PlayerStatDisplayGraphics/Health-plunger/plunger_1.png",
                "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_1.png",
                "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_1.png"};
        resourceService.loadTextures(statImages);
        resourceService.loadAll();

        dummy = new Entity();
        playStatDisplay = new PlayerStatsDisplay();
        dummy.addComponent(playStatDisplay);
    }

    @Test
    void checkImageTest(){

       assertEquals("1.png", playStatDisplay.checkImage(100));
    }

    

}
