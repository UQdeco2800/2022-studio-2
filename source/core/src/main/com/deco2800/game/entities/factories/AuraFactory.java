package com.deco2800.game.entities.factories;

import com.deco2800.game.components.CombatItemsComponents.WeaponAuraComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.CombatItemsConfig.AuraConfig;
import com.deco2800.game.entities.configs.CombatItemsConfig.BaseAuraConfig;
import com.deco2800.game.files.FileLoader;

import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

/**
 * Factory to create Aura entities.
 *
 * <p>Each Aura entity type should have a creation method that returns a corresponding entity.
 */
public class AuraFactory {

    private static final BaseAuraConfig configs =
            FileLoader.readClass(BaseAuraConfig.class, "configs/Auras.json");

    /**
     * Creates a generic Aura to be used as a base Aura entity by more specific aura creation methods.
     * @return base aura entity
     */
    public static Entity createBaseAura() {
        Entity aura = new Entity()
                .addComponent(new PhysicsComponent());
        aura.scaleHeight(5f);

        return aura;
    }

    /**
     * Creates weapon speed buff aura using BaseAuraConfig file that takes in the Aura stats
     * @return entity weapon speed buff aura
     */
    public static Entity createWeaponSpeedBuff() {
        Entity weaponSpeedBuff = createBaseAura();
        AuraConfig config = configs.speedBuff;
        weaponSpeedBuff
                .addComponent(new WeaponAuraComponent(config.auraDuration, config.damageMultiplier,
                        config.coolDownMultiplier, config.weightMultiplier))
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/Weapon Speed Buff.png"));
        weaponSpeedBuff.getComponent(TextureRenderComponent.class).scaleEntity();
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
                        config.coolDownMultiplier, config.weightMultiplier))
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/Weapon Speed Buff.png"));
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
        weaponDmgBuff
                .addComponent(new WeaponAuraComponent(config.auraDuration, config.damageMultiplier,
                        config.coolDownMultiplier, config.weightMultiplier))
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/Damage Increase Buff.png"));
        weaponDmgBuff.getComponent(TextureRenderComponent.class).scaleEntity();
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
                        config.coolDownMultiplier, config.weightMultiplier))
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
        weaponFireBuff
                .addComponent(new WeaponAuraComponent(config.auraDuration, config.damageMultiplier,
                        config.coolDownMultiplier, config.weightMultiplier))
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/PeriPeriBuff_FIRE.png"));
        weaponFireBuff.getComponent(TextureRenderComponent.class).scaleEntity();
        return weaponFireBuff;
    }

    /**
     * Creating weapon Poison buff using BaseAuraConfig file that takes in the Aura stats
     * @return entity weapon Poison buff
     */
    public static Entity createPoisonBuff() {
        Entity weaponPoisonBuff = createBaseAura();
        AuraConfig config = configs.poisonBuff;
        weaponPoisonBuff
                .addComponent(new WeaponAuraComponent(config.auraDuration, config.damageMultiplier,
                        config.coolDownMultiplier, config.weightMultiplier))
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/poisonBuff.png"));
        weaponPoisonBuff.getComponent(TextureRenderComponent.class).scaleEntity();
        return weaponPoisonBuff;
    }

}