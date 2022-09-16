package com.deco2800.game.components.deathscreen;

import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to events relevant to the Main Game Screen and does something when one of the
 * events is triggered.
 */
public class DeathScreenActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(DeathScreenActions.class);
    private GdxGame game;

    public DeathScreenActions(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        entity.getEvents().addListener("exit", this::onExit);
    }

    /**
     * Swaps to the Main Menu screen.
     */
    private void onExit() {
        logger.info("Exiting main game screen");
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }
}
