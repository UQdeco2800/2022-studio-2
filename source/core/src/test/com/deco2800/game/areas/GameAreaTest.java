package com.deco2800.game.areas;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.math.GridPoint2;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
}
