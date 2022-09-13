package com.deco2800.game.components;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.CombatItemsComponents.WeaponPickupComponent;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Generic pickup component for all items. Adpoted from Team 4's weapon pickup logic
 *
 */
public class ItemPickupComponent extends Component{

    private static Logger logger;
    private HitboxComponent hitboxComponent;
    private short targetLayer;

    public ItemPickupComponent(short targetLayer) {
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
            ForestGameArea.removeItemOnMap(entityOfComponent);

            //insert into inventory
            ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class).addItem(entityOfComponent);
            logger.info("Item picked up");
        }
    }
}
