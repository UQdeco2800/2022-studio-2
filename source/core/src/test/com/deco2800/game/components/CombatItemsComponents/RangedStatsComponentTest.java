package com.deco2800.game.components.CombatItemsComponents;
import com.deco2800.game.crafting.Materials;
import com.deco2800.game.extensions.GameExtension;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(GameExtension.class)
class RangedStatsComponentTest {
    public RangedStatsComponent rangedweapon1;
    public RangedStatsComponent rangedweapon2;

    @BeforeEach
    public void setUp() throws Exception {
        HashMap<Materials, Integer> materials1 = new HashMap<>();
        materials1.put(Materials.Steel, 10);

        HashMap<Materials, Integer> materials2 = new HashMap<>();
        materials2.put(Materials.Steel, 20);
        materials2.put(Materials.Wood, 5);

        rangedweapon1 = new RangedStatsComponent(20, 10, materials1, 2);
        rangedweapon2 = new RangedStatsComponent(30, 20, materials2, 5);
    }

    @Test
    public void testGetDamage() {
        assertEquals(20, rangedweapon1.getDamage(), "Incorrect value was returned.");
        assertEquals(30, rangedweapon2.getDamage(), "Incorrect value was returned.");
    }

    @Test
    public void testGetCoolDown() {
        assertEquals(10, rangedweapon1.getCoolDown(), "Incorrect value was returned.");
        assertEquals(20, rangedweapon2.getCoolDown(), "Incorrect value was returned.");
    }

    @Test
    public void testGetMaterials() {
        HashMap<Materials, Integer> materialsTest1 = new HashMap<>();
        materialsTest1.put(Materials.Steel, 10);

        HashMap<Materials, Integer> materialsTest2 = new HashMap<>();
        materialsTest2.put(Materials.Steel, 20);
        materialsTest2.put(Materials.Wood, 5);

        assertTrue(materialsTest1.equals(rangedweapon1.getMaterials()));
    }

    @Test
    public void testGetWeight() {
        assertEquals(2.0, rangedweapon1.getWeight(), "Incorrect value was returned.");
    }

    @Test
    public void testSetDamage() {
        rangedweapon1.setDamage(25);
        assertEquals(25, rangedweapon1.getDamage(), "Incorrect value was returned.");
    }

    @Test
    public void testSetCoolDown() {
        rangedweapon1.setCoolDown(5.0);
        assertEquals(5.0, rangedweapon1.getCoolDown(), "Incorrect value was returned.");
    }

    @Test
    public void testSetWeight() {
        rangedweapon1.setWeight(3.0);
        assertEquals(3.0, rangedweapon1.getWeight(), "Incorrect value was returned.");
    }

    @Test
    public void testSetMaterials() {
        HashMap<Materials, Integer> materialsTest = new HashMap<>();
        materialsTest.put(Materials.Wood, 3);

        rangedweapon1.setMaterials(materialsTest);

        HashMap<Materials, Integer> materialsTest2 = new HashMap<>();
        materialsTest2.put(Materials.Wood, 3);

        rangedweapon1.setMaterials(materialsTest);
        assertTrue(materialsTest2.equals(rangedweapon1.getMaterials()));
    }

}



