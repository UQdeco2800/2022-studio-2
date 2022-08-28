package com.deco2800.game.areas;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.areas.terrain.TerrainComponent;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.areas.terrain.TerrainFactory.TerrainType;
import com.deco2800.game.components.tasks.CombatItemsComponents.MeleeStatsComponent;
import com.deco2800.game.components.tasks.CombatItemsComponents.WeaponAuraComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.*;
import com.deco2800.game.components.MenuComponent;
import com.deco2800.game.crafting.craftingDisplay.CraftingMenuActions;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.utils.math.GridPoint2Utils;
import com.deco2800.game.utils.math.RandomUtils;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

/** Forest area for the demo game with trees, a player, and some enemies. */
public class ForestGameArea extends GameArea {
  private static final Logger logger = LoggerFactory.getLogger(ForestGameArea.class);
//  private static final int NUM_TREES = 3;
  private static final int NUM_GHOSTS = 2;
  private static final GridPoint2 PLAYER_SPAWN = new GridPoint2(10, 10);
  private static final float WALL_WIDTH = 0.1f;
  private static final String[] forestTextures = {
    "images/atlantis_citizen_gym_bro.png",
    "images/box_boy_leaf.png",
    "images/tree.png",
    "images/ghost_king.png",
    "images/ghost_1.png",
    "images/grass_1.png",
    "images/grass_2.png",
    "images/grass_3.png",
    "images/hex_grass_1.png",
    "images/hex_grass_2.png",
    "images/hex_grass_3.png",
    "images/iso_grass_1.png",
    "images/iso_grass_2.png",
    "images/iso_grass_3.png",
    "images/CombatWeapons-assets-sprint1/Level 2 Dagger 1.png",
    "images/CombatWeapons-assets-sprint1/Level 2 Dagger 2png.png",
    "images/CombatWeapons-assets-sprint1/Weapon Speed Buff.png",
    "images/Crafting-assets-sprint1/widgets/craftButton.png",
    "images/Crafting-assets-sprint1/crafting table/craftingUI.png",
    "images/Crafting-assets-sprint1/crafting table/craftingTable.png",
    "images/gold_cobble.png",
    "images/gold_drain.png",
    "images/Map_assets/sprint_1/column.png",
    "images/Map_assets/sprint_1/tree-1_1.png",
    "images/Map_assets/sprint_1/tree-2_2.png",
    "images/level_1_tiledmap/32x32/tree.png",
    "images/level_1_tiledmap/32x32/gold_cobble.png",
    "images/level_1_tiledmap/32x32/grass.png",
    "images/level_1_tiledmap/32x32/gold_drain.png",
    "images/level_1_tiledmap/32x32/water_tile.png",
    "images/level_1_tiledmap/32x32/wall_tile.png",
    "images/level_1_tiledmap/32x32/tile_wet.png",
    "images/level_1_tiledmap/32x32/stairs.png",
    "images/level_1_tiledmap/32x32/tree.png",
    "images/level_1_tiledmap/32x32/column.png",
    "images/NPC/male_citizen/male_citizen.png",
    "images/CombatWeapons-assets-sprint1/Enemy_dumbbell.png",
    "images/CombatWeapons-assets-sprint1/Damage Increase Buff.png",
    "images/CombatWeapons-assets-sprint1/Sword_Lvl2.png",
    "images/CombatWeapons-assets-sprint1/AttackDamageDebuff.png",
    "images/CombatWeapons-assets-sprint1/PeriPeriBuff_FIRE.png",
    "images/CombatWeapons-assets-sprint1/poisonBuff.png"
  };

  public static String[] newTextures;
  private static final String[] forestTextureAtlases = {
    "images/terrain_iso_grass.atlas", "images/ghost.atlas", "images/ghostKing.atlas"
  };
  private static final String[] forestSounds = {"sounds/Impact4.ogg"};
  private static final String backgroundMusic = "sounds/BGM_03_mp3.mp3";
  private static final String[] forestMusic = {backgroundMusic};

  private final TerrainFactory terrainFactory;

  private Entity player;
  private List<Entity> weaponOnMap = new ArrayList<>();
  private List<Entity> auraOnMap = new ArrayList<>();

  private static GridPoint2 craftingMenuPos;

  private static GridPoint2 craftingTablePos;

  public ForestGameArea(TerrainFactory terrainFactory) {
    super();
    this.terrainFactory = terrainFactory;

    ServiceLocator.registerGameArea(this);

  }

  /**
   * Get the player entity from the map.
   * @return player entity.
   */
  public Entity getPlayer() {
    return player;
  }

  /** Create the game area, including terrain, static entities (trees), dynamic entities (player) */
  @Override
  public void create() {
    loadAssets();
    displayUI();
    spawnTerrain();
    spawnDagger();
    spawnDaggerTwo();
    spawnSwordLvl2();
    spawnCraftingTable();
    player = spawnPlayer();
    spawnGhosts();
    spawnGhostKing();
    spawnEffectBlobs();
    spawnAtlantisCitizen();
    spawnColumn(20, 20);
    spawnColumn(30, 20);
    spawnOneLegGirl();
    playMusic();
    spawnDumbbell();
    spawnSpeedDebuff();
    spawnDmgBuff();
    spawnDmgDebuff();
    spawnFireBuff();
    spawnPoisonBuff();
  }

  private void displayUI() {
    Entity ui = new Entity();
    ui.addComponent(new GameAreaDisplay("Box Forest"));
    spawnEntity(ui);
  }

  private void spawnTerrain() {
    // Background terrain
    terrain = terrainFactory.createTerrain(TerrainType.LevelOneFlat);
    spawnEntity(new Entity().addComponent(terrain));

    //Place the columns
    spawnColumn(8, 3);
    spawnColumn(15, 3);

    //Place the trees
    spawnTrees(2,15);
    spawnTrees(22,15);
    spawnSmallTrees(1,6);
    spawnSmallTrees(22,6);

    // Terrain walls
    float tileSize = terrain.getTileSize();
    GridPoint2 tileBounds = terrain.getMapBounds(0);
    Vector2 worldBounds = new Vector2(tileBounds.x * tileSize, tileBounds.y * tileSize);

    // Left
    spawnEntityAt(
        ObstacleFactory.createWall(WALL_WIDTH, worldBounds.y), GridPoint2Utils.ZERO, false, false);
    // Right
    spawnEntityAt(
        ObstacleFactory.createWall(WALL_WIDTH, worldBounds.y),
        new GridPoint2(tileBounds.x, 0),
        false,
        false);
    // Top
    spawnEntityAt(
        ObstacleFactory.createWall(worldBounds.x, WALL_WIDTH),
        new GridPoint2(0, tileBounds.y),
        false,
        false);
    // Bottom
    spawnEntityAt(
        ObstacleFactory.createWall(worldBounds.x, WALL_WIDTH), GridPoint2Utils.ZERO, false, false);

    // Castle Wall
    spawnEntityAt(ObstacleFactory.createWall(1f, 18f), new GridPoint2(3, 6), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 18f), new GridPoint2(21, 6), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(18f, 1f), new GridPoint2(4, 23), false,
            false);
  }

  private void spawnTrees(int x, int y) {
      Entity tree = ObstacleFactory.createTree();
      spawnEntityAt(tree, new GridPoint2(x, y), true, false);
  }

  private void spawnEffectBlobs() {

    GridPoint2 minPos = new GridPoint2(2, 2);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(4, 4);


    for (int i = 0; i < 10; i++) {
      Entity speedBuff1 = AuraFactory.createWeaponSpeedBuff();
      auraOnMap.add(speedBuff1);
      GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
      spawnEntityAt(speedBuff1, randomPos, true, false);

      Timer timer = new Timer();
      timer.schedule(new TimerTask() {
                       @Override
                       public void run() {
                         logger.info("EffectBlobs disappear");
                         speedBuff1.dispose();
                         auraOnMap.remove(speedBuff1);
                         timer.cancel();
                       }
                     }
              , 7000, 5000);
    }
  }

  private void spawnSpeedDebuff() {
    Entity speedDebuff = AuraFactory.createWeaponSpeedDeBuff();
    weaponOnMap.add(speedDebuff);
    spawnEntityAt(speedDebuff, new GridPoint2(10,10), true, false);
  }
  private void spawnDmgBuff() {
    Entity dmgBuff = AuraFactory.createWeaponDmgBuff();
    weaponOnMap.add(dmgBuff);
    spawnEntityAt(dmgBuff, new GridPoint2(15,15), true, false);
  }
  private void spawnDmgDebuff() {
    Entity dmgDebuff = AuraFactory.createWeaponDmgDebuff();
    weaponOnMap.add(dmgDebuff);
    spawnEntityAt(dmgDebuff, new GridPoint2(11,15), true, false);
  }
  private void spawnFireBuff() {
    Entity fireBuff = AuraFactory.createFireBuff();
    weaponOnMap.add(fireBuff);
    spawnEntityAt(fireBuff, new GridPoint2(20,10), true, false);
  }
  private void spawnPoisonBuff() {
    Entity fireBuff = AuraFactory.createPoisonBuff();
    weaponOnMap.add(fireBuff);
    spawnEntityAt(fireBuff, new GridPoint2(18,12), true, false);
  }

  /**
   * Spawn small tress in a certain position. - Team 5 1map4all @LYB
   */
  private void spawnSmallTrees(int x, int y) {
    Entity tree = ObstacleFactory.createSmallTree();
    spawnEntityAt(tree, new GridPoint2(x, y), false, false);
  }

  /**
   * Spawn column in a certain position. - Team 5 1map4all @LYB
   * @param x x-axis for the position(horizontal).
   * @param y y-axis for the position (vertical).
   */
  private void spawnColumn(int x, int y) {
    Entity column = ObstacleFactory.createColumn();
    spawnEntityAt(column, new GridPoint2(x, y), false, false);
    }

  public void spawnCraftingMenu() {
    GridPoint2 menuPos = new GridPoint2(10, 6);
    GridPoint2 craftButtonPos = new GridPoint2(13, 6);
    Entity craftingMenu = ObstacleFactory.createCraftingMenu();
    Entity craftButton = ObstacleFactory.createCraftButton();
    spawnEntityAt(craftingMenu, menuPos, true, false);
    spawnEntityAt(craftButton, craftButtonPos, true, false);
  }

  public void disposeCraftingMenu() {
    for (int i = 0; i < areaEntities.size();i++) {
      if (areaEntities.get(i).getComponent(MenuComponent.class) != null){
        areaEntities.get(i).dispose();
      }
    }
  }

  public void spawnCraftingTable() {
    GridPoint2 minPos = new GridPoint2(2, 2);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(4, 4);

    GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
    craftingTablePos = randomPos;
    Entity craftingTable = ObstacleFactory.createCraftingTable();
    spawnEntityAt(craftingTable, randomPos, true, false);
  }

  private void spawnDagger() {
    Entity dagger = WeaponFactory.createDagger();
    weaponOnMap.add(dagger);
    spawnEntityAt(dagger, new GridPoint2(10, 10), true, false);
  }

  private void spawnDaggerTwo() {
    Entity daggerTwo = WeaponFactory.createDaggerTwo();
    weaponOnMap.add(daggerTwo);
    spawnEntityAt(daggerTwo, new GridPoint2(18,10), true, false);
  }

  private void spawnDumbbell() {
    Entity dumbbell = WeaponFactory.createDumbbell();
    weaponOnMap.add(dumbbell);
    spawnEntityAt(dumbbell, new GridPoint2(5,10), true, false);
  }

  private void spawnSwordLvl2() {
    Entity SwordLvl2 = WeaponFactory.createSwordLvl2();
    weaponOnMap.add(SwordLvl2);
    spawnEntityAt(SwordLvl2, new GridPoint2(20,20), true, false);
  }


  public static GridPoint2 getCraftingTablePos() {
    return craftingTablePos;
  }

  private Entity spawnPlayer() {
    Entity newPlayer = PlayerFactory.createPlayer();
    spawnEntityAt(newPlayer, PLAYER_SPAWN, true, true);
    return newPlayer;
  }

  private void spawnGhosts() {
    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

    for (int i = 0; i < NUM_GHOSTS; i++) {
      GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
      Entity ghost = NPCFactory.createGhost(player);
      spawnEntityAt(ghost, randomPos, true, true);
    }
  }

  private void spawnOneLegGirl() {
    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

    GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
    Entity oneLegGirl = NPCFactory.createOneLegGirl(player);
    spawnEntityAt(oneLegGirl, randomPos, true, true);
  }

  private void spawnGhostKing() {
    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

    GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
    Entity ghostKing = NPCFactory.createGhostKing(player);
    spawnEntityAt(ghostKing, randomPos, true, true);
  }

  private void spawnAtlantisCitizen() {
    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

    for (int i = 0; i < NUM_GHOSTS; i++) {
      GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
      Entity atlantisCitizen = NPCFactory.createAtlantisCitizen(player);
      spawnEntityAt(atlantisCitizen, randomPos, true, true);
    }
  }

  private void playMusic() {
    Music music = ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class);
    music.setLooping(true);
    music.setVolume(0.0f);
    music.play();
  }

  private void loadAssets() {
    logger.debug("Loading assets");
    ResourceService resourceService = ServiceLocator.getResourceService();
    resourceService.loadTextures(forestTextures);
    resourceService.loadTextureAtlases(forestTextureAtlases);
    resourceService.loadSounds(forestSounds);
    resourceService.loadMusic(forestMusic);

    while (!resourceService.loadForMillis(10)) {
      // This could be upgraded to a loading screen
      logger.info("Loading... {}%", resourceService.getProgress());
    }
  }

  private void unloadAssets() {
    logger.debug("Unloading assets");
    ResourceService resourceService = ServiceLocator.getResourceService();
    resourceService.unloadAssets(forestTextures);
    resourceService.unloadAssets(forestTextureAtlases);
    resourceService.unloadAssets(forestSounds);
    resourceService.unloadAssets(forestMusic);
  }

  @Override
  public void dispose() {
    super.dispose();
    ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class).stop();
    this.unloadAssets();
  }
}
