package com.deco2800.game.components.levelTransition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A ui component for displaying the Death Screen.
 */
public class LevelTransitionDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(LevelTransitionDisplay.class);
    private static final float Z_INDEX = 1f;
    private Table table;
    // The image of the deathBackground
    private Image transitionImage;
    // Global variable of game level

    @Override
    public void create() {
        super.create();
        addActors();
    }


    /**
     * Adds table to the screen, specifically adds the image of the transition screen
     */
    private void addActors() {
        table = new Table();
        table.setFillParent(true);

        transitionImage = new Image(ServiceLocator.getResourceService()
                .getAsset("images/DeathScreens/lvl_2.png", Texture.class));

        entity.getEvents().addListener("nextMap", this::transitionDisplay);

        table.add(transitionImage);
        stage.addActor(table);
        logger.debug("DeathScreenDisplay table has been added to the actor");



        //Below is in case we need buttons for transition screen, although I doubt it.
//        TextButton continueBtn = new TextButton("PLAY AGAIN", skin);
//        logger.debug("Continue button and Exit button created");
//
//        // Triggers an event when the button is pressed
//        // Restarts game
//        continueBtn.addListener(
//                new ChangeListener() {
//                    @Override
//                    public void changed(ChangeEvent changeEvent, Actor actor) {
//                        logger.debug("The Continue button was clicked");
//                        entity.getEvents().trigger("continueGame");
//                    }
//                });
    }



    public void transitionDisplay() {

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
    // Remove the image here

        table.clear();
        super.dispose();
    }
}
