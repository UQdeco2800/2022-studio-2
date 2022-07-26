package com.deco2800.game.components.maingame;

import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to events relevant to the Main Game Screen and does something when one of the
 * events is triggered.
 */
public class MainGameActions extends Component {
  private static final Logger logger = LoggerFactory.getLogger(MainGameActions.class);
  private GdxGame game;

  public MainGameActions(GdxGame game) {
    this.game = game;
  }

  @Override
  public void create() {
    entity.getEvents().addListener("exit", this::onExit);
  }

  /**
   * Swaps to the Main Menu screen and clears the pause game status.
   */
  public void onExit() {
    logger.info("Exiting main game screen");
    EntityService.unpauseGame();
    game.setScreen(GdxGame.ScreenType.MAIN_MENU);
  }
}