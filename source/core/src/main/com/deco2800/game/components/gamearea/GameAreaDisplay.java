package com.deco2800.game.components.gamearea;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.GdxGame;
import com.deco2800.game.areas.GameArea;
import com.deco2800.game.components.CombatItemsComponents.MeleeStatsComponent;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.maingame.MainGameActions;
import com.deco2800.game.components.maingame.PauseMenuActions;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.components.player.KeyboardPlayerInputComponent;
import com.deco2800.game.components.player.OpenCraftingComponent;
import com.deco2800.game.components.player.OpenPauseComponent;
import com.deco2800.game.crafting.CraftingLogic;
import com.deco2800.game.crafting.Materials;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.crafting.CraftingSystem;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.configs.CombatItemsConfig.MeleeConfig;
import com.deco2800.game.entities.configs.CombatItemsConfig.WeaponConfig;
import com.deco2800.game.entities.factories.ArmourFactory;
import com.deco2800.game.entities.factories.EntityTypes;
import com.deco2800.game.entities.factories.MaterialFactory;
import com.deco2800.game.entities.factories.WeaponFactory;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * Displays the name of the current game area.
 */
public class GameAreaDisplay extends UIComponent {

  private String gameAreaName = "";
  private Label title;

  private static final Logger logger = LoggerFactory.getLogger(GameAreaDisplay.class);
  private static Component mainGameActions;
  private int numcrafted = 0;
  private ImageButton craftButton;
  private ImageButton catalogueButton;
  private ImageButton catOneButton;
  private ImageButton catTwoButton;
  private ImageButton inventoryButton;
  private ImageButton exitButton;
  private Texture buttonTexture;

  private TextureRegion buttonTextureRegion;
  private TextureRegionDrawable buttonDrawable;
  private Image craftMenu;
  private List<MeleeConfig> possibleBuilds;
  Entity currentWeapon;
  private Image catOneMenu;
  private Image catTwoMenu;
  private Image pauseMenu;
  private ImageButton material;
  private ImageButton firstToCraft;
  private ImageButton secondToCraft;

  private Image resume_image;
  private ImageButton resume;

  private ImageButton exit;
  private Texture materialTexture;
  private TextureRegion materialTextureRegion;
  private TextureRegionDrawable materialDrawable;

  private String weaponType = "";
  private Image weapon;
  private Group craftingGroup = new Group();
  private Group materialsGroup = new Group();
  private Materials[] boxes = new Materials[2];
  private Group pausingGroup = new Group();

  private int firstTime = 0;
  List<Entity> inventory;
  InventoryComponent inventoryComponent;
  private int index;
  private Image inventoryMenu;
  private Group inventoryGroup = new Group();
  private List<Entity> items;

  // Janky fix for deathscreen, temp fix
  private Image deathScreen;
  private Image deathScreenTwo;
  private Image deathScreenThree;

    @Override
    public void create() {
        super.create();
        deathScreenDisplay();
        addActors();
    }

  public GameAreaDisplay(String gameAreaName) {
    this.gameAreaName = gameAreaName;
    logger.info("The current map is {}", this.gameAreaName);
    ServiceLocator.registerCraftArea(this);
    ServiceLocator.registerInventoryArea(this);
    ServiceLocator.registerPauseArea(this);
  }

  public String getGameAreaName() {
    return gameAreaName;

  }

    private void addActors() {
        title = new Label(this.gameAreaName, skin, "large");
        stage.addActor(title);
    }

    public void updateInventoryDisplay() {
        inventoryGroup.clear();
        ServiceLocator.getInventoryArea().displayInventoryMenu();
        ServiceLocator.getInventoryArea().displayItems();
        ServiceLocator.getInventoryArea().displayEquipables();
    }

    /**
     * Displays the inventory UI.
     *
     * INVENTORY_DISPLAY Self-made tag for the ease of searching
     */
    public void displayInventoryMenu() {
        inventoryMenu = new Image(new Texture(Gdx.files.internal
                ("images/Inventory/Inventory_v3.png")));
        //Note: the position of the asset is at the bottom left.
        inventoryMenu.setSize(1024, 768);
        inventoryMenu.setPosition(Gdx.graphics.getWidth() / 2 - inventoryMenu.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - inventoryMenu.getHeight() / 2);
        inventoryGroup.addActor(inventoryMenu);
        stage.addActor(inventoryGroup);
        stage.draw();
    }

    /**
     * Displays the items that the player has equipped.
     */
    public void displayEquipables() {
        InventoryComponent inventory = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
        for (Entity item : inventory.getEquipables()) {
            if (item != null) {
                int itemSlot;
                float padding = 128 + 64;
                final float horizontalPosition = (inventoryMenu.getX() + 696);
                float verticalPosition;
                Texture itemTexture = new Texture(item.getComponent(TextureRenderComponent.class).getTexturePath());
//                Texture itemTexture = item.getComponent(TextureRenderComponent.class).getTexture();
                TextureRegion itemTextureRegion = new TextureRegion(itemTexture);
                TextureRegionDrawable itemTextureDrawable = new TextureRegionDrawable(itemTextureRegion);
                ImageButton equippedItem = new ImageButton(itemTextureDrawable);
                equippedItem.setSize(128, 128);

                if (item.checkEntityType(EntityTypes.WEAPON)) {
                    verticalPosition = inventoryMenu.getY() + 416;
                    itemSlot = 0;
                } else {
                    verticalPosition = inventoryMenu.getY() + 416 - padding;
                    itemSlot = 1;
                }
                equippedItem.setPosition(horizontalPosition, verticalPosition);
                equippedItem.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        TextButton unequipBtn = new TextButton("Unequip", skin);
                        TextButton dropItemBtn = new TextButton("Drop item", skin);
                        unequipBtn.setPosition(horizontalPosition, verticalPosition);
                        dropItemBtn.setPosition(horizontalPosition, verticalPosition - 50);
                        inventoryGroup.addActor(unequipBtn);
                        inventoryGroup.addActor(dropItemBtn);
                        unequipBtn.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                if (inventory.unequipItem(itemSlot)) {
                                    inventoryGroup.removeActor(equippedItem);
                                    updateInventoryDisplay();
                                }
                                if (unequipBtn.isPressed() || dropItemBtn.isPressed()) {
                                    inventoryGroup.removeActor(unequipBtn);
                                    inventoryGroup.removeActor(dropItemBtn);
                                }
                            }
                        });
                        dropItemBtn.addListener(
                                new ChangeListener() {
                                    @Override
                                    public void changed(ChangeEvent event, Actor actor) {
                                        if (inventory.removeEquipable(itemSlot)) {
                                            inventoryGroup.removeActor(equippedItem);
                                            updateInventoryDisplay();
                                        }
                                        //Team 4, Call drop item function here
                                        if (unequipBtn.isPressed() || dropItemBtn.isPressed()) {
                                            inventoryGroup.removeActor(unequipBtn);
                                            inventoryGroup.removeActor(dropItemBtn);
                                        }
                                    }
                                }
                        );
                        inventoryGroup.addActor(unequipBtn);
                        inventoryGroup.addActor(dropItemBtn);
                    }
                });
                inventoryGroup.addActor(equippedItem);
            }
        }
    }

    /**
     * Displays each item in the inventory in the inventory storage blocks.
     * Implemented by Team 2.
     */

    public void displayItems() {
        float padding = 32f;
        InventoryComponent inventory = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
        items = inventory.getInventory();
        for (int i = 0; i < items.size(); ++i) {
            Entity currentItem = items.get(i);
            Texture itemTexture = new Texture(currentItem.getComponent(TextureRenderComponent.class).getTexturePath());
//            Texture itemTexture = currentItem.getComponent(TextureRenderComponent.class).getTexture();
            TextureRegion itemTextureRegion = new TextureRegion(itemTexture);
            TextureRegionDrawable itemTextureDrawable = new TextureRegionDrawable(itemTextureRegion);
            ImageButton item = new ImageButton(itemTextureDrawable);
            item.setSize(64, 64);
            int row = i / 4;
            int column = i % 4;
            float horizontalPosition = (inventoryMenu.getX() + 192) + column * (padding + 64);
            float verticalPosition = (inventoryMenu.getY() + 496) - row * (padding + 64);
            item.setPosition(horizontalPosition, verticalPosition);
            // Triggers an event when the button is pressed.
            String buttonText;

            if (items.get(i).checkEntityType(EntityTypes.WEAPON)
                    || items.get(i).checkEntityType(EntityTypes.ARMOUR)) {
                buttonText = "Equip item";
            } else if (items.get(i).checkEntityType(EntityTypes.POTION)) {
                buttonText = "Add to quick bar";
            } else {
                buttonText = "Crafting";
            }
            item.addListener(
                    new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent changeEvent, Actor actor) {
//              Group dropDownMenuBtn = new Group();
//              dropDownMenuBtn.addActor(itemOpBtn);
//              dropDownMenuBtn.addActor(dropItemBtn);

                            TextButton itemOpBtn = new TextButton(buttonText, skin);
                            TextButton dropItemBtn = new TextButton("Drop item", skin);
                            itemOpBtn.setPosition(horizontalPosition, verticalPosition);
                            dropItemBtn.setPosition(horizontalPosition, verticalPosition - 50);
                            dropItemBtn.addListener(
                                    new ChangeListener() {
                                        @Override
                                        public void changed(ChangeEvent event, Actor actor) {
                                            if (inventory.removeItem(currentItem)) {
                                                inventoryGroup.removeActor(item);
                                                updateInventoryDisplay();
                                            }
                                            //Team 4, Call drop item function here
//                          Testing drop item function code
//                          float x = inventory.getEntity().getPosition().x;
//                          float y = inventory.getEntity().getPosition().y;
//                          ServiceLocator.getEntityService().register(currentItem);
//                          currentItem.setPosition(x , (float) (y - 1.2));
                                            if (itemOpBtn.isPressed() || dropItemBtn.isPressed()) {
                                                inventoryGroup.removeActor(itemOpBtn);
                                                inventoryGroup.removeActor(dropItemBtn);
                                            }
                                        }
                                    }
                            );
                            itemOpBtn.addListener(
                                    new ChangeListener() {
                                        @Override
                                        public void changed(ChangeEvent event, Actor actor) {
                                            switch (buttonText) {
                                                case "Equip item":
                                                    if (inventory.equipItem(currentItem)) {
                                                        //animation
                                                        String description = inventory.getEquipables()[0].getComponent(MeleeStatsComponent.class).getDescription();
                                                        inventory.getCombatAnimator().getEvents().trigger(description);
                                                        updateInventoryDisplay();
                                                    }
                                                    break;
                                                case "Add to quick bar":
                                                    if (inventory.addQuickBarItems(currentItem)) {
                                                        inventory.removeItem(currentItem);
                                                        updateInventoryDisplay();
                                                    }
                                                    break;
                                            }
                                            if (itemOpBtn.isPressed() || dropItemBtn.isPressed()) {
                                                inventoryGroup.removeActor(itemOpBtn);
                                                inventoryGroup.removeActor(dropItemBtn);
                                            }
                                        }
                                    }
                            );
                            if (!buttonText.equals("Crafting")) {
                                inventoryGroup.addActor(itemOpBtn);
                            }
                            inventoryGroup.addActor(dropItemBtn);
                        }
                    });
            inventoryGroup.addActor(item);
        }
    }

    /**
     * Display each item in the inventory in the inventory storage blocks.
     * Implemented by Peter.
     */
    public void displayItems(float padding, float pictureWidth, float pictureHeight) {
        InventoryComponent inventory = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
        int craftingTableX = 0;
        int craftingTableY = 0;
        items = inventory.getInventory();
        for (int i = 0; i < items.size(); ++i) {
            Entity currentItem = items.get(i);
            Texture itemTexture = currentItem.getComponent(TextureRenderComponent.class).getTexture();
            TextureRegion itemTextureRegion = new TextureRegion(itemTexture);
            TextureRegionDrawable itemTextureDrawable = new TextureRegionDrawable(itemTextureRegion);
            ImageButton item = new ImageButton(itemTextureDrawable);
            item.setSize(pictureWidth, pictureHeight);
            int row = i / 4;
            int column = i % 4;
            //These positions should be adjusted according to your crafting menu positions
            float horizontalPosition = (inventoryMenu.getX() + craftingTableX) + column * (padding + pictureWidth);
            float verticalPosition = (inventoryMenu.getY() + craftingTableY) - row * (padding + pictureHeight);
            item.setPosition(horizontalPosition, verticalPosition);
        }
    }


    /**
     * Disposes the inventory display group.
     */
    public void disposeInventoryMenu() {
        inventoryGroup.remove();
    }


    /**
     * Display deathscreen in game as a very temporary fix for grading purposes.
     */
    public void deathScreenDisplay() {
        deathScreen = new Image(new Texture(Gdx.files.internal
                ("images/DeathScreens/lvl 1.PNG")));
        deathScreen.setSize(250, 160);
        deathScreen.setPosition(0, 0);

        deathScreenTwo = new Image(new Texture(Gdx.files.internal
                ("images/DeathScreens/lvl 2.PNG")));
        deathScreenTwo.setSize(250, 160);
        deathScreenTwo.setPosition(0, 160);

        deathScreenThree = new Image(new Texture(Gdx.files.internal
                ("images/DeathScreens/lvl3.PNG")));
        deathScreenThree.setSize(250, 160);
        deathScreenThree.setPosition(0, 320);

        stage.addActor(deathScreen);
        stage.addActor(deathScreenTwo);
        stage.addActor(deathScreenThree);

    }

    /**
     * Code that opens an overlay crafting menu when the craft button is pressed. Creates assets based on users inventory
     * and creates button event handlers to test for user clicks.
     */
    public void openCraftingMenu() {
        if (firstTime == 0) {
            inventoryComponent = ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class);
            inventoryComponent.addItem(MaterialFactory.createGold());
            inventoryComponent.addItem(MaterialFactory.createSteel());
            inventoryComponent.addItem(MaterialFactory.createPoop());
            firstTime += 1;
        }
        /*if (getGameAreaName().equals("Underground")) {
            craftMenu = new Image(new Texture(Gdx.files.internal
                    ("images/Crafting-assets-sprint1/crafting table/crafting_level2_inventory.png")));
        } else {*/
            craftMenu = new Image(new Texture(Gdx.files.internal
                    ("images/Crafting-assets-sprint1/crafting table/crafting_inventory.png")));
        //}
        craftMenu.setSize(883.26f, 500);
        craftMenu.setPosition(Gdx.graphics.getWidth() / 2 - craftMenu.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - craftMenu.getHeight() / 2);
        craftingGroup.addActor(craftMenu);

        getInventory();
        buttonTexture = new Texture(Gdx.files.internal
                ("images/Crafting-assets-sprint1/widgets/craft_button.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        craftButton = new ImageButton(buttonDrawable);
        craftButton.setSize(146, 146);
        craftButton.setPosition(craftMenu.getX() + 527, craftMenu.getY() + 110.5f);
        craftButton.addListener(new ChangeListener() {

            // Method to add item to players inventory
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (weapon != null) {
                    disposeFirstBox();
                    disposeSecondBox();
                    ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class).addItem(currentWeapon);
                    inventoryComponent.addItem(currentWeapon);
                    weapon.remove();
                    weapon = null;
                    clearBoxes(0);
                }
                ;
                getInventory();
            }
        });
        craftingGroup.addActor(craftButton);
        entity.getEvents().addListener("check", this::checkBuildables);
        buttonTexture = new Texture(Gdx.files.internal
                ("images/Crafting-assets-sprint1/widgets/catalogue_button.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        catalogueButton = new ImageButton(buttonDrawable);
        catalogueButton.setSize(146, 146);
        catalogueButton.setPosition(craftMenu.getX() + 300, craftMenu.getY() + 302);
        catalogueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                displayCatOne();
            }
        });
        craftingGroup.addActor(catalogueButton);
        buttonTexture = new Texture(Gdx.files.internal
                ("images/Crafting-assets-sprint1/widgets/exit_button.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        exitButton = new ImageButton(buttonDrawable);
        exitButton.setSize(35, 35);
        exitButton.setPosition(craftMenu.getX() + 720, craftMenu.getY() + 365);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                disposeCraftingMenu();
                EntityService.pauseAndResume();
                OpenCraftingComponent.setCraftingStatus();
            }
        });
        craftingGroup.addActor(exitButton);
        stage.addActor(craftingGroup);
        stage.draw();
    }

  /**
   * Display the pause menu when ESC is clicked.
   */
  public void setPauseMenu() {
    pauseMenu = new Image(new Texture(Gdx.files.internal
            ("images/Crafting-assets-sprint1/screens/pauseScreen.png")));
    pauseMenu.setSize(1100, 1200);
    pauseMenu.setPosition(Gdx.graphics.getWidth()/2 - pauseMenu.getWidth()/2,
            Gdx.graphics.getHeight()/2 - pauseMenu.getHeight()/2);
    pausingGroup.addActor(pauseMenu);
    stage.addActor(pausingGroup);

    buttonTexture = new Texture(Gdx.files.internal
            ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
    buttonTextureRegion = new TextureRegion(buttonTexture);
    buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
    resume = new ImageButton(buttonDrawable);
    resume.setSize(386, 122.4f);
    resume.setPosition(pauseMenu.getX() + 356.8f, pauseMenu.getY() + 600);
    resume.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        disposePauseMenu();
        EntityService.pauseAndResume();
        KeyboardPlayerInputComponent.incrementPauseCounter();
        OpenPauseComponent.setPauseMenuStatus();
      }
    });
    pausingGroup.addActor(resume);
    stage.addActor(pausingGroup);

    buttonTexture = new Texture(Gdx.files.internal
            ("images/crafting_assets_sprint2/transparent-texture-buttonClick.png"));
    buttonTextureRegion = new TextureRegion(buttonTexture);
    buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
    exit = new ImageButton(buttonDrawable);
    exit.setSize(386, 122.4f);
    exit.setPosition(pauseMenu.getX() + 356.8f, pauseMenu.getY() + 432);
    exit.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        logger.debug("Exit button clicked");
        PauseMenuActions.setQuitGameStatus();
        KeyboardPlayerInputComponent.incrementPauseCounter();
        OpenPauseComponent.setPauseMenuStatus();
      }
    });
    pausingGroup.addActor(exit);
    stage.addActor(pausingGroup);

    stage.draw();
  }

  public void disposeResumeButton() {
    resume_image.remove();
  }

    private void checkBuildables() {
        if (boxes[0] != null && boxes[1] != null) {
            for (MeleeConfig item : possibleBuilds) {
                int numItems = 0;
                for (Map.Entry entry : item.materials.entrySet()) {
                    String entryString = entry.toString().split("=")[0];
                    String upperCaseEntry = entryString.substring(0, 1).toUpperCase() + entryString.substring(1);
                    if (boxes[0].toString().equals(upperCaseEntry) ||
                            boxes[1].toString().equals(upperCaseEntry)) {

                        numItems += 1;
                    }
                }
                if (numItems == 2) {
                    displayWeapon(item);
                    break;
                }
            }
        }
    }

    //return the inventory for the user

    private void getInventory() {
        index = 0;
        this.possibleBuilds = CraftingLogic.getPossibleWeapons();

        inventory = inventoryComponent.getInventory();
        System.out.println(inventory);
        //ServiceLocator.getGameArea().getPlayer().getComponent(InventoryComponent.class)
        for (Entity item : inventory) {
            if (item.checkEntityType(EntityTypes.CRAFTABLE)) {
                materialTexture = new Texture(item.getComponent(TextureRenderComponent.class).getTexturePath());
                materialTextureRegion = new TextureRegion(materialTexture);
                materialDrawable = new TextureRegionDrawable(materialTextureRegion);
                if (item.checkEntityType((EntityTypes.WEAPON))){
                    materialDrawable.setMinSize(150, 150);
                }

                material = new ImageButton(materialDrawable);
                if (!(item.checkEntityType((EntityTypes.WEAPON)))) {
                    material.setSize(50, 50);

                    material.setPosition(craftMenu.getX() + 172 + ((index % 4) * 68),
                            (float) (craftMenu.getTop() - ((Math.floor(index / 4) * 62) + 208)));
                } else {
                    material.setPosition(craftMenu.getX() + 125 + ((index % 4) * 68),
                            (float) (craftMenu.getTop() - ((Math.floor(index / 4) * 62) + 265)));
                }
                index++;
                material.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (boxes[0] == null) {
                            clearMaterials();
                            materialTexture = new Texture(item.getComponent(TextureRenderComponent.class).getTexturePath());
                            materialTextureRegion = new TextureRegion(materialTexture);
                            materialDrawable = new TextureRegionDrawable(materialTextureRegion);
                            if (item.checkEntityType((EntityTypes.WEAPON))){
                                materialDrawable.setMinSize(150, 150);
                            }
                            firstToCraft = new ImageButton(materialDrawable);
                            if (!(item.checkEntityType((EntityTypes.WEAPON)))) {
                                firstToCraft.setSize(50, 50);
                                firstToCraft.setPosition(craftMenu.getX() + 481, craftMenu.getY() + 230);
                            } else {
                                firstToCraft.setSize(150, 150);
                                firstToCraft.setPosition(craftMenu.getX() + 435, craftMenu.getY() + 175);
                            }

                            stage.addActor(firstToCraft);
                            addToBoxes(checkType(item));
                            inventoryComponent.removeItem(checkType(item));
                            firstToCraft.addListener(new ChangeListener() {
                                @Override
                                public void changed(ChangeEvent event, Actor actor) {
                                    disposeFirstBox();
                                    clearBoxes(1);
                                    addToInventory(checkType(item));
                                    getInventory();
                                }
                            });
                            getInventory();
                        } else if (boxes[1] == null) {
                            clearMaterials();
                            materialTexture = new Texture(item.getComponent(TextureRenderComponent.class).getTexturePath());
                            materialTextureRegion = new TextureRegion(materialTexture);
                            materialDrawable = new TextureRegionDrawable(materialTextureRegion);
                            if (item.checkEntityType((EntityTypes.WEAPON))){
                                materialDrawable.setMinSize(150, 150);
                            }
                            secondToCraft = new ImageButton(materialDrawable);
                            if (!(item.checkEntityType((EntityTypes.WEAPON)))) {
                                secondToCraft.setSize(50, 50);
                                secondToCraft.setPosition(craftMenu.getX() + 548, craftMenu.getY() + 230);
                            } else {
                                secondToCraft.setSize(150, 150);
                                secondToCraft.setPosition(craftMenu.getX() + 505, craftMenu.getY() + 175);
                            }
                            stage.addActor(secondToCraft);
                            addToBoxes(checkType(item));
                            inventoryComponent.removeItem(checkType(item));
                            secondToCraft.addListener(new ChangeListener() {
                                @Override
                                public void changed(ChangeEvent event, Actor actor) {
                                    disposeSecondBox();
                                    clearBoxes(2);
                                    addToInventory(checkType(item));
                                    getInventory();
                                }
                            });
                            getInventory();
                        }
                    }
                });
                materialsGroup.addActor(material);
                stage.addActor(materialsGroup);
            }
        }
    }

    private EntityTypes checkType(Entity entity) {
        EntityTypes result = null;
        if (entity.checkEntityType(EntityTypes.GOLD)) {
            result = EntityTypes.GOLD;
        } else if (entity.checkEntityType(EntityTypes.IRON)) {
            result = EntityTypes.IRON;
        } else if (entity.checkEntityType(EntityTypes.STEEL)) {
            result = EntityTypes.STEEL;
        } else if (entity.checkEntityType(EntityTypes.WOOD)) {
            result = EntityTypes.WOOD;
        } else if (entity.checkEntityType(EntityTypes.PLASTIC)) {
            result = EntityTypes.PLASTIC;
        } else if (entity.checkEntityType(EntityTypes.RUBBER)) {
            result = EntityTypes.RUBBER;
        } else if (entity.checkEntityType(EntityTypes.PLATINUM)) {
            result = EntityTypes.PLATINUM;
        } else if (entity.checkEntityType(EntityTypes.SILVER)) {
            result = EntityTypes.SILVER;
        } else if (entity.checkEntityType(EntityTypes.POOP)) {
            result = EntityTypes.POOP;
        } else {
            result = EntityTypes.WEAPON;
        }
        return result;
    }

    private void addToInventory(EntityTypes type) {
        if (type == EntityTypes.GOLD) {
            inventoryComponent.addItem(MaterialFactory.createGold());
        } else if (type == EntityTypes.IRON) {
            inventoryComponent.addItem(MaterialFactory.createIron());
        } else if (type == EntityTypes.STEEL) {
            inventoryComponent.addItem(MaterialFactory.createSteel());
        } else if (type == EntityTypes.WOOD) {
            inventoryComponent.addItem(MaterialFactory.createWood());
        } else if (type == EntityTypes.PLASTIC) {
            inventoryComponent.addItem(MaterialFactory.createPlastic());
        } else if (type == EntityTypes.RUBBER) {
            inventoryComponent.addItem(MaterialFactory.createRubber());
        } else if (type == EntityTypes.PLATINUM) {
            inventoryComponent.addItem(MaterialFactory.createPlatinum());
        } else if (type == EntityTypes.SILVER) {
            inventoryComponent.addItem(MaterialFactory.createSilver());
        } else if (type == EntityTypes.POOP) {
            inventoryComponent.addItem(MaterialFactory.createPoop());
        } else {
            inventoryComponent.addItem(WeaponFactory.createHera());
        }
    }

    private void addToBoxes(EntityTypes type) {
        Materials materials = Materials.Gold;
        if (type == EntityTypes.GOLD) {
            materials = Materials.Gold;
        } else if (type == EntityTypes.IRON) {
            materials = Materials.Iron;
        } else if (type == EntityTypes.STEEL) {
            materials = Materials.Steel;
        } else if (type == EntityTypes.WOOD) {
            materials = Materials.Wood;
        } else if (type == EntityTypes.PLASTIC) {
            materials = Materials.Plastic;
        } else if (type == EntityTypes.RUBBER) {
            materials = Materials.Rubber;
        } else if (type == EntityTypes.PLATINUM) {
            materials = Materials.Platinum;
        } else if (type == EntityTypes.SILVER) {
            materials = Materials.Silver;
        } else if (type == EntityTypes.POOP) {
            materials = Materials.Poop;
        }
        else {
            materials = Materials.HerraDag;
        }
        if (this.boxes[0] == null)
            boxes[0] = materials;
        else if (this.boxes[1] == null)
            boxes[1] = materials;
        checkBuildables();
    }

    //0 Clears both boxes
    //1 Clears box 1
    //2 Clears box 2
    private void clearBoxes(int number) {
        if (number == 0) {
            boxes[0] = null;
            boxes[1] = null;
            if (weapon != null) {
                weapon.remove();
                weapon = null;
            }
        } else if (number == 1) {
            boxes[0] = null;
            if (weapon != null) {
                weapon.remove();
                weapon = null;
            }
        } else if (number == 2) {
            boxes[1] = null;
            if (weapon != null) {
                weapon.remove();
                weapon = null;
            }
        }

    }

    public void displayCatOne() {
        disposeMaterials();
        /*if (getGameAreaName().equals("Underground")) {
            catOneMenu = new Image(new Texture(Gdx.files.internal
                    ("images/Crafting-assets-sprint1/crafting table/crafting_level2_catalogue1.png")));
        } else {*/
            catOneMenu = new Image(new Texture(Gdx.files.internal
                    ("images/Crafting-assets-sprint1/crafting table/crafting_catalogue_1.png")));
        //}
        catOneMenu.setSize(883.26f, 500);
        catOneMenu.setPosition(Gdx.graphics.getWidth() / 2 - catOneMenu.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - catOneMenu.getHeight() / 2);
        craftingGroup.addActor(catOneMenu);
        exitButton.setZIndex(catOneMenu.getZIndex() + 1);
        buttonTexture = new Texture(Gdx.files.internal
                ("images/Crafting-assets-sprint1/widgets/inventory_button.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        inventoryButton = new ImageButton(buttonDrawable);
        inventoryButton.setSize(146, 146);
        inventoryButton.setPosition(craftMenu.getX() + 150, craftMenu.getY() + 301);
        inventoryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                disposeCatOne();
                getInventory();
            }
        });
        craftingGroup.addActor(inventoryButton);
        buttonTexture = new Texture(Gdx.files.internal
                ("images/Crafting-assets-sprint1/widgets/catalogue_2_button.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        catTwoButton = new ImageButton(buttonDrawable);
        catTwoButton.setSize(60, 60);
        catTwoButton.setPosition(craftMenu.getX() + 110, craftMenu.getY() + 159);
        catTwoButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                disposeCatOne();
                displayCatTwo();
            }
        });
        craftingGroup.addActor(catTwoButton);
    }

    private void displayCatTwo() {
        disposeMaterials();
        /*if (getGameAreaName().equals("Underground")) {
            catTwoMenu = new Image(new Texture(Gdx.files.internal
                    ("images/Crafting-assets-sprint1/crafting table/crafting_level2_catalogue2.png")));
        } else {*/
            catTwoMenu = new Image(new Texture(Gdx.files.internal
                    ("images/Crafting-assets-sprint1/crafting table/crafting_catalogue_2.png")));
        //}
        catTwoMenu.setSize(883.26f, 500);
        catTwoMenu.setPosition(Gdx.graphics.getWidth() / 2 - catTwoMenu.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - catTwoMenu.getHeight() / 2);
        craftingGroup.addActor(catTwoMenu);
        exitButton.setZIndex(catTwoMenu.getZIndex() + 1);
        buttonTexture = new Texture(Gdx.files.internal
                ("images/Crafting-assets-sprint1/widgets/inventory_button.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        inventoryButton = new ImageButton(buttonDrawable);
        inventoryButton.setSize(146, 146);
        inventoryButton.setPosition(craftMenu.getX() + 150, craftMenu.getY() + 301);
        inventoryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                disposeCatTwo();
                getInventory();
            }
        });
        craftingGroup.addActor(inventoryButton);
        buttonTexture = new Texture(Gdx.files.internal
                ("images/Crafting-assets-sprint1/widgets/catalogue_1_button.png"));
        buttonTextureRegion = new TextureRegion(buttonTexture);
        buttonDrawable = new TextureRegionDrawable(buttonTextureRegion);
        catOneButton = new ImageButton(buttonDrawable);
        catOneButton.setSize(60, 60);
        catOneButton.setPosition(craftMenu.getX() + 110, craftMenu.getY() + 225);
        catOneButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                disposeCatTwo();
                displayCatOne();
            }
        });
        craftingGroup.addActor(catOneButton);
    }

    private void disposeCatOne() {
        catOneMenu.remove();
        inventoryButton.remove();
        catTwoButton.remove();
    }

    private void disposeCatTwo() {
        catTwoMenu.remove();
        inventoryButton.remove();
        catOneButton.remove();
    }

    private void clearMaterials() {
        materialsGroup.clear();
    }

    private void disposeMaterials() {
        materialsGroup.remove();
    }

    private void disposeFirstBox() {
        firstToCraft.remove();
    }

    private void disposeSecondBox() {
        secondToCraft.remove();
    }

    public void disposeCraftingMenu() {
        try {
            clearMaterials();
            clearBoxes(0);
            disposeFirstBox();
            disposeSecondBox();
        } catch (NullPointerException e) {
        }
        craftingGroup.remove();
    }

    public void disposePauseMenu() {
        pausingGroup.remove();
    }

    private void displayWeapon(MeleeConfig item) {
        Entity newItem = CraftingLogic.damageToWeapon(item);
        currentWeapon = newItem;
        String image = newItem.getComponent(TextureRenderComponent.class).getTexturePath();
        weapon = new Image(new Texture(Gdx.files.internal(image)));

        if (Math.floor(item.damage) == 25) {
            weapon.setSize(60, 60);
            weaponType = "Trident";
            weapon.setPosition(craftMenu.getX() + 650, craftMenu.getY() + 220);
        } else if (Math.floor(item.damage) == 26) {
            weapon.setSize(60, 60);
            weapon.setPosition(craftMenu.getX() + 675, craftMenu.getY() + 235);
        } else if (Math.floor(item.damage) == 5) {
            weapon.setSize(100, 100);
            weapon.setPosition(craftMenu.getX() + 640, craftMenu.getY() + 210);
        } else if (Math.floor(item.damage) == 3) {
            weapon.setSize(110, 110);
            weapon.setPosition(craftMenu.getX() + 640, craftMenu.getY() + 200);
        } else if (Math.floor(item.damage) == 35) {
            weapon.setSize(100, 100);
            weapon.setPosition(craftMenu.getX() + 640, craftMenu.getY() + 200);
        } else {
            weapon.setSize(200, 200);
            weapon.setPosition(craftMenu.getX() + 600, craftMenu.getY() + 150);
        }
        numcrafted += 1;
        craftingGroup.addActor(weapon);
    }

    @Override
    public void draw(SpriteBatch batch) {
        int screenHeight = Gdx.graphics.getHeight();
        float offsetX = 10f;
        float offsetY = 30f;

        title.setPosition(offsetX, screenHeight - offsetY);
    }

    @Override
    public void dispose() {
        super.dispose();
        title.remove();
    }
}