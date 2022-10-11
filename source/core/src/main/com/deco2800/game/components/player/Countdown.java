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

import java.util.ArrayList;
import java.util.HashMap;

public class Countdown extends UIComponent {
    //Ability activeAbility = Ability.NONE;
    long startTimeSkill0;
    long startTimeSkill1;
    long startTimeSkill2;
    long startTimeSkill3;

    private static final Logger logger = LoggerFactory.getLogger(Countdown.class);

    private int MAX_NUMBER = 5;
    private long COUNTDOWN_DURATION_MILLIS = 5000;
    private ArrayList<String> addedCountdownListeners = new ArrayList<>();

    HashMap<Integer, ArrayList<Image>> numberImages;

    private Table bar;

    //private enum Ability {
        //DASH, BLOCK, DODGE, INVULNERABLE, TELEPORT, NONE
    //}

    /**
     * Creates reusable ui styles and adds actors to the stage.
     */
    @Override
    public void create() {
        super.create();
        bar = new Table();
        bar.bottom().right();
        bar.setFillParent(true); //might need to change
        bar.padBottom(5f).padRight(25f);
        stage.addActor(bar);

        numberImages = new HashMap<>();
        initialiseImages(0, "images/Skills/blankSkillIcon.png");
        initialiseImages(1, "images/countdown/1.png");
        initialiseImages(2, "images/countdown/2.png");
        initialiseImages(3, "images/countdown/3.png");
        initialiseImages(4, "images/countdown/4.png");
        initialiseImages(5, "images/countdown/5.png");

        entity.getEvents().addListener("dashCountdown",
                () -> this.startCountdown(0));
    }

    private void initialiseImages(int imageNum, String filename) {
        ArrayList<Image> countdownImages = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            countdownImages.add(new Image(ServiceLocator.getResourceService()
                    .getAsset(filename, Texture.class)));
            numberImages.put(imageNum, countdownImages);
        }
    }

    public void setCountdownTrigger(int skillNum, String listenerName) {
        entity.getEvents().addListener(listenerName,
                () -> this.startCountdown(skillNum));

        addedCountdownListeners.add(listenerName);
    }

    public void clearCountdownListeners() {
        for (String countdownListener : addedCountdownListeners) {
            entity.getEvents().removeAllListeners(countdownListener);
        }
        addedCountdownListeners = new ArrayList<>();
    }

    /**
     * Starts the count-down
     * @param skillNum: the equipped skill num from left to right
     */
    private void startCountdown(int skillNum) {
        if (skillNum == 0) {
            startTimeSkill0 = TimeUtils.millis();
        } else if (skillNum == 1) {
            startTimeSkill1 = TimeUtils.millis();
        } else if (skillNum == 2) {
            startTimeSkill2 = TimeUtils.millis();
        } else if (skillNum == 3) {
            startTimeSkill3 = TimeUtils.millis();
        }
        this.update();
    }

    /**
     * Called every frame. Checks cool-down duration if any are active and updates the table accordingly
     */
    @Override
    public void update() {
        // For now just gives each ability 10 seconds
        //if (activeAbility == Ability.NONE)
        //    return;
        bar.clearChildren();
        long elapsedTime0 = TimeUtils.timeSinceMillis(startTimeSkill0);
        long elapsedTime1 = TimeUtils.timeSinceMillis(startTimeSkill1);
        long elapsedTime2 = TimeUtils.timeSinceMillis(startTimeSkill2);
        long elapsedTime3 = TimeUtils.timeSinceMillis(startTimeSkill3);
        int numberToDisplay0 = getNumToDisplay(elapsedTime0, 0);
        int numberToDisplay1 = getNumToDisplay(elapsedTime1, 1);
        int numberToDisplay2 = getNumToDisplay(elapsedTime2, 2);
        int numberToDisplay3 = getNumToDisplay(elapsedTime3, 3);

        if (true) { // temp disable flag until fixed
            alterTable(0, numberToDisplay0);
            alterTable(1, numberToDisplay1);
            alterTable(2, numberToDisplay2);
            alterTable(3, numberToDisplay3);
        }

    }

    private int getNumToDisplay(long elapsedTime, int skillNum) {
        int numberToDisplay = 0;
        if (elapsedTime < COUNTDOWN_DURATION_MILLIS) {
            numberToDisplay = ((int) (((COUNTDOWN_DURATION_MILLIS - elapsedTime) / 1000) + 1));
        }
        if (numberToDisplay > MAX_NUMBER) {
            numberToDisplay = MAX_NUMBER;
        }
        return numberToDisplay;
    }

    /**
     * Alters the table to display any given number above any given ability
     * @param skillNum: skillNum from left to right on equipped skills
     * @param number: number displayed
     */
    private void alterTable(int skillNum, int number) {
        // Should change one child, but for now resets entire table
        if (number > MAX_NUMBER)
            throw new IllegalArgumentException("number given to alterTable is too large");
        if (number < 0)
            throw new IllegalArgumentException("number given to alterTable is too small");
        bar.add(numberImages.get(number).get(skillNum)).size(96,96).pad(5);
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

