package com.deco2800.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.ai.tasks.PriorityTask;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;

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

    @Override
    public void update() {
        if (isAtTarget()) {
            if (getDistanceToTarget() <= 2f) {
                CombatStatsComponent targetStats = target.getComponent(CombatStatsComponent.class);
                targetStats.hit(combatStats);

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

    private void checkIfStuck() {
        if (didMove()) {
            lastTimeMoved = gameTime.getTime();
            lastPos = owner.getEntity().getPosition();
        } else if (gameTime.getTimeSince(lastTimeMoved) > 200L) {
            //movementComponent.setMoving(false);
            lastJumpTime = gameTime.getTime();
            status = Status.FINISHED;
            System.out.println("here");
        }
    }

    private boolean didMove() {
        return owner.getEntity().getPosition().dst2(lastPos) > 0.001f;
    }

    @Override
    public void stop() {
        super.stop();
        movementTask.stop();
    }

    private void animate() {
        Vector2 enemy = owner.getEntity().getCenterPosition();
        Vector2 player = target.getCenterPosition();

        float y = enemy.y - player.y;
        float x = enemy.x - player.x;

        if (Math.abs(y) > Math.abs(x)) {
            if (y >= 0) {
                this.owner.getEntity().getEvents().trigger("attackFront");
            } else {
                this.owner.getEntity().getEvents().trigger("attackBack");
            }
        } else {
            if (x >= 0) {
                this.owner.getEntity().getEvents().trigger("attackLeft");
            } else {
                this.owner.getEntity().getEvents().trigger("attackRight");
            }
        }
    }


    private float getDistanceToTarget() {
        return owner.getEntity().getPosition().dst(target.getPosition());
    }

    private boolean isAtTarget() {return owner.getEntity().getPosition().dst(targetPos) <= 2f;}

    @Override
    public int getPriority() {
        if (status == Status.ACTIVE) {
            return getActivePriority();
        }
        return getInactivePriority();
    }

    private int getActivePriority() {
        float dst = getDistanceToTarget();
        if (dst > attackRange || (gameTime.getTime() - lastJumpTime < 10000L)) {
            return -1;
        }
        return priority;
    }

    private int getInactivePriority() {
        float dst = getDistanceToTarget();
        if (dst < attackRange && (gameTime.getTime() - lastJumpTime > 10000L)) {
            return priority;
        }
        return -1;
    }
}
