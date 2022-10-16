package com.deco2800.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.deco2800.game.files.UserSettings;
import com.deco2800.game.screens.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.badlogic.gdx.Gdx.app;

/**
 * Entry point of the non-platform-specific game logic. Controls which screen is currently running.
 * The current screen triggers transitions to other screens. This works similarly to a finite state
 * machine (See the State Pattern).
 */
public class GdxGame extends Game {
  private static final Logger logger = LoggerFactory.getLogger(GdxGame.class);

  private static MainGameScreen currentGameScreen;
  private static DeathScreen deathScreen;
  private static LevelTransitionScreen levelTransitionScreen;
  private static MainMenuTransitionScreen mainMenuTransitionScreen;

  private static int level;

  @Override
  public void create() {
    logger.info("Creating game");
    loadSettings();

    // Sets background to blue colour for level 1
    Gdx.gl.glClearColor(79/255f, 174/255f, 177/255f, 1);

    setScreen(ScreenType.MAIN_MENU);

    level = 1;
  }

  /**
   * Loads the game's settings.
   */
  private void loadSettings() {
    logger.debug("Loading game settings");
    UserSettings.Settings settings = UserSettings.get();
    UserSettings.applySettings(settings);
  }

  /**
   * Sets the game's screen to a new screen of the provided type.
   * @param screenType screen type
   */
  public void setScreen(ScreenType screenType) {
    logger.info("Setting game screen to {}", screenType);
    Screen currentScreen = getScreen();
    if (currentScreen != null) {
      currentScreen.dispose();
    }
    setScreen(newScreen(screenType));
  }

  @Override
  public void dispose() {
    logger.debug("Disposing of current screen");
    getScreen().dispose();
  }

  /**
   * Create a new screen of the provided type.
   * @param screenType screen type
   * @return new screen
   */
  private Screen newScreen(ScreenType screenType) {
    switch (screenType) {
      case MAIN_MENU:
        level = 1;
        return new MainMenuScreen(this);
      case MAIN_GAME:
        currentGameScreen = new MainGameScreen(this, level);
        return currentGameScreen;
      case SETTINGS:
        return new SettingsScreen(this);
      case DEATH_SCREEN_L1:
        level = 1;
        deathScreen = new DeathScreen(this, 1);
        return deathScreen;
      case DEATH_SCREEN_L2:
        level = 2;
        deathScreen = new DeathScreen(this, 2);
        return deathScreen;
      case LEVEL_TRANSITION:
        level = 2;
        // Sets background to brown colour for level 2
        Gdx.gl.glClearColor(69/255f, 62/255f, 58/255f, 2);
        levelTransitionScreen = new LevelTransitionScreen(this);
        return levelTransitionScreen;
      case WIN_SCREEN:
        deathScreen = new DeathScreen(this, 3);
        return  deathScreen;
      case MENU_TRANSITION:
        level = 1;
        mainMenuTransitionScreen = new MainMenuTransitionScreen(this);
        return mainMenuTransitionScreen;
      default:
        return null;
    }
  }

  public enum ScreenType {
    MAIN_MENU, MAIN_GAME, SETTINGS, DEATH_SCREEN_L1, DEATH_SCREEN_L2, SkillsTree, LEVEL_TRANSITION, WIN_SCREEN,
    MENU_TRANSITION
  }

  /**
   * Exit the game.
   */
  public void exit() {
    app.exit();
  }
}