package com.deco2800.game.components.CombatItemsComponents;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Null;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeaponPickupComponent extends Component {
    private static Logger logger;
    private HitboxComponent hitboxComponent;
    private short targetLayer;


    public WeaponPickupComponent(short targetLayer) {
        this.targetLayer = targetLayer;
    }

    @Override
    public void create() {
        logger = LoggerFactory.getLogger(WeaponPickupComponent.class);
        entity.getEvents().addListener("collisionStart", this::pickUp);
    }

    private void pickUp(Fixture me, Fixture other) {
        hitboxComponent = entity.getComponent(HitboxComponent.class);
        Fixture f = ServiceLocator.getGameArea().getPlayer().getComponent(HitboxComponent.class).getFixture();

        if (other == f) {
            Entity entityOfComponent = getEntity();
            ForestGameArea.removeWeaponOnMap(entityOfComponent);
            //insert into inventory
        }
    }
}


