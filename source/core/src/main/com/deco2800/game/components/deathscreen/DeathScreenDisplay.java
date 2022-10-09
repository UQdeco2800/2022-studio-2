package com.deco2800.game.components.deathscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
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
    // The image of the deathBackground
    private Image deathBackground;
    // Global variable of game level
    private Integer level;

    /**
     * Constructor of DeathScreenDisplay, default empty
     */
    public DeathScreenDisplay(){ }

    /**
     * Constructor of DeathScreenDisplay, instantiates the varibles given
     * @param level is the level the game is on
     */
    public DeathScreenDisplay(int level) {
        this.level = level;

    }

    @Override
    public void create() {
        super.create();
        addActors();
    }
    
    /**
     * Sets 'background' to the appropriate image depedning on levelBackground, stages the image, and then
     * adds the buttons using setButtonDisplay.
     */
    private void addActors() {
        // Set's background image based on the level
        Image background = levelBackground(level);
        // Makes sure the image fills the entire screen
        background.setFillParent(true);
        // Stages the image
        stage.addActor(background);
        // stages the buttons
        setButtonDisplay();
        logger.debug("DeathScreenDisplay background image and buttons has been added to the actor");
    }

    /**
     * Sets up button display and adds them to the stage. This is done by setting up textures, assigning them to buttons
     * and assigning listeners to buttons.
     */
    public void setButtonDisplay(){
        Group buttons = new Group();
        //loads in transparent image for button texture, making button transparent
        Texture btnTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        // Texture Region of a given texture
        TextureRegion buttonTextureRegion = new TextureRegion(btnTexture);
        // Texture drawable of a given texture region
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        ImageButton playAgainBtn = new ImageButton(buttonDrawable);

        //playAgain
        if (getLevel() == 3) {
            playAgainBtn.setOrigin(playAgainBtn.getWidth()/3, playAgainBtn.getHeight()/3);
            playAgainBtn.setPosition(908, 370);
            playAgainBtn.setSize(88, 80);
        } else {
            playAgainBtn.setOrigin(playAgainBtn.getWidth()/3, playAgainBtn.getHeight()/3);
            playAgainBtn.setWidth(340);
            playAgainBtn.setHeight(270);
            playAgainBtn.setPosition(1565,0);
        }
        // play again/ restart level 1 listner
        playAgainBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("The play again button was clicked");
                        entity.getEvents().trigger("continueGame");
                    }
                });
        buttons.addActor(playAgainBtn);

        //Exit to main menu
        ImageButton exitBtn = new ImageButton(buttonDrawable);
        if (getLevel() == 3){
            playAgainBtn.setOrigin(playAgainBtn.getWidth()/3, playAgainBtn.getHeight()/3);
            exitBtn.setPosition(988, 290);
            exitBtn.setSize(88, 80);
        } else {
            exitBtn.setOrigin(playAgainBtn.getWidth()/3, playAgainBtn.getHeight()/3);
            exitBtn.setWidth(340);
            exitBtn.setHeight(270);
            exitBtn.setPosition(1167, 0);
        }
        // exit button/ return to main menu button listener
        exitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("The exit button was clicked");
                        entity.getEvents().trigger("exit");
                    }
                });
        buttons.addActor(exitBtn);
        stage.addActor(buttons);
        stage.draw();
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
                 deathBackground = new Image(ServiceLocator.getResourceService()
                        .getAsset("images/DeathScreens/lvl_1_w_buttons.png", Texture.class));
            }
            case 2 -> {
                logger.info("setting level 2 deathscreen from DeathScreenDisplay");
                deathBackground = new Image(ServiceLocator.getResourceService()
                        .getAsset("images/DeathScreens/lvl_2_w_buttons.png", Texture.class));
            }
            case 3 -> {
                logger.info("setting win screen from DeathScreenDisplay");
                deathBackground = new Image(ServiceLocator.getResourceService()
                        .getAsset("images/WinScreen/atlantis_sinks_no_island_win.png", Texture.class));
            }
            default -> deathBackground = new Image(ServiceLocator.getResourceService()
                        .getAsset("images/DeathScreens/lvl_1.png", Texture.class));
        }
        return deathBackground;
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
        super.dispose();
    }
}
