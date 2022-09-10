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
  private Entity combatAnimator;

  private Vector2 maxWalkSpeed = new Vector2(3f, 3f); // Metres per second
  private PhysicsComponent physicsComponent;
  private PlayerSkillComponent skillManager;
  private CombatStatsComponent combatStatsComponent;
  private PlayerModifier playerModifier;
  private Vector2 walkDirection = Vector2.Zero.cpy();
  private boolean miniMapOpen = false;
  private int stamina= 100;
  private int maxStamina =100;
  private int maxMana=100;
  private int mana=100;
  private HitboxComponent hit;

  private boolean resting = false;
  private long restStart=0;
  private long restEnd;

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
    entity.getEvents().addListener("toggleInventory", this::toggleInventory);
//    entity.getEvents().addListener("consumePotionSlot1", this::consumePotionSlot1);
//    entity.getEvents().addListener("consumePotionSlot2", this::consumePotionSlot2);
//    entity.getEvents().addListener("consumePotionSlot3", this::consumePotionSlot3);
    entity.getEvents().addListener("kill switch", this::killEnemy);
    entity.getEvents().addListener("toggleMinimap", this::toggleMinimap);
    entity.getEvents().addListener("attack", this::attackAnimation);
    entity.getEvents().addListener("attack2", this::attackAnimation2);


    // Skills and Dash initialisation
    String startingSkill = "block";
    skillManager = new PlayerSkillComponent(entity);
    skillManager.setSkill(1, startingSkill, entity,this);
    skillManager.setSkill(2, "dodge", entity, this);
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

  /**
   * Pressing the 'I' button toggles the inventory menu UI opening/closing.
   */
  public void toggleInventory(){
    entity.getComponent(InventoryComponent.class).toggleInventoryDisplay();
  }

  /**
   * Pressing the '1' button toggles the inventory menu UI opening/closing.
   */
  public void consumePotionSlot1(){
    entity.getComponent(InventoryComponent.class).consumePotion(1);
  }

  /**
   * Pressing the '2' button toggles the inventory menu UI opening/closing.
   */
  public void consumePotionSlot2(){
    entity.getComponent(InventoryComponent.class).consumePotion(2);
  }

  /**
   * Pressing the '3' button toggles the inventory menu UI opening/closing.
   */
  public void consumePotionSlot3(){
    entity.getComponent(InventoryComponent.class).consumePotion(3);
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

  /**
   * Applies bleed to the player's next attack. Registers call of the bleed function to the skill manager component.
   */
  void bleed() {
    if (mana>=10 && cooldownFinished("bleed", 7000)) {
      entity.getEvents().trigger("decreaseMana", -10);
      skillManager.startBleed();
    }
  }

  /**
   * Applies root to the player's next attack. Registers call of the root function to the skill manager component.
   */
  void root() {
    if (mana>=10 && cooldownFinished("root", 5000)) {
      entity.getEvents().trigger("decreaseMana", -10);
      skillManager.startRoot();
    }
  }

  /**
   * Makes the player dodge. Registers call of the dodge function to the skill manager component.
   */
  void dodge() {
    skillAnimator.getEvents().trigger("dodgeAnimation");
    skillManager.startDodge(this.walkDirection.cpy());
  }

  /**
   * Makes the player block. Registers call of the block function to the skill manager component.
   */
  void block() {
    skillAnimator.getEvents().trigger("blockAnimation");
    skillManager.startBlock();
  }

  public Vector2 getWalkDirection() {
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


  /**
   * Sets the combat item animator for this actions component
   * @param combatAnimator the combat animator entity which has subcomponents
   *                      PlayerSkillAnimationController and AnimationRenderer
   */
  public void setCombatAnimator(Entity combatAnimator){
    this.combatAnimator = combatAnimator;
  }

  /**
   *  Makes the player attack with the hera combat item.
   */
  void attackAnimation(){
    this.combatAnimator.getEvents().trigger("hera");
  }

  /**
   *  Makes the player attack with the level3Dagger combat item.
   */
  void attackAnimation2(){
    this.combatAnimator.getEvents().trigger("level3Dagger");
  }
}
