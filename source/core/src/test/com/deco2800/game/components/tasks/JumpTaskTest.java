package com.deco2800.game.components.tasks;

import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.events.listeners.EventListener0;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
class JumpTaskTest {
    @BeforeEach
    void beforeEach() {
        // Mock rendering, physics, game time
        RenderService renderService = new RenderService();
        renderService.setDebug(mock(DebugRenderer.class));
        ServiceLocator.registerRenderService(renderService);
        GameTime gameTime = mock(GameTime.class);
        when(gameTime.getDeltaTime()).thenReturn(20f / 1000);
        ServiceLocator.registerTimeSource(gameTime);
        ServiceLocator.registerPhysicsService(new PhysicsService());
    }

    @Test
    void shouldJumpTowardTarget() {
        Entity target = new Entity();
        target.setPosition(2f, 2f);

        AITaskComponent ai = new AITaskComponent().addTask(new JumpTask(target, 10, 5f, 2f, 2f));
        Entity entity = makePhysicsEntity().addComponent(ai);
        entity.create();
        entity.setPosition(0f, 0f);

        float initialDistance = entity.getPosition().dst(target.getPosition());
        for (int i = 0; i < 1; i++) {
            entity.earlyUpdate();
            entity.update();
            ServiceLocator.getPhysicsService().getPhysics().update();
        }
        float newDistance = entity.getPosition().dst(target.getPosition());
        assertTrue(newDistance < initialDistance);
    }

    private Entity makePhysicsEntity() {
        return new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(new PhysicsMovementComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new HitboxComponent());
    }

    @Test
    void shouldJumpOnlyWhenInDistance() {
        Entity target = new Entity();
        target.setPosition(0f, 6f);

        Entity entity = makePhysicsEntity();
        entity.create();
        entity.setPosition(0f, 0f);

        JumpTask jumpTask1 = new JumpTask(target, 10, 5f, 2f, 2f);
        jumpTask1.create(() -> entity);

        // Not currently active, target is too far, should have negative priority
        assertTrue(jumpTask1.getPriority() < 0);
    }

    @Test
    void stopTest() {
        Entity target = new Entity();
        target.setPosition(2f, 2f);

        JumpTask jumpTask = new JumpTask(target, 10, 5f, 10, 10f);
        AITaskComponent ai = new AITaskComponent().addTask(jumpTask);
        Entity entity = makePhysicsEntity().addComponent(ai);
        entity.create();
        entity.setPosition(0f, 0f);

        jumpTask.start();
        jumpTask.stop();
        assertEquals(Task.Status.INACTIVE, jumpTask.getStatus());
    }

    @Test
    void JumpAnimateFront() {
        Entity target = new Entity();
        JumpTask jumpTask = new JumpTask(target, 10, 5f, 2f, 2f);
        AITaskComponent ai = new AITaskComponent().addTask(jumpTask);
        Entity entity = makePhysicsEntity().addComponent(ai);
        entity.create();

        //enemy above target
        target.setPosition(0f, 0f);
        entity.setPosition(0f, 0.5f);

        // Register callbacks
        EventListener0 callback = mock(EventListener0.class);
        entity.getEvents().addListener("jumpFront", callback);

        jumpTask.start();
        verify(callback).handle();
    }

    @Test
    void JumpAnimateBack() {
        Entity target = new Entity();
        JumpTask jumpTask = new JumpTask(target, 10, 5f, 2f, 2f);
        AITaskComponent ai = new AITaskComponent().addTask(jumpTask);
        Entity entity = makePhysicsEntity().addComponent(ai);
        entity.create();

        //enemy behind target
        target.setPosition(0f, 0.5f);
        entity.setPosition(0f, 0f);

        // Register callbacks
        EventListener0 callback = mock(EventListener0.class);
        entity.getEvents().addListener("jumpBack", callback);

        jumpTask.start();
        verify(callback).handle();
    }

    @Test
    void JumpAnimateLeft() {
        Entity target = new Entity();
        JumpTask jumpTask = new JumpTask(target, 10, 5f, 2f, 2f);
        AITaskComponent ai = new AITaskComponent().addTask(jumpTask);
        Entity entity = makePhysicsEntity().addComponent(ai);
        entity.create();

        //target is left of enemy
        target.setPosition(-0.5f, 0f);
        entity.setPosition(0f, 0f);

        // Register callbacks
        EventListener0 callback = mock(EventListener0.class);
        entity.getEvents().addListener("jumpLeft", callback);

        jumpTask.start();
        verify(callback).handle();
    }

    @Test
    void JumpAnimateRight() {
        Entity target = new Entity();
        JumpTask jumpTask = new JumpTask(target, 10, 5f, 2f, 2f);
        AITaskComponent ai = new AITaskComponent().addTask(jumpTask);
        Entity entity = makePhysicsEntity().addComponent(ai);
        entity.create();

        //enemy is right of target
        target.setPosition(0.5f, 0f);
        entity.setPosition(0f, 0f);

        // Register callbacks
        EventListener0 callback = mock(EventListener0.class);
        entity.getEvents().addListener("jumpRight", callback);

        jumpTask.start();
        verify(callback).handle();
    }

}