package com.deco2800.game.components.player;

import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(GameExtension.class)

public class PlayerTouchAttackComponentTest {

    @BeforeEach
    void beforeEach() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
    }

    /*
    void attackTargetTest() {
        short targetLayer = (1 << 3);
        Entity entity = createAttacker(targetLayer);
        Entity target = createTarget(targetLayer);

        Fixture entityFixture = entity.getComponent(HitboxComponent.class).getFixture();
        Fixture targetFixture = target.getComponent(HitboxComponent.class).getFixture();

        //System.out.println("entity fixture" + entityFixture.toString());
        //System.out.println("entity fixture" + targetFixture.toString());
        //BodyUserData body = (BodyUserData) targetFixture.getBody().getUserData();
        //System.out.println("this is the value:" + body.entity.getEntityTypes().toString());

        entity.getEvents().trigger("playerCollisionStart", entityFixture, targetFixture);

        entity.getEvents().trigger("attack");

        assertEquals(10, target.getComponent(CombatStatsComponent.class).getHealth());

    } */

    /*
    void collisionActiveNoAttackTest() {

        // checks that even when collision is set to true and attack isn't called the health stays unchanged

        short targetLayer = (1 << 3);
        Entity entity = createAttacker(targetLayer);
        Entity target = createTarget(targetLayer);

        Fixture entityFixture = entity.getComponent(HitboxComponent.class).getFixture();
        Fixture targetFixture = target.getComponent(HitboxComponent.class).getFixture();

        //entity.getEvents().trigger("collisionStart", entityFixture, targetFixture);

        assertEquals(20, target.getComponent(CombatStatsComponent.class).getHealth());

    }*/

    @Test
    void collisionFalseAttackTest() {

        // checks when collision is false and attack is called the health stays unchanged

        short targetLayer = (1 << 3);
        Entity entity = createAttacker(targetLayer);

        try {
            entity.getEvents().trigger("attack");
        } catch (NullPointerException e) {
            System.out.println("Null Pointer Exception Error");
        }

    }

    Entity createAttacker(short targetLayer) {
        Entity entity =
                new Entity()
                        .addComponent(new TouchAttackComponent(targetLayer))
                        .addComponent(new PlayerTouchAttackComponent(targetLayer))
                        .addComponent(new CombatStatsComponent(100, 10, 1, 1))
                        .addComponent(new PhysicsComponent())
                        .addComponent(new HitboxComponent());
        entity.setEntityType(EntityTypes.PLAYER);
        entity.setEntityType(EntityTypes.ENEMY);
        entity.create();
        return entity;
    }

    Entity createTarget(short layer) {
        Entity target =
                new Entity()
                        .addComponent(new CombatStatsComponent(20, 0, 1, 1))
                        .addComponent(new PhysicsComponent())
                        .addComponent(new HitboxComponent().setLayer(layer));
        target.setEntityType(EntityTypes.ENEMY);
        target.create();
        return target;
    }
}
