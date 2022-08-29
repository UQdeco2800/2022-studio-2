package com.deco2800.game.entities.factories;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.components.player.PlayerStatsDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import org.junit.Test;


public class PotionFactory {


    public static Entity createPotion() {
        Entity potion = new Entity()
                .addComponent(new TextureRenderComponent("images/Potions/defence_potion.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent().setLayer(PhysicsLayer.PLAYER));

        potion.getComponent(PhysicsComponent.class).setBodyType(BodyDef.BodyType.StaticBody);
        potion.getComponent(TextureRenderComponent.class).scaleEntity();
        potion.scaleHeight(1.5f);
        PhysicsUtils.setScaledCollider(potion, 0.5f, 0.2f);
        return potion;

        //potion.create();
        //ServiceLocator.getEntityService().register(potion);
        //ServiceLocator.getResourceService().loadTextures(potionPictures);

    }


    private static final String[] potionPictures = {
            "images/Potions/defence_potion.png"
    };
}


