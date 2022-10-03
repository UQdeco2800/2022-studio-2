package com.deco2800.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.ai.tasks.PriorityTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;

/** Transport to an entity if it is still in attacking range. */
public class TransportTask extends DefaultTask implements PriorityTask {
    private Entity target;
    private int priority;
    private float effectiveRange;
    private Vector2 castPos;
    private GameTime gameTime;
    private final long castingTime = 2000L;
    private long startTime;
    private long lastTransportTime;

    /**
     * Create a TransportTask with target entity, effective range and its task priority.
     * @param target The target entity will be jumped at.
     * @param priority Task priority.
     * @param effectiveRange Maximum effective distance from the entity before giving up.
     */
    public TransportTask(Entity target, int priority, float effectiveRange) {
        this.target = target;
        this.priority = priority;
        this.effectiveRange = effectiveRange;
        gameTime = ServiceLocator.getTimeSource();
        lastTransportTime = gameTime.getTime() + 4000L;
    }

    /**
     * Start the transport task.
     */
    @Override
    public void start() {
        super.start();
        startTime = gameTime.getTime();
        castAnimate();
        castPos = owner.getEntity().getPosition();
    }

    /**
     * Update the transport task.
     */
    @Override
    public void update() {
        owner.getEntity().setPosition(owner.getEntity().getPosition());
        if (gameTime.getTime() > startTime + castingTime) {
            owner.getEntity().setPosition(target.getPosition());
            stop();
        }
    }

    /**
     * Stop the transport task.
     */
    @Override
    public void stop() {
        lastTransportTime = gameTime.getTime();
        super.stop();
    }

    /**
     * Animate the casting action.
     */
    private void castAnimate() {
        this.owner.getEntity().getEvents().trigger("cast");
    }

    /**
     * Get the distance from entity to its target.
     * @return float representing the distance from entity to its target.
     */
    private float getDistanceToTarget() {
        return owner.getEntity().getPosition().dst(target.getPosition());
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
     * Get the active priority.
     * @return integer representing the active priority
     */
    private int getActivePriority() {
        float dst = getDistanceToTarget();
        if (dst > effectiveRange || (gameTime.getTime() - lastTransportTime < 2000L)) {
            return -1;
        }
        return priority;
    }

    /**
     * Get the inactive priority.
     * @return integer representing the inactive priority.
     */
    private int getInactivePriority() {
        float dst = getDistanceToTarget();
        if (dst < effectiveRange && (gameTime.getTime() - lastTransportTime > 2000L)) {
            return priority;
        }
        return -1;
    }
}