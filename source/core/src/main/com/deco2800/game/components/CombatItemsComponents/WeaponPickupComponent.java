package com.deco2800.game.components.CombatItemsComponents;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//this is useless
public class WeaponPickupComponent extends Component {
    private static Logger logger;
    private HitboxComponent hitboxComponent;
    private short targetLayer;


    /**
     * Creates a component which allows a (weapon) entity to be picked up from the map and put in inventory
     * @param targetLayer The physics layer of the target's collider.
     */
    public WeaponPickupComponent(short targetLayer) {
        this.targetLayer = targetLayer;
    }

    @Override
    public void create() {
        logger = LoggerFactory.getLogger(WeaponPickupComponent.class);
        entity.getEvents().addListener("collisionStart", this::pickUp);
    }

    /**
     * Method called when collision starts between the weapon on the map that implements this component and the player.
     * Removes the weapon from the map and inserts the weapon into the inventory.
     * @param me Fixture of the weapon that implements this component.
     * @param other Fixture of the entity that is colliding with this weapon on the map.
     */
    private void pickUp(Fixture me, Fixture other) {
        hitboxComponent = entity.getComponent(HitboxComponent.class);
        Fixture f = ServiceLocator.getGameArea().getPlayer().getComponent(HitboxComponent.class).getFixture();

        if (other == f) {
            Entity entityOfComponent = getEntity();
            ForestGameArea.removeWeaponOnMap(entityOfComponent);

            //insert into inventory
            ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class).addItem(entityOfComponent);
        }
    }

    /**
     * Pickup function used purely for Junit testing only.
     * @param me Fixture of the weapon that implements this component.
     * @param other Fixture of the entity that is colliding with this weapon on the map.
     */
    public void pickUpJunit(Fixture me, Fixture other) {
        this.pickUp(me, other);
    }
}


