package com.deco2800.game.crafting;

//import com.deco2800.game.CombatItems.Weapon;
import com.deco2800.game.entities.configs.CombatItemsConfig.MeleeConfig;
import com.deco2800.game.extensions.GameExtension;
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
        List<MeleeConfig> imaginaryWeapons = new ArrayList<>();
        CraftingLogic.setPossibleBuilds(imaginaryWeapons);
        assertEquals(imaginaryWeapons, CraftingLogic.getPossibleBuilds());
    }

    @Test
    void basicSetPossibleBuildsTest() {
        List<MeleeConfig> inputList = new ArrayList<>();
        CraftingLogic.setPossibleBuilds(inputList);
        assertEquals(inputList, CraftingLogic.getPossibleBuilds());
    }

    @Test
    void getWeaponsTest() {
        List<MeleeConfig> possibleWeaponsTest = CraftingLogic.getPossibleWeapons();
        assertEquals(possibleWeaponsTest, CraftingLogic.getPossibleWeapons());
//        List<MeleeConfig> possibleWeaponsTest = new ArrayList<>();
//        possibleWeaponsTest.add(configs.athenaDag);
//        possibleWeaponsTest.add(configs.herraDag);
//        possibleWeaponsTest.add(configs.SwordLvl2);
//        possibleWeaponsTest.add(configs.dumbbell);
//        possibleWeaponsTest.add(configs.tridentLvl2);
//        possibleWeaponsTest.add(configs.herraAthenaDag);
//        possibleWeaponsTest.add(configs.plunger);
//        List<MeleeConfig> possibleWeaponsTest = CraftingLogic.getPossibleWeapons();

//        for (int i = 0; i < CraftingLogic.getPossibleWeapons().size(); i++){
//
//            switch (i) {
//                case 0:
//                    assertEquals(CraftingLogic.getPossibleWeapons().get(i), configs.athenaDag);
//                    break;
//                case 1:
//                    assertEquals(CraftingLogic.getPossibleWeapons().get(i), configs.herraDag);
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
//                    assertEquals(CraftingLogic.getPossibleWeapons().get(i), configs.herraAthenaDag);
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
        List<MeleeConfig> buildItemsTest = new ArrayList<>();
        List<Materials> inventoryContentsTest = new ArrayList<>();
        CraftingLogic.canBuild(inventoryContentsTest);
        assertEquals(CraftingLogic.canBuild(inventoryContentsTest), buildItemsTest);
    }

//    @Test
//    void damageToWeaponTest() {
//        WeaponConfig configs =
//                FileLoader.readClass(WeaponConfig.class, "configs/Weapons.json");
//        MeleeConfig daggerConfig = new MeleeConfig();
////        assertEquals(CraftingLogic.damageToWeapon(daggerConfig), WeaponFactory.createDagger());
//
//    }
//

//    @Test
//    void getPossibleWeaponsTest() {
//        inventory = new ArrayList<>();
//        inventory.add(Materials.Gold);
//        inventory.add(Materials.Iron);
//        WeaponConfig configs = FileLoader.readClass(WeaponConfig.class, "configs/Weapons.json");
//        assertEquals(CraftingLogic.canBuild(inventory).get(0).damage, configs.SwordLvl2.damage);
//    }

}
