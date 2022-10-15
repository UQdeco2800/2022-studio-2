package com.deco2800.game.components.leveltransition;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.screens.LevelTransitionScreen;
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
    private static final int FPS = 15;
    private static final long FRAME_DURATION =  (1000 / FPS);
    private Table table;
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

        Image transitionImage;

        if (frame < LevelTransitionScreen.FRAME_COUNT) {
            // Clear the stage before doing anything
            stage.clear();

            // Create table for display
            table = new Table();
            table.setFillParent(true);

            // Load the new frame
            transitionImage = new Image(ServiceLocator.getResourceService()
                    .getAsset(LevelTransitionScreen.getTransitionTexture(frame), Texture.class));

            // Update variables for pseudo-animation management and then display it
            frame++;
            table.add(transitionImage);
            stage.addActor(table);
            lastFrameTime = System.currentTimeMillis();
            logger.debug("Updating transition display animation");
        }
    }

    /**
     * Custom update function to work as a pseudo-animation controller.
     */
    @Override
    public void update() {
        if (System.currentTimeMillis() - lastFrameTime > FRAME_DURATION) {
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
        table.clear();
        super.dispose();
    }
}
