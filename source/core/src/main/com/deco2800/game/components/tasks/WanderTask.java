
package com.deco2800.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.ai.tasks.PriorityTask;
import com.deco2800.game.ai.tasks.Task;
import com.deco2800.game.utils.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wander around by moving a random position within a range of the starting position. Wait a little
 * bit between movements. Requires an entity with a PhysicsMovementComponent.
 */
public class WanderTask extends DefaultTask implements PriorityTask {
  private static final Logger logger = LoggerFactory.getLogger(WanderTask.class);
  private final Vector2 wanderRange;
  private final float waitTime;
  private Vector2 startPos;
  private MovementTask movementTask;
  private WaitTask waitTask;
  private Task currentTask;

  private Vector2 target;

  /**
   * @param wanderRange Distance in X and Y the entity can move from its position when start() is
   *     called.
   * @param waitTime How long in seconds to wait between wandering.
   */
  public WanderTask(Vector2 wanderRange, float waitTime) {
    this.wanderRange = wanderRange;
    this.waitTime = waitTime;
  }

  /**
   * Get the priority of this task.
   * @return integer representing the priority of this task.
   */
  @Override
  public int getPriority() {
    return 1; // Low priority task
  }

  /**
   * Start this task.
   */
  @Override
  public void start() {
    super.start();
    startPos = owner.getEntity().getPosition();

    waitTask = new WaitTask(waitTime);
    waitTask.create(owner);
    target = getRandomPosInRange();
    movementTask = new MovementTask(target);
    movementTask.create(owner);

    movementTask.start();
    currentTask = movementTask;

    animate();
  }

  /**
   * Update this task.
   */
  @Override
  public void update() {
    if (currentTask.getStatus() != Status.ACTIVE) {
      if (currentTask == movementTask) {
        startWaiting();
      } else {
        startMoving();
      }
    }
    currentTask.update();
  }

  /**
   * Start the waiting task.
   */
  private void startWaiting() {
    logger.debug("Starting waiting");
    swapTask(waitTask);
  }

  /**
   * Start the movement task.
   */
  private void startMoving() {
    logger.debug("Starting moving");
    this.target = getRandomPosInRange();
    movementTask.setTarget(this.target);
    swapTask(movementTask);
    animate();
  }

  /**
   * Swap tasks.
   */
  private void swapTask(Task newTask) {
    if (currentTask != null) {
      currentTask.stop();
    }
    currentTask = newTask;
    currentTask.start();
  }

  /**
   * Get a random position in range.
   * @return Vector2 that represents a random position in range.
   */
  private Vector2 getRandomPosInRange() {
    Vector2 halfRange = wanderRange.cpy().scl(0.5f);
    Vector2 min = startPos.cpy().sub(halfRange);
    Vector2 max = startPos.cpy().add(halfRange);
    return RandomUtils.random(min, max);
  }

  /**
   * Animates enemy based on which direction they are facing
   */
  private void animate() {
    Vector2 enemy = owner.getEntity().getCenterPosition();
    float y = enemy.y - target.y;
    float x = enemy.x - target.x;

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
}

