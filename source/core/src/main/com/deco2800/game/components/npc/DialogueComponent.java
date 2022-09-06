package com.deco2800.game.components.npc;

import com.badlogic.gdx.math.GridPoint2;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.settingsmenu.SettingsMenuDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deco2800.game.components.npc.DialogueKeybordInputComponent;

/**
 * Dialogue component for interacting with the player
 * Player can press F key to open the dialogue
 */

public class DialogueComponent extends Component{
    private static final Logger logger = LoggerFactory.getLogger(SettingsMenuDisplay.class);
    // state true show the dialogue, false hide the dialogue
    private Boolean dialogueState;
    private short dialogueLayer;
    private DialogueKeybordInputComponent dialogueKeybordInputComponent;



    public void create() {
//        entity.getEvents().addListener("showDialogue", this::nearNPCStart);
        this.dialogueState = dialogueKeybordInputComponent.getDialogueState();
    }

//    public DialogueKeybordInputComponent cpy() {
//        return new
//    }
    public void update() {
        this.dialogueState = dialogueKeybordInputComponent.getDialogueState();

    }

    /**
     * dialogueState true if distence between NPC and player less 0.5
     *
     * @param player
     * @param NPC
     * @return
     */
//    public Boolean nearNPCStart(GridPoint2 player, GridPoint2 NPC) {
//        dialogueState = player.dst(NPC) < 0.5;
//        return dialogueState;
//    }


}
