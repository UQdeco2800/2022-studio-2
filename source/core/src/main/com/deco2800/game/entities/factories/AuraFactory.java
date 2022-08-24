package com.deco2800.game.entities.factories;

import com.deco2800.game.CombatItems.Aura;
import com.deco2800.game.CombatItems.Weapon;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

import java.util.Arrays;
import java.util.List;

public class AuraFactory {
   private static final List<Aura> availableAuras = Arrays.asList((Aura)createWeaponSpeedBuff());

    public static List<Aura> getAvailableAuras() {
        return availableAuras;
    }

    public static Aura getAura(int auraIndex) {
        return getAvailableAuras().get(auraIndex);
    }

    public static Entity createWeaponSpeedBuff() {
        Aura speedBuff = new Aura(10, 1, 0.5, 1);
        speedBuff.addComponent(new TextureRenderComponent("images/CombatWeapons-assets-sprint1/Weapon Speed Buff.png"))
                .addComponent(new PhysicsComponent());
        speedBuff.getComponent(TextureRenderComponent.class).scaleEntity();
        speedBuff.scaleHeight(2f);
        return speedBuff;
    }
}
