package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

/**
 * This class listens to events relevant to a gym bro entity's state and plays the animation when one
 * of the events is triggered.
 */
public class PoopAnimationController extends Component {
    AnimationRenderComponent animator;

    /**
     * Creates the gym bro animation controller
     */
    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("walkFront", this::animateWalkFront);
        entity.getEvents().addListener("walkBack", this::animateWalkBack);
        entity.getEvents().addListener("walkLeft", this::animateWalkLeft);
        entity.getEvents().addListener("walkRight", this::animateWalkRight);
        entity.getEvents().trigger("walkFront");
    }

    /**
     * Animates the gym bro walking when facing right
     */
    private void animateWalkRight() {
        if (animator.getCurrentAnimation() != "walk_right") {
            animator.startAnimation("walk_right");
        }
    }

    /**
     * Animates the gym bro walking when facing left
     */
    private void animateWalkLeft() {
        if (animator.getCurrentAnimation() != "walk_left") {
            animator.startAnimation("walk_left");
        }
    }

    /**
     * Animates the gym bro walking when facing backwards
     */
    private void animateWalkBack() {
        if (animator.getCurrentAnimation() != "walk_back") {
            animator.startAnimation("walk_back");
        }
    }

    /**
     * Animates the gym bro walking when facing forwards
     */
    private void animateWalkFront() {
        if (animator.getCurrentAnimation() != "walk_front") {
            animator.startAnimation("walk_front");
        }
    }
}
