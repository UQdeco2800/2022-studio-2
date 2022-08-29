
package com.deco2800.game.components.CombatItemsComponents;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.deco2800.game.crafting.Materials;
import com.deco2800.game.extensions.GameExtension;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class will test each function of the class WeaponsStatsComponent
 */

@ExtendWith(GameExtension.class)
class MeleeStatsComponentTest {

    public MeleeStatsComponent weapons1;
    public MeleeStatsComponent weapons2;


    @BeforeEach
    public void setUp() throws Exception {


        HashMap<Materials, Integer> materials1 = new HashMap<>();
        materials1.put(Materials.Steel, 10);

        HashMap<Materials, Integer> materials2 = new HashMap<>();
        materials2.put(Materials.Steel, 20);
        materials2.put(Materials.Wood, 5);

        weapons1 = new MeleeStatsComponent(20, 10, materials1, 2);
        weapons2 = new MeleeStatsComponent(30, 20, materials2, 5);

    }

    @Test
    public void testGetDamage() {
        assertEquals(20, weapons1.getDamage(), "Incorrect value was returned.");
        assertEquals(30, weapons2.getDamage(), "Incorrect value was returned.");
    }

    @Test
    public void testGetCoolDown() {
        assertEquals(10, weapons1.getCoolDown(), "Incorrect value was returned.");
        assertEquals(20, weapons2.getCoolDown(), "Incorrect value was returned.");
    }

    @Test
    public void testGetMaterials() {
        HashMap<Materials, Integer> materialsTest1 = new HashMap<>();
        materialsTest1.put(Materials.Steel, 10);

        HashMap<Materials, Integer> materialsTest2 = new HashMap<>();
        materialsTest2.put(Materials.Steel, 20);
        materialsTest2.put(Materials.Wood, 5);

        assertTrue(materialsTest1.equals(weapons1.getMaterials()));
    }

    @Test
    public void testGetWeight() {
        assertEquals(2.0, weapons1.getWeight(), "Incorrect value was returned.");
    }

    @Test
    public void testSetDamage() {
        weapons1.setDamage(25);
        assertEquals(25, weapons1.getDamage(), "Incorrect value was returned.");
    }

    @Test
    public void testSetCoolDown() {
        weapons1.setCoolDown(5.0);
        assertEquals(5.0, weapons1.getCoolDown(), "Incorrect value was returned.");
    }

    @Test
    public void testSetWeight() {
        weapons1.setWeight(3.0);
        assertEquals(3.0, weapons1.getWeight(), "Incorrect value was returned.");
    }

    @Test
    public void testSetMaterials() {
        HashMap<Materials, Integer> materialsTest = new HashMap<>();
        materialsTest.put(Materials.Wood, 3);

        weapons1.setMaterials(materialsTest);

        HashMap<Materials, Integer> materialsTest2 = new HashMap<>();
        materialsTest2.put(Materials.Wood, 3);

        weapons1.setMaterials(materialsTest);
        assertTrue(materialsTest2.equals(weapons1.getMaterials()));
    }
/*
    @Test
    public void testAuraInEffect() {
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ResourceService rs = new ResourceService();
        String[] textureNames = {"source/core/assets/images/CombatWeapons-assets-sprint1/Weapon Speed Buff.png"};
        rs.loadTextures(textureNames);
        ServiceLocator.registerResourceService(rs);

        Entity auraSpeedBuff = AuraFactory.createWeaponSpeedBuff();
        weapons1.auraEffect(auraSpeedBuff);
        assertEquals(5.0, weapons1.getCoolDown(), "Incorrect value was returned.");
    }

    @Test
    public void testAuraAfterEffect() {
        Entity auraSpeedBuff = AuraFactory.createWeaponSpeedBuff();
        weapons2.auraEffect(auraSpeedBuff);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               assertEquals(20, weapons2.getCoolDown(), "Incorrect value was returned.");
                           }
                       }
                , auraSpeedBuff.getComponent(WeaponAuraComponent.class).getAuraDuration());
    }

 */

}
