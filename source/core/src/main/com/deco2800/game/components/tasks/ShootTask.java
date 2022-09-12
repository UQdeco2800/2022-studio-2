package com.deco2800.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.ai.tasks.PriorityTask;
import com.deco2800.game.entities.Entity;

/** Chases a target entity until they get too far away or line of sight is lost */
public class ShootTask extends DefaultTask implements PriorityTask {
    private final Entity target;

    private Vector2 targetPosition;
    private final int priority;
    private float speed = 1;
    private MovementTask movementTask;


    /**
     * @param target           The location to fire towards.
     * @param priority         Task priority when shot (0 when not shot).
     * @param speed            The speed to chase at.
     */
    public ShootTask(Entity target, int priority, float speed) {
        this.target = target;
        this.priority = priority;
        this.speed = speed;
    }


    /**
     * Start shoot task.
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
     * Update shoot task.
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
