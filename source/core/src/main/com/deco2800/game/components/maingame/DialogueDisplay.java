package com.deco2800.game.components.maingame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.components.mainmenu.MainMenuDisplay;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Displays a dialog box that allows the Player to interact with NPCs - Team 7 all-mid-npc
 */
public class DialogueDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(DialogueDisplay.class);
    private static final float Z_INDEX = 2f;
    private Table table;

    private static final String[] dialogueTextures = {
            "images/NPC/Dialogue/dialogues2.png"
    };

    @Override
    public void create() {
        super.create();
        displayDialogue();
    }

    private void displayDialogue() {
        table = new Table();
        table.setFillParent(true);



//        Image dialogue = new Image(ServiceLocator);


        TextButton Dialogue = new TextButton("Dialogue", skin);

//        table.add(dialogue).padBottom(2f);

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
