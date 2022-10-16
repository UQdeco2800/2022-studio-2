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

        String statImages[] = {"images/PlayerStatDisplayGraphics/Health-plunger/plunger_1.png",
                "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_1.png",
                "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_1.png"};
        resourceService.loadTextures(statImages);
        resourceService.loadAll();

        dummy = new Entity();
        playStatDisplay = new PlayerStatsDisplay();
        dummy.addComponent(playStatDisplay);
    }


    /**
     * Checking stat of 100 and that the class returns the 1.png string
     */
    @Test
    void checkImage100Test(){
       assertEquals("1.png", playStatDisplay.checkImage(100));
    }

    /**
     * Checking stat of 90 and that the class returns the 2.png string
     */
    @Test
    void checkImage90Test(){
        assertEquals("2.png", playStatDisplay.checkImage(90));
    }

    /**
     * Checking stat of 80 and that the class returns the 2.png string
     */
    @Test
    void checkImage80Test(){
        assertEquals("2.png", playStatDisplay.checkImage(80));
    }

    /**
     * Checking stat of 70 and that the class returns the 3.png string
     */
    @Test
    void checkImage70Test(){
        assertEquals("3.png", playStatDisplay.checkImage(70));
    }

    /**
     * Checking stat of 60 and that the class returns the 3.png string
     */
    @Test
    void checkImage60Test(){
        assertEquals("3.png", playStatDisplay.checkImage(60));
    }

    /**
     * Checking stat of 50 and that the class returns the 4.png string
     */
    @Test
    void checkImage50Test(){
        assertEquals("4.png", playStatDisplay.checkImage(50));
    }

    /**
     * Checking stat of 40 and that the class returns the 4.png string
     */
    @Test
    void checkImage40Test(){
        assertEquals("4.png", playStatDisplay.checkImage(40));
    }

    /**
     * Checking stat of 30 and that the class returns the 5.png string
     */
    @Test
    void checkImage30Test(){
        assertEquals("5.png", playStatDisplay.checkImage(30));
    }

    /**
     * Checking stat of 20 and that the class returns the 5.png string
     */
    @Test
    void checkImage20Test(){
        assertEquals("5.png", playStatDisplay.checkImage(20));
    }

    /**
     * Checking stat of 10 and that the class returns the 6.png string
     */
    @Test
    void checkImage10Test(){
        assertEquals("6.png", playStatDisplay.checkImage(10));
    }

    /**
     * Checking stat of 5 and that the class returns the 7.png string
     */
    @Test
    void checkImage5Test(){ assertEquals("7.png", playStatDisplay.checkImage(5));}

    /**
     * Checking stat of 0 and that the class returns the 7.png string
     */
    @Test
    void checkImage0Test(){ assertEquals("7.png", playStatDisplay.checkImage(0));}


}
