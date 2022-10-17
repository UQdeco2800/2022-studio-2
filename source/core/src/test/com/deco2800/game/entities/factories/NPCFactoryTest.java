package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(GameExtension.class)
class NPCFactoryTest {

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
    void createBaseNPC() {
        Entity baseEntity;
        baseEntity = NPCFactory.createBaseNPC();
        int id = baseEntity.getId();
        assertEquals(baseEntity.getId(), id);
        assertTrue(baseEntity instanceof Entity);
    }

    @Test
    void createOneLegGirl() {
        Entity entity = NPCFactory.creatTestNPC("female");
        assertTrue(entity.checkEntityType(EntityTypes.NPC));
        assertTrue(entity.checkEntityType(EntityTypes.FEMALE));
    }

    @Test
    void createmaleCitizen() {
        Entity entity = NPCFactory.creatTestNPC("male");
        assertTrue(entity.checkEntityType(EntityTypes.NPC));
        assertTrue(entity.checkEntityType(EntityTypes.MALE));
    }

    @Test
    void createChild() {
        Entity entity = NPCFactory.creatTestNPC("child");
        assertTrue(entity.checkEntityType(EntityTypes.NPC));
        assertTrue(entity.checkEntityType(EntityTypes.CHILD));
    }

    @Test
    void createFriendlyCreature() {
        Entity entity = NPCFactory.creatTestNPC("creature");
        assertTrue(entity.checkEntityType(EntityTypes.NPC));
        assertTrue(entity.checkEntityType(EntityTypes.CREATURE));
    }

    @Test
    void createPlumberFriend() {
        Entity entity = NPCFactory.creatTestNPC("plumberfriend");
        assertTrue(entity.checkEntityType(EntityTypes.NPC));
        assertTrue(entity.checkEntityType(EntityTypes.PLUMBERFRIEND));
    }

    @Test
    void createHumanGuard() {
        Entity entity = NPCFactory.creatTestNPC("humanguard");
        assertTrue(entity.checkEntityType(EntityTypes.NPC));
        assertTrue(entity.checkEntityType(EntityTypes.HUMANGUARD));
    }

    @Test
    void createGuard() {
        Entity entity = NPCFactory.creatTestNPC("guard");
        assertTrue(entity.checkEntityType(EntityTypes.NPC));
        assertTrue(entity.checkEntityType(EntityTypes.GUARD));
    }
}