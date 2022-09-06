package com.deco2800.game.areas;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.areas.terrain.TerrainFactory.TerrainType;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.*;
import com.deco2800.game.entities.factories.NPCFactory;
import com.deco2800.game.entities.factories.ObstacleFactory;
import com.deco2800.game.entities.factories.PlayerFactory;
import com.deco2800.game.entities.factories.PotionFactory;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.RenderComponent;
import com.deco2800.game.utils.math.GridPoint2Utils;
import com.deco2800.game.utils.math.RandomUtils;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.entities.factories.DialogueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;


/** Forest area for the demo game with trees, a player, and some enemies. */
public class ForestGameArea extends GameArea {
  private static final Logger logger = LoggerFactory.getLogger(ForestGameArea.class);
  private static final int NUM_TREES = 3;
  private static final int NUM_GHOSTS = 2;
  private static final GridPoint2 PLAYER_SPAWN = new GridPoint2(10, 10);
  private static final float WALL_WIDTH = 0.1f;
  private static final String[] forestTextures = {
    "images/box_boy_leaf.png",
    "images/tree.png",
    "images/Enemies/gym_bro.png",
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
    "images/level_1_tiledmap/32x32/drain_empty.png",
    "images/level_1_tiledmap/32x32/drain_plug.png",
    "images/NPC/male_citizen/male_citizen.png",
    "images/level_1_tiledmap/32x32/rock.png",
    "images/Potions/defence_potion.png",
    "images/level_1_tiledmap/32x32/column.png",
    "images/NPC/male_citizen/male_citizen.png",
    "images/Potions/defence_potion.png",
    "images/playerTeleport.png",
    "images/NPC/female npc/npcfemale_1.png",
    "images/NPC/child npc/npcchild_1.png",
    "images/NPC/guard npc/atlantisguardnpc_1.png",
    "images/NPC/Male_citizen/male_citizen.png",
    "images/NPC/Dialogue/dialogues2.png",
    "images/Potions/defence_potion.png",
    "images/level_1_tiledmap/32x32/column.png",
    "images/CombatWeapons-assets-sprint1/Enemy_dumbbell.png",
    "images/CombatWeapons-assets-sprint1/Damage Increase Buff.png",
    "images/CombatWeapons-assets-sprint1/Sword_Lvl2.png",
    "images/CombatWeapons-assets-sprint1/AttackDamageDebuff.png",
    "images/CombatWeapons-assets-sprint1/PeriPeriBuff_FIRE.png",
    "images/CombatWeapons-assets-sprint1/poisonBuff.png",
    "images/Potions/defence_potion.png",
    "images/CombatWeapons-assets-sprint1/trident_Lvl2.png",
    "images/NPC/Male_citizen/male_citizen.png",
    "images/Potions/agility_potion.png",
    "images/CombatWeapons-assets-sprint1/Sprint-2/H&ADagger.png",
    "images/CombatWeapons-assets-sprint1/Sprint-2/Plunger.png",
    "images/Skills/skillAnimations.png"
  };

  public static String[] newTextures;
  private static final String[] forestTextureAtlases = {
    "images/terrain_iso_grass.atlas", "images/playerTeleport.atlas",
    "images/Skills/skillAnimations.atlas", "images/Enemies/gym_bro.atlas"
  };
  private static final String[] forestSounds = {"sounds/Impact4.ogg"};
  private static final String backgroundMusic = "sounds/BGM_03_mp3.mp3";
  private static final String[] forestMusic = {backgroundMusic};

  private final TerrainFactory terrainFactory;

  private Entity player;
  private static List<Entity> weaponOnMap = new ArrayList<>();
  private static List<Entity> auraOnMap = new ArrayList<>();
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
    spawnTridentLvl2();
    spawnCraftingTable();
    spawnPotion();
    player = spawnPlayer();
    //spawnEffectBlobs();
    spawnGymBro();
    spawnOneLegGirl();
    spawnPlug();
    spawnChild();
    spawnGuard();
    spawnMaleCitizen();
//    spawnDialogue();
//    spawnColumn(20, 20);
//    spawnColumn(30, 20);
    playMusic();



    spawnDumbbell();
    spawnSpeedDebuff();
    spawnDmgBuff();
    spawnDmgDebuff();
    spawnFireBuff();
    spawnPoisonBuff();
    spawnHerraAndAthena();
    spawnPlunger();
  }



  private void displayUI() {
    Entity ui = new Entity();
    ui.addComponent(new GameAreaDisplay("Box Forest"));
    spawnEntity(ui);
  }

  private void spawnTerrain() {
    // Background terrain
    terrain = terrainFactory.createTerrain(TerrainType.LEVEL_ONE);
    spawnEntity(new Entity().addComponent(terrain));

    //Place the columns
    spawnColumn(8, 3);
    spawnColumn(15, 3);

    //Place the trees
    spawnTrees(2,15);
    spawnTrees(22,15);
    spawnSmallTrees(1,6);
    spawnSmallTrees(22,6);
    spawnRock(2,9);
    spawnRock(22, 8);

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

    // Castle Walls
    spawnEntityAt(ObstacleFactory.createWall(1f, 17f), new GridPoint2(3, 7), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 17f), new GridPoint2(21, 7), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(18f, 1f), new GridPoint2(4, 23), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(5f, 1f), new GridPoint2(3, 7), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(5f, 1f), new GridPoint2(17, 7), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 4f), new GridPoint2(7, 4), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 4f), new GridPoint2(17, 4), false,
            false);
  }

  private void spawnTrees(int x, int y) {
      Entity tree = ObstacleFactory.createTree();
      spawnEntityAt(tree, new GridPoint2(x, y), true, false);
  }

  public static void removeWeaponOnMap(Entity entityToRemove) {

    entityToRemove.setEnabled(false);
    weaponOnMap.remove(entityToRemove);

    Gdx.app.postRunnable(() -> entityToRemove.dispose());
  }


  /**
   * Spawns attack speed buff for the first 7 seconds and removes these buffs after the given time
   */
  /*
  private void spawnEffectBlobs() {

    GridPoint2 minPos = new GridPoint2(2, 2);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(4, 4);


    for (int i = 0; i < 10; i++) {
      Entity speedBuff1 = AuraFactory.createWeaponSpeedBuff();
      auraOnMap.add(speedBuff1);
      GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
      this.spawnEntityAt(speedBuff1, randomPos, true, false);

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
  */

  /**
   * Spawns speed debuff entity into the game
   * Spawns x-pos 10
   * Spawns y-pos 10
   */
  private void spawnSpeedDebuff() {
    Entity speedDebuff = AuraFactory.createWeaponSpeedDeBuff();
    weaponOnMap.add(speedDebuff);
    spawnEntityAt(speedDebuff, new GridPoint2(10,10), true, false);
  }
  /**
   * Spawns damage buff entity into the game
   * Spawns x-pos 15
   * Spawns y-pos 15
   */
  private void spawnDmgBuff() {
    Entity dmgBuff = AuraFactory.createWeaponDmgBuff();
    weaponOnMap.add(dmgBuff);
    spawnEntityAt(dmgBuff, new GridPoint2(15,15), true, false);
  }

  /**
   * Spawns damage debuff entity into the game
   * Spawns x-pos 11
   * Spawns y-pos 15
   */
  private void spawnDmgDebuff() {
    Entity dmgDebuff = AuraFactory.createWeaponDmgDebuff();
    weaponOnMap.add(dmgDebuff);
    spawnEntityAt(dmgDebuff, new GridPoint2(11,15), true, false);
  }

  /**
   * Spawns fire buff entity into the game
   * Spawns x-pos 20
   * Spawns y-pos 10
   */
  private void spawnFireBuff() {
    Entity fireBuff = AuraFactory.createFireBuff();
    weaponOnMap.add(fireBuff);
    spawnEntityAt(fireBuff, new GridPoint2(20,10), true, false);
  }

  /**
   * Spawns poison buff entity into the game
   * Spawns x-pos 18
   * Spawns y-pos 12
   */
  private void spawnPoisonBuff() {
    Entity fireBuff = AuraFactory.createPoisonBuff();
    weaponOnMap.add(fireBuff);
    spawnEntityAt(fireBuff, new GridPoint2(18,14), true, false);
  }

  /**
   * Spawn rock in a certain position. - Team 5 1map4all @LYB
   */
  private void spawnRock(int x, int y) {
    Entity rock = ObstacleFactory.createRock();
    spawnEntityAt(rock, new GridPoint2(x, y), false, false);
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
    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

    Entity column = ObstacleFactory.createColumn();
    spawnEntityAt(column, new GridPoint2(x, y), false, false);
    }





  public void spawnEntityOnMap(Entity entity,GridPoint2 position, Boolean centreX, Boolean centreY) {
    spawnEntityAt(entity, position, centreX, centreY);
  }

  public void spawnCraftingTable() {
    GridPoint2 minPos = new GridPoint2(2, 2);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(4, 4);


    GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
    craftingTablePos = randomPos;
    Entity craftingTable = ObstacleFactory.createCraftingTable();
    spawnEntityAt(craftingTable, randomPos, true, false);
  }

  /**
   * Spawns Level 2 dagger entity into the game
   * Spawns x-pos 10
   * Spawns y-pos 10
   */
  private void spawnDagger() {
    Entity dagger = WeaponFactory.createDagger();
    weaponOnMap.add(dagger);
    spawnEntityAt(dagger, new GridPoint2(10, 10), true, false);
  }
  /**
   * Spawns second Level 2 dagger entity into the game
   * Spawns x-pos 18
   * Spawns y-pos 10
   */
  private void spawnDaggerTwo() {
    Entity daggerTwo = WeaponFactory.createDaggerTwo();
    weaponOnMap.add(daggerTwo);
    spawnEntityAt(daggerTwo, new GridPoint2(18,10), true, false);
  }
  /**
   * Spawns dumbbell entity into the game
   * Spawns x-pos 5
   * Spawns y-pos 10
   */
  private void spawnDumbbell() {
    Entity dumbbell = WeaponFactory.createDumbbell();
    weaponOnMap.add(dumbbell);
    spawnEntityAt(dumbbell, new GridPoint2(7,10), true, false);
  }

  /**
   * Spawns level 3 Herra and Athena entity into the game
   * Spawns x-pos 10
   * Spawns y-pos 4
   */
  private void spawnHerraAndAthena() {
    Entity herraAthenaDag = WeaponFactory.createHerraAthenaDag();
    weaponOnMap.add(herraAthenaDag);
    spawnEntityAt(herraAthenaDag, new GridPoint2(10,4), true, false);
  }

  /**
   * Spawns basic plunger into game
   * Spawns x-pos 20
   * Spawns y-pos 4
   */
  private void spawnPlunger() {
    Entity plunger = WeaponFactory.createPlunger();
    weaponOnMap.add(plunger);
    spawnEntityAt(plunger, new GridPoint2(20,4), true, false);
  }

  /**
   * Spawns Level 2 Sword entity into the game
   * Spawns x-pos 20
   * Spawns y-pos 20
   */
  private void spawnSwordLvl2() {
    Entity SwordLvl2 = WeaponFactory.createSwordLvl2();
    weaponOnMap.add(SwordLvl2);
    spawnEntityAt(SwordLvl2, new GridPoint2(16,18), true, false);
  }

  /**
   * Spawns Level 2 Trident entity into the game
   */
  private void spawnTridentLvl2() {
    Entity tridentLvl2 = WeaponFactory.createTridentLvl2();
    weaponOnMap.add(tridentLvl2);
    spawnEntityAt(tridentLvl2, new GridPoint2(12,15), true, false);
  }
  public static GridPoint2 getCraftingTablePos() {
    return craftingTablePos;
  }

  /**
   * Spawn a potion in a random position.
   */
  private void spawnPotion() {
    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

    GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
    Entity potion = PotionFactory.createSpeedPotion();
    this.spawnEntityAt(potion, randomPos, true, false);
  }

  /**
   * Spawns a plug Entity to assist with map transition. - Team 5 1map4all
   */
  private void spawnPlug() {
    GridPoint2 plugPosition = new GridPoint2(12, 17);
    Entity plug = NPCFactory.createPlug(player);

    spawnEntityAt(plug, plugPosition, true, true);
  }

  /**
   * Spawns the player entity, with a skill animator overlaid above the player.
   * @return the player entity
   */
  private Entity spawnPlayer() {
    Entity newPlayer = PlayerFactory.createPlayer();
    Entity newSkillAnimator = PlayerFactory.createSkillAnimator(newPlayer);
    spawnEntityAt(newPlayer, PLAYER_SPAWN, true, true);
    spawnEntityAt(newSkillAnimator, PLAYER_SPAWN, true, true);
    newPlayer.getComponent(PlayerActions.class).setSkillAnimator(newSkillAnimator);
    return newPlayer;
  }

  /**
   * Spawn female NPC in random position. - Team 7 all-mid-npc
   */
  private void spawnOneLegGirl() {
    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

    GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
    Entity oneLegGirl = NPCFactory.createOneLegGirl(player);
    spawnEntityAt(oneLegGirl, randomPos, true, true);
    Entity dialogue = DialogueFactory.createDialogue();

    spawnEntityAt(dialogue, randomPos, true, true);
  }

  private GridPoint2 randomPositon() {
    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

    return RandomUtils.random(minPos, maxPos);
  }

  /**
   * Spawn child NPC in random position. - Team 7 all-mid-npc
   */
  private void spawnChild() {
    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

    GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);

    Entity child = NPCFactory.createChild(player);
    spawnEntityAt(child, randomPos, true, true);
    Entity dialogue = DialogueFactory.createDialogue();

    spawnEntityAt(dialogue, randomPos, true, true);
  }
//  private void spawnDialogue() {
//
//    Entity dialogue = DialogueFactory.createDialogue();
//
//    spawnEntityAt(dialogue, randomPositon(), true, true);
//    System.out.print(randomPositon());
//  }


  /**
   * Spawn guard NPC in random position. - Team 7 all-mid-npc
   */
  private void spawnGuard() {
    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

    GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);

    Entity guard = NPCFactory.createGuard(player);
    spawnEntityAt(guard, randomPos, true, true);
    Entity dialogue = DialogueFactory.createDialogue();

    spawnEntityAt(dialogue, randomPos, true, true);
  }

  /**
   * Spawn male NPC in random position. - Team 7 all-mid-npc
   */
  private void spawnMaleCitizen() {
    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

    GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
    Entity male_citizen = NPCFactory.createMale_citizen(player);
    spawnEntityAt(male_citizen, randomPos, true, true);
    Entity dialogue = DialogueFactory.createDialogue();

    spawnEntityAt(dialogue, randomPos, true, true);
  }


  private void spawnGymBro() {
    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

    for (int i = 0; i < 5; i++) {
      GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
      Entity gymBro = NPCFactory.createGymBro(player);
      spawnEntityAt(gymBro, randomPos, true, true);
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
