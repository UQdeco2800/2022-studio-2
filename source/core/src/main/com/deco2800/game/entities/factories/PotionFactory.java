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
     * Creates the base potion which can be modified to other types of potion. This potion on its
     * own has no effects
     * @return base potion
     */
    public static Entity createBasePotion() {
        Entity potion = new Entity()
                .addComponent(new PhysicsComponent().setBodyType(BodyDef.BodyType.StaticBody))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new ItemPickupComponent(PhysicsLayer.PLAYER));
        potion.setEntityType(EntityTypes.POTION);
        return potion;
    }

    /**
     * Creates speed potion which increases the players movement speed by a set amount
     * @return speed potion
     */
    public static Entity createSpeedPotion() {
        Entity speedPotion = createBasePotion()
                .addComponent(new TextureRenderComponent("images/Potions/agility_potion.png"
                ))
                .addComponent(new PotionEffectComponent(PhysicsLayer.PLAYER, "speed"));
        speedPotion.getComponent(TextureRenderComponent.class).scaleEntity();
        speedPotion.scaleHeight(1.0f);
        return speedPotion;
    }

    /**
     * Creates a health potion which gives the player health all at once
     * @return health potion
     */

    public static Entity createHealthPotion() {
        Entity healthPotion = createBasePotion()
                .addComponent(new TextureRenderComponent("images/Potions/health_potion.png"
                ))
                .addComponent(new PotionEffectComponent(PhysicsLayer.PLAYER, "health"));
        healthPotion.getComponent(TextureRenderComponent.class).scaleEntity();
        healthPotion.scaleHeight(1.0f);
        return healthPotion;
    }

    /**
     * Creates a stamina potion which gives the player health all at once
     * @return stamina potion
     */

    public static Entity createStaminaPotion() {
        Entity staminaPotion = createBasePotion()
                .addComponent(new TextureRenderComponent("images/Potions/defence_potion.png"
                ))
                .addComponent(new PotionEffectComponent(PhysicsLayer.PLAYER, "stamina"));
        staminaPotion.getComponent(TextureRenderComponent.class).scaleEntity();
        staminaPotion.scaleHeight(1.0f);
        return staminaPotion;
    }


    /**
     * creates a defence potion. This is scheduled for removal as it is too similar to other
     * potions and because too many potions already exist in the game
     * @return defence potion
     */
    public static Entity createDefencePotion() {
        Entity potion = createBasePotion()
                .addComponent(new TextureRenderComponent("images/Potions/defence_potion.png"))
                .addComponent(new PotionEffectComponent(PhysicsLayer.PLAYER, "damageReduction"));
        potion.getComponent(TextureRenderComponent.class).scaleEntity();
        potion.scaleHeight(1.5f);
        return potion;
    }

    /**
     * Create a health regen potion which slows gives the player health every few frames
     * @return health regen potion
     */
    public static Entity createHealthRegenPotion() {
        Entity healthRegenPotion = createBasePotion()
                .addComponent(new TextureRenderComponent("images/Potions/defence_potion.png"
                ))
                .addComponent(new PotionEffectComponent(PhysicsLayer.PLAYER, "health"));
        healthRegenPotion.getComponent(TextureRenderComponent.class).scaleEntity();
        healthRegenPotion.scaleHeight(1.0f);
        return healthRegenPotion;
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

    /**
     * Creates health potion without rendering Component.
     * @return health potion for testing purposes
     */
    public static Entity createTestHealthPotion() {
        Entity potion = createBasePotion()
                .addComponent(new PotionEffectComponent(PhysicsLayer.PLAYER, "health"));
        return potion;
    }

    /**
     * made to hold images of different potions' image location.
     */
    private static final String[] potionPictures = {
            "images/Potions/defence_potion.png",
            "images/Potions/agility_potion.png"
    };
}
