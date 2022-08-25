package com.deco2800.game.components.player;

import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;

/**
 * Skill component for managing player skills and the player state as a result of those skills.
 * Skill events should be initialised in create() and when triggered should call methods within this class.
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

    @Override
    public void create() {
    }

    @Override
    public void update() {
        if (this.dashing && System.currentTimeMillis() > this.dashEnd) {
            this.dashing = false;
        }
    }

    public boolean movementIsModified() {
        if (isDashing()) {
            return true;
        } else {
            return false;
        }
    }

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

    public boolean isDashing() {
        return this.dashing;
    }

    public void startDash(Vector2 moveDirection) {
        this.dashDirection = moveDirection;
        this.dashing = true;
        this.dashStart = System.currentTimeMillis();
        this.dashEnd = this.dashStart + DASH_LENGTH;
    }

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

    private Vector2 addVectors(Vector2 firstVector, Vector2 secondVector) {
        return new Vector2(firstVector.x + secondVector.x, firstVector.y + secondVector.y);
    }
}
