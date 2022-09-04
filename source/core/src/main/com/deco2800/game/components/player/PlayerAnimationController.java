package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.utils.math.Vector2Utils;

import java.util.ArrayList;

public class PlayerAnimationController extends Component {

    AnimationRenderComponent animator;

    private static final Vector2 LEFTUP = new Vector2(-1f, 1f);
    private static final Vector2 LEFTDOWN = new Vector2(-1f, -1f);
    private static final Vector2 RIGHTUP = new Vector2(1f, 1f);
    private static final Vector2 RIGHTDOWN = new Vector2(1f, -1f);

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("movementIdle", this::movementIdle);
        entity.getEvents().addListener("movementHandle", (Vector2 move) -> handleMovement(move));

        entity.getEvents().trigger("movementIdle"); // Start out default idle animation
    }

    private void movementIdle() {
        animator.startAnimation("idle");
    }

    private void handleMovement (Vector2 move) {

        // Cant do a switch for Vector2s UPSIDEDOWNSMILYFACE
        if (move.epsilonEquals(Vector2Utils.UP)) {
            animator.startAnimation("up");
        } else if (move.epsilonEquals(Vector2Utils.DOWN)) {
            animator.startAnimation("down");
        } else if (move.epsilonEquals(Vector2Utils.RIGHT)) {
            animator.startAnimation("right");
        } else if (move.epsilonEquals(Vector2Utils.LEFT)) {
            animator.startAnimation("left");
        } else if (move.epsilonEquals(RIGHTUP)) {
            animator.startAnimation("up"); // Will change with appropriate diagonal movement sprite
        } else if (move.epsilonEquals(RIGHTDOWN)) {
            animator.startAnimation("down"); // Will change with appropriate diagonal movement sprite
        } else if (move.epsilonEquals(LEFTUP)) {
            animator.startAnimation("up"); // Will change with appropriate diagonal movement sprite
        } else if (move.epsilonEquals(LEFTDOWN)) {
            animator.startAnimation("down"); // Will change with appropriate diagonal movement sprite
        }
    }
}

