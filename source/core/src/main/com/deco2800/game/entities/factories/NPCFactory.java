package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.npc.GhostAnimationController;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.tasks.ChaseTask;
import com.deco2800.game.components.tasks.WanderTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.*;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;

/**
 * Factory to create non-playable character (NPC) entities with predefined components.
 *
 * <p>Each NPC entity type should have a creation method that returns a corresponding entity.
 * Predefined entity properties can be loaded from configs stored as json files which are defined in
 * "NPCConfigs".
 *
 * <p>If needed, this factory can be separated into more specific factories for entities with
 * similar characteristics.
 */
public class NPCFactory {
  private static final NPCConfigs configs =
      FileLoader.readClass(NPCConfigs.class, "configs/NPCs.json");


  /**
   * Creates a ghost entity.
   *
   * @param target entity to chase
   * @return entity
   */
  public static Entity createGhost(Entity target) {
    Entity ghost = createBaseNPC(target);
    BaseEntityConfig config = configs.ghost;

    ghost.getComponent(AITaskComponent.class)
            .addTask(new WanderTask(new Vector2(2f, 2f), 2f))
            .addTask(new ChaseTask(target, 10, 5f, 6f, 100f));

    AnimationRenderComponent animator =
        new AnimationRenderComponent(
            ServiceLocator.getResourceService().getAsset("images/ghost.atlas", TextureAtlas.class));
    animator.addAnimation("angry_float", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("float", 0.1f, Animation.PlayMode.LOOP);

    ghost
        .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
        .addComponent(animator)
        .addComponent(new GhostAnimationController());

    ghost.getComponent(AnimationRenderComponent.class).scaleEntity();

    return ghost;
  }

  /**
   * Creates a ghost king entity.
   *
   * @param target entity to chase
   * @return entity
   */
  public static Entity createGhostKing(Entity target) {
    Entity ghostKing = createBaseNPC(target);
    GhostKingConfig config = configs.ghostKing;

    ghostKing.getComponent(AITaskComponent.class)
            .addTask(new WanderTask(new Vector2(2f, 2f), 2f))
            .addTask(new ChaseTask(target, 10, 5f, 6f, 100f));

    AnimationRenderComponent animator =
        new AnimationRenderComponent(
            ServiceLocator.getResourceService()
                .getAsset("images/ghostKing.atlas", TextureAtlas.class));
    animator.addAnimation("float", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("angry_float", 0.1f, Animation.PlayMode.LOOP);

    ghostKing
        .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
        .addComponent(animator)
        .addComponent(new GhostAnimationController());

    ghostKing.getComponent(AnimationRenderComponent.class).scaleEntity();
    return ghostKing;
  }
  public static Entity createOneLegGirl (Entity target) {
    Entity oneLegGirl = createBaseNPC(target);
    OneLegGirlConfig config = configs.oneLegGirl;

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService()
                            .getAsset("images/ghostKing.atlas", TextureAtlas.class)
            );

    oneLegGirl
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(animator)
            .addComponent(new GhostAnimationController());


    oneLegGirl.getComponent(AnimationRenderComponent.class).scaleEntity();
    return oneLegGirl;
  }
  /**
   * Creates an atlantis citizen entity.
   *
   * @param target entity to chase
   * @return entity
   */
  public static Entity createAtlantisCitizen(Entity target) {
    Entity atlantisCitizen = createBaseNPC(target);
    AtlantisCitizenConfig config = configs.atlantisCitizen;

    atlantisCitizen.getComponent(AITaskComponent.class)
            .addTask(new WanderTask(new Vector2(2f, 2f), 2f))
            .addTask(new ChaseTask(target, 10, 5f, 6f, 120f));

    //Once we have animation, can change from using Texture Component to Animation Component
    atlantisCitizen
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(new TextureRenderComponent("images/atlantis_citizen_gym_bro.png"));
    //atlantisCitizen.getComponent(TextureRenderComponent.class).scaleEntity();
    atlantisCitizen.setScale(2f, 2f);
    return atlantisCitizen;

  }

  /**
   * Creates a generic NPC to be used as a base entity by more specific NPC creation methods.
   *
   * @return entity
   */
  private static Entity createBaseNPC(Entity player) {
    AITaskComponent aiComponent = new AITaskComponent();
    Entity npc =
            new Entity()
                    .addComponent(new PhysicsComponent())
                    .addComponent(new PhysicsMovementComponent())
                    .addComponent(new ColliderComponent())
                    .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                    .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 1.5f))
                    .addComponent(aiComponent);

    PhysicsUtils.setScaledCollider(npc, 0.9f, 0.4f);
    return npc;
  }

  // Creates the plug as an NPC that can be pushed off the plughole by the player
  public static Entity createPlug(Entity target) {
    Entity plug = createBaseNPC(target);
    PlugConfig config = configs.plug;

    /**
    /plug.getComponent(AITaskComponent.class)
             // Create new task for the plug to either move off the plug or not
            .addTask(new WanderTask(new Vector2(2f, 2f), 2f))
            .addTask(new ChaseTask(target, 10, 5f, 6f, 120f));
*/
    plug
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(new TextureRenderComponent("images/level_1_tiledmap/32x32/drain_plug.png"));
    //atlantisCitizen.getComponent(TextureRenderComponent.class).scaleEntity();
    plug.setScale(3f, 3f);
    return plug;
  }

  private NPCFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
