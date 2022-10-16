package com.deco2800.game.components.CombatItemsComponents;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.AuraFactory;
import com.deco2800.game.entities.factories.WeaponFactory;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(GameExtension.class)
public class AuraPickupComponentTest {

    Entity player;
    Entity aura;
    Entity weapon;
    ForestGameArea fga;

    @BeforeEach
    public void setUp() throws Exception {

        ForestGameArea fga = mock(ForestGameArea.class);
        ServiceLocator.registerGameArea(fga);

        player = createPlayer();
        when(fga.getPlayer()).thenReturn(player);

        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        weapon = WeaponFactory.createTestDagger();

        aura = AuraFactory.createTestAura();

        player.getComponent(InventoryComponent.class).equipItem(weapon);

    }

    void pickUpAuraTest() {

        Fixture auraFixture = aura.getComponent(HitboxComponent.class).getFixture();
        Fixture playerFixture = fga.getPlayer().getComponent(HitboxComponent.class).getFixture();

        aura.getComponent(AuraPickupComponent.class).pickUpJunit(auraFixture, playerFixture);
        //assertTrue(fga.getPlayer().getComponent(InventoryComponent.class).getInventory().contains(weapon));

    }

    public Entity createPlayer() {
        Entity player = new Entity()
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new InventoryComponent())
                .addComponent(mock(BuffDisplayComponent.class))
                .addComponent(new WeaponAuraManager());

        return player;

    }

}
