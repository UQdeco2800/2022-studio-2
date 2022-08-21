package com.deco2800.game.entities.configs;

/**
 * Defines the properties stored in player config files to be loaded by the Player Factory.
 */
public class PlayerConfig extends BaseEntityConfig  {
  public int gold = 100;
  public String favouriteColour = "none";
  public float moveSpeed = 5f;
  public float stamina = 0f;
  public float mana = 0f;
}
