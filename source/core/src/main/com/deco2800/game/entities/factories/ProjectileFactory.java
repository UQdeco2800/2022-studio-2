package com.deco2800.game.entities.factories;



import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.components.combatitemscomponents.WeaponArrowProjectileComponent;
import com.deco2800.game.components.combatitemscomponents.PhysicalWeaponStatsComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.npc.EnemyProjectileComponent;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.components.player.PlayerSkillProjectileComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.configs.combatitemsconfig.WeaponConfigSetup;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;

public class ProjectileFactory {

    /**
     * Creates base entity of projectile
     * @param ownerEntity the entity that throws this projectile
     * @param target the entity that this projectile is thrown at
     * @return the base of projectile
     */
    public static Entity createBaseProjectile(Entity ownerEntity, Entity target) {
        EnemyProjectileComponent enemyProjectileComponent = new EnemyProjectileComponent();

        Entity projectile = new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent().setLayer(PhysicsLayer.NONE))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
                .addComponent(new CombatStatsComponent(1, 3, 0, 0))
                .addComponent(enemyProjectileComponent);
        projectile.setEntityType(EntityTypes.PROJECTILE);
        enemyProjectileComponent.setProjectileDirection(new Vector2(
                target.getPosition().x - ownerEntity.getPosition().x,
                target.getPosition().y - ownerEntity.getPosition().y
                ));
        return projectile;

    }

    /**
     * Create poop sludge projectile
     * @param ownerEntity the entity that throws this projectile
     * @param target the entity that this projectile is thrown at
     * @return the poop sludge projectile
     */
    public static Entity createPoopsSludge(Entity ownerEntity, Entity target) {
        Entity poops = createBaseProjectile(ownerEntity, target);
        poops.addComponent(new TextureRenderComponent("images/Enemies/poopSludge.png"))
                .getComponent(TextureRenderComponent.class).scaleEntity();

        //Make hitbox and collider correct size.
        //The size values come from dividing the poop sludge's actual size by the total 64x64 sprite size
        //The alignment values come from 1/64 multiplied by the center point of the poop sludge (Since the poop sludge sprite is slightly off center)
        poops.getComponent(ColliderComponent.class).setAsBox(new Vector2(0.17f, 0.09f), new Vector2(0.48f, 0.47f));
        poops.getComponent(HitboxComponent.class).setAsBox(new Vector2(0.17f, 0.09f), new Vector2(0.48f, 0.47f));
        poops.setEntityType(EntityTypes.ENEMY);

        poops.setScale(2,2);
        return poops;
    }

    /**
     * Create discus projectile
     * @param ownerEntity the entity that throws this projectile
     * @param target the entity that this projectile is thrown at
     * @return the discus projectile
     */
    public static Entity createDiscus(Entity ownerEntity, Entity target) {
        Entity discus = createBaseProjectile(ownerEntity, target);
        discus.addComponent(new TextureRenderComponent("images/Enemies/discus.png"))
                .getComponent(TextureRenderComponent.class).scaleEntity();

        //Make hitbox and collider correct size
        //The size values come from dividing the discus' actual size by the total 64x64 sprite size
        //The alignment is center because the discus is in the center of its sprite
        discus.getComponent(ColliderComponent.class).setAsBoxAligned(new Vector2(0.28f, 0.125f),
                PhysicsComponent.AlignX.CENTER, PhysicsComponent.AlignY.CENTER);
        discus.getComponent(HitboxComponent.class).setAsBoxAligned(new Vector2(0.28f, 0.125f),
                PhysicsComponent.AlignX.CENTER, PhysicsComponent.AlignY.CENTER);

        discus.setEntityType(EntityTypes.ENEMY);
        discus.setScale(2,2);
        return discus;
    }

    /**
     * Creates a non-colliding wrench projectile shooting in the walk direction of the player
     * which damages enemies.
     * @param player the player entity
     * @param angle the angle in multiples of pi radians, angle = 2 = 360deg (2pi radians)
     *              from the walk direction of the player
     * @return the projectile entity
     */
    public static Entity createWrenchPlayerProjectile(Entity player, double angle) {
        PhysicsComponent physicsComponent = new PhysicsComponent();
        PlayerSkillProjectileComponent playerSkillProjectileComponent = new PlayerSkillProjectileComponent();

        AnimationRenderComponent projectileAnimator = new AnimationRenderComponent(
                ServiceLocator.getResourceService().getAsset("images/Skills/WrenchAnimation.atlas",
                        TextureAtlas.class));
        projectileAnimator.addAnimation("wrenchSpin",0.05f, Animation.PlayMode.LOOP);
        Entity projectile = new Entity()
                .addComponent(physicsComponent)
                .addComponent(new ColliderComponent().setLayer(PhysicsLayer.NONE))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                .addComponent(new TouchAttackComponent(PhysicsLayer.NPC, 1.5f))
                .addComponent(new CombatStatsComponent(1000, 5, 0, 0))
                .addComponent(projectileAnimator)
                .addComponent(playerSkillProjectileComponent);

        PhysicsUtils.setScaledCollider(projectile, 0.8f, 0.8f);
        projectile.getComponent(AnimationRenderComponent.class).scaleEntity();
        projectile.setEntityType(EntityTypes.PROJECTILE);

        PlayerActions playerActions = player.getComponent(PlayerActions.class);
        if(playerActions.getWalkDirection().cpy().x == 0 && playerActions.getWalkDirection().cpy().y == 0) {
            playerSkillProjectileComponent.setProjectileDirection(new Vector2(1, 0));
        } else {
            double angleRadians = angle * Math.PI;
            Vector2 rotatedVector = rotateVector(playerActions.getWalkDirection().cpy(), angleRadians);
            playerSkillProjectileComponent.setProjectileDirection(rotatedVector.cpy());
        }
        projectileAnimator.startAnimation("wrenchSpin");
        return projectile;
    }

    /**
     * Creates a non-colliding projectile shooting in the walk direction of the player
     * which damages enemies. Uses animations from images/Skills/projectileSprites.png
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
     * Creates a non-colliding projectile shooting in the walk direction of the player (thank YOU TEAM 08 KURT)
     * which damages enemies.
     * @param player the player entity
     * @param angle the angle in multiples of pi radians, angle = 2 = 360deg (2pi radians)
     *              from the walk direction of the player
     * @return the projectile entity
     */
    public static Entity createWeaponProjectile(Entity player, double angle) { //TEAM 04 WIP
       WeaponConfigSetup configs = FileLoader.readClass(WeaponConfigSetup.class, "configs/Weapons.json");
        double dmg = player.getComponent(InventoryComponent.class).getEquipable(0).getComponent(PhysicalWeaponStatsComponent.class).getDamage();

        PhysicsComponent physicsComponent = new PhysicsComponent();
        WeaponArrowProjectileComponent weaponArrowProjectileComponent = new WeaponArrowProjectileComponent();

        AnimationRenderComponent projectileAnimator = new AnimationRenderComponent(
                ServiceLocator.getResourceService().getAsset("images/CombatItems/animations/PlungerBow/plungerBowProjectile.atlas",
                        TextureAtlas.class));
        projectileAnimator.addAnimation("upright",0.05f,  Animation.PlayMode.LOOP);
        projectileAnimator.addAnimation("right",0.05f, Animation.PlayMode.LOOP);
        projectileAnimator.addAnimation("downright",0.05f, Animation.PlayMode.LOOP);
        projectileAnimator.addAnimation("down",0.05f, Animation.PlayMode.LOOP);
        projectileAnimator.addAnimation("downleft",0.05f, Animation.PlayMode.LOOP);
        projectileAnimator.addAnimation("left",0.05f, Animation.PlayMode.LOOP);
        projectileAnimator.addAnimation("upleft",0.05f, Animation.PlayMode.LOOP);
        projectileAnimator.addAnimation("up",0.05f, Animation.PlayMode.LOOP);

        Entity projectile = new Entity()
                .addComponent(physicsComponent)
                .addComponent(new ColliderComponent().setLayer(PhysicsLayer.NONE))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                .addComponent(new TouchAttackComponent(PhysicsLayer.NPC, 5.0f))
                .addComponent(new CombatStatsComponent(100000, (int)dmg, 0, 0))
                .addComponent(projectileAnimator)
                .addComponent(weaponArrowProjectileComponent);

        PhysicsUtils.setScaledCollider(projectile, 1.0f, 1.0f);
        projectile.getComponent(AnimationRenderComponent.class).scaleEntity();
        projectile.setEntityType(EntityTypes.PROJECTILE);

        PlayerActions playerActions = player.getComponent(PlayerActions.class);
        if(playerActions.getWalkDirection().cpy().x == 0 && playerActions.getWalkDirection().cpy().y == 0) {
            weaponArrowProjectileComponent.setProjectileDirection(new Vector2(1, 0));
            projectileAnimator.startAnimation("right");
        } else {
            double angleRadians = angle * Math.PI;
            Vector2 rotatedVector = rotateVector(playerActions.getWalkDirection().cpy(), angleRadians);
            setAnimationDirection(getVectorAngle(rotatedVector.cpy()), projectileAnimator);
            weaponArrowProjectileComponent.setProjectileDirection(rotatedVector.cpy());
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

    /**
     * Creates an AOE attack. It is functionally a projectile with no movement.
     * @param player the player entity
     * @param angle the angle in multiples of pi radians, angle = 2 = 360deg (2pi radians)
     *              from the walk direction of the player
     * @return the projectile entity
     */
    public static Entity createPlayerAOE(Entity player, double angle) {
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
                .addComponent(new TouchAttackComponent(PhysicsLayer.NPC, 150.0f))
                .addComponent(new CombatStatsComponent(100000, 0, 0, 0))
                .addComponent(projectileAnimator)
                .addComponent(playerSkillProjectileComponent);

        projectile.setScale(4, 4);
        PhysicsUtils.setScaledCollider(projectile, 1, 1);
        //projectile.getComponent(AnimationRenderComponent.class).scaleEntity();
        projectile.setEntityType(EntityTypes.PROJECTILE);

        PlayerActions playerActions = player.getComponent(PlayerActions.class);
        if(playerActions.getWalkDirection().cpy().x == 0 && playerActions.getWalkDirection().cpy().y == 0) {
            playerSkillProjectileComponent.setProjectileDirection(new Vector2(1, 0).scl(0.001f));
            projectileAnimator.startAnimation("right");
        } else {
            double angleRadians = angle * Math.PI;
            Vector2 rotatedVector = rotateVector(playerActions.getWalkDirection().cpy(), angleRadians);
            setAnimationDirection(getVectorAngle(rotatedVector.cpy()), projectileAnimator);
            playerSkillProjectileComponent.setProjectileDirection(rotatedVector.cpy().scl(0.001f));
        }
        return projectile;
    }
}