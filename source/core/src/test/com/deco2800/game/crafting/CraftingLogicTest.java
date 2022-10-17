package com.deco2800.game.crafting;

//import com.deco2800.game.CombatItems.Weapon;
import com.deco2800.game.entities.configs.combatitemsconfig.WeaponConfig;
import com.deco2800.game.entities.configs.combatitemsconfig.WeaponConfigSetup;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.files.FileLoader;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
public class CraftingLogicTest {


    @Before
    public void setup() {
    }

    @Test
    void basicGetPossibleBuildsTest() {
        List<WeaponConfig> imaginaryWeapons = new ArrayList<>();
        CraftingLogic.setPossibleBuilds(imaginaryWeapons);
        assertEquals(imaginaryWeapons, CraftingLogic.getPossibleBuilds());
    }

    @Test
    void basicSetPossibleBuildsTest() {
        List<WeaponConfig> inputList = new ArrayList<>();
        CraftingLogic.setPossibleBuilds(inputList);
        assertEquals(inputList, CraftingLogic.getPossibleBuilds());
    }

    @Test
    void getWeaponsTest() {
//        List<MeleeConfig> possibleWeaponsTest = CraftingLogic.getPossibleWeapons();
//        assertEquals(possibleWeaponsTest, CraftingLogic.getPossibleWeapons());
        WeaponConfigSetup configs =
                FileLoader.readClass(WeaponConfigSetup.class, "configs/Weapons.json");
        List<WeaponConfig> possibleWeaponsTest = new ArrayList<>();
        possibleWeaponsTest.add(configs.athenaDag);
        possibleWeaponsTest.add(configs.heraDag);
        possibleWeaponsTest.add(configs.SwordLvl2);
        possibleWeaponsTest.add(configs.dumbbell);
        possibleWeaponsTest.add(configs.tridentLvl2);
        possibleWeaponsTest.add(configs.heraAthenaDag);
        possibleWeaponsTest.add(configs.plunger);
        possibleWeaponsTest.add(configs.pipe);
        possibleWeaponsTest.add(configs.goldenPlungerBow);
        possibleWeaponsTest.add(configs.plungerBow);

        assertNotSame(possibleWeaponsTest, CraftingLogic.getPossibleWeapons());

//        for (int i = 0; i < CraftingLogic.getPossibleWeapons().size(); i++){
//
//            switch (i) {
//                case 0:
//                    assertEquals(CraftingLogic.getPossibleWeapons().get(i), configs.athenaDag);
//                    break;
//                case 1:
//                    assertEquals(CraftingLogic.getPossibleWeapons().get(i), configs.heraDag);
//                    break;
//                case 2:
//                    assertEquals(CraftingLogic.getPossibleWeapons().get(i), configs.SwordLvl2);
//                    break;
//                case 3:
//                    assertEquals(CraftingLogic.getPossibleWeapons().get(i), configs.dumbbell);
//                    break;
//                case 4:
//                    assertEquals(CraftingLogic.getPossibleWeapons().get(i), configs.tridentLvl2);
//                    break;
//                case 5:
//                    assertEquals(CraftingLogic.getPossibleWeapons().get(i), configs.heraAthenaDag);
//                    break;
//                case 6:
//                    assertEquals(CraftingLogic.getPossibleWeapons().get(i), configs.plunger);
//                    break;
//            }
//        for (int i = 0; i < CraftingLogic.getPossibleWeapons().size(); i++) {
//            assertSame(CraftingLogic.getPossibleWeapons().get(i), CraftingLogic.getPossibleWeapons().get(i));
//       }
    }

    @Test
    void canBuildTest() {
        List<WeaponConfig> buildItemsTest = new ArrayList<>();
        List<Materials> inventoryContentsTest = new ArrayList<>();
        inventoryContentsTest.add(Materials.Steel);
        inventoryContentsTest.add(Materials.Wood);
        inventoryContentsTest.add(Materials.Gold);
        inventoryContentsTest.add(Materials.Iron);
        inventoryContentsTest.add(Materials.Poop);
        CraftingLogic.canBuild(inventoryContentsTest);
        assertEquals(CraftingLogic.canBuild(inventoryContentsTest), buildItemsTest);
    }

//    @Test
//    void damageToWeaponTest() {
//        WeaponConfigSetup configs =
//                FileLoader.readClass(WeaponConfigSetup.class, "configs/Weapons.json");
//        WeaponConfig athenaDag = configs.athenaDag;
//        WeaponConfig heraDag = configs.heraDag;
//        WeaponConfig dumbbell = configs.dumbbell;
//        WeaponConfig swordLvl2 = configs.SwordLvl2;
//        WeaponConfig tridentLvl2 = configs.tridentLvl2;
//        WeaponConfig heraAthenaDag = configs.heraAthenaDag;
//        WeaponConfig plunger = configs.plunger;
//        WeaponConfig pipe = configs.pipe;
//        WeaponConfig plungerBow = configs.plungerBow;
//        WeaponConfig goldenPlungerBow = configs.goldenPlungerBow;
//        assertTrue(CraftingLogic.damageToWeapon(athenaDag).checkEntityType(EntityTypes.WEAPON));
//        assertEquals(WeaponFactory.createDagger() ,CraftingLogic.damageToWeapon(athenaDag));
//        MeleeConfig daggerConfig = new MeleeConfig();
//        daggerConfig.damage = 7;
//        double damage = daggerConfig.damage;
//        assertEquals(CraftingLogic.damageToWeapon(daggerConfig), WeaponFactory.createDagger());
//        }
}
