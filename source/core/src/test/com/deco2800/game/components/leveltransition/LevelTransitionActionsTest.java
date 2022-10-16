package com.deco2800.game.components.leveltransition;

import com.deco2800.game.GdxGame;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class LevelTransitionActionsTest {

    Entity ui;
    LevelTransitionActions levelTransitionActions;
    GdxGame game;

    @BeforeEach
    void init() {
        ui = new Entity();
        game = new GdxGame();
        levelTransitionActions = new LevelTransitionActions(game);

        ui.addComponent(levelTransitionActions);
        ui.create();
    }

    @Test
    void shouldHaveListener() {
        assertEquals(1, ui.getEvents().getNumberOfListeners("mapTransition"));
    }

    @Test
    void shouldHaveGame() {
        assertEquals(game, levelTransitionActions.getGame());
    }
}
