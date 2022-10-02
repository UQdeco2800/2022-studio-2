package com.deco2800.game.components.CombatItemsComponents;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.ItemPickupComponent;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//this is useless
public class AuraPickupComponent extends ItemPickupComponent {
    private static Logger logger;
    private HitboxComponent hitboxComponent;
    private short targetLayer;


    /**
     * Creates a component which allows a (weapon) entity to be picked up from the map and put in inventory
     *
     * @param targetLayer The physics layer of the target's collider.
     */
    public AuraPickupComponent(short targetLayer) {
        super(targetLayer);
    }


    @Override
    public void create() {
        logger = LoggerFactory.getLogger(AuraPickupComponent.class);
        entity.getEvents().addListener("collisionStart", this::pickUp);
    }

    /**
     * Method called when collision starts between the weapon on the map that implements this component and the player.
     * Removes the weapon from the map and inserts the weapon into the inventory.
     *
     * @param me    Fixture of the weapon that implements this component.
     * @param other Fixture of the entity that is colliding with this weapon on the map.
     */
    private void pickUp(Fixture me, Fixture other) {
        hitboxComponent = entity.getComponent(HitboxComponent.class);
        Fixture f = ServiceLocator.getGameArea().getPlayer().getComponent(HitboxComponent.class).getFixture();
        //aura is only picked up if weapon equipped
        if (other == f) {
            Entity weapon;
            if ((weapon = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class).getEquipable(0)) != null
            && ServiceLocator.getGameArea().getPlayer().getComponent(WeaponAuraManager.class).auraApplied == null) { //if there is weapon equipped and there are no current buffs
                Entity entityOfComponent = getEntity();
                Sound pickupSound = ServiceLocator.getResourceService().getAsset("sounds/buffPickupSound.wav", Sound.class);
                pickupSound.play();
                ForestGameArea.removeAuraOnMap(entityOfComponent);
                logger.info("Aura picked up");
                ServiceLocator.getGameArea().getPlayer().getComponent(WeaponAuraManager.class)
                        .applyAura(entity, weapon);
            }
        }
    }
}



