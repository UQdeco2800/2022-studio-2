package com.deco2800.game.components.gamearea;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Null;
import com.deco2800.game.crafting.CraftingSystem;
import com.deco2800.game.crafting.Materials;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays the name of the current game area.
 */
public class GameAreaDisplay extends UIComponent {
  private String gameAreaName = "";
  private Label title;
  private ImageButton craftButton;
  private Texture buttonTexture;
  private TextureRegion buttonTextureRegion;
  private TextureRegionDrawable buttonDrawable;
  private Image craftMenu;
  private ImageButton wood;
  private Texture woodTexture;
  private TextureRegion woodTextureRegion;
  private TextureRegionDrawable woodDrawable;
  private ImageButton steel;
  private Texture steelTexture;
  private TextureRegion steelTextureRegion;
  private TextureRegionDrawable steelDrawable;
  private Image weapon;
  private Group craftingGroup = new Group();
  private int count;
  List<Materials> inventory;

  public GameAreaDisplay(String gameAreaName) {
    this.gameAreaName = gameAreaName;
    ServiceLocator.registerCraftArea(this);
  }

  @Override
  public void create() {
    super.create();
    addActors();
  }

  private void addActors() {
    title = new Label(this.gameAreaName, skin, "large");
    stage.addActor(title);
  }

  /**
   * Code that opens an overlay crafting menu when the craft button is pressed. Creates assets based on users inventory
   * and creates button event handlers to test for user clicks.
   */
  public void openCraftingMenu() {
    craftMenu = new Image(new Texture(Gdx.files.internal
            ("images/Crafting-assets-sprint1/crafting table/crafting_inventory.png")));
    craftMenu.setSize(883.26f, 500);
    craftMenu.setPosition(Gdx.graphics.getWidth()/2 - craftMenu.getWidth()/2,
            Gdx.graphics.getHeight()/2 - craftMenu.getHeight()/2);
    craftingGroup.addActor(craftMenu);
    buttonTexture = new Texture(Gdx.files.internal
            ("images/Crafting-assets-sprint1/widgets/craft_button.png"));
    buttonTextureRegion = new TextureRegion(buttonTexture);
    buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
    craftButton = new ImageButton(buttonDrawable);
    craftButton.setSize(146, 146);
    craftButton.setPosition(craftMenu.getX() + 527, craftMenu.getY() + 110.5f);
    craftButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        if (weapon != null) {
          weapon.setPosition(craftMenu.getX() + 180, craftMenu.getTop() - 200);
          wood.remove();
          steel.remove();
        }
      }
    });
    craftingGroup.addActor(craftButton);
    getInventory();
    entity.getEvents().addListener("check", this::displayWeapon);
    stage.addActor(craftingGroup);
    stage.draw();
  }

  private void getInventory() {
    count = 0;
    CraftingSystem craftingSystem = new CraftingSystem();
    inventory = craftingSystem.getInventoryContents();
    try {
      for (int i = 0; i < inventory.size(); i++) {
        switch (inventory.get(i)) {
          case Wood:
            woodTexture = new Texture(Gdx.files.internal
                    ("images/Crafting-assets-sprint1/materials/wood.png"));
            woodTextureRegion = new TextureRegion(woodTexture);
            woodDrawable = new TextureRegionDrawable(woodTextureRegion);
            wood = new ImageButton(woodDrawable);
            wood.setSize(50, 50);
            wood.setPosition(craftMenu.getX() + 170 + (i * 60),
                    (float) (craftMenu.getTop() - ((Math.floor(i / 4) * 100) + 208)));
            wood.addListener(new ChangeListener() {
              @Override
              public void changed(ChangeEvent event, Actor actor) {
                wood.setPosition(craftMenu.getX() + 480, craftMenu.getY() + 230);
                count++;
                entity.getEvents().trigger("check");
              }
            });
            craftingGroup.addActor(wood);
            break;
          case Steel:
            steelTexture = new Texture(Gdx.files.internal
                    ("images/Crafting-assets-sprint1/materials/steel.png"));
            steelTextureRegion = new TextureRegion(steelTexture);
            steelDrawable = new TextureRegionDrawable(steelTextureRegion);
            steel = new ImageButton(steelDrawable);
            steel.setSize(50, 50);
            steel.setPosition(craftMenu.getX() + 170 + (i * 70),
                    (float) (craftMenu.getTop() - ((Math.floor(i / 4) * 100) + 208)));
            steel.addListener(new ChangeListener() {
              @Override
              public void changed(ChangeEvent event, Actor actor) {
                steel.setPosition(craftMenu.getX() + 548, craftMenu.getY() + 230);
                count++;
                entity.getEvents().trigger("check");
              }
            });
            craftingGroup.addActor(steel);
            break;
        }
      }
    } catch (Exception e) {}
  }

  public void disposeCraftingMenu() {
    craftingGroup.remove();
  }

  private void displayWeapon() {
    if (count == 2) {
      weapon = new Image(new Texture(Gdx.files.internal
              ("images/CombatWeapons-assets-sprint1/Sword_Lvl2.png")));
      weapon.setSize(50, 50);
      weapon.setPosition(craftMenu.getX() + 674, craftMenu.getY() + 237);
      craftingGroup.addActor(weapon);
    }
  }

  @Override
  public void draw(SpriteBatch batch)  {
    int screenHeight = Gdx.graphics.getHeight();
    float offsetX = 10f;
    float offsetY = 30f;

    title.setPosition(offsetX, screenHeight - offsetY);
  }

  @Override
  public void dispose() {
    super.dispose();
    title.remove();
  }
}