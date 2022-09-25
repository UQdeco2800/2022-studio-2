package com.deco2800.game.components.CombatItemsComponents;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.player.*;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.configs.CombatItemsConfig.MeleeConfig;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.entities.factories.WeaponFactory;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(GameExtension.class)
public class WeaponPickupComponentTest {

/*    @Test
    void pickUp() {
        ForestGameArea fga = mock(ForestGameArea.class);
        ServiceLocator.registerGameArea(fga);

        Entity player = new Entity().addComponent(new InventoryComponent())
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER));
        when(fga.getPlayer()).thenReturn(player);

        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

//        Entity weapon = new Entity().addComponent(new PhysicsComponent().setBodyType(BodyDef.BodyType.StaticBody))
//                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
//                .addComponent(new WeaponPickupComponent(PhysicsLayer.PLAYER));
        
//        Added by Peter from team 2 just to get the test passed
        Entity weapon = WeaponFactory.createTestDagger()
                .addComponent(new PhysicsComponent().setBodyType(BodyDef.BodyType.StaticBody))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new WeaponPickupComponent(PhysicsLayer.PLAYER));
        weapon.setEntityType(EntityTypes.WEAPON);

        Fixture weaponFixture = weapon.getComponent(HitboxComponent.class).getFixture();
        Fixture playerFixture = fga.getPlayer().getComponent(HitboxComponent.class).getFixture();

        weapon.getComponent(WeaponPickupComponent.class).pickUpJunit(weaponFixture, playerFixture);
        assertTrue(fga.getPlayer().getComponent(InventoryComponent.class).getInventory().contains(weapon));
    }*/
}
