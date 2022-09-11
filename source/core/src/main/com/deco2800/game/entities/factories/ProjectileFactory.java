package com.deco2800.game.entities.factories;


import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.tasks.ShootTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

public class ProjectileFactory {

    /**
     * Creates base entity of projectile
     * @return the base of projectile
     */
    public static Entity createBaseProjectile() {
        AITaskComponent aiComponent = new AITaskComponent();
        Entity projectile = new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(new PhysicsMovementComponent())
                .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
                .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 1.5f)) //7.5
                .addComponent(aiComponent);
        PhysicsUtils.setScaledCollider(projectile, 0.1f, 0.1f);

        projectile.setEntityType(EntityTypes.PROJECTILE);
        return projectile;

    }

    public static Entity createPoopsSludge(Entity target) {
        Entity poops = createBaseProjectile();
        poops.addComponent(new CombatStatsComponent(1, 10, 0, 0))
                .addComponent(new TextureRenderComponent("images/Enemies/poopSludge.png"))
                .getComponent(TextureRenderComponent.class).scaleEntity();
        poops.setScale(2f, 2f);
        poops.getComponent(AITaskComponent.class)
                .addTask(new ShootTask(target, 10, 10f));
        poops.getComponent(PhysicsMovementComponent.class);
        poops.setEntityType(EntityTypes.ENEMY);
        return poops;
    }

    public static Entity createDiscus(Entity target) {
        Entity discus = createBaseProjectile();
        discus.addComponent(new CombatStatsComponent(1, 10, 0, 0))
                .addComponent(new TextureRenderComponent("images/Enemies/discus.png"))
                .getComponent(TextureRenderComponent.class).scaleEntity();
        discus.setScale(2f, 2f);
        discus.getComponent(AITaskComponent.class)
                .addTask(new ShootTask(target, 10, 10f));
        discus.getComponent(PhysicsMovementComponent.class);
        discus.setEntityType(EntityTypes.ENEMY);
        return discus;
    }
}