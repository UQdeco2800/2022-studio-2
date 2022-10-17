package com.deco2800.game.components.tasks;

import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.events.listeners.EventListener0;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DeadTaskTest {
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

    private Entity makePhysicsEntity() {
        return new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(new PhysicsMovementComponent())
                .addComponent(new HitboxComponent());
    }

    @Test
    void animateVanishBackTest() {
        Entity target = new Entity();
        DeadTask deadTask = new DeadTask(target, 10);
        AITaskComponent ai = new AITaskComponent().addTask(deadTask);
        Entity entity = makePhysicsEntity().addComponent(ai);
        entity.create();

        //enemy below target
        target.setPosition(0f, 2f);
        entity.setPosition(0f, 0f);

        // Register callbacks
        EventListener0 callback = mock(EventListener0.class);
        entity.getEvents().addListener("vanishBack", callback);

        deadTask.start();
        verify(callback).handle();
    }

    @Test
    void animateVanishFrontTest() {
        Entity target = new Entity();
        DeadTask deadTask = new DeadTask(target, 10);
        AITaskComponent ai = new AITaskComponent().addTask(deadTask);
        Entity entity = makePhysicsEntity().addComponent(ai);
        entity.create();

        //enemy above target
        target.setPosition(0f, 0f);
        entity.setPosition(0f, 2f);

        // Register callbacks
        EventListener0 callback = mock(EventListener0.class);
        entity.getEvents().addListener("vanishFront", callback);

        deadTask.start();
        verify(callback).handle();
    }

    @Test
    void animateVanishLeftTest() {
        Entity target = new Entity();
        DeadTask deadTask = new DeadTask(target, 10);
        AITaskComponent ai = new AITaskComponent().addTask(deadTask);
        Entity entity = makePhysicsEntity().addComponent(ai);
        entity.create();

        //target is left of enemy
        target.setPosition(-2f, 0f);
        entity.setPosition(0f, 0f);

        // Register callbacks
        EventListener0 callback = mock(EventListener0.class);
        entity.getEvents().addListener("vanishLeft", callback);

        deadTask.start();
        verify(callback).handle();
    }

    @Test
    void animateVanishRightTest() {
        Entity target = new Entity();
        DeadTask deadTask = new DeadTask(target, 10);
        AITaskComponent ai = new AITaskComponent().addTask(deadTask);
        Entity entity = makePhysicsEntity().addComponent(ai);
        entity.create();

        //target is right of enemy
        target.setPosition(2f, 0f);
        entity.setPosition(0f, 0f);

        // Register callbacks
        EventListener0 callback = mock(EventListener0.class);
        entity.getEvents().addListener("vanishRight", callback);

        deadTask.start();
        verify(callback).handle();
    }

    @Test
    void getPriorityTest() {
        Entity target = new Entity();
        DeadTask deadTask = new DeadTask(target, 10);
        AITaskComponent ai = new AITaskComponent().addTask(deadTask);
        Entity entity = makePhysicsEntity().addComponent(ai);
        CombatStatsComponent combatStatsComponent = new CombatStatsComponent(10, 10, 10, 10);
        entity.addComponent(combatStatsComponent);
        entity.create();

        assertEquals(-1, deadTask.getPriority());
        entity.flagDead();
        assertEquals(10, deadTask.getPriority());
    }

    @Test
    void shouldDisposeAfterStopping() {
        Entity target = new Entity();
        DeadTask deadTask = new DeadTask(target, 10);
        AITaskComponent ai = new AITaskComponent().addTask(deadTask);
        Entity entity = spy(makePhysicsEntity().addComponent(ai));
        CombatStatsComponent combatStatsComponent = new CombatStatsComponent(10, 10, 10, 10);
        entity.addComponent(combatStatsComponent);
        entity.create();

        EntityService entityService = mock(EntityService.class);
        ServiceLocator.registerEntityService(entityService);
        entity.flagDead();
        entity.update();
        verify(entity).dispose();
        verify(entityService).unregister(entity);
    }
}
