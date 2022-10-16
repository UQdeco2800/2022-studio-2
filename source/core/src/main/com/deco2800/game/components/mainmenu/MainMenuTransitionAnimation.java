package com.deco2800.game.components.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.screens.MainMenuTransitionScreen;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainMenuTransitionAnimation extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(MainMenuTransitionAnimation.class);
    private static final float Z_INDEX = 2f;
    private Table table;
    private Image transitionFrames;
    private int fps = 15;
    private final long frameDuration =  (long)(300 / fps);
    public static int frame;
    private long lastFrameTime;
    public static Boolean canOpen = false;

    @Override
    public void create() {
        frame = 0;
        super.create();
        addActors();
    }

    private void addActors() {
        if (frame < MainMenuTransitionScreen.frameCount) {
            stage.clear();

            transitionFrames = new Image(ServiceLocator.getResourceService()
                    .getAsset(MainMenuTransitionScreen.transitionTextures[frame], Texture.class));

            transitionFrames.setWidth(Gdx.graphics.getWidth());
            transitionFrames.setHeight(Gdx.graphics.getHeight());
            frame++;
            logger.info("frame = " + frame);
            stage.addActor(transitionFrames);
            lastFrameTime = System.currentTimeMillis();
        }
    }

    @Override
    public void update() {
        if (System.currentTimeMillis() - lastFrameTime > frameDuration) {
            addActors();
        }
    }
//
//    @Override
//    public void update() {
//        addActors();
//    }

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
