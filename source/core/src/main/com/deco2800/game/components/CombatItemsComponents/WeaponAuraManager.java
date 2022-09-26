package com.deco2800.game.components.CombatItemsComponents;

import com.deco2800.game.components.Component;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.entities.Entity;

//this is tied to the player
public class WeaponAuraManager extends Component {

    Entity auraApplied;
    private long auraEndTime;
    WeaponStatsComponent weaponStats;

    @Override
    public void update() {
        if (entity.getComponent(InventoryComponent.class).getEquipable(0) != null) {
            checkAuraEffect();
        }
    }

    public void checkAuraEffect() {
        if (auraApplied != null && System.currentTimeMillis() > auraEndTime) {
            weaponStats.setDamage(weaponStats.getDamage() / auraApplied.getComponent(WeaponAuraComponent.class).getDmgMultiplier());
            weaponStats.setCoolDown(weaponStats.getCoolDown() / auraApplied.getComponent(WeaponAuraComponent.class).getCdMultiplier());
            auraApplied = null;
            auraEndTime = 0;
        }
    }

    public void applyAura(Entity aura, Entity weapon) {
        auraEndTime = System.currentTimeMillis() + aura.getComponent(WeaponAuraComponent.class).getAuraDuration();
        auraApplied = aura;
        if (weapon.getComponent(PhyiscalWeaponStatsComponent.class) != null) {
            weaponStats = weapon.getComponent(PhyiscalWeaponStatsComponent.class);
            weaponStats.setDamage(weaponStats.getDamage() * aura.getComponent(WeaponAuraComponent.class).getDmgMultiplier());
            weaponStats.setCoolDown(weaponStats.getCoolDown() * aura.getComponent(WeaponAuraComponent.class).getCdMultiplier());
        }
    }
}
