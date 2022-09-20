package com.deco2800.game.entities.factories;

import com.deco2800.game.components.CombatItemsComponents.WeaponPickupComponent;
import com.deco2800.game.components.ItemPickupComponent;
import com.deco2800.game.crafting.Materials;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.rendering.TextureRenderComponent;

public class MaterialFactory {

    public static Entity createGold() {
        Entity gold = new Entity();
        gold.addComponent(new TextureRenderComponent("images/Crafting-assets-sprint1/materials/gold.png"));
        gold.addComponent(new ItemPickupComponent(PhysicsLayer.PLAYER));
        gold.addComponent(new WeaponPickupComponent(PhysicsLayer.PLAYER));
        gold.setEntityType(EntityTypes.GOLD);
        gold.setEntityType(EntityTypes.CRAFTABLE);
        return gold;
    }

    public static Entity createIron() {
        Entity iron = new Entity();
        iron.addComponent(new TextureRenderComponent("images/Crafting-assets-sprint1/materials/iron.png"));
        iron.setEntityType(EntityTypes.IRON);
        iron.setEntityType(EntityTypes.CRAFTABLE);
        return iron;
    }

    public static Entity createSteel() {
        Entity steel = new Entity();
        steel.addComponent(new TextureRenderComponent("images/Crafting-assets-sprint1/materials/steel.png"));
        steel.setEntityType(EntityTypes.STEEL);
        steel.setEntityType(EntityTypes.CRAFTABLE);
        return steel;
    }

    public static Entity createWood() {
        Entity wood = new Entity();
        wood.addComponent(new TextureRenderComponent("images/Crafting-assets-sprint1/materials/wood.png"));
        wood.setEntityType(EntityTypes.WOOD);
        wood.setEntityType(EntityTypes.CRAFTABLE);
        return wood;
    }

    public static Entity createPlastic() {
        Entity plastic = new Entity();
        plastic.addComponent
                (new TextureRenderComponent("images/Crafting-assets-sprint1/materials/plastic.png"));
        plastic.setEntityType(EntityTypes.PLASTIC);
        plastic.setEntityType(EntityTypes.CRAFTABLE);
        return plastic;
    }

    public static Entity createRubber() {
        Entity rubber = new Entity();
        rubber.addComponent
                (new TextureRenderComponent("images/Crafting-assets-sprint1/materials/rubber.png"));
        rubber.setEntityType(EntityTypes.RUBBER);
        rubber.setEntityType(EntityTypes.CRAFTABLE);
        return rubber;
    }

    public static Entity createPlatinum() {
        Entity platinum = new Entity();
        platinum.addComponent
                (new TextureRenderComponent("images/Crafting-assets-sprint1/materials/platinum.png"));
        platinum.setEntityType(EntityTypes.PLATINUM);
        platinum.setEntityType(EntityTypes.CRAFTABLE);
        return platinum;
    }

    public static Entity createSilver() {
        Entity silver = new Entity();
        silver.addComponent
                (new TextureRenderComponent("images/Crafting-assets-sprint1/materials/silver.png"));
        silver.setEntityType(EntityTypes.SILVER);
        silver.setEntityType(EntityTypes.CRAFTABLE);
        return silver;
    }
}