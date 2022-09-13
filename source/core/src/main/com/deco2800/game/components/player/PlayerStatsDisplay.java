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

  // Text for the stat labels
  private Label healthLabel;
  private Label staminaLabel;
  private Label manaLabel;
  private Boolean isHealth = false;

  // Dimensions of the stamina image
  private int staminaWidth = 120;

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
    // Creates a new table
    table = new Table();
    // Places table at top and to the left of the screen
    table.top().left();
    table.setFillParent(true);
    // Sets padding of table
    table.padTop(45f).padLeft(5f);

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

    // Adding health bar text and image to the table
    table.row();
    table.add(heartImage).size(120, 50).pad(10).left();
    table.add(healthLabel).right();
    stage.addActor(table);

    // Adding stamina bar text and image to the table
    table.row();
    table.add(staminaImage).size(staminaWidth, 50).pad(10).left();
    table.add(staminaLabel);
    stage.addActor(table);

    // Adding mana bar text and image to the table
    table.row();
    table.add(manaImage).size(60, 60).pad(10).left();
    table.add(manaLabel).right();
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
    // Sets health image to correct image depending on stamina given
    isHealth = true;
    heartImage = new Image(ServiceLocator.getResourceService().getAsset("images/PlayerStatDisplayGraphics/Health-plunger/plunger_"+checkImage(health), Texture.class));
    addActors();
    isHealth =false;
    // Sets health text to correct image depending on stamina given
    CharSequence text = String.format("Health: %d", health);
    healthLabel.setText(text);


  }

  /**
   * Changes the image depending on the value given
   * @param value the statistic of either health, stamina, or mana
   * @return filename change the filename of the image accordingly
   */
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
    else if (value ==0 && isHealth){
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
    // Sets stamina image to correct image depending on stamina given
    staminaImage = new Image(ServiceLocator.getResourceService().getAsset("images/PlayerStatDisplayGraphics/Stamina-tp/tp-stamina_"+checkImage(stamina), Texture.class));
    playerStaminaImage(stamina);

    addActors();
    // Sets stamina text to correct stat depending on stamina given
    CharSequence text = String.format("Stamina: %d", stamina);
    staminaLabel.setText(text);
  }

  /**
   * Simulates a reflexive size for the stamina image.
   * @param stamina the current stamina statistic to determine size of image
   */
  public void playerStaminaImage(int stamina) {
    if (stamina == 100) {
      staminaWidth = 120;
    } else if (stamina >= 80 && stamina < 100) {
      staminaWidth = 110;
    } else if (stamina < 80 && stamina >= 60) {
      staminaWidth = 100;
    } else if (stamina < 60 && stamina >= 40) {
      staminaWidth = 90;
    } else if (stamina < 40 && stamina >= 20) {
      staminaWidth = 80;
    } else if (stamina < 20 && stamina >= 10) {
      staminaWidth = 70;
    } else if (stamina == 0 && isHealth) {
      staminaWidth = 60;
    } else if (stamina < 10 && stamina >= 0) {
      staminaWidth = 60;
    }
  }

  /**
   * Updates the player's mana on the ui.
   * @param mana player mana
   */
  public void updatePlayerManaUI(int mana) {
    dispose();
    // Sets mana image to correct image depending on stamina given
    manaImage = new Image(ServiceLocator.getResourceService().getAsset("images/PlayerStatDisplayGraphics/Mana-bucket/bucket-mana_"+checkImage(mana), Texture.class));
    addActors();
    // Sets mana text to correct image depending on stamina given
    CharSequence text = String.format("Mana: %d", mana);
    manaLabel.setText(text);
  }

  /**
   * Disposes of all images and labels
   */
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
