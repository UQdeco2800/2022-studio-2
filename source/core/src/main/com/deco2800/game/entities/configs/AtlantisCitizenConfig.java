package com.deco2800.game.entities.configs;

import com.deco2800.game.CombatItems.Melee;
import com.deco2800.game.CombatItems.Weapon;

public class AtlantisCitizenConfig extends BaseEntityConfig {
    public float movementSpeed = 0.05f;
    public Weapon weapon = new Melee(1,1, 1, 1);
}
