package com.deco2800.game.components.player;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.components.player.PlayerKPAnimationController;
import com.deco2800.game.utils.math.Vector2Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
public class KeyPromptTest {

    Entity dummy;
    AnimationRenderComponent animator;
    PlayerKPAnimationController controller;

    @BeforeEach
    void init () {

        dummy = new Entity();
        controller = new PlayerKPAnimationController(dummy);

        AssetManager assetManager = spy(AssetManager.class);
        ResourceService resourceService = new ResourceService(assetManager);

        String textures[] = {"images/KeyPrompt/KEY_Q_!.png"};
        String atlases[] = {"images/KeyPrompt/KEY_Q_!.atlas"};
        resourceService.loadTextures(textures);
        resourceService.loadTextureAtlases(atlases);
        resourceService.loadAll();

        animator = new AnimationRenderComponent(
                        resourceService
                                .getAsset("images/KeyPrompt/KEY_Q_!.atlas", TextureAtlas.class));

        animator.addAnimation("default", 0.1f, Animation.PlayMode.LOOP);
        animator.addAnimation("Q", 0.1f, Animation.PlayMode.LOOP);

        dummy.addComponent(animator).addComponent(controller);
        dummy.getComponent(PlayerKPAnimationController.class).create();


    }

    @Test
    void hasAnimations () {
        assertTrue(animator.hasAnimation("default"));
        assertTrue(animator.hasAnimation("Q"));

    }




}
