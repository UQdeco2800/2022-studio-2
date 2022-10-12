package com.deco2800.game.entities.factories;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.deco2800.game.components.CombatItemsComponents.PhysicalWeaponStatsComponent;
import com.deco2800.game.components.ItemPickupComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.CombatItemsConfig.WeaponConfig;
import com.deco2800.game.entities.configs.CombatItemsConfig.WeaponConfigSetup;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

/**
 * Factory to create Weapon entities.
 *
 * <p>Each Weapon entity type should have a creation method that returns a corresponding entity.
 */
public class WeaponFactory {

    private WeaponFactory(){}

    private static final WeaponConfigSetup configs =
            FileLoader.readClass(WeaponConfigSetup.class, "configs/Weapons.json");

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
        WeaponConfig config = configs.athenaDag;
        PhysicalWeaponStatsComponent weaponStats = new PhysicalWeaponStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "athena");

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
        WeaponConfig config = configs.heraDag;
        PhysicalWeaponStatsComponent weaponStats = new PhysicalWeaponStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "hera");
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
        WeaponConfig config = configs.dumbbell;
        PhysicalWeaponStatsComponent weaponStats = new PhysicalWeaponStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "dumbbell");

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
        Entity SwordLevelTwo = createBaseWeapon();
        WeaponConfig config = configs.SwordLvl2;
        PhysicalWeaponStatsComponent weaponStats = new PhysicalWeaponStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "sword");

        SwordLevelTwo
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-1/Sword_Lvl2.png"));
        SwordLevelTwo.getComponent(TextureRenderComponent.class).scaleEntity();
        SwordLevelTwo.scaleHeight(2f);
        SwordLevelTwo.setEntityType(EntityTypes.MELEE);
        return SwordLevelTwo;
    }

    /**
     * Creates the level 2 trident for the player
     * @return level 2 trident for players
     */
    public static Entity createTridentLvl2() {
        Entity TridentLvl2 = createBaseWeapon();
        WeaponConfig config = configs.tridentLvl2;
        PhysicalWeaponStatsComponent weaponStats = new PhysicalWeaponStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "trident");

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
        WeaponConfig config = configs.heraAthenaDag;
        PhysicalWeaponStatsComponent weaponStats = new PhysicalWeaponStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "heraAthena");

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
        WeaponConfig config = configs.plunger;
        PhysicalWeaponStatsComponent weaponStats = new PhysicalWeaponStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "plunger");

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
     * @return pipe weapon
     */
    public static Entity createPipe() {
        Entity pipe = createBaseWeapon();
        WeaponConfig config = configs.pipe;
        PhysicalWeaponStatsComponent weaponStats = new PhysicalWeaponStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "pipe");
        pipe
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-2/pipe.png"));
        pipe.getComponent(TextureRenderComponent.class).scaleEntity();
        pipe.scaleHeight(2f);
        pipe.setEntityType(EntityTypes.MELEE);
        return pipe;
    }

    /**
     * Create plunger bow
     * @return plunger bow
     */
    public static Entity createPlungerBow() {
        Entity plungerBow = createBaseWeapon();
        WeaponConfig config = configs.plungerBow;
        PhysicalWeaponStatsComponent weaponStats = new PhysicalWeaponStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "plungerBow");
        plungerBow
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/Bow.png"));
        plungerBow.getComponent(TextureRenderComponent.class).scaleEntity();
        plungerBow.scaleHeight(2f);
        plungerBow.setEntityType(EntityTypes.RANGED);
        return plungerBow;
    }

    /**
     * Create a golden plunger bow
     * @return golden plunger bow
     */
    public static Entity createGoldenPlungerBow() {
        Entity goldenPlungerBow = createBaseWeapon();
        WeaponConfig config = configs.goldenPlungerBow;
        PhysicalWeaponStatsComponent weaponStats = new PhysicalWeaponStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "goldenBow");
        goldenPlungerBow
                .addComponent(weaponStats)
                .addComponent(new TextureRenderComponent("images/CombatItems/Sprint-3/craftingTeamAssetsNoWhiteSpace/goldenBowPlunger.png"));
        goldenPlungerBow.getComponent(TextureRenderComponent.class).scaleEntity();
        goldenPlungerBow.scaleHeight(2f);
        goldenPlungerBow.setEntityType(EntityTypes.RANGED);
        return goldenPlungerBow;
    }

    /**
     * Creates a dagger for testing
     * @return test weapon
     */
    public static Entity createTestDagger() {
        Entity dagger = createBaseWeapon();
        dagger.setEntityType(EntityTypes.WEAPON);
        WeaponConfig config = configs.athenaDag;
        PhysicalWeaponStatsComponent weaponStats = new PhysicalWeaponStatsComponent(config.damage, config.coolDown, config.materials, config.weight, "hera");
        dagger.addComponent(weaponStats);
        dagger.setEntityType(EntityTypes.MELEE);
        return dagger;
    }

    /**
     * Creates the specified type of weapon for testing
     * @return test weapon
     */
    public static Entity createTestWeapon(String weaponName) {
        Entity weapon = createBaseWeapon();
        weapon.setEntityType(EntityTypes.WEAPON);
        WeaponConfig config;
        config = switch (weaponName) {
            case "Dumbbell" -> configs.dumbbell;
            case "Dagger2" -> configs.heraDag;
            case "SwordLvl2" -> configs.SwordLvl2;
            case "tridentLvl2" -> configs.tridentLvl2;
            case "herraAthenaDag" -> configs.heraAthenaDag;
            case "Plunger"-> configs.plunger;
            case "Pipe" ->  configs.pipe;
            default -> configs.athenaDag;
        };
        PhysicalWeaponStatsComponent weaponStats = new PhysicalWeaponStatsComponent(config.damage, config.coolDown, config.materials, config.weight ,"hera");
        weapon.addComponent(weaponStats);
        return weapon;
    }
}