package com.deco2800.game.SkillsTree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkillsTreeDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(com.deco2800.game.SkillsTree.SkillsTreeDisplay.class);
    private static final float Z_INDEX = 2f;
    private static final int SKILL_ICON_BUTTON_SIZE = 50;
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
        imgTexture = new Texture(Gdx.files.internal("images/Skill_tree/skill_tree_2.png"));

        /**
         * Set images status
         */
        Img = new Image(imgTexture);
        Img.setSize(850f, 850f);
        Img.setPosition(250,0);

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
        addSkillTreeButton(810, 644, "dash");
        // Row 1
        addSkillTreeButton(698, 532, "dodge");
        addSkillTreeButton(774, 532, "teleport");
        addSkillTreeButton(920, 532, "invulnerability");
        addSkillTreeButton(844, 532, "invulnerability");
        // Row 2
        addSkillTreeButton(736, 436, "block");
        addSkillTreeButton(882, 436, "invulnerability");
        // Row 3
        addSkillTreeButton(736, 344, "invulnerability");
        addSkillTreeButton(920, 344, "invulnerability");
        addSkillTreeButton(844, 344, "invulnerability");
        // Row 4
        addSkillTreeButton(781, 210, "invulnerability");
        addSkillTreeButton(850, 210, "fireballUltimate");

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

    private void addSkillToTree(int skillNumber, String imageFileName) {
        switch (skillNumber) {
            case 1 -> addSkillTreeButton(803, 618, imageFileName);
            case 2 -> addSkillTreeButton(698, 532, imageFileName);
            case 3 -> addSkillTreeButton(774, 532, imageFileName);
            case 4 -> addSkillTreeButton(920, 532, imageFileName);
            case 5 -> addSkillTreeButton(844, 532, imageFileName);
            case 6 -> addSkillTreeButton(736, 436, imageFileName);
            case 7 -> addSkillTreeButton(882, 436, imageFileName);
            case 8 -> addSkillTreeButton(736, 344, imageFileName);
            case 9 -> addSkillTreeButton(920, 344, imageFileName);
            case 10 -> addSkillTreeButton(844, 344, imageFileName);
            case 11 -> addSkillTreeButton(781, 210, imageFileName);
            case 12 -> addSkillTreeButton(850, 210, imageFileName);
        }
    }

    private void addSkillTreeButton(int xCoord, int yCoord, String imageFilePath) {
        TextureRegionDrawable texture = new TextureRegionDrawable(ServiceLocator.getResourceService()
                .getAsset("images/Skills/" + imageFilePath + ".png", Texture.class));
        TextureRegionDrawable texture2 = new TextureRegionDrawable(ServiceLocator.getResourceService()
                .getAsset("images/Skills/block.png", Texture.class));
        ImageButton button = new ImageButton(texture);
        ImageButton.ImageButtonStyle style = button.getStyle();
        style.imageDown = texture2;
        style.imageUp = texture;
        button.getImage().setFillParent(true);

        button.setPosition(xCoord, yCoord);
        button.setSize(SKILL_ICON_BUTTON_SIZE, SKILL_ICON_BUTTON_SIZE);

        stage.addActor(button);
    }
}
