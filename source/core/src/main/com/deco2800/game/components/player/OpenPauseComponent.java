package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.components.maingame.OpenKeyBinds;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.game.entities.EntityService;

import java.util.ArrayList;

public class OpenPauseComponent extends Component {
    private static Logger logger = LoggerFactory.getLogger(OpenPauseComponent.class);
    private static Boolean pauseOpen;
    private static Boolean keyBindOpen;
    private static Boolean playerGuideOpen;
    private static Boolean inventoryToggled;
    private static ArrayList<String> playerGuideAssets = new ArrayList<>();
    private static ArrayList<String> playerGuideAssetslevel2 = new ArrayList<>();
    private OpenKeyBinds openKeyBinds;

    static {
        pauseOpen = false;
        keyBindOpen = false;
        playerGuideOpen = false;
        inventoryToggled = false;
    }

    @Override
    public void create() {

        openKeyBinds = new OpenKeyBinds();
        playerGuideAssets.add("images/Player Guide/level_1/1.png");
        playerGuideAssets.add("images/Player Guide/level_1/2.png");
        playerGuideAssets.add("images/Player Guide/level_1/3.png");
        playerGuideAssets.add("images/Player Guide/level_1/4.png");
        playerGuideAssets.add("images/Player Guide/level_1/5.png");
        playerGuideAssets.add("images/Player Guide/level_1/6.png");
        playerGuideAssets.add("images/Player Guide/level_1/7.png");
        playerGuideAssets.add("images/Player Guide/level_1/8.png");

        playerGuideAssetslevel2.add("images/Player Guide/level_2/1.png");
        playerGuideAssetslevel2.add("images/Player Guide/level_2/2.png");
        playerGuideAssetslevel2.add("images/Player Guide/level_2/3.png");
        playerGuideAssetslevel2.add("images/Player Guide/level_2/4.png");
        playerGuideAssetslevel2.add("images/Player Guide/level_2/5.png");
        playerGuideAssetslevel2.add("images/Player Guide/level_2/6.png");
        playerGuideAssetslevel2.add("images/Player Guide/level_2/7.png");
        playerGuideAssetslevel2.add("images/Player Guide/level_2/8.png");

        entity.getEvents().addListener("escInput", this::togglePauseMenu);
        entity.getEvents().addListener("toggleInventory", this::setInventoryStatus);


    }

    /**
     * Return the OpenKeyBinds component this utilises
     * @return  As above.
     */
    public OpenKeyBinds getOpenKeyBinds() { return openKeyBinds; }

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
        } else if (Boolean.FALSE.equals(pauseOpen)) {
            openPauseMenu();
        } else {
            closePauseMenu();
        }
    }

    private void setInventoryStatus() {
        inventoryToggled = !inventoryToggled;
    }

    /**
     * Utility function to OPEN pause window and PAUSE the game.
     */
    public static void openPauseMenu() {
        logger.info("Opening pause window");
        ServiceLocator.getPauseMenuArea().setPauseMenu();
        pauseOpen = true;
        if (Boolean.FALSE.equals(inventoryToggled)) { EntityService.pauseGame(); }
    }

    public static Boolean openPlayerGuide(Integer playerGuidePageNumber) {
        switch (playerGuidePageNumber) {
            case 1:
                logger.info("Turn to player guide page 1");
                ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssets.get(0));
                playerGuideOpen = true;
                return true;
            case 2:
                logger.info("Turn to player guide page 2");
                ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssets.get(1));
                playerGuideOpen = true;
                return true;
            case 3:
                logger.info("Turn to player guide page 3");
                ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssets.get(2));
                playerGuideOpen = true;
                return true;
            case 4:
                logger.info("Turn to player guide page 4");
                ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssets.get(3));
                playerGuideOpen = true;
                return true;
            case 5:
                logger.info("Turn to player guide page 5");
                ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssets.get(4));
                playerGuideOpen = true;
                return true;
            case 6:
                logger.info("Turn to player guide page 6");
                ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssets.get(5));
                playerGuideOpen = true;
                return true;
            case 7:
                logger.info("Turn to player guide page 7");
                ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssets.get(6));
                playerGuideOpen = true;
                return true;
            case 8:
                logger.info("Turn to player guide page 8");
                ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssets.get(7));
                playerGuideOpen = true;
                return true;
            default:
                return false;
        }
    }

    public static Boolean openPlayerGuideLevel2(Integer playerGuidePageNumber) {
        switch (playerGuidePageNumber) {
            case 1:
                logger.info("Turn to player guide page 1");
                ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssetslevel2.get(0));
                playerGuideOpen = true;
                return true;
            case 2:
                logger.info("Turn to player guide page 2");
                ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssetslevel2.get(1));
                playerGuideOpen = true;
                return true;
            case 3:
                logger.info("Turn to player guide page 3");
                ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssetslevel2.get(2));
                playerGuideOpen = true;
                return true;
            case 4:
                logger.info("Turn to player guide page 4");
                ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssetslevel2.get(3));
                playerGuideOpen = true;
                return true;
            case 5:
                logger.info("Turn to player guide page 5");
                ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssetslevel2.get(4));
                playerGuideOpen = true;
                return true;
            case 6:
                logger.info("Turn to player guide page 6");
                ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssetslevel2.get(5));
                playerGuideOpen = true;
                return true;
            case 7:
                logger.info("Turn to player guide page 7");
                ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssetslevel2.get(6));
                playerGuideOpen = true;
                return true;
            case 8:
                logger.info("Turn to player guide page 8");
                ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssetslevel2.get(7));
                playerGuideOpen = true;
                return true;
            default:
                return false;
        }
    }

//    public static void openPlayerGuide1() {
//        logger.info("Opening player guide menu 1");
//        ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssets.get(0));
//        playerGuideOpen = true;
//    }
//
//    public static void openPlayerGuide2() {
//        logger.info("Opening player guide menu 2");
//        ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssets.get(1));
//        playerGuideOpen = true;
//    }
//
//    public static void openPlayerGuide3() {
//        logger.info("Opening player guide menu 3");
//        ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssets.get(2));
//        playerGuideOpen = true;
//    }

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
        if (Boolean.FALSE.equals(inventoryToggled)) { EntityService.pauseAndResume(); }
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
}
