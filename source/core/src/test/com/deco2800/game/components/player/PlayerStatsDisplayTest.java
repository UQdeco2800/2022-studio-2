package com.deco2800.game.components.player;
import com.badlogic.gdx.assets.AssetManager;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.components.player.PlayerStatsDisplay;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.player.*;


import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;
import org.junit.Assert;
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

        dummy = new Entity();
        playStatDisplay = new PlayerStatsDisplay();
                //.addComponent(new PlayerStatsDisplay());
       // display = entity.getComponent(PlayerStatsDisplay.class);
        AssetManager assetManager = spy(AssetManager.class);
        ResourceService resourceService = new ResourceService(assetManager);
        ServiceLocator.registerResourceService(resourceService);

        /*
        Okay so current problem is the constructor of PlayStatDisplay
        loads images as textures and for some reason
        below isn't loading them as textures?
         */
        String statImages[] = {"images/PlayerStatDisplayGraphics/Health-plunger/plunger_1.png",
                "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_1.png",
                "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_1.png"};
        resourceService.loadTextures(statImages);
        resourceService.loadAll();

        dummy.addComponent(playStatDisplay);
    }

    //@Test
    void checkImageTest(){

       // assertEquals("1.png", playStatDisplay.checkImage(100));
    }

    

}
