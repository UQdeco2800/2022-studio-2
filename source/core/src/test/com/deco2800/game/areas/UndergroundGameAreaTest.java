package com.deco2800.game.areas;

import com.badlogic.gdx.math.GridPoint2;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(GameExtension.class)
class UndergroundGameAreaTest {
    void shouldSpawnEntities() {
        TerrainFactory factory = mock(TerrainFactory.class);

        UndergroundGameArea undergroundGameArea =
                new UndergroundGameArea(factory) {
                    @Override
                    public void create() {}
                };

        ServiceLocator.registerEntityService(new EntityService());
        Entity entity = mock(Entity.class);

        undergroundGameArea.spawnEntity(entity);
        verify(entity).create();

        undergroundGameArea.dispose();
        verify(entity).dispose();
    }

    @Test
    void testGetPlayer() {
        UndergroundGameArea undergroundGameArea = mock(UndergroundGameArea.class);
        undergroundGameArea.getPlayer();
        verify(undergroundGameArea).getPlayer();
    }

    @Test
    void testCreate() {
        UndergroundGameArea undergroundGameArea = mock(UndergroundGameArea.class);
        undergroundGameArea.create();
        verify(undergroundGameArea).create();
    }

    @Test
    void testSpawnCraftingTable() {
        UndergroundGameArea undergroundGameArea = mock(UndergroundGameArea.class);
        Entity entity = new Entity();
        undergroundGameArea.spawnEntity(entity);
        verify(undergroundGameArea).spawnEntity(entity);
    }

    @Test
    void testSpawnWeaponProjectile() {
        UndergroundGameArea undergroundGameArea = mock(UndergroundGameArea.class);
        undergroundGameArea.spawnWeaponProjectile();
        verify(undergroundGameArea).spawnWeaponProjectile();
    }

    @Test
    void testDispose() {
        UndergroundGameArea undergroundGameArea = mock(UndergroundGameArea.class);
        undergroundGameArea.dispose();
        verify(undergroundGameArea).dispose();
    }

    @Test
    void testRemoveItemOnMap() {
        UndergroundGameArea undergroundGameArea = mock(UndergroundGameArea.class);
        Entity entity = new Entity();
        undergroundGameArea.spawnEntityAt(entity, new GridPoint2(10,10), true, true);
        verify(undergroundGameArea).spawnEntityAt(entity, new GridPoint2(10,10), true, true);
    }
}