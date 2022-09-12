package com.deco2800.game.components.deathscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.components.player.OpenPauseComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import com.sun.jdi.PathSearchingVirtualMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Displays a button to exit the Main Game screen to the Main Menu screen.
 */
public class DeathScreenDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(DeathScreenDisplay.class);
    private static final float Z_INDEX = 2f;
    private Table table;
    private static Boolean isOpen = false;
    private Image deathScreenOne;
    private Group deathGroup;

    /**
     *
     */
    @Override
    public void create() {
        entity.getEvents().addListener("DeathScreen", this::showDeathScreen);
        super.create();
        addActors();

    }

    /**
     *
     */
    private void addActors() {
        table = new Table();
        table.center();
        table.setFillParent(true);
        table.setHeight(200);
        table.setWidth(200);
        Image title =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/DeathScreens/lvl 1.PNG", Texture.class));
        table.add(title);

        stage.addActor(table);
    }

    /**
     * Displays death screen image in game, this is an event that is called
     */
    private void showDeathScreen() {
        deathScreenOne = new Image(new Texture(Gdx.files.internal
                ("images/DeathScreens/lvl 1.PNG")));
        deathScreenOne.setPosition(Gdx.graphics.getWidth()/2 - deathScreenOne.getWidth()/2,
                Gdx.graphics.getHeight()/2 - deathScreenOne.getHeight()/2);
        deathGroup.addActor(deathScreenOne);
        stage.addActor(deathGroup);
        stage.draw();
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
}
