package com.deco2800.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.GdxGame;
import com.deco2800.game.SkillsTree.SkillsTreeDisplay;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.areas.UndergroundGameArea;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.maingame.MainGameActions;
import com.deco2800.game.components.maingame.OpenKeyBinds;
import com.deco2800.game.components.maingame.PauseMenuActions;
import com.deco2800.game.components.npc.DialogueDisplay;
import com.deco2800.game.components.player.CooldownBarDisplay;
import com.deco2800.game.components.player.PlayerActions;
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
import net.dermetfan.gdx.physics.box2d.PositionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The game screen containing the main game.
 *
 * <p>Details on libGDX screens: https://happycoding.io/tutorials/libgdx/game-screens
 */
public class MainGameScreen extends ScreenAdapter {
  private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
  private static final String[] mainGameTextures = {"images/heart.png"};
  private static final String backgroundMusicMapOne = "sounds/music_sprint4/level0_1.wav";
  private static final String backgroundMusicMapTwo = "sounds/music_sprint4/level0_2.wav";
  private static final String[] mainMenuMusic = {backgroundMusicMapOne,backgroundMusicMapTwo};
  private static final String WALKING_SOUND = "sounds/walk_on_sand.wav";
  private static final String[] quickBar = {"images/Inventory/quickbar_sprint3.png"};
  private static final String[] healthBar = {"images/PlayerStatDisplayGraphics/Health-plunger/plunger_1.png","images/PlayerStatDisplayGraphics/Health-plunger/plunger_2.png", "images/PlayerStatDisplayGraphics/Health-plunger/plunger_3.png", "images/PlayerStatDisplayGraphics/Health-plunger/plunger_4.png", "images/PlayerStatDisplayGraphics/Health-plunger/plunger_5.png", "images/PlayerStatDisplayGraphics/Health-plunger/plunger_6.png", "images/PlayerStatDisplayGraphics/Health-plunger/plunger_7.png", "images/PlayerStatDisplayGraphics/Health-plunger/plunger_8.png"};
  private static final String[] staminaBar = {"images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_1.png","images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_2.png", "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_3.png", "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_4.png", "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_5.png", "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_6.png", "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_7.png" };
  private static final String[] manaBar = {"images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_1.png","images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_2.png", "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_3.png", "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_4.png", "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_5.png", "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_6.png", "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_7.png"};
  private static final String[] skillIcons = {
          "images/Skills/blankSkillIcon.png",
          "images/Skills/dash.png",
          "images/Skills/block.png",
          "images/Skills/block_disabled.png",
          "images/Skills/dodge.png",
          "images/Skills/dodge_disabled.png",
          "images/Skills/invulnerability.png",
          "images/Skills/invulnerability_disabled.png",
          "images/Skills/teleport.png",
          "images/Skills/teleport_disabled.png",
          "images/Skills/fireballUltimate.png",
          "images/Skills/fireballUltimate_disabled.png",
          "images/Skills/aoe.png",
          "images/Skills/aoe_disabled.png",
          "images/Skills/root.png",
          "images/Skills/root_disabled.png",
          "images/Skills/ultimate.png",
          "images/Skills/ultimate_disabled.png",
          "images/Skills/wrenchProjectile.png",
          "images/Skills/wrenchProjectile_disabled.png",
          "images/Skills/bleed.png",
          "images/Skills/bleed_disabled.png",
          "images/Skills/charge.png",
          "images/Skills/charge_disabled.png",
          "images/Skill_tree/tooltips/aoeTooltip.png",
          "images/Skill_tree/tooltips/bleedTooltip.png",
          "images/Skill_tree/tooltips/blockTooltip.png",
          "images/Skill_tree/tooltips/chargeTooltip.png",
          "images/Skill_tree/tooltips/dashTooltip.png",
          "images/Skill_tree/tooltips/dodgeTooltip.png",
          "images/Skill_tree/tooltips/fireballUltimateTooltip.png",
          "images/Skill_tree/tooltips/invulnerabilityTooltip.png",
          "images/Skill_tree/tooltips/wrenchProjectileTooltip.png",
          "images/Skill_tree/tooltips/rootTooltip.png",
          "images/Skill_tree/tooltips/teleportTooltip.png",
          "images/Skill_tree/tooltips/ultimateTooltip.png"
  };

  private static final String[] dialogueImg = {
          "images/NPC/Dialogue/dialoguesboxmale2.png",
          "images/NPC/Dialogue/dialoguesboxguard2.png",
          "images/NPC/Dialogue/dialoguesboxchild2.png",
          "images/NPC/Dialogue/dialoguesboxfemale2.png",
          "images/NPC/Dialogue/humanguarddialogue2.png",
          "images/NPC/Dialogue/friendlycreaturedialogue2.png",
          "images/NPC/Dialogue/plumberfriend2.png"
  };
  private static final String[] teleportImg = {"images/Skills/teleport.png"};
  private static final String[] skillScreenOverlays = {
          "images/Skills/white-flash.png",
          "images/Skills/blank-screen.png",
          "images/Skills/EquippedSkillsText.png",
          "images/Skills/clearSkillsButton.png",
          "images/Skills/clearSkillsButton_down.png",
          "images/Skills/skill-tree-icon.png",
          "images/Skills/skillExitButton.png",
          "images/Skills/skillExitButtonDown.png"
  };

  private static final Vector2 CAMERA_POSITION = new Vector2(7.5f, 7.5f);
  private Entity player;
  private final GdxGame game;
  private final Renderer renderer;
  private final PhysicsEngine physicsEngine;
  private static GameArea map;
  private static Component mainGameActions;
  private static Boolean dead;

  private static Boolean transition;
  private static Integer gameLevel;
  private static Boolean win;




  public MainGameScreen(GdxGame game, int level) {

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
    gameLevel = level;

    this.map = chooseMap(gameLevel);

    if (this.map == null) {
      logger.error("INCORRECT LEVEL SELECTED. ERROR TIME!");
    }

    player = map.getPlayer();

    // Management components for death and transition screen
    dead = false;
    transition = false;
    win = false;
    // Add a death listener to the player
    player.getEvents().addListener("death", this::deathScreenStart);
    // Add screen transition listener to player
    player.getEvents().addListener("mapTransition", this::transitionScreenStart);
    // Add win screen listener to player
    player.getEvents().addListener("win", this::winScreenStart);
  }

  private void playMusicOne() {
    Music music = ServiceLocator.getResourceService().getAsset(backgroundMusicMapOne, Music.class);
    music.setLooping(true);
    music.setVolume(0.2f);
    music.play();
  }

  private void playMusicTwo() {
    Music music = ServiceLocator.getResourceService().getAsset(backgroundMusicMapTwo, Music.class);
    music.setLooping(true);
    music.setVolume(0.2f);
    music.play();
  }

  private void stopWalkingSound() {
    Music music = ServiceLocator.getResourceService().getAsset(WALKING_SOUND, Music.class);
    music.stop();
  }

  /**
   * Sets dead to true, changing the render of the game
   */
  public void deathScreenStart() {
    stopWalkingSound();
    dead = true;
  }

  /**
   * Sets transition to true
   */
  public void transitionScreenStart() {
    stopWalkingSound();
    transition = true;
  }

  /**
   * Sets win to true
   */
  public void winScreenStart() {
    stopWalkingSound();
    win = true;
  }


  /**
   * Gets the games current map
   * @return map - current map
   */
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
      if (gameLevel == 1) {
        game.setScreen(GdxGame.ScreenType.DEATH_SCREEN_L1);
      } else if (gameLevel == 2) {
        game.setScreen(GdxGame.ScreenType.DEATH_SCREEN_L2);
      }
    }
    if (win) {
      logger.info("Win screen screen type set");
      player.getComponent(PlayerActions.class).stopWalking();
      game.setScreen(GdxGame.ScreenType.WIN_SCREEN);

    }

    if (transition) {
      game.setScreen(GdxGame.ScreenType.LEVEL_TRANSITION);
    }

    if (PauseMenuActions.getQuitGameStatus()) {
      mainGameActions.getEntity().getEvents().trigger("exit");
      PauseMenuActions.setQuitGameStatus();
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
    ServiceLocator.getResourceService().getAsset(backgroundMusicMapOne, Music.class).stop();
    ServiceLocator.getResourceService().getAsset(backgroundMusicMapTwo, Music.class).stop();
    ServiceLocator.getEntityService().dispose();
    ServiceLocator.getRenderService().dispose();
    ServiceLocator.getResourceService().dispose();

    ServiceLocator.clear();
  }

  /**
   * Gets game level
   * @return gameLevel - integer value of current game level
   */
  public int getGameLevel() {
    return gameLevel;
  }

  /**
   * Sets game level
   * @param gameLevel - the game level you want to change the level to.
   */
  public void setGameLevel(int gameLevel) {
    this.gameLevel = gameLevel;
  }

  /**
   * Disposes of the current level and loads the next one - Team 5 1map4all @otili9890
   * @param level (int) - The int describing which map to load (1-3)
   */
  public GameArea chooseMap(int level) {
    switch (level) {
      case 1:
        return this.map = loadLevelOneMap();
      case 2:
        return this.map = loadLevelTwoMap();
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
    playMusicOne();
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
    playMusicTwo();
    undergroundGameArea.create();
    return undergroundGameArea;
  }

  private void loadAssets() {
    logger.debug("Loading assets");
    ResourceService resourceService = ServiceLocator.getResourceService();
    resourceService.loadMusic(mainMenuMusic);
    resourceService.loadTextures(mainGameTextures);
    resourceService.loadTextures(quickBar);
    resourceService.loadTextures(healthBar);
    resourceService.loadTextures(staminaBar);
    resourceService.loadTextures(manaBar);
    resourceService.loadTextures(skillIcons);
    resourceService.loadTextures(teleportImg);
    resourceService.loadTextures(dialogueImg);
    resourceService.loadTextures(skillScreenOverlays);

    ServiceLocator.getResourceService().loadAll();
  }

  private void unloadAssets() {
    logger.debug("Unloading assets");
    ResourceService resourceService = ServiceLocator.getResourceService();
    resourceService.unloadAssets(mainGameTextures);
    resourceService.unloadAssets(quickBar);
    resourceService.unloadAssets(healthBar);
    resourceService.unloadAssets(staminaBar);
    resourceService.unloadAssets(manaBar);
    resourceService.unloadAssets(skillIcons);
    resourceService.unloadAssets(teleportImg);
    resourceService.loadTextures(dialogueImg);
    resourceService.unloadAssets(skillScreenOverlays);
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
            .addComponent(new SkillsTreeDisplay())
            .addComponent(new CooldownBarDisplay())
            .addComponent(new PerformanceDisplay())
            .addComponent(mainGameActions)
            .addComponent(new MainGameExitDisplay())
            .addComponent(new Terminal())
            .addComponent(inputComponent)
            .addComponent(new TerminalDisplay())
            .addComponent(new DialogueDisplay())
            .addComponent(new PauseMenuActions());

    ServiceLocator.getEntityService().register(ui);
  }



  /**
   * The function that make the camera moves along with the player.
   */
  private void cameraTracePlayer() {
    renderer.getCamera().getEntity().setPosition(player.getPosition());
  }
}