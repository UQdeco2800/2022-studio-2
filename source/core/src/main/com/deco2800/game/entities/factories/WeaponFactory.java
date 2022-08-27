package com.deco2800.game.entities.factories;

import com.deco2800.game.components.tasks.CombatItemsComponents.MeleeStatsComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.CombatItemsConfig.MeleeConfig;
import com.deco2800.game.entities.configs.CombatItemsConfig.WeaponConfig;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

import java.util.Arrays;
import java.util.List;

public class WeaponFactory {
    private static final WeaponConfig configs =
            FileLoader.readClass(WeaponConfig.class, "configs/Weapons.json");

    private static final List<Entity> availableWeapons = Arrays.asList(createDagger());

    public static List<Entity> getAvailableWeapons() {
        return availableWeapons;
    }

    public static Entity getWeapon(int weaponIndex) {return getAvailableWeapons().get(weaponIndex);
    }

    public static Entity createBaseWeapon() {
        Entity weapon = new Entity()
                .addComponent(new PhysicsComponent());
        return weapon;
    }

    public static Entity createDagger() {
        Entity dagger = createBaseWeapon();
        MeleeConfig config = configs.dagger;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight);

        dagger
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatWeapons-assets-sprint1/Level 2 Dagger 1.png"));
    //apply rohan's crafting component
        return dagger;
    }

}
