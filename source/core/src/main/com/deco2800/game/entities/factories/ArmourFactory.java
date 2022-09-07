package com.deco2800.game.entities.factories;


import DefensiveItemsComponents.ArmourStatsComponent;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.deco2800.game.components.CombatItemsComponents.WeaponPickupComponent;
import com.deco2800.game.entities.Entity;
import DefensiveItemsComponents.ArmourConfig;
import DefensiveItemsComponents.ArmourTypeConfig;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

public class ArmourFactory {

/**
 * Factory to create Armour entities.
 *
 * <p>Each Weapon entity type should have a creation method that returns a corresponding entity.
 */

    private static final ArmourTypeConfig configs =
            FileLoader.readClass(ArmourTypeConfig.class, "configs/Armours.json");

    /**
     * Creates a generic Weapon to be used as a base Weapon entity by more specific aura creation methods.
     * @return base weapon entity
     */
    public static Entity createBaseArmour() {
        Entity weapon = new Entity()
                .addComponent(new PhysicsComponent().setBodyType(BodyDef.BodyType.StaticBody))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new WeaponPickupComponent(PhysicsLayer.PLAYER));
        return weapon;
    }

    public enum ArmourType {
        SLOW_DIAMOND,
        FAST_LEATHER,
        DAMAGE_RETURNER
    }


    public static Entity createArmour(ArmourType type) {
        Entity armour = createBaseArmour();
        String texturePath = "";
        ArmourConfig config = null;
        switch (type){
            case DAMAGE_RETURNER:
                config = configs.damage_returner;
                texturePath = "";
                break;
            case FAST_LEATHER:
                config = configs.fast_leather;
                texturePath = "";
                break;
            case SLOW_DIAMOND:
                config = configs.slow_diamond;
                texturePath = "";
                break;
        }

        ArmourStatsComponent armourStats = new ArmourStatsComponent(config.phyResistance,
                config.durability, config.vitality,
                config.dmgReturn, config.weight, config.materials);

        armour
                .addComponent(armourStats)
                .addComponent(new TextureRenderComponent(texturePath));
        armour.getComponent(TextureRenderComponent.class).scaleEntity();
        armour.scaleHeight(5f);
        return armour;
    }
}
