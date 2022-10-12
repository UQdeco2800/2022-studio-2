package com.deco2800.game.components.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.tasks.ChaseTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.services.ServiceLocator;

import java.util.HashMap;
import java.util.Map;

/**
 * Skill component for managing player skills and the player state as a result of those skills.
 * Designed to be a subcomponent of the PlayerActions.java class as a controller of
 * the skill subset of the player's actions.
 * Functions in the class should be called from the player actions class, passing information from
 * the player action manager into this class's skill functionality.
 */
public class PlayerSkillComponent extends Component {

    private Entity skillAnimator;
    private Entity playerEntity;
    private static final String SKILL1_LISTENER = "skill";
    private static final String SKILL2_LISTENER = "skill2";
    private static final String SKILL3_LISTENER = "skill3";

    public enum SkillTypes {
        NONE,
        DASH,
        TELEPORT,
        BLOCK,
        DODGE,
        BLEED,
        ROOT,
        CHARGE,
        AOE,
        ULTIMATE,
        FIREBALLULTIMATE,
        INVULNERABILITY,
        PROJECTILE
    }

    private int playerSkillPoints = 0;

    private boolean isInvulnerable;
    private long invulnerableEnd;
    private Entity enemy = null;

    Map<String, Long> skillCooldowns = new HashMap<>();

    // Teleport variables
    private static final int TELEPORT_LENGTH = 4;
    private long teleportEnd; // Teleport charge end system time
    private boolean teleporting;
    private static final long TELEPORT_CHARGE_LENGTH = 1000; // In MilliSec (1000millisec = 1sec)
    private static final float TELEPORT_MOVEMENT_RESTRICTION = 0.5f; // As a proportion of regular move (0.8 = 80%)
    private static final long TELEPORT_COOLDOWN = 3000;
    private boolean teleportEndEvent = false;

    // Dashing Variables
    private static final Vector2 DASH_SPEED = new Vector2(6f, 6f);
    private static final long DASH_LENGTH = 300; // In MilliSec (1000millisec = 1sec)
    private static final float DASH_MOVEMENT_RESTRICTION = 0.8f; // As a proportion of regular move (0.8 = 80%)
    private Vector2 dashDirection = Vector2.Zero.cpy();
    private boolean dashing = false;
    private long dashEnd; // Dash end system time
    private boolean dashEndEvent = false;

    // Dodge Variables
    private long dodgeEnd; // Teleport charge end system time
    private boolean dodging;
    private long dodgeSpeedBoostEnd;
    private boolean dodgeSpeedBoost;
    private static final float DODGE_SPEED_BOOST = 1.5f;
    private static final long DODGE_LENGTH = 300; // In MilliSec (1000millsec = 1sec)
    private static final Vector2 DODGE_SPEED = new Vector2(-4.5f, -4.5f); // As a proportion of regular move (0.8 = 80%)
    private static final float DODGE_SIDE_MOVE = 3.0f;
    private static final long DODGE_COOLDOWN = 500;
    private boolean dodgeEndEvent = false;
    private Vector2 dodgeDirection;

    // Block Variables
    private boolean blocking;
    private long blockEnd;
    private static final long BLOCK_LENGTH = 400;
    private static final long BLOCK_COOLDOWN = 500;
    private boolean blockEndEvent;

    // Root Variables
    private boolean rootApplied;
    private boolean rooted;
    private long rootEnd;
    private static final long ROOT_LENGTH = 5000;
    private boolean rootEndEvent = false;

    // Bleed Variables
    private boolean bleedApplied;
    private boolean bleeding;
    private long bleedStart;
    private long bleedEnd = 0;
    private static final long BLEED_LENGTH = 1000;
    private static final long BLEED_HITS = 7;
    private static final int BLEED_DAMAGE = 5;
    private boolean bleedEndEvent = false;

    // Charge variables
    private boolean enemyDead = false;
    private boolean chargeFirstHit = false;
    private Vector2 chargeDirection = Vector2.Zero.cpy();
    private boolean charging;
    private long chargeEnd;
    private static final Vector2 CHARGE_SPEED = new Vector2(20f, 20f);
    private int CHARGE_DAMAGE = 30;

    private boolean chargingUltimate;
    private long ultimateChargeEnd;
    private static final long ULTIMATE_CHARGE_LENGTH = 2600;
    private static final long ULTIMATE_TIMESTOP_LENGTH = 6000;
    private long ultimateTimeStopEnd;
    private boolean timeStopped;

    private boolean chargingUltimateFireball;
    private static final long FIREBALL_CHARGE_LENGTH = 1000;
    private long ultimateFireballChargeEnd;

    private boolean invulnerabilitySkill;
    private static final long INVULNERABILITY_LENGTH = 2600;
    private long invulnerabilitySkillEnd;

    private long aoeAnimationEnd;
    private boolean aoeAnimationRunning;
    private static final long AOE_ANIMATION_LENGTH = 500;

    private long rootAnimationEnd;
    private boolean rootAnimationRunning;
    private static final long ROOT_ANIMATION_LENGTH = 500;

    /**
     * Initialises the player skill component, taking a player entity as the parent component.
     * @param playerEntity the player entity this skill component is a subcomponent of
     */
    public PlayerSkillComponent(Entity playerEntity) {
        this.playerEntity = playerEntity;
    }

    /**
     * Sets the skill animator entity for this skill component, so this skill component
     * can interact with the corresponding animator. This entity should have as components
     * at least a PlayerSkillAnimationController and an AnimationRenderComponent.
     * @param skillAnimator skill animator entity with PlayerSkillAnimationController
     *                      and an AnimationRenderComponent
     */
    public void setSkillAnimator(Entity skillAnimator) {
        this.skillAnimator = skillAnimator;
    }

    /**
     * Update should update the cooldowns/state of skills within the skill manager
     */
    @Override
    public void update() {

        // Check if dead enemy needs to be removed
        if (this.enemyDead) {
            this.enemyDead = false;
            this.enemy.dispose();
            if (this.enemy.getComponent(AnimationRenderComponent.class) != null) {
                this.enemy.getComponent(AnimationRenderComponent.class).stopAnimation();
            }
        }

        // Check if player should still be invulnerable
        if (this.isInvulnerable && System.currentTimeMillis() > this.invulnerableEnd) {
            this.isInvulnerable = false;
        }

        // Check if player should still be invulnerable
        if (this.blocking && System.currentTimeMillis() > this.blockEnd) {
            this.blocking = false;
            this.blockEndEvent = true;
            skillAnimator.getEvents().trigger("regularAnimation");
        }

        // Check if the player is in a dash and waiting for the dash to end
        if (this.dashing && System.currentTimeMillis() > this.dashEnd) {
            // Only end animation if not interrupting another skill
            if (!this.teleporting && !this.blocking && !this.dodging && !this.charging) {
                skillAnimator.getEvents().trigger("regularAnimation");
            }
            this.dashing = false;
            this.dashEndEvent = true;
        }

        // Check if the player is in a charge and waiting for the charge to end
        if (this.charging && System.currentTimeMillis() > this.chargeEnd) {
            // Only end animation if not interrupting another skill
            if (!this.teleporting && !this.blocking && !this.dodging) {
                skillAnimator.getEvents().trigger("regularAnimation");
            }
            this.charging = false;
        }

        // Check if the player is waiting to teleport from charging
        // if true teleport the player and finish charging
        if (this.teleporting && System.currentTimeMillis() > this.teleportEnd) {
            this.teleporting = false;
            this.teleportEndEvent = true;
            teleportPlayer();
            skillAnimator.getEvents().trigger("regularAnimation");
        }

        // Check if the player is waiting to finish a dodge
        if (this.dodging && System.currentTimeMillis() > this.dodgeEnd) {
            this.dodging = false;
            this.dodgeEndEvent = true;
            skillAnimator.getEvents().trigger("regularAnimation");
        } else if (System.currentTimeMillis() > this.dodgeSpeedBoostEnd) {
            this.dodgeSpeedBoost = false;
        }

        // Check if the slow effect should be ended
        if (this.rooted && System.currentTimeMillis() > this.rootEnd) {
            this.rooted = false;
            this.rootEndEvent = true;
            changeSpeed(this.enemy, 0, false);
        }

        // Check if bleed is applied to enemy
        if (this.bleeding) {
            // Do damage every x seconds (time set by BLEED_LENGTH)
            if (this.bleedEnd - this.bleedStart < BLEED_LENGTH * (BLEED_HITS - 1)) {
                checkBleed(this.enemy);
            } else {
                this.bleeding = false;
                this.bleedEndEvent = true;
            }
        }

        // Check for ultimate end and screen animation triggers
        if (this.chargingUltimate) {

            if (System.currentTimeMillis() > this.ultimateChargeEnd) {
                playerEntity.getEvents().trigger("skillScreenOverlayFlash", false);
                skillAnimator.getEvents().trigger("regularAnimation");
                this.chargingUltimate = false;
                ServiceLocator.getEntityService().toggleTimeStop();
                this.timeStopped = true;
            } else if (System.currentTimeMillis() > this.ultimateChargeEnd - 1500) {
                playerEntity.getEvents().trigger("skillScreenOverlayFlash", false);
            } else if (System.currentTimeMillis() > this.ultimateChargeEnd - 1800) {
                playerEntity.getEvents().trigger("skillScreenOverlayFlash", true);
            }
        }
        if (this.timeStopped) {
            if (System.currentTimeMillis() > this.ultimateTimeStopEnd) {
                this.timeStopped = false;
                ServiceLocator.getEntityService().toggleTimeStop();
            }
        }

        if (this.chargingUltimateFireball) {
            if (System.currentTimeMillis() > this.ultimateFireballChargeEnd) {
                this.chargingUltimateFireball = false;
                skillAnimator.getEvents().trigger("regularAnimation");
                if (ServiceLocator.getGameArea().getClass() == ForestGameArea.class) {
                    ((ForestGameArea) ServiceLocator.getGameArea()).spawnPlayerProjectileSpray();
                }
            }
        }

        if (this.invulnerabilitySkill) {
            if (System.currentTimeMillis() > this.invulnerabilitySkillEnd) {
                this.invulnerabilitySkill = false;
                skillAnimator.getEvents().trigger("regularAnimation");
            }
        }

        if (this.rootAnimationRunning) {
            if (System.currentTimeMillis() > this.rootAnimationEnd) {
                this.rootAnimationRunning = false;
                skillAnimator.getEvents().trigger("regularAnimation");
            }
        }
        if (this.aoeAnimationRunning) {
            if (System.currentTimeMillis() > this.aoeAnimationEnd) {
                this.aoeAnimationRunning = false;
                skillAnimator.getEvents().trigger("regularAnimation");
            }
        }
    }

    public void addSkillPoints(int skillPoints) {
        playerSkillPoints += skillPoints;
    }

    public int getSkillPoints() {
        return playerSkillPoints;
    }

    /**
     * Sets a listener to the skill event
     * @param skillName the skill name:
     *                  - "teleport" - the teleport skill
     * @param skillNum the skill number (not 0 based and up to 2 skills)
     * @param entity the player entity of the player actions component
     * @param playerActionsComponent the player actions component containing the call for the skill to
     *                               pass information into the skill manager
     */
    public void setSkill(int skillNum, SkillTypes skillName, Entity entity, PlayerActions playerActionsComponent) {
        String skillEvent;
        if (skillNum == 1) {
            skillEvent = SKILL1_LISTENER;
        } else if (skillNum == 2) {
            skillEvent = SKILL2_LISTENER;
        } else {
            skillEvent = SKILL3_LISTENER;
        }

        if (skillName == SkillTypes.TELEPORT) {
            entity.getEvents().addListener(skillEvent, playerActionsComponent::teleport);
        } else if (skillName == SkillTypes.DODGE) {
            entity.getEvents().addListener(skillEvent, playerActionsComponent::dodge);
        } else if (skillName == SkillTypes.BLOCK) {
            entity.getEvents().addListener(skillEvent, playerActionsComponent::block);
        } else if (skillName == SkillTypes.BLEED) {
            entity.getEvents().addListener(skillEvent, playerActionsComponent::bleed);
            entity.getEvents().addListener("hitEnemy", this::hitBleed);
        }  else if (skillName == SkillTypes.ROOT) {
            entity.getEvents().addListener(skillEvent, playerActionsComponent::root);
            entity.getEvents().addListener("hitEnemy", this::hitRoot);
        } else if (skillName == SkillTypes.FIREBALLULTIMATE) {
            entity.getEvents().addListener(skillEvent, playerActionsComponent::fireballUltimate);
        } else if (skillName == SkillTypes.ULTIMATE) {
            entity.getEvents().addListener(skillEvent, playerActionsComponent::ultimate);
        } else if (skillName == SkillTypes.CHARGE) {
            entity.getEvents().addListener(skillEvent, playerActionsComponent::charge);
            entity.getEvents().addListener("enemyCollision", this::chargeHit);
        } else if (skillName == SkillTypes.AOE) {
            entity.getEvents().addListener(skillEvent, playerActionsComponent::aoe);
        } else if (skillName == SkillTypes.INVULNERABILITY) {
            entity.getEvents().addListener(skillEvent, playerActionsComponent::invulnerabilitySkill);
        } else if (skillName == SkillTypes.PROJECTILE) {
            entity.getEvents().addListener(skillEvent, playerActionsComponent::coneProjectile);
        }
    }

    /**
     * Resets all skills for the skill event of player entity
     * @param entity the player entity to remove all skill listeners from
     */
    public void resetSkills(Entity entity) {
        entity.getEvents().removeAllListeners(SKILL1_LISTENER);
        entity.getEvents().removeAllListeners(SKILL2_LISTENER);
        entity.getEvents().removeAllListeners(SKILL3_LISTENER);
    }

    /**
     * Checks if the movement of the player entity should be altered based on skill state.
     * @return true - if the movement of the player should be modified based on skill state
     */
    public boolean movementIsModified() {

        return (isDashing() || isTeleporting() || isDodging() || isCharging() || this.dodgeSpeedBoost);
    }

    /**
     * Returns the modified movement vector based on base movement speed and the alterations
     * of the skill state on the player movement.
     * @param baseMovement the base player movement speed vector
     * @return the modified vector considering skill state alterations
     */
    public Vector2 getModifiedMovement(Vector2 baseMovement) {
        Vector2 modifiedMovementVector = baseMovement;

        if (isDodging()) { // Dodging has priority over dash (can interrupt dash with a dodge)
            Vector2 dodgeVelocity = dodgeDirection.cpy().scl(DODGE_SPEED);
            float vectorDotProduct = vectorDotProduct(modifiedMovementVector, dodgeVelocity);
            Vector2 alteredMovement = new Vector2(modifiedMovementVector.x * (1 - Math.abs(vectorDotProduct)) * DODGE_SIDE_MOVE,
                    modifiedMovementVector.y * (1 - Math.abs(vectorDotProduct)) * DODGE_SIDE_MOVE);

            modifiedMovementVector = addVectors(alteredMovement, dodgeVelocity);

        } else if (isDashing()) { // Dashing movement
            Vector2 dashVelocity = dashDirection.cpy().scl(DASH_SPEED);
            Vector2 reducedMovement = new Vector2(modifiedMovementVector.x * DASH_MOVEMENT_RESTRICTION,
                    modifiedMovementVector.y * DASH_MOVEMENT_RESTRICTION);
            modifiedMovementVector = addVectors(reducedMovement, dashVelocity);
        }

        if (this.dodgeSpeedBoost) { // Flat speed boost from mitigating damage from dodge
            modifiedMovementVector = new Vector2(modifiedMovementVector.x * DODGE_SPEED_BOOST,
                    modifiedMovementVector.y * DODGE_SPEED_BOOST);
        }

        else if (isCharging()) {
            Vector2 dashVelocity = chargeDirection.cpy().scl(CHARGE_SPEED);
            Vector2 reducedMovement = new Vector2(modifiedMovementVector.x * DASH_MOVEMENT_RESTRICTION,
                    modifiedMovementVector.y * DASH_MOVEMENT_RESTRICTION);
            modifiedMovementVector = addVectors(reducedMovement, dashVelocity);
        }

        if (isTeleporting()) {
            Vector2 reducedMovement = new Vector2(modifiedMovementVector.x * TELEPORT_MOVEMENT_RESTRICTION,
                    modifiedMovementVector.y * TELEPORT_MOVEMENT_RESTRICTION);
            modifiedMovementVector = reducedMovement;
        }
        return modifiedMovementVector;
    }

    /**
     * Checks the skill state for invulnerability as a result of a player skill.
     * @return true if the player should be invulnerable
     *         false if the player should be able to take damage
     */
    public boolean isInvulnerable() {
        return this.isInvulnerable;
    }

    /**
     * Functional equivalent of isInvulnerable but also triggers effects internally based on skill
     * states. Should be used instead of isInvulnerable unless just checking invulnerable state.
     * @return true if the player should be invulnerable
     *         false if the player should be able to take damage
     */
    public boolean skillDamageTrigger() {
        if (this.isInvulnerable) {
            if (this.isBlocking()) {
                this.skillCooldowns.forEach((skill, timestamp) -> timestamp = timestamp - 500);
            }
            if (this.isDodging()) {
                dodgeSpeedBoost = true;
                this.dodgeSpeedBoostEnd = dodgeEnd + (DODGE_LENGTH * 3);
            }
            return true;
        }
        return false;
    }

    /**
     * Checks for the end of a skill, should be polled continuously in update.
     * Note: if not polled continuously cannot guarantee correct behaviour.
     * @param skillName - the name of the skill to check end condition
     * @return true if the skill has ended and the flag has not been polled
     *          false if the skill end has been read
     */
    public boolean checkSkillEnd(SkillTypes skillName) {
        switch(skillName) {
            case DODGE:
                if (this.dodgeEndEvent) {
                    this.dodgeEndEvent = false;
                    return true;
                }
                return false;
            case DASH:
                if (this.dashEndEvent) {
                    this.dashEndEvent = false;
                    return true;
                }
                return false;
            case TELEPORT:
                if (this.teleportEndEvent) {
                    this.teleportEndEvent = false;
                    return true;
                }
                return false;
            case BLOCK:
                if (this.blockEndEvent) {
                    this.blockEndEvent = false;
                    return true;
                }
                return false;
            case BLEED:
                if (this.bleedEndEvent) {
                    this.bleedEndEvent = false;
                    return true;
                }
                return false;
            case ROOT:
                if (this.rootEndEvent) {
                    this.rootEndEvent = false;
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    /**
     * Checks if the player is in the dash skill state
     * @return true - if the player is dashing
     *         false - otherwise
     */
    public boolean isDashing() {
        return this.dashing;
    }

    /**
     * Checks if the player is in the charge skill state
     * @return true - if the player is charging
     *         false - otherwise
     */
    public boolean isCharging() {
        return this.charging;
    }

    /**
     * Checks if the player is in the teleport skill state
     * @return true - if the player is charging a teleport
     *         false - otherwise
     */
    public boolean isTeleporting() {
        return this.teleporting;
    }

    /**
     * Checks if the player is in the dodge skill state
     * @return true - if the player is dodging
     *         false - otherwise
     */
    public boolean isDodging() {
        return this.dodging;
    }

    /**
     * Checks if the player is in the block skill state
     * @return true - if the player is blocking
     *         false - otherwise
     */
    public boolean isBlocking() {
        return this.blocking;
    }

    /**
     * Checks if the player is in the bleed skill state
     * @return true - if the player has bleed active
     *         false - otherwise
     */
    public boolean bleedActive() {
        return this.bleedApplied;
    }

    /**
     * Checks if the enemy has bleeding applied
     * @return true - if the enemy is bleeding
     *         false - otherwise
     */
    public boolean isBleeding() {
        return this.bleeding;
    }

    /**
     * Checks if the player is in the root skill state
     * @return true - if the player has root active
     *         false - otherwise
     */
    public boolean rootActive() {
        return this.rootApplied;
    }

    /**
     * Checks if the enemy has rooted applied
     * @return true - if the enemy is rooted
     *         false - otherwise
     */
    public boolean isRooted() {
        return this.rooted;
    }

    /**
     * The functional start of the invulnerability skill.
     * Should be called when the player actions component registers invulnerability skill
     */
    public void startInvulnerabilitySkill() {
        if (cooldownFinished("invulnerable", (INVULNERABILITY_LENGTH * 3))) {
            this.invulnerabilitySkill = true;
            skillAnimator.getEvents().trigger("invulnerabilityAnimation");
            playerEntity.getEvents().trigger("invulnerabilityCountdown");
            long invulnerabilityStart = System.currentTimeMillis();
            this.invulnerabilitySkillEnd = invulnerabilityStart + INVULNERABILITY_LENGTH;
            setInvulnerable(INVULNERABILITY_LENGTH);
            setSkillCooldown("invulnerable");
        }
    }

    /**
     * The functional start of the dash.
     * Should be called when player actions component registers dash event.
     * @param moveDirection the direction of the players movement at the start of the dash event.
     */
    public void startDash(Vector2 moveDirection) {
        if (cooldownFinished("dash", (long) (DASH_LENGTH * 1.2))) {
            this.dashDirection = moveDirection;
            this.dashing = true;
            skillAnimator.getEvents().trigger("dashAnimation");
            playerEntity.getEvents().trigger("dashCountdown");
            long dashStart = System.currentTimeMillis();
            this.dashEnd = dashStart + DASH_LENGTH;
            setInvulnerable(DASH_LENGTH/2);
            setSkillCooldown("dash");
        }
    }

    /**
     * The functional start of the teleport skill.
     * Should be called when player actions component registers teleport event.
     */
    public void startTeleport() {
        if (cooldownFinished("teleport", TELEPORT_COOLDOWN)) {
            this.teleporting = true;
            skillAnimator.getEvents().trigger("teleportAnimation");
            playerEntity.getEvents().trigger("teleportCountdown");
            long teleportStart = System.currentTimeMillis();
            this.teleportEnd = teleportStart + TELEPORT_CHARGE_LENGTH;
            setSkillCooldown("teleport");
        }
    }

    /**
     * The functional start of the dodge skill.
     * Should be called when player actions component registers dodge event.
     */
    public void startDodge(Vector2 moveDirection) {
        if (cooldownFinished("dodge", DODGE_COOLDOWN)) {
            this.dodgeDirection = moveDirection;
            this.dodging = true;
            skillAnimator.getEvents().trigger("dodgeAnimation");
            playerEntity.getEvents().trigger("dodgeCountdown");
            long dodgeStart = System.currentTimeMillis();
            this.dodgeEnd = dodgeStart + DODGE_LENGTH;
            setInvulnerable(DODGE_LENGTH);
            setSkillCooldown("dodge");
        }
    }

    /**
     * The functional start of the block skill.
     * Should be called when player actions component registers block event.
     */
    public void startBlock() {
        if (cooldownFinished("block", BLOCK_COOLDOWN)) {
            this.blocking = true;
            skillAnimator.getEvents().trigger("blockAnimation");
            playerEntity.getEvents().trigger("blockCountdown");
            long blockStart = System.currentTimeMillis();
            this.blockEnd = blockStart + BLOCK_LENGTH;
            setInvulnerable(BLOCK_LENGTH);
            setSkillCooldown("block");
        }

    }

    /**
     * The functional start of the root skill.
     * Should be called when player actions component registers root event.
     */
    public void startRoot() {
        skillAnimator.getEvents().trigger("rootAnimation");
        playerEntity.getEvents().trigger("rootCountdown");
        this.rootAnimationRunning = true;
        long rootStart = System.currentTimeMillis();
        this.rootAnimationEnd = rootStart + ROOT_ANIMATION_LENGTH;
        this.rootApplied = true;
    }

    /**
     * The functional start of the timestop ultimate skill.
     * Should be called when player actions component registers ultimate event.
     */
    public void startUltimate() {
        if (cooldownFinished("timestop", (long) (ULTIMATE_CHARGE_LENGTH * 3))) {
            skillAnimator.getEvents().trigger("ultimateAnimation");
            playerEntity.getEvents().trigger("ultimateCountdown");
            chargingUltimate = true;
            long ultimateStart = System.currentTimeMillis();
            this.ultimateChargeEnd = ultimateStart + ULTIMATE_CHARGE_LENGTH;
            this.ultimateTimeStopEnd = ultimateChargeEnd + ULTIMATE_TIMESTOP_LENGTH;
            setSkillCooldown("timestop");
        }
    }

    /**
     * The functional start of the wrench projectile skill.
     * Should be called when player actions component registers projectile skill event.
     */
    public void startProjectileSkill() {
        if (cooldownFinished("projectile", 5000)) {
            playerEntity.getEvents().trigger("wrenchCountdown");
            if (ServiceLocator.getGameArea().getClass() == ForestGameArea.class) {
                ((ForestGameArea) ServiceLocator.getGameArea()).spawnPlayerProjectileCone();
            }
        }
    }

    /**
     * The functional start of the fireball ultimate skill.
     * Should be called when player actions component registers ultimate event.
     */
    public void startFireballUltimate() {
        if (cooldownFinished("fireball", FIREBALL_CHARGE_LENGTH * 10)) {
            chargingUltimateFireball = true;
            skillAnimator.getEvents().trigger("fireballAnimation");
            playerEntity.getEvents().trigger("fireballCountdown");
            long ultimateStart = System.currentTimeMillis();
            this.ultimateFireballChargeEnd = ultimateStart + FIREBALL_CHARGE_LENGTH;
            setSkillCooldown("fireball");
        }
    }

    /**
     * Apply root effect to enemy
     * @param target enemy to apply effect on
     */
    public void hitRoot(Entity target) {
        if (!this.rootApplied) {
            return;
        }
        this.enemy = target;
        changeSpeed(target, ROOT_LENGTH, true);
    }

    /**
     * The functional start of the bleed skill.
     * Should be called when player actions component registers bleed event.
     */
    public void startBleed() {
        this.bleedApplied = true;
        playerEntity.getEvents().trigger("bleedCountdown");
    }

    /**
     * Apply bleed effect to enemy
     * @param target enemy to apply effect on
     */
    public void hitBleed(Entity target) {
        if (!this.bleedApplied) {
            return;
        }
        this.enemy = target;
        this.bleeding = true;
        this.bleedApplied = false;
        this.bleedStart = System.currentTimeMillis();
    }

    /**
     * Does damage over time to target.
     * @param target enemy to damage
     */
    public void checkBleed(Entity target) {
        if (System.currentTimeMillis() > this.bleedEnd + BLEED_LENGTH) {
            CombatStatsComponent enemyStats = target.getComponent(CombatStatsComponent.class);
            enemyStats.setHealth(enemyStats.getHealth() - BLEED_DAMAGE);

            // enemy dead
            if (target.getComponent(CombatStatsComponent.class).getHealth() == 0) {
                target.dispose();
                if (target.getComponent(AnimationRenderComponent.class) != null) {
                    target.getComponent(AnimationRenderComponent.class).stopAnimation();
                }
                this.bleedEnd = this.bleedStart + BLEED_LENGTH * (BLEED_HITS - 1) + 1;
                return;
            }
            this.bleedEnd = System.currentTimeMillis();
        }
    }

    /**
     * Teleports the player a set distance from their current position in
     * the walk direction.
     */
    public void teleportPlayer() {
        PlayerActions actions = playerEntity.getComponent(PlayerActions.class);
        float teleportPositionX = playerEntity.getPosition().x + actions.getWalkDirection().x * TELEPORT_LENGTH;
        float teleportPositionY = playerEntity.getPosition().y + actions.getWalkDirection().y * TELEPORT_LENGTH;

        // Check if teleport is out of map bounds
        if (teleportPositionX < -0.08)
            teleportPositionX = -0.08f;
        if (teleportPositionY < 0.11)
            teleportPositionY = 0.11f;
        if (teleportPositionX > 24.18)
            teleportPositionX = 24.18f;
        if (teleportPositionY > 24.68)
            teleportPositionY = 24.68f;
        playerEntity.setPosition(teleportPositionX, teleportPositionY);

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
            return true; // First time this skill is called don't check previous time stamps
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
     */
    public void setSkillCooldown(String skill) {
        skillCooldowns.putIfAbsent(skill, 0L);
        skillCooldowns.replace(skill, System.currentTimeMillis());
    }

    /**
     * Tool for basic vector addition.
     * Adds the x and y components of the two vectors and returns the resultant vector.
     * @param firstVector first vector for addition
     * @param secondVector second vector for addition
     * @return a vector which is the sum the combined vectors
     */
    private Vector2 addVectors(Vector2 firstVector, Vector2 secondVector) {
        return new Vector2(firstVector.x + secondVector.x, firstVector.y + secondVector.y);
    }

    /**
     * Normalises a vector such that the magnitude of the vector is 1.
     * @param rawVector the vector to be normalised
     * @return the normalised vector, in the same direction as rawVector but with a magnitude of 1
     */
    private Vector2 normaliseVector(Vector2 rawVector) {
        float magnitude;

        if (rawVector.y == 0 && rawVector.x == 0) {
            return new Vector2(0,0);
        } else if (rawVector.x == 0) {
            magnitude = rawVector.y;
        } else if (rawVector.y == 0) {
            magnitude = rawVector.x;
        } else {
            magnitude = (float) Math.pow(Math.pow(rawVector.x, 2) + Math.pow(rawVector.y, 2), 0.5);
        }
        return new Vector2(rawVector.x / magnitude, rawVector.y / magnitude);
    }

    /**
     * Tool for normalised vector dot product.
     * Normalises input vectors to have the same magnitude, and as a result
     * the output of this is between -1 and 1 depending on the angle between the two vectors.
     * Essentially gives the cos of the angle between the two input vectors.
     * @param firstVector first vector for dot product
     * @param secondVector second vector for dot product
     * @return a float which is the dot product of the vectors always between -1 and 1,
     *          with 0 being the vectors are perpendicular
     *          and magnitude 1 being the vectors are parallel
     */
    private float vectorDotProduct(Vector2 firstVector, Vector2 secondVector) {
        Vector2 normFirstVector = normaliseVector(firstVector);
        Vector2 normSecondVector = normaliseVector(secondVector);
        return ((normFirstVector.x * normSecondVector.x) + (normFirstVector.y * normSecondVector.y));
    }

    /**
     * Sets player invulnerability as a result of a player skill.
     * @param invulnerableLength length of time in ms for a skill to render player
     *                           invulnerable
     */
    private void setInvulnerable(long invulnerableLength) {
        this.isInvulnerable = true;
        long newInvulnerableEnd = System.currentTimeMillis() + invulnerableLength;
        if (newInvulnerableEnd > this.invulnerableEnd) {
            this.invulnerableEnd =  newInvulnerableEnd;
        }
    }

    /**
     * Changes enemy movement speed as a result of a player skill.
     * @param target the entity to change speed
     * @param slowLength length of time in ms for a skill to reduce speed
     * @param slow true to slow enemy, false to return to normal speed
     */
    private void changeSpeed(Entity target, long slowLength, boolean slow) {
        if (slow) {
            target.getComponent(AITaskComponent.class).addTask
                    (new ChaseTask(playerEntity, 50, 5f, 6f, 1f));
            this.rooted = true;
            this.rootApplied = false;
            this.rootEnd = System.currentTimeMillis() + slowLength;
        } else if (target != null){
            //target.getComponent(AITaskComponent.class).dispose();
            target.getComponent(AITaskComponent.class).getPriorityTasks().remove
                    (target.getComponent(AITaskComponent.class).getPriorityTasks().size() - 1);
        }
    }

    /**
     * The functional start of the charge.
     * Should be called when player actions component registers charge event.
     * @param moveDirection the direction of the players movement at the start of the charge event.
     */
    public void startCharge(Vector2 moveDirection) {
        if (1 < 2) {//cooldown
            this.chargeDirection = moveDirection;
            this.charging = true;
            this.chargeFirstHit = true;
            skillAnimator.getEvents().trigger("dashAnimation");
            playerEntity.getEvents().trigger("chargeCountdown");
            this.chargeEnd = System.currentTimeMillis() + DASH_LENGTH;
            setInvulnerable(DASH_LENGTH / 2);
            setSkillCooldown("charge");
        }
    }

    /**
     * Ensures enemy is not a projectile then sets entity enemy variable
     * @param target the first enemy the player collides with
     */
    public void chargeHit(Entity target) {
        if (this.charging && this.chargeFirstHit && !(target.checkEntityType(EntityTypes.PROJECTILE))) {
            this.chargeFirstHit = false;
            this.enemy = target;
            this.chargeEnd = 0;
            chargeAttack(this.enemy);
        }
    }

    /**
     * Apply damage to enemy then knockback.
     * @param target the first enemy the player collides with during charge skill
     */
    private void chargeAttack(Entity target) {
        CombatStatsComponent enemyStats = target.getComponent(CombatStatsComponent.class);
        enemyStats.setHealth(enemyStats.getHealth() - CHARGE_DAMAGE);

        // enemy dead
        if (target.getComponent(CombatStatsComponent.class).getHealth() == 0) {
            this.enemyDead = true;
            return;
        }

        PhysicsComponent physicsComponent = target.getComponent(PhysicsComponent.class);
        if (physicsComponent != null) {
            Body targetBody = physicsComponent.getBody();
            Vector2 direction = target.getCenterPosition().sub(playerEntity.getCenterPosition());
            Vector2 impulse;
            if (target.checkEntityType(EntityTypes.MELEE)) {
                impulse = direction.setLength(100f); // knockback strength (gymbro)
            } else {
                impulse = direction.setLength(40f); // knockback strength (not gymbro)
            }
            targetBody.applyLinearImpulse(impulse, targetBody.getWorldCenter(), true);
        }
    }

    /**
     * Damages all enemies around player and knocks them back.
     */
    public void aoeAttack() {
        if (cooldownFinished("aoe", AOE_ANIMATION_LENGTH * 10)) {
            long aoeStart = System.currentTimeMillis();
            this.aoeAnimationEnd = aoeStart + AOE_ANIMATION_LENGTH;
            skillAnimator.getEvents().trigger("aoeAnimation");
            playerEntity.getEvents().trigger("aoeCountdown");
            this.aoeAnimationRunning = true;
            if (ServiceLocator.getGameArea().getClass() == ForestGameArea.class) {
                Entity projectile = ((ForestGameArea) ServiceLocator.getGameArea()).spawnPlayerAOE();
                ForestGameArea.removeProjectileOnMap(projectile);
                if (projectile.getComponent(AnimationRenderComponent.class) != null) {
                    projectile.getComponent(AnimationRenderComponent.class).stopAnimation();
                }
            }
            setSkillCooldown("aoe");
        }
    }
}
