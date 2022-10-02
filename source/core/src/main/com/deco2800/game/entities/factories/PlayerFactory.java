package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.deco2800.game.components.CombatItemsComponents.BuffDisplayComponent;
import com.deco2800.game.components.CombatItemsComponents.WeaponAuraManager;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.maingame.PauseMenuActions;
import com.deco2800.game.components.npc.DialogueDisplay;
import com.deco2800.game.components.npc.DialogueKeybordInputComponent;
import com.deco2800.game.components.player.*;
import com.deco2800.game.components.player.PlayerTouchAttackComponent;
import com.deco2800.game.components.player.PlayerKeyPrompt;
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
            .addComponent(new PlayerAnimationController())
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
                //.addComponent(new TransitionMapComponent())
                .addComponent(new DialogueKeybordInputComponent())
                .addComponent(new DialogueDisplay())
            .addComponent(new OpenPauseComponent())
            .addComponent(new PlayerTouchAttackComponent(PhysicsLayer.PLAYER)) //team4
                .addComponent(new WeaponAuraManager())
            .addComponent(animator)
                .addComponent(new BuffDisplayComponent())
            .addComponent(new PlayerKeyPrompt(PhysicsLayer.PLAYER))
            .addComponent(new PlayerAnimationController()).addComponent(new PauseMenuActions());

    PhysicsUtils.setScaledCollider(player, 0.6f, 0.3f);
    player.getComponent(ColliderComponent.class).setDensity(1.5f);
    player.getComponent(AnimationRenderComponent.class).scaleEntity();
    player.setEntityType(EntityTypes.PLAYER);

    //FOR TESTING
    player.getComponent(InventoryComponent.class).addItem(WeaponFactory.createSwordLvl2());
    player.getComponent(InventoryComponent.class).addItem(WeaponFactory.createPlungerBow());
    player.getComponent(InventoryComponent.class).addItem(WeaponFactory.createDagger());
    player.getComponent(InventoryComponent.class).addItem(WeaponFactory.createHeraAthenaDag());
    player.getComponent(InventoryComponent.class).addItem(WeaponFactory.createHera());
    return player;
  }

  /**
   * Create a player entity for test.
   * @return entity
   */
  public static Entity createTestPlayer() {
    Entity player = new Entity()
                    .addComponent(new PhysicsComponent())
                    .addComponent(new ColliderComponent())
                    .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                    .addComponent(new CombatStatsComponent(stats.health, stats.baseAttack, stats.stamina, stats.mana))
                    .addComponent(new PlayerActions())
                    .addComponent(new InventoryComponent())
                    .addComponent(new PlayerModifier())
                    .addComponent(new OpenCraftingComponent())
                    .addComponent(new PlayerTouchAttackComponent(PhysicsLayer.PLAYER))
                    .addComponent(new PlayerKeyPrompt(PhysicsLayer.PLAYER));
    return player;
  }

  public static Entity createSkillAnimator(Entity playerEntity) {
    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService().getAsset("images/Skills/skillAnimations.atlas", TextureAtlas.class));
    animator.addAnimation("no_animation", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("teleport", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("block", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("dodge", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("vendemaire", 0.1f, Animation.PlayMode.NORMAL);
    animator.addAnimation("dash", 0.1f, Animation.PlayMode.NORMAL);
    animator.addAnimation("attackSpeed", 0.1f, Animation.PlayMode.LOOP);

    Entity skillAnimator =
            new Entity().addComponent(animator)
                    .addComponent(new PlayerSkillAnimationController(playerEntity));

    skillAnimator.getComponent(AnimationRenderComponent.class).scaleEntity();
    return skillAnimator;
  }

  public static Entity createKeyPromptAnimator(Entity playerEntity) {
    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService().getAsset("images/KeyPrompt/KEY_Q_!.atlas", TextureAtlas.class));
//    AnimationRenderComponent animator =
//            new AnimationRenderComponent(
//                    ServiceLocator.getResourceService().getAsset("images/Skills/skillAnimations.atlas", TextureAtlas.class));

    animator.addAnimation("default", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("Q", 0.1f, Animation.PlayMode.LOOP);
    Entity keyPromptAnimator =
            new Entity().addComponent(animator)
                    .addComponent(new PlayerKPAnimationController(playerEntity));

    keyPromptAnimator.getComponent(AnimationRenderComponent.class).scaleEntity();
    return keyPromptAnimator;
  }
  /**
   * Create combat item animator
   * @return entity
   */
  public static Entity createCombatAnimator(Entity playerEntity) {
    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService().getAsset("images/CombatItems/animations/combatanimation.atlas", TextureAtlas.class));
    animator.addAnimation("noAnimation", 0.1f);
    animator.addAnimation("heraAthenaDag", 0.1f);
    animator.addAnimation("hera", 0.1f);

    Entity combatAnimator =
            new Entity().addComponent(animator)
                    .addComponent(new PlayerCombatAnimationController(playerEntity));

    combatAnimator.getComponent(AnimationRenderComponent.class).scaleEntity();
    return combatAnimator;
  }

  private PlayerFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}