package com.deco2800.game.entities.factories;



import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.ai.tasks.AITaskComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.components.player.PlayerSkillProjectileComponent;
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

    /**
     * Creates a non-colliding projectile shooting in the walk direction of the player
     * which damages enemies.
     * @param player the player entity
     * @param angle the angle in multiples of pi radians, angle = 2 = 360deg (2pi radians)
     * @return the projectile entity
     */
    public static Entity createBasePlayerProjectile(Entity player, double angle) {
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
        if(angle == 0) {
            playerSkillProjectileComponent.setProjectileDirection(playerActions.getWalkDirection().cpy());
        } else {
            double angleRadians = angle * Math.PI;
            Vector2 rotatedVector = rotateVector(playerActions.getWalkDirection().cpy(), angleRadians);
            playerSkillProjectileComponent.setProjectileDirection(rotatedVector.cpy());
        }
        return projectile;
    }

    /**
     * Rotates a vector a certain number of radians. Uses a rotation transformation matrix
     * calculations.
     * @param vectorToRotate the vector to rotate
     * @param radianAngle the angle in radians to rotate the vector
     * @return the rotated vector
     */
    private static Vector2 rotateVector(Vector2 vectorToRotate, double radianAngle) {
        double cosAngle = Math.cos(radianAngle);
        double sinAngle = Math.sin(radianAngle);
        return new Vector2((float) ((vectorToRotate.x * cosAngle) - (vectorToRotate.y * sinAngle)),
                (float) ((vectorToRotate.x * sinAngle) + (vectorToRotate.y * cosAngle)));
    }

}