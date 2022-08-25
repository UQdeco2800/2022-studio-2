package com.deco2800.game.entities.configs;

/**
 * Defines a basic set of properties stored in entities config files to be loaded by Entity Factories.
 */
public class BaseEntityConfig {
    public int health = 30;
    public int stamina = 1;
    public int baseAttack = 5;
    public int mana= 20;
    public String enemyType = "Minion";
    public void setBoss() {
        enemyType = "Boss";
        this.health = 100;
        this.baseAttack = 15;
    }
}
