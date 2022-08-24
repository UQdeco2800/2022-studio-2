package com.deco2800.game.entities.configs;

/**
 * Defines a basic set of properties stored in entities config files to be loaded by Entity Factories.
 */
public class BaseEntityConfig {
    public String enemyType = "Minion";
    public int health = 30;
    public int baseAttack = 5;

    public void setBoss() {
        enemyType = "Boss";
        this.health = 100;
        this.baseAttack = 15;
    }
}
