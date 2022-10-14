package com.deco2800.game.areas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.areas.terrain.TerrainFactory.TerrainType;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.components.player.PlayerKeyPrompt;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.*;
import com.deco2800.game.entities.factories.NPCFactory;
import com.deco2800.game.entities.factories.ObstacleFactory;
import com.deco2800.game.entities.factories.PlayerFactory;
import com.deco2800.game.entities.factories.PotionFactory;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.utils.math.GridPoint2Utils;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.entities.factories.DialogueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.ArrayList;


/** Forest area for the demo game with trees, a player, and some enemies. */
public class ForestGameArea extends GameArea {
  private static final Logger logger = LoggerFactory.getLogger(ForestGameArea.class);
  private static final GridPoint2 PLAYER_SPAWN = new GridPoint2(96, 3);
  private static final float WALL_WIDTH = 0.1f;
  private static final String[] forestTextures = {
    "images/box_boy_leaf.png",
    "images/tree.png",
    "images/Enemies/gym_bro.png",
    "images/Enemies/discus.png",
    "images/grass_1.png",
    "images/grass_2.png",
    "images/grass_3.png",
    "images/hex_grass_1.png",
    "images/hex_grass_2.png",
    "images/hex_grass_3.png",
    "images/iso_grass_1.png",
    "images/iso_grass_2.png",
    "images/iso_grass_3.png",
    "images/Armour-assets-sprint2/baseArmour.png",
    "images/Armour-assets-sprint2/slowDiamond.png",
    "images/Armour-assets-sprint2/damageReturner.png",
    "images/Armour-assets-sprint2/fastLeather.png",
    "images/CombatItems/Sprint-1/Level 2 Dagger 1.png",
    "images/CombatItems/Sprint-1/Level 2 Dagger 2png.png",
    "images/CombatItems/Sprint-1/Weapon Speed Buff.png",
    "images/CombatItems/Sprint-1/AttackSpeedDebuff.png",
    "images/Crafting-assets-sprint1/widgets/craftButton.png",
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
    "images/level_1_tiledmap/32x32/tree-2.png",
    "images/level_1_tiledmap/32x32/rock.png",
    "images/NPC/male_citizen/male_citizen.png",
    "images/Potions/health_potion.png",
    "images/Potions/defence_potion.png",
    "images/NPC/male_citizen/male_citizen.png",
    "images/playerTeleport.png",
    "images/NPC/female npc/npcfemale_1.png",
    "images/NPC/child npc/npcchild_1.png",
    "images/NPC/guard npc/atlantisguardnpc_1.png",
    "images/NPC/Dialogue/dialogues2.png",
    "images/CombatItems/Sprint-1/Enemy_dumbbell.png",
    "images/CombatItems/Sprint-1/Damage Increase Buff.png",
    "images/CombatItems/Sprint-1/Sword_Lvl2.png",
    "images/CombatItems/Sprint-1/AttackDamageDebuff.png",
    "images/CombatItems/Sprint-1/PeriPeriBuff_FIRE.png",
    "images/CombatItems/Sprint-1/poisonBuff.png",
    "images/Potions/defence_potion.png",
    "images/NPC/dialogue_indicator/dialogue.png",
    "images/NPC/dialogue_indicator/dialogue_1.png",
    "images/NPC/dialogue_indicator/dialogue_2.png",
    "images/NPC/dialogue_indicator/dialogue_3.png",
    "images/NPC/dialogue_indicator/dialogue_indicator_sprite_sheet.png",
    "images/Potions/agility_potion.png",
    "images/CombatWeapons-assets-sprint1/trident_Lvl2.png",
    "images/CombatItems/Sprint-1/trident_Lvl2.png",
    "images/NPC/Male_citizen/male_citizen.png",
    "images/Movement/movement.png",
    "images/CombatItems/Sprint-2/H&ADagger.png",
    "images/CombatItems/Sprint-2/Plunger.png",
    "images/Skills/skillAnimations.png",
    "images/Skills/invulnerability.png",
    "images/Crafting-assets-sprint1/materials/toilet_paper.png",
    "images/Crafting-assets-sprint1/materials/gold.png",
    "images/Crafting-assets-sprint1/materials/iron.png",
    "images/Crafting-assets-sprint1/materials/plastic.png",
    "images/Crafting-assets-sprint1/materials/platinum.png",
    "images/Crafting-assets-sprint1/materials/rubber.png",
    "images/Crafting-assets-sprint1/materials/silver.png",
    "images/Crafting-assets-sprint1/materials/steel.png",
    "images/Crafting-assets-sprint1/materials/wood.png",
    "images/Crafting-assets-sprint1/materials/rainbow_poop.png",
    "images/Skills/projectileSprites.png",
    "images/CombatItems/animations/combatItemsAnimation.png",
    "images/CombatItems/Sprint-2/pipe.png",
    "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/Bow.png",
    "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/goldenBowPlunger.png",
    "images/Skills/WrenchAnimation.png",
    "images/countdown/1.png",
    "images/countdown/2.png",
    "images/countdown/3.png",
    "images/countdown/4.png",
    "images/countdown/5.png",
    "images/CombatItems/animations/PlungerBow/plungerBowProjectile.png",
    "images/CombatItems/animations/BuffBounce/mapBounce.png",
    "images/CombatItems/animations/BuffAnimations/buff.png"
  };

  private static final String[] forestTextureAtlases = {

          "images/Skills/skillAnimations.atlas", "images/KeyPrompt/KEY_Q_!.atlas","images/Enemies/gym_bro.atlas",
          "images/terrain_iso_grass.atlas", "images/playerTeleport.atlas",
          "images/Skills/skillAnimations.atlas", "images/Movement/movement.atlas",
          "images/NPC/dialogue_indicator/dialogue.atlas", "images/NPC/male_citizen/male-atlas.atlas",
          "images/NPC/child npc/npcchild.atlas","images/NPC/female npc/npcfemale.atlas",
          "images/NPC/plumber_friend/plumber_friend.atlas", "images/NPC/guard npc/npcguard.atlas",
          "images/NPC/friendly_creature npc/friendly_creature.atlas", "images/NPC/human_guard/human_guard.atlas",
    "images/CombatItems/animations/combatItemsAnimation.atlas", "images/Skills/projectileSprites.atlas",
    "images/Enemies/heracles.atlas", "images/Enemies/mega_poop.atlas",
    "images/Enemies/poop.atlas", "images/CombatItems/animations/PlungerBow/plungerBowProjectile.atlas",
    "images/Enemies/poop.atlas",
          "images/CombatItems/animations/combatItemsAnimation.atlas", "images/Skills/projectileSprites.atlas",
          "images/Enemies/heracles.atlas", "images/Skills/WrenchAnimation.atlas",
          "images/CombatItems/animations/BuffBounce/mapBounce.atlas",
          "images/CombatItems/animations/BuffAnimations/buff.atlas"
  };
  private static final String[] forestSounds = {"sounds/Impact4.ogg", "sounds/plungerArrowSound.mp3",
          "sounds/buffPickupSound.wav", "sounds/WeaponCrafted.wav", "sounds/Blueprint.wav", "sounds/ItemClick.wav",
          "sounds/Scroll.wav", "sounds/new_Weapon_Crafted.wav"};
  private static final String backgroundMusic = "sounds/BGM_03_mp3.mp3";
  private static final String[] forestMusic = {backgroundMusic};

  private final TerrainFactory terrainFactory;

  private Entity player;
  private static Entity heracles;
  private static List<Entity> itemsOnMap = new ArrayList<>();
  private static List<Entity> auraOnMap = new ArrayList<>();
  public static GridPoint2 oneLegGirlPosition;
  public static GridPoint2 oneLegGirlDialoguePosition;
  public static GridPoint2 HumanGuardPosition;
  public static GridPoint2 HumanGuardDialoguePosition;
  public static GridPoint2 PlumberFriendPosition;
  public static GridPoint2 PLumberFriendDialoguePosition;
  public static GridPoint2 GuardPosition;
  public static GridPoint2 GuardDialoguePosition;
  public static GridPoint2 friendlycreaturePosition;
  public static GridPoint2 friendlycreatureDialoguePosition;
  public static GridPoint2 maleCitizenPosition;
  public static GridPoint2 maleCitizenDialoguePosition;
  public static GridPoint2 childPosition;
  public static GridPoint2 childDialoguePosition;
//  public static GridPoint2 friendly_creaturePosition;

  public ForestGameArea(TerrainFactory terrainFactory) {
    super();
    this.terrainFactory = terrainFactory;
    areaEntities = new ArrayList<>();
    ServiceLocator.registerGameArea(this);
  }

  /**
   * Get the player entity from the map.
   * @return player entity.
   */
  @Override
  public Entity getPlayer() {
    return player;
  }

  /**
   * Get Heracles the level 1 boss
   * @return Heracles
   */
  public Entity getHeracles() {
    return heracles;
  }

  /**
   * Check if Heracles is alive on map
   */
  public static boolean ifHeraclesOnMap() {
    return heracles.isDead();
  }


  /** Create the game area, including terrain, static entities (trees), dynamic entities (player) */
  @Override
  public void create() {
    loadAssets();
    displayUI();
    spawnTerrain();
    spawnCraftingTables();
    spawnPotion();
    player = spawnPlayer();
    spawnGymBro();
    heracles = spawnHeracles();
    spawnOneLegGirl();
    spawnHumanGuard();
    spawnfriendlycreature();
    spawnGuard();
    spawnPlumberFriend();
    spawnChild();
    spawnMaleCitizen();
    playMusic();
    spawnSpeedPotion();
    spawnHealthPotion();

    spawnDumbbell();
    spawnArmour(ArmourFactory.ArmourType.slowDiamond, 16, 16);
    spawnArmour(ArmourFactory.ArmourType.baseArmour, 5, 5);
    spawnArmour(ArmourFactory.ArmourType.fastLeather, 7, 7);
    spawnArmour(ArmourFactory.ArmourType.damageReturner, 12, 12);

    spawnDmgBuff();
    spawnFireBuff();
    spawnPoisonBuff();
    spawnSpeedBuff();

  }



  private void displayUI() {
    Entity ui = new Entity();
    ui.addComponent(new GameAreaDisplay("Forest"));
    spawnEntity(ui);
  }

  private void spawnTerrain() {
    // Background terrain
    terrain = terrainFactory.createTerrain(TerrainType.LEVEL_ONE);
    spawnEntity(new Entity().addComponent(terrain));

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

    // Zone A Walls
    spawnEntityAt(ObstacleFactory.createWall(1f, 10f), new GridPoint2(88, 6), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 10f), new GridPoint2(104, 6), false,
            false);
    spawnEntityAt(ObstacleFactory.drawColumnsCollision(10f), new GridPoint2(104, 16), false,
            false);
    spawnEntityAt(ObstacleFactory.drawColumnsCollision(9f), new GridPoint2(80, 16), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 22f), new GridPoint2(79, 18), false,
            false);
    spawnEntityAt(ObstacleFactory.drawColumnsCollision(34f), new GridPoint2(80, 40), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(25f, 1f), new GridPoint2(114, 17), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 10f), new GridPoint2(138, 17), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(10f, 1f), new GridPoint2(123, 26), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 5f), new GridPoint2(123, 26), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(5f, 1f), new GridPoint2(119, 30), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 5f), new GridPoint2(119, 30), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(4f, 1f), new GridPoint2(116, 34), false,
            false);
      // the pool
    spawnEntityAt(ObstacleFactory.createWall(11f, 1f), new GridPoint2(92, 21), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 9f), new GridPoint2(92, 21), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 9f), new GridPoint2(102, 21), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(5f, 1f), new GridPoint2(90, 30), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(5f, 1f), new GridPoint2(100, 30), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 8f), new GridPoint2(90, 30), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 8f), new GridPoint2(104, 30), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(14f, 1f), new GridPoint2(90, 37), false,
            false);

    // Zone B Walls
    spawnEntityAt(ObstacleFactory.createWall(1f, 39f), new GridPoint2(116, 34), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 15f), new GridPoint2(113, 42), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(31f, 1f), new GridPoint2(82, 56), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 16f), new GridPoint2(82, 56), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(6f, 1f), new GridPoint2(82, 72), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(2f, 1f), new GridPoint2(95, 72), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 1f), new GridPoint2(115, 72), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 24f), new GridPoint2(87, 72), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 66f), new GridPoint2(95, 72), false,
            false);

    // Zone D Walls
    spawnEntityAt(ObstacleFactory.drawColumnsCollision(28f), new GridPoint2(60, 96), false,
            false);
    spawnEntityAt(ObstacleFactory.drawColumnsCollision(10f), new GridPoint2(60, 120), false,
            false);
    spawnEntityAt(ObstacleFactory.drawColumnsCollision(21f), new GridPoint2(69, 130), false,
            false);

    // Zone C Walls
    spawnEntityAt(ObstacleFactory.createWall(1f, 26f), new GridPoint2(60, 70), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 50f), new GridPoint2(19, 70), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(25f, 1f), new GridPoint2(36, 69), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(41f, 1f), new GridPoint2(19, 120), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(21f, 1f), new GridPoint2(24, 119), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(21f, 1f), new GridPoint2(24, 99), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 21f), new GridPoint2(24, 99), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 21f), new GridPoint2(45, 99), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 10f), new GridPoint2(44, 72), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 10f), new GridPoint2(54, 72), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(10f, 1f), new GridPoint2(44, 72), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(10f, 1f), new GridPoint2(44, 81), false,
            false);

    // Zone E Walls
    spawnEntityAt(ObstacleFactory.createWall(1f, 49f), new GridPoint2(89, 132), false,
            false);
    spawnEntityAt(ObstacleFactory.drawColumnsCollision(53f), new GridPoint2(89, 181), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(55f, 1f), new GridPoint2(95, 137), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 25f), new GridPoint2(141, 156), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(10f, 1f), new GridPoint2(141, 156), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 7f), new GridPoint2(150, 149), false,
            false);

    // Zone E Walls Pools
    spawnEntityAt(ObstacleFactory.createWall(11f, 1f), new GridPoint2(106, 146), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(11f, 1f), new GridPoint2(119, 146), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(11f, 1f), new GridPoint2(106, 154), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(11f, 1f), new GridPoint2(119, 154), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(20f, 1f), new GridPoint2(108, 156), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(20f, 1f), new GridPoint2(108, 170), false,
            false);

    spawnEntityAt(ObstacleFactory.createWall(1f, 15f), new GridPoint2(108, 156), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 15f), new GridPoint2(127, 156), false,
            false);

    spawnEntityAt(ObstacleFactory.createWall(1f, 9f), new GridPoint2(106, 146), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 9f), new GridPoint2(117, 146), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 9f), new GridPoint2(119, 146), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 9f), new GridPoint2(129, 146), false,
            false);

    // Zone F Walls
    spawnEntityAt(ObstacleFactory.createWall(1f, 12f), new GridPoint2(149, 125), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 23f), new GridPoint2(161, 125), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(6f, 1f), new GridPoint2(143, 125), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(21f, 1f), new GridPoint2(161, 125), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(6f, 1f), new GridPoint2(143, 110), false,
            false);

    // Zone G Walls
    spawnEntityAt(ObstacleFactory.createWall(1f, 20f), new GridPoint2(169, 90), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 45f), new GridPoint2(181, 90), false,
            false);

    // Zone H Walls
    spawnEntityAt(ObstacleFactory.createWall(4f, 1f), new GridPoint2(165, 90), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(4f, 1f), new GridPoint2(165, 89), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(3f, 1f), new GridPoint2(165, 88), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(2f, 1f), new GridPoint2(165, 87), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(5f, 1f), new GridPoint2(181, 90), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(4f, 1f), new GridPoint2(182, 89), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(3f, 1f), new GridPoint2(183, 88), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(2f, 1f), new GridPoint2(184, 87), false,
            false);

    spawnEntityAt(ObstacleFactory.createWall(20f, 1f), new GridPoint2(165, 50), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 40f), new GridPoint2(165, 50), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 40f), new GridPoint2(185, 50), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(4f, 1f), new GridPoint2(165, 51), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(2f, 1f), new GridPoint2(165, 52), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(4f, 1f), new GridPoint2(182, 51), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(2f, 1f), new GridPoint2(184, 52), false,
            false);

    // Zone G Walls pool
    spawnEntityAt(ObstacleFactory.createWall(1f, 26f), new GridPoint2(171, 60), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(1f, 26f), new GridPoint2(179, 60), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(9f, 1f), new GridPoint2(171, 59), false,
            false);
    spawnEntityAt(ObstacleFactory.createWall(9f, 1f), new GridPoint2(171, 85), false,
            false);

    spawnEntityAt(ObstacleFactory.drawColumnsCollision(19f), new GridPoint2(166, 48), false,
            false);
  }
  /**
   * Spawns Proper Trees as an entity into the game
   */
//  private void spawnTrees(int x, int y) {
//      Entity tree = ObstacleFactory.createTree();
//      spawnEntityAt(tree, new GridPoint2(x, y), true, false);
//  }

  public static void removeAuraOnMap(Entity entityToRemove) {

    entityToRemove.setEnabled(false);
    auraOnMap.remove(entityToRemove);

    Gdx.app.postRunnable(entityToRemove::dispose);
    if (entityToRemove.getComponent(AnimationRenderComponent.class) != null) {
      Gdx.app.postRunnable(() -> entityToRemove.getComponent(AnimationRenderComponent.class).stopAnimation());
    }
  }

  public static void removeItemOnMap(Entity entityToRemove) {
    entityToRemove.setEnabled(false);
    itemsOnMap.remove(entityToRemove);
    Gdx.app.postRunnable(entityToRemove::dispose);
  }

  public static void removeProjectileOnMap(Entity entityToRemove) {
    entityToRemove.setEnabled(false);
    Gdx.app.postRunnable(entityToRemove::dispose);
    if (entityToRemove.getComponent(AnimationRenderComponent.class) != null) {
      entityToRemove.getComponent(AnimationRenderComponent.class).stopAnimation();
    }
  }

  /**
   * Spawns speed buff entity into the game
   * Spawns x-pos 10
   * Spawns y-pos 10
   */
  private void spawnSpeedBuff() {
    List<GridPoint2> locations = new ArrayList<>();
    locations.add(new GridPoint2(48,18));
    locations.add(new GridPoint2(129,22));
    locations.add(new GridPoint2(77,86));
    locations.add(new GridPoint2(63,126));
    for (GridPoint2 location : locations) {
      Entity speedbuff = AuraFactory.createWeaponSpeedBuff();
      auraOnMap.add(speedbuff);
      spawnEntityAt(speedbuff, location, true, false);
    }
  }

/*
  */
/**
   * Spawns speed debuff entity into the game
   *//*

  private void spawnSpeedDebuff() {
    List<GridPoint2> locations = new ArrayList<>();
    locations.add(new GridPoint2(84, 178));
    locations.add(new GridPoint2(79, 26));
    for (GridPoint2 location : locations) {
      Entity speedDebuff = AuraFactory.createWeaponSpeedDeBuff();
      auraOnMap.add(speedDebuff);
      spawnEntityAt(speedDebuff, location, true, false);
    }
  }
*/

  /**
   * Spawns damage buff entity into the game
   */
  private void spawnDmgBuff() {
    List<GridPoint2> locations = new ArrayList<>();
    locations.add(new GridPoint2(45, 47));
    locations.add(new GridPoint2(34, 90));
    locations.add(new GridPoint2(96, 22));
    locations.add(new GridPoint2(145, 152));
    for (GridPoint2 location : locations) {
      Entity dmgBuff = AuraFactory.createWeaponDmgBuff();
      auraOnMap.add(dmgBuff);
      spawnEntityAt(dmgBuff, location, true, false);
    }
  }

/*  *//**
   * Spawns damage debuff entity into the game
   *//*
  private void spawnDmgDebuff() {
    List<GridPoint2> locations = new ArrayList<>();
    locations.add(new GridPoint2(96, 65));
    locations.add(new GridPoint2(79, 26));
    for (GridPoint2 location : locations) {
      Entity dmgDebuff = AuraFactory.createWeaponDmgDebuff();
      auraOnMap.add(dmgDebuff);
      spawnEntityAt(dmgDebuff, location, true, false);
    }
  }*/

  /**
   * Spawns fire buff entity into the game
   */
  private void spawnFireBuff() {
    List<GridPoint2> locations = new ArrayList<>();
    locations.add(new GridPoint2(9, 129));
    locations.add(new GridPoint2(100, 100));
    locations.add(new GridPoint2(178, 123));
    locations.add(new GridPoint2(174, 176));
    locations.add(new GridPoint2(96, 2));

    for (GridPoint2 location : locations) {
      Entity fireBuff = AuraFactory.createFireBuff();
      auraOnMap.add(fireBuff);
      spawnEntityAt(fireBuff, location, true, false);
    }
  }

  /**
   * Spawns poison buff entity into the game
   */
  private void spawnPoisonBuff() {
    List<GridPoint2> locations = new ArrayList<>();
    locations.add(new GridPoint2(153, 113));
    locations.add(new GridPoint2(118, 141));
    locations.add(new GridPoint2(178, 163));
    locations.add(new GridPoint2(118, 175));
    for (GridPoint2 location : locations) {
      Entity fireBuff = AuraFactory.createPoisonBuff();
      auraOnMap.add(fireBuff);
      spawnEntityAt(fireBuff, location, true, false);
    }
  }

  /**
   * Spawn rock in a certain position. - Team 5 1map4all @LYB
   */
//  private void spawnRock(int x, int y) {
//    Entity rock = ObstacleFactory.createRock();
//    spawnEntityAt(rock, new GridPoint2(x, y), false, false);
//  }

  /**
   * spawn an armour on the map based on the input armour type
   * @param armourType armourType of the armour to be spawned
   */
  private void spawnArmour(ArmourFactory.ArmourType armourType, int x, int y) {
    Entity armour = ArmourFactory.createArmour(armourType);
    itemsOnMap.add(armour);
    spawnEntityAt(armour, new GridPoint2( x,y), true, false);
  }

  /**
   * Spawn small tress in a certain position. - Team 5 1map4all @LYB
   */
//  private void spawnSmallTrees(int x, int y) {
//    Entity tree = ObstacleFactory.createSmallTree();
//    spawnEntityAt(tree, new GridPoint2(x, y), false, false);
//  }

  /**
   * Spawn column in a certain position. - Team 5 1map4all @LYB
   * @param x x-axis for the position(horizontal).
   * @param y y-axis for the position (vertical).
   */
//  private void spawnColumn(int x, int y) {
//    Entity column = ObstacleFactory.createColumn();
//    spawnEntityAt(column, new GridPoint2(x, y), false, false);
//    }

  public void spawnEntityOnMap(Entity entity,GridPoint2 position, Boolean centreX, Boolean centreY) {
    spawnEntityAt(entity, position, centreX, centreY);
  }

  /**
   * Spawns the crafting table entity on the forest map
   */
  public void spawnCraftingTables() {
    Entity craftingTable1 = ObstacleFactory.createCraftingTableForest();
    craftingTable1.setEntityType(EntityTypes.CRAFTINGTABLE);
    spawnEntityAt(craftingTable1, new GridPoint2(100, 10), true, true);
    Entity craftingTable2 = ObstacleFactory.createCraftingTableForest();
    craftingTable2.setEntityType(EntityTypes.CRAFTINGTABLE);
    spawnEntityAt(craftingTable2, new GridPoint2(47, 100), true, true);
    Entity craftingTable3 = ObstacleFactory.createCraftingTableForest();
    craftingTable3.setEntityType(EntityTypes.CRAFTINGTABLE);
    spawnEntityAt(craftingTable3, new GridPoint2(96, 139), true, true);
    Entity craftingTable4 = ObstacleFactory.createCraftingTableForest();
    craftingTable4.setEntityType(EntityTypes.CRAFTINGTABLE);
    spawnEntityAt(craftingTable4, new GridPoint2(144, 111), true, true);
  }

  /**
   * Spawns speed potion entity into the game
   * Spawns x-pos 30
   * Spawns y-pos 23
   */
  private void spawnSpeedPotion() {
    Entity speedPotion = PotionFactory.createSpeedPotion();
    itemsOnMap.add(speedPotion);
    spawnEntityAt(speedPotion, new GridPoint2(90, 10), true, true);
  }

  private void spawnHealthPotion() {
    Entity speedPotion = PotionFactory.createHealthPotion();
    itemsOnMap.add(speedPotion);
    spawnEntityAt(speedPotion, new GridPoint2(93, 10), true, true);
  }

  /**
   * Spawns Level 2 dagger entity into the game
   * Spawns x-pos 10
   * Spawns y-pos 10
   */
  //private void spawnDagger() {
   // Entity dagger = WeaponFactory.createDagger();
   // weaponOnMap.add(dagger);
   // spawnEntityAt(dagger, new GridPoint2(20, 20), true, false);
 // }
  /**
   * Spawns second Level 2 dagger entity into the game
   * Spawns x-pos 18
   * Spawns y-pos 10
   */
 // private void spawnDaggerTwo() {
  //  Entity daggerTwo = WeaponFactory.createDaggerTwo();
  //  weaponOnMap.add(daggerTwo);
  //  spawnEntityAt(daggerTwo, new GridPoint2(18,10), true, false);
  //}
  /**
   * Spawns dumbbell entity into the game
   * Spawns x-pos 5
   * Spawns y-pos 10
   */

  private void spawnDumbbell() {
    Entity dumbbell = WeaponFactory.createDumbbell();
    itemsOnMap.add(dumbbell);
    spawnEntityAt(dumbbell, new GridPoint2(7,10), true, false);
  }

  /**
   * Spawns level 3 Hera and Athena entity into the game
   * Spawns x-pos 10
   * Spawns y-pos 4
   */
  private void spawnHeraAndAthena() {
    Entity heraAthenaDag = WeaponFactory.createHeraAthenaDag();
  //  weaponOnMap.add(heraAthenaDag);
    spawnEntityAt(heraAthenaDag, new GridPoint2(10,4), true, false);
  }

  /**
   * Spawns basic plunger into game
   * Spawns x-pos 20
   * Spawns y-pos 4
   */
  //private void spawnPlunger() {
  //  Entity plunger = WeaponFactory.createPlunger();
  //  weaponOnMap.add(plunger);
  //  spawnEntityAt(plunger, new GridPoint2(20,4), true, false);
 // }

  /**
   * Spawns basic PVC pipe into game
   * Spawns x-pos 15
   * Spawns y-pos 10
   */
 // private void spawnPipe() {
 //   Entity plunger = WeaponFactory.createPipe();
 //   weaponOnMap.add(plunger);
 //   spawnEntityAt(plunger, new GridPoint2(15,10), true, false);
 // }

  /**
   * Spawns Level 2 Sword entity into the game
   * Spawns x-pos 20
   * Spawns y-pos 20
   */
 // private void spawnSwordLvl2() {
 //   Entity SwordLvl2 = WeaponFactory.createSwordLvl2();
  //  weaponOnMap.add(SwordLvl2);
  //  spawnEntityAt(SwordLvl2, new GridPoint2(16,18), true, false);
 // }

  /**
   * Spawns Level 2 Trident entity into the game
   */
 // private void spawnTridentLvl2() {
  //  Entity tridentLvl2 = WeaponFactory.createTridentLvl2();
  //  weaponOnMap.add(tridentLvl2);
  //  spawnEntityAt(tridentLvl2, new GridPoint2(12,15), true, false);
 // }

  /**
   * Spawn a potion in a random position.
   */
  private void spawnPotion() {
//    GridPoint2 minPos = new GridPoint2(0, 0);
//    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);
//    GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
//    Entity potion = PotionFactory.createSpeedPotion();
//    this.spawnEntityAt(potion, randomPos, true, false);

    Entity potion = PotionFactory.createDamageReductionPotion();
    this.spawnEntityAt(potion, new GridPoint2(5,5), true, false);
  }

  /**
   * Spawns a plug Entity to assist with map transition. - Team 5 1map4all
   */
//  private void spawnPlug() {
//    GridPoint2 plugPosition = new GridPoint2(12, 17);
//    Entity plug = NPCFactory.createPlug(player);
//    areaEntities.add(plug);
//
//    spawnEntityAt(plug, plugPosition, true, true);
//  }

  /**
   * Spawns the player entity, with a skill and combat animator overlaid above the player.
   * @return the player entity
   */
  private Entity spawnPlayer() {
    Entity newPlayer = PlayerFactory.createPlayer();
    Entity newSkillAnimator = PlayerFactory.createSkillAnimator(newPlayer);
    Entity newKeyPromptAnimator= PlayerFactory.createKeyPromptAnimator(newPlayer);

//    Entity newCombatAnimator = PlayerFactory.createCombatAnimator(newPlayer);
    spawnEntityAt(newPlayer, PLAYER_SPAWN, true, true);
    spawnEntityAt(newSkillAnimator, PLAYER_SPAWN, true, true);
    spawnEntityAt(newKeyPromptAnimator, PLAYER_SPAWN, true, true);
//    spawnEntityAt(newCombatAnimator, PLAYER_SPAWN, true, true);
    newPlayer.getComponent(PlayerActions.class).setSkillAnimator(newSkillAnimator);
    newPlayer.getComponent(PlayerKeyPrompt.class)
            .setKeyPromptAnimator(newKeyPromptAnimator);
//    newPlayer.getComponent(PlayerTouchAttackComponent.class).setCombatAnimator(newCombatAnimator);
//    newPlayer.getComponent(InventoryComponent.class).setCombatAnimator(newCombatAnimator);

    return newPlayer;
  }

  /**
   * Spawns a projectile at the player entity's coordinates.
   */
  public void spawnPlayerProjectile() {
    Entity newProjectile = ProjectileFactory.createBasePlayerProjectile(player, 0);
    spawnEntityAt(newProjectile,
            new GridPoint2((int) player.getCenterPosition().x, (int) player.getCenterPosition().y),
            true, true);
  }

  /**
   * Spawns a spray of projectiles at the player entity's coordinates.
   */
  public void spawnPlayerProjectileSpray() {
    double[] sprayAngles = {0,0.25,0.5,0.75,1,1.25,1.5,1.75};
    for (int i = 0; i < sprayAngles.length; ++i) {
      Entity newProjectile = ProjectileFactory.createBasePlayerProjectile(player,sprayAngles[i]);
      spawnEntityAt(newProjectile,
              new GridPoint2((int) player.getCenterPosition().x, (int) player.getCenterPosition().y),
              true, true);
    }
  }

  /**
   * Spawns a spray of projectiles at the player entity's coordinates.
   */
  public void spawnPlayerProjectileCone() {
    double[] sprayAngles = {0,0.06,0.11,1.89,1.94};
    for (int i = 0; i < sprayAngles.length; ++i) {
      Entity newProjectile = ProjectileFactory.createWrenchPlayerProjectile(player,sprayAngles[i]);
      spawnEntityAt(newProjectile,
              new GridPoint2((int) player.getCenterPosition().x, (int) player.getCenterPosition().y),
              true, true);
    }
  }

  /**
   * Spawns a weapon bow projectile at the player entity's coordinates.
   */
  public void spawnWeaponProjectile() { //TEAM 04 WIP

    Entity newProjectile = ProjectileFactory.createWeaponProjectile(player, 0);
    spawnEntityAt(newProjectile,
            new GridPoint2((int) player.getCenterPosition().x, (int) player.getCenterPosition().y),
            true, true);

  }


  /**
   * Spawns an AOE attack at the player entity's coordinates.
   */
  public Entity spawnPlayerAOE() {
    Entity newProjectile = ProjectileFactory.createPlayerAOE(player, 0);
    spawnEntityAt(newProjectile,
            new GridPoint2((int) player.getCenterPosition().x, (int) player.getCenterPosition().y),
            true, true);
    return newProjectile;
  }

/*
>>>>>>> e73afe5ba69f8264e7bb25c687ce9c6feaba9d20
   private void spawnPlungerBow() {
    Entity c = WeaponFactory.createPlungerBow();
    itemsOnMap.add(c);
    spawnEntityAt(c, new GridPoint2(5,4), true, false);
   }
*/

  /**
   * Spawn female NPC in random position. - Team 7 all-mid-npc
   */
  private void spawnOneLegGirl() {

    oneLegGirlPosition = new GridPoint2(87, 28);
    oneLegGirlDialoguePosition = new GridPoint2(87, 29);

    Entity oneLegGirl = NPCFactory.createOneLegGirl(player);
    spawnEntityAt(oneLegGirl, oneLegGirlPosition, true, true);
    areaEntities.add(oneLegGirl);

    Entity dialogue = DialogueFactory.createDialogue();
    spawnEntityAt(dialogue, oneLegGirlDialoguePosition, true, true);
  }

  public static GridPoint2 getOneLegGirlPosition() {
    return oneLegGirlPosition;
  }

  public static Vector2 GridPointToVector(GridPoint2 position) {
    int playerX = position.x;
    int playerY = position.y;
    return new Vector2(playerX, playerY);
  }

  /**
   * Spawn child NPC in random position. - Team 7 all-mid-npc
   */
  private void spawnChild() {
    childPosition = new GridPoint2(33,95);
    childDialoguePosition = new GridPoint2(33, 96);

    Entity child = NPCFactory.createChild(player);
    spawnEntityAt(child, childPosition, true, true);
    areaEntities.add(child);

    Entity dialogue = DialogueFactory.createDialogue();
    spawnEntityAt(dialogue, childDialoguePosition, true, true);
    //areaEntities.add(dialogue);
  }

  public static GridPoint2 getChildPosition() {
    return childPosition;
  }


  private void spawnHumanGuard() {
    HumanGuardPosition = new GridPoint2(110, 41);
    HumanGuardDialoguePosition = new GridPoint2(110, 42);

    Entity humanguard = NPCFactory.createHumanGuard(player);
    spawnEntityAt(humanguard, HumanGuardPosition, true, true);
    areaEntities.add(humanguard);

    Entity dialogue = DialogueFactory.createDialogue();
    spawnEntityAt(dialogue, HumanGuardDialoguePosition, true, true);
    areaEntities.add(dialogue);
  }

  private void spawnPlumberFriend() {
    PlumberFriendPosition = new GridPoint2(96, 13);
    PLumberFriendDialoguePosition = new GridPoint2(96, 14);

    Entity plumberfriend = NPCFactory.createPlumberFriend(player);
    spawnEntityAt(plumberfriend, PlumberFriendPosition, true, true);
    areaEntities.add(plumberfriend);

    Entity dialogue = DialogueFactory.createDialogue();
    spawnEntityAt(dialogue, PLumberFriendDialoguePosition, true, true);
    areaEntities.add(dialogue);
  }

  private void spawnGuard() {
    GuardPosition = new GridPoint2(100, 16);
    GuardDialoguePosition = new GridPoint2(100, 17);

    Entity guard = NPCFactory.createGuard(player);
    spawnEntityAt(guard, GuardPosition, true, true);
    areaEntities.add(guard);

    Entity dialogue = DialogueFactory.createDialogue();
    spawnEntityAt(dialogue, GuardDialoguePosition, true, true);
    areaEntities.add(dialogue);
  }
  public static GridPoint2 getGuardPosition() {
    return GuardPosition;
  }

  private void spawnfriendlycreature() {
    friendlycreaturePosition = new GridPoint2(97, 16);
    friendlycreatureDialoguePosition = new GridPoint2(97, 17);

    Entity friendlycreature = NPCFactory.createFriendlyCreature(player);
    spawnEntityAt(friendlycreature, friendlycreaturePosition, true, true);
    areaEntities.add(friendlycreature);

    Entity dialogue = DialogueFactory.createDialogue();
    spawnEntityAt(dialogue, friendlycreaturePosition, true, true);
    areaEntities.add(dialogue);
  }

  /**
   * Spawn male NPC in random position. - Team 7 all-mid-npc
   */
  private void spawnMaleCitizen() {
    maleCitizenPosition = new GridPoint2(74,121);
    maleCitizenDialoguePosition = new GridPoint2(74, 122);

    Entity male_citizen = NPCFactory.createMale_citizen(player);
    spawnEntityAt(male_citizen, maleCitizenPosition, true, true);
    areaEntities.add(male_citizen);

    Entity dialogue = DialogueFactory.createDialogue();
    spawnEntityAt(dialogue, maleCitizenDialoguePosition, true, true);
    areaEntities.add(dialogue);
  }
  public static GridPoint2 getMaleCitizenPosition() {
    return maleCitizenPosition;
  }

  /**
   * Spawn gym bros in positions.
   */
  private void spawnGymBro() {
    ArrayList<GridPoint2> positions = new ArrayList<>();
    positions.add(new GridPoint2(99, 65));
    positions.add(new GridPoint2(89, 65));
    positions.add(new GridPoint2(105, 62));

    positions.add(new GridPoint2(34, 100));
    positions.add(new GridPoint2(20, 106));
    positions.add(new GridPoint2(25, 99));

    positions.add(new GridPoint2(80, 100));
    positions.add(new GridPoint2(63, 106));
    positions.add(new GridPoint2(90, 99));

    positions.add(new GridPoint2(118, 173));
    positions.add(new GridPoint2(118, 141));
    positions.add(new GridPoint2(145, 152));

    positions.add(new GridPoint2(153, 123));
    positions.add(new GridPoint2(153, 141));
    positions.add(new GridPoint2(153, 130));

    positions.add(new GridPoint2(153, 113));
    positions.add(new GridPoint2(175, 113));
    positions.add(new GridPoint2(178, 123));

    for (GridPoint2 position: positions) {
      Entity gymBro = NPCFactory.createGymBro(player);
      areaEntities.add(gymBro);
      spawnEntityAt(gymBro, position, true, true);
    }
  }

  /**
   * Spawn Heracles in a position.
   */
  private Entity spawnHeracles() {
    GridPoint2 position = new GridPoint2(174, 65);
    Entity heraclesBoss = NPCFactory.createHeracles(player);
    areaEntities.add(heraclesBoss);
    spawnEntityAt(heraclesBoss, position, true, true);
    return heraclesBoss;
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
    // Dispose of all internal entities in the area
    for (Entity entity : areaEntities) {
      entity.dispose();
    }
    logger.info("Disposed of ForestAreaEntities");

    ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class).stop();
    logger.info("Unloading forest assets");
    this.unloadAssets();
  }

  /**
   * toString returning a string of the classes name
   * @return (String) class name
   */
  @Override
  public String toString() {
    return "ForestGameArea";
  }
}
