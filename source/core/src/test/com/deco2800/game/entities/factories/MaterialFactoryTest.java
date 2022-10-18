package com.deco2800.game.entities.factories;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.components.player.OpenCraftingComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
@ExtendWith(GameExtension.class)
class MaterialFactoryTest {

    @Mock
    Entity entity;

    @Mock MaterialFactory materialFactory;

    @BeforeEach
    void setUp() {
        ForestGameArea fga = mock(ForestGameArea.class);
        ServiceLocator.registerGameArea(fga);
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerRenderService(new RenderService());
        ResourceService resourceService = new ResourceService();
        ServiceLocator.registerResourceService(resourceService);

        String[] textures = {
                "images/Crafting-assets-sprint1/materials/gold.png",
                "images/Crafting-assets-sprint1/materials/rainbow_poop.png",
                "images/Crafting-assets-sprint1/materials/iron.png",
                "images/Crafting-assets-sprint1/materials/toilet_paper.png",
                "images/Crafting-assets-sprint1/materials/steel.png",
                "images/Crafting-assets-sprint1/materials/wood.png",
                "images/Crafting-assets-sprint1/materials/plastic.png",
                "images/Crafting-assets-sprint1/materials/rubber.png",
                "images/Crafting-assets-sprint1/materials/platinum.png",
                "images/Crafting-assets-sprint1/materials/silver.png"};
        resourceService.loadTextures(textures);
        String[] textureAtlases = {"images/CombatItems/animations/combatItemsAnimation.atlas"};
        resourceService.loadTextureAtlases(textureAtlases);
        resourceService.loadAll();

        GameArea gameArea = spy(GameArea.class);
        RenderService renderService = new RenderService();
        renderService.setStage(mock(Stage.class));
        GameAreaDisplay material = new GameAreaDisplay("");
        ServiceLocator.registerGameArea(gameArea);
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerInventoryArea(material);
        material.create();
        entity = new Entity();
    }

    @Test
    void MaterialFactory() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor constructor = MaterialFactory.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        MaterialFactory obj=(MaterialFactory) constructor.newInstance();
        obj.toString();
    }
    @Test
    void shouldCreateBaseMaterial() {
        Entity baseEntity;
        baseEntity = MaterialFactory.createBaseMaterial();
        int id = baseEntity.getId();
        assertEquals(baseEntity.getId(), id);
        assertTrue(baseEntity instanceof Entity);
    }


    @Test
    void  shouldCreateGold(){
        entity = MaterialFactory.createGold();
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.GOLD));
    }

    @Test
    void shouldCreatePoop() {
        entity = MaterialFactory.createPoop();
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.POOP));
    }

    @Test
    void createIron() {
        entity = MaterialFactory.createIron();
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.IRON));
    }

    @Test
    void createToiletPaper() {
        entity = MaterialFactory.createToiletPaper();
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.TOILETPAPER));
    }

    @Test
    void shouldCreateSteel() {
        entity = MaterialFactory.createSteel();
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.STEEL));
    }

    @Test
    void createWood() {
        entity = MaterialFactory.createWood();
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.WOOD));
    }

    @Test
    void creatPlastic(){
        entity = MaterialFactory.createPlastic();
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.PLASTIC));
    }

    @Test
    void createRubber() {
        entity = MaterialFactory.createRubber();
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.RUBBER));
    }

    @Test
    void createPlatinum() {
        entity = MaterialFactory.createPlatinum();
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.PLATINUM));
    }

    @Test
    void createSilver() {
        entity = MaterialFactory.createSilver();
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.SILVER));
    }
}