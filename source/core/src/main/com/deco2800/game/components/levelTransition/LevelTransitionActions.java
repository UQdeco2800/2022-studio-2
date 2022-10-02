package com.deco2800.game.components.levelTransition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to events relevant to the Main Menu Screen and does something when one of the
 * events is triggered.
 */
public class LevelTransitionActions extends Component {
    private static Logger logger;
    private GdxGame game;

    public LevelTransitionActions(GdxGame game) {
        this.game = game;
    }

    /**
     * Creates the event listeners relevant to the transition screen
     */
    @Override
    public void create() {
        logger = LoggerFactory.getLogger(LevelTransitionActions.class);
        entity.getEvents().addListener("mapTransition", this::mapTransition);
    }

    /**
     * Changes from the transition screen back to the main game.
     */
    private void mapTransition() {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/ButtonSoundtrack.wav"));
        sound.play(1.0f);
        logger.info("Enter key has beeen presssed, changing to next level");
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }
}
