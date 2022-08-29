package com.deco2800.game.entities.configs;

import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.WeaponFactory;

/**
 * The statistics that make up the configuration of the Atlantis Citizen enemy.
 */
public class AtlantisCitizenConfig extends BaseEntityConfig {
    public float speed = 0f;
    public Entity weapon = WeaponFactory.createDumbbell();
}
