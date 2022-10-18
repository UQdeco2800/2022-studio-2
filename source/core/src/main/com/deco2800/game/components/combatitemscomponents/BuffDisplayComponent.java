package com.deco2800.game.components.combatitemscomponents;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

public class BuffDisplayComponent extends UIComponent {
    Table table;
    private Image buff;
    boolean hasBuff;

    /**
     * Creates reusable ui styles and adds actors to the stage.
     */
    @Override
    public void create() {
        super.create();
        addActors();
    }

    /**
     *
     * @param batch Batch to render to.
     */
    @Override
    protected void draw(SpriteBatch batch) {
        // Draws the renderable
    }

    /**
     * Creates actors and positions them on the stage using a table.
     * @see Table for positioning options
     */
    private void addActors() {
        // Creates a new table
        table = new Table();
        // Places table at top of the screen
        table.top();
        table.setFillParent(true);
        // Sets padding of table
        table.padTop(60f);

        table.add(buff);
        stage.addActor(table);
    }

    /**
     * Set the buff to be displayed as the currently applied buff on the weapon.
     */
    public void setBuff(String auraDesc) {

        TextureAtlas atlas = ServiceLocator.getResourceService()
                .getAsset("images/CombatItems/animations/BuffAnimations/buff.atlas", TextureAtlas.class);
        Animation<TextureRegion> animation1 = new Animation<>(0.05f, atlas.findRegions(auraDesc));

        buff = new AnimatedImage(animation1);
        buff.scaleBy(1.2F);
        hasBuff = true;
        table.add(buff);
    }

    /**
     * Clears the buff from the display
     */
    public void clearBuff() {
        buff = null;
        hasBuff = false;
        table.clear();
    }


}
