package com.deco2800.game.components.tasks;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.services.ServiceLocator;

public class PlayerTouchAttackComponent extends TouchAttackComponent {
    private CombatStatsComponent combatStats;
    private boolean enemyCollide = false;
    private Entity target;

    /**
     * Create a component which attacks entities on collision, without knockback.
     * @param targetLayer The physics layer of the target's collider.
     */
    public PlayerTouchAttackComponent(short targetLayer) {
        super(targetLayer);
    }


    @Override
    public void create() {
        entity.getEvents().addListener("attack", this::attack);
        entity.getEvents().addListener("collisionStart", this::onCollisionStart);
        combatStats = entity.getComponent(CombatStatsComponent.class); //or just get the currently equipped weapon's damage
        entity.getEvents().addListener("collisionEnd", this::onCollisionEnd);
    }


    private void onCollisionStart(Fixture me, Fixture other) {
        target = ((BodyUserData) other.getBody().getUserData()).entity;
        if ((target.checkEntityType(EntityTypes.ENEMY))) {
            // Doesn't match our target layer, ignore
            enemyCollide = true;
        }
    }

    void attack() {
        Sound attackSound = ServiceLocator.getResourceService().getAsset("sounds/Impact4.ogg", Sound.class);
        attackSound.play();
        if (enemyCollide) {
            applyDamage(target);
            if (target.getComponent(CombatStatsComponent.class).getHealth() == 0) {
                target.dispose();
                target.getComponent(AnimationRenderComponent.class).stopAnimation(); //this is the magic line
            }
        }
    }
    /**
     * Applies damage to a given target. Checks for skill state invulnerability in the case
     * of a player target.
     * @param target the target entity to do domage to
     */
    private void applyDamage(Entity target) {
        // Try to attack target.
        CombatStatsComponent targetStats = target.getComponent(CombatStatsComponent.class);
        targetStats.hit(combatStats);
    }

    private void onCollisionEnd(Fixture me, Fixture other) {
        enemyCollide = false;
    }
}
