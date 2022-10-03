package com.deco2800.game.components.player;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.areas.ForestGameArea;
import com.deco2800.game.areas.UndergroundGameArea;
import com.deco2800.game.components.CombatItemsComponents.*;
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
    private Entity combatAnimator;
    private boolean canAttack;
    private long  cooldownEnd;
    private String animationDesc;

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
            entity.getEvents().trigger("enemyCollision", target); // skill listener
        }
    }

    /**
     * Sets the combat item animator for this actions component
     * @param combatAnimator the combat animator entity which has subcomponents
     *                      PlayerSkillAnimationController and AnimationRenderer
     */
    public void setCombatAnimator(Entity combatAnimator){
        this.combatAnimator = combatAnimator;
    }

    /**
     * Returns the combat item animator
     * @return combat item animator
     */
    public Entity getCombatAnimator() {
        return combatAnimator;
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
            Entity auraEquipped = ServiceLocator.getGameArea().getPlayer().getComponent(WeaponAuraManager.class).auraApplied;
            if (weaponEquipped != null) {
                if (weaponEquipped.getComponent(PhysicalWeaponStatsComponent.class) != null) {
                    cooldownEnd = (long) (System.currentTimeMillis() + weaponEquipped.getComponent(PhysicalWeaponStatsComponent.class).getCoolDown());
                    //Sets the attack animation dependent on the weapon that is currently equipped
                    String description = weaponEquipped.getComponent(PhysicalWeaponStatsComponent.class).getDescription();
                    //When an aura is applied, play the respective aura animation
                    if (auraEquipped != null) {
                        String current_aura = auraEquipped.getComponent(WeaponAuraComponent.class).getDescription();
                        animationDesc = description+current_aura;
                    }
                    else {
                        animationDesc = description;
                    }
                    combatAnimator.getEvents().trigger(animationDesc);
                }

            } else {
                cooldownEnd = (System.currentTimeMillis() + 4000); //cooldown when no weapon equipped
            }

            if (enemyCollide) {
                applyDamageToTarget(target);
                entity.getEvents().trigger("hitEnemy", target); // for skill listener
            }

            else if (weaponEquipped != null && weaponEquipped.checkEntityType(EntityTypes.RANGED)) {
                if (ServiceLocator.getGameArea() instanceof ForestGameArea) {
                    ((ForestGameArea) ServiceLocator.getGameArea()).spawnWeaponProjectile();
                }
                else if (ServiceLocator.getGameArea() instanceof UndergroundGameArea){
                    ((UndergroundGameArea) ServiceLocator.getGameArea()).spawnWeaponProjectile();
                }
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
