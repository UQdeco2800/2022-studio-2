package com.deco2800.game.components.player;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.deco2800.game.components.Component;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Action component for interacting with the player. Player events should be initialised in create()
 * and when triggered should call methods within this class.
 */
public class PlayerActions extends Component {
  private Vector2 maxWalkSpeed = new Vector2(3f, 3f); // Metres per second
  private PhysicsComponent physicsComponent;
  private PlayerSkillComponent skillManager;

  private static final Logger logger = LoggerFactory.getLogger(PlayerActions.class);

  private PlayerModifier playerModifier;
  private Vector2 walkDirection = Vector2.Zero.cpy();
  private boolean inventoryIsOpened = false;

  @Override
  public void create() {
    physicsComponent = entity.getComponent(PhysicsComponent.class);
    playerModifier = entity.getComponent(PlayerModifier.class);
    entity.getEvents().addListener("walk", this::walk);
    entity.getEvents().addListener("walkStop", this::stopWalking);
    entity.getEvents().addListener("attack", this::attack);
    entity.getEvents().addListener("toggleInventory", this::toggleInventory);

    // Skills and Dash initialisation
    skillManager = new PlayerSkillComponent(entity);
    skillManager.setSkill("teleport", entity, this);
    entity.getEvents().addListener("dash", this::dash);
  }

  @Override
  public void update() {
    updateSpeed();
    this.skillManager.update();
    this.playerModifier.update();
  }

  private void toggleInventory(){
    inventoryIsOpened = !inventoryIsOpened;
    //Code for debugging
    if(inventoryIsOpened) {
      System.out.println("Opening inventory");
      // Open code
    } else {
      System.out.println("Closing inventory");
      // Close code
    }
  }

  private void updateSpeed() {
    Body body = physicsComponent.getBody();
    Vector2 velocity = body.getLinearVelocity();
    Vector2 walkVelocity = walkDirection.cpy().scl(maxWalkSpeed);
    Vector2 desiredVelocity;


    if (skillManager.movementIsModified()) {
      // If the character's movement is modified by a skill
      desiredVelocity = skillManager.getModifiedMovement(walkVelocity);
    } else {
      desiredVelocity = walkVelocity; // Regular walk
    }

    // impulse = (desiredVel - currentVel) * mass
    Vector2 impulse = desiredVelocity.sub(velocity).scl(body.getMass());
    body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
  }

  /**
   * Moves the player towards a given direction.
   *
   * @param direction direction to move in
   */
  void walk(Vector2 direction) {
    this.walkDirection = direction;
  }

  /**
   * Stops the player from walking.
   */
  void stopWalking() {
    this.walkDirection = Vector2.Zero.cpy();
    updateSpeed();
  }

  /**
   * Makes the player attack.
   */
  void attack() {
    Sound attackSound = ServiceLocator.getResourceService().getAsset("sounds/Impact4.ogg", Sound.class);
    attackSound.play();
    playerModifier.createModifier("moveSpeed", 2, true, 350);
  }

  /**
   * Public function to set new max speed.
   *
   * @param newSpeed of the player character
   */
  public void updateMaxSpeed(float newSpeed) {
    maxWalkSpeed = new Vector2(newSpeed, newSpeed);
  }

  /**
   * Return the scalar max speed of the player.
   */
  public float getMaxSpeed() {
    return maxWalkSpeed.x;
  }

  /**
   *  Makes the player dash. Logs the start dash time and registers movement increase to updateSpeed().
   */
  void dash() {
    skillManager.startDash(this.walkDirection.cpy());
  }

  /**
   * Teleports the player a set distance in the currently facing direction.
   */
  void teleport() {
    skillManager.startTeleport();
  }

  Vector2 getWalkDirection() {
    return this.walkDirection;
  }
}
