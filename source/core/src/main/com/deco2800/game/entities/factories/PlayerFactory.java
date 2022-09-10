package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.player.*;
import com.deco2800.game.components.player.PlayerTouchAttackComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.PlayerConfig;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;

/**
 * Factory to create a player entity.
 *
 * <p>Predefined player properties are loaded from a config stored as a json file and should have
 * the properties stores in 'PlayerConfig'.
 */
public class PlayerFactory {
  private static final PlayerConfig stats =
      FileLoader.readClass(PlayerConfig.class, "configs/player.json");


  /**
   * Create a player entity.
   * @return entity
   */
  public static Entity createPlayer() {
    InputComponent inputComponent =
        ServiceLocator.getInputService().getInputFactory().createForPlayer();

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService()
                            .getAsset("images/Movement/movement.atlas", TextureAtlas.class));
    animator.addAnimation("idle", 0.2f, Animation.PlayMode.LOOP);
    animator.addAnimation("up", 0.2f, Animation.PlayMode.LOOP);
    animator.addAnimation("down", 0.2f, Animation.PlayMode.LOOP);
    animator.addAnimation("left", 0.2f, Animation.PlayMode.LOOP);
    animator.addAnimation("right", 0.2f, Animation.PlayMode.LOOP);

    Entity player =
        new Entity()
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent())
            .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
            .addComponent(new CombatStatsComponent(stats.health, stats.baseAttack, stats.stamina, stats.mana))
            .addComponent(new PlayerActions())
            .addComponent(new InventoryComponent())
            .addComponent(new PlayerModifier())
            .addComponent(inputComponent)
            .addComponent(new PlayerStatsDisplay())
            .addComponent(new OpenCraftingComponent())
            .addComponent(new PlayerTouchAttackComponent(PhysicsLayer.PLAYER)) //team4
            .addComponent(animator)
            .addComponent(new PlayerAnimationController());

    PhysicsUtils.setScaledCollider(player, 0.6f, 0.3f);
    player.getComponent(ColliderComponent.class).setDensity(1.5f);
    player.getComponent(AnimationRenderComponent.class).scaleEntity();
    return player;
  }

  public static Entity createSkillAnimator(Entity playerEntity) {
    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService().getAsset("images/Skills/skillAnimations.atlas", TextureAtlas.class));
    animator.addAnimation("no_animation", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("teleport", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("block", 0.1f, Animation.PlayMode.LOOP);

    Entity skillAnimator =
            new Entity().addComponent(animator)
                    .addComponent(new PlayerSkillAnimationController(playerEntity));

    skillAnimator.getComponent(AnimationRenderComponent.class).scaleEntity();
    return skillAnimator;
  }


  private PlayerFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}