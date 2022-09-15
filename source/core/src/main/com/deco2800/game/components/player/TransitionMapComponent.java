package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that handles the transition between maps - Team 5 1map4all @otili9890
 */
public class TransitionMapComponent extends Component {
    private static Logger logger;

    public void create() {

        logger = LoggerFactory.getLogger(TransitionMapComponent.class);
        entity.getEvents().addListener("nextMap", this::nextMap);
    }

    /**
     * Determines which is the next map to transition too based on the current map
     * - Team 5 1map4all @otili9890.
     * @return (bool) true if map transition occurs, else false
     */
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
}