package com.deco2800.game.entities.configs;

import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.WeaponFactory;

/**
 * The statistics that make up the configuration of the Atlantis Citizen enemy.
 */
public class GymBroConfig extends BaseEntityConfig {
    public float speed = 2f;
    public int baseAttack = 5;
    public int health = 70;
    public Entity weapon = WeaponFactory.createDumbbell();
}