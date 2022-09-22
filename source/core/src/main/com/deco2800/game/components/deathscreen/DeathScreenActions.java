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
    private GdxGame game;
    private Sound sound;

    public DeathScreenActions(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        entity.getEvents().addListener("continueGame", this::onContinue);
        entity.getEvents().addListener("exit", this::onExit);
    }

    /**
     * Swaps to the Main Game screen.
     */
    private void onContinue() {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/ButtonSoundtrack.wav"));
        sound.play(1.0f);
        logger.info("Continue playing game after death");
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }


    /**
     * Returns to main menu after death when exit is selected.
     */
    private void onExit() {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/ButtonSoundtrack.wav"));
        sound.play(1.0f);
        logger.info("Return to main menu");
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
    }
}
