package com.deco2800.game.components.CombatItemsComponents;

import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.player.PlayerTouchAttackComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.AuraFactory;
import com.deco2800.game.entities.factories.PlayerFactory;
import com.deco2800.game.entities.factories.WeaponFactory;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
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

    public Entity animator;

    @BeforeEach
    public void setUp() throws Exception {

        ForestGameArea fga = mock(ForestGameArea.class);
        ServiceLocator.registerGameArea(fga);

        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        //ResourceService resources = mock(ResourceService.class);
        //AnimationRenderComponent animator = mock(AnimationRenderComponent.class);
        //TextureAtlas atlas = mock(TextureAtlas.class);

        //when(ServiceLocator.getResourceService().getAsset("images/CombatItems/animations/combatItemsAnimation.atlas", TextureAtlas.class)).thenReturn(atlas);

        aura1 = AuraFactory.createTestAura();
        aura2 = AuraFactory.createTestAura();
        weapon = WeaponFactory.createTestDagger();
        player = createPlayer();

        manager = player.getComponent(WeaponAuraManager.class);

        String atlases[] = {"images/CombatItems/animations/combatItemsAnimation.atlas"};
        ServiceLocator.getResourceService().loadTextureAtlases(atlases);


        //Entity mockAnimator = mock(Entity.class);
        //when(PlayerFactory.createCombatAnimator(player)).thenReturn(mockAnimator);
        //manager.setCombatAnimator(mock);

    }

    void applyAuraTest() {

        PlayerFactory factory = mock(PlayerFactory.class);

        manager.applyAura(aura1, weapon);

        assertEquals(aura1, manager.getAura(), "Incorrect aura was returned.");

    }


    public Entity createPlayer() {
        Entity player = new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(new PlayerTouchAttackComponent(PhysicsLayer.PLAYER)) //team4
                .addComponent(mock(BuffDisplayComponent.class))
                .addComponent(new WeaponAuraManager());

        return player;

    }

}
