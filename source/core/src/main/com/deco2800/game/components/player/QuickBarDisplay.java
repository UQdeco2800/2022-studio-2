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
import java.util.ArrayList;
import java.util.List;

public class QuickBarDisplay extends UIComponent {

    private Table quickBar;

    //Potion display table:
    private Table potionTable = new Table();
    private ArrayList<Image> potionImages = new ArrayList<>();


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
                .size(382, 175).pad(5);
        stage.addActor(quickBar);
        potionTable.bottom();
    }

    @Override
    public void draw(SpriteBatch batch) {
        //draw is handled by the stage
        updatePotionTable();
    }

    public void updatePotionTable() {
        if (potionsTex.size() == 0) {
            for (Image potion : potionImages) {
                potionTable.removeActor(potion);
            }
        }

        if (potionImages.size() != potionsTex.size()) {
            potionImages.clear();
            for (int i = 0; i < potionsTex.size(); i++) {
                potionImages.add(new Image(potionsTex.get(i)));
            }
        }
        for (int i = 0; i < potionImages.size(); i++) {
            Image potion = potionImages.get(i);
            potionTable.add(potion).size(64, 64);
            potion.setPosition(845 + i * 100, 60);
        }
        stage.addActor(potionTable);
    }

    @Override
    public void dispose() {
        super.dispose();
        quickBar.clear();
        potionTable.clear();
    }

}
