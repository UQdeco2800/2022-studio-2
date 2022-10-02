package com.deco2800.game.services;

import com.badlogic.gdx.Screen;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.screens.DeathScreen;
import com.deco2800.game.screens.LevelTransitionScreen;
import com.deco2800.game.screens.MainGameScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.reporters.jq.Main;

/**
 * A simplified implementation of the Service Locator pattern:
 * https://martinfowler.com/articles/injection.html#UsingAServiceLocator
 *
 * <p>Allows global access to a few core game services.
 * Warning: global access is a trap and should be used <i>extremely</i> sparingly.
 * Read the wiki for details (https://github.com/UQdeco2800/game-engine/wiki/Service-Locator).
 */
public class ServiceLocator {
  private static final Logger logger = LoggerFactory.getLogger(ServiceLocator.class);
  private static EntityService entityService;

  private static RenderService renderService;
  private static PhysicsService physicsService;
  private static GameTime timeSource;
  private static InputService inputService;
  private static ResourceService resourceService;

  private static GameArea gameArea;

  private static MainGameScreen mainGameScreen;

  private static GameAreaDisplay craftArea;
  private static GameAreaDisplay pauseMenuArea;
  private static GameAreaDisplay inventoryArea;
  private static GameAreaDisplay keyBindArea;

  public static GameArea getGameArea() {return gameArea;}
  public static EntityService getEntityService() {
    return entityService;
  }

  public static RenderService getRenderService() {
    return renderService;
  }

  public static PhysicsService getPhysicsService() {
    return physicsService;
  }

  public static GameTime getTimeSource() {
    return timeSource;
  }

  public static InputService getInputService() {
    return inputService;
  }

  public static ResourceService getResourceService() {
    return resourceService;
  }

  public static void registerEntityService(EntityService service) {
    logger.debug("Registering entity service {}", service);
    entityService = service;
  }

  public static void registerRenderService(RenderService service) {
    logger.debug("Registering render service {}", service);
    renderService = service;
  }

  public static void registerPhysicsService(PhysicsService service) {
    logger.debug("Registering physics service {}", service);
    physicsService = service;
  }

  public static void registerTimeSource(GameTime source) {
    logger.debug("Registering time source {}", source);
    timeSource = source;
  }

  public static void registerInputService(InputService source) {
    logger.debug("Registering input service {}", source);
    inputService = source;
  }

  public static void registerResourceService(ResourceService source) {
    logger.debug("Registering resource service {}", source);
    resourceService = source;
  }

  public static void clear() {
    entityService = null;
    renderService = null;
    physicsService = null;
    timeSource = null;
    inputService = null;
    resourceService = null;
  }

  private ServiceLocator() {
    throw new IllegalStateException("Instantiating static util class");
  }

  public static void registerGameArea(GameArea area){
     gameArea = area;
  }

  public static void registerMainGameScreen(MainGameScreen gameScreen) {
    mainGameScreen = gameScreen;
  }

  public static MainGameScreen getMainGameScreen() {
    return mainGameScreen;
  }

  public static void registerCraftArea(GameAreaDisplay area){
    craftArea = area;
  }

  public static void registerPauseArea(GameAreaDisplay area) {pauseMenuArea = area;}

  public static void registerInventoryArea(GameAreaDisplay area){
    inventoryArea = area;
  }

  /**
   * Register the display area the keybinding area will now exist in.
   * @param area  GameAreaDisplay to introduce the keybinding area to
   */
  public static void registerKeyBindArea(GameAreaDisplay area) { keyBindArea = area;}

  public static GameAreaDisplay getCraftArea() {
    return craftArea;
  }

  public static GameAreaDisplay getPauseMenuArea() {
    return pauseMenuArea;
  }

  public static GameAreaDisplay getInventoryArea() {
    return inventoryArea;
  }

  /**
   * Returns the keybinding GameAreaDisplay element.
   * @return GameAreaDisplay  Associated GameAreaDisplay element
   */
  public static GameAreaDisplay getKeyBindArea() { return keyBindArea; }
}

