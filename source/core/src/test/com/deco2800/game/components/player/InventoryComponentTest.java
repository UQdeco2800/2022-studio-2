package com.deco2800.game.components.player;


import com.deco2800.game.crafting.Buildable;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.WeaponFactory;
import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
class InventoryComponentTest {

  @Test
  void createEmptyInventory() {
    InventoryComponent testInventory1 = new InventoryComponent();
    assertEquals(testInventory1.getItems(), new ArrayList<>(16));
  }
/*
  @Test
  void addItem() {
    InventoryComponent testInventory2 = new InventoryComponent();
    Entity testMelee = WeaponFactory.getWeapon(0);
    testInventory2.addItem((Buildable) testMelee);
    List<Entity> expectedList = new ArrayList<>(16);
    expectedList.add(testMelee);
    assertEquals(testInventory2.getItems(), expectedList);
  }

  @Test
  void removeItem() {
    InventoryComponent testInventory3 = new InventoryComponent();
    Entity testMelee = WeaponFactory.getWeapon(0);
    List<Entity> expectedList = new ArrayList<>(16);

    testInventory3.addItem((Buildable) testMelee);
    expectedList.add(testMelee);

    expectedList.remove(testMelee);
    testInventory3.removeItem((Buildable) testMelee);
    assertEquals(testInventory3.getItems(), expectedList);
  }

 */

  /*
  @Test
  void addItem() {
    InventoryComponent testInventory2 = new InventoryComponent();
    Weapon testRanged = new Ranged(1,1,1,1);
    testInventory2.addItem(testRanged);
    List<Weapon> expectedList = new ArrayList<>(16);
    expectedList.add(testRanged);
    assertEquals(testInventory2.getItems(), expectedList);
  }

   */

  /*
  @Test
  void removeItem() {
    InventoryComponent testInventory3 = new InventoryComponent();
    Weapon testRanged = new Ranged(1,1,1,1);
    List<Weapon> expectedList = new ArrayList<>(16);

    testInventory3.addItem(testRanged);
    expectedList.add(testRanged);

    expectedList.remove(testRanged);
    testInventory3.removeItem(testRanged);
    assertEquals(testInventory3.getItems(), expectedList);
  }

   */


}
