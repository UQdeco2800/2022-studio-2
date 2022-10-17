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
     * Creates the poop animation controller
     */
    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("walkFront", this::animateWalkFront);
        entity.getEvents().addListener("walkBack", this::animateWalkBack);
        entity.getEvents().addListener("walkLeft", this::animateWalkLeft);
        entity.getEvents().addListener("walkRight", this::animateWalkRight);
        entity.getEvents().addListener("vanishFront", this::animateVanishFront);
        entity.getEvents().addListener("vanishBack", this::animateVanishBack);
        entity.getEvents().addListener("vanishLeft", this::animateVanishLeft);
        entity.getEvents().addListener("vanishRight", this::animateVanishRight);
        entity.getEvents().trigger("walkFront");
    }

    /**
     * Animates the poop walking when facing right
     */
    private void animateWalkRight() {
        if (!"walk_right".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("walk_right");
        }
    }

    /**
     * Animates the poop walking when facing left
     */
    private void animateWalkLeft() {
        if (!"walk_left".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("walk_left");
        }
    }

    /**
     * Animates the poop walking when facing backwards
     */
    private void animateWalkBack() {
        if (!"walk_back".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("walk_back");
        }
    }

    /**
     * Animates the poop walking when facing forwards
     */
    private void animateWalkFront() {
        if (!"walk_front".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("walk_front");
        }
    }

    /**
     * Animates the poop vanishing when facing front
     */
    private void animateVanishFront() {
        if (!"vanish_front".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("vanish_front");
        }
    }

    /**
     * Animates the poop vanishing when facing backwards
     */
    private void animateVanishBack() {
        if (!"vanish_back".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("vanish_back");
        }
    }

    /**
     * Animates the poop vanishing when facing left
     */
    private void animateVanishLeft() {
        if (!"vanish_left".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("vanish_left");
        }
    }

    /**
     * Animates the poop vanishing when facing right
     */
    private void animateVanishRight() {
        if (!"vanish_right".equals(animator.getCurrentAnimation())) {
            animator.startAnimation("vanish_right");
        }
    }
}
