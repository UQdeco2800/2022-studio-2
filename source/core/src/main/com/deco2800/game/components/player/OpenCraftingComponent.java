package com.deco2800.game.components.player;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import com.deco2800.game.entities.EntityService;
import org.testng.reporters.jq.Main;


public class OpenCraftingComponent extends Component {
    private static Logger logger;
    public static Boolean craftingStatus = false;

    public void create() {

        logger = LoggerFactory.getLogger(OpenCraftingComponent.class);
        entity.getEvents().addListener("can_open", this::openCrafting);

    }

    private void openCrafting() {
        if (inRange(entity.getCenterPosition()) && craftingStatus == false) {
            ServiceLocator.getCraftArea().openCraftingMenu();
            Sound openCraftMenu = ServiceLocator.getResourceService().getAsset("sounds/Scroll.wav", Sound.class);
            openCraftMenu.play();
            ServiceLocator.getCraftArea().displayCatOne();
            setCraftingStatus();
            EntityService.pauseGame();
        }
    }

    public boolean inRange(Vector2 playerPosition) {
        if (ServiceLocator.getCraftArea().getGameAreaName().equals("Underground")) {
            if (playerPosition.dst(36, 15) < 3 || playerPosition.dst(11, 69) < 3 ||
                    playerPosition.dst(49, 81) < 3 || playerPosition.dst(90, 45) < 3) {
                return true;
            }
        } else {
            if (playerPosition.dst(100, 10) < 3 || playerPosition.dst(47, 100) < 3 ||
                    playerPosition.dst(96, 139) < 3 || playerPosition.dst(144, 111) < 3) {
                return true;
            }
        }
        return false;
    }

    public static void setCraftingStatus() {
        craftingStatus = !craftingStatus;
    }

    public static Boolean getCraftingStatus() {
        return craftingStatus;
    }
}