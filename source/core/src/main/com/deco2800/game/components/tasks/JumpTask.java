package com.deco2800.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.ai.tasks.PriorityTask;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;

/** Jump to an entity and attack it if it is still in attacking range. */
public class JumpTask extends DefaultTask implements PriorityTask {
    private Entity target;
    private int priority;
    private float attackRange;
    private float glidingSpeed;
    private Vector2 targetPos;
    private float knockBackForce;
    private float stopDistance = 0.01f;
    private float lastJumpTime;
    private long lastTimeMoved;
    private Vector2 lastPos;
    private MovementTask movementTask;
    private GameTime gameTime;
    private CombatStatsComponent combatStats;

    /**
     * Create a JumpTask with target, attack range, gliding speed and knock back distance.
     * @param target The target entity will be jumped at.
     * @param priority Task priority when jumping.
     * @param attackRange Maximum distance from the entity while gliding before giving up.
     * @param glidingSpeed The speed to glide at.
     * @param knockBackForce The magnitude of the knock back applied to the entity.
     */
    public JumpTask(Entity target, int priority, float attackRange, float glidingSpeed, float knockBackForce) {
        this.target = target;
        this.priority = priority;
        this.attackRange = attackRange;
        this.knockBackForce = knockBackForce;
        this.glidingSpeed = glidingSpeed;
        gameTime = ServiceLocator.getTimeSource();
        lastJumpTime = gameTime.getTime() + 1000L;
        combatStats = target.getComponent(CombatStatsComponent.class);
    }

    /**
     * Start the jump task.
     */
    @Override
    public void start() {
        super.start();
        targetPos = target.getPosition();
        movementTask = new MovementTask(targetPos, stopDistance, glidingSpeed);
        movementTask.create(owner);
        movementTask.start();
        lastTimeMoved = gameTime.getTime();
        lastPos = owner.getEntity().getPosition();
        animate();
    }

    /**
     * Update the jump task.
     */
    @Override
    public void update() {
        if (isAtTarget()) {
            if (getDistanceToTarget() <= 2f) {
                PlayerActions playerActions = target.getComponent(PlayerActions.class);
                if (playerActions != null && !(playerActions.getSkillComponent().skillDamageTrigger())) {
                    target.getComponent(CombatStatsComponent.class)
                            .hit(owner.getEntity().getComponent(CombatStatsComponent.class));
                }

                // Apply knock back
                PhysicsComponent physicsComponent = target.getComponent(PhysicsComponent.class);
                if (physicsComponent != null && knockBackForce > 0f) {
                    Body targetBody = physicsComponent.getBody();
                    Vector2 direction = target.getCenterPosition().sub(owner.getEntity().getCenterPosition());
                    Vector2 impulse = direction.setLength(knockBackForce);
                    targetBody.applyLinearImpulse(impulse, targetBody.getWorldCenter(), true);
                }
            }
            lastJumpTime = gameTime.getTime();
            status = Status.FINISHED;
        } else {
            movementTask.setTarget(targetPos);
            movementTask.update();
        }
        checkIfStuck();
    }

    /**
     * Stop the jump task.
     */
    @Override
    public void stop() {
        super.stop();
        movementTask.stop();
    }

    /**
     * Check if entity is stuck.
     */
    private void checkIfStuck() {
        if (didMove()) {
            lastTimeMoved = gameTime.getTime();
            lastPos = owner.getEntity().getPosition();
        } else if (gameTime.getTimeSince(lastTimeMoved) > 150L) {
            stop();
            lastJumpTime = gameTime.getTime();
            status = Status.FINISHED;
        }
    }

    /**
     * Check if entity did move.
     * @return true if entity did move, false if not.
     */
    private boolean didMove() {
        return owner.getEntity().getPosition().dst2(lastPos) > 0.07f;
    }

    /**
     * Animates enemy based on which direction they are facing.
     */
    private void animate() {
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

    /**
     * Get the distance from entity to its target.
     * @return float representing the distance from entity to its target.
     */
    private float getDistanceToTarget() {
        return owner.getEntity().getPosition().dst(target.getPosition());
    }

    /**
     * Check if entity at target
     * @return true if entity is at target, else false.
     */
    private boolean isAtTarget() {return owner.getEntity().getPosition().dst(targetPos) <= 1f;}

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
        if (dst > attackRange || (gameTime.getTime() - lastJumpTime < 7000L)) {
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
        if (dst < attackRange && (gameTime.getTime() - lastJumpTime > 7000L)) {
            return priority;
        }
        return -1;
    }
}
