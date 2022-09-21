package com.deco2800.game.SkillsTree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkillsTreeDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(com.deco2800.game.SkillsTree.SkillsTreeDisplay.class);
    private static final float Z_INDEX = 2f;
    private Table table;

    @Override
    public void create() {
        super.create();
        addActors();
    }

    private void addActors() {
        table = new Table();
        table.top().right();
        table.setFillParent(true);
        Texture imgTexture;
        Image Img;

        /**
         * change your images here
         */
        imgTexture = new Texture(Gdx.files.internal("images/box_boy.png"));

        /**
         * Set images status
         */
        Img = new Image(imgTexture);
        Img.setSize(500f, 500f);
        Img.setPosition(400,200);

        TextButton mainMenuBtn = new TextButton("Exit", skin);

        /**
         * Currently clicking exit button will start a new game,
         * but I think someone was fixing this in GdxGame and I
         * have no idea about how to fix this  XD
         */
        mainMenuBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Exit button clicked");
                        entity.getEvents().trigger("exit");
                    }
                });

        table.add(mainMenuBtn).padTop(10f).padRight(10f);

        stage.addActor(table);
        stage.addActor(Img);
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
