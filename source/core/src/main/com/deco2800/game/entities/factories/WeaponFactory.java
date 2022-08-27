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

/**
 * Factory to create Weapon entities.
 *
 * <p>Each Weapon entity type should have a creation method that returns a corresponding entity.
 */
public class WeaponFactory {
    private static final WeaponConfig configs =
            FileLoader.readClass(WeaponConfig.class, "configs/Weapons.json");

/*
    private static Entity dagger = createDagger();
    private static Entity daggerTwo = createDaggerTwo();

    private static final List<Entity> availableWeapons = Arrays.asList(dagger, daggerTwo);




    *//**
     * Return the list containing all weapon entities that are available to be implemented in game
     * @return the list containing weapons entities
     *//*
    public static List<Entity> getAvailableWeapons() {
        return availableWeapons;
    }

    *//**
     * Return the weapon entity from the list of weapon entities at the given index
     * @param weaponIndex index of the weapon entity in the list of weapon entities
     * @return weapon entity from the list of weapon entities at the given index
     *//*
    public static Entity getWeapon(int weaponIndex) {return getAvailableWeapons().get(weaponIndex);
    }*/




    /**
     * Creates a generic Weapon to be used as a base Weapon entity by more specific aura creation methods.
     * @return base weapon entity
     */
    public static Entity createBaseWeapon() {
        Entity weapon = new Entity()
                .addComponent(new PhysicsComponent());
        return weapon;
    }

    /**
     * Creates basic dagger
     * @return basic dagger
     */
    public static Entity createDagger() {
        Entity dagger = createBaseWeapon();
        MeleeConfig config = configs.dagger;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight);

       dagger
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatWeapons-assets-sprint1/Level 2 Dagger 1.png"));
        dagger.getComponent(TextureRenderComponent.class).scaleEntity();
        dagger.scaleHeight(5f);
        return dagger;
    }

    /**
     * Creates basic second dagger
     * @return basic second dagger
     */
    public static Entity createDaggerTwo() {
        Entity daggerTwo = createBaseWeapon();
        MeleeConfig config = configs.daggerTwo;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight);

        daggerTwo
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatWeapons-assets-sprint1/Level 2 Dagger 2png.png"));
        daggerTwo.getComponent(TextureRenderComponent.class).scaleEntity();
        daggerTwo.scaleHeight(5f);
        return daggerTwo;
    }

}
