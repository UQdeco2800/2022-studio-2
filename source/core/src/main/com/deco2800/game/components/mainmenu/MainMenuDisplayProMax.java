package com.deco2800.game.components.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.GdxGame;
import com.deco2800.game.screens.MainMenuScreen;
import com.deco2800.game.screens.MainMenuTransitionScreen;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainMenuDisplayProMax extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(MainMenuDisplayProMax.class);
    private Table table;

    private Texture buttonTexture;
    private Image mainMenu;
    private Image transitionFrames;

    private TextureRegion buttonTextureRegion;
    private TextureRegionDrawable buttonDrawable;
    private ImageButton startButton;
    private ImageButton exitButton;
    private ImageButton settingsButton;
    public static int frame;
    private long lastFrameTime;


    private static final float Z_INDEX = 2f;
    private int fps = 15;
    private final long frameDuration =  (long)(800 / fps);

    private Group menuGroup = new Group();

    @Override
    public void create() {
        frame=1;
        super.create();
        addActors();
    }

    private void addActors() {
        table = new Table();
        table.setFillParent(true);
        mainMenu =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset(
                                        "images/Crafting-assets-sprint1/screens/atlantis sinks main menu.png",
                                        Texture.class));
        mainMenu.setWidth(Gdx.graphics.getWidth());
        mainMenu.setHeight(Gdx.graphics.getHeight());
        mainMenu.setPosition(0, 0);
        stage.addActor(mainMenu);

        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        startButton = new ImageButton(buttonDrawable);
        startButton.setSize(290, 170);
        startButton.setPosition(Gdx.graphics.getWidth() / 2 - 461,
                Gdx.graphics.getHeight() / 2 - 365);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                logger.debug("Game starting...");
                entity.getEvents().trigger("load");
            }
        });
//        menuGroup.addActor(startButton);
        stage.addActor(startButton);

        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        settingsButton = new ImageButton(buttonDrawable);
        settingsButton.setSize(290, 170);
        settingsButton.setPosition(Gdx.graphics.getWidth() / 2 - 146,
                Gdx.graphics.getHeight() / 2 - 365);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                logger.debug("Settings button clicked...");
                entity.getEvents().trigger("settings");
            }
        });
//        menuGroup.addActor(settingsButton);
        stage.addActor(settingsButton);

        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        exitButton = new ImageButton(buttonDrawable);
        exitButton.setSize(290, 170);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2 + 185,
                Gdx.graphics.getHeight() / 2 - 365);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                logger.debug("Exiting game...");
                entity.getEvents().trigger("exit");
            }
        });
//        menuGroup.addActor(exitButton);
        stage.addActor(exitButton);
        stage.addActor(table);

        if (frame < MainMenuScreen.frameCount) {
//            stage.clear();
            transitionFrames = new Image(ServiceLocator.getResourceService()
                    .getAsset(MainMenuScreen.transitionTextures[frame], Texture.class));

            transitionFrames.setWidth(Gdx.graphics.getWidth());
            transitionFrames.setHeight(Gdx.graphics.getHeight()/2);
            transitionFrames.setPosition(0, Gdx.graphics.getHeight()/2+15);
            frame++;
//            logger.info("frame = " + frame);
            stage.addActor(transitionFrames);
            lastFrameTime = System.currentTimeMillis();
        } else {
            frame=1;
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
        table.clear();
        super.dispose();
    }
}
