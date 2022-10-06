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
    }


    /**
     * This function visualizes potions in the item bar when equipped from the inventory.
     */
    private void addActors() {
        quickBar = new Table();
        quickBar.bottom();
        quickBar.setFillParent(true);
        quickBar.padBottom(0f).padLeft(50f);
        quickBar.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/Inventory/quickbar_sprint3.png", Texture.class)))
                .size(382,175).pad(5);
        stage.addActor(quickBar);
        potionTable.bottom();
    }

    @Override
    public void draw(SpriteBatch batch) {
        //draw is handled by the stage
        updatePotionTable();
    }

    public void updatePotionTable(){
        if(potionEQ == 1) {
            if(texCheck != potionTex){
                healthPotion = new Image(new Texture(Gdx.files.internal
                        (String.valueOf(potionTex))));
                texCheck = potionTex;
            }
            potionTable.add(healthPotion).size(64, 64);
            healthPotion.setPosition(726, 60);

            stage.addActor(potionTable);
        } else {
            //potionTable.removeActor(bombPotion);
            potionTable.removeActor(healthPotion);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        quickBar.clear();
        potionTable.clear();
    }

}
