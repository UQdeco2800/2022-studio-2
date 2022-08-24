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
  private static final Vector2 DASH_SPEED = new Vector2(6f, 6f); // Metres per second
  private static final long DASH_LENGTH = 350; // In MilliSec (1000millsec = 1sec)
  private static final float DASH_MOVEMENT_RESTRICTION = 0.8f;
  private static final int TELEPORT_LENGTH = 4;

  private static final Logger logger = LoggerFactory.getLogger(PlayerActions.class);
  private static Vector2 maxSpeed = new Vector2(3f, 3f); // Metres per second
  private PhysicsComponent physicsComponent;
  private PlayerModifier playerModifier;
  private Vector2 walkDirection = Vector2.Zero.cpy();
  private Vector2 dashDirection = Vector2.Zero.cpy();
  private boolean moving = false;
  private boolean dashing = false;
  private boolean inventoryIsOpened = false;
  private long dashStart;
  private long dashEnd;

  /**
   * Init function of the class that sets player movement speed.
   *
   * @param moveSpeed of the player character
   */
  public PlayerActions(float moveSpeed) {
    maxSpeed = new Vector2(moveSpeed, moveSpeed);
  }

  @Override
  public void create() {
    physicsComponent = entity.getComponent(PhysicsComponent.class);
    entity.getEvents().addListener("walk", this::walk);
    entity.getEvents().addListener("walkStop", this::stopWalking);
    entity.getEvents().addListener("attack", this::attack);
    entity.getEvents().addListener("dash", this::dash);
    entity.getEvents().addListener("teleport", this::teleport);
    entity.getEvents().addListener("toggleInventory", this::toggleInventory);
  }

  @Override
  public void update() {
    updateSpeed();
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

    Vector2 walkVelocity = walkDirection.cpy().scl(maxSpeed);
    Vector2 dashVelocity;
    Vector2 desiredVelocity;

    // If the character is dashing, and dash length isn't over
    if (this.dashing && System.currentTimeMillis() < this.dashEnd) {

      dashVelocity = dashDirection.cpy().scl(DASH_SPEED); // Dash in direction of movement at start of dash
      // Allow players to move side-to-side during dash
      desiredVelocity = new Vector2(walkVelocity.x * DASH_MOVEMENT_RESTRICTION + dashVelocity.x,
              walkVelocity.y * DASH_MOVEMENT_RESTRICTION + dashVelocity.y);
    } else {
      desiredVelocity = walkDirection.cpy().scl(maxSpeed); // Regular walk
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
   * Public function to set new max speed.
   *
   * @param newSpeed of the player character
   */
  public void updateMaxSpeed(float newSpeed) {
    maxSpeed = new Vector2(newSpeed, newSpeed);
  }

  /**
   * Return the max speed of the player actions.
   */
  public float getMaxSpeed() { return maxSpeed.x; }

   /** Makes the player dash. Logs the start dash time and registers movement increase to updateSpeed().
   */
  void dash() {
    Sound attackSound = ServiceLocator.getResourceService().getAsset("sounds/Impact4.ogg", Sound.class);
    attackSound.play();
    this.dashDirection = this.walkDirection.cpy();
    this.dashing = true;
    this.dashStart = System.currentTimeMillis();
    this.dashEnd = this.dashStart + DASH_LENGTH;
  }

  /**
   * Teleports the player a set distance in the currently facing direction.
   */
  void teleport() {
    float teleportPositionX = entity.getPosition().x + walkDirection.x * TELEPORT_LENGTH;
    float teleportPositionY = entity.getPosition().y + walkDirection.y * TELEPORT_LENGTH;

    // Check if teleport is out of map bounds
    if (teleportPositionX < -0.08)
      teleportPositionX = -0.08f;
    if (teleportPositionY < 0.11)
      teleportPositionY = 0.11f;
    if (teleportPositionX > 14.18)
      teleportPositionX = 14.18f;
    if (teleportPositionY > 14.68)
      teleportPositionY = 14.68f;

    entity.setPosition(teleportPositionX, teleportPositionY);
  }
}
