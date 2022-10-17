package com.deco2800.game.components.npc;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class NPCAnimationControllerTest {
    @BeforeEach
    void beforeEach() {
        // Mock rendering, physics, game time
        RenderService renderService = new RenderService();
        renderService.setDebug(mock(DebugRenderer.class));
        ServiceLocator.registerRenderService(renderService);
        GameTime gameTime = mock(GameTime.class);
        when(gameTime.getDeltaTime()).thenReturn(20f / 1000);
        ServiceLocator.registerTimeSource(gameTime);
        ServiceLocator.registerPhysicsService(new PhysicsService());
        EntityService entityService = new EntityService();
        ServiceLocator.registerEntityService(entityService);
        ResourceService resourceService = new ResourceService();
        ServiceLocator.registerResourceService(resourceService);
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
        TextureAtlas atlas = createMockAtlas("MaleShake", 1);
        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("MaleShake", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        NPCAnimationController npcAnimationController = new NPCAnimationController();
        npcAnimationController.setEntity(entity);
        npcAnimationController.create();

        assertEquals(1, entity.getEvents().getNumberOfListeners("MaleShake"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("femaleShake"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("childShake"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("guardShake"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("humanguardShake"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("plumberfriendShake"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("creatureShake"));

    }

    @Test
    void MaleShakeStart() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("MaleShake", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("MaleShake", 1f, Animation.PlayMode.LOOP);animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        NPCAnimationController npcAnimationController = new NPCAnimationController();
        npcAnimationController.setEntity(entity);
        npcAnimationController.create();

        entity.getEvents().trigger("MaleShake");
        assertEquals("MaleShake", animationRenderComponent.getCurrentAnimation());

    }
    @Test
    void femaleShakeStart() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("femaleShake", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("femaleShake", 1f, Animation.PlayMode.LOOP);animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        NPCAnimationController npcAnimationController = new NPCAnimationController();
        npcAnimationController.setEntity(entity);
        npcAnimationController.create();

        entity.getEvents().trigger("femaleShake");
        assertEquals("femaleShake", animationRenderComponent.getCurrentAnimation());

    }

    @Test
    void childShakeStart() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("childShake", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("childShake", 1f, Animation.PlayMode.LOOP);animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        NPCAnimationController npcAnimationController = new NPCAnimationController();
        npcAnimationController.setEntity(entity);
        npcAnimationController.create();

        entity.getEvents().trigger("childShake");
        assertEquals("childShake", animationRenderComponent.getCurrentAnimation());

    }

    @Test
    void guardShakeStart() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("guardShake", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("guardShake", 1f, Animation.PlayMode.LOOP);animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        NPCAnimationController npcAnimationController = new NPCAnimationController();
        npcAnimationController.setEntity(entity);
        npcAnimationController.create();

        entity.getEvents().trigger("guardShake");
        assertEquals("guardShake", animationRenderComponent.getCurrentAnimation());

    }
}
