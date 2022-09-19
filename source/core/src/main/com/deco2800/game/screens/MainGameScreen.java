package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.areas.UndergroundGameArea;
import com.deco2800.game.areas.terrain.TerrainComponent;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.maingame.MainGameActions;
import com.deco2800.game.components.npc.DialogueDisplay;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.components.player.PlayerStatsDisplay;
import com.deco2800.game.components.player.QuickBarDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.terminal.Terminal;
import com.deco2800.game.ui.terminal.TerminalDisplay;
import com.deco2800.game.components.maingame.MainGameExitDisplay;
import com.deco2800.game.components.gamearea.PerformanceDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The game screen containing the main game.
 *
 * <p>Details on libGDX screens: https://happycoding.io/tutorials/libgdx/game-screens
 */
public class MainGameScreen extends ScreenAdapter {
  private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
  private static final String[] mainGameTextures = {"images/heart.png","images/Inventory/quickbar.png"};
  private static final String[] healthBar = {"images/PlayerStatDisplayGraphics/Health-plunger/plunger_1.png","images/PlayerStatDisplayGraphics/Health-plunger/plunger_2.png", "images/PlayerStatDisplayGraphics/Health-plunger/plunger_3.png", "images/PlayerStatDisplayGraphics/Health-plunger/plunger_4.png", "images/PlayerStatDisplayGraphics/Health-plunger/plunger_5.png", "images/PlayerStatDisplayGraphics/Health-plunger/plunger_6.png", "images/PlayerStatDisplayGraphics/Health-plunger/plunger_7.png", "images/PlayerStatDisplayGraphics/Health-plunger/plunger_8.png"};
  private static final String[] staminaBar = {"images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_1.png","images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_2.png", "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_3.png", "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_4.png", "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_5.png", "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_6.png", "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_7.png" };
  private static final String[] manaBar = {"images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_1.png","images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_2.png", "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_3.png", "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_4.png", "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_5.png", "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_6.png", "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_7.png"};
  private static final String[] dashImg = {"images/Skills/dash.png"};
  private static final String[] blockImg = {"images/Skills/block.png"};
  private static final String[] dodgeImg = {"images/Skills/dodge.png"};
  private static final String[] invulnerabilityImg = {"images/Skills/invulnerability.png"};
  private static final String[] dialogueImg = {"images/NPC/Dialogue/dialogues2.png"};
  private static final String[] teleportImg = {"images/Skills/teleport.png"};
  private static final Vector2 CAMERA_POSITION = new Vector2(7.5f, 7.5f);
  private Entity player;
  private final GdxGame game;
  private final Renderer renderer;
  private final PhysicsEngine physicsEngine;
  private static GameArea map;
  private static Component mainGameActions;
  private static Boolean dead;




  public MainGameScreen(GdxGame game) {

    this.game = game;
    logger.debug("Initialising main game screen services");
    ServiceLocator.registerMainGameScreen(this);
    ServiceLocator.registerTimeSource(new GameTime());

    PhysicsService physicsService = new PhysicsService();
    ServiceLocator.registerPhysicsService(physicsService);
    physicsEngine = physicsService.getPhysics();

    ServiceLocator.registerInputService(new InputService());
    ServiceLocator.registerResourceService(new ResourceService());

    ServiceLocator.registerEntityService(new EntityService());
    ServiceLocator.registerRenderService(new RenderService());

    renderer = RenderFactory.createRenderer();
//    renderer.getCamera().getEntity().setPosition(CAMERA_POSITION);
    renderer.getDebug().renderPhysicsWorld(physicsEngine.getWorld());

    loadAssets();
    createUI();

    logger.debug("Initialising main game screen entities");
    ForestGameArea map = (ForestGameArea) chooseMap(1);
//    UndergroundGameArea map = loadLevelTwoMap();
    this.map = map;
//    GameArea map = loadLevelTwoMap();
    player = map.getPlayer();
    dead = false;

    // Add a death listener to the player
    player.getEvents().addListener("death", this::deathScreenStart);

  }

  public void deathScreenStart() { dead = true; }

  public GameArea getMap(){
    return map;
  }

  @Override
  public void render(float delta) {
    physicsEngine.update();
    ServiceLocator.getEntityService().update();
    cameraTracePlayer();
    renderer.render();
    if (dead) {
      // Could add further player cleanup functionality here
      player.getComponent(PlayerActions.class).stopWalking();
      mainGameActions.getEntity().getEvents().trigger("exit");
    }
  }

  @Override
  public void resize(int width, int height) {
    renderer.resize(width, height);
    logger.trace("Resized renderer: ({} x {})", width, height);
  }

  @Override
  public void pause() {
    logger.info("Game paused");
  }

  @Override
  public void resume() {
    logger.info("Game resumed");
  }

  @Override
  public void dispose() {
    logger.debug("Disposing main game screen");

    renderer.dispose();
    unloadAssets();

    ServiceLocator.getEntityService().dispose();
    ServiceLocator.getRenderService().dispose();
    ServiceLocator.getResourceService().dispose();

    ServiceLocator.clear();
  }

  /**
   * Disposes of the current level and loads the next one - Team 5 1map4all @otili9890
   * @param level (int) - The int describing which map to load (1-3)
   */
  public GameArea chooseMap(int level) {
    switch (level) {
      case 1:
        return this.loadLevelOneMap();
      case 2:
        map.dispose();
        this.map = this.loadLevelTwoMap();
        player = map.getPlayer();
      default:
    }
    return null;
  }

  /**
   * Load the first map. - Team 5 1map4all @LYB
   * @return The game instance.
   */
  private ForestGameArea loadLevelOneMap() {
    TerrainFactory terrainFactory = new TerrainFactory(renderer.getCamera());
    ForestGameArea forestGameArea = new ForestGameArea(terrainFactory);

    forestGameArea.create();
    return forestGameArea;
  }

  /**
   * Load the level two map. - Team 5 1map4all @LYB
   * @return The game instance.
   */
  private UndergroundGameArea loadLevelTwoMap() {

    TerrainFactory terrainFactory = new TerrainFactory(renderer.getCamera());
    UndergroundGameArea undergroundGameArea = new UndergroundGameArea(terrainFactory);

    undergroundGameArea.create();
    return undergroundGameArea;
  }

  private void loadAssets() {
    logger.debug("Loading assets");
    ResourceService resourceService = ServiceLocator.getResourceService();
    resourceService.loadTextures(mainGameTextures);
    resourceService.loadTextures(healthBar);
    resourceService.loadTextures(staminaBar);
    resourceService.loadTextures(manaBar);
    resourceService.loadTextures(dashImg);
    resourceService.loadTextures(blockImg);
    resourceService.loadTextures(dodgeImg);
    resourceService.loadTextures(invulnerabilityImg);
    resourceService.loadTextures(teleportImg);
    resourceService.loadTextures(dialogueImg);

    ServiceLocator.getResourceService().loadAll();
  }

  private void unloadAssets() {
    logger.debug("Unloading assets");
    ResourceService resourceService = ServiceLocator.getResourceService();
    resourceService.unloadAssets(mainGameTextures);
    resourceService.unloadAssets(healthBar);
    resourceService.unloadAssets(staminaBar);
    resourceService.unloadAssets(manaBar);
    resourceService.unloadAssets(dashImg);
    resourceService.unloadAssets(blockImg);
    resourceService.unloadAssets(dodgeImg);
    resourceService.unloadAssets(invulnerabilityImg);
    resourceService.unloadAssets(teleportImg);
    resourceService.loadTextures(dialogueImg);
  }

  /**
   * Creates the main game's ui including components for rendering ui elements to the screen and
   * capturing and handling ui input.
   */
  private void createUI() {
    logger.debug("Creating ui");
    mainGameActions = new MainGameActions(this.game);
    Stage stage = ServiceLocator.getRenderService().getStage();
    InputComponent inputComponent =
        ServiceLocator.getInputService().getInputFactory().createForTerminal();

    Entity ui = new Entity();
    ui.addComponent(new InputDecorator(stage, 10))
        .addComponent(new QuickBarDisplay())
        .addComponent(new PerformanceDisplay())
        .addComponent(mainGameActions)
        .addComponent(new MainGameExitDisplay())
        .addComponent(new Terminal())
        .addComponent(inputComponent)
        .addComponent(new TerminalDisplay())
        .addComponent(new DialogueDisplay());

    ServiceLocator.getEntityService().register(ui);
  }



  /**
   * The function that make the camera moves along with the player.
   */
  private void cameraTracePlayer() {
    renderer.getCamera().getEntity().setPosition(player.getPosition());
  }
}