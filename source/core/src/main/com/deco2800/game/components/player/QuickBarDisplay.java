package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;


import java.util.List;

public class QuickBarDisplay extends UIComponent {

    private static Group potions;

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
    private static void addActors() {
        potions = new Group();
        Image quickBarImage = new Image(new Texture("images/Inventory/quickbar_sprint3.png"));
        quickBarImage.setSize(382,175);
        quickBarImage.setPosition((float)Gdx.graphics.getWidth() / 2 - quickBarImage.getWidth() / 2, 0);
        stage.addActor(quickBarImage);
        stage.addActor(potions);
    }

    /**
     * Draw the quick bar by default UI behavior
     * @param batch Batch to render to.
     */
    @Override
    public void draw(SpriteBatch batch) {
    }

    public static void updatePotionTable() {

        potions.clear();
        InventoryComponent inventory = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
        List<Entity> quickBarItems = inventory.getQuickBarItems();
        for (int i = 0; i < quickBarItems.size(); ++i) {
            Texture itemTexture = new Texture(quickBarItems.get(i).getComponent(TextureRenderComponent.class).getTexturePath());
            Image potion = new Image(itemTexture);
            potion.setPosition(822 + i * 106, 60);
            potion.setSize(64, 64);
            potions.addActor(potion);
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        potions.clear();
    }

}
