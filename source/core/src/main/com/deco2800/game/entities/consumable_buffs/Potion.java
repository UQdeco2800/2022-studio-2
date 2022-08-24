package com.deco2800.game.entities.consumable_buffs;

import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;

public class potion extends ConsumableBuff {

    /**
     * Creates a potion
     * @return
     */
    public static Entity spawnPotion() implements Renderable {
        Entity potion = new Entity()
                .addComponent(new TextureRenderComponent("images/Potions/defence potion.png"))
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent());
        //potion.create();
        ServiceLocator.getEntityService().register(potion);
        ServiceLocator.getResourceService().loadTextures(potionPictures);

    }

    //.draw() ??

    private static final String[] potionPictures = {
            "images/Potions/defence potion.png"
    };

    //public void create() {
    //    ServiceLocator.getResourceService().loadTextures(potionPictures);
    //}

    //public static void defense {
    //    .createModifier("movespeed", 0.25, true, 60000);
    //}
}


