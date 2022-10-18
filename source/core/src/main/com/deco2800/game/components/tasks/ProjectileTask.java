package com.deco2800.game.components.tasks;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.ai.tasks.PriorityTask;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.raycast.RaycastHit;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.ai.tasks.Task;

import static com.deco2800.game.entities.factories.ProjectileFactory.createDiscus;
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

    /**
     * Get the priority of this task
     * @return the priority of this task
     */
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
            if (currentTask == taskWait) {
                waiting();
            } else {
                attackAnimate();
                shoot(this.projectileType);
            }
        }
        currentTask.update();
    }

    /**
     * If entity is waiting, set its task to shoot
     */
    public void waiting() {
        setTask(taskShoot);
    }


    /**
     * Shoot projectile
     * @param projectileType the type of projectile to shoot
     */
    public void shoot(String projectileType) {
        if (projectileType.equals("poopSludge")) {
            projectile = createPoopsSludge(owner.getEntity(), target);
        }
        if (projectileType.equals("discus")) {
            projectile = createDiscus(owner.getEntity(), target);
        }
        if (owner.getEntity().checkEntityType(EntityTypes.MEGAPOOP)) {
            Entity projectile1 = createPoopsSludge(owner.getEntity(), target);
            ServiceLocator.getEntityService().register(projectile1);
            projectile1.setPosition(owner.getEntity().getPosition().x - 0.5f, owner.getEntity().getPosition().y - 0.5f);

            Entity projectile2 = createPoopsSludge(owner.getEntity(), target);
            ServiceLocator.getEntityService().register(projectile2);
            projectile2.setPosition(owner.getEntity().getPosition().x + 0.5f, owner.getEntity().getPosition().y + 0.5f);

        } else if (projectile != null) {
            ServiceLocator.getEntityService().register(projectile);
            projectile.setPosition(owner.getEntity().getPosition().x, owner.getEntity().getPosition().y);
        }
        setTask(taskWait);
    }

    /**
     * Set the task
     * @param task the task to set it to
     */
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

    /**
     * Returns if the target is visible or not
     * @return true if target visible, false otherwise
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
     * Animate the projectile attack
     */
    private void attackAnimate() {
        Vector2 enemy = owner.getEntity().getCenterPosition();
        Vector2 player = target.getCenterPosition();


        float y = enemy.y - player.y;
        float x = enemy.x - player.x;

        if (Math.abs(y) > Math.abs(x)) {
            if (y >= 0) {
                this.owner.getEntity().getEvents().trigger("discusAttackFront");
                this.owner.getEntity().getEvents().trigger("projectileAttackFront");
            } else {
                this.owner.getEntity().getEvents().trigger("discusAttackBack");
                this.owner.getEntity().getEvents().trigger("projectileAttackBack");
            }
        } else {
            if (x >= 0) {
                this.owner.getEntity().getEvents().trigger("discusAttackLeft");
                this.owner.getEntity().getEvents().trigger("projectileAttackLeft");
            } else {
                this.owner.getEntity().getEvents().trigger("discusAttackRight");
                this.owner.getEntity().getEvents().trigger("projectileAttackRight");
            }
        }
    }
}
