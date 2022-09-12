package com.deco2800.game.components.npc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.areas.ForestGameArea;

import static com.deco2800.game.areas.ForestGameArea.*;


/**
 * Displays a dialog box that allows the Player to interact with NPCs - Team 7 all-mid-npc
 */
public class DialogueDisplay extends UIComponent {

    private static Image dialogueImage;

    private String dialogueText;

    public static Boolean state = false;


    @Override
    public void create() {
        super.create();
        openDialogue();
        entity.getEvents().addListener("openDialogue", this::openDialogue);
        entity.getEvents().addListener("hideDialogue", this::hideDialogue);
    }

    public void openDialogue() {
        Table dialogueTable = new Table();
        dialogueTable.bottom();
        dialogueTable.setFillParent(true);
        dialogueTable.padBottom(0f).padLeft(50f);

        dialogueImage = new Image(ServiceLocator.getResourceService()
                .getAsset("images/NPC/Dialogue/dialogues2.png", Texture.class));
        // need add text function here
        dialogueTable.add(dialogueImage).size(100,100).pad(0);

        if (state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(oneLegGirlPosition)) < 2) {
            stage.addActor(dialogueImage);
            state = false;
        } else if (state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(GuardPosition)) < 2) {
            stage.addActor(dialogueImage);
            state = false;
        } else if (state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(maleCitizenPosition)) < 2) {
            stage.addActor(dialogueImage);
            state = false;
        } else if (state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(childPosition)) < 2) {
            stage.addActor(dialogueImage);
            state = false;
        } else if (state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(HumanGuardPosition)) < 2) {
            stage.addActor(dialogueImage);
            state = false;
        } else if (state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(PlumberFriendPosition)) < 2) {
            stage.addActor(dialogueImage);
            state = false;
        } else if (state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(friendlycreaturePosition)) < 2) {
            stage.addActor(dialogueImage);
            state = false;
        }
    }

    //hide dialogue function
    public void hideDialogue() {
        if (!state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(oneLegGirlPosition)) < 2) {
            dialogueImage.remove();
            state = true;
        } else if (!state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(GuardPosition)) < 2) {
            dialogueImage.remove();
            state = true;
        } else if (!state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(maleCitizenPosition)) < 2) {
            dialogueImage.remove();
            state = true;
        } else if (!state && entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(childPosition)) < 2) {
            dialogueImage.remove();
            state = true;
        } else if (entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(oneLegGirlPosition)) > 2) {
            dialogueImage.remove();
            state = true;
        } else if (entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(GuardPosition)) > 2) {
            dialogueImage.remove();
            state = true;
        } else if (entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(maleCitizenPosition)) > 2) {
            dialogueImage.remove();
            state = true;
        } else if (entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(childPosition)) > 2) {
            dialogueImage.remove();
            state = true;
        }
        else if (entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(HumanGuardPosition)) > 2) {
            dialogueImage.remove();
            state = true;
        } else if (entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(PlumberFriendPosition)) > 2) {
            dialogueImage.remove();
            state = true;
        } else if (entity.getCenterPosition().dst(ForestGameArea.GridPointToVector(friendlycreaturePosition)) > 2) {
            dialogueImage.remove();
            state = true;
        }

    }
    public static void loadText() {

    }

    @Override
    public void draw(SpriteBatch batch) {

    }



    @Override
    public void dispose() {
        dialogueImage.clear();
        super.dispose();
    }
}
