package com.deco2800.game.components.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.deco2800.game.components.player.PlayerAnimationController;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@ExtendWith(GameExtension.class)
//@ExtendWith(MockitoExtension.class)
//public class PlayerAnimationControllerTest {
//
//    // maybe have a before each that creates an entity with the
//    // animation, animation controller, and the inputcomponent
//
////    Entity dummy = new Entity();
////    PlayerAnimationController controller = new PlayerAnimationController();
////    @BeforeEach
////    void init () {
////
//////        ServiceLocator.registerResourceService();
////        ResourceService resourceService = mock(ResourceService.class);
////
////        String textures[] = {"images/Movement/movement.png"};
////        String atlases[] = {"images/Movement/movement.atlas"};
////
////        resourceService.loadTextures(textures);
////        resourceService.loadTextureAtlases(atlases);
////
//////        TextureAtlas atlas = createMockAtlas("images/Movement/movement.atlas", 5);
//////        AnimationRenderComponent animator = new AnimationRenderComponent("images/Movement/movement.atlas");
////
////        AnimationRenderComponent animator =
////                new AnimationRenderComponent(
////                        ServiceLocator.getResourceService()
////                                .getAsset("images/Movement/movement.atlas", TextureAtlas.class));
////
////        animator.addAnimation("idle", 0.2f, Animation.PlayMode.LOOP);
////        animator.addAnimation("up", 0.2f, Animation.PlayMode.LOOP);
////        animator.addAnimation("down", 0.2f, Animation.PlayMode.LOOP);
////        animator.addAnimation("left", 0.2f, Animation.PlayMode.LOOP);
////        animator.addAnimation("right", 0.2f, Animation.PlayMode.LOOP);
////
////        dummy.addComponent(animator).addComponent(controller);
////    }
////
//////    @Test
//////    void tester () {
//////        //assertTrue(dummy.getComponent(AnimationRenderComponent.class).removeAnimation("idle"));
//////    }
////
////    static TextureAtlas createMockAtlas(String animationName, int numRegions) {
////        TextureAtlas atlas = mock(TextureAtlas.class);
////        Array<TextureAtlas.AtlasRegion> regions = new Array<>(numRegions);
////        for (int i = 0; i < numRegions; i++) {
////            regions.add(mock(TextureAtlas.AtlasRegion.class));
////        }
////        when(atlas.findRegions(animationName)).thenReturn(regions);
////        return atlas;
////    }
//}
