package com.deco2800.game.entities.factories;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.deco2800.game.components.CombatItemsComponents.MeleeStatsComponent;
import com.deco2800.game.components.CombatItemsComponents.WeaponPickupComponent;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.CombatItemsConfig.MeleeConfig;
import com.deco2800.game.entities.configs.CombatItemsConfig.WeaponConfig;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

/**
 * Factory to create Weapon entities.
 *
 * <p>Each Weapon entity type should have a creation method that returns a corresponding entity.
 */
public class WeaponFactory {

    private static final WeaponConfig configs =
            FileLoader.readClass(WeaponConfig.class, "configs/Weapons.json");

    /**
     * Creates a generic Weapon to be used as a base Weapon entity by more specific aura creation methods.
     * @return base weapon entity
     */
    public static Entity createBaseWeapon() {
        Entity weapon = new Entity()
                .addComponent(new PhysicsComponent().setBodyType(BodyDef.BodyType.StaticBody))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new WeaponPickupComponent(PhysicsLayer.PLAYER));
        weapon.setEntityType(EntityTypes.WEAPON);
        return weapon;
    }

    /**
     * Creates Level 2 dagger - Athena's Dagger
     * @return level 2 dagger - Athena's Dagger
     */
    public static Entity createDagger() {
        Entity dagger = createBaseWeapon();
        MeleeConfig config = configs.athenaDag;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight);

       dagger
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/Level 2 Dagger 1.png"));
        dagger.getComponent(TextureRenderComponent.class).scaleEntity();
        dagger.scaleHeight(1f);
        return dagger;
    }

    /**
     * Creates Level 2 dagger 2 - Hera's Dagger
     * @return Level 2 dagger 2 - Hera's Dagger
     */
    public static Entity createDaggerTwo() {
        Entity daggerTwo = createBaseWeapon();
        MeleeConfig config = configs.herraDag;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight);

        daggerTwo
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/Level 2 Dagger 2png.png"));
        daggerTwo.getComponent(TextureRenderComponent.class).scaleEntity();
        daggerTwo.scaleHeight(5f);
        return daggerTwo;
    }

    /**
     * Creates basic weapon for enemy character
     * @return basic enemy dumbbell
     */
    public static Entity createDumbbell() {
        Entity dumbbell = createBaseWeapon();
        MeleeConfig config = configs.dumbbell;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight);

        dumbbell
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/Enemy_dumbbell.png"));
        dumbbell.getComponent(TextureRenderComponent.class).scaleEntity();
        dumbbell.scaleHeight(1f);
        return dumbbell;
    }

    /**
     * Creates the level 2 swords for player
     * @return level 2 sword for player
     */
    public static Entity createSwordLvl2() {
        Entity SwordLvl2 = createBaseWeapon();
        MeleeConfig config = configs.SwordLvl2;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight);

        SwordLvl2
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/Sword_Lvl2.png"));
        SwordLvl2.getComponent(TextureRenderComponent.class).scaleEntity();
        SwordLvl2.scaleHeight(2f);
        return SwordLvl2;
    }

    /**
     * Creates the level 2 trident for the player
     * @return level 2 trident for players
     */
    public static Entity createTridentLvl2() {
        Entity TridentLvl2 = createBaseWeapon();
        MeleeConfig config = configs.tridentLvl2;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight);

        TridentLvl2
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/trident_Lvl2.png"));
        TridentLvl2.getComponent(TextureRenderComponent.class).scaleEntity();
        TridentLvl2.scaleHeight(2f);
        return TridentLvl2;
    }

    /**
     * Creates Herra and Athena's Dagger
     * @return Herra and Athena's Dagger
     */
    public static Entity createHerraAthenaDag() {
        Entity heraAthenaDag = createBaseWeapon();
        MeleeConfig config = configs.herraAthenaDag;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight);

        heraAthenaDag
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-2/H&ADagger.png"));
        heraAthenaDag.getComponent(TextureRenderComponent.class).scaleEntity();
        heraAthenaDag.scaleHeight(2f);
        return heraAthenaDag;
    }

    /**
     * Creates the plunger weapon
     * @return plunger weapon
     */
    public static Entity createPlunger() {
        Entity plunger = createBaseWeapon();
        MeleeConfig config = configs.plunger;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight);

        plunger
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-2/Plunger.png"));
        plunger.getComponent(TextureRenderComponent.class).scaleEntity();
        plunger.scaleHeight(2f);
        return plunger;
    }

}
