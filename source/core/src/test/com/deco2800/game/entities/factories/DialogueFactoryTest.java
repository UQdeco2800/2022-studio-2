package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.deco2800.game.components.npc.DialogueAnimationController;
import com.deco2800.game.components.npc.NPCAnimationController;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(GameExtension.class)
class DialogueFactoryTest {

    @Mock
    ShapeRenderer shapeRenderer;
    @Mock
    Box2DDebugRenderer physicsRenderer;
    @Mock
    Matrix4 projMatrix;

    DebugRenderer debugRenderer;

    @BeforeEach
    void setUp() {
        debugRenderer = new DebugRenderer(physicsRenderer, shapeRenderer);
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerRenderService(new RenderService());


    }

    static TextureAtlas createMockAtlas(String animationName, int numRegions) {
        TextureAtlas atlas = mock(TextureAtlas.class);
        Array<TextureAtlas.AtlasRegion> regions = new Array<>(numRegions);
        for (int i = 0; i < numRegions; i++) {
            regions.add(mock(TextureAtlas.AtlasRegion.class));
        }
        when(atlas.findRegions(animationName)).thenReturn(regions);
        return atlas;
    }

    @Test
    void createTest() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("dialogueShake", 1);
        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("dialogueShake", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        DialogueAnimationController dialogueAnimationController = new DialogueAnimationController();
        dialogueAnimationController.setEntity(entity);
        dialogueAnimationController.create();

        assertEquals(1, entity.getEvents().getNumberOfListeners("dialogueShake"));


    }

    @Test
    void createDialogue() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("dialogueShake", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("dialogueShake", 1f, Animation.PlayMode.LOOP);animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        DialogueAnimationController dialogueAnimationController = new DialogueAnimationController();
        dialogueAnimationController.setEntity(entity);
        dialogueAnimationController.create();

        entity.getEvents().trigger("dialogueShake");
        assertEquals("dialogueShake", animationRenderComponent.getCurrentAnimation());

    }

}