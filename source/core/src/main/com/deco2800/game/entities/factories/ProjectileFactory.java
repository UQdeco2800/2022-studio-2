package com.deco2800.game.entities.factories;



import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;

public class ProjectileFactory {

    /**
     * Creates base entity of projectile
     * @return the base of projectile
     */
    public static Entity createBaseProjectile() {
        AITaskComponent aiComponent = new AITaskComponent();
        Entity projectile = new Entity()
                .addComponent(new PhysicsComponent())
                //.addComponent(new PhysicsMovementComponent())
                .addComponent(new ColliderComponent().setLayer(PhysicsLayer.NONE))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
                .addComponent(new CombatStatsComponent(1, 1, 0, 0))
                .addComponent(aiComponent);
        PhysicsUtils.setScaledCollider(projectile, 1.0f, 1.0f);

        projectile.setEntityType(EntityTypes.PROJECTILE);
        return projectile;

        /*
        Entity projectile = new Entity()
                .addComponent(physicsComponent)
                .addComponent(new ColliderComponent().setLayer(PhysicsLayer.NONE))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                .addComponent(new TouchAttackComponent(PhysicsLayer.NPC, 10.0f))
                .addComponent(new CombatStatsComponent(100000, 100000, 0, 0))
                .addComponent(projectileAnimator)
                .addComponent(playerSkillProjectileComponent);
         */

    }

    public static Entity createPoopsSludge(Entity target) {
        Entity poops = createBaseProjectile();
        poops.addComponent(new TextureRenderComponent("images/Enemies/poopSludge.png"))
                .getComponent(TextureRenderComponent.class).scaleEntity();
        //poops.getComponent(AITaskComponent.class)
          //      .addTask(new ShootTask(target, 10, 10f));
        poops.setEntityType(EntityTypes.ENEMY);
        return poops;
    }

    public static Entity createDiscus(Entity target) {
        Entity discus = createBaseProjectile();
        discus.addComponent(new TextureRenderComponent("images/Enemies/discus.png"))
                .getComponent(TextureRenderComponent.class).scaleEntity();
        //discus.getComponent(AITaskComponent.class)
          //      .addTask(new ShootTask(target, 10, 10f));
        discus.setEntityType(EntityTypes.ENEMY);
        return discus;
    }

    /**
     * Creates a non-colliding projectile shooting in the walk direction of the player
     * which damages enemies.
     * @param player the player entity
     * @param angle the angle in multiples of pi radians, angle = 2 = 360deg (2pi radians)
     *              from the walk direction of the player
     * @return the projectile entity
     */
    public static Entity createBasePlayerProjectile(Entity player, double angle) {
        PhysicsComponent physicsComponent = new PhysicsComponent();
        PlayerSkillProjectileComponent playerSkillProjectileComponent = new PlayerSkillProjectileComponent();

        AnimationRenderComponent projectileAnimator = new AnimationRenderComponent(
                ServiceLocator.getResourceService().getAsset("images/Skills/projectileSprites.atlas",
                        TextureAtlas.class));
        projectileAnimator.addAnimation("upright",0.2f, Animation.PlayMode.LOOP);
        projectileAnimator.addAnimation("right",0.2f, Animation.PlayMode.LOOP);
        projectileAnimator.addAnimation("downright",0.2f, Animation.PlayMode.LOOP);
        projectileAnimator.addAnimation("down",0.2f, Animation.PlayMode.LOOP);
        projectileAnimator.addAnimation("downleft",0.2f, Animation.PlayMode.LOOP);
        projectileAnimator.addAnimation("left",0.2f, Animation.PlayMode.LOOP);
        projectileAnimator.addAnimation("upleft",0.2f, Animation.PlayMode.LOOP);
        projectileAnimator.addAnimation("up",0.2f, Animation.PlayMode.LOOP);

        Entity projectile = new Entity()
                .addComponent(physicsComponent)
                .addComponent(new ColliderComponent().setLayer(PhysicsLayer.NONE))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                .addComponent(new TouchAttackComponent(PhysicsLayer.NPC, 10.0f))
                .addComponent(new CombatStatsComponent(100000, 100000, 0, 0))
                .addComponent(projectileAnimator)
                .addComponent(playerSkillProjectileComponent);

        PhysicsUtils.setScaledCollider(projectile, 1.0f, 1.0f);
        projectile.getComponent(AnimationRenderComponent.class).scaleEntity();
        projectile.setEntityType(EntityTypes.PROJECTILE);

        PlayerActions playerActions = player.getComponent(PlayerActions.class);
        if(playerActions.getWalkDirection().cpy().x == 0 && playerActions.getWalkDirection().cpy().y == 0) {
            playerSkillProjectileComponent.setProjectileDirection(new Vector2(1, 0));
            projectileAnimator.startAnimation("right");
        } else {
            double angleRadians = angle * Math.PI;
            Vector2 rotatedVector = rotateVector(playerActions.getWalkDirection().cpy(), angleRadians);
            setAnimationDirection(getVectorAngle(rotatedVector.cpy()), projectileAnimator);
            playerSkillProjectileComponent.setProjectileDirection(rotatedVector.cpy());
        }
        return projectile;
    }

    /**
     * Rotates a vector a certain number of radians. Uses a rotation transformation matrix
     * calculations.
     * @param vectorToRotate the vector to rotate
     * @param radianAngle the angle in radians to rotate the vector from the direction of
     *                    the original vector
     * @return the rotated vector
     */
    private static Vector2 rotateVector(Vector2 vectorToRotate, double radianAngle) {
        double cosAngle = Math.cos(radianAngle);
        double sinAngle = Math.sin(radianAngle);
        return new Vector2((float) ((vectorToRotate.x * cosAngle) - (vectorToRotate.y * sinAngle)),
                (float) ((vectorToRotate.x * sinAngle) + (vectorToRotate.y * cosAngle)));
    }

    /**
     * Sets a projectile animation direction based on the angle of the projectile
     * @param angle the angle of the projectile from the x > 0, x axis
     *              (counterclockwise from quadrant 1, x axis cartesian coordinates)
     * @param animator the projectile animator which has the animations:
     *                 "right", "upright", "up", "upleft", "left", "downleft", "down", "downright"
     *                 each corresponding to the direction the projectile head's art is facing
     */
    private static void setAnimationDirection(double angle, AnimationRenderComponent animator) {
        double angleDegrees = angle * (180/Math.PI);
        if (angleDegrees <= 22.5 || angleDegrees >=  337.5) {
            animator.startAnimation("right");
        } else if (angleDegrees <= 67.5) {
            animator.startAnimation("upright");
        } else if (angleDegrees <= 112.5) {
            animator.startAnimation("up");
        } else if (angleDegrees <= 157.5) {
            animator.startAnimation("upleft");
        } else if (angleDegrees <= 202.5) {
            animator.startAnimation("left");
        } else if (angleDegrees <= 247.5) {
            animator.startAnimation("downleft");
        } else if (angleDegrees <= 292.5) {
            animator.startAnimation("down");
        } else if (angleDegrees < 337.5) {
            animator.startAnimation("downright");
        }
    }

    /**
     * Returns the angle in radians of a given vector from the x axis of quadrant 1
     * @param vector the vector to get the angle of
     * @return angle from the positive x axis of the vector in radians,
     *          min 0 and max 2pi radians return
     */
    private static double getVectorAngle(Vector2 vector) {
        double rawAngle = Math.atan2(Math.abs(vector.y), Math.abs(vector.x));
        if (vector.y < 0 && vector.x < 0) { // Quadrant 3, add a half rotation
            return rawAngle + Math.PI;
        } else if (vector.y < 0) { // Quadrant 4, subtract from full rotation
            return 2 * Math.PI - rawAngle;
        } else if (vector.x < 0) { // Quadrant 2, add 1/4 of a rotation
            return Math.PI - rawAngle;
        } else { // Quadrant 1, no changes
            return rawAngle;
        }
    }

}