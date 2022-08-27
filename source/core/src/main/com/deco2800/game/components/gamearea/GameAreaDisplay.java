package com.deco2800.game.components.gamearea;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

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

  public GameAreaDisplay(String gameAreaName) {
    //Gdx.input.setInputProcessor(new InputMultiplexer());
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
    buttonTexture = new Texture(Gdx.files.internal
            ("images/Crafting-assets-sprint1/widgets/craftButton.png"));
    buttonTextureRegion = new TextureRegion(buttonTexture);
    buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
    craftButton = new ImageButton(buttonDrawable);
    stage.addActor(craftMenu);
    stage.addActor(craftButton);
    /*InputMultiplexer inputMultiplexer = (InputMultiplexer)Gdx.input.getInputProcessor();
    if (!inputMultiplexer.getProcessors().contains(stage, false)) {
      inputMultiplexer.addProcessor(stage);
    }
     */
    Gdx.input.setInputProcessor(stage);
    craftMenu.setPosition(Gdx.graphics.getWidth()/2 - craftMenu.getWidth()/2,
            Gdx.graphics.getHeight()/2 - craftMenu.getHeight()/2);
    craftButton.setPosition(craftMenu.getX() + 650, craftMenu.getY() + 130);
    craftButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        //Craft stuff
        System.out.println("I was clicked");
      }
    });
    stage.draw();
  }

  public void disposeCraftingMenu() {
    craftMenu.remove();
    craftButton.remove();
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
