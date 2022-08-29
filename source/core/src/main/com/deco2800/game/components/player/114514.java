package com.deco2800.game.components.player;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

public class 114514 extends UIComponent {

    private Table quickBar;
    private Image quickImage;

    /**
     * Creates reusable ui styles and adds actors to the stage.
     */
    @Override
    public void create() {
        super.create();
        addActors();
    }

    private void addActors() {

        quickBar = new Table();
        quickBar.bottom();
        quickBar.setFillParent(true);
        quickBar.padBottom(0f).padLeft(50f);

        quickImage = new Image(ServiceLocator.getResourceService()
                .getAsset("images/Inventory/quickbar.png", Texture.class));

        quickBar.add(quickImage).size(600,100).pad(5);
        stage.addActor(quickBar);

    }

    @Override
    public void draw(SpriteBatch batch) {
        //draw is handled by the stage
    }

    @Override
    public void dispose() {
        quickImage.clear();
        super.dispose();
    }

}
