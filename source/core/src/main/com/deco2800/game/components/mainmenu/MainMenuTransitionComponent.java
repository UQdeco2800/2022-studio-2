package com.deco2800.game.components.mainmenu;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.deco2800.game.components.Component;
import com.deco2800.game.input.InputComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainMenuTransitionComponent extends InputComponent {
    private static final Logger logger = LoggerFactory.getLogger(Component.class);

    public MainMenuTransitionComponent() {
        super(5);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.ENTER:
                logger.info("Exiting transition screen");
                entity.getEvents().trigger("start");
                return true;
            default:
                return false;
        }
    }

}
