package com.deco2800.game.crafting;

/**
 * Public enum that contains all the possible base materials that could be used for crafting items.
 **/
public enum Materials {
    Gold("Gold", 1, "images/Crafting-assets-sprint1/materials/gold.png"),
    Iron("Iron", 1, "images/Crafting-assets-sprint1/materials/iron.png"),
    Steel("Steel", 1, "images/Crafting-assets-sprint1/materials/steel.png"),
    Wood("Wood", 1, "images/Crafting-assets-sprint1/materials/wood.png"),
    Plastic("Plastic", 1, "images/Crafting-assets-sprint1/materials/plastic.png"),
    Rubber("Rubber", 1, "images/Crafting-assets-sprint1/materials/rubber.png"),
    Platinum("Platinum", 1, "images/Crafting-assets-sprint1/materials/platinum.png"),
    Silver("Silver", 1, "images/Crafting-assets-sprint1/materials/silver.png");

    private final String material;
    private final int amount;
    private final String image;

    Materials(String material, int amount, String image) {
        this.material = material;
        this.amount = amount;
        this.image = image;
    }
    public String getMaterial() {
        return material;
    }
    public int getAmount() {
        return amount;
    }
    public String getImage() {
        return image;
    }
}
