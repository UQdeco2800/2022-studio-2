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
 * <p>Each Armour entity type should have a creation method that returns a corresponding entity.
 */

    private static final ArmourTypeConfig configs =
            FileLoader.readClass(ArmourTypeConfig.class, "configs/Armours.json");

    /**
     * Creates a generic Armour to be used as a base Armour entity.
     * @return base weapon entity
     */
    public static Entity createBaseArmour() {
        Entity armour = new Entity()
                .addComponent(new PhysicsComponent().setBodyType(BodyDef.BodyType.StaticBody))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new WeaponPickupComponent(PhysicsLayer.PLAYER));
        armour.setEntityType(EntityTypes.ARMOUR);
        return armour;
    }

    public enum ArmourType {
        SLOW_DIAMOND,
        FAST_LEATHER,
        DAMAGE_RETURNER
    }

    /**
     * Returns the config based on the input ArmourType
     * @param type armour type
     * @return armour config, baseArmour config if no type is matching
     */
    private static ArmourConfig getConfig (ArmourType type) {
        switch (type){
            case DAMAGE_RETURNER:
                return configs.damageReturner;
            case FAST_LEATHER:
                return configs.fastLeather;
            case SLOW_DIAMOND:
                return configs.slowDiamond;
        }
        return configs.baseArmour;
    }

    public static Entity createArmour(ArmourType type, String texturePath) {
        Entity armour = createBaseArmour();
        ArmourConfig config = getConfig(type);
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
