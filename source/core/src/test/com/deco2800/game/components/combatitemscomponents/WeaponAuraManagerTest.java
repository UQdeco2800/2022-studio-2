package com.deco2800.game.components.combatitemscomponents;


import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.player.PlayerTouchAttackComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.AuraFactory;
import com.deco2800.game.entities.factories.WeaponFactory;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(GameExtension.class)
public class WeaponAuraManagerTest {

    public Entity aura1;
    public Entity aura2;
    public Entity weapon;
    WeaponAuraManager manager;

    Entity player;

    @BeforeEach
    public void setUp() throws Exception {

        ForestGameArea fga = mock(ForestGameArea.class);
        ServiceLocator.registerGameArea(fga);

        ResourceService resourceService = mock(ResourceService.class);
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        aura1 = AuraFactory.createTestAura();
        aura2 = AuraFactory.createTestAura();
        weapon = WeaponFactory.createTestDagger();
        player = createPlayer();
        manager = player.getComponent(WeaponAuraManager.class);

    }

    @Test
    void setCombatAnimatorTest() {
        Entity combatAnimator = new Entity();
        manager.setCombatAnimator(combatAnimator);
    }

    @Test
    void setAuraTest() {
        manager.setAura(aura1);
        assertEquals(aura1, manager.getAura(), "Incorrect aura was returned.");
    }

    @Test
    void setWeaponStatsTest() {
        manager.setWeaponStats(weapon);
        WeaponStatsComponent stats = weapon.getComponent(PhysicalWeaponStatsComponent.class);
        assertEquals(stats, manager.getWeaponStats(), "Incorrect aura was returned.");
    }


    public Entity createPlayer() {
        Entity player = new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(mock(AnimationRenderComponent.class))
                .addComponent(new PlayerTouchAttackComponent(PhysicsLayer.PLAYER)) //team4
                .addComponent(mock(BuffDisplayComponent.class))
                .addComponent(new WeaponAuraManager());

        return player;

    }

}
