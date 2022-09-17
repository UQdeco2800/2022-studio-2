package com.deco2800.game.entities.factories;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.ItemPickupComponent;
import com.deco2800.game.components.player.PlayerModifier;
import com.deco2800.game.components.player.PotionEffectComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;


/**
 * Factory to create a potion entity.
 *
 * <p>Each Potion entity type has a creation method returning the corresponding potion.
 */
public class PotionFactory {

    /**
     * Creates the base potion which can be modified to other types of potion.
     * @return base potion
     */
    public static Entity createBasePotion() {
        Entity potion = new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new ItemPickupComponent(PhysicsLayer.PLAYER));
        potion.setEntityType(EntityTypes.POTION);
        potion.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
        return potion;
    }

    /**
     * Creates speed potion
     * @return speed potion
     */
    public static Entity createSpeedPotion() {
        Entity speedPotion = createBasePotion()
                .addComponent(new TextureRenderComponent("images/Potions/agility_potion.png"
                ))
                .addComponent(new PotionEffectComponent(PhysicsLayer.PLAYER, "speed"));
        speedPotion.getComponent(TextureRenderComponent.class).scaleEntity();
        speedPotion.scaleHeight(1.0f);
        PhysicsUtils.setScaledCollider(speedPotion, 0.5f, 0.2f);
        return speedPotion;
    }

    /**
     * Creates speed potion without rendering Component.
     * @return speed potion for testing purposes
     */
    public static Entity createTestSpeedPotion() {
        Entity speedPotion = createBasePotion()
                .addComponent(new PotionEffectComponent(PhysicsLayer.PLAYER, "speed"));
        return speedPotion;
    }

    public static Entity createDefencePotion() {
        Entity potion = createBasePotion()
                .addComponent(new TextureRenderComponent("images/Potions/defence_potion.png"));

        potion.getComponent(TextureRenderComponent.class).scaleEntity();
        potion.scaleHeight(1.5f);
        PhysicsUtils.setScaledCollider(potion, 0.5f, 0.2f);
        return potion;
    }


    private static final String[] potionPictures = {
            "images/Potions/defence_potion.png",
            "images/Potions/agility_potion.png"
    };
}
