package com.deco2800.game.entities.configs;

import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.WeaponFactory;

/**
 * The statistics that make up the configuration of Heracles, the level 1 boss.
 */
public class HeraclesConfig extends BaseEntityConfig {
    public float speed = 100f;
    public Entity weapon = WeaponFactory.createDumbbell();

}

