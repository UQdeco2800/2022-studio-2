package com.deco2800.game.components.combatitemsComponents;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.services.ServiceLocator;


//MASSIVE CREDITS TO KURT
public class WeaponArrowProjectileComponent extends Component {

    private PhysicsComponent physicsComponent;
    private Vector2 projectileVector;
    private final Vector2 projectileVelocity = new Vector2(6, 6);
    long arrowExpireTime = System.currentTimeMillis() + 2000;

    @Override
    public void create() {
        physicsComponent = entity.getComponent(PhysicsComponent.class);
        entity.getEvents().addListener("collisionStart", this::onCollisionStart);
    }

    /**
     * Sets the projectile to move in a certain direction at a certain speed
     * and updates the projectile to keep moving at that speed. Also disposes the arrow after 2 seconds by checking the
     * duration of the projectile spawned.
     */
    @Override
    public void update() {
        Body body = physicsComponent.getBody();
        Vector2 currentVelocity = body.getLinearVelocity();

        Vector2 desiredVelocity = projectileVector.cpy();

        // impulse = (desiredVel - currentVel) * mass
        Vector2 impulse = desiredVelocity.sub(currentVelocity).scl(body.getMass());
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);

        disposeExpiredArrow();
    }

    /**
     * Sets the projectile's direction
     *
     * @param projectileDirection the vector direction of the projectile's movement
     */
    public void setProjectileDirection(Vector2 projectileDirection) {
        projectileVector = projectileDirection.cpy().scl(projectileVelocity);
    }

    /**
     * Plays sound when in contact with enemy
     *
     * @param me    - arrow entity
     * @param other - any other entity
     */
    private void onCollisionStart(Fixture me, Fixture other) {
        Entity enemy = ((BodyUserData) other.getBody().getUserData()).entity;
        if (enemy.checkEntityType(EntityTypes.ENEMY)) {
            Sound attackSound = ServiceLocator.getResourceService().getAsset("sounds/combatitems/plungerSound.mp3", Sound.class);
            attackSound.play();
        }
    }

    /**
     * Disposes arrow from map after 2 seconds the arrow is shot.
     */
    private void disposeExpiredArrow() {
        if (System.currentTimeMillis() > arrowExpireTime) {
            entity.dispose();
            entity.getComponent(AnimationRenderComponent.class).stopAnimation();
        }
    }
}
