package com.deco2800.game.components.player;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.CombatItemsComponents.WeaponPickupComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Null;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.rendering.AnimationRenderComponent;

public class PlayerKeyPrompt extends TouchAttackComponent{
    private static Logger logger;
    private HitboxComponent hitboxComponent;
    private short targetLayer;

    public PlayerKeyPrompt(short targetLayer) {
        super(targetLayer);
    }
    @Override
    public void create() {
        logger = LoggerFactory.getLogger(PlayerKeyPrompt.class);
        entity.getEvents().addListener("collisionStart", this::keyPrompt);
    }

    public void keyPrompt(Fixture me, Fixture other){
//        hitboxComponent = entity.getComponent(HitboxComponent.class);
//        Fixture f = ServiceLocator.getGameArea().getPlayer().getComponent(HitboxComponent.class).getFixture();
//        if (other == f) {
//            Entity entityOfComponent = getEntity();
//            //insert into inventory
//            ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class).addItem(entityOfComponent);
//            logger.info("KEY PROMPT");
//        }

        if (((BodyUserData) other.getBody().getUserData()).entity.checkEntityType(EntityTypes.POTION)){
            logger.info("TEST KEY PROMPT POTION");
        }
//        else if (((BodyUserData) other.getBody().getUserData()).entity.checkEntityType(EntityTypes.ENEMY)) {
//            logger.info("TEST KEY PROMPT ENEMY");
//        }
        else if (((BodyUserData) other.getBody().getUserData()).entity.checkEntityType(EntityTypes.NPC)){
            logger.info("TEST KEY PROMPT NPC PRESS F TO OPEN DIALOGUE");
            logger.info("TEST KEY PROMPT NPC PRESS G TO CLOSE DIALOGUE");
        }

    }

}