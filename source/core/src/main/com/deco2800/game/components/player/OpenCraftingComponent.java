package com.deco2800.game.components.player;

import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.Component;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import com.deco2800.game.entities.EntityService;


public class OpenCraftingComponent extends Component {

    float craftingTableXCoord;
    float craftingTableYCoord;
    private static Logger logger;
    public static Boolean craftingStatus = false;


    public void create() {

        logger = LoggerFactory.getLogger(OpenCraftingComponent.class);
        this.craftingTableXCoord = (float) ForestGameArea.getCraftingTablePos().x;
        this.craftingTableYCoord = (float) ForestGameArea.getCraftingTablePos().y;

        entity.getEvents().addListener("can_open", this::openCrafting);

    }

    private void openCrafting() {
        if (entity.getCenterPosition().dst(craftingTableXCoord, craftingTableYCoord) < 3 && craftingStatus == false) {
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
