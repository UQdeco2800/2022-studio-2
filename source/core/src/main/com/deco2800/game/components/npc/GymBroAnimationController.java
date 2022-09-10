package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

/**
 * This class listens to events relevant to a gym bro entity's state and plays the animation when one
 * of the events is triggered.
 */
public class GymBroAnimationController extends Component {
    AnimationRenderComponent animator;

    /**
     * Creates the gym bro animation controller
     */
    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("attackFront", this::animateAttackFront);
        entity.getEvents().addListener("attackBack", this::animateAttackBack);
        entity.getEvents().addListener("attackLeft", this::animateAttackLeft);
        entity.getEvents().addListener("attackRight", this::animateAttackRight);
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

    /**
     * Animates the gym bro attacking when facing right
     */
    private void animateAttackRight() {
        if (animator.getCurrentAnimation() != "attack_right") {
            animator.startAnimation("attack_right");
        }
    }

    /**
     * Animates the gym bro attacking when facing left
     */
    private void animateAttackLeft() {
        if (animator.getCurrentAnimation() != "attack_left") {
            animator.startAnimation("attack_left");
        }
    }

    /**
     * Animates the gym bro attacking when facing forwards
     */
    private void animateAttackFront() {
        if (animator.getCurrentAnimation() != "attack_front") {
            animator.startAnimation("attack_front");
        }
    }

    /**
     * Animates the gym bro attacking when facing backwards
     */
    private void animateAttackBack() {
        if (animator.getCurrentAnimation() != "attack_back") {
            animator.startAnimation("attack_back");
        }
    }
}