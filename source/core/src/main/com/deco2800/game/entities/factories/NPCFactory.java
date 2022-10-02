package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.npc.GymBroAnimationController;
import com.deco2800.game.components.npc.NPCAnimationController;
import com.deco2800.game.components.npc.PoopAnimationController;
import com.deco2800.game.components.npc.HeraclesAnimationController;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.npc.*;
import com.deco2800.game.components.tasks.*;
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

    AnimationRenderComponent animator =
            new AnimationRenderComponent(ServiceLocator.getResourceService().getAsset("images/NPC/female npc/npcfemale.atlas", TextureAtlas.class));
    animator.addAnimation("femaleShake", 0.1f, Animation.PlayMode.LOOP);

    oneLegGirl
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(animator)
            .addComponent(new NPCAnimationController());

    oneLegGirl.getComponent(AITaskComponent.class);
    oneLegGirl.getComponent(AnimationRenderComponent.class).scaleEntity();
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

    AnimationRenderComponent animator =
            new AnimationRenderComponent(ServiceLocator.getResourceService().getAsset("images/NPC/child npc/npcchild.atlas", TextureAtlas.class));
    animator.addAnimation("childShake", 0.1f, Animation.PlayMode.LOOP);


    child
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(animator)
            .addComponent(new NPCAnimationController());


    child.getComponent(AITaskComponent.class);
    child.getComponent(AnimationRenderComponent.class).scaleEntity();
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

    AnimationRenderComponent animator =
            new AnimationRenderComponent(ServiceLocator.getResourceService().getAsset("images/NPC/guard npc/npcguard.atlas", TextureAtlas.class));
    animator.addAnimation("guardShake", 0.1f, Animation.PlayMode.LOOP);


    guard
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(animator)
            .addComponent(new NPCAnimationController());

    guard.getComponent(AITaskComponent.class);
    guard.getComponent(AnimationRenderComponent.class).scaleEntity();
    guard.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
    guard.setScale(1, 1);
    return guard;
  }
  public static Entity createHumanGuard (Entity target) {
    Entity humanguard = createBaseNPC();
    HumanGuardConfig config = configs.humanguard;

    AnimationRenderComponent animator =
            new AnimationRenderComponent(ServiceLocator.getResourceService().getAsset("images/NPC/human_guard/human_guard.atlas", TextureAtlas.class));
    animator.addAnimation("humanguardShake", 0.1f, Animation.PlayMode.LOOP);


    humanguard
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(animator)
            .addComponent(new NPCAnimationController());

    humanguard.getComponent(AITaskComponent.class);
    humanguard.getComponent(AnimationRenderComponent.class).scaleEntity();
    humanguard.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
    humanguard.setScale(1, 1);
    humanguard.setEntityType(EntityTypes.NPC);
    return humanguard;
  }

  public static Entity createPlumberFriend (Entity target) {
    Entity plumberfriend = createBaseNPC();
    PlumberFriendConfig config = configs.plumberfriend;

    AnimationRenderComponent animator =
            new AnimationRenderComponent(ServiceLocator.getResourceService().getAsset("images/NPC/plumber_friend/plumber_friend.atlas", TextureAtlas.class));
    animator.addAnimation("plumberfriendShake", 0.1f, Animation.PlayMode.LOOP);


    plumberfriend
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(animator)
            .addComponent(new NPCAnimationController());

    plumberfriend.getComponent(AITaskComponent.class);
    plumberfriend.getComponent(AnimationRenderComponent.class).scaleEntity();
    plumberfriend.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
    plumberfriend.setScale(1, 1);
    plumberfriend.setEntityType(EntityTypes.NPC);
    return plumberfriend;
  }

  /**
   * Creates a friendly creature NPC entity.
   *
   * @param target entity to stand
   * @return entity
   */

  public static Entity createFriendlyCreature (Entity target) {
    Entity friendlycreature = createBaseNPC();
    FriendlyCreatureConfig config = configs.friendlycreature;

    AnimationRenderComponent animator =
            new AnimationRenderComponent(ServiceLocator.getResourceService().getAsset("images/NPC/friendly_creature npc/friendly_creature.atlas", TextureAtlas.class));
            animator.addAnimation("creatureShake", 0.1f, Animation.PlayMode.LOOP);

    friendlycreature
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(animator)
            .addComponent(new NPCAnimationController());

    friendlycreature.getComponent(AITaskComponent.class);
    friendlycreature.getComponent(AnimationRenderComponent.class).scaleEntity();
    friendlycreature.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
    friendlycreature.setScale(1, 1);
    friendlycreature.setEntityType(EntityTypes.NPC);
    return friendlycreature;

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
    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService()
                            .getAsset("images/NPC/male_citizen/male-atlas.atlas", TextureAtlas.class));
    animator.addAnimation("MaleShake", 0.1f, Animation.PlayMode.LOOP);

    male_citizen
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(animator)
            .addComponent(new NPCAnimationController());
    ;

//images/NPC/male_citizen/male-atlas.atlas

    male_citizen.getComponent(AITaskComponent.class);
    male_citizen.getComponent(AnimationRenderComponent.class).scaleEntity();
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
    GymBroConfig config = new NPCConfigs().gymBro;

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
  public static Entity createHeracles(Entity target)  {
    Entity heracles = createBaseNPC();
    HeraclesConfig config = new NPCConfigs().heracles;
    List<EntityTypes> types = heracles.getEntityTypes();
    String projectileType = "discus";

    heracles.getComponent(AITaskComponent.class)
            .addTask(new WanderTask(new Vector2(2f, 2f), 2f))
            .addTask(new ProjectileTask(target, projectileType, 10, 5f, 6f,config.speed, 2f))
            //.addTask(new ChaseTask(target, 10, 5f, 6f, config.speed));
            .addTask(new JumpTask(target, 11, 8f,180, 1.5f));

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService()
                            .getAsset("images/Enemies/heracles.atlas", TextureAtlas.class));
    animator.addAnimation("walk_front", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("walk_back", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("walk_left", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("walk_right", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("discus_attack_front", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("discus_attack_back", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("discus_attack_left", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("discus_attack_right", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("jump_front", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("jump_back", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("jump_left", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("jump_right", 0.1f, Animation.PlayMode.LOOP);


    heracles
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(animator)
            .addComponent(new HeraclesAnimationController());

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
    PoopsConfig config = new NPCConfigs().poops;
    String projectileType = "poopSludge";
    //List<EntityTypes> types = poops.getEntityTypes();
    poops.getComponent(AITaskComponent.class)
            .addTask(new ProjectileTask(target, projectileType, 10, 5f, 6f,config.speed, 2f))
            .addTask(new WanderTask(new Vector2(2f, 2f), 2f));

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService()
                            .getAsset("images/Enemies/poop.atlas", TextureAtlas.class));
    animator.addAnimation("walk_front", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("walk_back", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("walk_left", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("walk_right", 0.1f, Animation.PlayMode.LOOP);

    poops
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(animator)
            .addComponent(new PoopAnimationController());
    poops.setEntityType(EntityTypes.ENEMY);
    poops.setEntityType(EntityTypes.RANGED);
    poops.setScale(2f, 2f);
    return poops;
  }

  /**
   * Creates Mega Poop, the boss of the second level.
   *
   * @return entity
   */
  public static Entity createMegaPoop(Entity target)  {
    Entity megaPoop = createBaseNPC();
    MegaPoopConfig config = configs.megaPoop;
    List<EntityTypes> types = megaPoop.getEntityTypes();
    String projectileType = "discus";

    megaPoop.getComponent(AITaskComponent.class)
            .addTask(new WanderTask(new Vector2(2f, 2f), 2f))
            .addTask(new TransportTask(target, 10, 10f));

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService()
                            .getAsset("images/Enemies/mega_poop.atlas", TextureAtlas.class));
    animator.addAnimation("walk_front", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("walk_back", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("walk_left", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("walk_right", 0.1f, Animation.PlayMode.LOOP);


    megaPoop
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, config.stamina, config.mana))
            .addComponent(animator)
            .addComponent(new MegaPoopAnimationController());

    megaPoop.setEntityType(EntityTypes.ENEMY);
    megaPoop.setEntityType(EntityTypes.BOSS);
    megaPoop.setScale(3f, 3f);
    return megaPoop;
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
    plug.setScale(3f, 3f);
    return plug;
  }

  private NPCFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}