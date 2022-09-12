package com.deco2800.game.components.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.utils.math.Vector2Utils;

/**
 * Input handler for the player for keyboard and touch (mouse) input.
 * This input handler only uses keyboard input.
 */
public class KeyboardPlayerInputComponent extends InputComponent {
  private final Vector2 walkDirection = Vector2.Zero.cpy();

  public KeyboardPlayerInputComponent() {
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
      case Keys.W:
        walkDirection.add(Vector2Utils.UP);
        triggerWalkEvent();
        return true;
      case Keys.A:
        walkDirection.add(Vector2Utils.LEFT);
        triggerWalkEvent();
        return true;
      case Keys.S:
        walkDirection.add(Vector2Utils.DOWN);
        triggerWalkEvent();
        return true;
      case Keys.D:
        walkDirection.add(Vector2Utils.RIGHT);
        triggerWalkEvent();
        return true;
      case Keys.SPACE:
        entity.getEvents().trigger("attack");
        return true;
      case Keys.Q:
        entity.getEvents().trigger("can_open");
//        EntityService.pauseGame();
        return true;
      case Keys.L:
        System.out.println("9");
        entity.getEvents().trigger("can_close");
//        EntityService.pauseAndResume();
        return true;
      case Keys.E:
        entity.getEvents().trigger("skill");
        return true;
      case Keys.J:
        entity.getEvents().trigger("skill2");
        return true;
      case Keys.SHIFT_LEFT:
        entity.getEvents().trigger("dash");
        return true;
      case Keys.I:
        entity.getEvents().trigger("toggleInventory");
        return true;
      case Keys.NUM_1:
        entity.getEvents().trigger("consumePotionSlot1");
        return true;
      case Keys.NUM_2:
        entity.getEvents().trigger("consumePotionSlot2");
        return true;
      case Keys.NUM_3:
        entity.getEvents().trigger("consumePotionSlot3");
        return true;
      case Keys.ESCAPE:
        EntityService.pauseAndResume();
      case Keys.K:
        entity.getEvents().trigger("kill switch");
        return true;
      case Keys.M:
        entity.getEvents().trigger("toggleMinimap");
        return true;
      default:
        return false;
    }
  }

  /**
   * Triggers player events on specific keycodes.
   *
   * @return whether the input was processed
   * @see InputProcessor#keyUp(int)
   */
  @Override
  public boolean keyUp(int keycode) {
    switch (keycode) {
      case Keys.W:
        walkDirection.sub(Vector2Utils.UP);
        triggerWalkEvent();
        return true;
      case Keys.A:
        walkDirection.sub(Vector2Utils.LEFT);
        triggerWalkEvent();
        return true;
      case Keys.S:
        walkDirection.sub(Vector2Utils.DOWN);
        triggerWalkEvent();
        return true;
      case Keys.D:
        walkDirection.sub(Vector2Utils.RIGHT);
        triggerWalkEvent();
        return true;
      default:
        return false;
    }
  }

  private void triggerWalkEvent() {
    if (walkDirection.epsilonEquals(Vector2.Zero)) {
      entity.getEvents().trigger("walkStop");
      entity.getEvents().trigger("movementIdle");
    } else {
      entity.getEvents().trigger("walk", walkDirection);
      entity.getEvents().trigger("movementHandle", walkDirection);
    }
  }


}
