package com.deco2800.game.components.tasks;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.DefaultTask;
import com.deco2800.game.ai.tasks.PriorityTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;

public class DeadTask extends DefaultTask implements PriorityTask {
    private Entity target;
    private int priority;
    private float startTime;
    private GameTime gameTime;

    public DeadTask(Entity target, int priority) {
        this.target = target;
        this.priority = priority;
        gameTime = ServiceLocator.getTimeSource();
    }

    @Override
    public void start() {
        super.start();
        startTime = gameTime.getTime();
        animate();
        owner.getEntity().getComponent(HitboxComponent.class).setLayer(PhysicsLayer.OBSTACLE);
    }

    @Override
    public void  update() {
        super.update();
        animate();
        if (gameTime.getTime() - startTime > 1000L) {
            owner.getEntity().getComponent(AnimationRenderComponent.class).stopAnimation();
            owner.getEntity().dispose();
        }
    }

    @Override
    public int getPriority() {
        if (owner.getEntity().isDead()) {
            return priority;
        }
        return -1;
    }

    private void animate() {
        Vector2 enemy = owner.getEntity().getCenterPosition();
        Vector2 player = target.getCenterPosition();

        float y = enemy.y - player.y;
        float x = enemy.x - player.x;

        if (Math.abs(y) > Math.abs(x)) {
            if (y >= 0) {
                this.owner.getEntity().getEvents().trigger("vanishFront");
            } else {
                this.owner.getEntity().getEvents().trigger("vanishBack");
            }
        } else {
            if (x >= 0) {
                this.owner.getEntity().getEvents().trigger("vanishLeft");
            } else {
                this.owner.getEntity().getEvents().trigger("vanishRight");
            }
        }
    }
}
