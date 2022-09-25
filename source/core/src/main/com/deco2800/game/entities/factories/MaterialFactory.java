package com.deco2800.game.entities.factories;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.deco2800.game.components.ItemPickupComponent;
import com.deco2800.game.crafting.Materials;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

public class MaterialFactory {

    public static Entity createBaseMaterial() {
        Entity material = new Entity()
                .addComponent(new PhysicsComponent().setBodyType(BodyDef.BodyType.StaticBody))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new ItemPickupComponent(PhysicsLayer.PLAYER));
        return material;
    }
    public static Entity createGold() {
        Entity gold = createBaseMaterial();
        gold.addComponent(new TextureRenderComponent("images/Crafting-assets-sprint1/materials/gold.png"));
        gold.setEntityType(EntityTypes.GOLD);
        gold.setEntityType(EntityTypes.CRAFTABLE);
        return gold;
    }

    public static Entity createPoop() {
        Entity poop = createBaseMaterial();
        poop.addComponent(new TextureRenderComponent("images/Crafting-assets-sprint1/materials/rainbow_poop.png"));
        poop.setEntityType(EntityTypes.POOP);
        poop.setEntityType(EntityTypes.CRAFTABLE);
        return poop;
    }

    public static Entity createIron() {
        Entity iron = createBaseMaterial();
        iron.addComponent(new TextureRenderComponent("images/Crafting-assets-sprint1/materials/iron.png"));
        iron.setEntityType(EntityTypes.IRON);
        iron.setEntityType(EntityTypes.CRAFTABLE);
        return iron;
    }

    public static Entity createSteel() {
        Entity steel = createBaseMaterial();
        steel.addComponent(new TextureRenderComponent("images/Crafting-assets-sprint1/materials/steel.png"));
        steel.setEntityType(EntityTypes.STEEL);
        steel.setEntityType(EntityTypes.CRAFTABLE);
        return steel;
    }

    public static Entity createWood() {
        Entity wood = createBaseMaterial();
        wood.addComponent(new TextureRenderComponent("images/Crafting-assets-sprint1/materials/wood.png"));
        wood.setEntityType(EntityTypes.WOOD);
        wood.setEntityType(EntityTypes.CRAFTABLE);
        return wood;
    }

    public static Entity createPlastic() {
        Entity plastic = createBaseMaterial();
        plastic.addComponent
                (new TextureRenderComponent("images/Crafting-assets-sprint1/materials/plastic.png"));
        plastic.setEntityType(EntityTypes.PLASTIC);
        plastic.setEntityType(EntityTypes.CRAFTABLE);
        return plastic;
    }

    public static Entity createRubber() {
        Entity rubber = createBaseMaterial();
        rubber.addComponent
                (new TextureRenderComponent("images/Crafting-assets-sprint1/materials/rubber.png"));
        rubber.setEntityType(EntityTypes.RUBBER);
        rubber.setEntityType(EntityTypes.CRAFTABLE);
        return rubber;
    }

    public static Entity createPlatinum() {
        Entity platinum = createBaseMaterial();
        platinum.addComponent
                (new TextureRenderComponent("images/Crafting-assets-sprint1/materials/platinum.png"));
        platinum.setEntityType(EntityTypes.PLATINUM);
        platinum.setEntityType(EntityTypes.CRAFTABLE);
        return platinum;
    }

    public static Entity createSilver() {
        Entity silver = createBaseMaterial();
        silver.addComponent
                (new TextureRenderComponent("images/Crafting-assets-sprint1/materials/silver.png"));
        silver.setEntityType(EntityTypes.SILVER);
        silver.setEntityType(EntityTypes.CRAFTABLE);
        return silver;
    }
}