package com.deco2800.game.components.CombatItemsComponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.services.ServiceLocator;


//MASSIVE CREDITS TO KURT
public class WeaponArrowProjectileComponent extends Component {

    private PhysicsComponent physicsComponent;
    private Vector2 projectileVector;
    private final Vector2 projectileVelocity = new Vector2(8, 8);

    @Override
    public void create() {
        physicsComponent = entity.getComponent(PhysicsComponent.class);
        entity.getEvents().addListener("collisionStart", this::onCollisionStart);
    }

    /**
     * Sets the projectile to move in a certain direction at a certain speed
     * and updates the projectile to keep moving at that speed.
     */
    @Override
    public void update() {
        Body body = physicsComponent.getBody();
        Vector2 currentVelocity = body.getLinearVelocity();

        Vector2 desiredVelocity = projectileVector.cpy();

        // impulse = (desiredVel - currentVel) * mass
        Vector2 impulse = desiredVelocity.sub(currentVelocity).scl(body.getMass());
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
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
     * Disposes the arrow when in contact with an enemy
     *
     * @param me    - arrow entity
     * @param other - any other entity
     */
    private void onCollisionStart(Fixture me, Fixture other) {
        Entity projectile = ((BodyUserData) me.getBody().getUserData()).entity;
        Entity enemy = ((BodyUserData) other.getBody().getUserData()).entity;
        if (enemy.checkEntityType(EntityTypes.ENEMY)) {
            Sound attackSound = ServiceLocator.getResourceService().getAsset("sounds/plungerArrowSound.mp3", Sound.class);
            attackSound.play();
        }
    }
}
