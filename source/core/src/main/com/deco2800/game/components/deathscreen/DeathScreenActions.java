package com.deco2800.game.components.deathscreen;

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
public class DeathScreenActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(DeathScreenActions.class);
    private final GdxGame game;
    private Sound sound;

    public DeathScreenActions(GdxGame game) {
        this.game = game;
    }

    /**
     * Creates the event listeners relevant to DeathScreen
     */
    @Override
    public void create() {
        entity.getEvents().addListener("continueGame", this::onContinue);
        entity.getEvents().addListener("exit", this::onExit);
    }

    /**
     * Restarts the game by reloading Main Game screen.
     */
    private void onContinue() {
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/ButtonSoundtrack.wav"));
        sound.play(1.0f);
        logger.info("Event to continue playing game after death occurred");
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }


    /**
     * Returns to main menu after death when exit is selected.
     */
    private void onExit() {
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/ButtonSoundtrack.wav"));
        sound.play(1.0f);
        logger.info("Event to return to main menu occurred");
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
    }
}
