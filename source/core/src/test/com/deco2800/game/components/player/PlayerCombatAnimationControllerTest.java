package com.deco2800.game.components.player;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.services.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
public class PlayerCombatAnimationControllerTest {

    Entity entity;
    AnimationRenderComponent animator;
    PlayerCombatAnimationController controller;

    @BeforeEach
    void beforeEach() {
        short targetLayer = (1 << 3);

        entity = new Entity();
        controller = new PlayerCombatAnimationController(entity);

        AssetManager assetManager = spy(AssetManager.class);
        ResourceService resourceService = new ResourceService(assetManager);
        String textures[] = {"images/CombatItems/animations/combatItemsAnimation.png"};
        String atlases[] = {"images/CombatItems/animations/combatItemsAnimation.atlas"};
        resourceService.loadTextures(textures);
        resourceService.loadTextureAtlases(atlases);
        resourceService.loadAll();

        animator = new AnimationRenderComponent(
                resourceService
                        .getAsset("images/CombatItems/animations/combatItemsAnimation.atlas", TextureAtlas.class));


        animator.addAnimation("athena", 0.2f, Animation.PlayMode.LOOP);

        entity.addComponent(animator).addComponent(controller);
        entity.getComponent(PlayerCombatAnimationController.class).create();
        AnimationRenderComponent animate = mock(AnimationRenderComponent.class);
    }

    @Test
    void hasAnimations() {
        assertTrue(animator.hasAnimation("athena"));
    }

    @Test
    void hasListener() {
        assertEquals(1, entity.getEvents().getNumberOfListeners("athena"));
    }

    @Test
    void playAnimation() {
        entity.getEvents().trigger("athena");
    }


    Entity createPlayer(short targetLayer) {
        Entity entity =
                new Entity()
                        .addComponent(new TouchAttackComponent(targetLayer))
                        .addComponent(new PlayerTouchAttackComponent(targetLayer))
                        .addComponent(new InventoryComponent())
                        .addComponent(new CombatStatsComponent(100, 10, 1, 1))
                        .addComponent(new PhysicsComponent())
                        .addComponent(new HitboxComponent());
        entity.setEntityType(EntityTypes.PLAYER);
        entity.create();
        return entity;
    }

}
