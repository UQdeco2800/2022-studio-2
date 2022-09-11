package com.deco2800.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.ai.tasks.PriorityTask;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.raycast.RaycastHit;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.ai.tasks.Task;
import com.deco2800.game.physics.components.PhysicsComponent;

import static com.deco2800.game.entities.factories.ProjectileFactory.createPoopsSludge;

public class ProjectileTask extends DefaultTask implements PriorityTask{
    private final Entity target;
    private final float waitTime;
    private WaitTask taskWait;
    private ProjectileTask taskShoot;
    private final float speed;
    private final float viewDistance;
    private final float maxShootDistance;
    private final PhysicsEngine physics;
    private final DebugRenderer debugRenderer;
    private Task currentTask;
    private RaycastHit hit = new RaycastHit();

    private String projectileType;
    private int priority;

    private Entity projectile = null;

    /**
     * @param target The entity to chase.
     * @param speed The speed to chase at.
     */
    public ProjectileTask(Entity target, String projectileType, int priority, float viewDistance,
                          float maxShootDistance, float speed, float waitTime) {
        this.speed = speed;
        this.target = target;
        this.projectileType = projectileType;
        this.waitTime = waitTime;
        this.viewDistance = viewDistance;
        this.maxShootDistance = maxShootDistance;
        physics = ServiceLocator.getPhysicsService().getPhysics();
        debugRenderer = ServiceLocator.getRenderService().getDebug();
        this.priority = priority;
    }

    /**
     * Start shoot.
     */
    @Override
    public void start() {
        super.start();
        taskWait = new WaitTask(waitTime);
        taskWait.create(owner);

        taskShoot = new ProjectileTask(target, projectileType, priority, viewDistance, maxShootDistance, speed, waitTime);
        taskShoot.create(owner);

        currentTask = taskShoot;
    }

    @Override
    public int getPriority() {
        if (status == Status.ACTIVE) {
            return getActivePriority();
        }
        return getInactivePriority();
    }

    /**
     * Update projectile task.
     */
    @Override
    public void update() {
        if (currentTask.getStatus() != Status.ACTIVE) {
            if (currentTask == taskWait && waitTime == 0) {
                waiting();
            } else {
                shoot(this.projectileType);
            }
        }
        currentTask.update();
    }


    public void waiting() {
        setTask(taskShoot);
    }

    public void shoot(String projectileType) {
        if (projectileType == "poopSludge") {
            projectile = createPoopsSludge(target);
        }
        if (projectile != null) {
            ServiceLocator.getEntityService().register(projectile);
            projectile.setPosition(owner.getEntity().getPosition().x, owner.getEntity().getPosition().y);

            float xVel = owner.getEntity().getPosition().x - target.getCenterPosition().x;
            float yVel = owner.getEntity().getPosition().y - target.getCenterPosition().y;

            // SHOOOOOOOOOOOOOTTTTTT;
            projectile.getComponent(PhysicsComponent.class).getBody().setLinearVelocity(xVel, yVel);
            setTask(taskWait);
        }
    }
    
    public void setTask(Task task) {
        if (currentTask != null) {
            currentTask.stop();
        }
        currentTask = task;
        currentTask.start();
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
        if (dst > maxShootDistance || !isTargetVisible()) {
            return -1; // Too far, stop shooting
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
}
