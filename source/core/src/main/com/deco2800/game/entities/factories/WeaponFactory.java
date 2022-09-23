package com.deco2800.game.entities.factories;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.deco2800.game.components.CombatItemsComponents.MeleeStatsComponent;
import com.deco2800.game.components.ItemPickupComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.CombatItemsConfig.MeleeConfig;
import com.deco2800.game.entities.configs.CombatItemsConfig.WeaponConfig;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
//import com.deco2800.game.entities.factories.WeaponFactory.configs;

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
                .addComponent(new ItemPickupComponent(PhysicsLayer.PLAYER));
        weapon.setEntityType(EntityTypes.WEAPON);
        return weapon;
    }

    /**
     * Creates Level 2 dagger - Athena's Dagger
     * @return level 2 dagger - Athena's Dagger
     */
    public static Entity createDagger() {
        Entity dagger = createBaseWeapon();
        dagger.setEntityType(EntityTypes.CRAFTABLE);
        MeleeConfig config = configs.athenaDag;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "dagger");

       dagger
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/Level 2 Dagger 1.png"));
        dagger.getComponent(TextureRenderComponent.class).scaleEntity();
        dagger.scaleHeight(1f);
        dagger.setEntityType(EntityTypes.MELEE);
        return dagger;
    }

    /**
     * Creates Level 2 dagger 2 - Hera's Dagger
     * @return Level 2 dagger 2 - Hera's Dagger
     */
    public static Entity createHera() {
        Entity hera = createBaseWeapon();
        hera.setEntityType(EntityTypes.CRAFTABLE);
        MeleeConfig config = configs.heraDag;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "hera");
        hera
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/Level 2 Dagger 2png.png"));
        hera.getComponent(TextureRenderComponent.class).scaleEntity();
        hera.scaleHeight(5f);
        hera.setEntityType(EntityTypes.MELEE);
        return hera;
    }

    /**
     * Creates basic weapon for enemy character
     * @return basic enemy dumbbell
     */
    public static Entity createDumbbell() {
        Entity dumbbell = createBaseWeapon();
        MeleeConfig config = configs.dumbbell;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "dumbbell");

        dumbbell
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/Enemy_dumbbell.png"));
        dumbbell.getComponent(TextureRenderComponent.class).scaleEntity();
        dumbbell.scaleHeight(1f);
        dumbbell.setEntityType(EntityTypes.MELEE);
        return dumbbell;
    }

    /**
     * Creates the level 2 swords for player
     * @return level 2 sword for player
     */
    public static Entity createSwordLvl2() {
        Entity SwordLvl2 = createBaseWeapon();
        MeleeConfig config = configs.SwordLvl2;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "SwordLvl2");

        SwordLvl2
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/Sword_Lvl2.png"));
        SwordLvl2.getComponent(TextureRenderComponent.class).scaleEntity();
        SwordLvl2.scaleHeight(2f);
        SwordLvl2.setEntityType(EntityTypes.MELEE);
        return SwordLvl2;
    }

    /**
     * Creates the level 2 trident for the player
     * @return level 2 trident for players
     */
    public static Entity createTridentLvl2() {
        Entity TridentLvl2 = createBaseWeapon();
        MeleeConfig config = configs.tridentLvl2;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "TridentLvl2");

        TridentLvl2
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/trident_Lvl2.png"));
        TridentLvl2.getComponent(TextureRenderComponent.class).scaleEntity();
        TridentLvl2.scaleHeight(2f);
        TridentLvl2.setEntityType(EntityTypes.MELEE);
        return TridentLvl2;
    }

    /**
     * Creates hera and Athena's Dagger
     * @return hera and Athena's Dagger
     */
    public static Entity createHeraAthenaDag() {
        Entity heraAthenaDag = createBaseWeapon();
        MeleeConfig config = configs.heraAthenaDag;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "heraAthenaDag");

        heraAthenaDag
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-2/H&ADagger.png"));
        heraAthenaDag.getComponent(TextureRenderComponent.class).scaleEntity();
        heraAthenaDag.scaleHeight(2f);
        heraAthenaDag.setEntityType(EntityTypes.MELEE);
        return heraAthenaDag;
    }

    /**
     * Creates the plunger weapon
     * @return plunger weapon
     */
    public static Entity createPlunger() {
        Entity plunger = createBaseWeapon();
        MeleeConfig config = configs.plunger;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "plunger");

        plunger
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-2/Plunger.png"));
        plunger.getComponent(TextureRenderComponent.class).scaleEntity();
        plunger.scaleHeight(2f);
        plunger.setEntityType(EntityTypes.MELEE);
        return plunger;
    }
    /**
     * Creates the PVC pipe weapon
     * @return plunger weapon
     */
    public static Entity createPipe() {
        Entity pipe = createBaseWeapon();
        MeleeConfig config = configs.pipe;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "pipe");
        pipe
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-2/pipe.png"));
        pipe.getComponent(TextureRenderComponent.class).scaleEntity();
        pipe.scaleHeight(2f);
        pipe.setEntityType(EntityTypes.MELEE);
        return pipe;
    }

    /**
     * Creates a dagger for testing
     * @return test weapon
     */
    public static Entity createTestDagger() {
        Entity dagger = createBaseWeapon();
        dagger.setEntityType(EntityTypes.WEAPON);
        MeleeConfig config = configs.athenaDag;
        MeleeStatsComponent weaponStats = new MeleeStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "TestDagger");
        dagger.addComponent(weaponStats);
        dagger.setEntityType(EntityTypes.MELEE);
        return dagger;
    }

}