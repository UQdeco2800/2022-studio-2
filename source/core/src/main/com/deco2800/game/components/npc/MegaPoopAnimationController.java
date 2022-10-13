package com.deco2800.game.components.npc;

import com.deco2800.game.components.Component;
import com.deco2800.game.rendering.AnimationRenderComponent;

/**
 * This class listens to events relevant to a Mega Poop entity's state and plays the animation when one
 * of the events is triggered.
 */
public class MegaPoopAnimationController extends Component {
    AnimationRenderComponent animator;

    /**
     * Creates the Mega Poop animation controller
     */
    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("walkFront", this::animateWalkFront);
        entity.getEvents().addListener("walkBack", this::animateWalkBack);
        entity.getEvents().addListener("walkLeft", this::animateWalkLeft);
        entity.getEvents().addListener("walkRight", this::animateWalkRight);
        entity.getEvents().addListener("projectileAttackFront", this::animateAttackFront);
        entity.getEvents().addListener("projectileAttackBack", this::animateAttackBack);
        entity.getEvents().addListener("projectileAttackLeft", this::animateAttackLeft);
        entity.getEvents().addListener("projectileAttackRight", this::animateAttackRight);
        entity.getEvents().addListener("cast", this::animateCast);
        entity.getEvents().addListener("vanishFront", this::animateVanishFront);
        entity.getEvents().addListener("vanishBack", this::animateVanishBack);
        entity.getEvents().addListener("vanishLeft", this::animateVanishLeft);
        entity.getEvents().addListener("vanishRight", this::animateVanishRight);
        entity.getEvents().trigger("walkFront");
    }

    /**
     * Animates Mega Poop walking when facing right
     */
    private void animateWalkRight() {
        if (!"walk_right".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("walk_right");
        }
    }

    /**
     * Animates Mega Poop walking when facing left
     */
    private void animateWalkLeft() {
        if (!"walk_left".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("walk_left");
        }
    }

    /**
     * Animates Mega Poop walking when facing backwards
     */
    private void animateWalkBack() {
        if (!"walk_back".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("walk_back");
        }
    }

    /**
     * Animates Mega Poop walking when facing forwards
     */
    private void animateWalkFront() {
        if (!"walk_front".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("walk_front");
        }
    }

    /**
     * Animates Mega Poop casting
     */
    private void animateCast() {
        if (!"cast".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("cast");
        }
    }

    /**
     * Animates Mega Poop walking when attacking right
     */
    private void animateAttackRight() {
        if (!"projectile_attack_right".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("projectile_attack_right");
        }
    }

    /**
     * Animates Mega Poop walking when attacking left
     */
    private void animateAttackLeft() {
        if (!"projectile_attack_left".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("projectile_attack_left");
        }
    }

    /**
     * Animates Mega Poop walking when attacking backwards
     */
    private void animateAttackBack() {
        if (!"projectile_attack_back".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("projectile_attack_back");
        }
    }

    /**
     * Animates Mega Poop walking when attacking forwards
     */
    private void animateAttackFront() {
        if (!"projectile_attack_front".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("projectile_attack_front");
        }
    }

    private void animateVanishFront() {
        if (!"vanish_front".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("vanish_front");
        }
    }

    private void animateVanishBack() {
        if (!"vanish_back".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("vanish_back");
        }
    }

    private void animateVanishLeft() {
        if (!"vanish_left".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("vanish_left");
        }
    }

    private void animateVanishRight() {
        if (!"vanish_right".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("vanish_right");
        }
    }
}
