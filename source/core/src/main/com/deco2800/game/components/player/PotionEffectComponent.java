package com.deco2800.game.components.player;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;

import static com.deco2800.game.components.player.PlayerModifier.MOVESPEED;

public class PotionEffectComponent extends Component {
    private short targetLayer;
    private HitboxComponent hitboxComponent;
    private String effectType;
    private float effectValue;

    /**
     * Component that affects entities on collision.
     * @param targetLayer The physics layer of the target's collider.
     */

    public PotionEffectComponent(short targetLayer, String effectType) {
        this.targetLayer = targetLayer;
        this.effectType = effectType; // make enum for this later
        switch (effectType) {
            case "speed":
                this.effectValue = 0.50f;
            default:
                ;
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
     * @param me - player entity
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
        applyEffect(target);
    }

    /**
     * Applies component effect
     * @param target - the target entity (player)
     */
    public void applyEffect(Entity target) {
        PlayerModifier playerModifier = target.getComponent(PlayerModifier.class);
        switch (this.effectType) {
            case "speed":
                if (!playerModifier.checkModifier(MOVESPEED, this.effectValue, true, 60000)) {
                    // Modify does not exist
                    playerModifier.createModifier(MOVESPEED, this.effectValue, true, 60000);
                }
            default:
                ;
        }
    }
}
// This should be called in applyEffect but for some reason it crashes the game
//entity.dispose();