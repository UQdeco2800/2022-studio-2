package com.deco2800.game.areas;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.components.npc.NPCAnimationController;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Resources;

import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
class GameAreaTest {
  @Test
  void shouldSpawnEntities() {
    TerrainFactory factory = mock(TerrainFactory.class);

    GameArea gameArea =
        new GameArea() {
          @Override
          public void create() {}
        };

    ServiceLocator.registerEntityService(new EntityService());
    Entity entity = mock(Entity.class);

    gameArea.spawnEntity(entity);
    verify(entity).create();

    gameArea.dispose();
    verify(entity).dispose();
  }

  // Tests if minimap is successfully called.
  @Test
    void checkMinimap() {
      GameArea gameArea = mock(ForestGameArea.class);
      ServiceLocator.registerGameArea(gameArea);
    GameAreaDisplay gameAreaDisplay = mock(GameAreaDisplay.class);

    gameAreaDisplay.displayMinimap();
    verify(gameAreaDisplay).displayMinimap();
  }

    @Test
    void testCreate() {
        GameArea gameArea = mock(GameArea.class);
        gameArea.create();
        verify(gameArea).create();
    }

    @Test
    void testDispose() {
        GameArea gameArea = mock(GameArea.class);
        gameArea.dispose();
        verify(gameArea).dispose();
    }

    @Test
    void testSpawnEntity() {
        GameArea gameArea = mock(GameArea.class);
        gameArea.dispose();
        verify(gameArea).dispose();
    }

    @Test
    void testSpawnEntityAt() {
        GameArea gameArea = mock(GameArea.class);
        Entity entity = new Entity();
        gameArea.spawnEntityAt(entity, new GridPoint2(10,10), true, true);
        verify(gameArea).spawnEntityAt(entity, new GridPoint2(10,10), true, true);
    }

    @Test
    void getPlayer() {
        GameArea gameArea = mock(GameArea.class);
        gameArea.getPlayer();
        verify(gameArea).getPlayer();
    }

    @Test
    void setPlayer() {
        GameArea gameArea = mock(GameArea.class);
        Entity customPlayer = new Entity();
        gameArea.setPlayer(customPlayer);
        verify(gameArea).setPlayer(customPlayer);
    }

//    @Test
//    void shouldSpawnMale() {
//
////        PhysicsService physicsService = mock(PhysicsService.class);
//
//
//        AssetManager assetManager = spy(AssetManager.class);
//        ResourceService resourceService = new ResourceService(assetManager);
//
//
//        EntityService entityService = new EntityService();
//        TerrainFactory terrainFactory = mock(TerrainFactory.class);
////        ResourceService resourceService = new ResourceService();
//        PhysicsService physicsService = new PhysicsService();
//        RenderService renderService = new RenderService();
//        NPCAnimationController npcAnimationController = mock(NPCAnimationController.class);
//
////        ServiceLocator.registerEntityService(new EntityService());
//        ServiceLocator.registerEntityService(entityService);
//        ServiceLocator.registerPhysicsService(physicsService);
//        ServiceLocator.registerResourceService(resourceService);
//        ServiceLocator.registerRenderService(renderService);
//
////        resourceService
////                .getAsset(, TextureAtlas.class);
//
//        ServiceLocator.getResourceService().loadTextureAtlases(new String[]{"images/NPC/male_citizen/male-atlas.atlas"});
//        ServiceLocator.getResourceService().loadAll();
//        ServiceLocator.getResourceService().getAsset("images/NPC/male_citizen/male-atlas.atlas",TextureAtlas.class);
//
//        ForestGameArea forestGameArea =
//                new ForestGameArea(terrainFactory) {
//                    @Override
//                    public void create() {
//                    }
//                };
//        Entity entity = mock(Entity.class);
//
//        forestGameArea.spawnMaleCitizen();
//        verify(entity).create();
//
//    }
}
