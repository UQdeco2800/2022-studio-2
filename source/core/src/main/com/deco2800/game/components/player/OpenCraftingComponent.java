package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.UndergroundGameArea;

import org.slf4j.LoggerFactory;

import com.deco2800.game.entities.EntityService;
import org.testng.reporters.jq.Main;


public class OpenCraftingComponent extends Component {
    private static Logger logger;
    public static Boolean craftingStatus = false;

    public void create() {

        logger = LoggerFactory.getLogger(OpenCraftingComponent.class);
        entity.getEvents().addListener("can_open", this::openCrafting);
        entity.getEvents().addListener("nextMap", this::nextMap);

    }

    private boolean nextMap() {
        int mapLevel;
        MainGameScreen gameScreen = ServiceLocator.getMainGameScreen();

        if (gameScreen.getClass() == MainGameScreen.class) {
            logger.info("class of current map: " + gameScreen.getMap().toString());
            switch (gameScreen.getMap().toString()) {
                case "ForestGameArea":
                    mapLevel = 2;
                    logger.info("chooseMap: " + mapLevel);
                    gameScreen.chooseMap(mapLevel);
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }



    //Hi Ly, sry I touched ur method, cuz im trying to reduce the crafting range,
    // I changed it from 10 to 3, i think this is a reasonable rang.
    private void openCrafting() {
        if (entity.getCenterPosition().dst(15, 15) < 3 && craftingStatus == false) {
            ServiceLocator.getCraftArea().openCraftingMenu();
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