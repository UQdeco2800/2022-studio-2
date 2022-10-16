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
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.extensions.GameExtension;

import com.deco2800.game.input.InputComponent;
import com.deco2800.game.physics.BodyUserData;

import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.services.ServiceLocator;

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
        ServiceLocator.registerPhysicsService(new PhysicsService());
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
        animator.addAnimation("Npc", 0.1f, Animation.PlayMode.LOOP);
        dummy.addComponent(animator).addComponent(controller);
        dummy.getComponent(PlayerKPAnimationController.class).create();

    }


    @Test
    void hasAnimations () {
        assertTrue(animator.hasAnimation("default"));
        assertTrue(animator.hasAnimation("Q"));
        assertTrue(animator.hasAnimation("Npc"));

    }
    @Test
    void playAnimations(){
       dummy.getEvents().trigger("KeyQAnimation");
       dummy.getEvents().trigger("NpcAnimation");
    }
    @Test
    void testKeyPrompt () {
         short targetLayer = (1 << 3);
         Entity player =createPlayer(targetLayer);
         Entity npc = createNpc(targetLayer);
         Entity craftingTable= createCraftingTable(targetLayer);
         Entity enemy=createEnemy(targetLayer);
         Entity controller = new Entity();
         Fixture playerFixture= player.getComponent(HitboxComponent.class).getFixture();
         Fixture npcFixture= npc.getComponent(HitboxComponent.class).getFixture();
         Fixture craftingTableFixture= craftingTable.getComponent(HitboxComponent.class).getFixture();
         Fixture enemyFixture= enemy.getComponent(HitboxComponent.class).getFixture();
         player.getComponent(PlayerKeyPrompt.class).setKeyPromptAnimator(controller);
         player.getComponent(PlayerKeyPrompt.class).keyPrompt(playerFixture,npcFixture);
         player.getComponent(PlayerKeyPrompt.class).keyPrompt(playerFixture,craftingTableFixture);
         player.getComponent(PlayerKeyPrompt.class).keyPrompt(playerFixture,enemyFixture);
    }

    Entity createPlayer(short targetLayer) {
            Entity entity =
                    new Entity()
                            .addComponent(new PlayerKeyPrompt(targetLayer))
                            .addComponent(new CombatStatsComponent(100, 10, 1, 1))
                            .addComponent(new PhysicsComponent())
                            .addComponent(new HitboxComponent());
            entity.setEntityType(EntityTypes.PLAYER);
            entity.create();
            return entity;
        }
      Entity createEnemy(short targetLayer) {
                 Entity entity =
                         new Entity()
                                 .addComponent(new PlayerKeyPrompt(targetLayer))
                                 .addComponent(new CombatStatsComponent(100, 10, 1, 1))
                                 .addComponent(new PhysicsComponent())
                                 .addComponent(new HitboxComponent());
                 entity.setEntityType(EntityTypes.ENEMY);
                 entity.create();
                 return entity;
             }
     Entity createNpc(short targetLayer) {
                Entity entity =
                        new Entity()
                             .addComponent(new PlayerKeyPrompt(targetLayer))
                             .addComponent(new CombatStatsComponent(100, 10, 1, 1))
                             .addComponent(new PhysicsComponent())
                             .addComponent(new HitboxComponent());
                entity.setEntityType(EntityTypes.NPC);
                entity.create();
                return entity;
            }
     Entity createCraftingTable(short targetLayer){
                Entity entity =
                             new Entity()
                                  .addComponent(new PlayerKeyPrompt(targetLayer))
                                  .addComponent(new PhysicsComponent())
                                  .addComponent(new HitboxComponent());
                entity.setEntityType(EntityTypes.CRAFTINGTABLE);
                entity.create();
                return entity;
     }



}
