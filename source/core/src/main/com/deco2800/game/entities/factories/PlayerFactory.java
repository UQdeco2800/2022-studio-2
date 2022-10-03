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
    player.getComponent(InventoryComponent.class).addItem(WeaponFactory.createPlunger());
    player.getComponent(InventoryComponent.class).addItem(WeaponFactory.createSwordLvl2());
    player.getComponent(InventoryComponent.class).addItem(WeaponFactory.createDagger());
    player.getComponent(InventoryComponent.class).addItem(WeaponFactory.createHera());
    player.getComponent(InventoryComponent.class).addItem(WeaponFactory.createHeraAthenaDag());
    player.getComponent(InventoryComponent.class).addItem(WeaponFactory.createPlungerBow());
    player.getComponent(InventoryComponent.class).addItem(WeaponFactory.createGoldenPlungerBow());
    player.getComponent(InventoryComponent.class).addItem(WeaponFactory.createPipe());
    player.getComponent(InventoryComponent.class).addItem(WeaponFactory.createTridentLvl2());
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
                    ServiceLocator.getResourceService().getAsset("images/CombatItems/animations/combatItemsAnimation.atlas", TextureAtlas.class));
    //animator.addAnimation("noAnimation", 0.1f);

    /*athena animations*/
    animator.addAnimation("athena", 0.1f);
    animator.addAnimation("athenaDamage", 0.1f);
    animator.addAnimation("athenaDamageStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("athenaFire", 0.1f);
    animator.addAnimation("athenaFireStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("athenaPoison", 0.1f);
    animator.addAnimation("athenaPoisonStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("athenaSpeed", 0.1f);
    animator.addAnimation("athenaSpeedStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("athenaStatic", 0.1f, Animation.PlayMode.LOOP);

    /*hera animations*/
    animator.addAnimation("hera", 0.1f);
    animator.addAnimation("heraDamage", 0.1f);
    animator.addAnimation("heraDamageStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("heraFire", 0.1f);
    animator.addAnimation("heraFireStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("heraPoison", 0.1f);
    animator.addAnimation("heraPoisonStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("heraSpeed", 0.1f);
    animator.addAnimation("heraSpeedStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("heraStatic", 0.1f);

    /*hera athena animations*/
    animator.addAnimation("heraAthena", 0.1f);
    animator.addAnimation("heraAthenaDamage", 0.1f);
    animator.addAnimation("heraAthenaDamageStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("heraAthenaFire", 0.1f);
    animator.addAnimation("heraAthenaFireStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("heraAthenaPoison", 0.1f);
    animator.addAnimation("heraAthenaPoisonStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("heraAthenaSpeed", 0.1f);
    animator.addAnimation("heraAthenaSpeedStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("heraAthenaStatic", 0.1f, Animation.PlayMode.LOOP);

    /*pipe animations*/
    animator.addAnimation("pipe", 0.1f);
    animator.addAnimation("pipeDamage", 0.1f);
    //animator.addAnimation("pipeDamageStatic", 0.1f);
    animator.addAnimation("pipeFire", 0.1f);
    animator.addAnimation("pipeFireStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("pipePoison", 0.1f);
    animator.addAnimation("pipePoisonStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("pipeSpeed", 0.1f);
    //animator.addAnimation("pipeSpeedStatic", 0.1f);
    animator.addAnimation("pipeStatic", 0.1f);

    /*plunger animations*/
    animator.addAnimation("plunger", 0.1f);
    animator.addAnimation("plungerDamage", 0.1f);
    animator.addAnimation("plungerDamageStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("plungerFire", 0.1f);
    animator.addAnimation("plungerFireStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("plungerPoison", 0.1f);
    animator.addAnimation("plungerPoisonStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("plungerSpeed", 0.1f);
    animator.addAnimation("plungerSpeedStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("plungerStatic", 0.1f);

    /*sword animations*/
    animator.addAnimation("sword", 0.1f);
    animator.addAnimation("swordDamage", 0.1f);
    animator.addAnimation("swordDamageStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("swordFire", 0.1f);
    animator.addAnimation("swordFireStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("swordPoison", 0.1f);
    animator.addAnimation("swordPoisonStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("swordSpeed", 0.1f);
    animator.addAnimation("swordSpeedStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("swordStatic", 0.1f);

    /*trident animations*/
    animator.addAnimation("trident", 0.1f);
    animator.addAnimation("tridentDamage", 0.1f);
    animator.addAnimation("tridentDamageStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("tridentFire", 0.1f);
    animator.addAnimation("tridentFireStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("tridentPoison", 0.1f);
    animator.addAnimation("tridentPoisonStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("tridentSpeed", 0.1f);
    animator.addAnimation("tridentSpeedStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("tridentStatic", 0.1f);

    /*plunger bow animations*/
    animator.addAnimation("plungerBow", 0.1f);
    animator.addAnimation("plungerBowDamage", 0.1f);
    animator.addAnimation("plungerBowDamageStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("plungerBowFire", 0.1f);
    animator.addAnimation("plungerBowFireStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("plungerBowPoison", 0.1f);
    animator.addAnimation("plungerBowPoisonStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("plungerBowSpeed", 0.1f);
    animator.addAnimation("plungerBowSpeedStatic", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("plungerBowStatic", 0.1f);

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