package com.deco2800.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.ai.tasks.PriorityTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.raycast.RaycastHit;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;

/** Chases a target entity until they get too far away or line of sight is lost */
public class ShootTask extends DefaultTask implements PriorityTask {
    private final Entity target;

    private Vector2 targetPosition;
    private final int priority;
    private final PhysicsEngine physics;
    private final DebugRenderer debugRenderer;
    private float speed = 1;
    private final RaycastHit hit = new RaycastHit();
    private MovementTask movementTask;
    private float attackRange = 1.5f;

    private GameTime gameTime;
    private float lastAttackTime = 0L;

    /**
     * @param target           The location to fire towards.
     * @param priority         Task priority when shot (0 when not shot).
     * @param speed            The speed to chase at.
     */
    public ShootTask(Entity target, int priority, float speed) {
        this.target = target;
        this.priority = priority;
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
        targetPosition = target.getPosition();
        movementTask = new MovementTask(targetPosition, 0.01f, this.speed);
        movementTask.create(owner);
        movementTask.start();
    }

    /**
     * Update chase task.
     */
    @Override
    public void update() {
        movementTask.setTarget(target.getPosition());
        movementTask.update();
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
        return priority;
    }
}
