package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import com.deco2800.game.components.areas.ForestGameArea;
import org.slf4j.LoggerFactory;

import com.deco2800.game.entities.EntityService;


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

