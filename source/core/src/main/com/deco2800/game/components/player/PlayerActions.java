package com.deco2800.game.components.player;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.deco2800.game.components.Component;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.services.ServiceLocator;
import com.badlogic.gdx.utils.Timer;

/**
 * Action component for interacting with the player. Player events should be initialised in create()
 * and when triggered should call methods within this class.
 */
public class PlayerActions extends Component {
  private static final Vector2 MAX_SPEED = new Vector2(3f, 3f); // Metres per second
  private static final Vector2 DASH_SPEED = new Vector2(6f, 6f); // Metres per second
  private static final long DASH_LENGTH = 350; // In MilliSec (1000millsec = 1sec)
  private static final float DASH_MOVEMENT_RESTRICTION = 0.8f;

  private PhysicsComponent physicsComponent;
  private Vector2 walkDirection = Vector2.Zero.cpy();
  private Vector2 dashDirection = Vector2.Zero.cpy();
  private boolean moving = false;
  private boolean dashing = false;
  private long dashStart;
  private long dashEnd;
  private boolean inventoryIsOpened = false;

  @Override
  public void create() {
    physicsComponent = entity.getComponent(PhysicsComponent.class);
    entity.getEvents().addListener("walk", this::walk);
    entity.getEvents().addListener("walkStop", this::stopWalking);
    entity.getEvents().addListener("attack", this::attack);
    entity.getEvents().addListener("dash", this::dash);
    entity.getEvents().addListener("toggleInventory", this::toggleInventory);
  }

  @Override
  public void update() {

    updateSpeed();
  }

  private void toggleInventory(){
    if(inventoryIsOpened) {
      inventoryIsOpened = false;
      System.out.println("Opening inventory");

      // Open code

    } else {
      inventoryIsOpened = true;
      System.out.println("Closing inventory");

      // Close code
    }
  }

  private void updateSpeed() {
    Body body = physicsComponent.getBody();
    Vector2 velocity = body.getLinearVelocity();
    Vector2 walkVelocity = walkDirection.cpy().scl(MAX_SPEED);
    Vector2 dashVelocity;
    Vector2 desiredVelocity;

    // If the character is dashing, and dash length isn't over
    if (this.dashing && System.currentTimeMillis() < this.dashEnd) {

      dashVelocity = dashDirection.cpy().scl(DASH_SPEED); // Dash in direction of movement at start of dash
      // Allow players to move side-to-side during dash
      desiredVelocity = new Vector2(walkVelocity.x * DASH_MOVEMENT_RESTRICTION + dashVelocity.x,
              walkVelocity.y * DASH_MOVEMENT_RESTRICTION + dashVelocity.y);
    } else {
      desiredVelocity = walkDirection.cpy().scl(MAX_SPEED); // Regular walk
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
    moving = true;
  }

  /**
   * Stops the player from walking.
   */
  void stopWalking() {
    this.walkDirection = Vector2.Zero.cpy();
    updateSpeed();
    moving = false;
  }

  /**
   * Makes the player attack.
   */
  void attack() {
    Sound attackSound = ServiceLocator.getResourceService().getAsset("sounds/Impact4.ogg", Sound.class);
    attackSound.play();
  }

  /**
   * Makes the player dash. Logs the start dash time and registers movement increase to updateSpeed().
   */
  void dash() {
    Sound attackSound = ServiceLocator.getResourceService().getAsset("sounds/Impact4.ogg", Sound.class);
    attackSound.play();
    this.dashDirection = this.walkDirection.cpy();
    this.dashing = true;
    this.dashStart = System.currentTimeMillis();
    this.dashEnd = this.dashStart + DASH_LENGTH;
  }
}
