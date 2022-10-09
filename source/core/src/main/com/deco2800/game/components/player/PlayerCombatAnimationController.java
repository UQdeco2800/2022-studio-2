
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
     * Initialises all the required animation listeners
     */
    @Override
    public void create() {
        super.create();
        animator = this.entity.getComponent(AnimationRenderComponent.class);

        /*athena animation listeners*/
        entity.getEvents().addListener("athena", this::animateAthena);
        entity.getEvents().addListener("athenaDamage", this::animateAthenaDamage);
        entity.getEvents().addListener("athenaDamageStatic", this::animateAthenaDamageStatic);
        entity.getEvents().addListener("athenaFire", this::animateAthenaFire);
        entity.getEvents().addListener("athenaFireStatic", this::animateAthenaFireStatic);
        entity.getEvents().addListener("athenaPoison", this::animateAthenaPoison);
        entity.getEvents().addListener("athenaPoisonStatic", this::animateAthenaPoisonStatic);
        entity.getEvents().addListener("athenaSpeed", this::animateAthenaSpeed);
        entity.getEvents().addListener("athenaSpeedStatic", this::animateAthenaSpeedStatic);
        entity.getEvents().addListener("athenaStatic", this::animateAthenaStatic);

        /*hera animation listeners*/
        entity.getEvents().addListener("hera", this::animateHera);
        entity.getEvents().addListener("heraDamage", this::animateHeraDamage);
        entity.getEvents().addListener("heraDamageStatic", this::animateHeraDamageStatic);
        entity.getEvents().addListener("heraFire", this::animateHeraFire);
        entity.getEvents().addListener("heraFireStatic", this::animateHeraFireStatic);
        entity.getEvents().addListener("heraPoison", this::animateHeraPoison);
        entity.getEvents().addListener("heraPoisonStatic", this::animateHeraPoisonStatic);
        entity.getEvents().addListener("heraSpeed", this::animateHeraSpeed);
        entity.getEvents().addListener("heraSpeedStatic", this::animateHeraSpeedStatic);
        entity.getEvents().addListener("heraStatic", this::animateHeraStatic);

        /*hera athena animation listeners*/
        entity.getEvents().addListener("heraAthena", this::animateHeraAthena);
        entity.getEvents().addListener("heraAthenaDamage", this::animateHeraAthenaDamage);
        entity.getEvents().addListener("heraAthenaDamageStatic", this::animateHeraAthenaDamageStatic);
        entity.getEvents().addListener("heraAthenaFire", this::animateHeraAthenaFire);
        entity.getEvents().addListener("heraAthenaFireStatic", this::animateHeraAthenaFireStatic);
        entity.getEvents().addListener("heraAthenaPoison", this::animateHeraAthenaPoison);
        entity.getEvents().addListener("heraAthenaPoisonStatic", this::animateHeraAthenaPoisonStatic);
        entity.getEvents().addListener("heraAthenaSpeed", this::animateHeraAthenaSpeed);
        entity.getEvents().addListener("heraAthenaSpeedStatic", this::animateHeraAthenaSpeedStatic);
        entity.getEvents().addListener("heraAthenaStatic", this::animateHeraAthenaStatic);

        /*pipe animation listeners*/
        entity.getEvents().addListener("pipe", this::animatePipe);
        entity.getEvents().addListener("pipeDamage", this::animatePipeDamage);
        entity.getEvents().addListener("pipeDamageStatic", this::animatePipeDamageStatic);
        entity.getEvents().addListener("pipeFire", this::animatePipeFire);
        entity.getEvents().addListener("pipeFireStatic", this::animatePipeFireStatic);
        entity.getEvents().addListener("pipePoison", this::animatePipePoison);
        entity.getEvents().addListener("pipePoisonStatic", this::animatePipePoisonStatic);
        entity.getEvents().addListener("pipeSpeed", this::animatePipeSpeed);
        entity.getEvents().addListener("pipeSpeedStatic", this::animatePipeSpeedStatic);
        entity.getEvents().addListener("pipeStatic", this::animatePipeStatic);

        /*plunger animation listeners*/
        entity.getEvents().addListener("plunger", this::animatePlunger);
        entity.getEvents().addListener("plungerDamage", this::animatePlungerDamage);
        entity.getEvents().addListener("plungerDamageStatic", this::animatePlungerDamageStatic);
        entity.getEvents().addListener("plungerFire", this::animatePlungerFire);
        entity.getEvents().addListener("plungerFireStatic", this::animatePlungerFireStatic);
        entity.getEvents().addListener("plungerPoison", this::animatePlungerPoison);
        entity.getEvents().addListener("plungerPoisonStatic", this::animatePlungerPoisonStatic);
        entity.getEvents().addListener("plungerSpeed", this::animatePlungerSpeed);
        entity.getEvents().addListener("plungerSpeedStatic", this::animatePlungerSpeedStatic);
        entity.getEvents().addListener("plungerStatic", this::animatePlungerStatic);

        /*sword animation listeners*/
        entity.getEvents().addListener("sword", this::animateSword);
        entity.getEvents().addListener("swordDamage", this::animateSwordDamage);
        entity.getEvents().addListener("swordDamageStatic", this::animateSwordDamageStatic);
        entity.getEvents().addListener("swordFire", this::animateSwordFire);
        entity.getEvents().addListener("swordFireStatic", this::animateSwordFireStatic);
        entity.getEvents().addListener("swordPoison", this::animateSwordPoison);
        entity.getEvents().addListener("swordPoisonStatic", this::animateSwordPoisonStatic);
        entity.getEvents().addListener("swordSpeed", this::animateSwordSpeed);
        entity.getEvents().addListener("swordSpeedStatic", this::animateSwordSpeedStatic);
        entity.getEvents().addListener("swordStatic", this::animateSwordStatic);

        /*trident animation listeners*/
        entity.getEvents().addListener("trident", this::animateTrident);
        entity.getEvents().addListener("tridentDamage", this::animateTridentDamage);
        entity.getEvents().addListener("tridentDamageStatic", this::animateTridentDamageStatic);
        entity.getEvents().addListener("tridentFire", this::animateTridentFire);
        entity.getEvents().addListener("tridentFireStatic", this::animateTridentFireStatic);
        entity.getEvents().addListener("tridentPoison", this::animateTridentPoison);
        entity.getEvents().addListener("tridentPoisonStatic", this::animateTridentPoisonStatic);
        entity.getEvents().addListener("tridentSpeed", this::animateTridentSpeed);
        entity.getEvents().addListener("tridentSpeedStatic", this::animateTridentSpeedStatic);
        entity.getEvents().addListener("tridentStatic", this::animateTridentStatic);

        /*plunger bow animation listeners*/
        entity.getEvents().addListener("plungerBow", this::animatePlungerBow);
        entity.getEvents().addListener("plungerBowDamage", this::animatePlungerBowDamage);
        entity.getEvents().addListener("plungerBowDamageStatic", this::animatePlungerBowDamageStatic);
        entity.getEvents().addListener("plungerBowFire", this::animatePlungerBowFire);
        entity.getEvents().addListener("plungerBowFireStatic", this::animatePlungerBowFireStatic);
        entity.getEvents().addListener("plungerBowPoison", this::animatePlungerBowPoison);
        entity.getEvents().addListener("plungerBowPoisonStatic", this::animatePlungerBowPoisonStatic);
        entity.getEvents().addListener("plungerBowSpeed", this::animatePlungerBowSpeed);
        entity.getEvents().addListener("plungerBowSpeedStatic", this::animatePlungerBowSpeedStatic);
        entity.getEvents().addListener("plungerBowStatic", this::animatePlungerBowStatic);

        entity.getEvents().trigger("attack");
    }

    /**
     * Triggers the hera item animation of the combat item animator.
     */
    public void animateHera() {
        animator.startAnimation("hera");
    }
    void animateHeraDamage() {
        animator.startAnimation("heraDamage");
    }
    void animateHeraDamageStatic() {
        animator.startAnimation("heraDamageStatic");
    }
    void animateHeraFire() {
        animator.startAnimation("heraFire");
    }
    void animateHeraFireStatic() {
        animator.startAnimation("heraFireStatic");
    }
    void animateHeraPoison() {
        animator.startAnimation("heraPoison");
    }
    void animateHeraPoisonStatic() {
        animator.startAnimation("heraPoisonStatic");
    }
    void animateHeraSpeed() {
        animator.startAnimation("heraSpeed");
    }

    void animateHeraSpeedStatic() {
        animator.startAnimation("heraSpeedStatic");
    }

    void animateHeraStatic() {
        animator.startAnimation("heraStatic");
    }

    /**
     * Triggers the Athena item of the combat item animator.
     */

    void animateAthena() {
        animator.startAnimation("athena");
    }
    void animateAthenaDamage() {
        animator.startAnimation("athenaDamage");
    }
    void animateAthenaDamageStatic() {
        animator.startAnimation("athenaDamageStatic");
    }
    void animateAthenaFire() {
        animator.startAnimation("athenaFire");
    }
    void animateAthenaFireStatic() {
        animator.startAnimation("athenaFireStatic");
    }
    void animateAthenaPoison() {
        animator.startAnimation("athenaPoison");
    }
    void animateAthenaPoisonStatic() {
        animator.startAnimation("athenaPoisonStatic");
    }
    void animateAthenaSpeed() {
        animator.startAnimation("athenaSpeed");
    }

    void animateAthenaSpeedStatic() {
        animator.startAnimation("athenaSpeedStatic");
    }

    void animateAthenaStatic() {
        animator.startAnimation("athenaStatic");
    }

    /**
     * Triggers the Hera Athena item of the combat item animator.
     */


    void animateHeraAthena() {
        animator.startAnimation("heraAthena");
    }
    void animateHeraAthenaDamage() {
        animator.startAnimation("heraAthenaDamage");
    }
    void animateHeraAthenaDamageStatic() {
        animator.startAnimation("heraAthenaDamageStatic");
    }
    void animateHeraAthenaFire() {
        animator.startAnimation("heraAthenaFire");
    }
    void animateHeraAthenaFireStatic() {
        animator.startAnimation("heraAthenaFireStatic");
    }
    void animateHeraAthenaPoison() {
        animator.startAnimation("heraAthenaPoison");
    }
    void animateHeraAthenaPoisonStatic() {
        animator.startAnimation("heraAthenaPoisonStatic");
    }
    void animateHeraAthenaSpeed() {
        animator.startAnimation("heraAthenaSpeed");
    }

    void animateHeraAthenaSpeedStatic() {
        animator.startAnimation("heraAthenaSpeedStatic");
    }

    void animateHeraAthenaStatic() {
        animator.startAnimation("heraAthenaStatic");
    }

    /**
     * Triggers the Pipe item of the combat item animator.
     */

    void animatePipe() {
        animator.startAnimation("pipe");
    }
    void animatePipeDamage() {
        animator.startAnimation("pipeDamage");
    }
    void animatePipeDamageStatic() {
        animator.startAnimation("pipeDamageStatic");
    }
    void animatePipeFire() {
        animator.startAnimation("pipeFire");
    }
    void animatePipeFireStatic() {
        animator.startAnimation("pipeFireStatic");
    }
    void animatePipePoison() {
        animator.startAnimation("pipePoison");
    }
    void animatePipePoisonStatic() {
        animator.startAnimation("pipePoisonStatic");
    }
    void animatePipeSpeed() {
        animator.startAnimation("pipeSpeed");
    }

    void animatePipeSpeedStatic() {
        animator.startAnimation("pipeSpeedStatic");
    }

    void animatePipeStatic() {
        animator.startAnimation("pipeStatic");
    }

    /**
     * Triggers the Plunger item of the combat item animator.
     */

    void animatePlunger() {
        animator.startAnimation("plunger");
    }
    void animatePlungerDamage() {
        animator.startAnimation("plungerDamage");
    }
    void animatePlungerDamageStatic() {
        animator.startAnimation("plungerDamageStatic");
    }
    void animatePlungerFire() {
        animator.startAnimation("plungerFire");
    }
    void animatePlungerFireStatic() {
        animator.startAnimation("plungerFireStatic");
    }
    void animatePlungerPoison() {
        animator.startAnimation("plungerPoison");
    }
    void animatePlungerPoisonStatic() {
        animator.startAnimation("plungerPoisonStatic");
    }
    void animatePlungerSpeed() {
        animator.startAnimation("plungerSpeed");
    }

    void animatePlungerSpeedStatic() {
        animator.startAnimation("plungerSpeedStatic");
    }

    void animatePlungerStatic() {
        animator.startAnimation("plungerStatic");
    }

    /**
     * Triggers the Sword item of the combat item animator.
     */

    void animateSword() {
        animator.startAnimation("sword");
    }
    void animateSwordDamage() {
        animator.startAnimation("swordDamage");
    }
    void animateSwordDamageStatic() {
        animator.startAnimation("swordDamageStatic");
    }
    void animateSwordFire() {
        animator.startAnimation("swordFire");
    }
    void animateSwordFireStatic() {
        animator.startAnimation("swordFireStatic");
    }
    void animateSwordPoison() {
        animator.startAnimation("swordPoison");
    }
    void animateSwordPoisonStatic() {
        animator.startAnimation("swordPoisonStatic");
    }
    void animateSwordSpeed() {
        animator.startAnimation("swordSpeed");
    }

    void animateSwordSpeedStatic() {
        animator.startAnimation("swordSpeedStatic");
    }

    void animateSwordStatic() {
        animator.startAnimation("swordStatic");
    }

    /**
     * Triggers the Trident item of the combat item animator.
     */

    void animateTrident() {
        animator.startAnimation("trident");
    }
    void animateTridentDamage() {
        animator.startAnimation("tridentDamage");
    }
    void animateTridentDamageStatic() {
        animator.startAnimation("tridentDamageStatic");
    }
    void animateTridentFire() {
        animator.startAnimation("tridentFire");
    }
    void animateTridentFireStatic() {
        animator.startAnimation("tridentFireStatic");
    }
    void animateTridentPoison() {
        animator.startAnimation("tridentPoison");
    }
    void animateTridentPoisonStatic() {
        animator.startAnimation("tridentPoisonStatic");
    }
    void animateTridentSpeed() {
        animator.startAnimation("tridentSpeed");
    }

    void animateTridentSpeedStatic() {
        animator.startAnimation("tridentSpeedStatic");
    }

    void animateTridentStatic() {
        animator.startAnimation("tridentStatic");
    }

    /**
     * Triggers the Plunger Bow item of the combat item animator.
     */

    void animatePlungerBow() {
        animator.startAnimation("plungerBow");
    }
    void animatePlungerBowDamage() {
        animator.startAnimation("plungerBowDamage");
    }
    void animatePlungerBowDamageStatic() {
        animator.startAnimation("plungerBowDamageStatic");
    }
    void animatePlungerBowFire() {
        animator.startAnimation("plungerBowFire");
    }
    void animatePlungerBowFireStatic() {
        animator.startAnimation("plungerBowFireStatic");
    }
    void animatePlungerBowPoison() {
        animator.startAnimation("plungerBowPoison");
    }
    void animatePlungerBowPoisonStatic() {
        animator.startAnimation("plungerBowPoisonStatic");
    }
    void animatePlungerBowSpeed() {
        animator.startAnimation("plungerBowSpeed");
    }

    void animatePlungerBowSpeedStatic() {
        animator.startAnimation("plungerBowSpeedStatic");
    }

    void animatePlungerBowStatic() {
        animator.startAnimation("plungerBowStatic");
    }
}

