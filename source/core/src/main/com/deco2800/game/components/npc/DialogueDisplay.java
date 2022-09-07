package com.deco2800.game.components.npc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.components.npc.DialogueKeybordInputComponent;
import com.badlogic.gdx.Input.Keys;



/**
 * Displays a dialog box that allows the Player to interact with NPCs - Team 7 all-mid-npc
 */
public class DialogueDisplay extends UIComponent {

    private static Table dialogueTable;
    private static Image dialogueImage;

    private String dialogueText;

//    private static Boolean state = DiaDialogueKeybordInputComponentlogueKeybordInputComponent.dialogueState;
    public static Boolean state = false;

    @Override
    public void create() {
        super.create();
        openDialogue();
        entity.getEvents().addListener("openDialogue", this::openDialogue);
        entity.getEvents().addListener("hideDialogue", this::hideDialogue);
    }

    public void openDialogue() {
        dialogueTable = new Table();
        dialogueTable.bottom();
        dialogueTable.setFillParent(true);
        dialogueTable.padBottom(0f).padLeft(50f);

        dialogueImage = new Image(ServiceLocator.getResourceService()
                .getAsset("images/NPC/Dialogue/dialogues2.png", Texture.class));
        // need add text function here
        dialogueTable.add(dialogueImage).size(100,100).pad(0);

        if (state) {


            stage.addActor(dialogueImage);
            state = false;
        }



    }

    //hide dialogue function
    public void hideDialogue() {
        if (!state) {
            dialogueImage.remove();
            state = true;
        }

    }
    public static void loadText() {

    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }



    @Override
    public void dispose() {
        dialogueImage.clear();
        super.dispose();
    }
}
