package com.deco2800.game.entities.configs;

/**
 * Defines the properties stored in player config files to be loaded by the Player Factory.
 */
public class PlayerConfig extends BaseEntityConfig  {
  public int gold = 1;
  public int stamina = 100;
  public int maxStamina = 100;
  public int staminaRegenerationRate = 1;
  public String favouriteColour = "none";
}
