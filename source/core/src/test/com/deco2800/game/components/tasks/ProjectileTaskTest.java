package com.deco2800.game.components.tasks;

import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.events.listeners.EventListener0;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import net.bytebuddy.implementation.bytecode.collection.ArrayLength;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.deco2800.game.entities.factories.ProjectileFactory.createPoopsSludge;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class ProjectileTaskTest {
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
            EntityService entityService = new EntityService();
            ServiceLocator.registerEntityService(entityService);
        }

        @Test
        void createPoopSludgeProjectileTaskTest() {
            Entity target = new Entity();
            target.setPosition(2f, 2f);

            AITaskComponent ai = new AITaskComponent().addTask(new ProjectileTask(target, "poopSludge",
                    10, 10f, 10f, 10f, 1f));
            Entity entity = makePhysicsEntity().addComponent(ai);
            entity.create();
            entity.setPosition(0f, 0f);
        }

        @Test
        void createDiscusProjectileTaskTest() {
            Entity target = new Entity();
            target.setPosition(2f, 2f);

            AITaskComponent ai = new AITaskComponent().addTask(new ProjectileTask(target, "discus",
                    10, 10f, 10f, 10f, 1f));
            Entity entity = makePhysicsEntity().addComponent(ai);
            entity.create();
            entity.setPosition(0f, 0f);
        }


    @Test
    void attackAnimateDiscusAttackBackTest() {
        ResourceService resourceService = new ResourceService();
        ServiceLocator.registerResourceService(resourceService);
        String[] textures = {"images/Enemies/poopSludge.png"};
        String[] textureAtlases = {"images/Enemies/poop.atlas"};
        resourceService.loadTextures(textures);
        resourceService.loadTextureAtlases(textureAtlases);
        resourceService.loadAll();

        Entity target = new Entity();
        ProjectileTask projectileTask = new ProjectileTask(target, "poopSludge", 5, 10f, 10f, 10f, 1f);
        AITaskComponent ai = new AITaskComponent().addTask(projectileTask);
        Entity entity = makePhysicsEntity().addComponent(ai);
        entity.create();

        //enemy below target
        target.setPosition(0f, 2f);
        entity.setPosition(0f, 0f);

        // Register callbacks
        EventListener0 callback = mock(EventListener0.class);
        entity.getEvents().addListener("discusAttackBack", callback);

        projectileTask.start();
        projectileTask.update();
        verify(callback).handle();
    }

    @Test
    void attackAnimateDiscusAttackFrontTest() {
        ResourceService resourceService = new ResourceService();
        ServiceLocator.registerResourceService(resourceService);
        String[] textures = {"images/Enemies/poopSludge.png"};
        String[] textureAtlases = {"images/Enemies/poop.atlas"};
        resourceService.loadTextures(textures);
        resourceService.loadTextureAtlases(textureAtlases);
        resourceService.loadAll();

        Entity target = new Entity();
        ProjectileTask projectileTask = new ProjectileTask(target, "poopSludge", 5, 10f, 10f, 10f, 1f);
        AITaskComponent ai = new AITaskComponent().addTask(projectileTask);
        Entity entity = makePhysicsEntity().addComponent(ai);
        entity.create();

        //enemy above target
        target.setPosition(0f, 0f);
        entity.setPosition(0f, 2f);

        // Register callbacks
        EventListener0 callback = mock(EventListener0.class);
        entity.getEvents().addListener("discusAttackFront", callback);

        projectileTask.start();
        projectileTask.update();
        verify(callback).handle();
    }

    @Test
    void attackAnimateDiscusAttackLeftTest() {
        ResourceService resourceService = new ResourceService();
        ServiceLocator.registerResourceService(resourceService);
        String[] textures = {"images/Enemies/poopSludge.png"};
        String[] textureAtlases = {"images/Enemies/poop.atlas"};
        resourceService.loadTextures(textures);
        resourceService.loadTextureAtlases(textureAtlases);
        resourceService.loadAll();

        Entity target = new Entity();
        ProjectileTask projectileTask = new ProjectileTask(target, "poopSludge", 5, 10f, 10f, 10f, 1f);
        AITaskComponent ai = new AITaskComponent().addTask(projectileTask);
        Entity entity = makePhysicsEntity().addComponent(ai);
        entity.create();

        //target to left of enemy
        target.setPosition(0f, 0f);
        entity.setPosition(2f, 0f);

        // Register callbacks
        EventListener0 callback = mock(EventListener0.class);
        entity.getEvents().addListener("discusAttackLeft", callback);

        projectileTask.start();
        projectileTask.update();
        verify(callback).handle();
    }

    @Test
    void attackAnimateDiscusAttackRightTest() {
        ResourceService resourceService = new ResourceService();
        ServiceLocator.registerResourceService(resourceService);
        String[] textures = {"images/Enemies/poopSludge.png"};
        String[] textureAtlases = {"images/Enemies/poop.atlas"};
        resourceService.loadTextures(textures);
        resourceService.loadTextureAtlases(textureAtlases);
        resourceService.loadAll();

        Entity target = new Entity();
        ProjectileTask projectileTask = new ProjectileTask(target, "poopSludge", 5, 10f, 10f, 10f, 1f);
        AITaskComponent ai = new AITaskComponent().addTask(projectileTask);
        Entity entity = makePhysicsEntity().addComponent(ai);
        entity.create();

        //target to right of enemy
        target.setPosition(2f, 0f);
        entity.setPosition(0f, 0f);

        // Register callbacks
        EventListener0 callback = mock(EventListener0.class);
        entity.getEvents().addListener("discusAttackRight", callback);

        projectileTask.start();
        projectileTask.update();
        verify(callback).handle();
    }
    private Entity makePhysicsEntity() {
            return new Entity()
                    .addComponent(new PhysicsComponent())
                    .addComponent(new PhysicsMovementComponent());
        }
    }

