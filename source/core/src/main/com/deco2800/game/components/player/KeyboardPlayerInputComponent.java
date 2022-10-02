package com.deco2800.game.components.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.components.Component;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.utils.math.Vector2Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Provider;

/**
 * Input handler for the player for keyboard and touch (mouse) input.
 * This input handler only uses keyboard input.
 */
public class KeyboardPlayerInputComponent extends InputComponent {
  private final Vector2 walkDirection = Vector2.Zero.cpy();
  private static int keyPressedCounter = 1;

  private static final Logger logger = LoggerFactory.getLogger(Component.class);

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
      case Keys.E:
        entity.getEvents().trigger("skill");
        // Temporary projectile shoot on skill activation
        if (ServiceLocator.getGameArea().getClass() == ForestGameArea.class) {
          ((ForestGameArea) ServiceLocator.getGameArea()).spawnPlayerProjectileCone();
        }
        ServiceLocator.getEntityService().toggleTimeStop();
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
      case Keys.EQUALS: // temp mapping for sprint 2 marking
        entity.getEvents().trigger("skillTemp");
        return true;
      case Keys.BACKSLASH:
        entity.getEvents().trigger("ultimateTemp");
        return true;
      case Keys.BACKSPACE:
        entity.getEvents().trigger("attackspeedTemp");
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
        entity.getEvents().trigger("escInput");
        return true;
//        if (!OpenCraftingComponent.getCraftingStatus()) {
//
//        }
//        if (keyPressedCounter % 2 == 0) {
//          entity.getEvents().trigger("escape input");
//          return true;
//        }
//        entity.getEvents().trigger("game resumed");
//        return true;
      case Keys.K:
        entity.getEvents().trigger("kill switch");
        return true;
      case Keys.M:
        entity.getEvents().trigger("toggleMinimap");
        return true;
      case Keys.X:
        entity.getEvents().trigger("EquipWeapon");
        return true;
      case Keys.Y:
        entity.getEvents().trigger("dropWeapon");
        // Determines if the player is near the plug when enter is hit, transitions to next map
      case Keys.ENTER:
        if ((entity.getPosition().x > 11 && entity.getPosition().x < 13) &&
                (entity.getPosition().y > 16 && entity.getPosition().y < 18)
                 && (ForestGameArea.ifHeraclesOnMap()))
        {
          entity.getEvents().trigger("mapTransition");
          entity.getEvents().trigger("nextMap");
        }
        return true;
      default:
        return false;
    }
  }


  public static void incrementPauseCounter(){
    keyPressedCounter++;
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