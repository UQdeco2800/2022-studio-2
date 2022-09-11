package com.deco2800.game.components.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.utils.math.Vector2Utils;

import java.security.Provider;

/**
 * Input handler for the player for keyboard and touch (mouse) input.
 * This input handler only uses keyboard input.
 */
public class KeyboardPlayerInputComponent extends InputComponent {
  private final Vector2 walkDirection = Vector2.Zero.cpy();
  private int keyPressedCounter = 1;

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
      case Keys.N:
        entity.getEvents().trigger("attack2");
        return true;
      case Keys.Q:
        entity.getEvents().trigger("can_open");
//        EntityService.pauseGame();
        return true;
      case Keys.E:
        entity.getEvents().trigger("skill");
        // Temporary projectile shoot on skill activation
        if (ServiceLocator.getGameArea().getClass() == ForestGameArea.class) {
          ((ForestGameArea) ServiceLocator.getGameArea()).spawnPlayerProjectileSpray();
        }
        return true;
      case Keys.J:
        entity.getEvents().trigger("skill2");
        if (ServiceLocator.getGameArea().getClass() == ForestGameArea.class) {
          ((ForestGameArea) ServiceLocator.getGameArea()).spawnPlayerProjectile();
        }
        return true;
      case Keys.SHIFT_LEFT:
        entity.getEvents().trigger("dash");
        return true;
      case Keys.I:
        entity.getEvents().trigger("toggleInventory");
        return true;
//      case Keys.NUM_1:
//        entity.getEvents().trigger("consumePotionSlot1");
//        return true;
//      case Keys.NUM_2:
//        entity.getEvents().trigger("consumePotionSlot2");
//        return true;
//      case Keys.NUM_3:
//        entity.getEvents().trigger("consumePotionSlot3");
//        return true;
      case Keys.ESCAPE:
        if (!OpenCraftingComponent.getCraftingStatus()) {
          keyPressedCounter++;
        }
        if (keyPressedCounter % 2 == 0) {
          entity.getEvents().trigger("game paused");
          return true;
        }
        entity.getEvents().trigger("game resumed");
        return true;
      case Keys.K:
        entity.getEvents().trigger("kill switch");
        return true;
      case Keys.M:
        entity.getEvents().trigger("toggleMinimap");
        return true;
      case Keys.X:
        entity.getEvents().trigger("EquipWeapon");
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