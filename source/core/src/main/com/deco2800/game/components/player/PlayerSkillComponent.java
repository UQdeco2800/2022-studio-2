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
    private static final Vector2 DASH_SPEED = new Vector2(6f, 6f);
    private static final long DASH_LENGTH = 350; // In MilliSec (1000millsec = 1sec)
    private static final float DASH_MOVEMENT_RESTRICTION = 0.8f; // As a proportion of regular move (0.8 = 80%)
    private static final int TELEPORT_LENGTH = 4;
    private Vector2 dashDirection = Vector2.Zero.cpy();
    private boolean dashing = false;
    private long dashStart;
    private long dashEnd;

    /**
     * Update should update the cooldowns/state of skills within the skill manager
     */
    @Override
    public void update() {
        if (this.dashing && System.currentTimeMillis() > this.dashEnd) {
            this.dashing = false;
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
        if (isDashing()) {
            return true;
        } else {
            return false;
        }
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
        return modifiedMovementVector;
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
     * The functional start of the dash.
     * Should be called when player actions component registers dash event.
     * @param moveDirection the direction of the players movement at the start of the dash event.
     */
    public void startDash(Vector2 moveDirection) {
        this.dashDirection = moveDirection;
        this.dashing = true;
        this.dashStart = System.currentTimeMillis();
        this.dashEnd = this.dashStart + DASH_LENGTH;
    }

    /**
     * The functional start of the teleport skill.
     * Should be called when player actions component registers teleport event.
     * @param walkDirection the walking direction of the player at teleport event
     * @param entity the player entity
     */
    public void startTeleport(Vector2 walkDirection, Entity entity) {
        float teleportPositionX = entity.getPosition().x + walkDirection.x * TELEPORT_LENGTH;
        float teleportPositionY = entity.getPosition().y + walkDirection.y * TELEPORT_LENGTH;

        // Check if teleport is out of map bounds
        if (teleportPositionX < -0.08)
            teleportPositionX = -0.08f;
        if (teleportPositionY < 0.11)
            teleportPositionY = 0.11f;
        if (teleportPositionX > 24.18)
            teleportPositionX = 24.18f;
        if (teleportPositionY > 24.68)
            teleportPositionY = 24.68f;

        entity.setPosition(teleportPositionX, teleportPositionY);
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
}
