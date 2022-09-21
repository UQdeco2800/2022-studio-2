package com.deco2800.game.components.npc;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.services.ServiceLocator;


public class EnemyProjectileComponent extends Component {

    private PhysicsComponent physicsComponent;
    private Vector2 projectileVector;
    private Vector2 projectileVelocity = new Vector2(1,1);

    @Override
    public void create() {
        physicsComponent = entity.getComponent(PhysicsComponent.class);
        entity.getEvents().addListener("collisionStart", this::removeProjectile);
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
        Vector2 impulse = desiredVelocity.sub(currentVelocity).scl(body.getMass());
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
    }

    /**
     * Sets the projectile's direction
     * @param projectileDirection the vector direction of the projectile's movement
     */
    public void setProjectileDirection(Vector2 projectileDirection) {
        projectileVector = projectileDirection.cpy().scl(projectileVelocity);
    }

    /**
     * Remove projectile from map
     */
    public void removeProjectile(Fixture me, Fixture other) {
        Fixture f = ServiceLocator.getGameArea().getPlayer().getComponent(HitboxComponent.class).getFixture();
        if (other == f) {
            Entity entityOfComponent = getEntity();
            ForestGameArea.removeProjectileOnMap(entityOfComponent);
        }
    }

}
