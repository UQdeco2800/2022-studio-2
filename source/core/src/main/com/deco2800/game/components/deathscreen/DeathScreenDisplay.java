package com.deco2800.game.components.deathscreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A ui component for displaying the Death Screen.
 */
public class DeathScreenDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(DeathScreenDisplay.class);
    private static final float Z_INDEX = 2f;
    private Table table;
    private Image deathBackground = new Image(ServiceLocator.getResourceService()
            .getAsset("images/DeathScreens/lvl_1.png", Texture.class));

    @Override
    public void create() {
        super.create();
        addActors();
    }


    private void addActors() {
        table = new Table();
        table.setFillParent(true);


        TextButton yesBtn = new TextButton("Yes", skin);
        TextButton noBtn = new TextButton("No", skin);


        // Triggers an event when the button is pressed
        // For now it swtiches back to main game screen like in main menu.
        // TODO check if I need to swap this to saved version
        yesBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("The Yes button was clicked");
                        entity.getEvents().trigger("continueGame");
                    }
                });

        //For now it exits the game when no is pressed
        noBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("The No button was clicked");
                        entity.getEvents().trigger("exit");
                    }
                });


        // TODO adjust layout of table to make it align with what we planed for death screen
        // https://gamedev.stackexchange.com/questions/144876/how-do-you-stack-things-in-between-a-table
        table.add(deathBackground);
        table.row();
        table.add(yesBtn).padTop(30f).padLeft(30f).left();
        table.add(noBtn).padTop(30f).padRight(30f).right();
        table.row();

        stage.addActor(table);
    }

    /**
     * Adjusts DeathScreens background image based on given level
     * @param level the games level
     */
    // TODO workout where in the code i can set this, I am not sure how i check what the level is.
    public void levelBackground (int level) {
        switch (level){
            case 1:
                deathBackground = new Image(ServiceLocator.getResourceService()
                        .getAsset("images/DeathScreens/lvl_1.png", Texture.class));
            case 2:
                deathBackground = new Image(ServiceLocator.getResourceService()
                        .getAsset("images/DeathScreens/lvl_2.png", Texture.class));
            default:
                deathBackground = new Image(ServiceLocator.getResourceService()
                        .getAsset("images/DeathScreens/lvl_1.png", Texture.class));
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
        deathBackground.remove();
        table.clear();
        super.dispose();
    }
}
