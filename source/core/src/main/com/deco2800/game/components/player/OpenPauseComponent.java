package com.deco2800.game.components.player;

import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.maingame.OpenKeyBinds;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.game.entities.EntityService;

public class OpenPauseComponent extends Component {
    private static Logger logger;
    //private static Boolean isOpen = false;
    private static Boolean pauseOpen;
    private static Boolean keyBindOpen;
    private static Boolean playerGuideOpen;
    private static Boolean inventoryToggled;

    private static Boolean minimapToggled;

    public static OpenKeyBinds openKeyBinds;

    //private static Boolean craftingStatus = false;
//    private GdxGame game;

    public void create() {

        openKeyBinds = new OpenKeyBinds();

        logger = LoggerFactory.getLogger(OpenPauseComponent.class);
        entity.getEvents().addListener("escInput", this::togglePauseMenu);
        entity.getEvents().addListener("toggleInventory", this::setInventoryStatus);
        entity.getEvents().addListener("toggleMinimap", this::setMinimapStatus);
        pauseOpen = false;
        keyBindOpen = false;
        playerGuideOpen = false;
        inventoryToggled = false;
        minimapToggled = false;
//        entity.getEvents().addListener("game paused", this::openPauseMenu);
//        entity.getEvents().addListener("game resumed", this::closePauseMenu);
        //entity.getEvents().addListener("is_opening", this::setCraftingStatus);
        //entity.getEvents().addListener("is_closed", this::setCraftingStatus);
    }


    private void setMinimapStatus() {
        minimapToggled = !minimapToggled;
    }

    /**
     * Handler function for any escape input key-press registered by the player
     * entity.
     * If the keybinding menu is open, close it. As we do not want to close the
     * pause menu if this is open, this has first priority.
     * If the pause menu is not open, open it.
     * If the pause menu IS open, close it.
     */
    private void togglePauseMenu() {
        if (keyBindOpen || playerGuideOpen) {
            closeKeyBindings();
            closePlayerGuide();
        } else if (!pauseOpen) {
            openPauseMenu();
        } else if (pauseOpen) {
            closePauseMenu();
        }
    }

    /**
     * Utility function to OPEN pause window and PAUSE the game.
     */
    public static void openPauseMenu() {
        logger.info("Opening pause window");
        ServiceLocator.getPauseMenuArea().setPauseMenu();
        pauseOpen = true;
        if (!inventoryToggled) { EntityService.pauseGame(); }
    }

    public static void openPlayerGuide() {
        logger.info("Opening player guide menu");
        ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu();
        playerGuideOpen = true;
    }

    public static void closePlayerGuide() {
        logger.info("Closing player guide menu");
        ServiceLocator.getPlayerGuidArea().disposePlayerGuideMenu();
        playerGuideOpen = false;
    }
    /**
     * Utility function to CLOSE pause window and UNPAUSE the game.
     */
    public static void closePauseMenu() {
        logger.info("Closing pause window");
        ServiceLocator.getPauseMenuArea().disposePauseMenu();
        pauseOpen = false;
        if (!inventoryToggled) { EntityService.pauseAndResume(); }
    }

    /**
     * Utility function to OPEN key binding window.
     */
    public static void openKeyBindings() {
        logger.info("Opening key binding window");
        ServiceLocator.getKeyBindArea().setKeyBindMenu();
        keyBindOpen = true;
    }

    /**
     * Utility function to CLOSE key binding window.
     */
    private static void closeKeyBindings() {
        logger.info("Closing key binding window");
        ServiceLocator.getKeyBindArea().disposeKeyBindMenu();
        keyBindOpen = false;
    }

//    static Boolean getPauseOpen() {
//        return pauseOpen;
//    }

    private void setInventoryStatus() {
        inventoryToggled = !inventoryToggled;
    }
//
//    public static boolean getPausingStatus() {
//        return isOpen;
//    }
//
//    private boolean getInventoryStatus() {
//        return inventoryToggled;
//    }

    //    private void openPauseMenu() {
//        if (isOpen == false && OpenCraftingComponent.craftingStatus == false && !inventoryToggled) {
//            ServiceLocator.getPauseMenuArea().setPauseMenu();
//            isOpen = true;
//            EntityService.pauseGame();
//        }
//    }
//
//    private void closePauseMenu() {
//        if (isOpen == true) {
//            ServiceLocator.getPauseMenuArea().disposePauseMenu();
//            isOpen = false;
//            EntityService.pauseAndResume();
//        }
//    }

//    public static void setPauseMenuStatus() {
//        isOpen = !isOpen;
//    }
}
