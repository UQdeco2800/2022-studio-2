package com.deco2800.game.components.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Countdown extends UIComponent {
    Ability activeAbility = Ability.NONE;
    long startTime;
    private static final Logger logger = LoggerFactory.getLogger(Countdown.class);

    private int MAX_NUMBER = 5;
    private long COUNTDOWN_DURATION_MILLIS = 10000;
    private Entity playerEntity;

    HashMap<Integer, Image> numberImages;

    private Table bar;

    private enum Ability {
        DASH, BLOCK, DODGE, INVULNERABLE, TELEPORT, NONE
    }

    /**
     * Creates reusable ui styles and adds actors to the stage.
     */
    @Override
    public void create() {
        super.create();
        bar = new Table();
        bar.bottom().right();
        bar.setFillParent(true); //might need to change
        bar.padBottom(0f);
        stage.addActor(bar);

        numberImages = new HashMap<Integer, Image>();
        numberImages.put(1, new Image(ServiceLocator.getResourceService()
                .getAsset("images/countdown/1.png", Texture.class)));
        numberImages.put(2, new Image(ServiceLocator.getResourceService()
                .getAsset("images/countdown/2.png", Texture.class)));
        numberImages.put(3, new Image(ServiceLocator.getResourceService()
                .getAsset("images/countdown/3.png", Texture.class)));
        numberImages.put(4, new Image(ServiceLocator.getResourceService()
                .getAsset("images/countdown/4.png", Texture.class)));
        numberImages.put(5, new Image(ServiceLocator.getResourceService()
                .getAsset("images/countdown/5.png", Texture.class)));

        entity.getEvents().addListener("teleportAnimation",
                () -> this.startCountdown(Ability.TELEPORT));
        entity.getEvents().addListener("blockAnimation",
                () -> this.startCountdown(Ability.BLOCK));
        entity.getEvents().addListener("dodgeAnimation",
                () -> this.startCountdown(Ability.DODGE));
        entity.getEvents().addListener("attackSpeedAnimation",
                () -> this.startCountdown(Ability.BLOCK));
        entity.getEvents().addListener("dashAnimation",
                () -> this.startCountdown(Ability.DASH));
    }

    /**
     * Starts the count-down
     * @param ability: the ability that is in the cool-down phase
     */
    private void startCountdown(Ability ability) {
        startTime = TimeUtils.millis();
        activeAbility = ability;
        this.update();
    }

    /**
     * Called every frame. Checks cool-down duration if any are active and updates the table accordingly
     */
    @Override
    public void update() {
        // For now just gives each ability 10 seconds
        if (activeAbility == Ability.NONE)
            return;
        long elapsedTime = TimeUtils.timeSinceMillis(startTime);
        int numberToDisplay;
        if (elapsedTime > COUNTDOWN_DURATION_MILLIS) {
            activeAbility = Ability.NONE;
            numberToDisplay = 2;
        } else {
            numberToDisplay = ((int) ((COUNTDOWN_DURATION_MILLIS - elapsedTime) / 1000)) + 1;
        }
        if (true) { // temp disable flag until fixed
            alterTable(activeAbility, 2);//numberToDisplay);
        }

    }

    /**
     * Alters the table to display any given number above any given ability
     * @param ability: ability number is displayed above
     * @param number: number displayed
     */
    private void alterTable(Ability ability, int number) {
        // Should change one child, but for now resets entire table
        if (number > MAX_NUMBER)
            throw new IllegalArgumentException("number given to alterTable is too large");
        if (number < 1)
            throw new IllegalArgumentException("number given to alterTable is too small");
        bar.clearChildren();
        if (ability == Ability.DASH)
            bar.add(numberImages.get(number)).size(64,64).pad(0);
        else
            bar.add().size(64,64).pad(0);
        if (ability == Ability.BLOCK)
            bar.add(numberImages.get(number)).size(64,64).pad(0);
        else
            bar.add().size(64,64).pad(0);
        if (ability == Ability.DODGE)
            bar.add(numberImages.get(number)).size(100,100).pad(0);
        else
            bar.add().size(64,64).pad(0);
        //bar.add().size(382,175).pad(5);
        if (ability == Ability.INVULNERABLE)
            bar.add(numberImages.get(number)).size(64,64).pad(0);
        else
            bar.add().size(64,64).pad(0);
        if (ability == Ability.TELEPORT)
            bar.add(numberImages.get(number)).size(100,100).pad(0);
        else
            bar.add().size(64,64).pad(0);
    }

    @Override
    public void draw(SpriteBatch batch) {
//draw is handled by the stage
    }

    @Override
    public void dispose() {
        bar.clear();
        super.dispose();
    }
}

