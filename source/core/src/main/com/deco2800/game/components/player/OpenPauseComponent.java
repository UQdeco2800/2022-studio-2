package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import com.deco2800.game.components.areas.ForestGameArea;
import org.slf4j.LoggerFactory;

import com.deco2800.game.entities.EntityService;

public class OpenPauseComponent extends Component {
    private static Logger logger;
    private static Boolean isOpen = false;

    public void create() {

        logger = LoggerFactory.getLogger(OpenPauseComponent.class);

        entity.getEvents().addListener("game paused", this::openPauseMenu);
        entity.getEvents().addListener("game resumed", this::closePauseMenu);
    }

    private void openPauseMenu() {
        if (isOpen == false) {
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
}
