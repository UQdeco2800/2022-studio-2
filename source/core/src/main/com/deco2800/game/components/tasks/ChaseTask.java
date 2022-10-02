package com.deco2800.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.ai.tasks.PriorityTask;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.raycast.RaycastHit;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;

/** Chases a target entity until they get too far away or line of sight is lost */
public class ChaseTask extends DefaultTask implements PriorityTask {
  private final Entity target;
  private final int priority;
  private final float viewDistance;
  private final float maxChaseDistance;
  private final PhysicsEngine physics;
  private final DebugRenderer debugRenderer;
  private float speed = 1;
  private final RaycastHit hit = new RaycastHit();
  private MovementTask movementTask;
  private float attackRange = 1.5f;

  private GameTime gameTime;
  private float lastAttackTime = 0L;

  /**
   * @param target The entity to chase.
   * @param priority Task priority when chasing (0 when not chasing).
   * @param viewDistance Maximum distance from the entity at which chasing can start.
   * @param maxChaseDistance Maximum distance from the entity while chasing before giving up.
   */
  public ChaseTask(Entity target, int priority, float viewDistance, float maxChaseDistance) {
    this.target = target;
    this.priority = priority;
    this.viewDistance = viewDistance;
    this.maxChaseDistance = maxChaseDistance;
    physics = ServiceLocator.getPhysicsService().getPhysics();
    debugRenderer = ServiceLocator.getRenderService().getDebug();
    gameTime = ServiceLocator.getTimeSource();
  }

  /**
   * @param target The entity to chase.
   * @param priority Task priority when chasing (0 when not chasing).
   * @param viewDistance Maximum distance from the entity at which chasing can start.
   * @param maxChaseDistance Maximum distance from the entity while chasing before giving up.
   * @param speed The speed to chase at.
   */
  public ChaseTask(Entity target, int priority, float viewDistance, float maxChaseDistance, float speed) {
    this.target = target;
    this.priority = priority;
    this.viewDistance = viewDistance;
    this.maxChaseDistance = maxChaseDistance;

    this.speed = speed;
    physics = ServiceLocator.getPhysicsService().getPhysics();
    debugRenderer = ServiceLocator.getRenderService().getDebug();
    gameTime = ServiceLocator.getTimeSource();
  }

  /**
   * Start chase task.
   */
  @Override
  public void start() {
    super.start();
    movementTask = new MovementTask(target.getPosition(), 0.01f, this.speed);
    movementTask.create(owner);
    movementTask.start();
    walkAnimate();
  }

  /**
   * Update chase task.
   */
  @Override
  public void update() {
    float currentTime = gameTime.getTime();
    movementTask.setTarget(target.getPosition());
    movementTask.update();
    // Attack target if it appears in range
    if (getDistanceToTarget() <= attackRange) {
      if (currentTime - lastAttackTime > 1500L) {

        PlayerActions playerActions = target.getComponent(PlayerActions.class);
        if (playerActions != null && !(playerActions.getSkillComponent().skillDamageTrigger())) {
          target.getComponent(CombatStatsComponent.class)
                  .hit(owner.getEntity().getComponent(CombatStatsComponent.class));
        }

        lastAttackTime = gameTime.getTime();
      }
      attackAnimate();
    } else {
      walkAnimate();
    }

    if (movementTask.getStatus() != Status.ACTIVE) {
      movementTask.start();
    }
  }

  /**
   * Stop this task.
   */
  @Override
  public void stop() {
    super.stop();
    movementTask.stop();
  }

  /**
   * Get the priority of this task.
   * @return integer representing the priority of this task.
   */
  @Override
  public int getPriority() {
    if (status == Status.ACTIVE) {
      return getActivePriority();
    }
    return getInactivePriority();
  }

  /**
   * Get the distance from entity to its target.
   * @return float representing the distance from entity to its target.
   */
  private float getDistanceToTarget() {
    return owner.getEntity().getPosition().dst(target.getPosition());
  }

  /**
   * Get the active priority.
   * @return integer representing the active priority
   */
  private int getActivePriority() {
    float dst = getDistanceToTarget();
    if (dst > maxChaseDistance || !isTargetVisible()) {
      return -1; // Too far, stop chasing
    }
    return priority;
  }

  /**
   * Get the inactive priority.
   * @return integer representing the inactive priority
   */
  private int getInactivePriority() {
    float dst = getDistanceToTarget();
    if (dst < viewDistance && isTargetVisible()) {
      return priority;
    }
    return -1;
  }


  /**
   * Check if target is visible to entity.
   * @return true if target is visible, false if hidden/not visible.
   */
  private boolean isTargetVisible() {
    Vector2 from = owner.getEntity().getCenterPosition();
    Vector2 to = target.getCenterPosition();

    // If there is an obstacle in the path to the player, not visible.
    if (physics.raycast(from, to, PhysicsLayer.OBSTACLE, hit)) {
      debugRenderer.drawLine(from, hit.point);
      return false;
    }
    debugRenderer.drawLine(from, to);
    return true;
  }

  /**
   * Animates enemy based on which direction they are facing
   */
  private void walkAnimate() {
    Vector2 enemy = owner.getEntity().getCenterPosition();
    Vector2 player = target.getCenterPosition();

    float y = enemy.y - player.y;
    float x = enemy.x - player.x;

    if (Math.abs(y) > Math.abs(x)) {
      if (y >= 0) {
        this.owner.getEntity().getEvents().trigger("walkFront");
      } else {
        this.owner.getEntity().getEvents().trigger("walkBack");
      }
    } else {
      if (x >= 0) {
        this.owner.getEntity().getEvents().trigger("walkLeft");
      } else {
        this.owner.getEntity().getEvents().trigger("walkRight");
      }
    }
  }

  private void attackAnimate() {
    Vector2 enemy = owner.getEntity().getCenterPosition();
    Vector2 player = target.getCenterPosition();

    float y = enemy.y - player.y;
    float x = enemy.x - player.x;

    if (Math.abs(y) > Math.abs(x)) {
      if (y >= 0) {
        this.owner.getEntity().getEvents().trigger("attackFront");
      } else {
        this.owner.getEntity().getEvents().trigger("attackBack");
      }
    } else {
      if (x >= 0) {
        this.owner.getEntity().getEvents().trigger("attackLeft");
      } else {
        this.owner.getEntity().getEvents().trigger("attackRight");
      }
    }
  }
}