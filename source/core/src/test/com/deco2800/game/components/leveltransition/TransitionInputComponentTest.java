package com.deco2800.game.components.leveltransition;

import com.badlogic.gdx.Input;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;

import com.deco2800.game.input.InputService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
public class TransitionInputComponentTest {

    Entity ui;
    TransitionInputComponent transitionInputComponent;

    @BeforeEach
    void init() {
        // Allocate an input component
        ServiceLocator.registerInputService(new InputService());

        ui = new Entity();
        transitionInputComponent = new TransitionInputComponent();

        ui.addComponent(transitionInputComponent);
        ui.create();
    }

    @Test
    void shouldCreate() {
        assertEquals(5, transitionInputComponent.getPriority());
    }

    @Test
    void shouldTriggerKeyDown() {
        assertTrue(transitionInputComponent.keyDown(Input.Keys.ENTER));
    }

    @Test
    void shouldNotTriggerKeyDown() {
        assertFalse(transitionInputComponent.keyDown(Input.Keys.A));
    }
}
