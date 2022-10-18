package com.deco2800.game.components.player;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
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


        animator.addAnimation("athena", 0.1f);

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
    void playAthenaAnimation() {
        entity.getEvents().trigger("athena");
    }

    @Test
    void playAthenaDamageAnimation() {
        animator.addAnimation("athenaDamage", 0.1f);
        entity.getEvents().trigger("athenaDamage");
    }

    @Test
    void playAthenaDamageStaticAnimation() {
        animator.addAnimation("athenaDamageStatic", 0.1f, Animation.PlayMode.LOOP);
        entity.getEvents().trigger("athenaDamageStatic");
    }

    @Test
    void playAthenaFireAnimation() {
        animator.addAnimation("athenaFire", 0.1f);
        entity.getEvents().trigger("athenaFire");
    }

    @Test
    void playAthenaFireStaticAnimation() {
        animator.addAnimation("athenaFireStatic", 0.1f, Animation.PlayMode.LOOP);
        entity.getEvents().trigger("athenaFireStatic");
    }

    @Test
    void playAthenaPoisonAnimation() {
        animator.addAnimation("athenaPoison", 0.1f);
        entity.getEvents().trigger("athenaPoison");
    }

    @Test
    void playAthenaPoisonStaticAnimation() {
        animator.addAnimation("athenaPoisonStatic", 0.1f, Animation.PlayMode.LOOP);
        entity.getEvents().trigger("athenaPoisonStatic");
    }

    @Test
    void playAthenaSpeedAnimation() {
        animator.addAnimation("athenaSpeed", 0.1f);
        entity.getEvents().trigger("athenaSpeed");
    }

    @Test
    void playAthenaSpeedStaticAnimation() {
        animator.addAnimation("athenaSpeedStatic", 0.1f, Animation.PlayMode.LOOP);
        entity.getEvents().trigger("athenaSpeedStatic");
    }

    @Test
    void playAthenaStaticAnimation() {
        animator.addAnimation("athenaStatic", 0.1f, Animation.PlayMode.LOOP);
        entity.getEvents().trigger("athenaStatic");
    }

    @Test
    void playHeraAnimation() {
        animator.addAnimation("hera", 0.1f);
        entity.getEvents().trigger("hera");
    }

    @Test
    void playHeraDamageAnimation() {
        animator.addAnimation("heraDamage", 0.1f);
        entity.getEvents().trigger("heraDamage");
    }

    @Test
    void playHeraDamageStaticAnimation() {
        animator.addAnimation("heraDamageStatic", 0.1f);
        entity.getEvents().trigger("heraDamageStatic");
    }

    @Test
    void playHeraFireAnimation() {
        animator.addAnimation("heraFire", 0.1f);
        entity.getEvents().trigger("heraFire");
    }

    @Test
    void playHeraFireStaticAnimation() {
        animator.addAnimation("heraFireStatic", 0.1f);
        entity.getEvents().trigger("heraFireStatic");
    }

    @Test
    void playHeraPoisonAnimation() {
        animator.addAnimation("heraPoison", 0.1f);
        entity.getEvents().trigger("heraPoison");
    }

    @Test
    void playHeraPoisonStaticAnimation() {
        animator.addAnimation("heraPoisonStatic", 0.1f);
        entity.getEvents().trigger("heraPoisonStatic");
    }

    @Test
    void playHeraSpeedAnimation() {
        animator.addAnimation("heraSpeed", 0.1f);
        entity.getEvents().trigger("heraSpeed");
    }

    @Test
    void playHeraSpeedStaticAnimation() {
        animator.addAnimation("heraSpeedStatic", 0.1f);
        entity.getEvents().trigger("heraSpeedStatic");
    }

    @Test
    void playHeraStaticAnimation() {
        animator.addAnimation("heraStatic", 0.1f);
        entity.getEvents().trigger("heraStatic");
    }

    @Test
    void playHeraAthenaAnimation() {
        animator.addAnimation("heraAthena", 0.1f);
        entity.getEvents().trigger("heraAthena");
    }

    @Test
    void playHeraAthenaDamageAnimation() {
        animator.addAnimation("heraAthenaDamage", 0.1f);
        entity.getEvents().trigger("heraAthenaDamage");
    }

    @Test
    void playHeraAthenaDamageStaticAnimation() {
        animator.addAnimation("heraAthenaDamageStatic", 0.1f);
        entity.getEvents().trigger("heraAthenaDamageStatic");
    }

    @Test
    void playHeraAthenaFireAnimation() {
        animator.addAnimation("heraAthenaFire", 0.1f);
        entity.getEvents().trigger("heraAthenaFire");
    }

    @Test
    void playHeraAthenaFireStaticAnimation() {
        animator.addAnimation("heraAthenaFireStatic", 0.1f);
        entity.getEvents().trigger("heraAthenaFireStatic");
    }

    @Test
    void playHeraAthenaPoisonAnimation() {
        animator.addAnimation("heraAthenaPoison", 0.1f);
        entity.getEvents().trigger("heraAthenaPoison");
    }

    @Test
    void playHeraAthenaPoisonStaticAnimation() {
        animator.addAnimation("heraAthenaPoisonStatic", 0.1f);
        entity.getEvents().trigger("heraAthenaPoisonStatic");
    }

    @Test
    void playHeraAthenaSpeedAnimation() {
        animator.addAnimation("heraAthenaSpeed", 0.1f);
        entity.getEvents().trigger("heraAthenaSpeed");
    }

    @Test
    void playHeraAthenaSpeedStaticAnimation() {
        animator.addAnimation("heraAthenaSpeedStatic", 0.1f);
        entity.getEvents().trigger("heraAthenaSpeedStatic");
    }

    @Test
    void playHeraAthenaStaticAnimation() {
        animator.addAnimation("heraAthenaStatic", 0.1f);
        entity.getEvents().trigger("heraAthenaStatic");
    }

    @Test
    void playPipeAnimation() {
        animator.addAnimation("pipe", 0.1f);
        entity.getEvents().trigger("pipe");
    }

    @Test
    void playPipeDamageAnimation() {
        animator.addAnimation("pipeDamage", 0.1f);
        entity.getEvents().trigger("pipeDamage");
    }

    @Test
    void playPipeDamageStaticAnimation() {
        animator.addAnimation("pipeDamageStatic", 0.1f);
        entity.getEvents().trigger("pipeDamageStatic");
    }

    @Test
    void playPipeFireAnimation() {
        animator.addAnimation("pipeFire", 0.1f);
        entity.getEvents().trigger("pipeFire");
    }

    @Test
    void playPipeFireStaticAnimation() {
        animator.addAnimation("pipeFireStatic", 0.1f);
        entity.getEvents().trigger("pipeFireStatic");
    }

    @Test
    void playPipePoisonAnimation() {
        animator.addAnimation("pipePoison", 0.1f);
        entity.getEvents().trigger("pipePoison");
    }

    @Test
    void playPipePoisonStaticAnimation() {
        animator.addAnimation("pipePoisonStatic", 0.1f);
        entity.getEvents().trigger("pipePoisonStatic");
    }

    @Test
    void playPipeSpeedAnimation() {
        animator.addAnimation("pipeSpeed", 0.1f);
        entity.getEvents().trigger("pipeSpeed");
    }

    @Test
    void playPipeSpeedStaticAnimation() {
        animator.addAnimation("pipeSpeedStatic", 0.1f);
        entity.getEvents().trigger("pipeSpeedStatic");
    }

    @Test
    void playPipeStaticAnimation() {
        animator.addAnimation("pipeStatic", 0.1f);
        entity.getEvents().trigger("pipeStatic");
    }

    @Test
    void playPlungerAnimation() {
        animator.addAnimation("plunger", 0.1f);
        entity.getEvents().trigger("plunger");
    }

    @Test
    void playPlungerDamageAnimation() {
        animator.addAnimation("plungerDamage", 0.1f);
        entity.getEvents().trigger("plungerDamage");
    }

    @Test
    void playPlungerDamageStaticAnimation() {
        animator.addAnimation("plungerDamageStatic", 0.1f);
        entity.getEvents().trigger("plungerDamageStatic");
    }

    @Test
    void playPlungerFireAnimation() {
        animator.addAnimation("plungerFire", 0.1f);
        entity.getEvents().trigger("plungerFire");
    }

    @Test
    void playPlungerFireStaticAnimation() {
        animator.addAnimation("plungerFireStatic", 0.1f);
        entity.getEvents().trigger("plungerFireStatic");
    }

    @Test
    void playPlungerPoisonAnimation() {
        animator.addAnimation("plungerPoison", 0.1f);
        entity.getEvents().trigger("plungerPoison");
    }

    @Test
    void playPlungerPoisonStaticAnimation() {
        animator.addAnimation("plungerPoisonStatic", 0.1f);
        entity.getEvents().trigger("plungerPoisonStatic");
    }

    @Test
    void playPlungerSpeedAnimation() {
        animator.addAnimation("plungerSpeed", 0.1f);
        entity.getEvents().trigger("plungerSpeed");
    }

    @Test
    void playPlungerSpeedStaticAnimation() {
        animator.addAnimation("plungerSpeedStatic", 0.1f);
        entity.getEvents().trigger("plungerSpeedStatic");
    }

    @Test
    void playPlungerStaticAnimation() {
        animator.addAnimation("plungerStatic", 0.1f);
        entity.getEvents().trigger("plungerStatic");
    }

    @Test
    void playSwordAnimation() {
        animator.addAnimation("sword", 0.1f);
        entity.getEvents().trigger("sword");
    }

    @Test
    void playSwordDamageAnimation() {
        animator.addAnimation("swordDamage", 0.1f);
        entity.getEvents().trigger("swordDamage");
    }

    @Test
    void playSwordDamageStaticAnimation() {
        animator.addAnimation("swordDamageStatic", 0.1f);
        entity.getEvents().trigger("swordDamageStatic");
    }

    @Test
    void playSwordFireAnimation() {
        animator.addAnimation("swordFire", 0.1f);
        entity.getEvents().trigger("swordFire");
    }

    @Test
    void playSwordFireStaticAnimation() {
        animator.addAnimation("swordFireStatic", 0.1f);
        entity.getEvents().trigger("swordFireStatic");
    }

    @Test
    void playSwordPoisonAnimation() {
        animator.addAnimation("swordPoison", 0.1f);
        entity.getEvents().trigger("swordPoison");
    }

    @Test
    void playSwordPoisonStaticAnimation() {
        animator.addAnimation("swordPoisonStatic", 0.1f);
        entity.getEvents().trigger("swordPoisonStatic");
    }

    @Test
    void playSwordSpeedAnimation() {
        animator.addAnimation("swordSpeed", 0.1f);
        entity.getEvents().trigger("swordSpeed");
    }

    @Test
    void playSwordSpeedStaticAnimation() {
        animator.addAnimation("swordSpeedStatic", 0.1f);
        entity.getEvents().trigger("swordSpeedStatic");
    }

    @Test
    void playSwordStaticAnimation() {
        animator.addAnimation("swordStatic", 0.1f);
        entity.getEvents().trigger("swordStatic");
    }

    @Test
    void playTridentAnimation() {
        animator.addAnimation("trident", 0.1f);
        entity.getEvents().trigger("trident");
    }

    @Test
    void playTridentDamageAnimation() {
        animator.addAnimation("tridentDamage", 0.1f);
        entity.getEvents().trigger("tridentDamage");
    }

    @Test
    void playTridentDamageStaticAnimation() {
        animator.addAnimation("tridentDamageStatic", 0.1f);
        entity.getEvents().trigger("tridentDamageStatic");
    }

    @Test
    void playTridentFireAnimation() {
        animator.addAnimation("tridentFire", 0.1f);
        entity.getEvents().trigger("tridentFire");
    }

    @Test
    void playTridentFireStaticAnimation() {
        animator.addAnimation("tridentFireStatic", 0.1f);
        entity.getEvents().trigger("tridentFireStatic");
    }

    @Test
    void playTridentPoisonAnimation() {
        animator.addAnimation("tridentPoison", 0.1f);
        entity.getEvents().trigger("tridentPoison");
    }

    @Test
    void playTridentPoisonStaticAnimation() {
        animator.addAnimation("tridentPoisonStatic", 0.1f);
        entity.getEvents().trigger("tridentPoisonStatic");
    }

    @Test
    void playTridentSpeedAnimation() {
        animator.addAnimation("tridentSpeed", 0.1f);
        entity.getEvents().trigger("tridentSpeed");
    }

    @Test
    void playTridentSpeedStaticAnimation() {
        animator.addAnimation("tridentSpeedStatic", 0.1f);
        entity.getEvents().trigger("tridentSpeedStatic");
    }

    @Test
    void playTridentStaticAnimation() {
        animator.addAnimation("tridentStatic", 0.1f);
        entity.getEvents().trigger("tridentStatic");
    }

    @Test
    void playPlungerBowAnimation() {
        animator.addAnimation("plungerBow", 0.1f);
        entity.getEvents().trigger("plungerBow");
    }

    @Test
    void playPlungerBowDamageAnimation() {
        animator.addAnimation("plungerBowDamage", 0.1f);
        entity.getEvents().trigger("plungerBowDamage");
    }

    @Test
    void playPlungerBowDamageStaticAnimation() {
        animator.addAnimation("plungerBowDamageStatic", 0.1f);
        entity.getEvents().trigger("plungerBowDamageStatic");
    }

    @Test
    void playPlungerBowFireAnimation() {
        animator.addAnimation("plungerBowFire", 0.1f);
        entity.getEvents().trigger("plungerBowFire");
    }

    @Test
    void playPlungerBowFireStaticAnimation() {
        animator.addAnimation("plungerBowFireStatic", 0.1f);
        entity.getEvents().trigger("plungerBowFireStatic");
    }

    @Test
    void playPlungerBowPoisonAnimation() {
        animator.addAnimation("plungerBowPoison", 0.1f);
        entity.getEvents().trigger("plungerBowPoison");
    }

    @Test
    void playPlungerBowPoisonStaticAnimation() {
        animator.addAnimation("plungerBowPoisonStatic", 0.1f);
        entity.getEvents().trigger("plungerBowPoisonStatic");
    }

    @Test
    void playPlungerBowSpeedAnimation() {
        animator.addAnimation("plungerBowSpeed", 0.1f);
        entity.getEvents().trigger("plungerBowSpeed");
    }

    @Test
    void playPlungerBowSpeedStaticAnimation() {
        animator.addAnimation("plungerBowSpeedStatic", 0.1f);
        entity.getEvents().trigger("plungerBowSpeedStatic");
    }

    @Test
    void playPlungerBowStaticAnimation() {
        animator.addAnimation("plungerBowStatic", 0.1f);
        entity.getEvents().trigger("plungerBowStatic");
    }
    @Test
    void playGoldenPlungerBowStaticAnimation() {
        animator.addAnimation("goldenPlungerBow", 0.1f);
        entity.getEvents().trigger("goldenPlungerBow");
    }

}
