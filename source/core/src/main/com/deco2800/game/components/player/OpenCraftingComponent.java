package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import com.deco2800.game.entities.EntityService;


public class OpenCraftingComponent extends Component {
    private static Logger logger;
    public static Boolean craftingStatus = false;

    public void create() {

        logger = LoggerFactory.getLogger(OpenCraftingComponent.class);
        entity.getEvents().addListener("can_open", this::openCrafting);

    }

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