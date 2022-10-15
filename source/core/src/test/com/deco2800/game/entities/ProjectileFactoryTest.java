package com.deco2800.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static com.deco2800.game.entities.factories.ProjectileFactory.createDiscus;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(GameExtension.class)
public class ProjectileFactoryTest {
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

        ResourceService resourceService = new ResourceService();
        ServiceLocator.registerResourceService(resourceService);
        String[] textures = {"images/Enemies/discus.png"};
        String[] textureAtlases = {"images/Enemies/heracles.atlas"};
        resourceService.loadTextures(textures);
        resourceService.loadTextureAtlases(textureAtlases);
        resourceService.loadAll();
    }

    @Test
    void createDiscusTest() {
        Entity ownerEntity = new Entity();
        Entity target = new Entity();
        Entity discus = createDiscus(ownerEntity, target);

        Vector2 expectedScale = new Vector2(2, 2);

        assertEquals("images/Enemies/discus.png", discus.getComponent(TextureRenderComponent.class).getTexturePath());
        assertEquals(expectedScale, discus.getScale());
    }
}
