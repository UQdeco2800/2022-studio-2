package com.deco2800.game.entities.factories;

import com.deco2800.game.components.tasks.CombatItemsComponents.WeaponAuraComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.CombatItemsConfig.AuraConfig;
import com.deco2800.game.entities.configs.CombatItemsConfig.BaseAuraConfig;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

import java.util.Arrays;
import java.util.List;

public class AuraFactory {
    private static final BaseAuraConfig configs =
            FileLoader.readClass(BaseAuraConfig.class, "configs/Auras.json");
    private static final List<Entity> availableAuras = Arrays.asList(createWeaponSpeedBuff());

    public static List<Entity> getAvailableAuras() {
        return availableAuras;
    }

    public static Entity getAura(int auraIndex) {
        return getAvailableAuras().get(auraIndex);
    }

    public static Entity createBaseEntity() {
        Entity aura = new Entity()
                .addComponent(new PhysicsComponent());
        aura.scaleHeight(1.5f);

        return aura;
    }

    public static Entity createWeaponSpeedBuff() {
        Entity weaponSpeedBuff = createBaseEntity();
        AuraConfig config = configs.speedBuff;
        weaponSpeedBuff
                .addComponent(new WeaponAuraComponent(config.auraDuration, config.damageMultiplier,
                        config.coolDownMultiplier, config.weightMultiplier))
                .addComponent(new TextureRenderComponent("images/CombatWeapons-assets-sprint1/Weapon Speed Buff.png"));
        weaponSpeedBuff.getComponent(TextureRenderComponent.class).scaleEntity();

        return weaponSpeedBuff;
    }
}
