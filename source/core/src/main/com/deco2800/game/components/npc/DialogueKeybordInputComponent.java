package com.deco2800.game.components.npc;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.utils.math.Vector2Utils;

public class DialogueKeybordInputComponent extends InputComponent {

//    public static Boolean dialogueState = false;

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.F) {
            DialogueDisplay.state = true;
            entity.getEvents().trigger("openDialogue");
            entity.getEvents().trigger("nextText");
            return true;
        } else if (keycode == Keys.G) {
            entity.getEvents().trigger("hideDialogue");
            DialogueDisplay.state = false;
            return true;
        }

        return false;
    }



}
