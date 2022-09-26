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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.components.player.KeyboardPlayerInputComponent;
import com.deco2800.game.components.player.OpenPauseComponent;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainMenuDisplayProMax extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(MainMenuDisplayProMax.class);
    private Table table;

    private Texture buttonTexture;

    private TextureRegion buttonTextureRegion;
    private TextureRegionDrawable buttonDrawable;
    private ImageButton startButton;
    private ImageButton exitButton;
    private Image startButt;
    private Image exitButt;

    private static final float Z_INDEX = 2f;

    private Group menuGroup = new Group();

    @Override
    public void create() {
        super.create();
        addActors();
    }

    private void addActors() {
        table = new Table();
        table.setFillParent(true);
        Image mainMenu =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset(
                                        "images/Crafting-assets-sprint1/screens/atlantis sinks main menu.png",
                                        Texture.class));
        mainMenu.setWidth(Gdx.graphics.getWidth());
        mainMenu.setHeight(Gdx.graphics.getHeight());
        mainMenu.setPosition(0, 0);
        stage.addActor(mainMenu);

//        startButt = new Image(new Texture(Gdx.files.internal
//                ("images/Crafting-assets-sprint1/widgets/catalogue_button.png")));

        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        startButton = new ImageButton(buttonDrawable);
        startButton.setSize(290, 170);
        startButton.setPosition(Gdx.graphics.getWidth()/2 - 280,
                Gdx.graphics.getHeight()/2 - 510);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                entity.getEvents().trigger("start");
            }
        });
        menuGroup.addActor(startButton);
        stage.addActor(menuGroup);

//        exitButt = new Image(new Texture(Gdx.files.internal
//                ("images/Crafting-assets-sprint1/widgets/catalogue_button.png")));
        buttonTexture = new Texture(Gdx.files.internal
                ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        exitButton = new ImageButton(buttonDrawable);
        exitButton.setSize(290, 170);
        exitButton.setPosition(Gdx.graphics.getWidth()/2 + 40,
                Gdx.graphics.getHeight()/2 - 510);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                entity.getEvents().trigger("exit");
            }
        });
        menuGroup.addActor(exitButton);
        stage.addActor(menuGroup);

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
