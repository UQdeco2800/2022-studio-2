package com.deco2800.game.components.player;

import com.deco2800.game.CombatItems.Melee;
import com.deco2800.game.CombatItems.Ranged;
import com.deco2800.game.CombatItems.Weapon;
import com.deco2800.game.crafting.Buildable;
import com.deco2800.game.extensions.GameExtension;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
class InventoryComponentTest {

  @Test
  public void shouldHaveNoItem() {
    InventoryComponent testInventory1 = new InventoryComponent();
    assertEquals(testInventory1.getItems(), new ArrayList<>(16));
  }


  /**
   * I need help on this. This does not work now.
   */
//  @Test
//  void addItemTest() {
//    InventoryComponent testInventory2 = new InventoryComponent();
//    testInventory2.addItem(new Ranged(1,1,1,1));
//    List<Weapon> testList = new ArrayList<>(16);
//    testList.add(new Ranged(1,1, 1,1));
//    assertEquals(testInventory2.getItems(), testList);
//  }

}
