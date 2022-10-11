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
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.utils.math.GridPoint2Utils;
import com.deco2800.game.utils.math.RandomUtils;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/** Underground area for the demo game with trees, a player, and some enemies. */
public class UndergroundGameArea extends GameArea {
    private static final Logger logger = LoggerFactory.getLogger(UndergroundGameArea.class);
    private static final GridPoint2 PLAYER_SPAWN = new GridPoint2(35, 10);
    private static final float WALL_WIDTH = 0.1f;
    private static Entity megaPoop;
    private static List<Entity> ItemsOnMap = new ArrayList<>();
    private static List<Entity> auraOnMap = new ArrayList<>();
    private static final String[] undergroundTextures = {
            "images/box_boy_leaf.png",
            "images/Enemies/gym_bro.png",
            "images/Enemies/poops.png",
            "images/Enemies/poopSludge.png",
            "images/level_1_tiledmap/32x32/wall_tile.png",
            "images/level_2_tiledmap/dirt.png",
            "images/level_2_tiledmap/drain_empty.png",
            "images/level_2_tiledmap/columns.png",
            "images/level_2_tiledmap/torch.png",
            "images/level_2_tiledmap/floor1.png",
            "images/level_2_tiledmap/floor2.png",
            "images/level_2_tiledmap/floor3.png",
            "images/level_2_tiledmap/floor4.png",
            "images/level_2_tiledmap/floor5.png",
            "images/level_2_tiledmap/floor6.png",
            "images/level_2_tiledmap/floor7.png",
            "images/level_2_tiledmap/wall.png",
            "images/level_2_tiledmap/wall_corner.png",
            "images/level_2_tiledmap/wall_edge.png",
            "images/level_2_tiledmap/wall_side.png",
            "images/level_2_tiledmap/water.png",
            "images/NPC/friendly_creature npc/Friendly_creature.png",
            "images/ghost_1.png",
            "images/grass_1.png",
            "images/grass_2.png",
            "images/grass_3.png",
//            "images/level_2_tiledmap/32x32/grass.png",
//            "images/level_2_tiledmap/32x32/purple_cobble.png",
            "images/Crafting-assets-sprint1/crafting table/craftingTable2.png",
            "images/Crafting-assets-sprint1/materials/gold.png",
            "images/Crafting-assets-sprint1/materials/iron.png",
            "images/Crafting-assets-sprint1/materials/plastic.png",
            "images/Crafting-assets-sprint1/materials/platinum.png",
            "images/Crafting-assets-sprint1/materials/rubber.png",
            "images/Crafting-assets-sprint1/materials/silver.png",
            "images/Crafting-assets-sprint1/materials/steel.png",
            "images/Crafting-assets-sprint1/materials/wood.png",
            "images/Crafting-assets-sprint1/materials/rainbow_poop.png",
            "images/Crafting-assets-sprint1/materials/toilet_paper.png",
            "images/PlayerStatDisplayGraphics/Health-plunger/plunger_1.png",
            "images/PlayerStatDisplayGraphics/Health-plunger/plunger_2.png",
            "images/PlayerStatDisplayGraphics/Health-plunger/plunger_3.png",
            "images/PlayerStatDisplayGraphics/Health-plunger/plunger_4.png",
            "images/PlayerStatDisplayGraphics/Health-plunger/plunger_5.png",
            "images/PlayerStatDisplayGraphics/Health-plunger/plunger_6.png",
            "images/PlayerStatDisplayGraphics/Health-plunger/plunger_7.png",
            "images/PlayerStatDisplayGraphics/Health-plunger/plunger_8.png",
            "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_1.png",
            "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_2.png",
            "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_3.png",
            "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_4.png",
            "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_5.png",
            "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_6.png",
            "images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_7.png",
            "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_1.png",
            "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_2.png",
            "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_3.png",
            "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_4.png",
            "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_5.png",
            "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_6.png",
            "images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_7.png",
            "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/Athena.png",
            "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/Bow.png",
            "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/goldenBowPlunger.png",
            "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/Hera.png",
            "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/heraAthena.png",
            "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/Pipe.png",
            "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/Plunger.png",
            "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/Sword.png",
            "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/Trident.png",
            "images/CombatItems/Sprint-1/Sword_Lvl2.png",
            "images/CombatItems/Sprint-1/Level 2 Dagger 1.png",
            "images/CombatItems/Sprint-1/Level 2 Dagger 2png.png",
            "images/CombatItems/Sprint-1/Weapon Speed Buff.png",
            "images/CombatItems/Sprint-1/AttackSpeedDebuff.png",
            "images/CombatItems/Sprint-1/Enemy_dumbbell.png",
            "images/CombatItems/Sprint-1/Damage Increase Buff.png",
            "images/CombatItems/Sprint-1/Sword_Lvl2.png",
            "images/CombatItems/Sprint-1/AttackDamageDebuff.png",
            "images/CombatItems/Sprint-1/PeriPeriBuff_FIRE.png",
            "images/CombatItems/Sprint-1/poisonBuff.png",
            "images/CombatItems/animations/combatItemsAnimation.png",
            "images/CombatItems/Sprint-2/pipe.png",
            "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/Bow.png",
            "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/goldenBowPlunger.png",
            "images/CombatItems/Sprint-1/trident_Lvl2.png",
            "images/CombatItems/Sprint-2/H&ADagger.png",
            "images/CombatItems/Sprint-2/Plunger.png",
            "images/CombatItems/animations/PlungerBow/plungerBowProjectile.png",
            "images/level_2_tiledmap/pipe1.png",
            "images/level_2_tiledmap/pipe2.png",
            "images/level_2_tiledmap/statues.jpg",
            "images/level_2_tiledmap/statues2.jpg",
            "images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/Trident.png",
            "images/CombatItems/Sprint-1/Level 2 Dagger 1.png",
            "images/CombatItems/Sprint-1/Level 2 Dagger 2png.png",
            "images/CombatItems/Sprint-1/Enemy_dumbbell.png",
            "images/CombatItems/Sprint-1/Sword_Lvl2.png",
            "images/CombatItems/Sprint-1/trident_Lvl2.png",
            "images/CombatItems/Sprint-2/H&ADagger.png",
            "images/CombatItems/Sprint-2/Plunger.png",
            "images/CombatItems/Sprint-2/pipe.png",
            "images/countdown/1.png",
            "images/countdown/2.png",
            "images/countdown/3.png",
            "images/countdown/4.png",
            "images/countdown/5.png",
            "images/CombatItems/animations/BuffBounce/mapBounce.png",
            "images/CombatItems/animations/BuffAnimations/buff.png"

    };

    public static String[] newTextures;
    private static final String[] undergroundTextureAtlases = {
            "images/terrain_iso_grass.atlas", "images/playerTeleport.atlas",
            "images/Skills/skillAnimations.atlas", "images/Enemies/gym_bro.atlas",
            "images/Movement/movement.atlas", "images/KeyPrompt/KEY_Q_!.atlas",
            "images/CombatItems/animations/combatItemsAnimation.atlas", "images/CombatItems/animations/PlungerBow/plungerBowProjectile.atlas",
            "images/Enemies/mega_poop.atlas", "images/Enemies/poop.atlas", "images/NPC/guard npc/npcguard.atlas" ,
            "images/NPC/friendly_creature npc/friendly_creature.atlas",
            "images/CombatItems/animations/BuffBounce/mapBounce.atlas",
            "images/CombatItems/animations/BuffAnimations/buff.atlas"

    };
    private static final String[] undergroundSounds = {"sounds/Impact4.ogg", "sounds/plungerArrowSound.mp3",
            "sounds/buffPickupSound.wav", "sounds/WeaponCrafted.wav", "sounds/Blueprint.wav", "sounds/ItemClick.wav",
            "sounds/Scroll.wav", "sounds/new_Weapon_Crafted.wav"};
    private static final String backgroundMusic = "sounds/BGM_03_mp3.mp3";
    private final String[] undergroundMusic = {backgroundMusic};

    private final TerrainFactory terrainFactory;
//    public static GridPoint2 GuardPosition;
//    public static GridPoint2 GuardDialoguePosition;
//    public static GridPoint2 friendlycreaturePosition;
//    public static GridPoint2 friendlycreatureDialoguePosition;

    private Entity player;


    public UndergroundGameArea(TerrainFactory terrainFactory) {
        super();
        this.terrainFactory = terrainFactory;

        ServiceLocator.registerGameArea(this);
    }

    /**
     * Get the player entity from the map.
     *
     * @return player entity.
     */
    public Entity getPlayer() {
        return player;
    }

    /**
     * Create the game area, including terrain, static entities (trees), dynamic entities (player)
     */
    @Override
    public void create() {
        loadAssets();
        displayUI();
        spawnTerrain();
        spawnCraftingTable();
        player = spawnPlayer();
        spawnPoops();
//        spawnGuard();
//        spawnfriendlycreature();
        megaPoop = spawnMegaPoop();
        playMusic();

        spawnSpeedDebuff();
        spawnDmgBuff();
        spawnDmgDebuff();
        spawnFireBuff();
        spawnPoisonBuff();
        spawnSpeedBuff();
    }

    /**
     * Check if Mega Poop is alive on map
     */
    public static boolean ifMegaPoopOnMap() {
        return megaPoop.isDead();
    }


    private void displayUI() {
        Entity ui = new Entity();
        ui.addComponent(new GameAreaDisplay("Underground"));
        spawnEntity(ui);
    }

    private void spawnTerrain() {
        // Background terrain
        terrain = terrainFactory.createTerrain(TerrainType.LEVEL_TWO);
        spawnEntity(new Entity().addComponent(terrain));

        // Terrain walls
        float tileSize = terrain.getTileSize();
        GridPoint2 tileBounds = terrain.getMapBounds(0);
        Vector2 worldBounds = new Vector2(tileBounds.x * tileSize, tileBounds.y * tileSize);

        // create the world borders

        // Left
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 117), new GridPoint2(5,
                        2), false,
                false);
        // Right
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 117),
                new GridPoint2(tileBounds.x - 6, 2),
                false,
                false);
        // Top
        spawnEntityAt(
                ObstacleFactory.createWall(89, WALL_WIDTH),
                new GridPoint2(5, tileBounds.y - 6),
                false,
                false);
        // Bottom
        spawnEntityAt(
                ObstacleFactory.createWall(42, WALL_WIDTH), new GridPoint2(5, 2), false
                , false);

        spawnBottomRoomBorders();
        spawnTopRoomBorders();
        spawnRightTunnelBorders();
        spawnRightRoomBorders();
        spawnMiddleHallBorders();
    }

    private void spawnBottomRoomBorders() {
        // Left
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 15), new GridPoint2(24,
                        2), false,
                false);
        // Top
        spawnEntityAt(
                ObstacleFactory.createWall(8, WALL_WIDTH),
                new GridPoint2(24, 17),
                false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(8, WALL_WIDTH),
                new GridPoint2(39, 17),
                false,
                false);
        // Right
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 15),
                new GridPoint2(47, 2),
                false,
                false);

        // Tunnel
        // Left
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 19), new GridPoint2(32,
                        17), false,
                false);
        // Right
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 19),
                new GridPoint2(39, 17),
                false,
                false);
    }

    private void spawnTopRoomBorders() {
        // Left
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 9), new GridPoint2(31,
                        93), false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 17), new GridPoint2(25,
                        102), false,
                false);
//        // Right
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 17),
                new GridPoint2(54, 102),
                false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 9),
                new GridPoint2(48, 93),
                false,
                false);
        // Bottom
        spawnEntityAt(
                ObstacleFactory.createWall(5, WALL_WIDTH), new GridPoint2(31, 93), false
                , false);
        spawnEntityAt(
                ObstacleFactory.createWall(6, WALL_WIDTH), new GridPoint2(42, 93), false
                , false);
        spawnEntityAt(
                ObstacleFactory.createWall(6, WALL_WIDTH), new GridPoint2(25, 102), false
                , false);
        spawnEntityAt(
                ObstacleFactory.createWall(6, WALL_WIDTH), new GridPoint2(48, 102), false
                , false);
        // Tunnel
        // Left
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 8), new GridPoint2(36,
                        85), false,
                false);
        // Right
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 8),
                new GridPoint2(42, 85),
                false,
                false);
    }

    private void spawnRightTunnelBorders() {
        // Top Tunnel
        // Top
        spawnEntityAt(
                ObstacleFactory.createWall(35, WALL_WIDTH),
                new GridPoint2(53, 72),
                false,
                false);
        // Bottom
        spawnEntityAt(
                ObstacleFactory.createWall(30, WALL_WIDTH), new GridPoint2(53, 67), false
                , false);
        // Left
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 5),
                new GridPoint2(83, 62),
                false,
                false);
        // Right
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 10),
                new GridPoint2(88, 62),
                false,
                false);
        // Bottom Tunnel
        // Top
        spawnEntityAt(
                ObstacleFactory.createWall(10, WALL_WIDTH),
                new GridPoint2(49, 50),
                false,
                false);
        // Bottom
        spawnEntityAt(
                ObstacleFactory.createWall(10, WALL_WIDTH), new GridPoint2(49, 45), false
                , false);
    }

    private void spawnRightRoomBorders() {
        // Left Room
        // Top
        spawnEntityAt(
                ObstacleFactory.createWall(12, WALL_WIDTH),
                new GridPoint2(59, 55),
                false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(6, WALL_WIDTH),
                new GridPoint2(71, 50),
                false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(6, WALL_WIDTH),
                new GridPoint2(77, 62),
                false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(6, WALL_WIDTH),
                new GridPoint2(88, 62),
                false,
                false);
        // Left
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 5),
                new GridPoint2(59, 50),
                false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 2),
                new GridPoint2(59, 43),
                false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 12),
                new GridPoint2(77, 50),
                false,
                false);
        // Right
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 5),
                new GridPoint2(71, 50),
                false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 2),
                new GridPoint2(71, 43),
                false,
                false);
        // Bottom
        spawnEntityAt(
                ObstacleFactory.createWall(12, WALL_WIDTH), new GridPoint2(59, 43), false
                , false);
        spawnEntityAt(
                ObstacleFactory.createWall(23, WALL_WIDTH), new GridPoint2(71, 45), false
                , false);
    }

    private void spawnMiddleHallBorders() {
        // Left
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 19), new GridPoint2(22,
                        36), false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 10), new GridPoint2(16,
                        55), false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 13), new GridPoint2(10,
                        73), false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 4), new GridPoint2(14,
                        91), false,
                false);
        // Top
        spawnEntityAt(
                ObstacleFactory.createWall(5, WALL_WIDTH),
                new GridPoint2(5, 73),
                false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(9, WALL_WIDTH),
                new GridPoint2(5, 91),
                false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(13, WALL_WIDTH),
                new GridPoint2(14, 95),
                false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(9, WALL_WIDTH),
                new GridPoint2(27, 85),
                false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(11, WALL_WIDTH),
                new GridPoint2(42, 85),
                false,
                false);
//        // Right
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 10),
                new GridPoint2(27, 85),
                false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 13),
                new GridPoint2(53, 72),
                false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 12),
                new GridPoint2(53, 55),
                false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 5),
                new GridPoint2(49, 50),
                false,
                false);
        spawnEntityAt(
                ObstacleFactory.createWall(WALL_WIDTH, 9),
                new GridPoint2(49, 36),
                false,
                false);
        // Bottom
        spawnEntityAt(
                ObstacleFactory.createWall(10, WALL_WIDTH), new GridPoint2(22, 36), false
                , false);
        spawnEntityAt(
                ObstacleFactory.createWall(10, WALL_WIDTH), new GridPoint2(39, 36), false
                , false);
        spawnEntityAt(
                ObstacleFactory.createWall(6, WALL_WIDTH), new GridPoint2(16, 55), false
                , false);
        spawnEntityAt(
                ObstacleFactory.createWall(11, WALL_WIDTH), new GridPoint2(5, 65), false
                , false);
        spawnEntityAt(
                ObstacleFactory.createWall(5, WALL_WIDTH), new GridPoint2(5, 86), false
                , false);
        spawnEntityAt(
                ObstacleFactory.createWall(4, WALL_WIDTH), new GridPoint2(49, 55), false
                , false);
    }

    /**
     * Spawns the player entity, with a skill animator overlaid above the player.
     *
     * @return the player entity
     */
    private Entity spawnPlayer() {
        Entity newPlayer = PlayerFactory.createPlayer();
        Entity newSkillAnimator = PlayerFactory.createSkillAnimator(newPlayer);
        Entity newKeyPromptAnimator = PlayerFactory.createKeyPromptAnimator(newPlayer);
        spawnEntityAt(newPlayer, PLAYER_SPAWN, true, true);
        spawnEntityAt(newSkillAnimator, PLAYER_SPAWN, true, true);
        newPlayer.getComponent(PlayerActions.class).setSkillAnimator(newSkillAnimator);
        newPlayer.getComponent(PlayerKeyPrompt.class)
                .setKeyPromptAnimator(newKeyPromptAnimator);
        return newPlayer;
    }

    /**
     * Spawns speed buff entity into the game
     */
    private void spawnSpeedBuff() {
        List<GridPoint2> locations = new ArrayList<>();
        locations.add(new GridPoint2(52,69));
        locations.add(new GridPoint2(85,54));
        locations.add(new GridPoint2(32,10));
        locations.add(new GridPoint2(9,88));
        for (GridPoint2 location : locations) {
            Entity speedbuff = AuraFactory.createWeaponSpeedBuff();
            auraOnMap.add(speedbuff);
            spawnEntityAt(speedbuff, location, true, false);
        }
    }

    /**
     * Spawns speed debuff entity into the game
     */
    private void spawnSpeedDebuff() {
        List<GridPoint2> locations = new ArrayList<>();
        locations.add(new GridPoint2(35, 98));
        locations.add(new GridPoint2(54, 47));
        for (GridPoint2 location : locations) {
            Entity speedDebuff = AuraFactory.createWeaponSpeedDeBuff();
            auraOnMap.add(speedDebuff);
            spawnEntityAt(speedDebuff, location, true, false);
        }
    }

    /**
     * Spawns damage buff entity into the game
     */
    private void spawnDmgBuff() {
        List<GridPoint2> locations = new ArrayList<>();
        locations.add(new GridPoint2(35, 39));
        locations.add(new GridPoint2(70, 70));
        locations.add(new GridPoint2(29, 112));
        for (GridPoint2 location : locations) {
            Entity dmgBuff = AuraFactory.createWeaponDmgBuff();
            auraOnMap.add(dmgBuff);
            spawnEntityAt(dmgBuff, location, true, false);
        }
    }

    /**
     * Spawns damage debuff entity into the game
     */
    private void spawnDmgDebuff() {
        List<GridPoint2> locations = new ArrayList<>();
        locations.add(new GridPoint2(80, 48));
        locations.add(new GridPoint2(49, 110));
        locations.add(new GridPoint2(20, 90));
        for (GridPoint2 location : locations) {
            Entity dmgDebuff = AuraFactory.createWeaponDmgDebuff();
            auraOnMap.add(dmgDebuff);
            spawnEntityAt(dmgDebuff, location, true, false);
        }
    }

    /**
     * Spawns fire buff entity into the game
     */
    private void spawnFireBuff() {
        List<GridPoint2> locations = new ArrayList<>();
        locations.add(new GridPoint2(22, 55));
        locations.add(new GridPoint2(35, 24));
        locations.add(new GridPoint2(65, 47));
        locations.add(new GridPoint2(38, 85));

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
        locations.add(new GridPoint2(84, 64));
        locations.add(new GridPoint2(37, 47));
        locations.add(new GridPoint2(45, 74));
        for (GridPoint2 location : locations) {
            Entity fireBuff = AuraFactory.createPoisonBuff();
            auraOnMap.add(fireBuff);
            spawnEntityAt(fireBuff, location, true, false);
        }
    }

    /**
     * Spawns the crafting table entity on the underground map
     */
    public void spawnCraftingTable() {
        Entity craftingTable1 = ObstacleFactory.createCraftingTableUnderground();
        craftingTable1.setEntityType(EntityTypes.CRAFTINGTABLE);
        spawnEntityAt(craftingTable1, new GridPoint2(36, 15), true, false);
        Entity craftingTable2 = ObstacleFactory.createCraftingTableUnderground();
        craftingTable2.setEntityType(EntityTypes.CRAFTINGTABLE);
        spawnEntityAt(craftingTable2, new GridPoint2(11, 69), true, false);
        Entity craftingTable3 = ObstacleFactory.createCraftingTableUnderground();
        craftingTable3.setEntityType(EntityTypes.CRAFTINGTABLE);
        spawnEntityAt(craftingTable3, new GridPoint2(49, 81), true, false);
        Entity craftingTable4 = ObstacleFactory.createCraftingTableUnderground();
        craftingTable4.setEntityType(EntityTypes.CRAFTINGTABLE);
        spawnEntityAt(craftingTable4, new GridPoint2(90, 45), true, false);
    }

    /**
     * Spawn Mega Poop in a random position.
     */
    private Entity spawnMegaPoop() {
        GridPoint2 position = new GridPoint2(35, 102);
        Entity megaPoop = NPCFactory.createMegaPoop(player);
        spawnEntityAt(megaPoop, position, true, true);
        return megaPoop;
    }

    /**
     * Spawn poops in random positions.
     */
    private void spawnPoops() {
        ArrayList<GridPoint2> positions = new ArrayList<>();
        positions.add(new GridPoint2(37, 47));
        positions.add(new GridPoint2(32, 47));
        positions.add(new GridPoint2(42, 42));

        positions.add(new GridPoint2(32, 67));
        positions.add(new GridPoint2(45, 74));
        positions.add(new GridPoint2(35, 69));

        positions.add(new GridPoint2(9, 88));
        positions.add(new GridPoint2(20, 90));
        positions.add(new GridPoint2(20, 80));

        positions.add(new GridPoint2(65, 47));
        positions.add(new GridPoint2(60, 47));
        positions.add(new GridPoint2(63, 54));

        positions.add(new GridPoint2(85, 54));
        positions.add(new GridPoint2(80, 48));
        positions.add(new GridPoint2(84, 60));

        for (GridPoint2 position : positions) {
            Entity poops = NPCFactory.createPoops(player);
            areaEntities.add(poops);
            spawnEntityAt(poops, position, true, true);
        }
    }

    /**
     * Spawn guard NPC in random position. - Team 7 all-mid-npc
     */
//    private void spawnGuard() {
//        GuardPosition = new GridPoint2(10, 8);
//        GuardDialoguePosition = new GridPoint2(10, 9);
//
//        Entity guard = NPCFactory.createGuard(player);
//        spawnEntityAt(guard, GuardPosition, true, true);
//        areaEntities.add(guard);
//
//        Entity dialogue = DialogueFactory.createDialogue();
//        spawnEntityAt(dialogue, GuardDialoguePosition, true, true);
//        areaEntities.add(dialogue);
//    }
//    public static GridPoint2 getGuardPosition() {
//        return GuardPosition;
//    }

    /**
     * Spawn friendly creature NPC in random position. - Team 7 all-mid-npc
     */

//    private void spawnfriendlycreature() {
//        friendlycreaturePosition = new GridPoint2(5, 10);
//        friendlycreatureDialoguePosition = new GridPoint2(5, 11);
//
//        Entity friendlycreature = NPCFactory.createFriendlyCreature(player);
//        spawnEntityAt(friendlycreature, friendlycreaturePosition, true, true);
//        areaEntities.add(friendlycreature);
//
//        Entity dialogue = DialogueFactory.createDialogue();
//        spawnEntityAt(dialogue, friendlycreaturePosition, true, true);
//        areaEntities.add(dialogue);
//    }


    private GridPoint2 randomPositon() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);
        return RandomUtils.random(minPos, maxPos);
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
        resourceService.loadTextures(undergroundTextures);
        resourceService.loadTextureAtlases(undergroundTextureAtlases);
        resourceService.loadSounds(undergroundSounds);
        resourceService.loadMusic(undergroundMusic);

        while (!resourceService.loadForMillis(10)) {
            // This could be upgraded to a loading screen
            logger.info("Loading... {}%", resourceService.getProgress());
        }
    }

    /**
     * Spawns a projectile at the player entity's coordinates.
     */
    public void spawnWeaponProjectile() { //TEAM 04
        Entity newProjectile = ProjectileFactory.createWeaponProjectile(player, 0);
        spawnEntityAt(newProjectile,
                new GridPoint2((int) player.getCenterPosition().x, (int) player.getCenterPosition().y),
                true, true);
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(undergroundTextures);
        resourceService.unloadAssets(undergroundTextureAtlases);
        resourceService.unloadAssets(undergroundSounds);
        resourceService.unloadAssets(undergroundMusic);
    }

    @Override
    public void dispose() {
        super.dispose();
        ServiceLocator.getResourceService().getAsset(backgroundMusic, Music.class).stop();
        this.unloadAssets();
    }

    /**
     * toString returning a string of the classes name
     *
     * @return (String) class name
     */
    @Override
    public String toString() {
        return "UndergroundGameArea";
    }

    public static void removeAuraOnMap(Entity entityToRemove) {

        entityToRemove.setEnabled(false);
        auraOnMap.remove(entityToRemove);

        Gdx.app.postRunnable(() -> entityToRemove.dispose());
        if (entityToRemove.getComponent(AnimationRenderComponent.class) != null) {
            Gdx.app.postRunnable(() -> entityToRemove.getComponent(AnimationRenderComponent.class).stopAnimation());
        }
    }

    public static void removeItemOnMap(Entity entityToRemove) {

        entityToRemove.setEnabled(false);
        ItemsOnMap.remove(entityToRemove);
        Gdx.app.postRunnable(() -> entityToRemove.dispose());
    }
}
