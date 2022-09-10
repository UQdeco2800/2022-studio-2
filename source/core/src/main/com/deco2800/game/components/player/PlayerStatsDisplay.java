package com.deco2800.game.components.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

/**
 * A ui component for displaying player stats, e.g. health.
 */
public class PlayerStatsDisplay extends UIComponent {
  Table table;
  private Image heartImage = new Image(ServiceLocator.getResourceService().getAsset("images/heart.png", Texture.class));
  private Label healthLabel;
  private Label staminaLabel;
  private Label manaLabel;

  /**
   * Creates reusable ui styles and adds actors to the stage.
   */
  @Override
  public void create() {
    super.create();
    addActors();

    entity.getEvents().addListener("updateHealth", this::updatePlayerHealthUI);
    entity.getEvents().addListener("updateStamina", this::updatePlayerStaminaUI);
    entity.getEvents().addListener("updateMana", this::updatePlayerManaUI);

  }

  /**
   * Creates actors and positions them on the stage using a table.
   * @see Table for positioning options
   */
  private void addActors() {
    table = new Table();
    table.top().left();
    table.setFillParent(true);
    table.padTop(45f).padLeft(5f);

    // Heart image
    float heartSideLength = 120f;
//    heartImage = new Image(ServiceLocator.getResourceService().getAsset("images/heart.png", Texture.class));

    // Health text
    int health = entity.getComponent(CombatStatsComponent.class).getHealth();
    CharSequence healthText = String.format("Health: %d", health);
    healthLabel = new Label(healthText, skin, "large");

    // Stamina text
    int stamina = entity.getComponent(CombatStatsComponent.class).getStamina();
    CharSequence staminaText = String.format("Stamina: %d", stamina);
    staminaLabel = new Label(staminaText, skin, "large");

    // Mana text
    int mana = entity.getComponent(CombatStatsComponent.class).getMana();
    CharSequence manaText = String.format("Mana: %d", mana);
    manaLabel = new Label(manaText, skin, "large");
    table.add(heartImage).size(heartSideLength).pad(5);
    table.add(healthLabel);


    stage.addActor(table);
    table = new Table();
    table.top().left();
    table.setFillParent(true);
    table.padTop(90f).padLeft(5f);
    table.add(staminaLabel);
    stage.addActor(table);
    table = new Table();
    table.top().left();
    table.setFillParent(true);
    table.padTop(120f).padLeft(5f);
    table.add(manaLabel);
    stage.addActor(table);

  }

  @Override
  public void draw(SpriteBatch batch)  {
    // draw is handled by the stage
  }

  /**
   * Updates the player's health on the ui.
   * @param health player health
   */
  public void updatePlayerHealthUI(int health) {
    dispose();


    heartImage = new Image(ServiceLocator.getResourceService().getAsset("images/PlayerStatDisplayGraphic/Health_plunger/plunger_1.png", Texture.class));
    addActors();
    CharSequence text = String.format("Health: %d", health);
    healthLabel.setText(text);

  }

  /**
   * Updates the player's stamina on the ui.
   * @param stamina player stamina
   */
  public void updatePlayerStaminaUI(int stamina) {
    CharSequence text = String.format("Stamina: %d", stamina);
    staminaLabel.setText(text);
  }

  /**
   * Updates the player's mana on the ui.
   * @param mana player mana
   */
  public void updatePlayerManaUI(int mana) {
    CharSequence text = String.format("Mana: %d", mana);
    manaLabel.setText(text);
  }

  @Override
  public void dispose() {
    super.dispose();
    heartImage.remove();
    healthLabel.remove();
    staminaLabel.remove();
    manaLabel.remove();
  }
}
