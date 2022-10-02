package com.deco2800.game.components.CombatItemsComponents;

import com.deco2800.game.components.Component;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.rendering.TextureRenderComponent;

//this is tied to the player
public class WeaponAuraManager extends Component {

    public Entity auraApplied;
    private long auraEndTime;
    WeaponStatsComponent weaponStats;

    @Override
    public void update() {
        if (entity.getComponent(InventoryComponent.class).getEquipable(0) != null) {
            checkAuraEffect();
        }
    }

    /**
     * Check if the aura is expired, if it is reverts the damage stat to default and remove the aura.
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
     * Applies the given aura to the given weapon.
     * @param aura Aura to apply to the weapon
     * @param weapon Weapon to apply the aura to
     */
    public void applyAura(Entity aura, Entity weapon) {
        auraEndTime = System.currentTimeMillis() + aura.getComponent(WeaponAuraComponent.class).getAuraDuration();
        auraApplied = aura;
        if (weapon.getComponent(PhysicalWeaponStatsComponent.class) != null) {
            weaponStats = weapon.getComponent(PhysicalWeaponStatsComponent.class);

            weaponStats.setDamage(weaponStats.getDamage() * aura.getComponent(WeaponAuraComponent.class).getDmgMultiplier());
            weaponStats.setCoolDown(weaponStats.getCoolDown() * aura.getComponent(WeaponAuraComponent.class).getCdMultiplier());
            //shows the applied buff in the game
            entity.getComponent(BuffDisplayComponent.class).setBuff(aura.getComponent(TextureRenderComponent.class).getTexturePath());
        }
    }

    public Entity getAura(){
        return auraApplied;
    }
}
