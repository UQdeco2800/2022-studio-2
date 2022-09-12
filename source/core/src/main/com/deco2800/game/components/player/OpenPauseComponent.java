package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.game.entities.EntityService;

public class OpenPauseComponent extends Component {
    private static Logger logger;
    private static Boolean isOpen = false;
    //private static Boolean craftingStatus = false;

    public void create() {

        logger = LoggerFactory.getLogger(OpenPauseComponent.class);

        entity.getEvents().addListener("game paused", this::openPauseMenu);
        entity.getEvents().addListener("game resumed", this::closePauseMenu);
        //entity.getEvents().addListener("is_opening", this::setCraftingStatus);
        //entity.getEvents().addListener("is_closed", this::setCraftingStatus);
    }

    private void openPauseMenu() {
        if (isOpen == false && OpenCraftingComponent.craftingStatus == false) {
            ServiceLocator.getPauseMenuArea().setPauseMenu();
            isOpen = true;
            EntityService.pauseGame();
        }
    }

    private void closePauseMenu() {
        if (isOpen == true) {
            ServiceLocator.getPauseMenuArea().disposePauseMenu();
            isOpen = false;
            EntityService.pauseAndResume();
        }
    }

    /*private void setCraftingStatus() {
        craftingStatus = !craftingStatus;
    }*/
}
