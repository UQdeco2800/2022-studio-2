package com.deco2800.game.SkillsTree;

import com.deco2800.game.GdxGame;
import com.deco2800.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkillsTreeActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(com.deco2800.game.SkillsTree.SkillsTreeActions.class);
    private GdxGame game;

    public SkillsTreeActions(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        entity.getEvents().addListener("exit", this::onExit);
    }

    private void onExit() {
        logger.info("Exiting to main game screen");
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
    }
}
