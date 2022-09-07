package com.deco2800.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.raycast.RaycastHit;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.entities.Entity;

public class ProjectileTask extends DefaultTask {

    private final Entity target;
    private final float speed;
    private final PhysicsEngine physics;
    private final DebugRenderer debugRenderer;
    private MovementTask movementTask;

    private RaycastHit hit;

    /**
     * @param target The entity to chase.
     * @param speed The speed to chase at.
     */
    public ProjectileTask(Entity target, float speed) {
        this.target = target;
        this.speed = speed;
        physics = ServiceLocator.getPhysicsService().getPhysics();
        debugRenderer = ServiceLocator.getRenderService().getDebug();
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
    }

    /**
     * Update chase task.
     */
    @Override
    public void update() {
        movementTask.update();
        if (movementTask.getStatus() != Status.ACTIVE) {
            movementTask.start();
        }
    }


    // Weapon pickup component


    /**
     * Stop this task.
     */
    @Override
    public void stop() {
        super.stop();
        movementTask.stop();
    }

    private boolean hasCollided() {
        Vector2 currentPos = owner.getEntity().getCenterPosition();
        Vector2 targetPos = target.getCenterPosition();

        // If hits an obstacle, delete projectile
        if (physics.raycast(currentPos, targetPos, PhysicsLayer.OBSTACLE, hit)) {
            debugRenderer.drawLine(currentPos, hit.point);
            return false;
        }
        debugRenderer.drawLine(currentPos, targetPos);
        return true;
    }
}
