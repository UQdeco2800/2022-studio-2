package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.npc.GymBroAnimationController;
import com.deco2800.game.components.tasks.ChaseTask;
import com.deco2800.game.components.tasks.ProjectileTask;
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
import java.util.*;



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
    oneLegGirl.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
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
    child.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
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
    guard.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
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
            .addComponent(new TextureRenderComponent("images/NPC/male_citizen/male_citizen.png"));

    male_citizen.getComponent(AITaskComponent.class);
    male_citizen.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
    male_citizen.setScale(1, 1);
    return male_citizen;
  }

  /**
   * Creates an atlantis citizen entity.
   *
   * @param target entity to chase
   * @return entity
   */
  public static Entity createGymBro(Entity target) {
    Entity gymBro = createBaseNPC();
    GymBroConfig config = configs.gymBro;

    gymBro.getComponent(AITaskComponent.class)
            .addTask(new WanderTask(new Vector2(2f, 2f), 2f))
            .addTask(new ChaseTask(target, 10, 5f, 6f, config.speed));

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService()
                            .getAsset("images/Enemies/gym_bro.atlas", TextureAtlas.class));
    animator.addAnimation("walk_front", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("walk_back", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("walk_left", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("walk_right", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("attack_front", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("attack_back", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("attack_left", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("attack_right", 0.1f, Animation.PlayMode.LOOP);


    gymBro
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(animator)
            .addComponent(new GymBroAnimationController());
    gymBro.setScale(2f, 2f);
    gymBro.setEntityType(EntityTypes.ENEMY);
    gymBro.setEntityType(EntityTypes.MELEE);
    return gymBro;

  }

  /**
   * Creates Heracles, the boss of the first level.
   *
   * @return entity
   */
  private static Entity createHeracles()  {
    Entity heracles = createBaseNPC();
    HeraclesConfig config = configs.heracles;
    //Will need to add movement/attacks and will need to add texture
    heracles.setEntityType(EntityTypes.ENEMY);
    heracles.setEntityType(EntityTypes.BOSS);
    heracles.setScale(3f, 3f);
    return heracles;
  }

  /**
   * Creates Heracles, the boss of the first level.
   *
   * @return entity
   */
  public static Entity createPoops(Entity target)  {
    Entity poops = createBaseNPC();
    PoopsConfig config = configs.poops;
    List<EntityTypes> types = poops.getEntityTypes();
    poops.getComponent(AITaskComponent.class)
            .addTask(new ProjectileTask(target, types, 10, 5f, 6f,config.speed, 2f))
            .addTask(new WanderTask(new Vector2(2f, 2f), 2f));

    poops
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(new TextureRenderComponent("images/Enemies/poops.png"));
    //Will need to add movement/attacks and will need to add texture
    poops.setEntityType(EntityTypes.ENEMY);
    poops.setEntityType(EntityTypes.RANGED);
    poops.setScale(2f, 2f);
    return poops;
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
            //.addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 1.5f))
            .addComponent(aiComponent);

    PhysicsUtils.setScaledCollider(npc, 0.9f, 0.4f);
    return npc;
  }

  // Creates the plug as an NPC that can be pushed off the plughole by the player
  public static Entity createPlug(Entity target) {
    Entity plug = createBaseNPC();
    PlugConfig config = configs.plug;

    /**
    /plug.getComponent(AITaskComponent.class)
             // Create new task for the plug to move off the hole
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
