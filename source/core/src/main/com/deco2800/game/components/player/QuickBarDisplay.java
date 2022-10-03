package com.deco2800.game.components.player;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class QuickBarDisplay extends UIComponent {

    private Table quickBar;
    private Image quickImage;
    private Image image1;
    private Image image2;
    private Image image3;
    private Image image4;
    private Image image5;

    //Potion display table:
    private Table potionTable = new Table();
    private Image healthPotion;
    private Texture texCheck = null;





    /**
     * Creates reusable ui styles and adds actors to the stage.
     */
    @Override
    public void create() {
        super.create();
        addActors();
        addPotionTable();
    }

    /**
     * A table for the potion graphics.
     */
    private void addPotionTable() {

        potionTable.bottom();

    }

    /**
     * This function visualizes potions in the item bar when equipped from the inventory.
     */

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
        //TODO work in progress below
        System.out.println(potionEQ);
        updatePotionTable();

    }

    private InventoryComponent getInventory() {
        Entity player = ServiceLocator.getGameArea().getPlayer();
        return player.getComponent(InventoryComponent.class);
    }

    private void display(){
        List<Entity> quickBar = getInventory().getQuickBarItems();
        //Random number you can change it later
        float xPosition = 100;
        float yPosition = 100;
        float padding = 50;
        for (int i = 0; i < quickBar.size(); ++i) {
            //0 will be the first potion, 1 be 2nd and so on.

            //Get the picture asset of each potion
            Entity potion = quickBar.get(i);
            Image potionTexture =  new Image(
                    new Texture(potion.getComponent(TextureRenderComponent.class).getTexturePath()));
            potionTable.add(potionTexture);
            //Each time the loop iterates the x postion will dynamically change
            potion.setPosition(xPosition + padding, yPosition);
        }

    }

    public void updatePotionTable(){
        if(potionEQ == 1) {
            if(texCheck != potionTex){
                healthPotion = new Image(new Texture(Gdx.files.internal
                        (String.valueOf(potionTex))));
                texCheck = potionTex;
            }
            potionTable.add(healthPotion).size(150, 150);
            healthPotion.setPosition(498, -45);

            stage.addActor(potionTable);
        } else {
            //potionTable.removeActor(bombPotion);
            potionTable.removeActor(healthPotion);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        quickImage.clear();

        potionTable.clear();
    }

}
