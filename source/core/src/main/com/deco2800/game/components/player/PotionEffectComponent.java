package com.deco2800.game.components.player;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.PotionStatsComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;

import static com.deco2800.game.components.player.PlayerModifier.MOVESPEED;

public class PotionEffectComponent extends Component {
    private short targetLayer;
    private float knockbackForce = 0f;
    //private PotionStatsComponent potionStats;
    private HitboxComponent hitboxComponent;
    private String effectType;
    private float effectValue;


    /**
     * Create a component which attacks entities on collision, without knockback.
     *
     * @param targetLayer The physics layer of the target's collider.
     */

    //player layer
    public PotionEffectComponent(short targetLayer, String effectType) {
        this.targetLayer = targetLayer;
        this.effectType = effectType; // make enum for this?
        switch (effectType) {
            case "speed":
                this.effectValue = 0.25f;
            default:
                ;
        }
    }

    public void create() {
        entity.getEvents().addListener("collisionStart", this::onCollisionStart);
        hitboxComponent = entity.getComponent(HitboxComponent.class);
    }

    private void onCollisionStart(Fixture me, Fixture other) {
        if (hitboxComponent.getFixture() != me) {
            // Not triggered by hitbox, ignore
            return;
        }
        if (!PhysicsLayer.contains(targetLayer, other.getFilterData().categoryBits)) {
            // Doesn't match target layer, ignore
            return;
        }
        Entity target = ((BodyUserData) other.getBody().getUserData()).entity;
        applyEffect(target);
    }

    private void applyEffect(Entity target) {
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
        // This should be called but for some reason it crashes the game
        //entity.dispose();
    }
}
