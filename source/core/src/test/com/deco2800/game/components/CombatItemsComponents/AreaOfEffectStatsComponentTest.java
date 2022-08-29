package com.deco2800.game.components.CombatItemsComponents;

import com.deco2800.game.crafting.Materials;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.configs.CombatItemsConfig.AuraConfig;
import com.deco2800.game.entities.configs.CombatItemsConfig.BaseAuraConfig;
import com.deco2800.game.entities.factories.AuraFactory;
import com.deco2800.game.extensions.GameExtension;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import com.deco2800.game.files.FileLoader;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(GameExtension.class)
class AreaOfEffectStatsComponentTest {

    public AreaOfEffectStatsComponent areaEffectOne;
    public AreaOfEffectStatsComponent areaEffectTwo;

    @BeforeEach
    public void setUp() throws Exception {

        HashMap<Materials, Integer> materials = new HashMap<>();
        materials.put(Materials.Steel, 10);

        HashMap<Materials, Integer> materials2 = new HashMap<>();
        materials2.put(Materials.Wood, 5);

        areaEffectOne = new AreaOfEffectStatsComponent(20,10, 5.5, 10, materials);
        areaEffectTwo = new AreaOfEffectStatsComponent(15,8.0, 2.0, 7.0, materials2);

    }

    @Test
    public void testGetDamage() {
        assertEquals(20, areaEffectOne.getDamage(), "Incorrect value for Damage was returned.");
        assertEquals(15, areaEffectTwo.getDamage(), "Incorrect value for Damage was returned.");
    }

    @Test
    public void testGetCoolDown() {
        assertEquals(10, areaEffectOne.getCoolDown(), "Incorrect value for Cool Down was returned.");
        assertEquals(7.0, areaEffectTwo.getCoolDown(), "Incorrect value for Cool Down was returned.");
    }

    @Test
    public void testGetAreaRange() {
        assertEquals(10, areaEffectOne.getAreaRange(), "Incorrect value was for Area Range returned.");
        assertEquals(8.0, areaEffectTwo.getAreaRange(), "Incorrect value was for Area Range returned.");
    }

    @Test
    public void testGetDuration() {
        assertEquals(5.5, areaEffectOne.getDuration(), "Incorrect value was for Duration returned.");
        assertEquals(2.0, areaEffectTwo.getDuration(), "Incorrect value was for Duration returned.");
    }

    @Test
    public void testGetMaterials() {
        HashMap<Materials, Integer> materialsTest = new HashMap<>();
        materialsTest.put(Materials.Steel, 10);

        assertTrue(materialsTest.equals(areaEffectOne.getMaterials()));
    }

    @Test
    public void testSetDuration() {
        areaEffectOne.setDuration(4.0);
        areaEffectTwo.setDuration(2.8);

        assertEquals(4.0, areaEffectOne.getDuration(), "Incorrect value was for Duration returned.");
        assertEquals(2.8, areaEffectTwo.getDuration(), "Incorrect value was for Duration returned.");
    }

    @Test
    public void testSetDamage() {
        areaEffectOne.setDamage(12);
        assertEquals(12, areaEffectOne.getDamage(), "Incorrect value was returned.");
    }

    @Test
    public void testSetCoolDown() {
        areaEffectOne.setCoolDown(9.0);
        assertEquals(9.0, areaEffectOne.getCoolDown(), "Incorrect value was returned.");
    }

    @Test
    public void testSetMaterials() {
        HashMap<Materials, Integer> materialsTest = new HashMap<>();
        materialsTest.put(Materials.Wood, 3);

        areaEffectOne.setMaterials(materialsTest);

        assertTrue(materialsTest.equals(areaEffectOne.getMaterials()));
    }
    @Test
    public void testAuraInEffect() {
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerPhysicsService(new PhysicsService());

        BaseAuraConfig configs = FileLoader.readClass(BaseAuraConfig.class, "configs/Auras.json");
        AuraConfig config = configs.speedBuff;

        Entity auraSpeedBuff = AuraFactory.createBaseAura();
        auraSpeedBuff.addComponent(new WeaponAuraComponent(config.auraDuration, config.damageMultiplier,
                config.coolDownMultiplier, config.weightMultiplier));

        areaEffectOne.auraEffect(auraSpeedBuff);
        assertEquals(5.0, areaEffectOne.getCoolDown(), "Incorrect value was returned.");
    }

 /*   @Test
    public void testAuraAfterEffect() throws InterruptedException {
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerPhysicsService(new PhysicsService());

        BaseAuraConfig configs = FileLoader.readClass(BaseAuraConfig.class, "configs/Auras.json");
        AuraConfig config = configs.speedBuff;
        Entity auraSpeedBuff = AuraFactory.createBaseAura();
        auraSpeedBuff.addComponent(new WeaponAuraComponent(5000, config.damageMultiplier,
                config.coolDownMultiplier, config.weightMultiplier));

        areaEffectTwo.auraEffect(auraSpeedBuff);
        final CountDownLatch latch = new CountDownLatch(1);
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               latch.countDown();
                           }
                       }
                , auraSpeedBuff.getComponent(WeaponAuraComponent.class).getAuraDuration());
        latch.await();
        assertEquals(7.0, areaEffectTwo.getCoolDown(), "Incorrect value was returned.");
    }*/

}
