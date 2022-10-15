package com.deco2800.game.components.player;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;

import static java.util.Objects.isNull;

public class PotionEffectComponent extends Component {
    private short targetLayer;
    private HitboxComponent hitboxComponent;
    private String effectType;
    private float effectValue;

    /**
     * Component that affects entities on collision.
     *
     * @param targetLayer The physics layer of the target's collider.
     */

    public PotionEffectComponent(short targetLayer, String effectType) {
        this.targetLayer = targetLayer;
        this.effectType = effectType; // make enum for this later
        switch (effectType) {
            case "speed":
                this.effectValue = 1.5f;
                break;
            case "health":
                this.effectValue = 20f;
                break;
            case "damageReduction":
                this.effectValue = 2f;
                break;
            case "stamina":
                this.effectValue = 30f;
                break;
            default:
                break;
        }
    }

    /**
     * Creates component attached to potion entity
     */
    public void create() {
        entity.getEvents().addListener("collisionStart", this::onCollisionStart);
        hitboxComponent = entity.getComponent(HitboxComponent.class);
    }

    /**
     * Applies component effect on collision
     *
     * @param me    - player entity
     * @param other - other entity
     */
    private void onCollisionStart(Fixture me, Fixture other) {
        if (hitboxComponent.getFixture() != me) {
            return;
        }
        if (!PhysicsLayer.contains(targetLayer, other.getFilterData().categoryBits)) {
            return;
        }
        Entity target = ((BodyUserData) other.getBody().getUserData()).entity;
//        if (!target.checkEntityType(EntityTypes.PLAYER)) {
//            System.out.println("Not player pickup, applying effect");
//            applyEffect(target);
//        }
    }

    /**
     * Returns component effect
     */
    public String getPotionEffect() {
        return effectType;
    }

    /**
     * Returns if the two potion has the same effect
     *
     * @param other the potion to be checked
     * @return true if both have the same effect type, false otherwise
     * @requires potion.checkEntityType(EntityTypes.POTION) == true
     */
    public boolean equals(Entity other) {
        return this.effectType.equals(other.getComponent(PotionEffectComponent.class).getPotionEffect());
    }

    /**
     * Returns if the two potion has the same effect
     *
     * @param effectType the potion effect type
     * @return true if the effect type matches, false otherwise
     */
    public boolean equals(String effectType) {
        return this.effectType.equals(effectType);
    }

    /**
     * Applies component effect
     *
     * @param target - the target entity (player)
     */
    public void applyEffect(Entity target) {
        PlayerModifier playerModifier = target.getComponent(PlayerModifier.class);

        if (!isNull(playerModifier)) {
            switch (this.effectType) {
                case "speed":
                    if (!playerModifier.checkModifier(PlayerModifier.MOVESPEED, this.effectValue,
                            true, 3000)) {
                        // Modify does not exist
                        playerModifier.createModifier(PlayerModifier.MOVESPEED, this.effectValue,
                                true, 3000);
                    }
                    break;
                case "health":
                    playerModifier.createModifier(PlayerModifier.HEALTH, this.effectValue, false, 0);
                    break;
                case "stamina":
                    playerModifier.createModifier(PlayerModifier.STAMINA, this.effectValue, false,0);
                    break;
                case "damageReduction":
                    if (!playerModifier.checkModifier(PlayerModifier.DMGREDUCTION, this.effectValue, false,
                            3000)) {
                        // Modify does not exist
                        playerModifier.createModifier(PlayerModifier.DMGREDUCTION,
                                this.effectValue,
                                false,
                                3000);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
// This should be called in applyEffect but for some reason it crashes the game
//entity.dispose();