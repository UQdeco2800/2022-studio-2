package com.deco2800.game.entities.configs;

/**
 * Defines the properties stored in player config files to be loaded by the Player Factory.
 */
public class PlayerConfig extends BaseEntityConfig  {

  public int gold = 100;
  public int staminaRegenerationRate = 1;
  public String favouriteColour = "none";
  public float moveSpeed = 5f;
  public int stamina = 100;
  public int mana = 0;
}
