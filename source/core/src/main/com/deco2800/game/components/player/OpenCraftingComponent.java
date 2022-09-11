package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import com.deco2800.game.areas.ForestGameArea;
import org.slf4j.LoggerFactory;

import com.deco2800.game.entities.EntityService;
import org.testng.reporters.jq.Main;

import javax.swing.border.EmptyBorder;


public class OpenCraftingComponent extends Component {

    float craftingTableXCoord;
    float craftingTableYCoord;
    private static Logger logger;
    private static Boolean isOpen = false;


    public void create() {

        logger = LoggerFactory.getLogger(OpenCraftingComponent.class);
        this.craftingTableXCoord = (float)ForestGameArea.getCraftingTablePos().x;
        this.craftingTableYCoord = (float)ForestGameArea.getCraftingTablePos().y;

        entity.getEvents().addListener("can_open", this::openCrafting);
        entity.getEvents().addListener("can_close", this::closeCrafting);
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
        if (entity.getCenterPosition().dst(craftingTableXCoord, craftingTableYCoord) < 3 && isOpen == false) {
            ServiceLocator.getCraftArea().openCraftingMenu();
            isOpen = true;
            EntityService.pauseGame();
        }

    }

    private void closeCrafting() {
        if (isOpen == true) {
            ServiceLocator.getCraftArea().disposeCraftingMenu();
            isOpen = false;
            EntityService.pauseAndResume();
        }

    }

}

