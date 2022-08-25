package com.deco2800.game.components.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import com.deco2800.game.entities.EntityService;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.services.ServiceLocator;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.entities.factories.PlayerFactory;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.input.InputService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderable;
import com.deco2800.game.services.ResourceService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GameExtension.class)
public class PlayerModifierTest {

    @Test
    void shouldHaveIdenticalMoveSpeed() {



        PlayerActions actions = new PlayerActions(2);
        PlayerModifier modifier = new PlayerModifier();

        modifier.createModifier("moveSpeed", 2, false, 20);

        
//        PlayerModifier playerModifier = newPlayer.getComponent(PlayerModifier.class);
//        assertEquals(playerActions.getMaxSpeed(), playerModifier.getModified("moveSpeed"));
    }


}
