package com.deco2800.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Move to a given position, finishing when you get close enough. Requires an entity with a
 * PhysicsMovementComponent.
 */
public class MovementTask extends DefaultTask {
  private static final Logger logger = LoggerFactory.getLogger(MovementTask.class);

  private final GameTime gameTime;
  private Vector2 target;
  private float stopDistance = 0.01f;
  private long lastTimeMoved;
  private Vector2 lastPos;
  private PhysicsMovementComponent movementComponent;
  private float speed = 1;

  /**
   * Create movement task
   * @param target The target to move towards
   */
  public MovementTask(Vector2 target) {
    this.target = target;
    this.gameTime = ServiceLocator.getTimeSource();
  }

  /**
   * Create movement task
   * @param target The target to move towards
   * @param stopDistance The distance to stop at.
   * @param speed The speed to move at.
   */
  public MovementTask(Vector2 target, float stopDistance, float speed) {
    this.target = target;
    this.speed = speed;
    this.stopDistance = stopDistance;
    this.gameTime = ServiceLocator.getTimeSource();
  }

  /**
   * Create movement task
   * @param target The target to move towards
   * @param stopDistance The distance to stop at.
   */
  public MovementTask(Vector2 target, float stopDistance) {
    this(target);
    this.stopDistance = stopDistance;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }

  /**
   * Start movement task.
   */
  @Override
  public void start() {
    super.start();
    this.movementComponent = owner.getEntity().getComponent(PhysicsMovementComponent.class);
    movementComponent.setSpeed(this.speed);
    movementComponent.setTarget(target);
    movementComponent.setMoving(true);
    logger.debug("Starting movement towards {}", target);
    lastTimeMoved = gameTime.getTime();
    lastPos = owner.getEntity().getPosition();
  }

  /**
   * Update movement task
   */
  @Override
  public void update() {
    if (isAtTarget()) {
      movementComponent.setMoving(false);
      status = Status.FINISHED;
      logger.debug("Finished moving to {}", target);
    } else {
      checkIfStuck();
    }
  }

  /**
   * Set a new target.
   * @param target The target to move towards
   */
  public void setTarget(Vector2 target) {
    this.target = target;
    movementComponent.setTarget(target);
  }

  /**
   * Stop this task.
   */
  @Override
  public void stop() {
    super.stop();
    movementComponent.setMoving(false);
    logger.debug("Stopping movement");
  }

  /**
   * Check if entity at target
   * @return true if entity is at target, else false.
   */
  private boolean isAtTarget() {
    return owner.getEntity().getPosition().dst(target) <= stopDistance;
  }

  /**
   * Check if entity is stuck
   */
  private void checkIfStuck() {
    if (didMove()) {
      lastTimeMoved = gameTime.getTime();
      lastPos = owner.getEntity().getPosition();
    } else if (gameTime.getTimeSince(lastTimeMoved) > 500L) {
      movementComponent.setMoving(false);
      status = Status.FAILED;
      logger.debug("Got stuck! Failing movement task");
    }
  }

  /**
   * Check if entity did move
   * @return true if entity did move, false if not
   */
  private boolean didMove() {
    return owner.getEntity().getPosition().dst2(lastPos) > 0.001f;
  }
}
