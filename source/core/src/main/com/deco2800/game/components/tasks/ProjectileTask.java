package com.deco2800.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.ai.tasks.PriorityTask;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.raycast.RaycastHit;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.ai.tasks.Task;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.entities.factories.ProjectileFactory;
import com.badlogic.gdx.physics.box2d.Body;
import com.deco2800.game.physics.components.PhysicsComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class ProjectileTask extends DefaultTask implements PriorityTask{
    private static final Logger logger = LoggerFactory.getLogger(ProjectileTask.class);
    private final Entity target;
    private Entity poopSludge;
    private MovementTask movementTask;
    private final float waitTime;
    private Vector2 startPos;
    private WaitTask taskWait;
    private ProjectileTask taskShoot;
    private final float speed;
    private final float viewDistance;
    private final float maxShootDistance;
    private final PhysicsEngine physics;
    private final DebugRenderer debugRenderer;
    private Task currentTask;
    private RaycastHit hit = new RaycastHit();
    private List<EntityTypes> types;
    private int priority;
    /**
     * @param target The entity to chase.
     * @param speed The speed to chase at.
     */

    public ProjectileTask(Entity target, List<EntityTypes> types, int priority, float viewDistance,
                          float maxShootDistance, float speed, float waitTime) {

        this.speed = speed;
        this.target = target;
        this.types = types;
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
        startPos = owner.getEntity().getPosition();
        taskWait = new WaitTask(waitTime);
        taskWait.create(owner);

        taskShoot = new ProjectileTask(target, types, priority, viewDistance, maxShootDistance, speed, waitTime);
        taskShoot.create(owner);

        //movementTask = new MovementTask(target.getPosition(), 0.01f, this.speed);
        //movementTask.create(owner);
        //movementTask.start();

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
     * Update chase task.
     */
    @Override
    public void update() {
        //movementTask.setTarget(target.getPosition());
        //movementTask.update();
        if (currentTask.getStatus() != Status.ACTIVE) {
            if (currentTask == taskWait && waitTime == 0) {
                waiting();
            } else {
                shoot(this.types);
            }
        }
        currentTask.update();
    }


    public void waiting() {

        setTask(taskShoot);
    }

    public void shoot(List<EntityTypes> bulletType) {
        if (bulletType.contains(EntityTypes.ENEMY)) {
            if (bulletType.contains(EntityTypes.RANGED)) {
                poopSludge = ProjectileFactory.createPoopsSludge(target);
                ServiceLocator.getEntityService().register(poopSludge);
                poopSludge.setPosition(owner.getEntity().getPosition().x, owner.getEntity().getPosition().y);

                float xVel = owner.getEntity().getPosition().x - target.getCenterPosition().x;
                float yVel = owner.getEntity().getPosition().y - target.getCenterPosition().y;

                // SHOOOOOOOOOOOOOTTTTTT;
                poopSludge.getComponent(PhysicsComponent.class).getBody().setLinearVelocity(xVel, yVel);
                setTask(taskWait);
            }
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

    /*
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
    */
}
