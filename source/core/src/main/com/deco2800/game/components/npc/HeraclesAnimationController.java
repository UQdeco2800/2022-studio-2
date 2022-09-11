package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

/**
 * This class listens to events relevant to a gym bro entity's state and plays the animation when one
 * of the events is triggered.
 */
public class HeraclesAnimationController extends Component {
    AnimationRenderComponent animator;

    /**
     * Creates the gym bro animation controller
     */
    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("discusAttackFront", this::animateDiscusAttackFront);
        entity.getEvents().addListener("discusAttackBack", this::animateDiscusAttackBack);
        entity.getEvents().addListener("discusAttackLeft", this::animateDiscusAttackLeft);
        entity.getEvents().addListener("discusAttackRight", this::animateDiscusAttackRight);
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
    private void animateDiscusAttackRight() {
        if (animator.getCurrentAnimation() != "discus_attack_right") {
            animator.startAnimation("discus_attack_right");
        }
    }

    /**
     * Animates the gym bro attacking when facing left
     */
    private void animateDiscusAttackLeft() {
        if (animator.getCurrentAnimation() != "discus_attack_left") {
            animator.startAnimation("discus_attack_left");
        }
    }

    /**
     * Animates the gym bro attacking when facing forwards
     */
    private void animateDiscusAttackFront() {
        if (animator.getCurrentAnimation() != "discus_attack_front") {
            animator.startAnimation("discus_attack_front");
        }
    }

    /**
     * Animates the gym bro attacking when facing backwards
     */
    private void animateDiscusAttackBack() {
        if (animator.getCurrentAnimation() != "discus_attack_back") {
            animator.startAnimation("discus_attack_back");
        }
    }
}
