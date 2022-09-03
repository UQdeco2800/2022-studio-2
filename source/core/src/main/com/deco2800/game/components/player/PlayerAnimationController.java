package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

public class PlayerAnimationController extends Component {

    AnimationRenderComponent animator;

    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("movementIdle", this::movementIdle);
        entity.getEvents().addListener("movementUp", this::movementUp);
        entity.getEvents().addListener("movementDown", this::movementDown);
        entity.getEvents().addListener("movementLeft", this::movementLeft);
        entity.getEvents().addListener("movementRight", this::movementRight);

        entity.getEvents().trigger("movementIdle"); // Start out default idle animation
    }

    void movementIdle() {
        animator.startAnimation("idle");
    }

    void movementUp() {
        animator.startAnimation("up");
    }

    void movementDown() {
        animator.startAnimation("down");
    }

    void movementLeft() {
        animator.startAnimation("left");
    }

    void movementRight() {
        animator.startAnimation("right");
    }
}

