package com.deco2800.game.components.player;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.HitboxComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.physics.BodyUserData;

public class PlayerKeyPrompt extends TouchAttackComponent{
    private static Logger logger = LoggerFactory.getLogger(PlayerKeyPrompt.class);
    private Entity keyPromptAnimator;


    public PlayerKeyPrompt(short targetLayer) {
        super(targetLayer);
    }

    public void setKeyPromptAnimator(Entity keyPromptAnimator) {
        this.keyPromptAnimator = keyPromptAnimator;
    }
    @Override
    public void create() {

        entity.getEvents().addListener("collisionStart", this::keyPrompt);
        entity.getEvents().addListener("collisionEnd", this::playerCollisionEnd);
    }

    /**
     * This function is used for guiding the user to press the relevant key to interact with the entity
     * @param me
     * @param other
     */
    public void keyPrompt(Fixture me, Fixture other){

        if (((BodyUserData) other.getBody().getUserData()).entity.checkEntityType(EntityTypes.POTION)){
            logger.info("TEST KEY PROMPT POTION");
        }
        else if (((BodyUserData) other.getBody().getUserData()).entity.checkEntityType(EntityTypes.NPC)){
            logger.info("TEST KEY PROMPT NPC PRESS F TO OPEN DIALOGUE");
            logger.info("TEST KEY PROMPT NPC PRESS G TO CLOSE DIALOGUE");
            keyPromptAnimator.getEvents().trigger("KeyNpcAnimation");
        }


        else if (((BodyUserData) other.getBody().getUserData()).entity.checkEntityType(EntityTypes.CRAFTINGTABLE)){
            keyPromptAnimator.getEvents().trigger("KeyQAnimation");
            logger.info("TEST KEY PROMPT CRAFTING PRESS Q TO CRAFT");



        }


    }

    public void playerCollisionEnd(Fixture me, Fixture other){
        keyPromptAnimator.getEvents().trigger("regularAnimation");
    }


}