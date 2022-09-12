package com.deco2800.game.entities.factories;


import com.deco2800.game.components.DefensiveItemsComponents.ArmourStatsComponent;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.deco2800.game.components.CombatItemsComponents.WeaponPickupComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.components.DefensiveItemsComponents.ArmourConfig;
import com.deco2800.game.components.DefensiveItemsComponents.ArmourTypeConfig;
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
     * @return base armour entity
     */
    public static Entity createBaseArmour() {
        ArmourConfig config = getConfig(ArmourType.baseArmour);
        ArmourStatsComponent armourStats = new ArmourStatsComponent(config.phyResistance,
                config.durability, config.vitality,
                config.dmgReturn, config.weight, config.materials);
        Entity armour = new Entity()
                .addComponent(new PhysicsComponent().setBodyType(BodyDef.BodyType.StaticBody))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(armourStats)
                .addComponent(new WeaponPickupComponent(PhysicsLayer.PLAYER));
        armour.setEntityType(EntityTypes.ARMOUR);
        return armour;
    }

    public enum ArmourType {
        baseArmour,
        slowDiamond,
        fastLeather,
        damageReturner
    }

    /**
     * Returns the config based on the input ArmourType
     * @param type armour type
     * @return armour config, baseArmour config if no type is matching
     */
    private static ArmourConfig getConfig (ArmourType type) {
        ArmourConfig config = configs.baseArmour;
        switch (type){
            case damageReturner:
                config = configs.damageReturner;
            case fastLeather:
                config = configs.fastLeather;
            case slowDiamond:
                config = configs.slowDiamond;
        }
        return config;
    }

    /**
     * Returns the texture file path based on the input ArmourType
     * @param type armour type
     * @return armour
     */
    private static String getTexture (ArmourType type) {
        String texturePath = "images/Armour-assets-sprint2/baseArmour.png";
        switch (type){
            case damageReturner:
                texturePath = "images/Armour-assets-sprint2/damageReturner.png";
            case fastLeather:
                texturePath = "images/Armour-assets-sprint2/fastLeather.png";
            case slowDiamond:
                texturePath = "images/Armour-assets-sprint2/slowDiamond.png";
        }
        return texturePath;
    }

    public static Entity createArmour(ArmourType type) {
        Entity armour = createBaseArmour();
        ArmourConfig config = getConfig(type);
        String texturePath = getTexture(type);
        armour.addComponent(new TextureRenderComponent(texturePath));
        armour.getComponent(ArmourStatsComponent.class).setArmourStats(
                config.phyResistance,
                config.durability,
                config.vitality,
                config.dmgReturn,
                config.weight,
                config.materials);
        armour.getComponent(TextureRenderComponent.class).scaleEntity();
        armour.scaleHeight(5f);
        return armour;
    }
}
