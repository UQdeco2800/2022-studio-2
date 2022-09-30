package com.deco2800.game.components.levelTransition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.player.TransitionMapComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to events relevant to the Main Menu Screen and does something when one of the
 * events is triggered.
 */
public class LevelTransitionActions extends Component {
    private static Logger logger;
    private GdxGame game;
    private Sound sound;

    public LevelTransitionActions(GdxGame game) {
        this.game = game;
    }

    /**
     * Creates the event listeners relevant to DeathScreen
     */
    @Override
    public void create() {
        logger = LoggerFactory.getLogger(LevelTransitionActions.class);
        entity.getEvents().addListener("levelChanged", this::levelChanged);
    }

    /**
     * Changes screen type to level transition screen.
     */
    private void levelChanged() {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/ButtonSoundtrack.wav"));
        sound.play(1.0f);
        logger.info("Level has changed, transition screen will now display");
        game.setScreen(GdxGame.ScreenType.LEVEL_TRANSITION);
    }
}
