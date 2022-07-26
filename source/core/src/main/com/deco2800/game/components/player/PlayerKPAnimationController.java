package com.deco2800.game.components.player;

import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.rendering.AnimationRenderComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The controller for the player skill animator. This controller locks the player skill animator
 * to overlay the player entity and also uses event listeners to control the animation
 * of the skills overlay.
 */
public class PlayerKPAnimationController extends Component {

    Entity playerEntity;
    AnimationRenderComponent animator;
    private static Logger logger = LoggerFactory.getLogger(PlayerKPAnimationController.class);

    /**
     * Creates a player skill animation controller with the joined player entity with
     * which the animation controller overlays.
     * @param playerEntity the player entity which the skill animation controller overlays
     */
    public PlayerKPAnimationController(Entity playerEntity) {
        this.playerEntity = playerEntity;
    }

    /**
     * Updates the skill animator to always be overlaid the player entity
     */
    @Override
    public void update() {
        // Set the position of the animator to the player position on every frame update
        this.entity.setPosition(playerEntity.getPosition().x, playerEntity.getPosition().y+1);
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
        entity.getEvents().addListener("KeyQAnimation", this::animateKeyQ);
        entity.getEvents().addListener("KeyNpcAnimation", this::animateKeyNpc);
        entity.getEvents().trigger("regularAnimation");
    }

    /**
     * Triggers the regular state animation of the skill animator.
     */
    void animateRegular() {
        animator.startAnimation("default");
    }

    /**
     * Triggers the key q prompt animation.
     */
    void animateKeyQ() {
        logger.info("TEST KEY PROMPT ANIMATION CALL");
        animator.startAnimation("Q");
    }
     void animateKeyNpc() {
            logger.info("TEST KEY PROMPT ANIMATION CALL");
            animator.startAnimation("Npc");
        }

}