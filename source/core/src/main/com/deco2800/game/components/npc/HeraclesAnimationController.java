package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

/**
 * This class listens to events relevant to Heracles' state and plays the animation when one
 * of the events is triggered.
 */
public class HeraclesAnimationController extends Component {
    AnimationRenderComponent animator;

    /**
     * Creates the Heracles animation controller
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
        entity.getEvents().addListener("jumpFront", this::animateJumpFront);
        entity.getEvents().addListener("jumpBack", this::animateJumpBack);
        entity.getEvents().addListener("jumpLeft", this::animateJumpLeft);
        entity.getEvents().addListener("jumpRight", this::animateJumpRight);
        entity.getEvents().addListener("vanishFront", this::animateVanishFront);
        entity.getEvents().addListener("vanishBack", this::animateVanishBack);
        entity.getEvents().addListener("vanishLeft", this::animateVanishLeft);
        entity.getEvents().addListener("vanishRight", this::animateVanishRight);
        entity.getEvents().trigger("walkFront");
    }

    /**
     * Animates Heracles walking when facing right
     */
    private void animateWalkRight() {
        if (!"walk_right".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("walk_right");
        }
    }

    /**
     * Animates Heracles walking when facing left
     */
    private void animateWalkLeft() {
        if (!"walk_left".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("walk_left");
        }
    }

    /**
     * Animates Heracles walking when facing backwards
     */
    private void animateWalkBack() {
        if (!"walk_back".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("walk_back");
        }
    }

    /**
     * Animates Heracles walking when facing forwards
     */
    private void animateWalkFront() {
        if (!"walk_front".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("walk_front");
        }
    }

    /**
     * Animates Heracles attacking when facing right
     */
    private void animateDiscusAttackRight() {
        if (!"discus_attack_right".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("discus_attack_right");
        }
    }

    /**
     * Animates Heracles attacking when facing left
     */
    private void animateDiscusAttackLeft() {
        if (!"discus_attack_left".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("discus_attack_left");
        }
    }

    /**
     * Animates Heracles attacking when facing forwards
     */
    private void animateDiscusAttackFront() {
        if (!"discus_attack_front".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("discus_attack_front");
        }
    }

    /**
     * Animates Heracles attacking when facing backwards
     */
    private void animateDiscusAttackBack() {
        if (!"discus_attack_back".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("discus_attack_back");
        }
    }

    /**
     * Animates Heracles jumping when facing forwards
     */
    private void animateJumpFront() {
        if (!"jump_front".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("jump_front");
        }
    }

    /**
     * Animates Heracles jumping when facing backwards
     */
    private void animateJumpBack() {
        if (!"jump_back".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("jump_back");
        }
    }

    /**
     * Animates Heracles jumping when facing left
     */
    private void animateJumpLeft() {
        if (!"jump_left".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("jump_left");
        }
    }

    /**
     * Animates Heracles jumping when facing right
     */
    private void animateJumpRight() {
        if (!"jump_right".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("jump_right");
        }
    }

    /**
     * Animates Heracles vanishing when facing front
     */
    private void animateVanishFront() {
        if (!"vanish_front".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("vanish_front");
        }
    }

    /**
     * Animates Heracles vanishing when facing back
     */
    private void animateVanishBack() {
        if (!"vanish_back".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("vanish_back");
        }
    }

    /**
     * Animates Heracles vanishing when facing left
     */
    private void animateVanishLeft() {
        if (!"vanish_left".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("vanish_left");
        }
    }

    /**
     * Animates Heracles vanishing when facing right
     */
    private void animateVanishRight() {
        if (!"vanish_right".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("vanish_right");
        }
    }
}
