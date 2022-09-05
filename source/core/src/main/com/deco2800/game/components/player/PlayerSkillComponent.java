package com.deco2800.game.components.player;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;

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

    private boolean isInvulnerable;
    private long invulnerableEnd;

    // Teleport variables
    private static final int TELEPORT_LENGTH = 4;
    private long teleportEnd; // Teleport charge end system time
    private boolean teleporting;
    private static final long TELEPORT_CHARGE_LENGTH = 1000; // In MilliSec (1000millsec = 1sec)
    private static final float TELEPORT_MOVEMENT_RESTRICTION = 0.5f; // As a proportion of regular move (0.8 = 80%)
    private boolean teleportEndEvent = false;

    // Dashing Variables
    private static final Vector2 DASH_SPEED = new Vector2(6f, 6f);
    private static final long DASH_LENGTH = 350; // In MilliSec (1000millsec = 1sec)
    private static final float DASH_MOVEMENT_RESTRICTION = 0.8f; // As a proportion of regular move (0.8 = 80%)
    private Vector2 dashDirection = Vector2.Zero.cpy();
    private boolean dashing = false;
    private long dashEnd; // Dash end system time
    private boolean dashEndEvent = false;


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

        // Check if player should still be invulnerable
        if (this.isInvulnerable && System.currentTimeMillis() > this.invulnerableEnd) {
            this.isInvulnerable = false;
        }

        // Check if the player is in a dash and waiting for the dash to end
        if (this.dashing && System.currentTimeMillis() > this.dashEnd) {
            this.dashing = false;
            this.dashEndEvent = true;
        }

        // Check if the player is waiting to teleport from charging
        // if true teleport the player and finish charging
        if (this.teleporting && System.currentTimeMillis() > this.teleportEnd) {
            this.teleporting = false;
            this.teleportEndEvent = true;
            teleportPlayer();
            skillAnimator.getEvents().trigger("regularAnimation");
        }
    }

    /**
     * Sets a listener to the skill event
     * @param skillName the skill name:
     *                  - "teleport" - the teleport skill
     * @param entity the player entity of the player actions component
     * @param playerActionsComponent the player actions component containing the call for the skill to
     *                               pass information into the skill manager
     */
    public void setSkill(String skillName, Entity entity, PlayerActions playerActionsComponent) {
        if (skillName.equals("teleport")) {
            entity.getEvents().addListener("skill", playerActionsComponent::teleport);
        }
    }

    /**
     * Resets all skills for the skill event of player entity
     * @param entity the player entity to remove all skill listeners from
     */
    public void resetSkills(Entity entity) {
        entity.getEvents().removeAllListeners("skill");
    }

    /**
     * Checks if the movement of the player entity should be altered based on skill state.
     * @return true - if the movement of the player should be modified based on skill state
     */
    public boolean movementIsModified() {

        return (isDashing() || isTeleporting());
    }

    /**
     * Returns the modified movement vector based on base movement speed and the alterations
     * of the skill state on the player movement.
     * @param baseMovement the base player movement speed vector
     * @return the modified vector considering skill state alterations
     */
    public Vector2 getModifiedMovement(Vector2 baseMovement) {
        Vector2 modifiedMovementVector = baseMovement;
        if (isDashing()) {
            Vector2 dashVelocity = dashDirection.cpy().scl(DASH_SPEED);
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
     * Checks for the end of a skill, should be polled continuously in update.
     * Note: if not polled continuously cannot guarantee correct behaviour.
     * @param skillName - the name of the skill to check end condition
     * @return true if the skill has ended and the flag has not been polled
     *          false if the skill end has been read
     */
    public boolean checkSkillEnd(String skillName) {
        switch(skillName) {
            case "dash":
                if (this.dashEndEvent) {
                    this.dashEndEvent = false;
                    return true;
                }
                return false;
            case "teleport":
                if (this.teleportEndEvent) {
                    this.teleportEndEvent = false;
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
     * Checks if the player is in the teleport skill state
     * @return true - if the player is charging a teleport
     *         false - otherwise
     */
    public boolean isTeleporting() {
        return this.teleporting;
    }

    /**
     * The functional start of the dash.
     * Should be called when player actions component registers dash event.
     * @param moveDirection the direction of the players movement at the start of the dash event.
     */
    public void startDash(Vector2 moveDirection) {
        this.dashDirection = moveDirection;
        this.dashing = true;
        long dashStart = System.currentTimeMillis();
        this.dashEnd = dashStart + DASH_LENGTH;
        setInvulnerable(DASH_LENGTH);
    }

    /**
     * The functional start of the teleport skill.
     * Should be called when player actions component registers teleport event.
     */
    public void startTeleport() {
        this.teleporting = true;
        long teleportStart = System.currentTimeMillis();
        this.teleportEnd = teleportStart + TELEPORT_CHARGE_LENGTH;
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
     * Sets player invulnerability as a result of a player skill.
     * @param invulnerableLength length of time in ms for a skill to render player
     *                           invulnerable
     */
    private void setInvulnerable(long invulnerableLength) {
        this.isInvulnerable = true;
        this.invulnerableEnd = System.currentTimeMillis() + invulnerableLength;
    }
}
