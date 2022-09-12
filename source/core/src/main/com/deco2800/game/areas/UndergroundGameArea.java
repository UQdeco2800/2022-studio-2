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


/** Underground area for the demo game with trees, a player, and some enemies. */
public class UndergroundGameArea extends GameArea {
    private static final Logger logger = LoggerFactory.getLogger(UndergroundGameArea.class);
    private static final GridPoint2 PLAYER_SPAWN = new GridPoint2(10, 10);
    private static final float WALL_WIDTH = 0.1f;
    private static final String[] undergroundTextures = {
            "images/box_boy_leaf.png",
            "images/Enemies/gym_bro.png",
            "images/ghost_king.png",
            "images/NPC/friendly_creature npc/Friendly_creature.png",
            "images/ghost_1.png",
            "images/grass_1.png",
            "images/grass_2.png",
            "images/grass_3.png",
            "images/level_2_tiledmap/32x32/grass.png",
            "images/level_2_tiledmap/32x32/purple_cobble.png",
            "images/Crafting-assets-sprint1/crafting table/craftingTable.png",
    };

    public static String[] newTextures;
    private static final String[] undergroundTextureAtlases = {
            "images/terrain_iso_grass.atlas", "images/playerTeleport.atlas",
            "images/Skills/skillAnimations.atlas", "images/Enemies/gym_bro.atlas",
            "images/NPC/friendly_creature npc/Friendly_creaturesheet.atlas"
    };
    private static final String[] undergroundSounds = {"sounds/Impact4.ogg"};
    private static final String backgroundMusic = "sounds/BGM_03_mp3.mp3";
    private static final String[] undergroundMusic = {backgroundMusic};

    private final TerrainFactory terrainFactory;

    private Entity player;
    private static GridPoint2 craftingTablePos;


    public UndergroundGameArea(TerrainFactory terrainFactory) {
        super();
        this.terrainFactory = terrainFactory;
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
        spawnCraftingTable();
        player = spawnPlayer();
        playMusic();
    }



    private void displayUI() {
        Entity ui = new Entity();
        ui.addComponent(new GameAreaDisplay("Box Underground"));
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
    }

    private void spawnTrees(int x, int y) {
        Entity tree = ObstacleFactory.createTree();
        spawnEntityAt(tree, new GridPoint2(x, y), true, false);
    }

    public void spawnEntityOnMap(Entity entity,GridPoint2 position, Boolean centreX, Boolean centreY) {
        spawnEntityAt(entity, position, centreX, centreY);
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

    public void spawnCraftingTable() {
        GridPoint2 minPos = new GridPoint2(2, 2);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(4, 4);


        GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
        craftingTablePos = randomPos;
        Entity craftingTable = ObstacleFactory.createCraftingTable();
        spawnEntityAt(craftingTable, randomPos, true, false);
    }

    public static GridPoint2 getCraftingTablePos() {
        return craftingTablePos;
    }

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
}

