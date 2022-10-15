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

public class MegaPoopAnimationControllerTest {
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
        TextureAtlas atlas = createMockAtlas("walk_front", 1);
        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        MegaPoopAnimationController megaPoopAnimationController = new MegaPoopAnimationController();
        megaPoopAnimationController.setEntity(entity);
        megaPoopAnimationController.create();

        assertEquals(1, entity.getEvents().getNumberOfListeners("vanishFront"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("vanishBack"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("vanishLeft"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("vanishRight"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("projectileAttackFront"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("projectileAttackBack"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("projectileAttackLeft"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("projectileAttackRight"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("walkFront"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("walkBack"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("walkLeft"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("walkRight"));
        assertEquals(1, entity.getEvents().getNumberOfListeners("cast"));
    }

    @Test
    void animateWalkRightTest() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("walk_right", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("walk_right", 1f, Animation.PlayMode.LOOP);
        animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        MegaPoopAnimationController megaPoopAnimationController = new MegaPoopAnimationController();
        megaPoopAnimationController.setEntity(entity);
        megaPoopAnimationController.create();

        entity.getEvents().trigger("walkRight");
        assertEquals("walk_right", animationRenderComponent.getCurrentAnimation());
        entity.getEvents().trigger("walkRight");
        assertEquals("walk_right", animationRenderComponent.getCurrentAnimation());
    }

    @Test
    void animateWalkLeftTest() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("walk_left", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("walk_left", 1f, Animation.PlayMode.LOOP);
        animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        MegaPoopAnimationController megaPoopAnimationController = new MegaPoopAnimationController();
        megaPoopAnimationController.setEntity(entity);
        megaPoopAnimationController.create();

        entity.getEvents().trigger("walkLeft");
        assertEquals("walk_left", animationRenderComponent.getCurrentAnimation());
        entity.getEvents().trigger("walkLeft");
        assertEquals("walk_left", animationRenderComponent.getCurrentAnimation());
    }

    @Test
    void animateWalkBackTest() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("walk_back", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("walk_back", 1f, Animation.PlayMode.LOOP);
        animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        MegaPoopAnimationController megaPoopAnimationController = new MegaPoopAnimationController();
        megaPoopAnimationController.setEntity(entity);
        megaPoopAnimationController.create();

        entity.getEvents().trigger("walkBack");
        assertEquals("walk_back", animationRenderComponent.getCurrentAnimation());
        entity.getEvents().trigger("walkBack");
        assertEquals("walk_back", animationRenderComponent.getCurrentAnimation());
    }

    @Test
    void animateWalkFrontTest() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("walk_front", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        MegaPoopAnimationController megaPoopAnimationController = new MegaPoopAnimationController();
        megaPoopAnimationController.setEntity(entity);
        megaPoopAnimationController.create();

        entity.getEvents().trigger("walkFront");
        assertEquals("walk_front", animationRenderComponent.getCurrentAnimation());
        entity.getEvents().trigger("walkFront");
        assertEquals("walk_front", animationRenderComponent.getCurrentAnimation());
    }

    @Test
    void animateProjectileAttackRightTest() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("projectile_attack_right", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("projectile_attack_right", 1f, Animation.PlayMode.LOOP);
        animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        MegaPoopAnimationController megaPoopAnimationController = new MegaPoopAnimationController();
        megaPoopAnimationController.setEntity(entity);
        megaPoopAnimationController.create();

        entity.getEvents().trigger("projectileAttackRight");
        assertEquals("projectile_attack_right", animationRenderComponent.getCurrentAnimation());
        entity.getEvents().trigger("projectileAttackRight");
        assertEquals("projectile_attack_right", animationRenderComponent.getCurrentAnimation());
    }

    @Test
    void animateProjectileAttackLeftTest() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("projectile_attack_left", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("projectile_attack_left", 1f, Animation.PlayMode.LOOP);
        animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        MegaPoopAnimationController megaPoopAnimationController = new MegaPoopAnimationController();
        megaPoopAnimationController.setEntity(entity);
        megaPoopAnimationController.create();

        entity.getEvents().trigger("projectileAttackLeft");
        assertEquals("projectile_attack_left", animationRenderComponent.getCurrentAnimation());
        entity.getEvents().trigger("projectileAttackLeft");
        assertEquals("projectile_attack_left", animationRenderComponent.getCurrentAnimation());
    }

    @Test
    void animateProjectileAttackBackTest() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("projectile_attack_back", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("projectile_attack_back", 1f, Animation.PlayMode.LOOP);
        animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        MegaPoopAnimationController megaPoopAnimationController = new MegaPoopAnimationController();
        megaPoopAnimationController.setEntity(entity);
        megaPoopAnimationController.create();

        entity.getEvents().trigger("projectileAttackBack");
        assertEquals("projectile_attack_back", animationRenderComponent.getCurrentAnimation());
        entity.getEvents().trigger("projectileAttackBack");
        assertEquals("projectile_attack_back", animationRenderComponent.getCurrentAnimation());
    }

    @Test
    void animateProjectileAttackFrontTest() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("projectile_attack_front", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("projectile_attack_front", 1f, Animation.PlayMode.LOOP);
        animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        MegaPoopAnimationController megaPoopAnimationController = new MegaPoopAnimationController();
        megaPoopAnimationController.setEntity(entity);
        megaPoopAnimationController.create();

        entity.getEvents().trigger("projectileAttackFront");
        assertEquals("projectile_attack_front", animationRenderComponent.getCurrentAnimation());
        entity.getEvents().trigger("projectileAttackFront");
        assertEquals("projectile_attack_front", animationRenderComponent.getCurrentAnimation());
    }

    @Test
    void animateVanishRightTest() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("vanish_right", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("vanish_right", 1f, Animation.PlayMode.LOOP);
        animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        MegaPoopAnimationController megaPoopAnimationController = new MegaPoopAnimationController();
        megaPoopAnimationController.setEntity(entity);
        megaPoopAnimationController.create();

        entity.getEvents().trigger("vanishRight");
        assertEquals("vanish_right", animationRenderComponent.getCurrentAnimation());
        entity.getEvents().trigger("vanishRight");
        assertEquals("vanish_right", animationRenderComponent.getCurrentAnimation());
    }

    @Test
    void animateVanishLeftTest() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("vanish_left", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("vanish_left", 1f, Animation.PlayMode.LOOP);
        animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        MegaPoopAnimationController megaPoopAnimationController = new MegaPoopAnimationController();
        megaPoopAnimationController.setEntity(entity);
        megaPoopAnimationController.create();

        entity.getEvents().trigger("vanishLeft");
        assertEquals("vanish_left", animationRenderComponent.getCurrentAnimation());
        entity.getEvents().trigger("vanishLeft");
        assertEquals("vanish_left", animationRenderComponent.getCurrentAnimation());
    }

    @Test
    void animateVanishBackTest() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("vanish_back", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("vanish_back", 1f, Animation.PlayMode.LOOP);
        animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        MegaPoopAnimationController megaPoopAnimationController = new MegaPoopAnimationController();
        megaPoopAnimationController.setEntity(entity);
        megaPoopAnimationController.create();

        entity.getEvents().trigger("vanishBack");
        assertEquals("vanish_back", animationRenderComponent.getCurrentAnimation());
        entity.getEvents().trigger("vanishBack");
        assertEquals("vanish_back", animationRenderComponent.getCurrentAnimation());
    }

    @Test
    void animateVanishFrontTest() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("vanish_front", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("vanish_front", 1f, Animation.PlayMode.LOOP);
        animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        MegaPoopAnimationController megaPoopAnimationController = new MegaPoopAnimationController();
        megaPoopAnimationController.setEntity(entity);
        megaPoopAnimationController.create();

        entity.getEvents().trigger("vanishFront");
        assertEquals("vanish_front", animationRenderComponent.getCurrentAnimation());
        entity.getEvents().trigger("vanishFront");
        assertEquals("vanish_front", animationRenderComponent.getCurrentAnimation());
    }

    @Test
    void animateCast() {
        Entity entity = new Entity();
        TextureAtlas atlas = createMockAtlas("cast", 1);

        AnimationRenderComponent animationRenderComponent = new AnimationRenderComponent(atlas);
        animationRenderComponent.addAnimation("cast", 1f, Animation.PlayMode.LOOP);
        animationRenderComponent.addAnimation("walk_front", 1f, Animation.PlayMode.LOOP);
        entity.addComponent(animationRenderComponent);

        MegaPoopAnimationController megaPoopAnimationController = new MegaPoopAnimationController();
        megaPoopAnimationController.setEntity(entity);
        megaPoopAnimationController.create();

        entity.getEvents().trigger("cast");
        assertEquals("cast", animationRenderComponent.getCurrentAnimation());
        entity.getEvents().trigger("cast");
        assertEquals("cast", animationRenderComponent.getCurrentAnimation());
    }
}
