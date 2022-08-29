package com.deco2800.game.components.player;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.settingsmenu.SettingsMenuDisplay;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.EntityTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Action component for interacting with the player. Player events should be initialised in create()
 * and when triggered should call methods within this class.
 */
public class PlayerActions extends Component {

  private static final Logger logger = LoggerFactory.getLogger(SettingsMenuDisplay.class);
  private static final Vector2 MAX_SPEED = new Vector2(3f, 3f); // Metres per second
  private static final Vector2 DASH_SPEED = new Vector2(6f, 6f); // Metres per second
  private static final long DASH_LENGTH = 350; // In MilliSec (1000millsec = 1sec)
  private static final float DASH_MOVEMENT_RESTRICTION = 0.8f;
  private static final int TELEPORT_LENGTH = 4;

  private Vector2 maxWalkSpeed = new Vector2(3f, 3f); // Metres per second
  private PhysicsComponent physicsComponent;
  private PlayerSkillComponent skillManager;
  private CombatStatsComponent combatStatsComponent;
  private PlayerModifier playerModifier;
  private Vector2 walkDirection = Vector2.Zero.cpy();
  private boolean inventoryIsOpened = false;
  private boolean miniMapOpen = false;
  private long dashStart;
  private long dashEnd;
  private int stamina= 100;
  private int maxStamina =100;
  private int maxMana=100;
  private int mana=100;

  private boolean resting = false;
  private long restStart=0;
  private long restEnd;


  @Override
  public void create() {
    physicsComponent = entity.getComponent(PhysicsComponent.class);

    combatStatsComponent = entity.getComponent(CombatStatsComponent.class);
    this.maxStamina = combatStatsComponent.getMaxStamina();
    this.stamina = combatStatsComponent.getStamina();
    this.maxMana = combatStatsComponent.getMaxMana();
    this.mana = combatStatsComponent.getMana();

    playerModifier = entity.getComponent(PlayerModifier.class);
    entity.getEvents().addListener("walk", this::walk);
    entity.getEvents().addListener("walkStop", this::stopWalking);
    entity.getEvents().addListener("attack", this::attack);
    entity.getEvents().addListener("toggleInventory", this::toggleInventory);
    entity.getEvents().addListener("kill switch", this::killEnemy);
    entity.getEvents().addListener("toggleMinimap", this::toggleMinimap);


    // Skills and Dash initialisation
    skillManager = new PlayerSkillComponent(entity);
    skillManager.setSkill("teleport", entity, this);
    entity.getEvents().addListener("dash", this::dash);
  }

  @Override
  public void update() {
    this.maxStamina = combatStatsComponent.getMaxStamina();
    this.stamina = combatStatsComponent.getStamina();
    this.maxMana = combatStatsComponent.getMaxMana();
    this.mana = combatStatsComponent.getMana();

    checkrest();
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
      showInventory();
    } else {
      System.out.println("Closing inventory");
      // Close code
    }
  }

  private void showInventory() {
    JFrame j = new JFrame();
    j.setUndecorated(true);
    j.setLocationRelativeTo(null);
    j.setSize(400, 400);
    j.setResizable(false);
    j.getContentPane().setLayout(null);
    JPanel panel = new ImagePanel();
    panel.setBounds(0, 0, 400, 400);
    j.getContentPane().add(panel);
    j.setVisible(true);
  }

  class ImagePanel extends JPanel {
    public void paint(Graphics g) {
      super.paint(g);
      ImageIcon icon = new ImageIcon("images/Inventory/pixil-frame (x10).png");
      g.drawImage(icon.getImage(), 0, 0, 400, 400, this);
    }
  }

  public void killEnemy(){
    for (Entity enemy : ServiceLocator.getEntityService().getEntityList()) {
      if (enemy.checkEntityType(EntityTypes.ENEMY)) {
        enemy.flagDead();
      }
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
   * Pressing the 'I' button toggles the Minimap window being open.
   */
  private void toggleMinimap(){
    miniMapOpen = !miniMapOpen;

    if (miniMapOpen) {
      logger.trace("minimap open");
    } else {
      logger.trace("minimap closed");
    }
    //logger.debug()
    return;
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
   *  Makes the player dash. Registers call of the dash function to the skill manager component.
   */
  void dash() {
    if(stamina >=20){
      skillManager.startDash(this.walkDirection.cpy());
      entity.getEvents().trigger("decreaseStamina", -20);
    }
    playerModifier.createModifier(PlayerModifier.STAMINAREGEN, 3, true, 2000);
  }

  /**
   * Gets a reference to the skill subcomponent of playeractions.
   * This reference should be used sparingly as a way for external functionality to directly
   * interact with skill states, and should avoid directly inducing any skill start fuctions
   * using this reference. In future sprints
   * skill start functions will not be able to called externally.
   * @return the player skill component of player actions.
   */
  public PlayerSkillComponent getSkillComponent() {
    return this.skillManager;
  }

  /**
   * It is as a timer that check whether it has passed 1 second. After each second, rest() would be
   * called to regenerate stamina
   */
  void checkrest() {
    if (System.currentTimeMillis() > this.restEnd) {
      rest();
      this.restStart = 0;
    }
    if (this.restStart == 0) {
      this.restStart = System.currentTimeMillis();
      this.restEnd = this.restStart + 1000;

    }
  }

  /**
   * The player's stamina would regenerate as the rate of staminaRegenerationRate same as mana.
   */
  void rest() {
    if (stamina < maxStamina) {
      entity.getEvents().trigger("increaseStamina",
              combatStatsComponent.getStaminaRegenerationRate());

    }
    if (mana < maxMana) {
      entity.getEvents().trigger("increaseMana",
              combatStatsComponent.getManaRegenerationRate());

    }
  }

  /**
   * Makes the player teleport. Registers call of the teleport function to the skill manager component.
   */
  void teleport() {
    if (mana>=40) {
      entity.getEvents().trigger("decreaseMana", -40);
      entity.getEvents().trigger("teleportAnimation");
      skillManager.startTeleport();
    }
  }

  Vector2 getWalkDirection() {
    return this.walkDirection;
  }
}
