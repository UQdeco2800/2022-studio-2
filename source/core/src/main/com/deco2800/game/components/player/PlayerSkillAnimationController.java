package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.rendering.AnimationRenderComponent;

/**
 * The controller for the player skill animator. This controller locks the player skill animator
 * to overlay the player entity and also uses event listeners to control the animation
 * of the skills overlay.
 */
public class PlayerSkillAnimationController extends Component {

    Entity playerEntity;
    AnimationRenderComponent animator;

    /**
     * Creates a player skill animation controller with the joined player entity with
     * which the animation controller overlays.
     * @param playerEntity the player entity which the skill animation controller overlays
     */
    public PlayerSkillAnimationController(Entity playerEntity) {
        this.playerEntity = playerEntity;
    }

    /**
     * Updates the skill animator to always be overlaid the player entity
     */
    @Override
    public void update() {
        // Set the position of the animator to the player position on every frame update
        this.entity.setPosition(playerEntity.getPosition().x, playerEntity.getPosition().y);
    }

    /**
     * Initialises all the required animation listeners and triggers the regular (initial)
     * animation
     */
    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        entity.getEvents().addListener("regularAnimation", this::animateRegular);
        entity.getEvents().addListener("teleportAnimation", this::animateTeleport);
        entity.getEvents().addListener("blockAnimation", this::animateBlock);
        entity.getEvents().addListener("dodgeAnimation", this::animateDodge);
        entity.getEvents().addListener("ultimateAnimation", this::animateUltimate);
        entity.getEvents().addListener("fireballAnimation", this::animateFireballUltimate);
        entity.getEvents().addListener("rootAnimation", this::animateRoot);
        entity.getEvents().addListener("invulnerabilityAnimation", this::animateInvulnerability);
        entity.getEvents().addListener("aoeAnimation", this::animateAOE);
        entity.getEvents().addListener("dashAnimation", this::animateDash);
        entity.getEvents().trigger("regularAnimation");
    }

    private void animateAOE() {
        animator.startAnimation("aoe");
    }

    private void animateInvulnerability() {
        animator.startAnimation("invulnerability");
    }

    private void animateFireballUltimate() {
        animator.startAnimation("fireballUltimate");
    }

    /**
     * Triggers the regular state animation of the skill animator.
     */
    void animateRegular() {
        animator.startAnimation("no_animation");
    }

    /**
     * Triggers the teleport charge animation.
     */
    void animateTeleport() {
        animator.startAnimation("teleport");
    }

    /**
     * Triggers the block animation.
     */
    void animateBlock() {
        animator.startAnimation("block");
    }

    /**
     * Triggers the block animation.
     */
    void animateDodge() {
        animator.startAnimation("dodge");
    }

    /**
     * Triggers the dash animation.
     */
    void animateDash() {
        animator.startAnimation("dash");
    }

    /**
     * Triggers the timestop animation.
     */
    void animateUltimate() {
        animator.startAnimation("vendemaire");
    }

    /**
     * Triggers the attackspeed animation.
     */
    void animateRoot() {
        animator.startAnimation("attackSpeed");
    }
}