package com.deco2800.game.components.player;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.deco2800.game.components.player.PlayerAnimationController;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;

import com.deco2800.game.utils.math.Vector2Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
public class PlayerAnimationControllerTest {

    Entity dummy;
    AnimationRenderComponent animator;
    PlayerAnimationController controller;
    Vector2 walkDirection;

    @BeforeEach
    void init () {

        dummy = new Entity();
        controller = new PlayerAnimationController();

        AssetManager assetManager = spy(AssetManager.class);
        ResourceService resourceService = new ResourceService(assetManager);
        String textures[] = {"images/Movement/movement.png"};
        String atlases[] = {"images/Movement/movement.atlas"};
        resourceService.loadTextures(textures);
        resourceService.loadTextureAtlases(atlases);
        resourceService.loadAll();

        animator = new AnimationRenderComponent(
                        resourceService
                                .getAsset("images/Movement/movement.atlas", TextureAtlas.class));

        animator.addAnimation("idle", 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation("up", 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation("down", 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation("left", 0.2f, Animation.PlayMode.LOOP);
        animator.addAnimation("right", 0.2f, Animation.PlayMode.LOOP);

        dummy.addComponent(animator).addComponent(controller);
        dummy.getComponent(PlayerAnimationController.class).create();

        walkDirection = Vector2.Zero.cpy();
    }

    @Test
    void hasAnimations () {
        assertTrue(animator.hasAnimation("idle"));
        assertTrue(animator.hasAnimation("up"));
        assertTrue(animator.hasAnimation("down"));
        assertTrue(animator.hasAnimation("left"));
        assertTrue(animator.hasAnimation("right"));
    }

    @Test
    void shouldHaveListener() {
        assertEquals(1, dummy.getEvents().getNumberOfListeners("movementIdle"));
        assertEquals(1, dummy.getEvents().getNumberOfListeners("movementHandle"));
    }

    @Test
    void shouldStartIdle() {
        assertEquals(controller.MOVEIDLE, controller.getMovementAnimation());
    }

    @Test
    void shouldReturnIdle() {
        walkDirection.add(Vector2Utils.DOWN);
        dummy.getEvents().trigger("movementHandle", walkDirection);
        walkDirection.add(Vector2Utils.UP);
        dummy.getEvents().trigger("movementIdle");
        assertEquals(controller.MOVEIDLE, controller.getMovementAnimation());
    }

    @Test
    void shouldMoveDown() {
        walkDirection.add(Vector2Utils.DOWN);
        dummy.getEvents().trigger("movementHandle", walkDirection);
        assertEquals(controller.MOVEDOWN, controller.getMovementAnimation());
    }

    @Test
    void shouldMoveUp() {
        walkDirection.add(Vector2Utils.UP);
        dummy.getEvents().trigger("movementHandle", walkDirection);
        assertEquals(controller.MOVEUP, controller.getMovementAnimation());
    }

    @Test
    void shouldMoveRight() {
        walkDirection.add(Vector2Utils.RIGHT);
        dummy.getEvents().trigger("movementHandle", walkDirection);
        assertEquals(controller.MOVERIGHT, controller.getMovementAnimation());
    }

    @Test
    void shouldMoveLeft() {
        walkDirection.add(Vector2Utils.LEFT);
        dummy.getEvents().trigger("movementHandle", walkDirection);
        assertEquals(controller.MOVELEFT, controller.getMovementAnimation());
    }

    @Test
    void shouldMoveRightUp() {
        walkDirection.add(Vector2Utils.RIGHT);
        walkDirection.add(Vector2Utils.UP);
        dummy.getEvents().trigger("movementHandle", walkDirection);
        assertEquals(controller.MOVERIGHTUP, controller.getMovementAnimation());
    }

    @Test
    void shouldMoveRightDown() {
        walkDirection.add(Vector2Utils.RIGHT);
        walkDirection.add(Vector2Utils.DOWN);
        dummy.getEvents().trigger("movementHandle", walkDirection);
        assertEquals(controller.MOVERIGHTDOWN, controller.getMovementAnimation());
    }

    @Test
    void shouldMoveLeftUp() {
        walkDirection.add(Vector2Utils.LEFT);
        walkDirection.add(Vector2Utils.UP);
        dummy.getEvents().trigger("movementHandle", walkDirection);
        assertEquals(controller.MOVELEFTUP, controller.getMovementAnimation());
    }

    @Test
    void shouldMoveLeftDown() {
        walkDirection.add(Vector2Utils.LEFT);
        walkDirection.add(Vector2Utils.DOWN);
        dummy.getEvents().trigger("movementHandle", walkDirection);
        assertEquals(controller.MOVELEFTDOWN, controller.getMovementAnimation());
    }
}
