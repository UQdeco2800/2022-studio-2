package com.deco2800.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.components.player.PlayerSkillProjectileComponent;
import com.deco2800.game.components.tasks.ChaseTask;
import com.deco2800.game.components.tasks.ShootTask;
import com.deco2800.game.components.tasks.WanderTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.entities.factories.EntityTypes;
import java.util.*;

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
                //.addComponent(new ColliderComponent())
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
                .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 1.5f)) //7.5
                .addComponent(aiComponent);
        PhysicsUtils.setScaledCollider(projectile, 0.8f, 0.8f);
        projectile.setEntityType(EntityTypes.PROJECTILE);
        return projectile;

    }

    public static Entity createPoopsSludge(Entity target) {
        Entity poops = createBaseProjectile();
        poops.addComponent(new CombatStatsComponent(1, 10, 0, 0))
                .addComponent(new TextureRenderComponent("images/Enemies/poopSludge.png"))
                .getComponent(TextureRenderComponent.class).scaleEntity();
        poops.setScale(2f, 2f); //0.6
        poops.getComponent(AITaskComponent.class)
                .addTask(new ShootTask(target, 10, 10f));
        poops.getComponent(PhysicsMovementComponent.class);
        poops.setEntityType(EntityTypes.ENEMY);
        return poops;
    }

    /**
     * Creates a non-colliding projectile shooting in the walk direction of the player
     * which damages enemies.
     * @param player the player entity
     * @return the projectile entity
     */
    public static Entity createBasePlayerProjectile(Entity player) {
        PhysicsComponent physicsComponent = new PhysicsComponent();
        PlayerSkillProjectileComponent playerSkillProjectileComponent = new PlayerSkillProjectileComponent();

        Entity projectile = new Entity()
                .addComponent(new TextureRenderComponent("images/box_boy_leaf.png"))
                .addComponent(physicsComponent)
                .addComponent(new ColliderComponent().setLayer(PhysicsLayer.NONE))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                .addComponent(new TouchAttackComponent(PhysicsLayer.NPC, 1.5f))
                .addComponent(new CombatStatsComponent(100000, 100000, 0, 0))
                .addComponent(playerSkillProjectileComponent);

        PhysicsUtils.setScaledCollider(projectile, 1.0f, 1.0f);
        projectile.getComponent(TextureRenderComponent.class).scaleEntity();
        projectile.setEntityType(EntityTypes.PROJECTILE);

        PlayerActions playerActions = player.getComponent(PlayerActions.class);
        playerSkillProjectileComponent.setProjectileDirection(playerActions.getWalkDirection().cpy());
        return projectile;
    }
}