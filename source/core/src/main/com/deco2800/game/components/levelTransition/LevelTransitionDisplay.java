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
import com.deco2800.game.screens.LevelTransitionScreen;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A ui component for displaying the Transition Screen.
 */
public class LevelTransitionDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(LevelTransitionDisplay.class);
    private static final float Z_INDEX = 2f;
    private Table table;
    private Image transitionImage;
    private int fps = 15;
    private long frameDuration =  (long)(1000 / fps);
    private int frame;
    private long lastFrameTime;

    @Override
    public void create() {
        frame = 0;
        super.create();
        addActors();
    }

    /**
     * Adds table to the screen, specifically adds the image of the transition screen. Given
     * the transition screen frames are too large, they must be manually displayed by this function.
     */
    private void addActors() {

        if (frame < LevelTransitionScreen.frameCount) {
            // Clear the stage before doing anything
            stage.clear();

            // Create table for display
            table = new Table();
            table.setFillParent(true);

            // Load the new frame
            transitionImage = new Image(ServiceLocator.getResourceService()
                    .getAsset(LevelTransitionScreen.transitionTextures[frame], Texture.class));

            // Update variables for pseudo-animation management and then display it
            frame++;
            table.add(transitionImage);
            stage.addActor(table);
            lastFrameTime = System.currentTimeMillis();
            logger.debug("Updating transition display animation");
        }
    }

    @Override
    public void update() {
        if (System.currentTimeMillis() - lastFrameTime > frameDuration) {
            addActors();
        }
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
