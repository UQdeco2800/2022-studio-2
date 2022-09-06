package com.deco2800.game.services;

import com.deco2800.game.components.Component;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PauseGame extends Component {
    public Boolean gamePaused;
    private final PhysicsEngine physicsEngine;
    private static Logger logger;

    public PauseGame(){
        PhysicsService physicsService = new PhysicsService();
        physicsEngine = physicsService.getPhysics();
        gamePaused = false;
    }

//    public void create() {
//        logger = LoggerFactory.getLogger(PauseGame.class);
//        entity.getEvents().addListener("game_paused", this::gameLoop);
//    }

    public void gameLoop() {
        if (!(gamePaused)) {
            physicsEngine.update();
        }
    }

    public void setGamePaused() {
        gamePaused = false;
    }
}
