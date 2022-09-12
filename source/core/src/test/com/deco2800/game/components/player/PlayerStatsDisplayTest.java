package com.deco2800.game.components.player;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.components.player.PlayerStatsDisplay;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.player.*;


import com.deco2800.game.entities.Entity;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
public class PlayerStatsDisplayTest{
    Entity entity;
    PlayerStatsDisplay display;

//    @BeforeEach
//
//    void init() {
//        entity = new Entity().addComponent(new PlayerStatsDisplay());
//        display = entity.getComponent(PlayerStatsDisplay.class);
//    }
//
//    @Test
//    void checkImageTest(){
//        int a =100;
//        assertTrue("1.png" == display.checkImage(a));
//    }


}
