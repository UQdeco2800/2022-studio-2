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
public class DialogueAnimationControllerTest {
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
    void dialogueShakeStart() {
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
