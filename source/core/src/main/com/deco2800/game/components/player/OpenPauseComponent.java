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
    private Boolean pauseOpen;
    private Boolean keyBindOpen;
    private Boolean playerGuideOpen;
    private Boolean inventoryToggled;
    private ArrayList<String> playerGuideAssets = new ArrayList<>();
    private ArrayList<String> playerGuideAssetslevel2 = new ArrayList<>();
    private OpenKeyBinds openKeyBinds;

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

        pauseOpen = false;
        keyBindOpen = false;
        playerGuideOpen = false;
        inventoryToggled = false;
    }

    /**
     * Utility function to get pause menu open status.
     * @return  True if pause menu is open, else false
     */
    public Boolean getPauseOpen() { return pauseOpen; }

    /**
     * Utility function to get keybind menu open status.
     * @return  True if pause menu is open, else false
     */
    public Boolean getKeyBindOpen() { return keyBindOpen; }


    /**
     * Utility function to get player guide menu open status.
     * @return  True if pause menu is open, else false
     */
    public Boolean getPlayerGuideOpen() { return playerGuideOpen; }

    /**
     * Utility function to get inventory menu open status.
     * @return  True if pause menu is open, else false
     */
    public Boolean getInventoryToggled() { return inventoryToggled; }

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
        if (keyBindOpen) {
            closeKeyBindings();
        }else if (playerGuideOpen) {
            closePlayerGuide();
        } else if (pauseOpen) {
            closePauseMenu();
        } else {
            openPauseMenu();
        }
    }

    /**
     * Utility function to set the inventory open status boolean.
     */
    private void setInventoryStatus() {
        inventoryToggled = !inventoryToggled;
    }

    /**
     * Utility function to OPEN pause window and PAUSE the game.
     */
    public void openPauseMenu() {
        logger.info("Opening pause window");
        ServiceLocator.getPauseMenuArea().setPauseMenu();
        pauseOpen = true;
        if (Boolean.FALSE.equals(inventoryToggled)) { EntityService.pauseGame(); }
    }


    /**
     * Open the player guide menu and appropriately set the information page
     * and asset based on the given pageNumber and level.
     *
     * @param level         game level (either 1 or 2)
     * @param pageNumber    number of the page, 1 indexing (page 1 = 1, page 1 != 0)
     * @return              True on success else false
     */
    public Boolean openPlayerGuide(int level, int pageNumber) {

        if (pageNumber > 8) {
            logger.error("Invalid player guide page given");
            return false;
        }

        if (level == 1) {
            ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssets.get(pageNumber - 1));
        } else if (level == 2) {
            ServiceLocator.getPlayerGuidArea().setPlayerGuideMenu(playerGuideAssetslevel2.get(pageNumber - 1));
        } else {
            logger.error("Invalid player guide level given");
            return false;
        }

        playerGuideOpen = true;
        logger.info("Turning to player guide level {} page {}", level, pageNumber);
        return true;
    }

    /**
     * Utility function to CLOSE pause player guide window.
     */
    public void closePlayerGuide() {
        logger.info("Closing player guide menu");
        ServiceLocator.getPlayerGuidArea().disposePlayerGuideMenu();
        playerGuideOpen = false;
    }

    /**
     * Utility function to CLOSE pause window and UNPAUSE the game.
     */
    public void closePauseMenu() {
        logger.info("Closing pause window");
        ServiceLocator.getPauseMenuArea().disposePauseMenu();
        pauseOpen = false;
        if (Boolean.FALSE.equals(inventoryToggled)) { EntityService.pauseAndResume(); }
    }

    /**
     * Utility function to OPEN key binding window.
     */
    public void openKeyBindings() {
        logger.info("Opening key binding window");
        ServiceLocator.getKeyBindArea().setKeyBindMenu();
        keyBindOpen = true;
    }

    /**
     * Utility function to CLOSE key binding window.
     */
    public void closeKeyBindings() {
        logger.info("Closing key binding window");
        ServiceLocator.getKeyBindArea().disposeKeyBindMenu();
        keyBindOpen = false;
    }
}
