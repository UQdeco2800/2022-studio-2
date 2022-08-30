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
    Entity ghost = createBaseNPC();
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
    Entity ghostKing = createBaseNPC();
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

  /**
   * Creates an atlantis female NPC entity.
   *
   * @param target entity to stand
   * @return entity
   */
  public static Entity createOneLegGirl (Entity target) {
    Entity oneLegGirl = createBaseNPC();
    FemaleCitizenConfig config = configs.oneLegGirl;

    oneLegGirl
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(new TextureRenderComponent("images/NPC/female npc/npcfemale_1.png"));




    oneLegGirl.getComponent(AITaskComponent.class);
    oneLegGirl.setScale(1, 1);
    return oneLegGirl;
  }

  /**
   * Creates an atlantis child NPC entity.
   *
   * @param target entity to stand
   * @return entity
   */
  public static Entity createChild (Entity target) {
    Entity child = createBaseNPC();
    ChildConfig config = configs.child;


    child
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(new TextureRenderComponent("images/NPC/child npc/npcchild_1.png"));

    child.getComponent(AITaskComponent.class);
    child.setScale(1, 1);
    return child;
  }

  /**
   * Creates an atlantis guard NPC entity.
   *
   * @param target entity to stand
   * @return entity
   */
  public static Entity createGuard (Entity target) {
    Entity guard = createBaseNPC();
    GuardConfig config = configs.guard;


    guard
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(new TextureRenderComponent("images/NPC/guard npc/atlantisguardnpc_1.png"));

    guard.getComponent(AITaskComponent.class);
    guard.setScale(1, 1);
    return guard;
  }

  /**
   * Creates an atlantis male NPC entity.
   *
   * @param target entity to stand
   * @return entity
   */
  public static Entity createMale_citizen (Entity target) {
    Entity male_citizen = createBaseNPC();
    Male_citizenConfig config = configs.male_citizen;


    male_citizen
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(new TextureRenderComponent("images/NPC/Male_citizen/male_citizen.png"));

    male_citizen.getComponent(AITaskComponent.class);
    male_citizen.setScale(1, 1);
    return male_citizen;
  }

  /**
   * Creates an atlantis citizen entity.
   *
   * @param target entity to chase
   * @return entity
   */
  public static Entity createAtlantisCitizen(Entity target) {
    Entity atlantisCitizen = createBaseNPC();
    AtlantisCitizenConfig config = configs.atlantisCitizen;

    atlantisCitizen.getComponent(AITaskComponent.class)
            .addTask(new WanderTask(new Vector2(2f, 2f), 2f))
            .addTask(new ChaseTask(target, 10, 5f, 6f, config.speed));

    //Once we have animation, can change from using Texture Component to Animation Component
    atlantisCitizen
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(new TextureRenderComponent("images/atlantis_citizen_gym_bro.png"));
    atlantisCitizen.setScale(2f, 2f);
    atlantisCitizen.setEntityType(EntityTypes.ENEMY);
    atlantisCitizen.setEntityType(EntityTypes.MELEE);
    return atlantisCitizen;

  }

  /**
   * Creates a generic NPC to be used as a base entity by more specific NPC creation methods.
   *
   * @return entity
   */
  private static Entity createBaseNPC() {
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

  private NPCFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
