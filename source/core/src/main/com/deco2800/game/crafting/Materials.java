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
    Silver("Silver", 1, "images/Crafting-assets-sprint1/materials/silver.png"),

    HerraDag("HerraDag", 1, "images/CombatItems/Sprint-1/Level 2 Dagger 2png.png"),

    Poop("Poop", 1, "images/Crafting-assets-sprint1/materials/rainbow_poop.png"),

    ToiletPaper("ToiletPaper", 1, "images/Crafting-assets-sprint1/materials/toilet_paper.png");

    /* The name of the material */
    private final String material;

    /* The amound of material present */
    private final int amount;

    /* location of the image for the material */
    private final String image;

    /**
     * Creates the enum material objext
     * @param material the name of the material
     * @param amount the amount present
     * @param image the location of the image for the material
     */
    Materials(String material, int amount, String image) {
        this.material = material;
        this.amount = amount;
        this.image = image;
    }
}