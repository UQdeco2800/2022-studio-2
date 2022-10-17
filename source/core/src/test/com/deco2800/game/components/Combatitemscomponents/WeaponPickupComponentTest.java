package com.deco2800.game.components.Combatitemscomponents;

import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.mock;


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
