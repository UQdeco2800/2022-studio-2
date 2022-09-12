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
  private Image heartImage = new Image(ServiceLocator.getResourceService().getAsset("images/PlayerStatDisplayGraphics/Health-plunger/plunger_1.png", Texture.class));
  private Image staminaImage = new Image(ServiceLocator.getResourceService().getAsset("images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_1.png", Texture.class));
  private Image manaImage = new Image(ServiceLocator.getResourceService().getAsset("images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_1.png", Texture.class));
  private Label healthLabel;
  private Label staminaLabel;
  private Label manaLabel;
  private Boolean ishealth = false;

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
    table.add(heartImage).size(120, 30).pad(5);
    table.add(healthLabel);
    stage.addActor(table);
    table = new Table();
    table.top().left();
    table.setFillParent(true);
    table.padTop(90f).padLeft(5f);
    table.add(staminaImage).size(120, 30).pad(5);
    table.add(staminaLabel);
    stage.addActor(table);
    table = new Table();
    table.top().left();
    table.setFillParent(true);
    table.padTop(120f).padLeft(5f);
    table.add(manaImage).size(120, 30).pad(5);
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
    ishealth = true;
    heartImage = new Image(ServiceLocator.getResourceService().getAsset("images/PlayerStatDisplayGraphics/Health-plunger/plunger_"+checkImage(health), Texture.class));
    addActors();
    ishealth =false;
    CharSequence text = String.format("Health: %d", health);
    healthLabel.setText(text);


  }
  public String checkImage(int value){
    String filename= "";
    if (value ==100){
      filename= "1.png";
    }
    else if(value >=80 &&value<100 ){
      filename= "2.png";
    }
    else if (value <80 && value >=60){
      filename= "3.png";
    }
    else if (value <60 && value >=40){
      filename= "4.png";
    }
    else if (value <40 && value >=20){
      filename= "5.png";
    }
    else if (value <20 && value >=10){
      filename= "6.png";
    }
    else if (value ==0 && ishealth){
      filename = "8.png";
    }
    else if (value <10 && value >=0){
      filename= "7.png";
    }

    return filename;
  }


  /**
   * Updates the player's stamina on the ui.
   * @param stamina player stamina
   */
  public void updatePlayerStaminaUI(int stamina) {
    dispose();
    staminaImage = new Image(ServiceLocator.getResourceService().
            getAsset("images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_"+checkImage(stamina), Texture.class));
    addActors();
    CharSequence text = String.format("Stamina: %d", stamina);
    staminaLabel.setText(text);
  }

  /**
   * Updates the player's mana on the ui.
   * @param mana player mana
   */
  public void updatePlayerManaUI(int mana) {
    dispose();
    manaImage = new Image(ServiceLocator.getResourceService().getAsset("images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_"+checkImage(mana), Texture.class));
    addActors();
    CharSequence text = String.format("Mana: %d", mana);
    manaLabel.setText(text);
  }

  @Override
  public void dispose() {
    super.dispose();
    heartImage.remove();
    staminaImage.remove();
    manaImage.remove();
    healthLabel.remove();
    staminaLabel.remove();
    manaLabel.remove();
  }
}
