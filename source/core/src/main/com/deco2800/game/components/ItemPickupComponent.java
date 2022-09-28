package com.deco2800.game.components;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Generic pickup component for all items. Adpoted from Team 4's weapon pickup logic that is now
 * deleted to merge with this component
 *
 */
public class ItemPickupComponent extends Component{

    private static Logger logger;
    private HitboxComponent hitboxComponent;
    private short targetLayer;

    /**
     * Creates a component which allows an entity to be picked up from the map and put in inventory
     * @param targetLayer The physics layer of the target's collider.
     */
    public ItemPickupComponent(short targetLayer) {
        this.targetLayer = targetLayer;
    }

    @Override
    public void create() {
        logger = LoggerFactory.getLogger(ItemPickupComponent.class);
        entity.getEvents().addListener("collisionStart", this::pickUp);
    }

    /**
     * Method called when collision starts between the item on the map that implements this component and the player.
     * Removes the item from the map and inserts the item into the inventory.
     * @param me Fixture of the item that implements this component.
     * @param other Fixture of the entity that is colliding with this item on the map.
     */
    private void pickUp(Fixture me, Fixture other) {
        hitboxComponent = entity.getComponent(HitboxComponent.class);
        Fixture f = ServiceLocator.getGameArea().getPlayer().getComponent(HitboxComponent.class).getFixture();

        if (other == f) {
            Entity entityOfComponent = getEntity();
            ForestGameArea.removeItemOnMap(entityOfComponent);

            //insert into inventory
            ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class).addItem(entityOfComponent);
            logger.info("Item picked up");
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
