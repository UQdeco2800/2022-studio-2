package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.settingsmenu.SettingsMenuDisplay;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.EntityTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Action component for interacting with the player. Player events should be initialised in create()
 * and when triggered should call methods within this class.
 */
public class PlayerActions extends Component {

  private static final Logger logger = LoggerFactory.getLogger(SettingsMenuDisplay.class);
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
  private boolean walkStatus = false;
  private long restStart=0;
  private long restEnd;
  private final String WALKING_SOUND = "sounds/walk_on_sand.wav";
  private final String[] SOUND_EFFECTS = {WALKING_SOUND};
  private Music teleportSound= Gdx.audio.newMusic(Gdx.files.internal("sounds/teleport_sound.wav"));
  private Music dashSound= Gdx.audio.newMusic(Gdx.files.internal("sounds/dash.mp3"));
  private Music blockSound= Gdx.audio.newMusic(Gdx.files.internal("sounds/block.mp3"));
  private Music dodgeSound= Gdx.audio.newMusic(Gdx.files.internal("sounds/dodge.mp3"));
  private Music fireballSound= Gdx.audio.newMusic(Gdx.files.internal("sounds/fireball.wav"));
  private Music bleedingSound= Gdx.audio.newMusic(Gdx.files.internal("sounds/bleeding.wav"));
  private Music projectileSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/projectile.mp3"));
  private Music rootSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/root.mp3"));
  private Music chargeSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/charge.mp3"));
  private Music invulnerabilitySound= Gdx.audio.newMusic(Gdx.files.internal("sounds/invulnerability.mp3"));
  private Music oraSound= Gdx.audio.newMusic(Gdx.files.internal("sounds/ora.mp3"));
  private Music zawarudoSound= Gdx.audio.newMusic(Gdx.files.internal("sounds/zawarudo.mp3"));

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
    entity.getEvents().addListener("toggleMinimap", this::toggleMinimap);
    entity.getEvents().addListener("consumePotionSlot1", this::consumePotionSlot1);
    entity.getEvents().addListener("consumePotionSlot2", this::consumePotionSlot2);
    entity.getEvents().addListener("consumePotionSlot3", this::consumePotionSlot3);
    //entity.getEvents().addListener("kill switch", this::killEnemy);
    //entity.getEvents().addListener("attackEnemy", this::attackAnimation);

    // Allocate sound resources
    ResourceService resourceService = ServiceLocator.getResourceService();
    resourceService.loadMusic(SOUND_EFFECTS);
    resourceService.loadAll();

    // Skills and Dash initialisation
    skillManager = new PlayerSkillComponent(entity);
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

  /**
   * Pressing the 'I' button toggles the inventory menu UI opening/closing.
   */
  public void toggleInventory(){
    entity.getComponent(InventoryComponent.class).toggleInventoryDisplay();
  }

  /**
   * Pressing the 'M' button toggles the Minimap window being open.
   */
  private void toggleMinimap() {
    if (!miniMapOpen) {
      ServiceLocator.getInventoryArea().displayMinimap();
    } else {
      ServiceLocator.getInventoryArea().disposeMinimap();
    }
    logger.info("Minimap toggled: " + miniMapOpen);
    EntityService.pauseAndResume();
    miniMapOpen = !miniMapOpen;
  }

  /**
   * Pressing the '1' button triggers the player to consume potion slot 1
   */
  public void consumePotionSlot1() {
    entity.getComponent(InventoryComponent.class).consumePotion(1);
    QuickBarDisplay.updatePotionTable();
  }

  /**
   * Pressing the '2' button triggers the player to consume potion slot 2
   */
  public void consumePotionSlot2() {
    entity.getComponent(InventoryComponent.class).consumePotion(2);
    QuickBarDisplay.updatePotionTable();
  }

  /**
   * Pressing the '3' button triggers the player to consume potion slot 3
   */
  public void consumePotionSlot3() {
    entity.getComponent(InventoryComponent.class).consumePotion(3);
    QuickBarDisplay.updatePotionTable();
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
   * Moves the player towards a given direction. Calls the walking sound effect.
   * Only fully calls when the game is not paused.
   *
   * @param direction direction to move in
   */
  public void walk(Vector2 direction) {

    if(!EntityService.pauseCheck()) {
      Music walkingSound = ServiceLocator.getResourceService().getAsset(WALKING_SOUND, Music.class);
      walkingSound.setLooping(true);
      walkingSound.play();
      this.walkDirection = direction;
      walkStatus = true;
    }
  }

  /**
   * Stops the player from walking.
   */
  public void stopWalking() {

    Music walkingSound = ServiceLocator.getResourceService().getAsset(WALKING_SOUND, Music.class);
    this.walkDirection = Vector2.Zero.cpy();
    updateSpeed();
    walkingSound.stop();
    walkStatus = false;
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
//      teleportSound.play();
      dashSound.play();
      skillManager.startDash(this.walkDirection.cpy());
      entity.getEvents().trigger("decreaseStamina", -20);
    }

    playerModifier.createModifier(PlayerModifier.STAMINAREGEN, 3, true, 2000);
  }

  /**
   *  Makes the player charge. Registers call of the charge function to the skill manager component.
   */
  void charge() {
    if(mana >= 20){
      skillManager.startCharge();
      entity.getEvents().trigger("decreaseMana", -20);
      chargeSound.play();
    }
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
    if (mana>=20) {
      entity.getEvents().trigger("decreaseMana", -20);
      skillManager.startTeleport();
      teleportSound.play();
    }
  }


  /**
   * Applies bleed to the player's next attackEnemy. Registers call of the bleed function to the skill manager component.
   */
  void bleed() {
    if (mana>=10) {
      entity.getEvents().trigger("decreaseMana", -10);
      bleedingSound.play();
      skillManager.startBleed();
    }
  }

  /**
   * Applies root to the player's next attackEnemy. Registers call of the root function to the skill manager component.
   */
  void root() {
    if (mana>=10) {
      entity.getEvents().trigger("decreaseMana", -10);
      rootSound.play();
      skillManager.startRoot();
    }
  }

  /**
   * Does an aoe attackEnemy around the player. Registers call of the aoe function to the skill manager component.
   */
  void aoe() {
    if (mana>=5) {
      entity.getEvents().trigger("decreaseMana", -5);
      skillManager.aoeAttack();
    }
  }

  /**
   * Makes the player dodge. Registers call of the dodge function to the skill manager component.
   */
  void dodge() {
    dodgeSound.play();
    skillManager.startDodge(this.walkDirection.cpy());
  }

  /**
   * Makes the player block. Registers call of the block function to the skill manager component.
   */
  void block() {
    blockSound.play();
    skillManager.startBlock();
  }

  /**
   * Makes the player cast their ultimate skill.
   * Registers call of the ultimate function to the skill manager component.
   */
  void ultimate() {
    oraSound.play();
    zawarudoSound.play();
    skillManager.startUltimate();
  }

  /**
   * Makes the player cast their ultimate fireball skill.
   * Registers call of the ultimate function to the skill manager component.
   */
  public void fireballUltimate() {
    fireballSound.play();
    skillManager.startFireballUltimate();
  }

  /**
   * Makes the player cast their cone projectile skill.
   * Registers call of the projectile function to the skill manager component.
   */
  public void coneProjectile() {
    projectileSound.play();
    skillManager.startProjectileSkill();
  }

  public void invulnerabilitySkill() {
    invulnerabilitySound.play();
    skillManager.startInvulnerabilitySkill();
  }

  public Vector2 getWalkDirection() {

    return this.walkDirection;

  }

  /**
   * Sets the skill animator for this actions component and passes it to the skill
   * component so the skill component can alter the skill animation state.
   * @param skillAnimator the skill animator entity which has subcomponents
   *                      PlayerSkillAnimationController and AnimationRenderer
   */
  public void setSkillAnimator(Entity skillAnimator) {
    this.skillManager.setSkillAnimator(skillAnimator);
  }
}