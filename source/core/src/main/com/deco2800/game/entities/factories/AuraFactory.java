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

/**
 * Factory to create Aura entities.
 *
 * <p>Each Aura entity type should have a creation method that returns a corresponding entity.
 */
public class AuraFactory {

    private static final BaseAuraConfig configs =
            FileLoader.readClass(BaseAuraConfig.class, "configs/Auras.json");
    private static final List<Entity> availableAuras = Arrays.asList(createWeaponSpeedBuff());

    /**
     * Return the list containing all aura entities that are available to be implemented in game
     * @return the list containing aura entities
     */
    public static List<Entity> getAvailableAuras() {
        return availableAuras;
    }

    /**
     * Return the aura entity from the list of aura entities at the given index
     * @param auraIndex index of the aura in the list of aura entities
     * @return aura entity from the list of aura entities at the given index
     */
    public static Entity getAura(int auraIndex) {
        return getAvailableAuras().get(auraIndex);
    }

    /**
     * Creates a generic Aura to be used as a base Aura entity by more specific aura creation methods.
     * @return base aura entity
     */
    public static Entity createBaseAura() {
        Entity aura = new Entity()
                .addComponent(new PhysicsComponent());
        aura.scaleHeight(1.5f);

        return aura;
    }

    /**
     * Creates weapon speed buff aura
     * @return weapon speed buff aura
     */
    public static Entity createWeaponSpeedBuff() {
        Entity weaponSpeedBuff = createBaseAura();
        AuraConfig config = configs.speedBuff;
        weaponSpeedBuff
                .addComponent(new WeaponAuraComponent(config.auraDuration, config.damageMultiplier,
                        config.coolDownMultiplier, config.weightMultiplier))
                .addComponent(new TextureRenderComponent("images/CombatWeapons-assets-sprint1/Weapon Speed Buff.png"));
        weaponSpeedBuff.getComponent(TextureRenderComponent.class).scaleEntity();
        return weaponSpeedBuff;
    }
}
