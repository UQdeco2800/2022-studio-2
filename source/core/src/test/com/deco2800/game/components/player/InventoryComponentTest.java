package com.deco2800.game.components.player;

import com.deco2800.game.CombatItems.Melee;
import com.deco2800.game.CombatItems.Ranged;
import com.deco2800.game.CombatItems.Weapon;
import com.deco2800.game.crafting.Buildable;
import com.deco2800.game.extensions.GameExtension;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
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


  /**
   * I need help on this. This does not work now.
   */
  @Test
  void addItem() {
    InventoryComponent testInventory2 = new InventoryComponent();
    Weapon testRanged = new Ranged(1,1,1,1);
    testInventory2.addItem(testRanged);
    List<Weapon> expectedList = new ArrayList<>(16);
    expectedList.add(testRanged);
    assertEquals(testInventory2.getItems(), expectedList);
  }

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


}
