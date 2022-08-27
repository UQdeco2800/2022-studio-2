package com.deco2800.game.crafting.craftingDisplay;

import com.deco2800.game.GdxGame;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.components.Component;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CraftingMenuActions extends Component {

    private static Boolean menu = false;

    private static final Logger logger = LoggerFactory.getLogger(CraftingMenuActions.class);
    private GdxGame game;

    public CraftingMenuActions(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        entity.getEvents().addListener("open_crafting", this::openMenu);
    }

    private void openMenu() {
        ServiceLocator.getGameArea().spawnCraftingMenu();
        System.out.println("1");
        logger.info("Open menu");
       // game.setScreen(GdxGame.ScreenType.CRAFTING_MENU);


    }

    public static Boolean getMenuReady(){
        return menu;
    }

}
