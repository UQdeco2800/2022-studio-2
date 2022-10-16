package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.deco2800.game.components.combatitemscomponents.AuraPickupComponent;
import com.deco2800.game.components.combatitemscomponents.PhysicalWeaponStatsComponent;
import com.deco2800.game.components.combatitemscomponents.WeaponAuraComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.combatitemsconfig.AuraConfig;
import com.deco2800.game.entities.configs.combatitemsconfig.BaseAuraConfig;
import com.deco2800.game.entities.configs.combatitemsconfig.WeaponConfig;
import com.deco2800.game.files.FileLoader;

import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;

/**
 * Factory to create Aura entities.
 *
 * <p>Each Aura entity type should have a creation method that returns a corresponding entity.
 */
public class AuraFactory {

    private AuraFactory(){}

    private static final BaseAuraConfig configs =
            FileLoader.readClass(BaseAuraConfig.class, "configs/Auras.json");



    /**
     * Creates a generic Aura to be used as a base Aura entity by more specific aura creation methods.
     * @return base aura entity
     */
    public static Entity createBaseAura() {
        Entity aura = new Entity()
                .addComponent(new PhysicsComponent().setBodyType(BodyDef.BodyType.StaticBody))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new AuraPickupComponent(PhysicsLayer.PLAYER));

        AnimationRenderComponent auraBounce =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService()
                                .getAsset("images/CombatItems/animations/BuffBounce/mapBounce.atlas", TextureAtlas.class));

        aura.setEntityType(EntityTypes.AURA);
        aura.addComponent(auraBounce);

        return aura;
    }

    /**
     * Creates weapon speed buff aura using BaseAuraConfig file that takes in the Aura stats
     * @return entity weapon speed buff aura
     */
    public static Entity createWeaponSpeedBuff() {
        Entity weaponSpeedBuff = createBaseAura();
        AuraConfig config = configs.speedBuff;
        AnimationRenderComponent auraBounce = weaponSpeedBuff.getComponent(AnimationRenderComponent.class);

        auraBounce.addAnimation("mapAttackBuff", 0.1f, Animation.PlayMode.LOOP);
        weaponSpeedBuff
                .addComponent(new WeaponAuraComponent(config.auraDuration, config.damageMultiplier,
                        config.coolDownMultiplier, "Speed"));

        auraBounce.startAnimation("mapAttackBuff");

        return weaponSpeedBuff;
    }

    /**
     * Creating weapon Speed Debuff using BaseAuraConfig file that takes in the Aura stats
     * @return entity weapon Speed Debuff
     */
    public static Entity createWeaponSpeedDeBuff() {
        Entity weaponSpeedDeBuff = createBaseAura();
        AuraConfig config = configs.speedDebuff;
        weaponSpeedDeBuff
                .addComponent(new WeaponAuraComponent(config.auraDuration, config.damageMultiplier,
                        config.coolDownMultiplier, "SpeedDebuff"))
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/AttackSpeedDebuff.png"));
        weaponSpeedDeBuff.getComponent(TextureRenderComponent.class).scaleEntity();
        return weaponSpeedDeBuff;
    }

    /**
     * Creating weapon Damage Buff using BaseAuraConfig file that takes in the Aura stats
     * @return entity weapon Damage Buff
     */
    public static Entity createWeaponDmgBuff() {
        Entity weaponDmgBuff = createBaseAura();
        AuraConfig config = configs.dmgBuff;
        AnimationRenderComponent auraBounce = weaponDmgBuff.getComponent(AnimationRenderComponent.class);

        auraBounce.addAnimation("mapDamageBuff", 0.1f, Animation.PlayMode.LOOP);

        weaponDmgBuff
                .addComponent(new WeaponAuraComponent(config.auraDuration, config.damageMultiplier,
                        config.coolDownMultiplier, "Damage"));

        auraBounce.startAnimation("mapDamageBuff");
        return weaponDmgBuff;
    }

    /**
     * Creating weapon Damage Debuff using BaseAuraConfig file that takes in the Aura stats
     * @return entity weapon Damage Debuff
     */
    public static Entity createWeaponDmgDebuff() {
        Entity weaponDmgDebuff = createBaseAura();
        AuraConfig config = configs.dmgDebuff;
        weaponDmgDebuff
                .addComponent(new WeaponAuraComponent(config.auraDuration, config.damageMultiplier,
                        config.coolDownMultiplier, "DamageDebuff"))
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/AttackDamageDebuff.png"));
        weaponDmgDebuff.getComponent(TextureRenderComponent.class).scaleEntity();
        return weaponDmgDebuff;
    }

    /**
     * Creating weapon Fire buff using BaseAuraConfig file that takes in the Aura stats
     * @return entity weapon Fire buff
     */
    public static Entity createFireBuff() {
        Entity weaponFireBuff = createBaseAura();
        AuraConfig config = configs.fireBuff;
        AnimationRenderComponent auraBounce = weaponFireBuff.getComponent(AnimationRenderComponent.class);

        auraBounce.addAnimation("mapFireBuff", 0.1f, Animation.PlayMode.LOOP);

        weaponFireBuff
                .addComponent(new WeaponAuraComponent(config.auraDuration, config.damageMultiplier,
                        config.coolDownMultiplier, "Fire"));
        auraBounce.startAnimation("mapFireBuff");
        return weaponFireBuff;
    }

    /**
     * Creating weapon Poison buff using BaseAuraConfig file that takes in the Aura stats
     * @return entity weapon Poison buff
     */
    public static Entity createPoisonBuff() {
        Entity weaponPoisonBuff = createBaseAura();
        AuraConfig config = configs.poisonBuff;
        AnimationRenderComponent auraBounce = weaponPoisonBuff.getComponent(AnimationRenderComponent.class);

        auraBounce.addAnimation("mapPoisonBuff", 0.1f, Animation.PlayMode.LOOP);


        weaponPoisonBuff
                .addComponent(new WeaponAuraComponent(config.auraDuration, config.damageMultiplier,
                        config.coolDownMultiplier, "Poison"));
        auraBounce.startAnimation("mapPoisonBuff");
        return weaponPoisonBuff;
    }


    /**
     * Creates a dagger for testing
     * @return test weapon
     */
    public static Entity createTestAura() {

        Entity aura = new Entity()
                .addComponent(new PhysicsComponent().setBodyType(BodyDef.BodyType.StaticBody))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new AuraPickupComponent(PhysicsLayer.PLAYER));

        aura.setEntityType(EntityTypes.AURA);
        AuraConfig config = configs.fireBuff;
        WeaponAuraComponent weaponStats = new WeaponAuraComponent(config.auraDuration, config.damageMultiplier,
                config.coolDownMultiplier, "Fire");
        aura.addComponent(weaponStats);
        return aura;
    }

}