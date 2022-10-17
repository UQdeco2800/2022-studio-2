package com.deco2800.game.components.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.areas.UndergroundGameArea;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.Component;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.utils.math.Vector2Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Input handler for the player for keyboard and touch (mouse) input.
 * This input handler only uses keyboard input.
 */
public class KeyboardPlayerInputComponent extends InputComponent {
  private final Vector2 walkDirection = Vector2.Zero.cpy();
  private static int keyPressedCounter;
  private static boolean menuOpened = false;
  private static Enum currentMenu = MenuTypes.NONE;
  public enum MenuTypes{
    INVENTORY,
    CRAFTING,
    MINIMAP,
    PAUSEMENU,
    NONE
  }

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
        entity.getEvents().trigger("attackEnemy");
        return true;
      case Keys.Q:
        if (currentMenu == MenuTypes.NONE
                || currentMenu == MenuTypes.CRAFTING) {
          currentMenu = MenuTypes.CRAFTING;
          entity.getEvents().trigger("can_open");
          menuOpened = !menuOpened;
          if (!menuOpened) currentMenu = MenuTypes.NONE;
          return true;
        }
      case Keys.J:
        entity.getEvents().trigger("skill");
        return true;
      case Keys.K:
        entity.getEvents().trigger("skill2");
        return true;
      case Keys.L:
        entity.getEvents().trigger("skill3");
        return true;
      case Keys.SHIFT_LEFT:
        entity.getEvents().trigger("dash");
        return true;
      case Keys.I:
        if (currentMenu == MenuTypes.NONE
        || currentMenu == MenuTypes.INVENTORY) {
          currentMenu = MenuTypes.INVENTORY;
          entity.getEvents().trigger("toggleInventory");
          menuOpened = !menuOpened;
          if (!menuOpened) currentMenu = MenuTypes.NONE;
          return true;
        }
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
        if (currentMenu == MenuTypes.NONE
                || currentMenu == MenuTypes.PAUSEMENU) {
          currentMenu = MenuTypes.PAUSEMENU;
          entity.getEvents().trigger("escInput");
          return true;
        }
      case Keys.M:
        if (currentMenu == MenuTypes.NONE
                || currentMenu == MenuTypes.MINIMAP) {
          currentMenu = MenuTypes.MINIMAP;
          entity.getEvents().trigger("toggleMinimap");
          menuOpened = !menuOpened;
          if (!menuOpened) currentMenu = MenuTypes.NONE;
          return true;
        }
      case Keys.X:
        entity.getEvents().trigger("EquipWeapon");
        return true;
      case Keys.Y:
        entity.getEvents().trigger("dropWeapon");
        // Determines if the player is near the plug when enter is hit, transitions to next map
      case Keys.ENTER:
        // The coordinates below are for the new plug hole to go to forest game area
        //transition state for level 1

        if (ServiceLocator.getGameArea().getClass() == ForestGameArea.class &&
                (entity.getPosition().x > 174 && entity.getPosition().x < 176) &&
                  (entity.getPosition().y > 54 && entity.getPosition().y < 55)
                  && (ForestGameArea.ifHeraclesOnMap())) {
            entity.getEvents().trigger("mapTransition");
        }
        // Win logic for level 2
        if (ServiceLocator.getGameArea().getClass() == UndergroundGameArea.class &&
                UndergroundGameArea.ifMegaPoopOnMap() &&
                (entity.getPosition().x > 37 && entity.getPosition().x < 39) &&
                (entity.getPosition().y > 114 && entity.getPosition().y < 116)) {
            logger.info("win state triggered");
            entity.getEvents().trigger("win");
        }

        return true;
      case Keys.N:
        // Use to skip to level 2.
        entity.getEvents().trigger("mapTransition");
        return true;
      case Keys.B:
        // Use to skip to win screen.
        entity.getEvents().trigger("win");
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

  public static void setCurrentMenu(Boolean opened, MenuTypes menu) {
    menuOpened = opened;
    currentMenu = menu;
  }

  public static void clearMenuOpening() {
    menuOpened = false;
    currentMenu = MenuTypes.NONE;
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