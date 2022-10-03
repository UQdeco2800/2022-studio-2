package com.deco2800.game.entities;

import com.badlogic.gdx.utils.Array;
import com.deco2800.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides a global access point for entities to register themselves. This allows for iterating
 * over entities to perform updates each loop. All game entities should be registered here.
 *
 * Avoid adding additional state here! Global access is often the easy but incorrect answer to
 * sharing data.
 */
public class EntityService {
  private static final Logger logger = LoggerFactory.getLogger(EntityService.class);
  private static final int INITIAL_CAPACITY = 16;
  private static boolean paused = false;
  private static boolean pauseStartFlag = false;
  private static boolean pauseEndFlag = false;
  private static boolean entityTimeStop = false;

  private final Array<Entity> entities = new Array<>(false, INITIAL_CAPACITY);

  /**
   * Register a new entity with the entity service. The entity will be created and start updating.
   * @param entity new entity.
   */
  public void register(Entity entity) {
    logger.debug("Registering {} in entity service", entity);
    entities.add(entity);
    entity.create();
  }

  public Array<Entity> getEntityList() {
    return entities;
  }

  /**
   * Unregister an entity with the entity service. The entity will be removed and stop updating.
   * @param entity entity to be removed.
   */
  public void unregister(Entity entity) {
    logger.debug("Unregistering {} in entity service", entity);
    entities.removeValue(entity, true);
  }

  /**
   * Update all registered entities. Should only be called from the main game loop.
   */
  public void update() {
    for (Entity entity : entities) {
      if (!paused) {
        if(pauseEndFlag) {
          entity.togglePauseAnimations(true);
        }
        entity.earlyUpdate();
        entity.update();
      }
      if(pauseStartFlag) {
          entity.togglePauseAnimations(true);
      }
    }
    pauseStartFlag = false;
    pauseEndFlag = false;
  }

  /**
   * Pause and resume the game. Used for main pausing function.
   */
  public static void pauseAndResume() {
    paused = !paused;
    if (paused) {
      pauseStartFlag = true;
    } else {
      pauseEndFlag = true;
    }
  }

  /**
   * Pause the game. Used for when the player is interacting with the inventory or UI.
   */
  public static void pauseGame() {
    paused = true;
    pauseStartFlag = true;
  }

  /**
   * Unpause the game. Called when the exit button is clicked.
   * NOTE: this method is meant to fix a minor bug of UI interactions.
   */
  public static void unpauseGame() {
    paused = false;
    pauseEndFlag = true;
  }

  public static Boolean pauseCheck() {
    return paused;
  }

  public void toggleTimeStop() {
    entityTimeStop = !entityTimeStop;
    for (Entity entity : entities) {
      entity.togglePauseAnimations(false);
    }
  }

  public static boolean isTimeStopped() {
    return entityTimeStop;
  }

  /**
   * Dispose all entities.
   */
  public void dispose() {
    for (Entity entity : entities) {
      entity.dispose();
    }
  }
}