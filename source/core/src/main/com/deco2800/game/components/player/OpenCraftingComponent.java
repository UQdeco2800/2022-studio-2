package com.deco2800.game.components.player;

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
        if (((ServiceLocator.getCraftArea().getGameAreaName().equals("Underground")
                && entity.getCenterPosition().dst(36, 15) < 3)
                || (ServiceLocator.getCraftArea().getGameAreaName().equals("Forest")
                && entity.getCenterPosition().dst(100, 10) < 3)) && craftingStatus == false) {
            ServiceLocator.getCraftArea().openCraftingMenu();
            ServiceLocator.getCraftArea().displayCatOne();
            setCraftingStatus();
            EntityService.pauseGame();
        }
    }

    public static void setCraftingStatus() {
        craftingStatus = !craftingStatus;
    }

    public static Boolean getCraftingStatus() {
        return craftingStatus;
    }
}