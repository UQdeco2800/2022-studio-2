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
    private void animateWalkRight() { animator.startAnimation("walk_right"); }

    /**
     * Animates the gym bro walking when facing left
     */
    private void animateWalkLeft() { animator.startAnimation("walk_left"); }

    /**
     * Animates the gym bro walking when facing backwards
     */
    private void animateWalkBack() { animator.startAnimation("walk_back"); }

    /**
     * Animates the gym bro walking when facing forwards
     */
    private void animateWalkFront() { animator.startAnimation("walk_front"); }

    /**
     * Animates the gym bro attacking when facing right
     */
    private void animateAttackRight() { animator.startAnimation("attack_right"); }

    /**
     * Animates the gym bro attacking when facing left
     */
    private void animateAttackLeft() { animator.startAnimation("attack_left"); }

    /**
     * Animates the gym bro attacking when facing forwards
     */
    void animateAttackFront() { animator.startAnimation("attack_front"); }

    /**
     * Animates the gym bro attacking when facing backwards
     */
    void animateAttackBack() { animator.startAnimation("attack_back"); }
}
