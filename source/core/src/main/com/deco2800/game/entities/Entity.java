package com.deco2800.game.entities;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.deco2800.game.components.CameraComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.ComponentType;
import com.deco2800.game.components.npc.EnemyExperienceComponent;
import com.deco2800.game.components.npc.GymBroAnimationController;
import com.deco2800.game.components.player.*;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.events.EventHandler;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/**
 * Core entity class. Entities exist in the game and are updated each frame. All entities have a
 * position and scale, but have no default behaviour. Components should be added to an entity to
 * give it specific behaviour. This class should not be inherited or modified directly.
 *
 * <p>Example use:
 *
 * <pre>
 * Entity player = new Entity()
 *   .addComponent(new RenderComponent())
 *   .addComponent(new PlayerControllerComponent());
 * ServiceLocator.getEntityService().register(player);
 * </pre>
 */
public class Entity {
  private static final Logger logger = LoggerFactory.getLogger(Entity.class);
  private static int nextId = 0;
  private static final String EVT_NAME_POS = "setPosition";

  private final int id;
  private final IntMap<Component> components;
  private final EventHandler eventHandler;
  private boolean enabled = true;
  private boolean created = false;
  private Vector2 position = Vector2.Zero.cpy();
  private GridPoint2 gridPosition;
  private Vector2 scale = new Vector2(1, 1);
  private Array<Component> createdComponents;
  private boolean isDead = false;
  private boolean playerWin = false;
  private List<EntityTypes> entityType;
  public Entity() {
    id = nextId;
    nextId++;
    entityType = new ArrayList<>();
    components = new IntMap<>(4);
    eventHandler = new EventHandler();
  }

  /**
   * Enable or disable an entity. Disabled entities do not run update() or earlyUpdate() on their
   * components, but can still be disposed.
   *
   * @param enabled true for enable, false for disable.
   */
  public void setEnabled(boolean enabled) {
    logger.debug("Setting enabled={} on entity {}", enabled, this);
    this.enabled = enabled;
  }

  /**
   * Get the entity's game grid position.
   *
   * @return position
   */
  public GridPoint2 getGridPosition() {
    return gridPosition; // Cpy gives us pass-by-value to prevent bugs
  }

  /**
   * Get the entity's game position.
   *
   * @return position
   */
  public Vector2 getPosition() {
    return position.cpy(); // Cpy gives us pass-by-value to prevent bugs
  }

  /**
   * Set the entity's game grid position.
   *
   * @return position
   */
  public void setGridPosition(GridPoint2 position) {
    this.gridPosition = position;
  }

  /**
   * Set the entity's game position.
   *
   * @param position new position.
   */
  public void setPosition(Vector2 position) {
    this.position = position.cpy();
    getEvents().trigger(EVT_NAME_POS, position.cpy());
  }

  /**
   * Set the entity's game position.
   *
   * @param x new x position
   * @param y new y position
   */
  public void setPosition(float x, float y) {
    this.position.x = x;
    this.position.y = y;
    getEvents().trigger(EVT_NAME_POS, position.cpy());
  }

  /**
   * Set the entity's game position and optionally notifies listeners.
   *
   * @param position new position.
   * @param notify true to notify (default), false otherwise
   */
  public void setPosition(Vector2 position, boolean notify) {
    this.position = position;
    if (notify) {
      getEvents().trigger(EVT_NAME_POS, position);
    }
  }

  /**
   * Get the entity's scale. Used for rendering and physics bounding box calculations.
   *
   * @return Scale in x and y directions. 1 = 1 metre.
   */
  public Vector2 getScale() {
    return scale.cpy(); // Cpy gives us pass-by-value to prevent bugs
  }

  /**
   * Set the entity's scale.
   *
   * @param scale new scale in metres
   */
  public void setScale(Vector2 scale) {
    this.scale = scale.cpy();
  }

  /**
   * Set the entity's scale.
   *
   * @param x width in metres
   * @param y height in metres
   */
  public void setScale(float x, float y) {
    this.scale.x = x;
    this.scale.y = y;
  }

  /**
   * Set the entity's width and scale the height to maintain aspect ratio.
   *
   * @param x width in metres
   */
  public void scaleWidth(float x) {
    this.scale.y = this.scale.y / this.scale.x * x;
    this.scale.x = x;
  }

  /**
   * Set the entity's height and scale the width to maintain aspect ratio.
   *
   * @param y height in metres
   */
  public void scaleHeight(float y) {
    this.scale.x = this.scale.x / this.scale.y * y;
    this.scale.y = y;
  }

  /**
   * Get the entity's center position
   *
   * @return center position
   */
  public Vector2 getCenterPosition() {
    return getPosition().mulAdd(getScale(), 0.5f);
  }

  /**
   * Get a component of type T on the entity.
   *
   * @param type The component class, e.g. RenderComponent.class
   * @param <T> The component type, e.g. RenderComponent
   * @return The entity component, or null if nonexistent.
   */
  @SuppressWarnings("unchecked")
  public <T extends Component> T getComponent(Class<T> type) {
    ComponentType componentType = ComponentType.getFrom(type);
    return (T) components.get(componentType.getId());
  }

  /**
   * Add a component to the entity. Can only be called before the entity is registered in the world.
   *
   * @param component The component to add. Only one component of a type can be added to an entity.
   * @return Itself
   */
  public Entity addComponent(Component component) {
    if (created) {
      logger.error(
          "Adding {} to {} after creation is not supported and will be ignored", component, this);
      return this;
    }
    ComponentType componentType = ComponentType.getFrom(component.getClass());
    if (components.containsKey(componentType.getId())) {
      logger.error(
          "Attempted to add multiple components of class {} to {}. Only one component of a class "
              + "can be added to an entity, this will be ignored.",
          component,
          this);
      return this;
    }
    components.put(componentType.getId(), component);
    component.setEntity(this);

    return this;
  }

  /**
   * Set the entity to the specific type defined in EntityTypes class
   * @param type
   */
  public void setEntityType(EntityTypes type) {
    this.entityType.add(type);
  }

  public boolean checkEntityType(EntityTypes type) {
    return entityType.contains(type);
  }

  public List<EntityTypes> getEntityTypes() {
    return this.entityType;
  }


  /** Dispose of the entity. This will dispose of all components on this entity. */
  public void dispose() {
    for (Component component : createdComponents) {
      if (!(component instanceof AnimationRenderComponent)) {
        if (component instanceof EnemyExperienceComponent) {
          ((EnemyExperienceComponent) component).triggerExperienceGain();
        }
        component.dispose();
      } //this prevents the other entities using the same animation from having their atlases disposed (black box)
    }
    this.flagDead();
    ServiceLocator.getEntityService().unregister(this);
  }

  /**
   * Create the entity and start running. This is called when the entity is registered in the world,
   * and should not be called manually.
   */
  public void create() {
    if (created) {
      logger.error(
          "{} was created twice. Entity should only be registered with the entity service once.",
          this);
      return;
    }
    createdComponents = components.values().toArray();
    for (Component component : createdComponents) {
      component.create();
    }
    created = true;
  }

  /**
   * Perform an early update on all components. This is called by the entity service and should not
   * be called manually.
   */
  public void earlyUpdate() {
    if (!enabled) {
      return;
    }
    boolean timeStopped = EntityService.isTimeStopped();
    for (Component component : createdComponents) {
      if (!timeStopped) {

        component.triggerEarlyUpdate();
      } else {

        if (component instanceof CameraComponent ||
            component instanceof CombatStatsComponent ||
            component instanceof PlayerActions ||
            component instanceof KeyboardPlayerInputComponent ||
            component instanceof PlayerSkillProjectileComponent ||
            component instanceof PlayerSkillAnimationController ||
            component instanceof PlayerKPAnimationController ||
            component instanceof PlayerCombatAnimationController ||
            component instanceof PlayerTouchAttackComponent ||
            component instanceof PlayerModifier ||
            component instanceof PhysicsComponent ) {

          component.triggerEarlyUpdate();
        }
      }
    }
  }

  /**
   * Perform an update on all components. This is called by the entity service and should not be
   * called manually.
   */
  public void update() {
    if (!enabled) {
      return;
    }
    if (isDead) {
      if (!checkEntityType(EntityTypes.ENEMY)) {
        dispose();
      }
    }
    if (playerWin) {
      dispose();
    }
    boolean timeStopped = EntityService.isTimeStopped();
    for (int i = 0; i < createdComponents.size; ++i) {
      Component component = createdComponents.get(i);
      if (!timeStopped) {

        component.triggerUpdate();

      } else {
        if (component instanceof CameraComponent ||
                component instanceof CombatStatsComponent ||
                component instanceof PlayerActions ||
                component instanceof KeyboardPlayerInputComponent ||
                component instanceof PlayerSkillProjectileComponent ||
                component instanceof PlayerSkillAnimationController ||
                component instanceof PlayerKPAnimationController ||
                component instanceof PlayerCombatAnimationController ||
                component instanceof PlayerTouchAttackComponent ||
                component instanceof PlayerModifier ||
                component instanceof PhysicsComponent) {

          component.triggerUpdate();
        }
      }
    }
  }

  /**
   * Pauses all animations of registered entities. Can selectively
   * pause the player entity and player related entities, on the following conditions of
   * those entities:
   * To Pause player entity - must have KeyBoardPlayerInput Component
   * To Pause skill animator entity - must have PlayerSkillAnimationController
   * To Pause combat animator entity - must have PlayerCombatAnimationController
   * To Pause player projectile animator entity - must have PlayerSkillProjectileComponent
   * @param pausePlayer true if the player and player related entities should be paused
   *                    false if the player and player related entities should remain active
   *                    while pausing other animations
   */
  public void togglePauseAnimations(boolean pausePlayer) {
    if (!pausePlayer) {
      for (Component component : createdComponents) {
        if (component instanceof KeyboardPlayerInputComponent ||
               component instanceof PlayerSkillAnimationController ||
               component instanceof PlayerSkillProjectileComponent ||
               component instanceof PlayerCombatAnimationController ||
               component instanceof PlayerTouchAttackComponent ||
               component instanceof PlayerKPAnimationController
                ) {
          return;
        }
      }
    }
    for (Component component : createdComponents) {
      if (component instanceof PlayerCombatAnimationController) {
        return;
      }
    }
    for (Component component : createdComponents) {
      if (component instanceof AnimationRenderComponent) {
        ((AnimationRenderComponent) component).togglePauseAnimation();
      }
    }
  }

  /**
   * This entity's unique ID. Used for equality checks
   *
   * @return unique ID
   */
  public int getId() {
    return id;
  }


  public void flagDead(){
    isDead = true;
  }

  /**
   * Updates the playerWin variable to true, used to flag a win
   */
  public void flagWin() {
    playerWin = true;
  }

  /**
   * A method that returns true if dead and false if not
   * @return true if dead, false if not
   */
  public boolean isDead() {
    if (getComponent(CombatStatsComponent.class).getHealth() <= 0) {
      isDead = true;
    }
    return isDead;
  }
  public EventHandler getEvents() {
    return eventHandler;
  }

  /**
   * Check if this entity equalsOther another object
   * @param obj - the object to compare this entity to
   *
   * @return true if equal, false if not
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj.getClass() != this.getClass()) {
      return false;
    }
    return (obj instanceof Entity entity && ((Entity) obj).getId() == this.getId());
  }

  /**
   * Get the hashcode of this entity.
   * @return integer representing hashcode of this entity
   */
  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Convert this entity into string format
   * @return String representing the string format of this entity
   */
  @Override
  public String toString() {
    return String.format("Entity{id=%d}", id);
  }
}