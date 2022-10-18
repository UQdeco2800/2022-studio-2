package com.deco2800.game.components.combatitemscomponents;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.UndergroundGameArea;
import com.deco2800.game.components.ItemPickupComponent;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuraPickupComponent extends ItemPickupComponent {
    private static Logger logger;

    /**
     * Creates a component which allows an aura entity to be picked up from the map
     * @param targetLayer The physics layer of the target's collider.
     */
    public AuraPickupComponent(short targetLayer) {
        super(targetLayer);
    }

    @Override
    public void create() {
        logger = LoggerFactory.getLogger(AuraPickupComponent.class);
        entity.getEvents().addListener("collisionStart", this::pickUpAura);
    }

    /**
     * Method called when collision starts between the aura on the map that implements this component and the player.
     * Removes the aura from the map
     *
     * @param me    Fixture of the aura that implements this component.
     * @param other Fixture of the entity that is colliding with this aura on the map.
     */
    private void pickUpAura(Fixture me, Fixture other) {
        Fixture f = ServiceLocator.getGameArea().getPlayer().getComponent(HitboxComponent.class).getFixture();
        //aura is only picked up if weapon equipped
        if (other == f) {
            Entity weapon;
            //if there is weapon equipped, there are no current buffs and weapon is not golden plunger
            if ((weapon = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class).getEquipable(0)) != null
            && ServiceLocator.getGameArea().getPlayer().getComponent(WeaponAuraManager.class).auraApplied == null
            && (weapon.getComponent(PhysicalWeaponStatsComponent.class).getDescription().equals("goldenPlungerBow") != true)) {
                Entity entityOfComponent = getEntity();
                Sound pickupSound = ServiceLocator.getResourceService().getAsset("sounds/buffPickupSound.wav", Sound.class);
                pickupSound.play();
                if (ServiceLocator.getGameArea() instanceof ForestGameArea) {
                    ForestGameArea.removeAuraOnMap(entityOfComponent);
                }
                else if (ServiceLocator.getGameArea() instanceof UndergroundGameArea){
                    UndergroundGameArea.removeAuraOnMap(entityOfComponent);
                }
                logger.info("Aura picked up");
                ServiceLocator.getGameArea().getPlayer().getComponent(WeaponAuraManager.class)
                        .applyAura(entity, weapon);
            }
        }
    }
}



