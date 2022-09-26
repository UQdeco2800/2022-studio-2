package com.deco2800.game.components.CombatItemsComponents;

import com.deco2800.game.components.Component;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.entities.Entity;

//this is tied to the player
public class WeaponAuraManager extends Component {

    Entity auraApplied;
    private long auraEndTime;
    MeleeStatsComponent meleeStats;

    @Override
    public void update() {
        if (entity.getComponent(InventoryComponent.class).getEquipable(0) != null) {
            checkAuraEffect();
        }
    }

    public void checkAuraEffect() {
        if (auraApplied != null && System.currentTimeMillis() > auraEndTime) {
            meleeStats.setDamage(meleeStats.getDamage() / auraApplied.getComponent(WeaponAuraComponent.class).getDmgMultiplier());
            meleeStats.setCoolDown(meleeStats.getCoolDown() / auraApplied.getComponent(WeaponAuraComponent.class).getCdMultiplier());
            auraApplied = null;
            auraEndTime = 0;
        }
    }

    public void applyAura(Entity aura, Entity weapon) {
        auraEndTime = System.currentTimeMillis() + aura.getComponent(WeaponAuraComponent.class).getAuraDuration();
        auraApplied = aura;
        meleeStats = weapon.getComponent(MeleeStatsComponent.class);
        meleeStats.setDamage(meleeStats.getDamage() * aura.getComponent(WeaponAuraComponent.class).getDmgMultiplier());

        meleeStats.setCoolDown(meleeStats.getCoolDown() * aura.getComponent(WeaponAuraComponent.class).getCdMultiplier());
    }


}
