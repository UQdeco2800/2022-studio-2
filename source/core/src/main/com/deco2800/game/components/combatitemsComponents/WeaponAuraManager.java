package com.deco2800.game.components.combatitemsComponents;

import com.deco2800.game.components.Component;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.components.player.PlayerTouchAttackComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.PlayerFactory;

//this is tied to the player
public class WeaponAuraManager extends Component {

    public Entity auraApplied;
    private long auraEndTime;
    WeaponStatsComponent weaponStats;

    private Entity combatAnimator;

    /**
     * Sets the combat item animator for this actions component
     * @param combatAnimator the combat animator entity which has subcomponents
     *                      PlayerSkillAnimationController and AnimationRenderer
     */
    public void setCombatAnimator(Entity combatAnimator){
        this.combatAnimator = combatAnimator;
    }


    @Override
    public void update() {
        if (entity.getComponent(InventoryComponent.class).getEquipable(0) != null) {
            checkAuraEffect();
        }
    }

    /**
     * Check if the aura is expired, if it is reverts the weapon stats to default and remove the aura.
     */
    public void checkAuraEffect() {
        if (auraApplied != null && System.currentTimeMillis() > auraEndTime) {
            weaponStats.setDamage(weaponStats.getDamage() / auraApplied.getComponent(WeaponAuraComponent.class).getDmgMultiplier());
            weaponStats.setCoolDown(weaponStats.getCoolDown() / auraApplied.getComponent(WeaponAuraComponent.class).getCdMultiplier());
            auraApplied = null;
            auraEndTime = 0;
            //stops showing the applied buff in the UI
            entity.getComponent(BuffDisplayComponent.class).clearBuff();
        }
    }

    /**
     * Sets the given aura to the applied aura
     * @param aura
     */
    public void setAura(Entity aura) {
        auraApplied = aura;
    }

    /**
     * Sets the weapon stats if player is equipped with a weapon
     * @param weapon
     */
    public void setWeaponStats(Entity weapon) {
        weaponStats = weapon.getComponent(PhysicalWeaponStatsComponent.class);
    }

    /**
     * Applies the given aura to the given weapon.
     * @param aura Aura to apply to the weapon
     * @param weapon Weapon to apply the aura to
     */
    public void applyAura(Entity aura, Entity weapon) {
        auraEndTime = System.currentTimeMillis() + aura.getComponent(WeaponAuraComponent.class).getAuraDuration();
        setAura(aura);
        if (weapon.getComponent(PhysicalWeaponStatsComponent.class) != null) {

            setWeaponStats(weapon);

            weaponStats.setDamage(weaponStats.getDamage() * aura.getComponent(WeaponAuraComponent.class).getDmgMultiplier());
            weaponStats.setCoolDown(weaponStats.getCoolDown() * aura.getComponent(WeaponAuraComponent.class).getCdMultiplier());
            //shows the applied buff in the game
            String auraCurrent = auraApplied.getComponent(WeaponAuraComponent.class).getDescription();

            entity.getComponent(BuffDisplayComponent.class).setBuff(auraCurrent);

            //apply static aura animation
            Entity newCombatAnimator = PlayerFactory.createCombatAnimator(entity);
            setCombatAnimator(newCombatAnimator);
            String description = weapon.getComponent(PhysicalWeaponStatsComponent.class).getDescription();
            String animationToApply = description+ auraCurrent +"Static";
            combatAnimator.getEvents().trigger(animationToApply);
            entity.getComponent(PlayerTouchAttackComponent.class).getCombatAnimator().getEvents().trigger(animationToApply);
        }
    }

    /**
     * Returns the aura currently applied to the equipped weapon of the player
     * @return Aura currently applied to the equipped weapon of the player.
     */
    public Entity getAura(){
        return auraApplied;
    }

    public WeaponStatsComponent getWeaponStats() {
        return weaponStats;
    }
}
