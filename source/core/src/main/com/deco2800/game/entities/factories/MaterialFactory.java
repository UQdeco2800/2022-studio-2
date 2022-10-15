package com.deco2800.game.entities.factories;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Null;
import com.deco2800.game.components.ItemPickupComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;

/**
 * Factory style class used for creating materials of each type with certain components and base images.
 */
public class MaterialFactory {

    private MaterialFactory(){}

    /**
     * Creates a base material with the physics, hitbox and possibility to pick up. Used as a base for other materials.
     * @return the base material
     */
    public static Entity createBaseMaterial() {
        return new Entity()
                .addComponent(new PhysicsComponent().setBodyType(BodyDef.BodyType.StaticBody))
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new ItemPickupComponent(PhysicsLayer.PLAYER));
    }

    /**
     * Creates a gold material with the image and entity types set.
     * @return the gold material used for crafting.
     */
    public static Entity createGold() {
        Entity gold = createBaseMaterial();
        gold.addComponent(new TextureRenderComponent("images/Crafting-assets-sprint1/materials/gold.png"));
        gold.setEntityType(EntityTypes.GOLD);
        gold.setEntityType(EntityTypes.CRAFTABLE);
        return gold;
    }

    /**
     * Creates a poop material with the image and entity types set.
     * @return the poop material used for crafting.
     */
    public static Entity createPoop() {
        Entity poop = createBaseMaterial();
        poop.addComponent(new TextureRenderComponent("images/Crafting-assets-sprint1/materials/rainbow_poop.png"));
        poop.setEntityType(EntityTypes.POOP);
        poop.setEntityType(EntityTypes.CRAFTABLE);
        return poop;
    }

    /**
     * Creates a Iron material with the image and entity types set.
     * @return the Iron material used for crafting.
     */
    public static Entity createIron() {
        Entity iron = createBaseMaterial();
        iron.addComponent(new TextureRenderComponent("images/Crafting-assets-sprint1/materials/iron.png"));
        iron.setEntityType(EntityTypes.IRON);
        iron.setEntityType(EntityTypes.CRAFTABLE);
        return iron;
    }

    /**
     * Creates a ToiletPaper material with the image and entity types set.
     * @return the ToiletPaper material used for crafting.
     */
    public static Entity createToiletPaper() {
        Entity iron = createBaseMaterial();
        iron.addComponent(new TextureRenderComponent("images/Crafting-assets-sprint1/materials/toilet_paper.png"));
        iron.setEntityType(EntityTypes.TOILETPAPER);
        iron.setEntityType(EntityTypes.CRAFTABLE);
        return iron;
    }

    /**
     * Creates a steel material with the image and entity types set.
     * @return the steel material used for crafting.
     */
    public static Entity createSteel() {
        Entity steel = createBaseMaterial();
        steel.addComponent(new TextureRenderComponent("images/Crafting-assets-sprint1/materials/steel.png"));
        steel.setEntityType(EntityTypes.STEEL);
        steel.setEntityType(EntityTypes.CRAFTABLE);
        return steel;
    }

    /**
     * Creates a wood material with the image and entity types set.
     * @return the wood material used for crafting.
     */
    public static Entity createWood() {
        Entity wood = createBaseMaterial();
        wood.addComponent(new TextureRenderComponent("images/Crafting-assets-sprint1/materials/wood.png"));
        wood.setEntityType(EntityTypes.WOOD);
        wood.setEntityType(EntityTypes.CRAFTABLE);
        return wood;
    }

    /**
     * Creates a plastic material with the image and entity types set.
     * @return the plastic material used for crafting.
     */
    public static Entity createPlastic() {
        Entity plastic = createBaseMaterial();
        plastic.addComponent
                (new TextureRenderComponent("images/Crafting-assets-sprint1/materials/plastic.png"));
        plastic.setEntityType(EntityTypes.PLASTIC);
        plastic.setEntityType(EntityTypes.CRAFTABLE);
        return plastic;
    }
    /**
     * Creates a rubber material with the image and entity types set.
     * @return the rubber material used for crafting.
     */
    public static Entity createRubber() {
        Entity rubber = createBaseMaterial();
        rubber.addComponent
                (new TextureRenderComponent("images/Crafting-assets-sprint1/materials/rubber.png"));
        rubber.setEntityType(EntityTypes.RUBBER);
        rubber.setEntityType(EntityTypes.CRAFTABLE);
        return rubber;
    }
    /**
     * Creates a platinum material with the image and entity types set.
     * @return the platinum material used for crafting.
     */
    public static Entity createPlatinum() {
        Entity platinum = createBaseMaterial();
        platinum.addComponent
                (new TextureRenderComponent("images/Crafting-assets-sprint1/materials/platinum.png"));
        platinum.setEntityType(EntityTypes.PLATINUM);
        platinum.setEntityType(EntityTypes.CRAFTABLE);
        return platinum;
    }
    /**
     * Creates a silver material with the image and entity types set.
     * @return the silver material used for crafting.
     */
    public static Entity createSilver() {
        Entity silver = createBaseMaterial();
        silver.addComponent
                (new TextureRenderComponent("images/Crafting-assets-sprint1/materials/silver.png"));
        silver.setEntityType(EntityTypes.SILVER);
        silver.setEntityType(EntityTypes.CRAFTABLE);
        return silver;
    }

    /**
     * Crate a gold for testing
     * @return test material
     */
    public static Entity testCreateGold() {
        Entity gold = createBaseMaterial();
        gold.setEntityType(EntityTypes.GOLD);
        gold.setEntityType(EntityTypes.CRAFTABLE);
        return gold;
    }

    /**
     * Creates the specified type of material for testing
     * @return test material
     */
    public static Entity creatTestMaterial(String materialName) {
        Entity material = createBaseMaterial();
        material.setEntityType(EntityTypes.CRAFTABLE);
        switch (materialName) {
            case "gold" -> material.setEntityType(EntityTypes.GOLD);
            case "poop" -> material.setEntityType(EntityTypes.POOP);
            case "iron" -> material.setEntityType(EntityTypes.IRON);
            case "toiletPaper" -> material.setEntityType(EntityTypes.TOILETPAPER);
            case "wood" -> material.setEntityType(EntityTypes.WOOD);
            case "plastic" -> material.setEntityType(EntityTypes.PLASTIC);
            case "rubber" -> material.setEntityType(EntityTypes.RUBBER);
            case "platinum" -> material.setEntityType(EntityTypes.PLATINUM);
            case "silver" -> material.setEntityType(EntityTypes.SILVER);
            default -> new NullPointerException();
        }
        return material;
    }
}