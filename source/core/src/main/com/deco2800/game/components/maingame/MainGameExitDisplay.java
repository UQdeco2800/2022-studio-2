package com.deco2800.game.components.maingame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.SkillsTree.SkillsTreeDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Displays a button to exit the Main Game screen to the Main Menu screen.
 */
public class MainGameExitDisplay extends UIComponent {
  private static final Logger logger = LoggerFactory.getLogger(MainGameExitDisplay.class);
  private static final float Z_INDEX = 2f;
  private Table table;

  @Override
  public void create() {
    super.create();
    addActors();
  }

  private void addActors() {
    table = new Table();
    table.top().right();
    table.setFillParent(true);

//    TextButton mainMenuBtn = new TextButton("Exit", skin);
    TextureRegionDrawable skillTreeButtonTexture = new TextureRegionDrawable(ServiceLocator.getResourceService()
            .getAsset("images/Skills/skill-tree-icon.png", Texture.class));
    ImageButton skillsTreeBtn = new ImageButton(skillTreeButtonTexture);

    // Triggers an event when the button is pressed.
//    mainMenuBtn.addListener(
//      new ChangeListener() {
//        @Override
//        public void changed(ChangeEvent changeEvent, Actor actor) {
//          logger.debug("Exit button clicked");
//          entity.getEvents().trigger("exit");
//        }
//      });

    skillsTreeBtn.addListener(
      new ChangeListener() {
        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
          logger.debug("SkillsTree button clicked");
          entity.getComponent(SkillsTreeDisplay.class).toggleSkillTreeDisplay();
        }
      });

//    table.add(mainMenuBtn).padTop(10f).padRight(10f);
    table.add(skillsTreeBtn).padTop(11f).padRight(11f);

    stage.addActor(table);
  }

  @Override
  public void draw(SpriteBatch batch) {
    // draw is handled by the stage
  }

  @Override
  public float getZIndex() {
    return Z_INDEX;
  }

  @Override
  public void dispose() {
    table.clear();
    super.dispose();
  }
}