package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.utils.math.Vector2Utils;

public class PlayerAnimationController extends Component {

    AnimationRenderComponent animator;

    public static final String MOVEIDLE = "moveIdle";
    public static final String MOVEUP = "moveUp";
    public static final String MOVEDOWN = "moveDown";
    public static final String MOVELEFT = "moveLeft";
    public static final String MOVELEFTUP = "moveLeftUp";
    public static final String MOVELEFTDOWN = "moveLeftDown";
    public static final String MOVERIGHT = "moveRight";
    public static final String MOVERIGHTUP = "moveRightUp";
    public static final String MOVERIGHTDOWN = "moveRightDown";

    private static final Vector2 LEFTUP = new Vector2(-1f, 1f);
    private static final Vector2 LEFTDOWN = new Vector2(-1f, -1f);
    private static final Vector2 RIGHTUP = new Vector2(1f, 1f);
    private static final Vector2 RIGHTDOWN = new Vector2(1f, -1f);

    private static String currentAnimation;

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("movementIdle", this::movementIdle);
        entity.getEvents().addListener("movementHandle", (Vector2 move) -> handleMovement(move));

        entity.getEvents().trigger("movementIdle"); // Start out default idle animation
    }

    /**
     * Basic function to get the current movement animation.
     *
     * @return String enum that represents the movement animation.
     */
    public String getMovementAnimation() { return currentAnimation; }

    /**
     * Function for activating the idle movement animation
     * and setting the current animation variable.
     */
    private void movementIdle() {
        animator.startAnimation("idle");
        currentAnimation = MOVEIDLE;
    }

    /**
     * General movement handler to determine the animation to play
     * given the player movement vector. Updates the current movement animation
     * string variable appropriately. Movement input vector components should either
     * be 0 or 1.
     *
     * @param move  Movement Vector2 that determines the direction of movement.
     */
    private void handleMovement (Vector2 move) {

        // Cant do a switch for Vector2s UPSIDEDOWNSMILYFACE
        if (move.epsilonEquals(Vector2Utils.UP)) {
            animator.startAnimation("up");
            currentAnimation = MOVEUP;
        } else if (move.epsilonEquals(Vector2Utils.DOWN)) {
            animator.startAnimation("down");
            currentAnimation = MOVEDOWN;
        } else if (move.epsilonEquals(Vector2Utils.RIGHT)) {
            animator.startAnimation("right");
            currentAnimation = MOVERIGHT;
        } else if (move.epsilonEquals(Vector2Utils.LEFT)) {
            animator.startAnimation("left");
            currentAnimation = MOVELEFT;
        } else if (move.epsilonEquals(RIGHTUP)) {
            animator.startAnimation("up"); // Will change with appropriate diagonal movement sprite
            currentAnimation = MOVERIGHTUP;
        } else if (move.epsilonEquals(RIGHTDOWN)) {
            animator.startAnimation("down"); // Will change with appropriate diagonal movement sprite
            currentAnimation = MOVERIGHTDOWN;
        } else if (move.epsilonEquals(LEFTUP)) {
            animator.startAnimation("up"); // Will change with appropriate diagonal movement sprite
            currentAnimation = MOVELEFTUP;
        } else if (move.epsilonEquals(LEFTDOWN)) {
            animator.startAnimation("down"); // Will change with appropriate diagonal movement sprite
            currentAnimation = MOVELEFTDOWN;
        }
    }
}

