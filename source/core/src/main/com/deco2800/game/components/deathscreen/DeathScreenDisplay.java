package com.deco2800.game.components.deathscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
    // The image of the deathBackground
    private Image deathBackground;
    // Global variable of game level
    private Integer level;
    // Texture of continue button
    private Texture continueBtnTexture;
    // Texture of exit button
    private Texture exitBtnTexture;
    // Texture Region of a given texture
    private TextureRegion buttonTextureRegion;
    // Texture drawable of a given texture region
    private TextureRegionDrawable buttonDrawable;
    // Image button of continue button
    private ImageButton continueButton;
    // Image button of exit button
    private ImageButton exitButton;


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


        //Play Again/ Continue Button
        if (getLevel() == 1) {
            continueBtnTexture = new Texture(Gdx.files
                    .internal("images/DeathScreens/widgets/play_again_lvl_1.png"));
        } else if (getLevel() == 2) {
            new Texture(Gdx.files
                    .internal("images/DeathScreens/widgets/play_again_lvl_2.png"));
        } else if (getLevel() == 3) {
            exitBtnTexture = new Texture(Gdx.files.internal("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        }
        buttonTextureRegion = new TextureRegion(continueBtnTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        continueButton = new ImageButton(buttonDrawable);



        // Play Again/ continue button listener - Restarts game
        continueButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("The continue/play again button was clicked");
                        entity.getEvents().trigger("continueGame");
                    }
                });

        //Exit Button texture set up
        if (getLevel() == 1) {
            exitBtnTexture = new Texture(Gdx.files
                    .internal("images/DeathScreens/widgets/main_menu_lvl_1.png"));

        } else if (getLevel() == 2) {
            exitBtnTexture = new Texture(Gdx.files
                    .internal("images/DeathScreens/widgets/main_menu_lvl_2.png"));
        } else if (getLevel() == 3) {
            exitBtnTexture = new Texture(Gdx.files.internal("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        }
        buttonTextureRegion = new TextureRegion(exitBtnTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        exitButton = new ImageButton(buttonDrawable);


        // Exit button listener, returns to main menu
        exitButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("The exit button was clicked");
                        entity.getEvents().trigger("exit");
                    }
                });

        logger.debug("Continue button and Exit button created");


        // Creates a stack of items, this allows you to overlay them and 'stack' them on top of eachother
        Stack background = new Stack();
        background.setFillParent(true);
        background.add(levelBackground(level));

        logger.debug("Stack created and level background image added");

        // Creates a table within the stack for the buttons
        Table btnTable = new Table();
        btnTable.setFillParent(true);
        btnTable.bottom().right();
        btnTable.add(continueButton).padTop(35).padLeft(200).left().height(100);
        btnTable.row();
        btnTable.add(exitButton).padBottom(35).padLeft(200).left().height(100);

        background.setPosition(table.getX() + 1600, table.getY() + 900);
        //background.set
        background.add(btnTable);

        // Adds the stack to the parent table
        table.add(background);
        table.right().bottom();
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
        switch (level) {
            case 1 -> {
                logger.info("setting level 1 deathscreen from DeathScreenDisplay");
                return deathBackground = new Image(ServiceLocator.getResourceService()
                        .getAsset("images/DeathScreens/lvl_1.png", Texture.class));
            }
            case 2 -> {
                logger.info("setting level 2 deathscreen from DeathScreenDisplay");
                return deathBackground = new Image(ServiceLocator.getResourceService()
                        .getAsset("images/DeathScreens/lvl_2.png", Texture.class));
            }
            case 3 -> {
                logger.info("setting win screen from DeathScreenDisplay");
                return deathBackground = new Image(ServiceLocator.getResourceService()
                        .getAsset("images/WinScreen/Win.png", Texture.class));
            }
        }
        return null;
    }

    /**
     * Gets current level of game as passed in to the contructor
     * @return level - the games current level
     */
    public int getLevel() {
        return level;
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
