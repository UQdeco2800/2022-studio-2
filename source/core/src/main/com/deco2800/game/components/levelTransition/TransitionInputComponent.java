package com.deco2800.game.components.levelTransition;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.deco2800.game.components.Component;
import com.deco2800.game.input.InputComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Input handler for the player for keyboard and touch (mouse) input.
 * This input handler only uses keyboard input.
 */
public class TransitionInputComponent extends InputComponent {

  private static final Logger logger = LoggerFactory.getLogger(Component.class);

  public TransitionInputComponent() {
    super(5);
  }

  /**
   * Triggers player events on specific keycodes.
   *
   * @return whether the input was processed
   * @see InputProcessor#keyDown(int)
   */
  @Override
  public boolean keyDown(int keycode) {
    switch (keycode) {
      case Keys.ENTER:
          logger.info("Exiting transition screen");
          entity.getEvents().trigger("mapTransition");
        return true;
      default:
        return false;
    }
  }
}