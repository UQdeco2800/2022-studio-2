package com.deco2800.game.components.deathscreen;

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
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * A ui component for displaying the Death Screen.
 */
public class DeathScreenDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(DeathScreenDisplay.class);
    private static final float Z_INDEX = 2f;
    private Table table;
    // The image of the deathBackground
    private Image deathBackground;
    // Global variable of game level
    private Integer level;
    //private List<String> deathBackgrounds = new List<String>();

    /**
     * Constructor of DeathScreenDisplay, default empty
     */
    public DeathScreenDisplay(){

    }

    /**
     * Constructor of DeathScreenDisplay, instantiates the varibles given
     * @param level is the level the game is on
     */
    public DeathScreenDisplay(int level) {
        this.level = level;
        //levelBackground(level);
    }

    @Override
    public void create() {
        super.create();
        addActors();
    }


    /**
     * Adds table to the screen, specifically adds the image of the deathscreen and buttons to the screen
     * after the player has died, this then gives the player the option to return to main menu to play again
     */
    private void addActors() {
        table = new Table();
        table.setFillParent(true);


        TextButton continueBtn = new TextButton("PLAY AGAIN", skin);
        TextButton exitBtn = new TextButton("EXIT", skin);
        logger.debug("Continue button and Exit button created");

        // Triggers an event when the button is pressed
        // Restarts game
        // TODO add win screen logic
        //if (!(deathBackground.getName() == deathBackgrounds.))
        continueBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("The Continue button was clicked");
                        entity.getEvents().trigger("continueGame");
                    }
                });

        // Returns to main menu
        exitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("The No button was clicked");
                        entity.getEvents().trigger("exit");
                    }
                });


        // TODO adjust layout of table to make it align with what we planed for death screen

        // Creates a stack of items, this allows you to overlay them and 'stack' them on top of eachother
        Stack background = new Stack();
        background.add(levelBackground(level));
        logger.debug("Stack created and level background image added");

        // Creates a table within the stack for the buttons
        Table btnTable = new Table();
        btnTable.bottom().right();
        btnTable.add(continueBtn).pad(35).right();
        btnTable.row();
        btnTable.add(exitBtn).pad(35).right();
        background.add(btnTable);

        // Adds the stack to the parent table
        table.add(background);
        stage.addActor(table);
        logger.debug("DeathScreenDisplay table has been added to the actor");
    }

    /**
     * Adjusts DeathScreens background image based on given level, it checks what value the level is and outputs the
     * appropriate level background image
     * @param level the games level as given in GdxGame
     * @return deathBackground if level is 1 it returns level 1 image, if level is 2 it reyurns level 2, else level 1
     */
    public Image levelBackground (int level) {
        switch (level){
            case 1:
                logger.info("setting level 1 deathscreen from DeathScreenDisplay");
                return deathBackground = new Image(ServiceLocator.getResourceService()
                        .getAsset("images/DeathScreens/lvl_1.png", Texture.class));

            case 2:
                logger.info("setting level 2 deathscreen from DeathScreenDisplay");
                return deathBackground = new Image(ServiceLocator.getResourceService()
                        .getAsset("images/DeathScreens/lvl_2.png", Texture.class));
                // TODO change this out to win screen.
            case 3:
                logger.info("setting level 3 deathScreen from DeathScreenDisplay");
                return deathBackground = new Image(ServiceLocator.getResourceService()
                        .getAsset("images/DeathScreens/lvl_23.png", Texture.class));
        }
        return deathBackground = new Image(ServiceLocator.getResourceService()
                .getAsset("images/DeathScreens/lvl_1.png", Texture.class));
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
