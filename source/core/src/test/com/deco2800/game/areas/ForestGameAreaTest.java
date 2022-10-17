package com.deco2800.game.areas;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.rendering.DebugRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ExtendWith(GameExtension.class)

class ForestGameAreaTest {
    @Mock
    ShapeRenderer shapeRenderer;
    @Mock
    Box2DDebugRenderer physicsRenderer;
    @Mock
    Matrix4 projMatrix;

    DebugRenderer debugRenderer;

    @BeforeEach
    void beforeEach() {
        debugRenderer = new DebugRenderer(physicsRenderer, shapeRenderer);
    }


    @Test
    void spawnPlayer() {
        ForestGameArea forestGameArea = mock(ForestGameArea.class);
        forestGameArea.spawnPlayer();
        verify(forestGameArea).spawnPlayer();
    }

    @Test
    void testSpawnTerrain() {
        ForestGameArea forestGameArea = mock(ForestGameArea.class);
        forestGameArea.getPlayer();
        verify(forestGameArea).getPlayer();
    }

    @Test
    void testGetPlayer() {
        ForestGameArea forestGameArea = mock(ForestGameArea.class);
        forestGameArea.getPlayer();
        verify(forestGameArea).getPlayer();
    }

    @Test
    void testCreate() {
        ForestGameArea forestGameArea = mock(ForestGameArea.class);
        forestGameArea.create();
        verify(forestGameArea).create();
    }

    @Test
    void spawnEntityOnMap() {
        ForestGameArea forestGameArea = mock(ForestGameArea.class);
        Entity entity = new Entity();
        forestGameArea.spawnEntityOnMap(entity, new GridPoint2(10,10), true, true);
        verify(forestGameArea).spawnEntityOnMap(entity, new GridPoint2(10,10), true, true);
    }

    @Test
    void testDispose() {
        ForestGameArea forestGameArea = mock(ForestGameArea.class);
        forestGameArea.dispose();
        verify(forestGameArea).dispose();
    }
}