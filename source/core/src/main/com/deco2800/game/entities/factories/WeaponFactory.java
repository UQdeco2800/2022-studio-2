package com.deco2800.game.entities.factories;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.deco2800.game.CombatItems.Melee;
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

public class WeaponFactory {

    public static Entity createDagger() {

        Melee dagger = new Melee(10, 2, 1, 1);
        dagger.addComponent(new TextureRenderComponent("images/CombatWeapons-assets-sprint1/pixelart-sword_1.png"))
                .addComponent(new PhysicsComponent());


        dagger.getComponent(TextureRenderComponent.class).scaleEntity();
        return dagger;
    }


}
