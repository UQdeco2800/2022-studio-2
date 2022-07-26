package com.deco2800.game.entities;

import static com.deco2800.game.entities.factories.NPCFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import com.badlogic.gdx.physics.box2d.Body;
import com.deco2800.game.ai.tasks.AITaskComponent;

import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.npc.GymBroAnimationController;
import com.deco2800.game.components.tasks.ChaseTask;
import com.deco2800.game.components.tasks.WanderTask;
import com.deco2800.game.entities.configs.*;
import com.deco2800.game.entities.factories.NPCFactory;
import com.deco2800.game.entities.factories.WeaponFactory;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.PhysicsUtils;

import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.utils.math.GridPoint2Utils;

import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.DebugRenderer;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@ExtendWith(GameExtension.class)
class EntityTest {

  @Mock
  ShapeRenderer shapeRenderer;
  @Mock
  Box2DDebugRenderer physicsRenderer;
  @Mock
  Matrix4 projMatrix;

  DebugRenderer debugRenderer;

  @BeforeEach
  void beforeEach() {
    debugRenderer = new DebugRenderer(physicsRenderer, shapeRenderer);

  }


  @Test
  void shouldSetAndGetPosition() {
    Entity entity = new Entity();
    Vector2 pos = new Vector2(5f, -5f);
    entity.setPosition(pos);
    assertEquals(pos, entity.getPosition());

    entity.setPosition(0f, 0f);
    assertEquals(Vector2.Zero, entity.getPosition());
  }

  @Test
  void shouldSetAndGetScale() {
    Entity entity = new Entity();
    Vector2 scale = new Vector2(2f, 3f);
    entity.setScale(scale);
    assertEquals(scale, entity.getScale());

    entity.setScale(0.1f, 0.2f);
    assertEquals(new Vector2(0.1f, 0.2f), entity.getScale());
  }

  @Test
  void shouldHaveCorrectCenter() {
    Entity entity = new Entity();
    entity.setPosition(0f, 0f);
    entity.setScale(2f, 2f);
    assertEquals(new Vector2(1f, 1f), entity.getCenterPosition());

    entity.setPosition(-5f, -10f);
    assertEquals(new Vector2(-4f, -9f), entity.getCenterPosition());
  }

  @Test
  void shouldUpdateComponents() {
    Entity entity = new Entity();
    Component component1 = spy(TestComponent1.class);
    Component component2 = spy(TestComponent2.class);
    entity.addComponent(component1);
    entity.addComponent(component2);
    entity.create();
    entity.update();

    verify(component1).update();
    verify(component2).update();

    entity.earlyUpdate();
    verify(component1).earlyUpdate();
    verify(component2).earlyUpdate();
  }

  @Test
  void shouldCreateComponents() {
    Entity entity = new Entity();
    Component component1 = spy(TestComponent1.class);
    Component component2 = spy(TestComponent2.class);
    entity.addComponent(component1);
    entity.addComponent(component2);
    entity.create();

    verify(component1).create();
    verify(component2).create();
    assertEquals(entity, component1.getEntity());
    assertEquals(entity, component2.getEntity());
  }

  @Test
  void shouldRejectSameClassComponents() {
    Entity entity = new Entity();
    Component component1 = new TestComponent1();
    Component component2 = new TestComponent1();
    entity.addComponent(component1);
    entity.addComponent(component2);
    assertEquals(component1, entity.getComponent(TestComponent1.class));
  }

  @Test
  void shouldRejectAfterCreate() {
    Entity entity = new Entity();
    entity.create();
    entity.addComponent(new TestComponent1());
    assertNull(entity.getComponent(TestComponent1.class));
  }

  @Test
  void shouldGetComponent() {
    Entity entity = new Entity();
    Component component1 = new TestComponent1();
    Component component2 = new TestComponent2();
    entity.addComponent(component1);
    entity.addComponent(component2);

    TestComponent1 gotComponent1 = entity.getComponent(TestComponent1.class);
    TestComponent2 gotComponent2 = entity.getComponent(TestComponent2.class);

    assertEquals(component1, gotComponent1);
    assertEquals(component2, gotComponent2);
  }

  @Test
  void shouldNotGetMissingComponent() {
    Entity entity = new Entity();
    TestComponent1 component = entity.getComponent(TestComponent1.class);
    assertNull(component);
  }

  @Test
  void shouldDisposeComponents() {
    Entity entity = new Entity();
    TestComponent1 component = spy(TestComponent1.class);
    entity.addComponent(component);
    entity.create();

    EntityService entityService = mock(EntityService.class);
    ServiceLocator.registerEntityService(entityService);

    entity.dispose();
    verify(component).dispose();
    verify(entityService).unregister(entity);
  }

  @Test
  void shouldHaveUniqueId() {
    Entity entity1 = new Entity();
    Entity entity2 = new Entity();

    assertNotEquals(entity1.getId(), entity2.getId());
  }

  @Test
  void shouldEqualWithId() {
    Entity entity1 = new Entity();
    Entity entity2 = mock(Entity.class);
    int id = entity1.getId();
    when(entity2.getId()).thenReturn(id);

    assertEquals(entity1, entity2);
  }

  @Test
  void shouldNotUpdateIfDisabled() {
    Entity entity = new Entity();
    TestComponent1 component = spy(TestComponent1.class);
    entity.addComponent(component);
    entity.create();

    entity.setEnabled(false);
    entity.earlyUpdate();
    entity.update();

    verify(component, times(0)).earlyUpdate();
    verify(component, times(0)).update();
  }
  @Test
  void havePotions() {

    Entity entity = new Entity();
    Vector2 scale = new Vector2(2f, 3f);
    entity.setScale(scale);
    assertEquals(scale, entity.getScale());

    entity.setScale(0.1f, 0.2f);
    assertEquals(new Vector2(0.1f, 0.2f), entity.getScale());


    if (entity.getScale() != null) {
      System.out.println("pass");
//      PhysicsUtils.setScaledCollider(entity, 0.5f, 0.2f);

    } else {
      System.out.println("fail");
    }
  }

  @Test
  void potionsHeights() {

    Entity entity = new Entity();
    Vector2 scale = new Vector2(2f, 3f);
    entity.setScale(scale);
    assertEquals(scale, entity.getScale());

    entity.setScale(0.5f, 0.2f);
    assertEquals(new Vector2(0.5f, 0.2f), entity.getScale());

    System.out.println(entity.getScale().x);
    System.out.println(entity.getScale().y);
  }

@Test
void checkIsDead() {
    Entity entity = new Entity();
    entity.addComponent(new CombatStatsComponent(90, 5, 100, 100));
    assertFalse(entity.isDead());
    entity.getComponent(CombatStatsComponent.class).setHealth(0);
    assertTrue(entity.isDead());
}



  static class TestComponent1 extends Component {}

  static class TestComponent2 extends Component {}
}
