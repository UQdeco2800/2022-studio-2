
package com.deco2800.game.components.combatitemscomponents;

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
class PhysicalStatsComponentTest {

    public PhysicalWeaponStatsComponent weapons1;
    public PhysicalWeaponStatsComponent weapons2;
    public PhysicalWeaponStatsComponent weapons3;
    public PhysicalWeaponStatsComponent weapons4;
    public PhysicalWeaponStatsComponent weapons5;


    @BeforeEach
    public void setUp() throws Exception {


        HashMap<Materials, Integer> materials1 = new HashMap<>();
        materials1.put(Materials.Steel, 10);

        HashMap<Materials, Integer> materials2 = new HashMap<>();
        materials2.put(Materials.Steel, 20);
        materials2.put(Materials.Wood, 5);

        weapons1 = new PhysicalWeaponStatsComponent(20, 10, materials1, 2, "pipe");
        weapons2 = new PhysicalWeaponStatsComponent(20, 20, materials2, 2, "pipe");
        weapons3 = new PhysicalWeaponStatsComponent(20, 10, materials1, 2, "pipe");

        weapons4 = new PhysicalWeaponStatsComponent(30, 10, materials2, 2, "pipe");
        weapons5 = new PhysicalWeaponStatsComponent(20, 10, materials2, 5, "pipe");

    }

    @Test
    public void testGetDamage() {
        assertEquals(20, weapons1.getDamage(), "Incorrect value was returned.");
        assertEquals(30, weapons4.getDamage(), "Incorrect value was returned.");
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

    @Test
    public void testGetDescription() {
        assertEquals("pipe", weapons1.getDescription(), "Incorrect value was returned.");
    }

    @Test
    public void testEqualsOtherFalse() {
        // checks for each potential difference
        assertEquals(false, weapons1.equalsOther(weapons2), "Incorrect value was returned.");
        assertEquals(false, weapons1.equalsOther(weapons4), "Incorrect value was returned.");
        assertEquals(false, weapons1.equalsOther(weapons5), "Incorrect value was returned.");
    }

    @Test
    public void testEqualsOtherTrue() {
        assertEquals(true, weapons1.equalsOther(weapons3), "Incorrect value was returned.");
    }



   /* @Test
    public void testAuraInEffect() {
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerPhysicsService(new PhysicsService());

        BaseAuraConfig configs = FileLoader.readClass(BaseAuraConfig.class, "configs/Auras.json");
        AuraConfig config = configs.speedBuff;

        Entity auraSpeedBuff = AuraFactory.createBaseAura();
        auraSpeedBuff.addComponent(new WeaponAuraComponent(config.auraDuration, config.damageMultiplier,
                config.coolDownMultiplier, config.weightMultiplier));

        weapons1.auraEffect(auraSpeedBuff);
        assertEquals(5.0, weapons1.getCoolDown(), "Incorrect value was returned.");
    }*/
}
