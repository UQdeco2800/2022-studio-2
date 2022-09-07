package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.settingsmenu.SettingsMenuDisplay;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.EntityTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Action component for interacting with the player. Player events should be initialised in create()
 * and when triggered should call methods within this class.
 */
public class PlayerActions extends Component {

  private static final Logger logger = LoggerFactory.getLogger(SettingsMenuDisplay.class);
  private Entity skillAnimator;

  private Vector2 maxWalkSpeed = new Vector2(3f, 3f); // Metres per second
  private PhysicsComponent physicsComponent;
  private PlayerSkillComponent skillManager;
  private CombatStatsComponent combatStatsComponent;
  private PlayerModifier playerModifier;
  private Vector2 walkDirection = Vector2.Zero.cpy();
  private boolean inventoryIsOpened = false;
  private boolean miniMapOpen = false;
  private int stamina= 100;
  private int maxStamina =100;
  private int maxMana=100;
  private int mana=100;
  private HitboxComponent hit;

  private boolean resting = false;
  private long restStart=0;
  private long restEnd;

  private boolean enemyCollide = false;
  private Entity enemyToKill;
  private int enemyHealth;
  private int killID;

  Map<String, Long> skillCooldowns = new HashMap<String, Long>();

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
    entity.getEvents().addListener("collisionStart", this::collisionAttack);
    entity.getEvents().addListener("toggleInventory", this::toggleInventory);
    entity.getEvents().addListener("kill switch", this::killEnemy);
    entity.getEvents().addListener("toggleMinimap", this::toggleMinimap);


    // Skills and Dash initialisation
    String startingSkill = "dodge";
    skillManager = new PlayerSkillComponent(entity);
    skillManager.setSkill(startingSkill, entity, this);
    entity.getEvents().addListener("dash", this::dash);

    skillCooldowns.put(startingSkill, 0L);
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
      // Open code
    } else {
      // Close code
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
   * Pressing the 'M' button toggles the Minimap window being open.
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

  void collisionAttack(Fixture player, Fixture other) {

    ArrayList<Entity> enemies = new ArrayList<>();

    for (Entity enemy : ServiceLocator.getEntityService().getEntityList()) {
      if (enemy.checkEntityType(EntityTypes.ENEMY)) {
        enemies.add(enemy);
      }
    }

    for(Entity i : enemies) {
      Fixture fix = i.getComponent(ColliderComponent.class).getFixture();

      if (other == fix) {
        enemyCollide = true;
        enemyToKill = i;
        break;
      } else {
        enemyCollide = false;
      }
    }
  }

  /**
   * Makes the player attack.
   */
  void attack() {
    Sound attackSound = ServiceLocator.getResourceService().getAsset("sounds/Impact4.ogg", Sound.class);
    attackSound.play();
    int damage = -100; // call the weapons damage, if equipped
    //playerModifier.createModifier("moveSpeed", 2, true, 350);


    if (enemyCollide == true) {

      for (Entity enemy : ServiceLocator.getEntityService().getEntityList()) {
        //if (enemy.checkEntityType(EntityTypes.ENEMY)) {
        if (enemy.equals(enemyToKill)) {
          // load in current health
          enemyHealth = enemy.getComponent(CombatStatsComponent.class).getHealth();
          // to avoid changing weapon stats should probably just create a removeHealth function
          enemy.getComponent(CombatStatsComponent.class).addHealth(damage);

          if (enemyHealth == 0) {
            enemyToKill.setScale(0f,0f);
            ServiceLocator.getEntityService().unregister(enemy);
            //enemy.flagDead();
          }
          // anti-snipe
          enemyCollide = false;
          enemyToKill = null;
          break;
        }
      }
    }

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
      skillAnimator.getEvents().trigger("teleportAnimation");
      skillManager.startTeleport();
    }
  }

  void dodge() {
    skillAnimator.getEvents().trigger("dodgeAnimation");
    skillManager.startDodge(this.walkDirection.cpy());
  }

  Vector2 getWalkDirection() {
    return this.walkDirection;
  }

  /**
   * Checks if the cooldown period is over for the given skill and updates cooldown map.
   * @param skill the skill to check
   * @param cooldown the cooldown period (in milliseconds)
   *
   * @return true if cooldown period is over, false otherwise
   */
  public boolean cooldownFinished(String skill, long cooldown) {
    if (skillCooldowns.get(skill) == null) {
      return false;
    }
    if (System.currentTimeMillis() - skillCooldowns.get(skill) > cooldown) {
      skillCooldowns.replace(skill, System.currentTimeMillis());
      return true;
    } else {
      return false;
    }
  }
  /**
  * Sets an existing skill cooldown to a new cooldown.
  * @param skill the skill to check
  * @param cooldown the cooldown period (in milliseconds)
  *
  */
  public void setSkillCooldown(String skill, long cooldown) {
    if (skillCooldowns.get(skill) != null) {
      skillCooldowns.replace(skill, System.currentTimeMillis());
    }
  }

  /**
   * Sets the skill animator for this actions component and passes it to the skill
   * component so the skill component can alter the skill animation state.
   * @param skillAnimator the skill animator entity which has subcomponents
   *                      PlayerSkillAnimationController and AnimationRenderer
   */
  public void setSkillAnimator(Entity skillAnimator) {
    this.skillAnimator = skillAnimator;
    this.skillManager.setSkillAnimator(skillAnimator);
  }
}
