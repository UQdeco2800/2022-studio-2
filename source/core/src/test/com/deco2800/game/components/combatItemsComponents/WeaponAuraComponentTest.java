package com.deco2800.game.components.combatItemsComponents;
import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(GameExtension.class)
class WeaponAuraComponentTest {
    public WeaponAuraComponent aura1;
    public WeaponAuraComponent aura2;

    @BeforeEach
    public void setUp() throws Exception {
        aura1 = new WeaponAuraComponent(5, 1.25, 0.75, "fire");
        aura2 = new WeaponAuraComponent(10, 2, 2,1, 5);
    }

    @Test
    public void testSetAuraDuration() {
        aura1.setAuraDuration(7);
        assertEquals(7, aura1.getAuraDuration(), "Incorrect value was returned.");
    }

    @Test
    public void testSetDmgMultiplier() {
        aura1.setDmgMultiplier(5);
        assertEquals(5, aura1.getDmgMultiplier(), "Incorrect value was returned.");
    }

    @Test
    public void testSetCdMultiplier() {
        aura1.setCdMultiplier(1);
        assertEquals(1, aura1.getCdMultiplier(), "Incorrect value was returned.");
    }

    @Test
    public void testSetWeightMultiplier() {
        aura1.setDmgMultiplier(5);
        assertEquals(5, aura1.getDmgMultiplier(), "Incorrect value was returned.");
    }

    @Test
    public void testSetAreaMultiplier() {
        aura2.setAreaMultiplier(3);
        assertEquals(3, aura2.getAreaMultiplier(), "Incorrect value was returned.");
    }

    @Test
    public void testGetAuraMultiplier() {
        assertEquals(5, aura1.getAuraDuration(), "Incorrect value was returned.");
        assertEquals(10, aura2.getAuraDuration(), "Incorrect value was returned.");
    }

    @Test
    public void testGetDmgMultiplier() {
        assertEquals(1.25, aura1.getDmgMultiplier(), "Incorrect value was returned.");
        assertEquals(2, aura2.getDmgMultiplier(), "Incorrect value was returned.");
    }

    @Test
    public void testGetCdMultiplier() {
        assertEquals(0.75, aura1.getCdMultiplier(), "Incorrect value was returned.");
        assertEquals(5, aura2.getCdMultiplier(), "Incorrect value was returned.");
    }

    @Test
    public void testGetDurationMultiplier() {
        assertEquals(1, aura2.getDurationMultiplier(), "Incorrect value was returned.");
    }

    @Test
    public void testGetDescription() {
        assertEquals("fire", aura1.getDescription(), "Incorrect value was returned.");
    }
}