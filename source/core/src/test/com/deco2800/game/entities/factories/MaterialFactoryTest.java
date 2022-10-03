package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.deco2800.game.components.ItemPickupComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(GameExtension.class)
class MaterialFactoryTest {

    @Mock
    ShapeRenderer shapeRenderer;
    @Mock
    Box2DDebugRenderer physicsRenderer;
    @Mock
    Matrix4 projMatrix;

    DebugRenderer debugRenderer;

    @BeforeEach
    void setUp() {
        debugRenderer = new DebugRenderer(physicsRenderer, shapeRenderer);
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerRenderService(new RenderService());
    }

    @Test
    void createBaseMaterial() {
        Entity baseEntity;
        baseEntity = MaterialFactory.createBaseMaterial();
        int id = baseEntity.getId();
        assertEquals(baseEntity.getId(), id);
        assertTrue(baseEntity instanceof Entity);
    }

    @Test
    void createGold(){
        Entity entity = MaterialFactory.testCreateGold();
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.GOLD));
//        List<EntityTypes> entityTypes = new ArrayList<>();
//        entityTypes.add(EntityTypes.CRAFTABLE);
//        entityTypes.add(EntityTypes.GOLD);
//        assertEquals(entity.getEntityTypes(), entityTypes);
    }

//    @Test
//    void createGold() {
//        Entity entity;
//        entity= MaterialFactory.createGold();
//        assertTrue(entity instanceof Entity);
////        assertTrue(entity.checkEntityType(EntityTypes.GOLD));
//        List<EntityTypes> entityTypes = entity.getEntityTypes();
//        entityTypes.add(EntityTypes.GOLD);
//        entityTypes.add(EntityTypes.CRAFTABLE);
////        System.out.println(entity.getEntityTypes());
////        assertEquals(entity.getEntityTypes(), entityTypes);
////        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
//    }

    @Test
    void createPoop() {
        Entity entity = MaterialFactory.creatTestMaterial("poop");
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.POOP));
    }

    @Test
    void createIron() {
        Entity entity = MaterialFactory.creatTestMaterial("iron");
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.IRON));
    }

    @Test
    void createToiletPaper() {
        Entity entity = MaterialFactory.creatTestMaterial("toiletPaper");
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.TOILETPAPER));
    }

    @Test
    void createWood() {
        Entity entity = MaterialFactory.creatTestMaterial("wood");
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.WOOD));
    }

    @Test
    void creatPlastic(){
        Entity entity = MaterialFactory.creatTestMaterial("plastic");
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.PLASTIC));
    }

    @Test
    void createRubber() {
        Entity entity = MaterialFactory.creatTestMaterial("rubber");
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.RUBBER));
    }

    @Test
    void createPlatinum() {
        Entity entity = MaterialFactory.creatTestMaterial("platinum");
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.PLATINUM));
    }

    @Test
    void createSilver() {
        Entity entity = MaterialFactory.creatTestMaterial("silver");
        assertTrue(entity.checkEntityType(EntityTypes.CRAFTABLE));
        assertTrue(entity.checkEntityType(EntityTypes.SILVER));
    }
}