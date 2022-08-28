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
  private Image wood;
  private Image steel;
  private Group craftingGroup = new Group();
  List<Materials> inventory;
  //InputMultiplexer inputMultiplexer;

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

  public void openCraftingMenu() {
    craftMenu = new Image(new Texture(Gdx.files.internal
            ("images/Crafting-assets-sprint1/crafting table/craftingUI.png")));
    craftMenu.setPosition(Gdx.graphics.getWidth()/2 - craftMenu.getWidth()/2,
            Gdx.graphics.getHeight()/2 - craftMenu.getHeight()/2);
    craftingGroup.addActor(craftMenu);
    buttonTexture = new Texture(Gdx.files.internal
            ("images/Crafting-assets-sprint1/widgets/craftButton.png"));
    buttonTextureRegion = new TextureRegion(buttonTexture);
    buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
    craftButton = new ImageButton(buttonDrawable);
    craftButton.setPosition(craftMenu.getX() + 650, craftMenu.getY() + 130);
    craftButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        //Craft stuff
        System.out.println("I was clicked");
      }
    });
    craftingGroup.addActor(craftButton);
    getInventory();
    stage.addActor(craftingGroup);
    stage.draw();
  }

  private void getInventory() {
    inventory = CraftingSystem.getInventoryContents();
    try {
      for (int i = 0; i < inventory.size(); i++) {
        switch (inventory.get(i)) {
          case Wood:
            wood = new Image(new Texture(Gdx.files.internal
                    ("images/Crafting-assets-sprint1/materials/wood.png")));
            wood.setPosition(craftMenu.getX() + 100 + (i * 100),
                    (float) (craftMenu.getY() + craftMenu.getHeight() - (Math.floor(i / 4) * 100)));
            craftingGroup.addActor(wood);
            break;
          case Steel:
            steel = new Image(new Texture(Gdx.files.internal
                    ("images/Crafting-assets-sprint1/materials/steel.png")));
            steel.setPosition(craftMenu.getX() + 100 + (i * 100),
                    (float) (craftMenu.getY() + craftMenu.getHeight() - (Math.floor(i / 4) * 100)));
            craftingGroup.addActor(steel);
            break;
        }
      }
    } catch (Exception e) {}
    System.out.println(inventory);
  }

  public void disposeCraftingMenu() {
    craftingGroup.remove();
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
