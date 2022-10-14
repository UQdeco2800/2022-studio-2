package com.deco2800.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.deco2800.game.rendering.RenderComponent;
import com.deco2800.game.rendering.Renderable;
import com.deco2800.game.services.ServiceLocator;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * A generic component for rendering onto the ui.
 */
public abstract class UIComponent extends RenderComponent implements Renderable {
    private static final int UI_LAYER = 2;
    protected static final Skin skin =
            new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
    protected static Stage stage;

    //TODO
    protected static ArrayList<Texture> potionsTex = new ArrayList<>();
    protected static ArrayList<Integer> potionsQty = new ArrayList<Integer>();
    @Override
    public void create() {
        super.create();
        stage = ServiceLocator.getRenderService().getStage();
    }

    @Override
    public int getLayer() {
        return UI_LAYER;
    }

    @Override
    public float getZIndex() {
        return 1f;
    }

    public static void RemovePotionTextureAt(int index) {
        if (index < potionsTex.size()) {
            potionsTex.remove(index);
            potionsQty.remove(index);
        }
    }

    protected void addItemTexture(Texture tex) {
        int index = 0;
        boolean addQty = false;
        for (Texture t : potionsTex){
            if(t == tex) {
                addQty = true;
                break;
            }
        }

        if(addQty) {
            System.out.println("Texture add qty");

            int currentQty = potionsQty.get(index);
            potionsQty.set(index, currentQty + 1);
        } else {
            System.out.println("Texture add new");

            potionsTex.add(tex);
            potionsQty.add(0);
        }
    }

}
