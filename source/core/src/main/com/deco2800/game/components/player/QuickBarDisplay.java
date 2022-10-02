package com.deco2800.game.components.player;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

public class QuickBarDisplay extends UIComponent {

    private Table quickBar;
    private Image quickImage;
    private Image image1;
    private Image image2;
    private Image image3;
    private Image image4;
    private Image image5;

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
                .getAsset("images/Inventory/quickbar_sprint3.png", Texture.class));
        image1 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/Skills/dash.png", Texture.class));
        image2 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/Skills/block.png", Texture.class));
        image3 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/Skills/dodge.png", Texture.class));
        image4 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/Skills/invulnerability.png", Texture.class));
        image5 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/Skills/teleport.png", Texture.class));


        quickBar.add(image1).size(100,100).pad(5);
        quickBar.add(image2).size(100,100).pad(5);
        quickBar.add(image3).size(100,100).pad(5);
        quickBar.add(quickImage).size(382,175).pad(5);
        quickBar.add(image4).size(100,100).pad(5);
        quickBar.add(image5).size(100,100).pad(5);
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
