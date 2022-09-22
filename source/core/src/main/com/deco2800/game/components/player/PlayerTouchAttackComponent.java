package com.deco2800.game.components.player;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Null;
import com.deco2800.game.components.CombatItemsComponents.MeleeStatsComponent;
import com.deco2800.game.components.CombatItemsComponents.WeaponStatsComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.TouchAttackComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.physics.BodyUserData;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.services.ServiceLocator;

public class PlayerTouchAttackComponent extends TouchAttackComponent {
    private CombatStatsComponent combatStats;
    private boolean enemyCollide = false;
    private Entity target;

    private boolean canAttack;
    private long  cooldownEnd;

    /**
     * Create a component which attacks enemy entities on collision, without knockback.
     * @param targetLayer The physics layer of the target's collider.
     */
    public PlayerTouchAttackComponent(short targetLayer) {
        super(targetLayer);
    }

    @Override
    public void create() {
        entity.getEvents().addListener("attack", this::attack);
        entity.getEvents().addListener("collisionStart", this::playerCollidesEnemyStart);
        combatStats = entity.getComponent(CombatStatsComponent.class); //or just get the currently equipped weapon's damage
        entity.getEvents().addListener("collisionEnd", this::playerCollidesEnemyEnd);
        //entity.getEvents().addListener("cooldownOver", this::cooldownOver);
    }

    @Override
    public void update() {
        checkCanAttack();
    }

    /**
     * Method called when collision is detected between the hitbox of the entity that implements this component (player)
     * and another entity (enemy).
     * @param me The fixture of the entity (player) that implements this component.
     * @param other The fixture of the other entity (enemy) that is colliding.
     */
    private void playerCollidesEnemyStart(Fixture me, Fixture other) {
        if (((BodyUserData) other.getBody().getUserData()).entity.checkEntityType(EntityTypes.ENEMY)) {
            target = ((BodyUserData) other.getBody().getUserData()).entity;
            enemyCollide = true;
        }
    }

    /**
     * Method called when the player entity is attacking.
     */
    void attack() {
        if (canAttack) {
            Sound attackSound = ServiceLocator.getResourceService().getAsset("sounds/Impact4.ogg", Sound.class);
            attackSound.play();
            canAttack = false;

            Entity weaponEquipped = entity.getComponent(InventoryComponent.class).getEquipable(0);
            if (weaponEquipped != null) {
                cooldownEnd = (long) (System.currentTimeMillis() + weaponEquipped.getComponent(MeleeStatsComponent.class).getCoolDown());
            } else {
                cooldownEnd = (System.currentTimeMillis() + 4000); //cooldown when no weapon equipped
            }
            if (enemyCollide) {
                applyDamageToTarget(target);
                if (target.getComponent(CombatStatsComponent.class).getHealth() == 0) {
                    target.dispose();
                    target.getComponent(CombatStatsComponent.class).dropWeapon();
                    if (target.getComponent(AnimationRenderComponent.class) != null) {
                        target.getComponent(CombatStatsComponent.class).dropMaterial();
                        target.getComponent(AnimationRenderComponent.class).stopAnimation(); //this is the magic line
                    }
                }
                entity.getEvents().trigger("hitEnemy", target); // for skill listener
            }
        }
    }

    public void checkCanAttack() {
        if (System.currentTimeMillis() > cooldownEnd) {
            canAttack = true;
            cooldownEnd = 0;
        }
    }

    /**
     * Applies damage to a given enemy target
     * @param target the target enemy entity to do damage to
     */
    private void applyDamageToTarget(Entity target) {
        CombatStatsComponent targetStats = target.getComponent(CombatStatsComponent.class);
        targetStats.hit(combatStats);
    }

    /**
     * Method called when collision ends between the hitbox of the entity that implements this component (player)
     * and another entity (enemy)
     * @param me The fixture of the entity (player) that implements this component.
     * @param other The fixture of the other entity (enemy) that is colliding.
     */
    private void playerCollidesEnemyEnd(Fixture me, Fixture other) {
        enemyCollide = false;
    }
}
