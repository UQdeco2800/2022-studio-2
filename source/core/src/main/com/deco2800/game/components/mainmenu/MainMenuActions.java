package com.deco2800.game.components.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import com.deco2800.game.screens.MainMenuTransitionScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to events relevant to the Main Menu Screen and does something when one of the
 * events is triggered.
 */
public class MainMenuActions extends Component {
  private static final Logger logger = LoggerFactory.getLogger(MainMenuActions.class);
  private GdxGame game;
  private Sound sound;

  public MainMenuActions(GdxGame game) {
    this.game = game;
  }

  @Override
  public void create() {
    entity.getEvents().addListener("start", this::onStart);
    entity.getEvents().addListener("load", this::onLoad);
    entity.getEvents().addListener("exit", this::onExit);
    entity.getEvents().addListener("settings", this::onSettings);
  }

  /**
   * Swaps to the Main Game screen.
   */
  private void onStart() {
    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/ButtonSoundtrack.wav"));
    sound.play(1.0f);
    logger.info("Start game");
    game.setScreen(GdxGame.ScreenType.MAIN_GAME);
  }

  /**
   * Intended for loading a saved game state.
   * Load functionality is not actually implemented.
   */
  private void onLoad() {
    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/ButtonSoundtrack.wav"));
    sound.play(1.0f);
    logger.info("Load game");
    game.setScreen(GdxGame.ScreenType.MENU_TRANSITION);
  }

  /**
   * Exits the game.
   */
  private void onExit() {
    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/ButtonSoundtrack.wav"));
    sound.play(1.0f);
    logger.info("Exit game");
    game.exit();
  }

  /**
   * Swaps to the Settings screen.
   */
  private void onSettings() {
    Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/ButtonSoundtrack.wav"));
    sound.play(1.0f);
    logger.info("Launching settings screen");
    game.setScreen(GdxGame.ScreenType.SETTINGS);
  }
}
