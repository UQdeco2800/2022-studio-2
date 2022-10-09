package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerSkillScreenOverlay extends UIComponent {

    private Table fullscreenOverlay;
    private Image fullscreenDisplay;

    private static final Logger logger = LoggerFactory.getLogger(PlayerSkillScreenOverlay.class);


    /**
     * Creates reusable ui styles and adds actors to the stage.
     */
    @Override
    public void create() {
        super.create();

        entity.getEvents().addListener("skillScreenOverlayFlash", this::setFlashOverlay);
    }

    private void addActors() {

        fullscreenOverlay = new Table();
        fullscreenOverlay.bottom();
        fullscreenOverlay.setFillParent(true);

        fullscreenOverlay.add(fullscreenDisplay).size(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage.addActor(fullscreenOverlay);

    }

    @Override
    public void draw(SpriteBatch batch) {
        //draw is handled by the stage
    }

    @Override
    public void dispose() {
        if(fullscreenDisplay != null) {
            fullscreenDisplay.remove();
        }
        fullscreenDisplay = null;
        super.dispose();
    }

    public void setFlashOverlay(boolean set) {
        if (fullscreenDisplay != null) {
            dispose();
        }
        if (set) {
            fullscreenDisplay = new Image(ServiceLocator.getResourceService()
                    .getAsset("images/Skills/white-flash.png", Texture.class));
            addActors();
        }
    }

}
