
package com.deco2800.game.components.player;

        import com.deco2800.game.components.Component;
        import com.deco2800.game.entities.Entity;
        import com.deco2800.game.rendering.AnimationRenderComponent;

/**
 * The controller for the player combat item animator. This controller locks the player combat item animator
 * to overlay the player entity and also uses event listeners to control the animation
 * of the combat item overlay.
 */
public class PlayerCombatAnimationController extends Component {

    Entity playerEntity;
    AnimationRenderComponent animator;

    /**
     * Creates a player combat item animation controller with the joined player entity with
     * which the animation controller overlays.
     *
     * @param playerEntity the player entity which the combat item animation controller overlays
     */
    public PlayerCombatAnimationController(Entity playerEntity) {
        this.playerEntity = playerEntity;
    }

    /**
     * Updates the combat item animator to always be overlaid the player entity
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
        entity.getEvents().addListener("hera", this::animateHera);
        entity.getEvents().addListener("heraAthenaDag", this::animateheraAthenaDag);
        entity.getEvents().trigger("attack");
    }

    /**
     * Triggers the hera item animation of the combat item animator.
     */
    void animateHera() {
        animator.startAnimation("hera");
    }

    /**
     * Triggers the Hera Athena item of the combat item animator.
     */
    void animateheraAthenaDag() {
        animator.startAnimation("heraAthenaDag");
    }
}

